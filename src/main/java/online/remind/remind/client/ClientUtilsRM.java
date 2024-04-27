package online.remind.remind.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.DistExecutor;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.network.stc.SCSyncGlobalCapabilityToAllPacketRM;

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
