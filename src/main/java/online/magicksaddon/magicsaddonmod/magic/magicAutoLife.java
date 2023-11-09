package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;

public class magicAutoLife extends Magic {


    public magicAutoLife(ResourceLocation registryName, boolean hasToSelect, int maxLevel) {
        super(registryName, hasToSelect, maxLevel, null);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnTarget) {
        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);
        caster.swing(InteractionHand.MAIN_HAND);
        player.level.playSound(null, player.blockPosition(), MagicSounds.HASTE.get(), SoundSource.PLAYERS, 1F, 1F);

        // Auto Life status set to True
        //globalData.setAutoLifeActive() = true;
        System.out.println("Auto Life Active?" + globalData.getAutoLifeActive());
    }


}
