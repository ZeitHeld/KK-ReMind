package online.magicksaddon.magicsaddonmod.entity.magic;

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
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.damagesource.DarknessDamageSource;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesMA;

public class UltimaEntity extends ThrowableProjectile {
        int maxTicks = 300;
        Player player;
        String caster;
        float dmgMult = 1;
        float radius = 0.5F;

        public UltimaEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
                super(type, world);
                this.blocksBuilding = true;
        }

        public UltimaEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
                super(ModEntitiesMA.TYPE_RUIN.get(), world);
        }

        public UltimaEntity(Level world) {
                super(ModEntitiesMA.TYPE_RUIN.get(), world);
                this.blocksBuilding = true;
        }

        public UltimaEntity(Level world, LivingEntity player, float dmgMult) {
                super(ModEntitiesMA.TYPE_RUIN.get(), player, world);
                this.dmgMult = dmgMult;
        }

        @Override
        public Packet<ClientGamePacketListener> getAddEntityPacket() {
                return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
        }

        @Override
        protected float getGravity(){
                return 0F;
        }

        double a = 0;

        @Override
        public void tick() {
        if (this.tickCount > maxTicks){
                this.remove(RemovalReason.KILLED);
        }
        this.hurtMarked = true;

        double X = getX();
        double Y = getY();
        double Z = getZ();

        if(tickCount <= 1){
                } else if (tickCount < 25) { // Ultima being dropped to target
                        // Start

                }else if (tickCount > 25) {  // EXPLOOOOOSION!!!!! (And damage)
                // Start
                setDeltaMovement(0,0,0);
                radius = Math.min((tickCount - 34) / 10F, 20);
                this.getBoundingBox().inflate(radius,radius,radius);


                }
                super.tick();
        }
        @Override
        protected void onHit(HitResult rtRes) {
                if (!level.isClientSide) {

                        EntityHitResult ertResult = null;
                        BlockHitResult brtResult = null;

                        if (rtRes instanceof EntityHitResult) {
                                ertResult = (EntityHitResult) rtRes;
                        }

                        if (rtRes instanceof BlockHitResult) {
                                brtResult = (BlockHitResult) rtRes;
                        }

                        if (ertResult != null && ertResult.getEntity() instanceof LivingEntity) {
                                if (tickCount > 25) {

                                LivingEntity target = (LivingEntity) ertResult.getEntity();

                                        if (target != getOwner()) {
                                                Party p = null;
                                                if (getOwner() != null) {
                                                        p = ModCapabilities.getWorld(getOwner().level).getPartyFromMember(getOwner().getUUID());
                                                }
                                                if (p == null || (p.getMember(target.getUUID()) == null || p.getFriendlyFire())) { //If caster is not in a party || the party doesn't have the target in it || the party has FF on
                                                        float dmg = this.getOwner() instanceof Player ? DamageCalculation.getMagicDamage((Player) this.getOwner()) * 0.2F : 2;
                                                        target.invulnerableTime = 0;
                                                        target.hurt(DamageSource.thrown(this, this.getOwner()), dmg * dmgMult);
                                                }
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
