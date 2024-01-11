package online.magicksaddon.magicsaddonmod.network.cts;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;

public class CSSyncAllClientDataXPacket {

	public CSSyncAllClientDataXPacket() {
		}

	public void encode(FriendlyByteBuf buffer) {

	}

	public static CSSyncAllClientDataXPacket decode(FriendlyByteBuf buffer) {
		CSSyncAllClientDataXPacket msg = new CSSyncAllClientDataXPacket();

		return msg;
	}

	public static void handle(final CSSyncAllClientDataXPacket message, Supplier<NetworkEvent.Context> ctx) {
		Player player = ctx.get().getSender();

		IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);
		PacketHandlerX.syncGlobalToAllAround(player, globalData);

		ctx.get().setPacketHandled(true);
	}

}