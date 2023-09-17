package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;


public class magicHaste extends Magic {

    public magicHaste(ResourceLocation registryName, boolean hasToSelect, int maxLevel, int order) {
        super(registryName, hasToSelect, maxLevel, null, order);
    }


    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult) {
        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);
        if(globalData != null) {
            int time = (int) (ModCapabilities.getPlayer(caster).getMaxMP() * ((level * 0.75) + 5) + 5);
            caster.swing(InteractionHand.MAIN_HAND);
            player.level.playSound(null, player.blockPosition(), MagicSounds.HASTE.get(), SoundSource.PLAYERS, 1F, 1F);
            // Effect and Level Modifier

            if (globalData.getHasteTicks() <= 0) {

                player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Haste", 0.25 + (0.25 * level), AttributeModifier.Operation.MULTIPLY_BASE));
                player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Haste", 0.25 + (0.25 * level), AttributeModifier.Operation.MULTIPLY_BASE));
            }
            globalData.setHasteTicks(time, level);
        }
    }

}