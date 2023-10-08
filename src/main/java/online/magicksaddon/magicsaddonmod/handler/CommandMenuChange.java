package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.client.gui.overlay.CommandMenuGui;
import online.kingdomkeys.kingdomkeys.client.sound.ModSounds;
import online.kingdomkeys.kingdomkeys.handler.InputHandler;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.magicksaddon.magicsaddonmod.capabilities.IPlayerCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.lib.Strings;

import java.util.List;
import java.util.UUID;

import static online.kingdomkeys.kingdomkeys.client.gui.overlay.CommandMenuGui.*;

public class CommandMenuChange extends InputHandler {

    List<UUID> portalCommands;
    public boolean darkPassageCheck() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        Level world = mc.level;

        IWorldCapabilities worldData = ModCapabilities.getWorld(world);
        if(playerData == null || worldData == null)


        switch (CommandMenuGui.selected) {
            case CommandMenuGui.ATTACK: //Accessing ATTACK / PORTAL submenu
                if (playerData.isAbilityEquipped(Strings.darkPassage) == darkPassageCheck() && playerData.getAlignment() == Utils.OrgMember.NONE) {
                    // Submenu of the portals
                    if (submenu == SUB_MAIN) {
                        if (!this.portalCommands.isEmpty() && !playerData.getRecharge()) {
                            submenu = SUB_PORTALS;
                            portalSelected = 0;
                            world.playSound(player, player.position().x(), player.position().y(), player.position().z(), ModSounds.menu_in.get(), SoundSource.MASTER, 1.0f, 1.0f);
                        } else {
                            selected = ATTACK;
                            world.playSound(player, player.position().x(), player.position().y(), player.position().z(), ModSounds.error.get(), SoundSource.MASTER, 1.0f, 1.0f);
                        }
                    }
                }
        }
        return false;
    }
}