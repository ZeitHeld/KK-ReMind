package online.magicksaddon.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.capabilities.ModCapabilitiesMA;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.magicksaddon.client.sound.MagicSounds;

public class magicHaste extends Magic {

    public magicHaste(ResourceLocation registryName, boolean hasToSelect, int maxLevel, int order) {
        super(registryName, hasToSelect, maxLevel, null, order++);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult) {
        System.out.println(player.level.isClientSide());
        System.out.println("Casting Haste");

        player.level.playSound(null, player.blockPosition(), MagicSounds.haste1.get(), SoundSource.PLAYERS, 1F, 1F);
        //System.out.println("Played Sound");

        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);
        /*System.out.println("Player: "+ player + " is casting Haste");
        System.out.println("Caster: "+ caster + " is casting Haste");
        System.out.println(globalData);
        System.out.println(caster + "has this much Max MP: "+ ModCapabilities.getPlayer(caster).getMaxMP());
        */
        int time = (int) (ModCapabilities.getPlayer(caster).getMaxMP() * (level * 0.5));
        globalData.setHasteTicks(time, level);
        caster.swing(InteractionHand.MAIN_HAND);

        System.out.println(time);
        System.out.println("Cast Successful!");






    }

}