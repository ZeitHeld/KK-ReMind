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
        PRESTIGE, DREAMEATER
    }

    final int SUBMENU_MAIN = 0, SUBMENU_ITEMS = 1;

    MenuButton prestige, dreamEater;

    protected void action(buttons buttonID){
        switch (buttonID){
            case PRESTIGE -> minecraft.setScreen(new PrestigeMenu());
            case DREAMEATER -> minecraft.setScreen(new DreamEaterMenu());
        }
    }

    @Override
    public void init(){
        super.width = width;
        super.height = height;
        super.init();

        float topBarHeight = (float) height * 0.17F;
        int start = (int)topBarHeight + 150;
        int pos = 0;

        float buttonPosX = (float) width * 0.03F;
        float buttonWidth = ((float) width * 0.1744F) - 22;

        addRenderableWidget(prestige = new MenuButton((int) buttonPosX, start, (int) buttonWidth, (StringsX.Gui_Menu_Button_Prestige), MenuButton.ButtonType.BUTTON, true, (e) -> {
            action(buttons.PRESTIGE);

        }));
        addRenderableWidget(dreamEater = new MenuButton((int) buttonPosX, start + 17, (int) buttonWidth, (StringsX.Gui_Menu_Button_DreamEater), MenuButton.ButtonType.BUTTON, false, (e) -> {
            action(buttons.DREAMEATER);
        }));
    }
}
