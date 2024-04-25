package online.magicksaddon.magicsaddonmod.entity.magic;

import org.joml.Vector3f;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
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
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesRM;

public class DrainEntity extends ThrowableProjectile {

    IWorldCapabilities worldData = ModCapabilities.getWorld(level());

    int maxTicks = 100;
    float dmgMult = 1;
    LivingEntity lockOnEntity;

    public DrainEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public DrainEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntitiesRM.TYPE_DRAIN.get(), world);
    }

    public DrainEntity(Level world) {
        super(ModEntitiesRM.TYPE_DRAIN.get(), world);
        this.blocksBuilding = true;
    }

    public DrainEntity(Level world, LivingEntity player, float dmgMult, LivingEntity lockOnEntity) {
        super(ModEntitiesRM.TYPE_DRAIN.get(), player, world);
        this.dmgMult = dmgMult;
        this.lockOnEntity = lockOnEntity;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected float getGravity() {
        return 0F;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        if (this.tickCount > maxTicks) {
            this.remove(RemovalReason.KILLED);
        }

        //world.addParticle(ParticleTypes.ENTITY_EFFECT, getPosX(), getPosY(), getPosZ(), 1, 1, 0);
        if(tickCount > 0)
            //level().addParticle(ParticleTypes.SQUID_INK, getX(), getY(), getZ(), 0, 0, 0);
            level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(1F,0F,0F),1F),getX(), getY(), getZ(), 0, 0, 0);
            level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.2F,0F,0F),1F),getX() + level().random.nextDouble() - 0.5D, getY(), getZ() + level().random.nextDouble() - 0.5D, 0, 0, 0);
            level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(1.5F,0F,0F),1F),getX() + level().random.nextDouble() - 0.5D, getY(), getZ() + level().random.nextDouble() - 0.5D, 0, 0, 0);


        super.tick();
    }

    @Override
    protected void onHit(HitResult rtRes) {
        if (!level().isClientSide && getOwner() != null) {
            EntityHitResult ertResult = null;
            BlockHitResult brtResult = null;

            if (rtRes instanceof EntityHitResult) {
                ertResult = (EntityHitResult) rtRes;
            }

            if (rtRes instanceof BlockHitResult) {
                brtResult = (BlockHitResult) rtRes;
            }

            if (ertResult != null && ertResult.getEntity() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) ertResult.getEntity();
                IPlayerCapabilities casterData = ModCapabilities.getPlayer((Player) getOwner());
                if (ertResult != null && ertResult.getEntity() instanceof Player) {
                IPlayerCapabilities targetData = ModCapabilities.getPlayer((Player) target);
                if (target != getOwner()) {
                    Party p = null;
                    if (getOwner() != null) {
                        p = ModCapabilities.getWorld(getOwner().level()).getPartyFromMember(getOwner().getUUID());
                    }
                    if(p == null || (p.getMember(target.getUUID()) == null || p.getFriendlyFire())) { //If caster is not in a party || the party doesn't have the target in it || the party has FF on
                        float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) / 2.5F : 2;
                        //dmg = (float) Math.max(dmg*dmgMult,targetData.getMaxHP());
                        System.out.println("HP Stolen: "+ dmg*dmgMult);
                        if(this.getOwner() instanceof Player) {
                            // HP & Hunger Drain
                            target.hurt(damageSources().indirectMagic(this, this.getOwner()), dmg);
                            ((Player) target).getFoodData().eat(-5,0);

                            // HP & Hunger Give to Caster
                            ((Player) getOwner()).heal(dmg);
                            ((Player) getOwner()).getFoodData().eat(5,5);
                            PacketHandler.sendTo(new SCSyncCapabilityPacket(casterData), (ServerPlayer) getOwner());
                        }
                    }
                }
            } else {
                        float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) / 2.5F : 2;
                        System.out.println("HP Stolen: "+ dmg*dmgMult);
                        if(this.getOwner() instanceof Player) {
                            // HP & Hunger Give to Caster
                            target.hurt(damageSources().indirectMagic(this, this.getOwner()), dmg);
                            ((Player) getOwner()).heal(dmg);
                            ((Player) getOwner()).getFoodData().eat(5,5);
                            PacketHandler.sendTo(new SCSyncCapabilityPacket(casterData), (ServerPlayer) getOwner());
                        }
                }
                remove(RemovalReason.KILLED);
            }

            if (brtResult != null) {
                if (this.getOwner() instanceof Player) {
                    float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) / 2.5F : 2;
                    }
                }

                remove(RemovalReason.KILLED);
            }
        }

    }
