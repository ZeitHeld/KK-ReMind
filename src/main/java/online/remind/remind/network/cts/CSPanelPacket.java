package online.remind.remind.network.cts;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.Strings;
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
    private static int driveLvl;

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
        ServerPlayer player = ctx.get().getSender();

        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);


        //System.out.println(globalData.getPanelChoice());

        switch(message.choice){
            case 0:
                System.out.println("This shouldn't happen");
                break;
            case 1:
                playerData.addHearts(-1000 * globalData.getSTRPanel());
                globalData.addSTRPanel(1);
                System.out.println(globalData.getSTRPanel());
                break;
            case 2:
                playerData.addHearts(-1000 * globalData.getMAGPanel());
                globalData.addMAGPanel(1);
                break;
            case 3:
                playerData.addHearts(-1000 * globalData.getDEFPanel());
                globalData.addDEFPanel(1);
                break;
            case 4:
                playerData.addMaxAP(2);
                playerData.addHearts(-1000);
                break;
            case 5:
                //playerData.addDriveFormExperience(Strings.Form_Valor, (ServerPlayer) player, 100);
                playerData.addDriveFormExperience(Strings.Form_Valor, player, 100);
                playerData.addHearts(-10000);
                PacketHandler.sendTo(new SCSyncCapabilityPacket(ModCapabilities.getPlayer(player)), (ServerPlayer) player);
                break;
            case 6:
                //playerData.addDriveFormExperience(Strings.Form_Valor, (ServerPlayer) player, 100);
                playerData.addDriveFormExperience(Strings.Form_Wisdom, player, 100);
                playerData.addHearts(-10000);
                PacketHandler.sendTo(new SCSyncCapabilityPacket(ModCapabilities.getPlayer(player)), (ServerPlayer) player);
                break;
            case 7:
                //playerData.addDriveFormExperience(Strings.Form_Valor, (ServerPlayer) player, 100);
                playerData.addDriveFormExperience(Strings.Form_Limit, player, 100);
                playerData.addHearts(-10000);
                PacketHandler.sendTo(new SCSyncCapabilityPacket(ModCapabilities.getPlayer(player)), (ServerPlayer) player);
                break;
            case 8:
                //playerData.addDriveFormExperience(Strings.Form_Valor, (ServerPlayer) player, 100);
                playerData.addDriveFormExperience(Strings.Form_Master, player, 100);
                playerData.addHearts(-10000);
                PacketHandler.sendTo(new SCSyncCapabilityPacket(ModCapabilities.getPlayer(player)), (ServerPlayer) player);
                break;
            case 9:
                //playerData.addDriveFormExperience(Strings.Form_Valor, (ServerPlayer) player, 100);
                playerData.addDriveFormExperience(Strings.Form_Final, player, 100);
                playerData.addHearts(-10000);
                PacketHandler.sendTo(new SCSyncCapabilityPacket(ModCapabilities.getPlayer(player)), (ServerPlayer) player);
                break;
        }

        PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), player);
        PacketHandlerRM.syncGlobalToAllAround(player, globalData);
        ctx.get().setPacketHandled(true);
    }
}


