package online.remind.remind.entity.magic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import online.kingdomkeys.kingdomkeys.entity.ModEntities;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.remind.remind.entity.ModEntitiesRM;
import online.remind.remind.entity.reactioncommand.LightBeamEntity;

public class FaithEntity extends ThrowableProjectile {
    int maxTicks = 20;
    float dmgMult = 1;
    LivingEntity lockedOnEntity;

    public FaithEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public FaithEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntitiesRM.TYPE_FAITH.get(), world);
    }

    public FaithEntity(Level world, Player player, float dmgMult, LivingEntity lockedOnEntity) {
        super(ModEntitiesRM.TYPE_FAITH.get(), player, world);
        setCaster(player.getUUID());
        this.dmgMult = dmgMult;
        this.lockedOnEntity = lockedOnEntity;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public int getMaxTicks() {
        return maxTicks;
    }

    public void setMaxTicks(int maxTicks) {
        this.maxTicks = maxTicks;
    }

    private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(FaithEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        if (this.entityData.get(OWNER) != null) {
            compound.putString("OwnerUUID", this.entityData.get(OWNER).get().toString());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.entityData.set(OWNER, Optional.of(UUID.fromString(compound.getString("OwnerUUID"))));
    }

    public Player getCaster() {
        return this.getEntityData().get(OWNER).isPresent() ? this.level().getPlayerByUUID(this.getEntityData().get(OWNER).get()) : null;
    }

    public void setCaster(UUID uuid) {
        this.entityData.set(OWNER, Optional.of(uuid));
    }

    List<LivingEntity> list = new ArrayList<LivingEntity>();

    @Override
    public void tick() {
        if (this.tickCount > maxTicks) {
            this.remove(RemovalReason.KILLED);
        }

        if (getCaster() == null) {
            remove(RemovalReason.KILLED);
            return;
        }

        float radius = 2.5F;

        if (!level().isClientSide && getCaster() != null) { // Only calculate and spawn lightning bolts server side
            if (tickCount == 1) {
                if (lockedOnEntity != null) {
                    list = Utils.getLivingEntitiesInRadiusExcludingParty(getCaster(), lockedOnEntity, radius, radius, radius);
                } else {
                    list = Utils.getLivingEntitiesInRadiusExcludingParty(getCaster(), radius);
                }
                list.remove(this);
            }

            if (tickCount % 10 == 1){
                if (!list.isEmpty()) {
                    int i = level().random.nextInt(list.size());
                    Entity e = (Entity) list.get(i);
                    if (e instanceof LivingEntity) {
                        if(!e.isAlive()){
                            list.remove(e);
                        }
                        float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) * 0.05F :2;
                        LightBeamEntity shot = new LightBeamEntity(getCaster().level(), getCaster(), dmg * dmgMult, e.getX(), e.getY(), e.getZ());

                        level().addFreshEntity(shot);

                        LightningBolt LightBeamEntity = ModEntitiesRM.TYPE_LIGHT_BEAM.create(this.level());
                        LightBeamEntity.setVisualOnly(true);
                        LightBeamEntity.moveTo(Vec3.atBottomCenterOf(e.blockPosition()));
                        LightBeamEntity.setCause(getCaster() instanceof ServerPlayer ? (ServerPlayer) getCaster() : null);
                        this.level().addFreshEntity(LightBeamEntity);


                    }
                } else {
                    int x,z;
                    if(lockedOnEntity != null) {
                        x = (int) lockedOnEntity.getX();
                        z = (int) lockedOnEntity.getZ();
                    } else {
                        x = (int) getCaster().getX();
                        z = (int) getCaster().getZ();
                    }
                    int posX = (int) (x + getCaster().level().random.nextInt((int) (radius*2)) - radius / 2)-1;
                    int posZ = (int) (z + getCaster().level().random.nextInt((int) (radius*2)) - radius / 2)-1;

                    float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) * 0.05F :2;
                    LightBeamEntity shot = new LightBeamEntity(getCaster().level(), getCaster(), posX, getCaster().level().getHeight(Types.WORLD_SURFACE, posX, posZ), posZ, dmg * dmgMult);

                    level().addFreshEntity(shot);

                    BlockPos pos = new BlockPos(posX, getCaster().level().getHeight(Types.WORLD_SURFACE, posX, posZ), posZ);
                    LightningBolt LightBeamEntity = ModEntitiesRM.TYPE_LIGHT_BEAM.create(this.level());
                    LightBeamEntity.moveTo(Vec3.atBottomCenterOf(pos));
                    LightBeamEntity.setVisualOnly(true);
                    LightBeamEntity.setCause(getCaster() instanceof ServerPlayer ? (ServerPlayer) getCaster() : null);
                    this.level().addFreshEntity(LightBeamEntity);

                }
            }
        }

        super.tick();
    }


    @Override
    protected void defineSynchedData() {
        this.entityData.define(OWNER, Optional.of(Util.NIL_UUID));
    }
}

