package online.magicksaddon.magicsaddonmod.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.DistExecutor;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.network.stc.SCSyncGlobalCapabilityToAllPacketX;

public class ClientUtilsMA {

	 public static DistExecutor.SafeRunnable syncCapability(SCSyncGlobalCapabilityToAllPacketX message) {
	        return new DistExecutor.SafeRunnable() {
	            @Override
	            public void run() {
	                IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(Minecraft.getInstance().player);

	                globalData.setBerserkTicks(message.berserkLvl, message.berserkTicks);
	                
	            }
	        };
	    }

}
