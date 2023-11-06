package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.entity.magic.HolyEntity;
import online.magicksaddon.magicsaddonmod.lib.Strings;


public class magicHoly extends Magic {

    public magicHoly(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility) {
        super(registryName, hasToSelect, maxLevel, gmAbility);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnTarget){
        //IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);
        float dmgMult = getDamageMult(level) + ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(Strings.lightBoost) * 0.2F;
        dmgMult *= fullMPBlastMult;

        switch(level) {
        case 0:
        	for(int i = -1; i <=1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player,i, dmgMult);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
            }
            player.level.playSound(null, player.blockPosition(), MagicSounds.HOLY.get(), SoundSource.PLAYERS, 1F, 1F);

        	break;
        case 1:
        	for(int i = -2; i <= 2; i++) {
                HolyEntity holy = new HolyEntity(player.level, player,i, dmgMult);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
            }
            player.level.playSound(null, player.blockPosition(), MagicSounds.HOLY.get(), SoundSource.PLAYERS, 1F, 1F);

        	break;
        case 2:
        	for(int i = -3; i <= 3; i++) {
                HolyEntity holy = new HolyEntity(player.level, player,i, dmgMult);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
            }
            player.level.playSound(null, player.blockPosition(), MagicSounds.HOLY.get(), SoundSource.PLAYERS, 1F, 1F);

        	break;
        }

    }
}
