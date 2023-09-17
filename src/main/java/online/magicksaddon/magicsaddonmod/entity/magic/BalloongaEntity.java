package online.magicksaddon.magicsaddonmod.entity.magic;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesMA;

public class BalloongaEntity extends ThrowableProjectile {
    // Start
    int maxTicks = 100;
    float dmgMult = 1;

    public BalloongaEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public BalloongaEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntitiesMA.TYPE_BALLOONGA.get(), world);
    }

    public BalloongaEntity(Level world) {
        super(ModEntitiesMA.TYPE_BALLOONGA.get(), world);
        this.blocksBuilding = true;
    }

    public BalloongaEntity(Level world, LivingEntity player, float dmgMult) {
        super(ModEntitiesMA.TYPE_BALLOONGA.get(), player, world);
        this.dmgMult = dmgMult;
    }


    private void balloonBurst(){
        float explosionSize = 2.0F;
        this.level.explode(this, this.blockPosition().getX(), this.blockPosition().getY() + (double)(this.getBbHeight() / 1.0F), this.blockPosition().getZ(), explosionSize, false, Explosion.BlockInteraction.NONE);
    }


    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected float getGravity() {
        return 0.125F;
    }

    private void Caster(){
        this.getOwner();
    }

    @Override
    public void tick() {
        if (this.tickCount > maxTicks) {
            this.remove(RemovalReason.KILLED);
        }

        //world.addParticle(ParticleTypes.ENTITY_EFFECT, getPosX(), getPosY(), getPosZ(), 1, 1, 0);
        if(tickCount > 2)
            level.addParticle(ParticleTypes.GLOW_SQUID_INK, getX(), getY(), getZ(), 0, 0, 0);

        super.tick();
    }

    @Override
    protected void onHit(HitResult rtRes) {
        if (!level.isClientSide && getOwner() != null) {
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

                if (target != getOwner()) {
                    Party p = null;
                    if (getOwner() != null) {
                        p = ModCapabilities.getWorld(getOwner().level).getPartyFromMember(getOwner().getUUID());
                    }
                    if(p == null || (p.getMember(target.getUUID()) == null || p.getFriendlyFire())) { //If caster is not in a party || the party doesn't have the target in it || the party has FF on
                        float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) * 0.2F : 2;
                        target.hurt(DamageSource.thrown(this, this.getOwner()), dmg * dmgMult);
                        target.invulnerableTime = 0;
                        playSound(MagicSounds.BALLOON_BOUNCE.get(),1F,1F);
                        // The Dumb part
                        for(int i = 0; i < 360; i+=45) {
                            ThrowableProjectile balloon = new BalloonEntity(this.level, (LivingEntity) getOwner(), dmgMult);
                            balloon.setPos(new Vec3(this.getX(), this.getY(), this.getZ()));
                            balloon.shootFromRotation(this, this.getXRot(), this.getYRot()+i, 0, 0.5F, 0);
                            level.addFreshEntity(balloon);

                        }
                        this.remove(RemovalReason.KILLED);
                    }
                }
            }

            if (brtResult != null && rtRes.getType() == HitResult.Type.BLOCK) {

                // Bounce

                Vec3 mot = getDeltaMovement();

                double x = mot.x();
                double y = mot.y();
                double z = mot.z();

                if(brtResult.getDirection() == Direction.UP || brtResult.getDirection() == Direction.DOWN){
                    this.setDeltaMovement(x,-y,z);
                    this.markHurt();
                } else if (brtResult.getDirection() == Direction.EAST || brtResult.getDirection() == Direction.WEST){
                    this.setDeltaMovement(-x,y,z);
                    this.markHurt();
                }else if (brtResult.getDirection() == Direction.NORTH || brtResult.getDirection() == Direction.SOUTH){
                	this.setDeltaMovement(x,y,-z);
                    this.markHurt();
                }
                playSound(MagicSounds.BALLOON_BOUNCE.get(),1F,1F);

            }
        }

    }
    public int getMaxTicks() {
        return maxTicks;
    }

    public void setMaxTicks(int maxTicks) {
        this.maxTicks = maxTicks;
    }



    @Override
    protected void defineSynchedData() {
        // TODO Auto-generated method stub

    }
}
