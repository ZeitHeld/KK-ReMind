package online.magicksaddon.magicsaddonmod.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.entity.HeartEntity;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class RiskchargeReaction extends ReactionCommand {


    public RiskchargeReaction(ResourceLocation registryName, boolean constantCheck) {
        super(registryName, constantCheck);
    }





    @Override
    public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
        if(conditionsToAppear(player,player)){
            IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
            IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
            HeartEntity heart = new HeartEntity(player.level());

            player.level().playSound(null, player.position().x(),player.position().y(),player.position().z(), ModSoundsRM.RISKCHARGE.get(), SoundSource.PLAYERS, 1F, 1F);
            player.level().addFreshEntity(heart);
            heart.setPos(player.getX(),player.getY() + 1,player.getZ());
            player.setHealth(player.getHealth()/2);
            playerData.getStrengthStat().addModifier("Riskcharge", 5, true);
            playerData.addFP(50);
            globalData.setRiskchargeCount(globalData.getRiskchargeCount()+1);
            PacketHandlerRM.syncGlobalToAllAround(player, globalData);
            PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
        }
    }

    @Override
    public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
        if(playerData != null){
            if(playerData.getActiveDriveForm().equals("magicksaddon:form_rage")){
                if(globalData.getRiskchargeCount() < 3){
                    return true;
                }
            }
        }
        return false;
    }
}
