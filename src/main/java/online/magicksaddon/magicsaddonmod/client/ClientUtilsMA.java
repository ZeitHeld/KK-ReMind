package online.magicksaddon.magicsaddonmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.DistExecutor;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncGlobalCapabilityPacket;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.network.stc.SCSyncGlobalCapabilityToAllPacket;

public class ClientUtilsMA {

	 public static DistExecutor.SafeRunnable syncCapability(SCSyncGlobalCapabilityToAllPacket message) {
	        return new DistExecutor.SafeRunnable() {
	            @Override
	            public void run() {
	                IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(Minecraft.getInstance().player);

	                globalData.setBerserkTicks(message.berserkLvl, message.berserkTicks);
	                
	            }
	        };
	    }

}
