package online.remind.remind.shotlock;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.remind.remind.client.sound.ModSoundsRM;

import java.util.List;

public class ShotlockHeartlessAngel extends Shotlock {

    public ShotlockHeartlessAngel(String registeryName, int order, int cooldown, int max){
        super(registeryName,order,cooldown,max);
    }

    int maxTicks;

    @Override
    public void doPartialShotlock(Player player, List<Entity> list) {

    }

    @Override
    public void doFullShotlock(Player player, List<Entity> list) {
        Entity target = list.get(0);
        if (target instanceof Player){
            IPlayerCapabilities targetData = ModCapabilities.getPlayer((Player) target);

            targetData.remMP(targetData.getMaxMP());
            targetData.remFocus(100);
            targetData.remDP(1000);
            int damage = targetData.getMaxHP();
            ((Player) target).setHealth(2);
        }
        if (target instanceof LivingEntity le) {
            le.setHealth(2);
        }
        //System.out.println(target);
        player.level().playSound(null, player.blockPosition(), ModSoundsRM.HEARTLESS_ANGEL.get(), SoundSource.PLAYERS, 1F, 1F);
    }
}
