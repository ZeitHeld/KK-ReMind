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

import java.sql.SQLOutput;
import java.util.function.Supplier;

public class CSPanelPacket {
    // 0 = none (default), 1 = str, 2 = mag, 3 = def...
    private int choice;

    public CSPanelPacket(){}

    public CSPanelPacket(int choice){
        this.choice = choice;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.choice);
    }

    public static CSPanelPacket decode(FriendlyByteBuf buffer) {
        CSPanelPacket msg = new CSPanelPacket();
        msg.choice = buffer.readInt();
        return msg;
    }

    public static void handle(final CSPanelPacket message, Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();

        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);

        playerData.addHearts(-1000);
        System.out.println(globalData.getPanelChoice());

        if (message.choice > 0) {
            switch(message.choice){
                case 0:
                    System.out.println("This shouldn't happen");
                    break;
                case 1:
                    globalData.addSTRPanel(1);
                    System.out.println(globalData.getSTRPanel());
                    break;
                case 2:
                    globalData.addMAGPanel(1);
                    break;
                case 3:
                    globalData.addDEFPanel(1);
                    break;

            }

        }

        PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
        PacketHandlerRM.syncGlobalToAllAround(player, globalData);
        ctx.get().setPacketHandled(true);
    }
}


