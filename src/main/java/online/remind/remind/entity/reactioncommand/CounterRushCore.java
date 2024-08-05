package online.remind.remind.entity.reactioncommand;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.remind.remind.entity.ModEntitiesRM;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CounterRushCore extends ThrowableProjectile {

    int maxTicks = 100;
    List<LivingEntity> targetList = new ArrayList<LivingEntity>();
    float dmg;

    float radius= 3;

    public CounterRushCore(EntityType<? extends ThrowableProjectile> type, Level world){
        super(type, world);
        this.blocksBuilding = true;
    }

    public CounterRushCore(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super((EntityType<? extends ThrowableProjectile>) ModEntitiesRM.TYPE_COUNTER_RUSH.get(), world);
    }

    public CounterRushCore(Player player, Level world, List<LivingEntity> targets, float dmg) {
        super(ModEntitiesRM.TYPE_COUNTER_RUSH.get(), player, world);
        setCaster(player.getUUID());
        String targetIDS = "";
        for(Entity t : targets) {
            targetIDS+=","+t.getId();
        }
        setTarget(targetIDS.substring(1));
        this.targetList = targets;
        this.dmg = dmg;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    int i = 0;

    @Override
    public void tick() {
        if (this.tickCount > maxTicks || getCaster() == null) {
            this.remove(RemovalReason.KILLED);
        }

        double X = getX();
        double Y = getY();
        double Z = getZ();

        if (getCaster() != null && targetList != null && !targetList.isEmpty() && targetList.size() > i) {
            IPlayerCapabilities playerData = ModCapabilities.getPlayer(getCaster());
            if (tickCount % 5 == 0) {
                Entity target = targetList.get(i++);
                if(target != null) {
                    float dmg = (float) (playerData.getStrengthStat().get() * 0.015f);
                    target.hurt(target.damageSources().indirectMagic(this, this.getOwner()), dmg);
                }
            }

            if(targetList.size() <= i) {
                this.remove(RemovalReason.KILLED);
            }
        }
        super.tick();
    }

    @Override
    protected void onHit(HitResult rtRes) {
        // Damage Here
    }

    public int getMaxTicks() {
        return maxTicks;
    }

    public void setMaxTicks(int maxTicks) {
        this.maxTicks = maxTicks;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.entityData.get(OWNER) != null) {
            compound.putString("OwnerUUID", this.entityData.get(OWNER).get().toString());
            compound.putString("TargetsUUID", this.entityData.get(TARGETS));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(OWNER, Optional.of(UUID.fromString(compound.getString("OwnerUUID"))));
        this.entityData.set(TARGETS, compound.getString("TargetUUID"));
    }

    private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(CounterRushCore.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<String> TARGETS = SynchedEntityData.defineId(CounterRushCore.class, EntityDataSerializers.STRING);

    public Player getCaster() {
        return this.getEntityData().get(OWNER).isPresent() ? this.level().getPlayerByUUID(this.getEntityData().get(OWNER).get()) : null;
    }

    public void setCaster(UUID uuid) {
        this.entityData.set(OWNER, Optional.of(uuid));
    }

   /* public List<Entity> getTargets() {
        List<Entity> list = new ArrayList<Entity>();
        String[] ids = this.getEntityData().get(TARGETS).split(",");

        for(String id : ids) {

            if(!id.equals(""))
                list.add(level().getEntity(Integer.parseInt(id)));
        }
        return list;
    }*/

    public void setTarget(String lists) {
        this.entityData.set(TARGETS, lists);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(OWNER, Optional.of(new UUID(0L, 0L)));
        this.entityData.define(TARGETS, "");
    }
}
