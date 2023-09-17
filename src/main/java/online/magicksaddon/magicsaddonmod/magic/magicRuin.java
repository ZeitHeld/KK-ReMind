package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.entity.magic.RuinEntity;

public class magicRuin extends Magic {

    public magicRuin(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility, int order) {
        super(registryName, hasToSelect, maxLevel, gmAbility, order);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult){
        // dmg
        float dmgMult = getDamageMult(level);
        dmgMult *= fullMPBlastMult;

        switch (level) {
		case 0: 
			ThrowableProjectile ruin = new RuinEntity(player.level, player, dmgMult);
            player.level.addFreshEntity(ruin);
            ruin.shootFromRotation(player, player.getXRot(), player.getYRot(),0,2F,0);
            player.level.playSound(null, player.blockPosition(), MagicSounds.RUIN.get(), SoundSource.PLAYERS, 1F, 1F);
			break;
		case 1:
            ThrowableProjectile ruinra = new RuinEntity(player.level, player, dmgMult * 0.25F);
            player.level.addFreshEntity(ruinra);
            ruinra.shootFromRotation(player, player.getXRot(), player.getYRot(),0,2F,0);
            player.level.playSound(null, player.blockPosition(), MagicSounds.RUIN.get(), SoundSource.PLAYERS, 1F, 1F);
			break;
		case 2:
            ThrowableProjectile ruinaga = new RuinEntity(player.level, player, dmgMult * 0.5F);
            player.level.addFreshEntity(ruinaga);
            ruinaga.shootFromRotation(player, player.getXRot(), player.getYRot(),0,2F,0);
            player.level.playSound(null, player.blockPosition(), MagicSounds.RUIN.get(), SoundSource.PLAYERS, 1F, 1F);
			break;
        }    
    }
}

