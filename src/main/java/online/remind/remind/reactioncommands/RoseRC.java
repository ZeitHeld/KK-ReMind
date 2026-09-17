package online.remind.remind.reactioncommands;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.client.sound.ModSounds;
import online.kingdomkeys.kingdomkeys.data.PlayerData;
import online.kingdomkeys.kingdomkeys.data.WorldData;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.driveform.ModDriveForms;
import online.kingdomkeys.kingdomkeys.entity.ModEntities;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncWorldData;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.remind.remind.capabilities.GlobalDataRM;
import online.remind.remind.capabilities.ModDataRM;
import online.remind.remind.item.ModItemsRM;

import java.util.UUID;

public class RoseRC extends ReactionCommand {

    private static final String ROSE_SHADOW_1 = "RoseShadow1";
    private static final String ROSE_SHADOW_2 = "RoseShadow2";

    public RoseRC(ResourceLocation registryName, boolean constantCheck, int duration, int color) {
        super(registryName, constantCheck, -1, 0x002E68);
    }

    @Override
    public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
        PlayerData playerData = PlayerData.get(player);
        GlobalDataRM globalData = ModDataRM.getGlobal(player);

        if (playerData == null || globalData == null) {
            return;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }

        // Do not allow another pair while either previous summon is active.
        if (hasActiveRoseSummons(serverPlayer)) {
            return;
        }

        playerData.remFocus(10);
        playerData.remDP(100);
        globalData.setRCCooldownTicks(200);

        LivingEntity shadow1 = summonShadow(serverPlayer, level, 1.5D, 0.5D);
        LivingEntity shadow2 = summonShadow(serverPlayer, level, -1.5D, 0.5D);

        CompoundTag persistentData = serverPlayer.getPersistentData();

        if (shadow1 != null) {
            persistentData.putUUID(ROSE_SHADOW_1, shadow1.getUUID());
        }

        if (shadow2 != null) {
            persistentData.putUUID(ROSE_SHADOW_2, shadow2.getUUID());
        }

        level.playSound(
                null,
                player.blockPosition(),
                ModSounds.heartlessSpawn.get(),
                SoundSource.PLAYERS,
                1.0F,
                0.8F
        );

        level.sendParticles(
                ParticleTypes.PORTAL,
                player.getX(),
                player.getY() + 1.0D,
                player.getZ(),
                40,
                1.0D,
                1.0D,
                1.0D,
                0.1D
        );
    }

    private LivingEntity summonShadow(
            ServerPlayer player,
            ServerLevel level,
            double offsetX,
            double offsetZ
    ) {

        LivingEntity shadow = ModEntities.TYPE_SHADOW.get().create(level);

        if (shadow == null) {
            return null;
        }

        shadow.moveTo(
                player.getX() + offsetX,
                player.getY(),
                player.getZ() + offsetZ,
                player.getYRot(),
                0.0F
        );

        shadow.getPersistentData().putBoolean("RoseSummon", true);
        shadow.getPersistentData().putUUID("RoseSummoner", player.getUUID());

        level.addFreshEntity(shadow);

        addSummonToParty(player, shadow);

        level.sendParticles(
                ParticleTypes.SMOKE,
                shadow.getX(),
                shadow.getY() + 0.5D,
                shadow.getZ(),
                15,
                0.25D,
                0.4D,
                0.25D,
                0.02D
        );

        return shadow;
    }

    private static boolean hasActiveRoseSummons(ServerPlayer player) {
        if (player.getServer() == null) {
            return false;
        }

        WorldData worldData = WorldData.get(player.getServer());

        if (worldData == null) {
            return false;
        }

        Party party = worldData.getPartyFromMember(player.getUUID());

        if (party == null) {
            return false;
        }

        CompoundTag persistentData = player.getPersistentData();

        boolean shadow1Active = isRoseSummonActive(
                persistentData,
                party,
                ROSE_SHADOW_1
        );

        boolean shadow2Active = isRoseSummonActive(
                persistentData,
                party,
                ROSE_SHADOW_2
        );

        return shadow1Active || shadow2Active;
    }

    private static boolean isRoseSummonActive(
            CompoundTag persistentData,
            Party party,
            String key
    ) {

        if (!persistentData.contains(key)) {
            return false;
        }

        UUID uuid = persistentData.getUUID(key);

        // Still a member of the KK party = still an active summon.
        if (party.getMember(uuid) != null) {
            return true;
        }

        // KK no longer considers this entity a party member.
        // Forget the old UUID so user can summon again.
        persistentData.remove(key);

        return false;
    }

    public static void addSummonToParty(ServerPlayer player, LivingEntity summon) {
        if (player == null || summon == null || player.getServer() == null) {
            return;
        }

        WorldData worldData = WorldData.get(player.getServer());

        if (worldData == null) {
            return;
        }

        Party party = worldData.getPartyFromMember(player.getUUID());

        // Player isn't currently in a party, so create one.
        if (party == null) {
            String partyName = player.getName().getString() + "'s Party";


            party = new Party(
                    partyName,
                    player.getUUID(),
                    player.getGameProfile().getName(),
                    true,
                    (byte) 3
            );

            worldData.addParty(party);
        }

        if (party.getMember(summon.getUUID()) != null) {
            return;
        }

        worldData.addPartyMember(party, summon);

        PacketHandler.sendToAll(
                new SCSyncWorldData(player.getServer())
        );
    }

    @Override
    public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
        PlayerData playerData = PlayerData.get(player);
        GlobalDataRM globalData = ModDataRM.getGlobal(player);

        if (playerData == null || globalData == null) {
            return false;
        }

        if (playerData.getFocus() < 10) {
            return false;
        }

        if ( playerData.getDP() < 100){
            return false;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            if (hasActiveRoseSummons(serverPlayer)) {
                return false;
            }
        }

        if (playerData.getEquippedKeychain(DriveForm.NONE) == null) {
            return false;
        }

        if (playerData.getAlignment() != Utils.OrgMember.NONE) {
            return false;
        }

        if (!playerData.isFormActive(ModDriveForms.NONE)) {
            return false;
        }

        if (playerData.getEquippedKeychain(DriveForm.NONE).getItem()
                != ModItemsRM.roseKeybladeChain.get()) {
            return false;
        }

        if (globalData.getRCCooldownTicks() != 0) {
            return false;
        }

        return true;
    }
}