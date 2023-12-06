package online.magicksaddon.magicsaddonmod.network.cts;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import online.magicksaddon.magicsaddonmod.capabilities.IPlayerCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;
import online.magicksaddon.magicsaddonmod.network.stc.SCSyncCapabilityPacketX;

public class CSSyncAllClientDataPacketX {

    public CSSyncAllClientDataPacketX() {
    }

    public void encode(FriendlyByteBuf buffer) {

    }

    public static CSSyncAllClientDataPacketX decode(FriendlyByteBuf buffer) {
        CSSyncAllClientDataPacketX msg = new CSSyncAllClientDataPacketX();

        return msg;
    }

    public static void handle(final CSSyncAllClientDataPacketX message, Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();

        IPlayerCapabilitiesX playerData = ModCapabilitiesX.getPlayer(player);
        PacketHandlerX.sendTo(new SCSyncCapabilityPacketX(playerData), (ServerPlayer) player);

        ctx.get().setPacketHandled(true);
    }
}
