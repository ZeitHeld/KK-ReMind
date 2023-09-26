package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.entity.magic.CometEntity;


public class magicComet extends Magic {

    public magicComet(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility, int order) {
        super(registryName, hasToSelect, maxLevel, gmAbility, order);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult) {
        float dmgMult = getDamageMult(level);
        dmgMult *= fullMPBlastMult;

        switch (level) {
            case 0:
                // Comet
                ThrowableProjectile comet = new CometEntity(player.level, player, dmgMult, 2, 0);
                comet.setOwner(caster);
                comet.setPos(player.getX(), player.getY() + 4, player.getZ());
                player.level.addFreshEntity(comet);
                comet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 0);
                player.level.playSound(null, player.blockPosition(), MagicSounds.PLAYER_CAST.get(), SoundSource.PLAYERS, 1F, 1F);
                break;
            case 1:
                // Meteor
                for (int i = -3; i <= 3; i++) {
                    ThrowableProjectile meteor = new CometEntity(player.level, player, dmgMult, 2, i);
                    meteor.setOwner(caster);
                    meteor.setPos(player.getX()+ (Math.random() * 10 ) - 5, player.getY() + (Math.random() * i) + 6, player.getZ()+ (Math.random() * 10) - 5);
                    player.level.addFreshEntity(meteor);
                    meteor.shootFromRotation(player, player.getXRot(), player.getYRot() , 0, 1.5F, 0);
                }
                player.level.playSound(null, player.blockPosition(), MagicSounds.PLAYER_CAST.get(), SoundSource.PLAYERS, 1F, 1F);
                break;
        }

    }
}
