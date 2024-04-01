package online.magicksaddon.magicsaddonmod.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.DistExecutor;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.network.stc.SCSyncGlobalCapabilityToAllPacketRM;

public class ClientUtilsRM {

	 public static DistExecutor.SafeRunnable syncCapability(SCSyncGlobalCapabilityToAllPacketRM message) {
	        return new DistExecutor.SafeRunnable() {
	            @Override
	            public void run() {
	                IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(Minecraft.getInstance().player);

	                globalData.setBerserkTicks(message.berserkTicks, message.berserkLvl);
	                
	            }
	        };
	    }

}
