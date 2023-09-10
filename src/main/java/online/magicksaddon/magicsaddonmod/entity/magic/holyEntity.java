package online.magicksaddon.magicsaddonmod.entity.magic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesMA;
import online.kingdomkeys.kingdomkeys.damagesource.MagicDamageSource;

public class holyEntity extends ThrowableProjectile {
    int maxTicks = 100;
    Player player;
    String caster;
    float dmgMult = 1;

    public holyEntity(EntityType<? extends ThrowableProjectile> type, level world){
        super(type, world);
        this.blocksBuilding = true;
    }

    public holyEntity(PlayMessages.SpawnEntity spawnEntity, level world) {
        super(ModEntitiesMA.TYPE_HOLY.get(), world);
    }

    public holyEntity(level world, LivingEntity player, float dmgMult){
        super(ModEntitiesMA.TYPE_HOLY.get(), player, world);
        this.dmgMult = dmgMult;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(){
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected float getGravity(){
        return 0F;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick(){
        if (this.tickCount > maxTicks){
            this.remove(RemovalReason.KILLED);
        }

        if(tickCount > 2){
            level.addParticle((ParticleOptions) ParticleTypes.SCULK_CHARGE, getX(), getY(), getZ(),0,0,0);

            super.tick();
        }

        @Override
        protected void onHit(HitResult rtRes){
            if (!level.isClientSide && getOwner() != null){
                EntityHitResult ertResult = null;
                BlockHitResult brtResult= null;

                if (rtRes instanceof EntityHitResult){
                    ertResult = (EntityHitResult) rtRes;
                }
                if (rtRes instanceof BlockHitResult){
                    brtResult = (BlockHitResult) htRes;
                }
                if (ertResult != null && ertResult.getEntity() instanceof LivingEntity){
                    LivingEntity target = (LivingEntity) ertResult.getEntity();
                }
                    if (target != getOwner()){
                        Party p = null;
                        if (getOwner() != null){
                            p = ModCapabilities.getWorld(getOwner().level).getPartyFromMember(getOwner().getUUID());
                        }
                        if(p == null || (p.getMember(target.getUUID()) == null || p.getFriendlyFire())){
                            float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) * 0.2F : 2;
                            target.hurt(MagicDamageSource.getMagicDamage(this, this.getOwner()), dmg * dmgMult);
                        }
                    }
                }
            if (brtResult != null){
                remove(RemovalReason.KILLED);
            }
            }

        }
}
