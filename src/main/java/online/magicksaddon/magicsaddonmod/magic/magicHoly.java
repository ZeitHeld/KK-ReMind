package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.entity.magic.HolyEntity;


public class magicHoly extends Magic {

    public magicHoly(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility, int order) {
        super(registryName, hasToSelect, maxLevel, gmAbility, order);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult){
        //IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);
        System.out.println("Casting!");
        float dmgMult = getDamageMult(level);
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
                HolyEntity holy = new HolyEntity(player.level, player,i, dmgMult * 0.25F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
            }
            player.level.playSound(null, player.blockPosition(), MagicSounds.HOLY.get(), SoundSource.PLAYERS, 1F, 1F);

        	break;
        case 2:
        	for(int i = -3; i <= 3; i++) {
                HolyEntity holy = new HolyEntity(player.level, player,i, dmgMult * 0.5F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
            }
            player.level.playSound(null, player.blockPosition(), MagicSounds.HOLY.get(), SoundSource.PLAYERS, 1F, 1F);

        	break;
        }

    }
}
