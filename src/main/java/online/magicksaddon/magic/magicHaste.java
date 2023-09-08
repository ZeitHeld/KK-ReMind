package online.magicksaddon.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.capabilities.ModCapabilitiesMA;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.magicksaddon.client.sound.MagicSounds;

import static java.awt.Event.F1;

public class magicHaste extends Magic {

    public magicHaste(ResourceLocation registryName, boolean hasToSelect, int maxLevel, int order) {
        super(registryName, hasToSelect, maxLevel, null, order++);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult) {
        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);
        int time = (int) (ModCapabilities.getPlayer(caster).getMaxMP() * (level * 0.5));
        globalData.setHasteTicks(time, level);
        caster.swing(InteractionHand.MAIN_HAND);
        // Effect and Level Modifier
        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Haste", 0.25 + (0.25 * level), AttributeModifier.Operation.MULTIPLY_BASE));
        player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Haste", 0.25 + (0.25 * level), AttributeModifier.Operation.MULTIPLY_BASE));
        player.level.playSound(null, player.getX(),player.getY(),player.getZ(), MagicSounds.HASTE.get(), SoundSource.PLAYERS, F1, F1);

    }

}