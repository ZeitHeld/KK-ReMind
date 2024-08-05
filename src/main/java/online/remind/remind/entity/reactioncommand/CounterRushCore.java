package online.remind.remind.entity.reactioncommand;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CounterRushCore extends ThrowableProjectile {
    int maxTicks = 100;
    List<Entity> targetList = new ArrayList<Entity>();
    float dmg;

    protected CounterRushCore(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    protected void defineSynchedData() {

    }
}
