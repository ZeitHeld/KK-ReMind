package online.remind.remind.entity.attacks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.Vec3;
import online.remind.remind.entity.ModEntitiesRM;

public class CrossSlashEffectEntity extends Entity {

    private static final EntityDataAccessor<Integer> STAGE =
            SynchedEntityData.defineId(
                    CrossSlashEffectEntity.class,
                    EntityDataSerializers.INT
            );

    public CrossSlashEffectEntity(
            EntityType<? extends CrossSlashEffectEntity> type,
            Level level
    ) {
        super(type, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData(
            SynchedEntityData.Builder builder
    ) {
        builder.define(STAGE, 1);
    }

    public void setStage(int stage) {
        entityData.set(
                STAGE,
                Math.max(1, Math.min(stage, 3))
        );
    }

    public int getStage() {
        return entityData.get(STAGE);
    }

    @Override
    public void tick() {
        super.tick();

        /*
         * No automatic lifetime here.
         *
         * CrossSlashSequenceHandler owns the effect lifetime
         * and discards this entity when the sequence ends.
         */
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Stage")) {
            setStage(tag.getInt("Stage"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Stage", getStage());
    }

    public static CrossSlashEffectEntity spawn(
            Level level,
            LivingEntity target,
            LivingEntity player
    ) {
        if (level.isClientSide) {
            return null;
        }

        CrossSlashEffectEntity effect =
                ModEntitiesRM.CROSS_SLASH_EFFECT.get().create(level);

        if (effect == null) {
            return null;
        }

        effect.setStage(1);

        // Direction from target toward the player
        Vec3 towardPlayer =
                player.position()
                        .subtract(target.position());

        if (towardPlayer.lengthSqr() < 0.001D) {
            towardPlayer = target.getLookAngle().scale(-1.0D);
        }

        towardPlayer = towardPlayer.normalize();

//
        double distanceInFront = 0.42D;

        effect.setPos(
                target.getX() + towardPlayer.x * distanceInFront,
                target.getY() + target.getBbHeight() * 0.62D,
                target.getZ() + towardPlayer.z * distanceInFront
        );

        level.addFreshEntity(effect);

        return effect;
    }
}