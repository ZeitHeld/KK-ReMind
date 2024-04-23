package online.magicksaddon.magicsaddonmod.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import online.kingdomkeys.kingdomkeys.item.ModItems;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.driveform.DriveFormTwilight;
import online.magicksaddon.magicsaddonmod.entity.reactioncommand.DarkMineEntity;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class TwilightForm extends ReactionCommand {


    public TwilightForm(ResourceLocation registryName, boolean constantCheck) {
        super(registryName, constantCheck);
    }


    @Override
    public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
        if (conditionsToAppear(player, player)) {
            IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
            IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
            playerData.setActiveDriveForm("magicksaddon:form_twilight");

        }
    }


    @Override
    public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
        if (playerData != null) {
            if (playerData.getDriveFormLevel("magicksaddon:form_dark") == 7 && playerData.getDriveFormLevel("magicksaddon:form_light") == 7) {
                //System.out.println(playerData.getEquippedKeychain(DriveForm.NONE));
                //System.out.println(playerData.getEquippedKeychain(DriveForm.SYNCH_BLADE));
                if (playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
                    if (playerData.getEquippedKeychain(DriveForm.NONE).equals("kingdomkeys:oblivion_chain")){
                        return true;
                    }
                } else if (playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
                    if (playerData.getEquippedKeychain(DriveForm.NONE).equals("kingdomkeys:oathkeeper_chain")){
                        return true;
                    }
                }
            }
        }
            return false;
        }
    }
