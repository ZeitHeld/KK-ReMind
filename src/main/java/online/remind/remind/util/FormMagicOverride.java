package online.remind.remind.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
import online.kingdomkeys.kingdomkeys.data.PlayerData;
import online.kingdomkeys.kingdomkeys.item.MagicSpellItem;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncPlayerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class FormMagicOverride {

    /**
     * Stores the player's original magic loadout while an override is active.
     *
     * Currently memory-only.
     * Logout/death/restart safety can be handled later.
     */
    private static final Map<UUID, Map<Integer, ItemStack>> SAVED_LOADOUTS =
            new HashMap<>();

    private static final Map<UUID, Map<Integer, ItemStack>> FORCED_LOADOUTS =
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
     * Restores the exact spell loadout the player had before the override.
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

        SAVED_LOADOUTS.remove(uuid);
        FORCED_LOADOUTS.remove(uuid);

        // THIS is the missing one
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
        FORCED_LOADOUTS.remove(uuid);
        OVERRIDE_FORMS.remove(uuid);
    }

    /**
     * Returns true if this player currently has an override active.
     */
    public static boolean hasSavedLoadout(ServerPlayer player) {
        return player != null
                && SAVED_LOADOUTS.containsKey(player.getUUID());
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

        String[] spellIds =
                definition.spells()
                        .stream()
                        .map(ResourceLocation::toString)
                        .toArray(String[]::new);

        Map<Integer, ItemStack> loadout;

        try {
            loadout = buildLoadout(
                    player,
                    spellIds
            );
        } catch (IllegalArgumentException | IllegalStateException e) {
            return false;
        }

        if (!saveCurrentLoadout(player)) {
            return false;
        }

        OVERRIDE_FORMS.put(
                player.getUUID(),
                definition.form()
        );

        FORCED_LOADOUTS.put(
                player.getUUID(),
                copyLoadout(loadout)
        );

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

        Map<Integer, ItemStack> forced =
                FORCED_LOADOUTS.get(player.getUUID());

        if (forced == null) {
            return;
        }

        Map<Integer, ItemStack> current =
                playerData.getEquippedMagics();

        boolean changed = false;

        if (current.size() != forced.size()) {
            changed = true;
        }

        if (!changed) {
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
                    changed = true;
                    break;
                }
            }
        }

        if (!changed) {
            return;
        }

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

        sync(player);
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