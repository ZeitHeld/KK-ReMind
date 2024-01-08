package online.magicksaddon.magicsaddonmod.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.gui.GuiHelper;
import online.kingdomkeys.kingdomkeys.client.gui.elements.MenuBackground;
import online.kingdomkeys.kingdomkeys.client.gui.elements.buttons.MenuButton;

import java.awt.*;

public class PrestigeMenu extends MenuBackground{

    final IPlayerCapabilities playerData = ModCapabilities.getPlayer(minecraft.player);

    private MenuButton backButton, yes, no;


    public PrestigeMenu() {
        super("Re:Mind Menu", new Color(248, 225, 81));
        minecraft = Minecraft.getInstance();
    }
}
