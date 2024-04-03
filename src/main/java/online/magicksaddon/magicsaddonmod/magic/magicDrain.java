package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.entity.magic.DrainEntity;

public class magicDrain extends Magic {

    public magicDrain(ResourceLocation registryName, boolean hasToSelect, int maxLevel) {
        super(registryName, hasToSelect, maxLevel, null);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnEntity) {
        float hpTaken = getDamageMult(level);
        hpTaken *= fullMPBlastMult;

        lockOnEntity = getMagicLockOn(level) ? lockOnEntity : null;
        caster.swing(InteractionHand.MAIN_HAND);
        player.level().playSound(null, player.blockPosition(), ModSoundsRM.DRAIN.get(), SoundSource.PLAYERS, 1F, 1F);

        switch(level) {
            case 0:
                ThrowableProjectile drain = new DrainEntity(player.level(), player, hpTaken,lockOnEntity);
                player.level().addFreshEntity(drain);
                drain.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2F, 0);
                break;
            case 1:
                ThrowableProjectile drainra = new DrainEntity(player.level(), player, hpTaken,lockOnEntity);
                player.level().addFreshEntity(drainra);
                drainra.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2.5F, 0);
                break;
            case 2:
                ThrowableProjectile drainga = new DrainEntity(player.level(), player, hpTaken,lockOnEntity);
                player.level().addFreshEntity(drainga);
                drainga.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3F, 0);
                break;
        }


    }
}
