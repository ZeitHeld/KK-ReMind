package online.magicksaddon.magicsaddonmod.entity.reactioncommand;

import java.util.List;

import org.joml.Vector3f;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.damagesource.DarknessDamageSource;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesRM;

public class DarkMineEntity extends ThrowableProjectile {
    int maxTicks = 120;
    float dmg;

    @Override
    protected float getGravity() {
        return 0F;
    }

    public DarkMineEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public DarkMineEntity(PlayMessages.SpawnEntity spawnEntity, Level world){
        super(ModEntitiesRM.TYPE_DARK_MINE.get(), world);
    }

    public DarkMineEntity(Level world, LivingEntity player, float damage, double x, double y, double z) {
        this(world, player,  damage);
        this.setPos(x,y,z);
    }

    public DarkMineEntity(Level world) {
        super(ModEntitiesRM.TYPE_DARK_MINE.get(), world);
        this.blocksBuilding = true;
    }

    public DarkMineEntity(Level world, LivingEntity player, float damage) {
        super(ModEntitiesRM.TYPE_DARK_MINE.get(), player, world);
        this.dmg = damage;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        if (this.tickCount > maxTicks) {
            level().explode(this.getOwner(), this.blockPosition().getX(),this.blockPosition().getY() + (double)(this.getBbHeight() / 16.0F), this.blockPosition().getZ(), 3,false, Level.ExplosionInteraction.NONE);
            this.remove(RemovalReason.KILLED);
        }

        //world.addParticle(ParticleTypes.ENTITY_EFFECT, getPosX(), getPosY(), getPosZ(), 1, 1, 0);
        if(tickCount > 0)
            level().addParticle(ParticleTypes.SQUID_INK, getX(), getY() + 1, getZ(), 0, 0, 0);
            level().addAlwaysVisibleParticle(ParticleTypes.SQUID_INK, getX() + level().random.nextDouble() - 0.5D, getY()+ level().random.nextDouble() *2D, getZ() + level().random.nextDouble() - 0.5D, 0, 0, 0);
            level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0F,0.5F),1F),getX() + level().random.nextDouble() - 0.5D, getY()+ level().random.nextDouble() *2D, getZ() + level().random.nextDouble() - 0.5D, 0, 0, 0);
            level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0F,1F),1F),getX() + level().random.nextDouble() - 0.5D, getY()+ level().random.nextDouble() *2D, getZ() + level().random.nextDouble() - 0.5D, 0, 0, 0);
            level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.2F,0F,0F),1F),getX() + level().random.nextDouble() - 0.55D, getY()+ level().random.nextDouble() *2D, getZ() + level().random.nextDouble() - 0.55D, 0, 0, 0);
        if(tickCount > 8){
            setDeltaMovement(0,0,0);
            //level().playSound(null, this.getX(),this.getY(),this.getZ(), ModSoundsRM.DARK_MINE_ALIVE.get(), SoundSource.NEUTRAL,1F,1F);
        }

        super.tick();
    }

    @Override
    protected void onHit(HitResult rtRes) {
        if (!level().isClientSide && getOwner() != null) {
            EntityHitResult ertResult = null;
            BlockHitResult brtResult = null;

            int radius = 3;

            if (rtRes instanceof EntityHitResult) {
                ertResult = (EntityHitResult) rtRes;
            }

            if (rtRes instanceof BlockHitResult) {
                brtResult = (BlockHitResult) rtRes;
            }

            if (ertResult != null && ertResult.getEntity() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) ertResult.getEntity();

                if (target != getOwner()) {
                    Party p = null;
                    if (getOwner() != null) {
                        p = ModCapabilities.getWorld(getOwner().level()).getPartyFromMember(getOwner().getUUID());
                    }
                    if(p == null || (p.getMember(target.getUUID()) == null || p.getFriendlyFire())) { //If caster is not in a party || the party doesn't have the target in it || the party has FF on
                        float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) : 2;
                        Player player = (Player) this.getOwner();
                        //target.hurt(DarknessDamageSource.getDarknessDamage(this, this.getOwner()), dmg * dmgMult);
                        if(this.getOwner() instanceof Player) {
                            List<LivingEntity> targetList = Utils.getLivingEntitiesInRadiusExcludingParty((Player) this.getOwner(), this, radius,radius,radius);
                            for(LivingEntity e : targetList) {
                                e.hurt(DarknessDamageSource.getDarknessDamage(this, this.getOwner()), dmg);
                                e.invulnerableTime = 0;
                            }
                        }
                        level().explode(this.getOwner(), this.blockPosition().getX(), this.blockPosition().getY() + (double)(this.getBbHeight() / 16.0F), this.blockPosition().getZ(), radius, false, Level.ExplosionInteraction.NONE);
                        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
                        playerData.setDriveFormExp(player, playerData.getActiveDriveForm(), (int) (playerData.getDriveFormExp(playerData.getActiveDriveForm()) + (2)));


                        remove(RemovalReason.KILLED);

                    }
                }
            }

            if (brtResult != null) {
                setDeltaMovement(0,0,0);
            }
        }

    }
    @Override
    protected void defineSynchedData() {

    }
}

