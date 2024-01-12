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
import online.magicksaddon.magicsaddonmod.lib.StringsX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;

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

        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);

        // Storing Old Choice For Bonus
        String oldChoice = String.valueOf(playerData.getChosen());
        System.out.println(oldChoice);
        Utils.restartLevel(playerData, player);
        playerData.setSoAState(SoAState.NONE);
        globalData.addPrestigeLvl(+1);

        if (oldChoice == "WARRIOR"){
            //System.out.println("Warrior NG+ Working");
            globalData.setNGPWarriorCount(+1);
            /*
            playerData.addAbility(Strings.luckyLucky, true);
            playerData.addAbility(Strings.formBoost, true);
            playerData.addAbility(Strings.formBoost, true);
            playerData.addAbility(Strings.criticalBoost, true);
            playerData.addAbility(Strings.criticalBoost, true);
            playerData.addAbility(StringsX.adrenaline, true);
            */
            globalData.setSTRBonus(+2);
            System.out.println("Strength Bonus: " + globalData.getSTRBonus());
            PacketHandlerX.syncGlobalToAllAround(player, globalData);


        }
        if (oldChoice == "MYSTIC"){
            //System.out.println("Mystic NG+ Working");
            globalData.setNGPMysticCount(+1);
            /*
            playerData.addAbility(Strings.mpHastega, true);
            playerData.addAbility(Strings.mpThrift, true);
            playerData.addAbility(Strings.fireBoost, true);
            playerData.addAbility(Strings.blizzardBoost, true);
            playerData.addAbility(Strings.thunderBoost, true);
            playerData.addAbility(StringsX.critical_surge, true);
             */
            globalData.setMAGBonus(+2);
            System.out.println("Magic Bonus: " + globalData.getMAGBonus());
            PacketHandlerX.syncGlobalToAllAround(player, globalData);


        }
        if (oldChoice == "GUARDIAN"){
            //System.out.println("Guardian NG+ Working");
            globalData.setNGPGuardianCount(+1);
            /*
            playerData.addAbility(Strings.luckyLucky, true);
            playerData.addAbility(Strings.jackpot, true);
            playerData.addAbility(Strings.jackpot, true);
            playerData.addAbility(Strings.damageControl, true);
            playerData.addAbility(Strings.damageControl, true);
             */
            globalData.setDEFBonus(+2);
            System.out.println("Defense Bonus: " + globalData.getDEFBonus());
            PacketHandlerX.syncGlobalToAllAround(player, globalData);



        }
        System.out.println("NG+ Counts: " + globalData.getNGPWarriorCount() + ", " + globalData.getNGPMysticCount() + ", " + globalData.getNGPGuardianCount());

        System.out.println("Bonus Stats: " + globalData.getSTRBonus() + ", " + globalData.getMAGBonus() + ", " + globalData.getDEFBonus());

        playerData.getStrengthStat().removeModifier("NG+ Bonus");
        playerData.getMagicStat().removeModifier("NG+ Bonus");
        playerData.getDefenseStat().removeModifier("NG+ Bonus");
        playerData.getStrengthStat().removeModifier("sacrifice");
        playerData.getMagicStat().removeModifier("sacrifice");
        playerData.getDefenseStat().removeModifier("sacrifice");


        playerData.getStrengthStat().addModifier("NG+ Bonus", globalData.getSTRBonus(), true);
        playerData.getMagicStat().addModifier("NG+ Bonus",globalData.getMAGBonus(), true);
        playerData.getDefenseStat().addModifier("NG+ Bonus",globalData.getDEFBonus(), true);
        playerData.addMaxHP(2 * globalData.getPrestigeLvl());
        playerData.addMaxMP(2 * globalData.getPrestigeLvl());


        PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
        System.out.println("Prestige Level: " + globalData.getPrestigeLvl());
        PacketHandlerX.syncGlobalToAllAround(player, globalData);
        ctx.get().setPacketHandled(true);
    }
}
