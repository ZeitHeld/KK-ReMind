package online.magicksaddon.magicsaddonmod.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.lib.StringsX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;

@Mod.EventBusSubscriber(modid = MagicksAddonMod.MODID)
public class RiskchargeReaction extends ReactionCommand {
    public RiskchargeReaction(ResourceLocation registryName, boolean constantCheck) {
        super(registryName, constantCheck);
    }



    @Override
    public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
        if(conditionsToAppear(player,player)){
            IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
            IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);
            // Riskcharge noise goes below
            //player.level().playSound(null, player.position().x(),player.position().y(),player.position().z(), ModSounds.drive.get(), SoundSource.PLAYERS, 1F, 1F);

            player.setHealth(player.getHealth()/2);
            playerData.getStrengthStat().addModifier("Riskcharge", 5, true);
            globalData.setRiskchargeCount(globalData.getRiskchargeCount()+1);
            PacketHandlerX.syncGlobalToAllAround(player, globalData);
            PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
        }
    }

    @Override
    public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);
        if(playerData != null){
        	System.out.println(player.level().isClientSide+" "+ globalData.getRiskchargeCount());
            if(playerData.getActiveDriveForm().equals("magicksaddon:form_rage")){
                if(globalData.getRiskchargeCount() < 3){
                    return true;
                }
            }
        }
        return false;
    }
}
