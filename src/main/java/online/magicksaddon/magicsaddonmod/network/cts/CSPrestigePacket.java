package online.magicksaddon.magicsaddonmod.network.cts;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.SoAState;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;

public class CSPrestigePacket {



    public CSPrestigePacket(){}

    public void encode(FriendlyByteBuf buffer) {

    }

    public static CSPrestigePacket decode(FriendlyByteBuf buffer) {
        CSPrestigePacket msg = new CSPrestigePacket();

        return msg;
    }

    public static void handle(final CSPrestigePacket message, Supplier<NetworkEvent.Context> ctx) {
        Player player = ctx.get().getSender();

        //IPlayerCapabilitiesX playerDataX = ModCapabilitiesX.getPlayer(player);
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);
        Utils.restartLevel(playerData, player);
        // Counter Stuff Here
        // Choice Reset Here
        playerData.setSoAState(SoAState.NONE);
        globalData.addPrestigeLvl(+1);
        playerData.getStrengthStat().addModifier("NG+ Bonus",1, true);
        playerData.getMagicStat().addModifier("NG+ Bonus",1, true);
        playerData.getDefenseStat().addModifier("NG+ Bonus",1, true);
        playerData.addAbility(Strings.luckyLucky, true);
        PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
        ctx.get().setPacketHandled(true);
    }
}
