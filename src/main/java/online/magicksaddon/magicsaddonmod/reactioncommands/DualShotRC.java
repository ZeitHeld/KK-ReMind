package online.magicksaddon.magicsaddonmod.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.entity.reactioncommand.DualShotEntity;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class DualShotRC extends ReactionCommand {
    public DualShotRC(ResourceLocation registryName, boolean constantCheck) {
        super(registryName, constantCheck);
    }

    @Override
    public void onUse(Player player, LivingEntity livingEntity, LivingEntity lockOnEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
        float dmgmult = (ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsRM.darknessBoost) + ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsRM.lightBoost))  * 0.2F;
        globalData.setRCCooldownTicks(40);
        playerData.setFP(playerData.getFP() + 40);

        // Fire Dual Shot
        player.swing(InteractionHand.MAIN_HAND);


        ThrowableProjectile dualShot = new DualShotEntity(player.level(), player,dmgmult,lockOnEntity);
        player.level().addFreshEntity(dualShot);
        dualShot.shootFromRotation(player, player.getXRot(),player.getYRot() - 0.5F,0,1.25F, 0);

        // Sync Packet
        PacketHandlerRM.syncGlobalToAllAround(player, globalData);


    }

    @Override
    public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
        if (playerData != null) {
            if (playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.twilight) && globalData.getRCCooldownTicks() == 0) {
                return true;
            }
        }
        return false;
    }
}
