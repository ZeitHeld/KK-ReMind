package online.magicksaddon.magicsaddonmod.entity.mob;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesMA;

public class ChirithyEntity extends Entity {

    private GoalSelector goalSelector;

    public ChirithyEntity(EntityType<? extends Entity> type, Level worldIn) {
        super(type, worldIn);
    }

    public ChirithyEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntitiesMA.TYPE_CHIRITHY.get(), world);
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
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }
}
