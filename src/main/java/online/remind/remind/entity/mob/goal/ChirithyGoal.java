package online.remind.remind.entity.mob.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.PathfinderMob;

public class ChirithyGoal extends TargetGoal{


    public ChirithyGoal(Mob pMob, boolean pMustSee) {
        super(pMob, pMustSee);
    }









    @Override
    public boolean canUse() {
        return false;
    }


}
