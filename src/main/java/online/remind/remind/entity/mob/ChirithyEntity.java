package online.remind.remind.entity.mob;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;
import online.remind.remind.entity.ModEntitiesRM;

public class ChirithyEntity extends Mob {

    private GoalSelector goalSelector;

    public ChirithyEntity(EntityType<? extends Mob> type, Level worldIn) {
        super(type, worldIn);
    }

    public ChirithyEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntitiesRM.TYPE_CHIRITHY.get(), world);
    }

    public ChirithyEntity(Level world){
        this(ModEntitiesRM.TYPE_CHIRITHY.get(), world);
    }

    /*
    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }*/

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1000.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.5D);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

}
