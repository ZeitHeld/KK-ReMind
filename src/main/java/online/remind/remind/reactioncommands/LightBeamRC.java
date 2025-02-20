package online.remind.remind.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.client.sound.ModSoundsRM;
import online.remind.remind.entity.reactioncommand.LightBeamEntity;
import online.remind.remind.lib.StringsRM;
import online.remind.remind.network.PacketHandlerRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class LightBeamRC extends ReactionCommand {


    public LightBeamRC(ResourceLocation registryName, boolean constantCheck) {
        super(registryName, constantCheck);
    }


    @Override
    public void onUse(Player player, LivingEntity target, LivingEntity lockedOnEntity) {
        if (conditionsToAppear(player, player)) {
            IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
            IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
            float dmgmult = ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsRM.lightBoost) * 0.2F;


            playerData.setFP(playerData.getFP() - 40);

            globalData.setRCCooldownTicks(40);
            player.level().playSound(null, player.blockPosition(), ModSoundsRM.LIGHT_BEAM.get(), SoundSource.PLAYERS, 1F, 1F);
            for (int i = -90; i <= 225; i += 45) {
                ThrowableProjectile LightBeam = new LightBeamEntity(player.level(), player, dmgmult, false);
                LightBeam.setOwner(player);
                LightBeam.setPos(player.getX(),player.getY(),player.getZ());
                player.level().addFreshEntity(LightBeam);
                LightBeam.shootFromRotation(player, 0, player.getYRot() + i, 0, 1.25F, 0);
            }

            PacketHandlerRM.syncGlobalToAllAround(player, globalData);
            PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
        }
    }


    @Override
    public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
        if(playerData != null){
            if(playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.lightForm) && globalData.getRCCooldownTicks() == 0){
                return true;
            }
        }
        return false;
    }
}