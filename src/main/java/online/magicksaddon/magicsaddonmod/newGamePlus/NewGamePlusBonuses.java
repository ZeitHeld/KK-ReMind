package online.magicksaddon.magicsaddonmod.newGamePlus;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;

public class NewGamePlusBonuses {

    public void NGPlusBonus() {
        Player player = Minecraft.getInstance().player;
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);
        /*
        switch(globalData.getPrestigeLvl()){
            case 1:
                playerData.getStrengthStat().addModifier("NG+ Bonus",1, true);
                playerData.getMagicStat().addModifier("NG+ Bonus",1, true);
                playerData.getDefenseStat().addModifier("NG+ Bonus",1, true);
                playerData.addAbility(Strings.luckyLucky, true);
                PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
                System.out.println(globalData.getPrestigeLvl());
                PacketHandlerX.syncGlobalToAllAround(player, globalData);
            case 2:
                playerData.getStrengthStat().addModifier("NG+ Bonus",2, true);
                playerData.getMagicStat().addModifier("NG+ Bonus",2, true);
                playerData.getDefenseStat().addModifier("NG+ Bonus",2, true);
                playerData.addAbility(Strings.luckyLucky, true);
                playerData.addAbility(Strings.experienceBoost, true);
                PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
                System.out.println(globalData.getPrestigeLvl());
                PacketHandlerX.syncGlobalToAllAround(player, globalData);
            case 3:
                playerData.getStrengthStat().addModifier("NG+ Bonus",3, true);
                playerData.getMagicStat().addModifier("NG+ Bonus",3, true);
                playerData.getDefenseStat().addModifier("NG+ Bonus",3, true);
                playerData.addAbility(Strings.luckyLucky, true);
                playerData.addAbility(Strings.experienceBoost, true);

                PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
                System.out.println(globalData.getPrestigeLvl());
                PacketHandlerX.syncGlobalToAllAround(player, globalData);
        }
        */
        

    }





}
