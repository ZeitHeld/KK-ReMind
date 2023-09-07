package online.magicksaddon.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.magicksaddon.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.client.sound.MagicSounds;

public class magicSlow extends Magic {
    public magicSlow(ResourceLocation registryName, boolean hasToSelect, int maxLevel, int order) {
        super(registryName, hasToSelect, maxLevel, null, order++);
    }

    IGlobalCapabilitiesMA globalData;

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult) {
        System.out.println("Casting Spell");
        player.level.playSound(null, player.blockPosition(), MagicSounds.slow.get(), SoundSource.PLAYERS, 1F, 1F);
        IGlobalCapabilitiesMA globalData = (IGlobalCapabilitiesMA) ModCapabilities.getGlobal(player);
        int time = (int) (ModCapabilities.getPlayer(caster).getMaxMP() * (4F + level / 2F) * globalData.getHasteTicks());
        globalData.setSlowTicks(time, level);
        PacketHandler.syncToAllAround(player, (online.kingdomkeys.kingdomkeys.capability.IGlobalCapabilities) globalData);
        caster.swing(InteractionHand.MAIN_HAND);
    }
}
