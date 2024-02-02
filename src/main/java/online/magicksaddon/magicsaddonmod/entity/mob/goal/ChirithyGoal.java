package online.magicksaddon.magicsaddonmod.entity.mob.goal;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.level.Level;

public class ChirithyGoal extends TargetGoal{


    public ChirithyGoal(Mob pMob, boolean pMustSee) {
        super(pMob, pMustSee);
    }







    @Override
    public boolean canUse() {
        return false;
    }


}
