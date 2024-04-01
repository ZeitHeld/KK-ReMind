package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.config.ModConfigs;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;
import online.magicksaddon.magicsaddonmod.mixin.DriveFormMixin;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;

@Mod.EventBusSubscriber(modid = MagicksAddonMod.MODID)
public class DriveFormDark extends DriveForm {
    ResourceLocation skinRL2;


    public DriveFormDark(String registeryName, int order, ResourceLocation skinRL, boolean hasKeychain, boolean baseGrowthAbilities) {
        super(registeryName, order, hasKeychain, baseGrowthAbilities);
        ((DriveFormMixin) this).setColor(new float[] {0.25F,0F,0.25F});
        skinRL2 = skinRL;
    }

    @SubscribeEvent
    public static void getDarkModeXP(LivingDeathEvent event) {
        if (!event.getEntity().level().isClientSide && event.getEntity() instanceof Monster) {
            if (event.getSource().getEntity() instanceof Player) {
                Player player = (Player) event.getSource().getEntity();
                IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
                IGlobalCapabilitiesRM formData = ModCapabilitiesRM.getGlobal(player);

                if (playerData != null && playerData.getActiveDriveForm().equals(MagicksAddonMod.MODID+":"+ StringsRM.darkMode)) {
                    double mult = Double.parseDouble(ModConfigs.driveFormXPMultiplier.get(1).split(",")[1]);
                    playerData.setDriveFormExp(player, playerData.getActiveDriveForm(), (int) (playerData.getDriveFormExp(playerData.getActiveDriveForm()) + (1 * mult)));

                    PacketHandlerRM.syncGlobalToAllAround(player, formData);
                    PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
                }
            }
        }
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
    	return skinRL2;
    }

}
