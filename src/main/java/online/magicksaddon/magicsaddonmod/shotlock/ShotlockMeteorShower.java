package online.magicksaddon.magicsaddonmod.shotlock;

import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.magicksaddon.magicsaddonmod.entity.shotlock.MeteorShowerCoreEntity;
import online.magicksaddon.magicsaddonmod.lib.StringsX;

public class ShotlockMeteorShower extends Shotlock {

    public ShotlockMeteorShower(String registeryName, int order, int cooldown, int max){
        super(registeryName,order,cooldown,max);
    }

    @Override
    public void onUse(Player player, List<Entity> targetList) {

        float damage = (float) (DamageCalculation.getMagicDamage(player) * 0.5 + ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsX.lightBoost) * 0.2F);
        MeteorShowerCoreEntity core = new MeteorShowerCoreEntity(player.level(), player, targetList, damage);
        core.setPos(player.getX(), player.getY(), player.getZ());
        player.level().addFreshEntity(core);



    }
}
