package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.entity.magic.UltimaEntity;


public class magicUltima extends Magic {
    public magicUltima(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility, int order) {
        super(registryName, hasToSelect, maxLevel, gmAbility, order);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult) {
        float dmgMult = getDamageMult(level);
        dmgMult *= fullMPBlastMult;

        switch (level) {
            case 0:
                ThrowableProjectile ultima = new UltimaEntity(player.level, player, dmgMult);
                ultima.setPos(player.getX(), player.getY()+5, player.getZ());
                player.level.addFreshEntity(ultima);
                ultima.shootFromRotation(player, player.getXRot(), player.getYRot()-6,0,0.5F,0);
                player.level.playSound(null, player.blockPosition(), MagicSounds.RUIN.get(), SoundSource.PLAYERS, 1F, 1F);
                break;
            case 1:

                break;
        }

    }
}
