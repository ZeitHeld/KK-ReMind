package online.magicksaddon.magicsaddonmod.driveform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.mixin.DriveFormMixin;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;

@Mod.EventBusSubscriber(modid = MagicksAddonMod.MODID)
public class DriveFormRage extends DriveForm {
	ResourceLocation skinRL2;

    public DriveFormRage(String registeryName, int order, ResourceLocation skinRL, boolean hasKeychain, boolean baseGrowthAbilities) {
        super(registeryName, order, hasKeychain, baseGrowthAbilities);
        ((DriveFormMixin) this).setColor(new float[] { 0.5F, 0F, 0F });
        skinRL2 = skinRL;        
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
    	return skinRL2;
    }
    
    @Override
    public void initDrive(Player player) {
    	IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);
    	globalData.setRiskchargeCount(0);
    	PacketHandlerX.syncGlobalToAllAround(player, globalData);
    	super.initDrive(player);
    }
   
}
