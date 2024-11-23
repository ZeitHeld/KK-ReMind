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

    private MenuButton backButton, strUp, magUp, defUp, apUp, giveAbility;

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
                    System.out.println(globalData.getPanelChoice());
                    PacketHandlerRM.sendToServer(new CSPanelPacket(1));
            }
            if (string.equals("defUp")) {

                    //playerData.getDefenseStat().addModifier("Panel", 1, true, false);
                    globalData.setPanelChoice("DEF");
                    PacketHandlerRM.sendToServer(new CSPanelPacket(2));

            }
            if (string.equals("magUp")) {

                    //playerData.getMagicStat().addModifier("Panel", 1, true, false);
                    globalData.setPanelChoice("MAG");
                    PacketHandlerRM.sendToServer(new CSPanelPacket(3));
            }
            if (string.equals("apUp")) {
                //
                playerData.addHearts(-1000);
                playerData.addMaxAP(2);
                PacketHandlerRM.sendToServer(new CSPanelPacket(4));
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

        float buttonWidth = ((float) width * 0.1744F)- 20;
        float subButtonWidth = buttonWidth - 10;


        float dataWidth = ((float) width * 0.1744F)-10;

        int col1X = (int) (subButtonPosX + buttonWidth + 10), col2X=(int) (col1X + dataWidth * 2)+5 ;

        int i = 0;


        addRenderableWidget(strUp = new MenuButton((int) buttonPosX, button_statsY, (int) buttonWidth, ("STR +"), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action("strUp");
        }));
        addRenderableWidget(magUp = new MenuButton((int) buttonPosX, button_statsY + 20, (int) buttonWidth, ("MAG +"), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action("magUp");
        }));
        addRenderableWidget(defUp = new MenuButton((int) buttonPosX, button_statsY + 40, (int) buttonWidth, ("DEF +"), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action("defUp");
        }));
        addRenderableWidget(apUp = new MenuButton((int) buttonPosX, button_statsY + 60, (int) buttonWidth, ("AP +"), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action("apUp");
        }));
        addRenderableWidget(backButton = new MenuButton((int) buttonPosX, button_statsY + 120, (int) buttonWidth, (Strings.Gui_Menu_Back), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action("back");
        }));

        //Stats
        int c = 0;
        int d = 0;
        int spacer = 14;

        // Stats Column
        //addRenderableWidget(gainedHP = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth*2, Utils.translateToLocal("Gained Max HP: "), "" + addedData.getPrestigeLvl() * 2, 0x3ECE44));
        //addRenderableWidget(gainedMP = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth*2, Utils.translateToLocal("Gained Max MP: "), "" + addedData.getPrestigeLvl() * 2, 0x3ECE44));


        addRenderableWidget(str = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth, Utils.translateToLocal("Granted STR: "), "" + addedData.getSTRPanel(), 0xaa190f));
        addRenderableWidget(mag = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth, Utils.translateToLocal("Granted MAG: "), "" + addedData.getMAGPanel(), 0xaa190f));
        addRenderableWidget(def = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth, Utils.translateToLocal("Granted DEF: "), "" + addedData.getDEFPanel(), 0xaa190f));
        addRenderableWidget(ap = new MenuColourBox(col2X, button_statsY + (d++* spacer), (int) dataWidth, Utils.translateToLocal("AP: "), "" + playerData.getMaxAPStat().getStat(), 0xaa190f));
    }
}