package online.remind.remind.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.gui.elements.MenuBackground;
import online.kingdomkeys.kingdomkeys.client.gui.elements.MenuColourBox;
import online.kingdomkeys.kingdomkeys.client.gui.elements.buttons.MenuButton;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.lib.StringsRM;
import online.remind.remind.network.PacketHandlerRM;
import online.remind.remind.network.cts.CSPanelPacket;
import online.remind.remind.network.cts.CSPrestigePacket;

import java.awt.*;

public class PanelsMenu extends MenuBackground {

    private MenuButton backButton, strUp, magUp, defUp, apUp, giveAbility, req0, req1, req2, req3, valorUp, wisdomUp, limitUp, masterUp, finalUp, reqV, reqW, reqL, reqM, reqF;

    MenuColourBox str, mag, def, ap;

    MenuColourBox[] playerWidgets = {str, mag, def, ap};

    public PanelsMenu(String name, Color rgb) {
        super(name, rgb);
    }

    public PanelsMenu() {
        super("Panel System", new Color(154, 154, 154));
        minecraft = Minecraft.getInstance();
    }

    protected void action(String string) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(minecraft.player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(minecraft.player);

        if (string.equals("back"))
            GUIHelperRM.openAddonMenu();

        //Stat Boosts
        if (string.equals("strUp")) {
            //playerData.getStrengthStat().addModifier("Panel", 1, true, false);
            globalData.setPanelChoice("STR");
            PacketHandlerRM.sendToServer(new CSPanelPacket(1));
            GUIHelperRM.openAddonMenu();
        }
        if (string.equals("defUp")) {

            //playerData.getDefenseStat().addModifier("Panel", 1, true, false);
            globalData.setPanelChoice("DEF");
            PacketHandlerRM.sendToServer(new CSPanelPacket(3));
            GUIHelperRM.openAddonMenu();

        }
        if (string.equals("magUp")) {

            //playerData.getMagicStat().addModifier("Panel", 1, true, false);
            globalData.setPanelChoice("MAG");
            PacketHandlerRM.sendToServer(new CSPanelPacket(2));
            GUIHelperRM.openAddonMenu();
        }
        if (string.equals("apUp")) {
            //
            PacketHandlerRM.sendToServer(new CSPanelPacket(4));
            GUIHelperRM.openAddonMenu();
        }
        if (string.equals("valorUp")) {
            //
            PacketHandlerRM.sendToServer(new CSPanelPacket(5));
            GUIHelperRM.openAddonMenu();
        }
        if (string.equals("wisdomUp")) {
            //
            PacketHandlerRM.sendToServer(new CSPanelPacket(6));
            GUIHelperRM.openAddonMenu();
        }
        if (string.equals("limitUp")) {
            //
            PacketHandlerRM.sendToServer(new CSPanelPacket(7));
            GUIHelperRM.openAddonMenu();
        }
        if (string.equals("masterUp")) {
            //
            PacketHandlerRM.sendToServer(new CSPanelPacket(8));
            GUIHelperRM.openAddonMenu();
        }
        if (string.equals("finalUp")) {
            //
            PacketHandlerRM.sendToServer(new CSPanelPacket(9));
            GUIHelperRM.openAddonMenu();
        }
    }

    @Override
    public void init() {

        Player player;
        final IPlayerCapabilities playerData = ModCapabilities.getPlayer(minecraft.player);
        IGlobalCapabilitiesRM addedData = ModCapabilitiesRM.getGlobal(minecraft.player);

        super.init();
        this.renderables.clear();

        float topBarHeight = (float) height * 0.17F;
        int button_statsY = (int) topBarHeight + 5;
        int button_stats_playerY = button_statsY;

        float buttonPosX = (float) width * 0.03F;
        float subButtonPosX = buttonPosX + 10;

        float buttonWidth = ((float) width * 0.1744F) - 20;
        float subButtonWidth = buttonWidth - 10;


        float dataWidth = ((float) width * 0.1744F) - 10;

        int col1X = (int) (subButtonPosX + buttonWidth + 10), col2X = (int) (col1X + dataWidth * 2) + 5;

        int i = 0;

        if (playerData.getHearts() >= 1000 * addedData.getSTRPanel()) {
            addRenderableWidget(strUp = new MenuButton((int) buttonPosX, button_statsY, (int) buttonWidth, ("STR +"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("strUp");
            }));
        } else {
            addRenderableWidget(req0 = new MenuButton((int) buttonPosX, button_statsY, (int) buttonWidth, "Hearts Needed: " + ((1000 * addedData.getSTRPanel()) - playerData.getHearts()), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("req");
            }));
        }
        if (playerData.getHearts() >= 1000 * addedData.getMAGPanel()) {
            addRenderableWidget(magUp = new MenuButton((int) buttonPosX, button_statsY + 20, (int) buttonWidth, ("MAG +"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("magUp");
            }));
        } else {
            addRenderableWidget(req1 = new MenuButton((int) buttonPosX, button_statsY + 20, (int) buttonWidth, "Hearts Needed: " + ((1000 * addedData.getMAGPanel()) - playerData.getHearts()), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("req");
            }));
        }
        if (playerData.getHearts() >= 1000 * addedData.getMAGPanel()) {
                addRenderableWidget(defUp = new MenuButton((int) buttonPosX, button_statsY + 40, (int) buttonWidth, ("DEF +"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                    action("defUp");
                }));
        } else {
            addRenderableWidget(req2 = new MenuButton((int) buttonPosX, button_statsY + 40, (int) buttonWidth, "Hearts Needed: " + ((1000 * addedData.getDEFPanel()) - playerData.getHearts()), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("req");
            }));
        }
        if (playerData.getHearts() >= 1000) {
            addRenderableWidget(apUp = new MenuButton((int) buttonPosX, button_statsY + 60, (int) buttonWidth, ("AP +"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("apUp");
            }));
        } else {
            addRenderableWidget(req3 = new MenuButton((int) buttonPosX, button_statsY + 60, (int) buttonWidth, "Hearts Needed: " + (1000 - playerData.getHearts()), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("req");
            }));
        }
        if (playerData.getDriveFormLevel(Strings.Form_Valor) < 7 && playerData.getHearts() >= 10000) {
            addRenderableWidget(valorUp = new MenuButton((int) buttonPosX, button_statsY + 80, (int) buttonWidth, ("Valor Form EXP Up"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("valorUp");
            }));
        }
        if (playerData.getDriveFormLevel(Strings.Form_Wisdom) < 7 && playerData.getHearts() >= 10000) {
            addRenderableWidget(wisdomUp = new MenuButton((int) buttonPosX, button_statsY + 100, (int) buttonWidth, ("Wisdom Form EXP Up"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("wisdomUp");
            }));
        }
        if (playerData.getDriveFormLevel(Strings.Form_Limit) < 7 && playerData.getHearts() >= 10000) {
            addRenderableWidget(limitUp = new MenuButton((int) buttonPosX, button_statsY + 120, (int) buttonWidth, ("Limit Form EXP Up"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("limitUp");
            }));
        }
        if (playerData.getDriveFormLevel(Strings.Form_Master) < 7 && playerData.getHearts() >= 10000) {
            addRenderableWidget(masterUp = new MenuButton((int) buttonPosX, button_statsY + 140, (int) buttonWidth, ("Master Form EXP Up"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("masterUp");
            }));
        }
        if (playerData.getDriveFormLevel(Strings.Form_Final) < 7 && playerData.getHearts() >= 10000) {
            addRenderableWidget(finalUp = new MenuButton((int) buttonPosX, button_statsY + 160, (int) buttonWidth, ("Final Form EXP Up"), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("finalUp");
            }));
        }
        addRenderableWidget(backButton = new MenuButton((int) buttonPosX, button_statsY + 180, (int) buttonWidth, (Strings.Gui_Menu_Back), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action("back");
        }));

        //Stats
        int c = 0;
        int d = 0;
        int spacer = 14;

        // Stats Column
        //addRenderableWidget(gainedHP = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth*2, Utils.translateToLocal("Gained Max HP: "), "" + addedData.getPrestigeLvl() * 2, 0x3ECE44));
        //addRenderableWidget(gainedMP = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth*2, Utils.translateToLocal("Gained Max MP: "), "" + addedData.getPrestigeLvl() * 2, 0x3ECE44));


        addRenderableWidget(str = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth, Utils.translateToLocal("Panel STR: "), "" + addedData.getSTRPanel(), 0xaa190f));
        addRenderableWidget(mag = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth, Utils.translateToLocal("Panel MAG: "), "" + addedData.getMAGPanel(), 0xaa190f));
        addRenderableWidget(def = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth, Utils.translateToLocal("Panel DEF: "), "" + addedData.getDEFPanel(), 0xaa190f));
        addRenderableWidget(ap = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth, Utils.translateToLocal("AP: "), "" + playerData.getMaxAPStat().getStat(), 0xaa190f));
    }
}