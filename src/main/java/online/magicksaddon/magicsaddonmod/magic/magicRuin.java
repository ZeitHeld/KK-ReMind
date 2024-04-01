package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.entity.magic.RuinEntity;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;

public class magicRuin extends Magic {

    public magicRuin(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility) {
        super(registryName, hasToSelect, maxLevel, gmAbility);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnTarget){
        // dmg
        float dmgMult = getDamageMult(level) + ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsRM.darknessBoost) * 0.2F;
        dmgMult *= fullMPBlastMult;

        switch (level) {
		case 0: 
			ThrowableProjectile ruin = new RuinEntity(player.level(), player, dmgMult, 2);
            player.level().addFreshEntity(ruin);
            ruin.shootFromRotation(player, player.getXRot(), player.getYRot(),0,2F,0);
            player.level().playSound(null, player.blockPosition(), ModSoundsRM.RUIN.get(), SoundSource.PLAYERS, 1F, 1F);
			break;
		case 1:
            ThrowableProjectile ruinra = new RuinEntity(player.level(), player, dmgMult * 1.5F, 3);
            player.level().addFreshEntity(ruinra);
            ruinra.shootFromRotation(player, player.getXRot(), player.getYRot(),0,2F,0);
            player.level().playSound(null, player.blockPosition(), ModSoundsRM.RUIN.get(), SoundSource.PLAYERS, 1F, 1F);
			break;
		case 2:
            ThrowableProjectile ruinaga = new RuinEntity(player.level(), player, dmgMult * 2.5F, 4);
            player.level().addFreshEntity(ruinaga);
            ruinaga.shootFromRotation(player, player.getXRot(), player.getYRot(),0,2F,0);
            player.level().playSound(null, player.blockPosition(), ModSoundsRM.RUIN.get(), SoundSource.PLAYERS, 1F, 1F);
			break;
        }    
    }
}

