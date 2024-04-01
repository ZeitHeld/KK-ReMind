package online.magicksaddon.magicsaddonmod.network.cts;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;

public class CSSyncAllClientDataPacketRM {

	public CSSyncAllClientDataPacketRM() {
		}

	public void encode(FriendlyByteBuf buffer) {

	}

	public static CSSyncAllClientDataPacketRM decode(FriendlyByteBuf buffer) {
		CSSyncAllClientDataPacketRM msg = new CSSyncAllClientDataPacketRM();

		return msg;
	}

	public static void handle(final CSSyncAllClientDataPacketRM message, Supplier<NetworkEvent.Context> ctx) {
		Player player = ctx.get().getSender();

		IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
		PacketHandlerRM.syncGlobalToAllAround(player, globalData);

		ctx.get().setPacketHandled(true);
	}

}