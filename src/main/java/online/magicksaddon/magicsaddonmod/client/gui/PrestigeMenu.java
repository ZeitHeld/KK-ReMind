package online.magicksaddon.magicsaddonmod.client.gui;

import java.awt.Color;

//import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.gui.GuiHelper;
import online.kingdomkeys.kingdomkeys.client.gui.elements.MenuBackground;
import online.kingdomkeys.kingdomkeys.client.gui.elements.MenuColourBox;
import online.kingdomkeys.kingdomkeys.client.gui.elements.buttons.MenuButton;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.lib.StringsX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;
import online.magicksaddon.magicsaddonmod.network.cts.CSPrestigePacket;

public class PrestigeMenu extends MenuBackground{



    private MenuButton backButton, prestige, levelReq;

    MenuColourBox level, prestigeLevel, gainedHP, gainedMP, gainedSTR, gainedMAG, gainedDEF;

    MenuColourBox[] playerWidgets = {level, prestigeLevel, gainedHP, gainedMP, gainedSTR, gainedMAG, gainedDEF};



    public PrestigeMenu() {
        super("New Game +", new Color(248, 225, 81));
        minecraft = Minecraft.getInstance();
    }

    protected void action(String string) {
        if (string.equals("back"))
            GuiHelper.openMenu();
        if (string.equals("confirm")){
            PacketHandlerX.sendToServer(new CSPrestigePacket());
            minecraft.setScreen(null);
        }

    }

    @Override
    public void init() {

        Player player;
        final IPlayerCapabilities playerData = ModCapabilities.getPlayer(minecraft.player);
        IGlobalCapabilitiesX addedData = ModCapabilitiesX.getGlobal(minecraft.player);
        System.out.println(addedData);

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

        int col1X = (int) (subButtonPosX + buttonWidth + 40), col2X=(int) (col1X + dataWidth * 2)+10 ;

        int i = 0;

        addRenderableWidget(backButton = new MenuButton((int) buttonPosX, button_statsY + 20, (int) buttonWidth, (Strings.Gui_Menu_Back), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action("back");
        }));
        if (playerData.getLevel() >= 90) {
            addRenderableWidget(prestige = new MenuButton((int) buttonPosX, button_statsY, (int) buttonWidth, (StringsX.Gui_Menu_Button_PrestigeConfirm), MenuButton.ButtonType.BUTTON, true, (e) -> {
                action("confirm");

            }));
        } else {
            addRenderableWidget(levelReq = new MenuButton((int) buttonPosX, button_statsY, (int) buttonWidth, "Levels Until NG+: " + (90 - playerData.getLevel()), MenuButton.ButtonType.BUTTON, false, (e) -> {
                action("prestige");
            }));
        }


        //Stats
        int c = 0;
        int spacer = 14;

        addRenderableWidget(level = new MenuColourBox(col1X, button_statsY + (c++* spacer), (int) dataWidth*2, Utils.translateToLocal(Strings.Gui_Menu_Status_Level),"" + playerData.getLevel(), 0x000088));
        addRenderableWidget(prestigeLevel = new MenuColourBox(col1X, button_statsY + (c++* spacer), (int) dataWidth*2, Utils.translateToLocal(StringsX.Gui_Menu_Button_PrestigeLevel),"" + addedData.getPrestigeLvl(), 0xe3ce44));
        //addRenderableWidget(gainedSTR = new MenuColourBox(col1X, button_statsY + (c++* spacer), (int) dataWidth*2, Utils.translateToLocal("Gained STR: "), "0", 0xe3ce44));
        //addRenderableWidget(gainedMAG = new MenuColourBox(col1X, button_statsY + (c++* spacer), (int) dataWidth*2, Utils.translateToLocal("Gained MAG: "), "0", 0xe3ce44));
        //addRenderableWidget(gainedDEF = new MenuColourBox(col1X, button_statsY + (c++* spacer), (int) dataWidth*2, Utils.translateToLocal("Gained MAG: "), "0", 0xe3ce44));
    }


}
