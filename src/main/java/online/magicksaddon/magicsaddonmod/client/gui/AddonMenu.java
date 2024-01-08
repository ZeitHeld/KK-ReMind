package online.magicksaddon.magicsaddonmod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen;
import online.kingdomkeys.kingdomkeys.client.gui.elements.MenuBackground;

import java.awt.*;

public class AddonMenu extends MenuBackground{

    public AddonMenu(){
        super("Re:Mind Menu", new Color(68, 4, 4));
        minecraft = Minecraft.getInstance();
    }

    public enum buttons {
        PRESTIGE
    }

    final int SUBMENU_MAIN = 0, SUBMENU_ITEMS = 1;

    protected void action(buttons buttonID){
        switch (buttonID){
            case PRESTIGE -> minecraft.setScreen(new PrestigeMenu());
        }
    }




}
