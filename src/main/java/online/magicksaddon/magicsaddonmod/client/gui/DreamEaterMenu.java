package online.magicksaddon.magicsaddonmod.client.gui;

import net.minecraft.client.Minecraft;
import online.kingdomkeys.kingdomkeys.client.gui.GuiHelper;
import online.kingdomkeys.kingdomkeys.client.gui.elements.MenuBackground;
import online.kingdomkeys.kingdomkeys.client.gui.elements.buttons.MenuButton;
import online.kingdomkeys.kingdomkeys.lib.Strings;

import java.awt.*;

public class DreamEaterMenu extends MenuBackground {

    public DreamEaterMenu(String name, Color rgb) {
        super(name, rgb);
    }

    private MenuButton backButton, bond, changeParty, createSpirit, abilityLinks;

    public DreamEaterMenu() {
        super("Dream Eaters", new Color(236, 85, 236));
        minecraft = Minecraft.getInstance();
    }

    protected void action(String string) {
        if (string.equals("back"))
        	GUIHelperX.openAddonMenu();
    }

    @Override
    public void init() {
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

        addRenderableWidget(bond = new MenuButton((int) buttonPosX, button_statsY + 20, (int) buttonWidth, "Bond", MenuButton.ButtonType.BUTTON, false, (e) -> {
            action(null);
        }));
        addRenderableWidget(changeParty = new MenuButton((int) buttonPosX, button_statsY + 40, (int) buttonWidth, "Change Party", MenuButton.ButtonType.BUTTON, false, (e) -> {
            action(null);
        }));
        addRenderableWidget(createSpirit = new MenuButton((int) buttonPosX, button_statsY + 60, (int) buttonWidth, "Create Spirit", MenuButton.ButtonType.BUTTON, false, (e) -> {
            action(null);
        }));
        addRenderableWidget(abilityLinks = new MenuButton((int) buttonPosX, button_statsY + 80, (int) buttonWidth, "Ability Links", MenuButton.ButtonType.BUTTON, false, (e) -> {
            action(null);
        }));
        addRenderableWidget(backButton = new MenuButton((int) buttonPosX, button_statsY + 100, (int) buttonWidth, (Strings.Gui_Menu_Back), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action("back");
        }));
    }
}
