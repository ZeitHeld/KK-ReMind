package online.magicksaddon.magicsaddonmod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen;
import online.kingdomkeys.kingdomkeys.client.gui.elements.MenuBackground;
import online.kingdomkeys.kingdomkeys.client.gui.elements.buttons.MenuButton;
import online.kingdomkeys.kingdomkeys.client.gui.menu.MenuScreen;
import online.magicksaddon.magicsaddonmod.lib.StringsX;

import java.awt.*;

public class AddonMenu extends MenuScreen {

    public AddonMenu(){
        super();
        minecraft = Minecraft.getInstance();
    }

    public enum buttons {
        PRESTIGE, PANELS
    }

    final int SUBMENU_MAIN = 0, SUBMENU_ITEMS = 1;

    MenuButton prestige, panels;

    protected void action(buttons buttonID){
        switch (buttonID){
            case PRESTIGE -> minecraft.setScreen(new PrestigeMenu());
        }
    }

    @Override
    public void init(){
        super.width = width;
        super.height = height;
        super.init();

        float topBarHeight = (float) height * 0.17F;
        int start = (int)topBarHeight + 135;
        int pos = 0;

        float buttonPosX = (float) width * 0.03F;
        float buttonWidth = ((float) width * 0.1744F) - 22;

        addRenderableWidget(prestige = new MenuButton((int) buttonPosX, start, (int) buttonWidth, (StringsX.Gui_Menu_Button_Prestige), MenuButton.ButtonType.BUTTON, true, (e) -> {
            action(buttons.PRESTIGE);
        }));
        /*
        addRenderableWidget(panels = new MenuButton((int) buttonPosX, start+18, (int) buttonWidth, "Panels", MenuButton.ButtonType.BUTTON, false, (e) -> {
            action(buttons.PANELS);
        }));
         */

    }




}
