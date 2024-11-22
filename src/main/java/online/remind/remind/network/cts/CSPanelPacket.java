package online.remind.remind.network.cts;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.client.gui.PanelsMenu;
import online.remind.remind.network.PacketHandlerRM;

import java.util.function.Supplier;

public class CSPanelPacket {

    public CSPanelPacket(){}

    public void encode(FriendlyByteBuf buffer) {

    }

    public static CSPanelPacket decode(FriendlyByteBuf buffer) {
        CSPanelPacket msg = new CSPanelPacket();

        return msg;
    }

    public static void handle(final CSPanelPacket message, Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();

        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);

        playerData.addHearts(-1000);

        if (globalData.getPanelChoice() != null) {

            // Boost Area
            if (globalData.getPanelChoice().equals("STR")) {
                globalData.addSTRPanel(1);
                System.out.println(globalData.getSTRPanel());
            }
            if (globalData.getPanelChoice().equals("MAG")) {
                globalData.addMAGPanel(1);
            }
            if (globalData.getPanelChoice().equals("DEF")) {
                globalData.addDEFPanel(1);
            }
        }

        PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
        PacketHandlerRM.syncGlobalToAllAround(player, globalData);
        ctx.get().setPacketHandled(true);
    }
}


