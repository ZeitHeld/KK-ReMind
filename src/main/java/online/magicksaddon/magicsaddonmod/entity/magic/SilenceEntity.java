package online.magicksaddon.magicsaddonmod.entity.magic;

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
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesRM;
import org.joml.Vector3f;

import java.util.List;

public class SilenceEntity extends ThrowableProjectile {
    int maxTicks = 100, radius = 2;
    float timeMult;

    IWorldCapabilities worldData = ModCapabilities.getWorld(level());

    LivingEntity lockOnEntity;

    public SilenceEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public SilenceEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntitiesRM.TYPE_SILENCE.get(), world);
    }

    public SilenceEntity(Level world) {
        super(ModEntitiesRM.TYPE_SILENCE.get(), world);
        this.blocksBuilding = true;
    }

    public SilenceEntity(Level world, LivingEntity player, float timeMult, LivingEntity lockOnEntity) {
        super(ModEntitiesRM.TYPE_SILENCE.get(), player, world);
        this.timeMult = timeMult;
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
            level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(1F,1F,1F),1F),getX(), getY(), getZ(), 0, 0, 0);
        level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0.5F,0.8F),1F),getX() + level().random.nextDouble() - 0.5D, getY(), getZ() + level().random.nextDouble() - 0.5D, 0, 0, 0);


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
                            double time = (timeMult * (casterData.getMaxMP()/2));
                            System.out.println(time);
                            if(this.getOwner() instanceof Player) {
                                List<LivingEntity> targetList = Utils.getLivingEntitiesInRadiusExcludingParty((Player) this.getOwner(), this, radius,radius,radius);
                                for(LivingEntity e : targetList) {
                                // Silence (Add Magic CD)
                                //targetData.remMP(dmg);
                                targetData.setMagicCooldownTicks((int) time);
                                playSound(ModSoundsRM.SILENCEHIT.get(),1F,1F);
                                PacketHandler.sendTo(new SCSyncCapabilityPacket(targetData), (ServerPlayer) target);
                                }
                            }
                        }
                    }
                } else {
                    remove(RemovalReason.KILLED);
                }
            }

            if (brtResult != null) {
                if (this.getOwner() instanceof Player) {
                    remove(RemovalReason.KILLED);
                }
            }


        }
    }

}
