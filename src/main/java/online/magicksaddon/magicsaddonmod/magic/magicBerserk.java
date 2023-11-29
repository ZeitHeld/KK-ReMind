package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;


public class magicBerserk extends Magic {

    public magicBerserk(ResourceLocation registryName, boolean hasToSelect, int maxLevel) {
        super(registryName, hasToSelect, maxLevel, null);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnTarget) {

        IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);

        if(globalData != null) {
            int time = 100;//(int) (ModCapabilities.getPlayer(caster).getMaxMP() * ((level * 0.75) + 5));
            caster.swing(InteractionHand.MAIN_HAND);
            player.level.playSound(null, player.blockPosition(), MagicSounds.BERSERK.get(), SoundSource.PLAYERS, 1F, 1F);
            // Effect and Level Modifier
            IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
                if (globalData.getBerserkTicks() <= 0) {

                    // Future color change line below
                    // Levels 0 - 2
                    switch (level) {
                        case 0:
                            playerData.getStrengthStat().addModifier("berserk", +3, false);
    						break;
                        case 1:
                            playerData.getStrengthStat().addModifier("berserk", +6 , false);
                            break;
                        case 2:
                            playerData.getStrengthStat().addModifier("berserk", +9 , false);
                            break;

                    }
                globalData.setBerserkTicks(time, level);
				PacketHandlerX.syncGlobalToAllAround(player, (IGlobalCapabilitiesX) globalData);

            }
        }
    }


}