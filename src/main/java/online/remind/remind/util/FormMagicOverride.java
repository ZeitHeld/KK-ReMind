package online.remind.remind.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import online.kingdomkeys.kingdomkeys.data.PlayerData;
import online.kingdomkeys.kingdomkeys.item.MagicSpellItem;
import online.kingdomkeys.kingdomkeys.item.ShotlockItem;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncPlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class FormMagicOverride {

    /**
     * Stores the player's original magic loadout while an override is active.
     *
     * These are session-only snapshots. The logout handler should restore the
     * original loadout before the player leaves so temporary form equipment is
     * never persisted as the player's real equipment.
     */
    private static final Map<UUID, Map<Integer, ItemStack>> SAVED_LOADOUTS =
            new HashMap<>();

    /**
     * Stores the player's real Shotlock before a form-specific Shotlock is
     * applied. ItemStack.EMPTY is a valid saved value and means the player had
     * no Shotlock equipped.
     */
    private static final Map<UUID, ItemStack> SAVED_SHOTLOCKS =
            new HashMap<>();

    private static final Map<UUID, Map<Integer, ItemStack>> FORCED_LOADOUTS =
            new HashMap<>();

    /**
     * Stores the form-owned Shotlock while an override is active.
     * No entry means the current form does not override Shotlocks.
     */
    private static final Map<UUID, ItemStack> FORCED_SHOTLOCKS =
            new HashMap<>();

    private static final Map<UUID, ResourceLocation> OVERRIDE_FORMS =
            new HashMap<>();

    private static final String FORM_MAGIC_TAG = "KKReMindFormMagic";

    private FormMagicOverride() {
    }

    /**
     * Saves the player's current spell loadout.
     *
     * Will not overwrite an existing snapshot.
     */
    public static boolean saveCurrentLoadout(ServerPlayer player) {
        if (player == null) {
            return false;
        }

        PlayerData playerData = PlayerData.get(player);

        if (playerData == null) {
            return false;
        }

        if (SAVED_LOADOUTS.containsKey(player.getUUID())) {
            return false;
        }

        SAVED_LOADOUTS.put(
                player.getUUID(),
                copyLoadout(playerData.getEquippedMagics())
        );

        return true;
    }

    /**
     * Replaces the player's equipped magic with a temporary loadout.
     */
    public static void applyTemporaryLoadout(
            ServerPlayer player,
            Map<Integer, ItemStack> loadout
    ) {
        if (player == null || loadout == null) {
            return;
        }

        PlayerData playerData = PlayerData.get(player);

        if (playerData == null) {
            return;
        }

        playerData.equipAllMagics(
                copyLoadout(loadout),
                true
        );

        sync(player);
    }

    /**
     * Restores the exact spell loadout and Shotlock the player had before the
     * override.
     */
    public static boolean restoreOriginalLoadout(ServerPlayer player) {
        if (player == null) {
            return false;
        }

        PlayerData playerData = PlayerData.get(player);

        if (playerData == null) {
            return false;
        }

        UUID uuid = player.getUUID();

        Map<Integer, ItemStack> original =
                SAVED_LOADOUTS.get(uuid);

        if (original == null) {
            return false;
        }

        removeEscapedFormSpells(player);

        playerData.equipAllMagics(
                copyLoadout(original),
                true
        );

        /*
         * Only forms with a data-driven Shotlock entry create this snapshot.
         * ItemStack.EMPTY is valid here and correctly unequips the temporary
         * form Shotlock when the player originally had none equipped.
         */
        if (SAVED_SHOTLOCKS.containsKey(uuid)) {
            ItemStack originalShotlock = SAVED_SHOTLOCKS.get(uuid);

            playerData.equipShotlock(
                    originalShotlock == null
                            ? ItemStack.EMPTY
                            : originalShotlock.copy()
            );
        }

        SAVED_LOADOUTS.remove(uuid);
        SAVED_SHOTLOCKS.remove(uuid);
        FORCED_LOADOUTS.remove(uuid);
        FORCED_SHOTLOCKS.remove(uuid);
        OVERRIDE_FORMS.remove(uuid);

        sync(player);

        return true;
    }

    private static void returnDisplacedRealSpells(
            ServerPlayer player,
            Map<Integer, ItemStack> current,
            Map<Integer, ItemStack> forced
    ) {
        for (Map.Entry<Integer, ItemStack> entry : current.entrySet()) {
            int slot = entry.getKey();

            ItemStack currentStack = entry.getValue();

            if (currentStack == null || currentStack.isEmpty()) {
                continue;
            }

            ItemStack forcedStack =
                    forced.getOrDefault(slot, ItemStack.EMPTY);

            // If the slot already contains exactly what the form wants,
            // there is nothing to return.
            if (ItemStack.matches(currentStack, forcedStack)) {
                continue;
            }

            // Temporary form spells are fake/form-owned.
            // Never return those to the player.
            if (isTemporaryFormSpell(currentStack)) {
                continue;
            }

            /*
             * This is a REAL spell the player tried to equip while
             * the form override was active.
             *
             * KK already removed it from their inventory, so put it
             * back before we overwrite the equipped magic map.
             */
            ItemStack refund = currentStack.copy();

            player.getInventory().add(refund);

            // If the inventory was full and anything remains,
            // drop the remainder instead of deleting it.
            if (!refund.isEmpty()) {
                player.drop(refund, false);
            }
        }
    }

    public static void clearOverrideState(ServerPlayer player) {
        if (player == null) {
            return;
        }

        UUID uuid = player.getUUID();

        SAVED_LOADOUTS.remove(uuid);
        SAVED_SHOTLOCKS.remove(uuid);
        FORCED_LOADOUTS.remove(uuid);
        FORCED_SHOTLOCKS.remove(uuid);
        OVERRIDE_FORMS.remove(uuid);
    }

    /**
     * Returns true if this player currently has an override snapshot active.
     */
    public static boolean hasSavedLoadout(ServerPlayer player) {
        if (player == null) {
            return false;
        }

        UUID uuid = player.getUUID();

        return SAVED_LOADOUTS.containsKey(uuid)
                || SAVED_SHOTLOCKS.containsKey(uuid);
    }

    /**
     * Builds a predetermined spell loadout.
     *
     * Any remaining available magic slots are filled with empty stacks.
     */
    public static Map<Integer, ItemStack> buildLoadout(
            ServerPlayer player,
            String... spellIds
    ) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        PlayerData playerData = PlayerData.get(player);

        if (playerData == null) {
            throw new IllegalStateException(
                    "PlayerData could not be found"
            );
        }

        int maxSlots = playerData.getMaxMagics();

        if (spellIds.length > maxSlots) {
            throw new IllegalArgumentException(
                    "Form magic loadout contains "
                            + spellIds.length
                            + " spells, but player only has "
                            + maxSlots
                            + " magic slots"
            );
        }

        Map<Integer, ItemStack> loadout = new HashMap<>();

        // Ensure every available magic slot exists.
        for (int slot = 0; slot < maxSlots; slot++) {
            loadout.put(slot, ItemStack.EMPTY);
        }

        // Fill the predetermined spell slots.
        for (int slot = 0; slot < spellIds.length; slot++) {
            ResourceLocation id =
                    ResourceLocation.parse(spellIds[slot]);

            Item item = BuiltInRegistries.ITEM
                    .getOptional(id)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Unknown magic item: " + id
                            )
                    );

            if (!(item instanceof MagicSpellItem)) {
                throw new IllegalArgumentException(
                        id + " is not a MagicSpellItem"
                );
            }

            loadout.put(
                    slot,
                    makeTemporaryFormSpell(item)
            );
        }

        return loadout;
    }

    /**
     * Builds and validates a form-owned Shotlock from the data-driven ID.
     */
    private static ItemStack buildShotlock(ResourceLocation shotlockId) {
        if (shotlockId == null) {
            return ItemStack.EMPTY;
        }

        Item item = BuiltInRegistries.ITEM
                .getOptional(shotlockId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unknown Shotlock item: " + shotlockId
                        )
                );

        if (!(item instanceof ShotlockItem)) {
            throw new IllegalArgumentException(
                    shotlockId + " is not a ShotlockItem"
            );
        }

        return new ItemStack(item);
    }

    /**
     * Saves the player's original loadout and applies the temporary one.
     */
    public static boolean beginOverride(
            ServerPlayer player,
            FormMagicOverrideDefinition definition
    ) {
        if (player == null || definition == null) {
            return false;
        }

        if (hasSavedLoadout(player)) {
            return false;
        }

        PlayerData playerData = PlayerData.get(player);

        if (playerData == null) {
            return false;
        }

        String[] spellIds =
                definition.spells()
                        .stream()
                        .map(ResourceLocation::toString)
                        .toArray(String[]::new);

        Map<Integer, ItemStack> loadout;
        ItemStack forcedShotlock = null;

        try {
            loadout = buildLoadout(
                    player,
                    spellIds
            );

            if (definition.shotlock() != null) {
                forcedShotlock = buildShotlock(
                        definition.shotlock()
                );
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            return false;
        }

        if (!saveCurrentLoadout(player)) {
            return false;
        }

        UUID uuid = player.getUUID();

        OVERRIDE_FORMS.put(
                uuid,
                definition.form()
        );

        FORCED_LOADOUTS.put(
                uuid,
                copyLoadout(loadout)
        );

        /*
         * A missing "shotlock" field means this form leaves the player's
         * existing Shotlock completely alone.
         */
        if (forcedShotlock != null) {
            SAVED_SHOTLOCKS.put(
                    uuid,
                    playerData.getEquippedShotlock().copy()
            );

            FORCED_SHOTLOCKS.put(
                    uuid,
                    forcedShotlock.copy()
            );

            playerData.equipShotlock(
                    forcedShotlock.copy()
            );
        }

        /*
         * applyTemporaryLoadout performs the PlayerData sync. The Shotlock is
         * applied first so the same sync also sends the new Shotlock state.
         */
        applyTemporaryLoadout(
                player,
                loadout
        );

        return true;
    }

    public static void enforceOverride(ServerPlayer player) {
        if (player == null) {
            return;
        }

        PlayerData playerData = PlayerData.get(player);

        if (playerData == null) {
            return;
        }

        UUID uuid = player.getUUID();

        Map<Integer, ItemStack> forced =
                FORCED_LOADOUTS.get(uuid);

        ItemStack forcedShotlock =
                FORCED_SHOTLOCKS.get(uuid);

        if (forced == null && forcedShotlock == null) {
            return;
        }

        boolean magicChanged = false;

        if (forced != null) {
            Map<Integer, ItemStack> current =
                    playerData.getEquippedMagics();

            if (current.size() != forced.size()) {
                magicChanged = true;
            }

            if (!magicChanged) {
                for (Map.Entry<Integer, ItemStack> entry : forced.entrySet()) {
                    int slot = entry.getKey();

                    ItemStack wanted =
                            entry.getValue() == null
                                    ? ItemStack.EMPTY
                                    : entry.getValue();

                    ItemStack equipped =
                            current.getOrDefault(
                                    slot,
                                    ItemStack.EMPTY
                            );

                    if (!ItemStack.matches(equipped, wanted)) {
                        magicChanged = true;
                        break;
                    }
                }
            }

            if (magicChanged) {
                /*
                 * VERY IMPORTANT:
                 *
                 * Before replacing the equipped magic map, rescue any real
                 * spell that KK moved into one of these slots.
                 */
                returnDisplacedRealSpells(
                        player,
                        current,
                        forced
                );

                /*
                 * Delete temporary form spells that escaped from the equipment
                 * slots into the player's inventory.
                 */
                removeEscapedFormSpells(player);

                /*
                 * Now it is safe to restore the forced loadout.
                 */
                playerData.equipAllMagics(
                        copyLoadout(forced),
                        true
                );
            }
        }

        boolean shotlockChanged =
                forcedShotlock != null
                        && !ItemStack.matches(
                        playerData.getEquippedShotlock(),
                        forcedShotlock
                );

        if (shotlockChanged) {
            playerData.equipShotlock(
                    forcedShotlock.copy()
            );
        }

        if (magicChanged || shotlockChanged) {
            sync(player);
        }
    }

    private static ItemStack makeTemporaryFormSpell(Item item) {
        ItemStack stack = new ItemStack(item);

        CompoundTag tag = new CompoundTag();
        tag.putBoolean(FORM_MAGIC_TAG, true);

        stack.set(
                DataComponents.CUSTOM_DATA,
                CustomData.of(tag)
        );

        return stack;
    }

    private static boolean isTemporaryFormSpell(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }

        CustomData customData =
                stack.get(DataComponents.CUSTOM_DATA);

        if (customData == null) {
            return false;
        }

        return customData.copyTag()
                .getBoolean(FORM_MAGIC_TAG);
    }

    public static void removeEscapedFormSpells(ServerPlayer player) {
        if (player == null) {
            return;
        }

        for (int slot = 0;
             slot < player.getInventory().getContainerSize();
             slot++) {

            ItemStack stack =
                    player.getInventory().getItem(slot);

            if (isTemporaryFormSpell(stack)) {
                player.getInventory().setItem(
                        slot,
                        ItemStack.EMPTY
                );
            }
        }
    }

    public static ResourceLocation getOverrideForm(
            ServerPlayer player
    ) {
        if (player == null) {
            return null;
        }

        return OVERRIDE_FORMS.get(player.getUUID());
    }

    /**
     * Copies both the map and every ItemStack inside it.
     */
    private static Map<Integer, ItemStack> copyLoadout(
            Map<Integer, ItemStack> source
    ) {
        Map<Integer, ItemStack> copy =
                new HashMap<>();

        source.forEach((slot, stack) -> {
            if (stack == null) {
                copy.put(
                        slot,
                        ItemStack.EMPTY
                );
            } else {
                copy.put(
                        slot,
                        stack.copy()
                );
            }
        });

        return copy;
    }

    /**
     * Push changed PlayerData to the client.
     */
    private static void sync(ServerPlayer player) {
        PacketHandler.sendTo(
                new SCSyncPlayerData(player),
                player
        );
    }
}
