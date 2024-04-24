package online.magicksaddon.magicsaddonmod.entity.reactioncommand;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesRM;
import org.joml.Vector3f;

public class DualShotEntity extends ThrowableProjectile {
    int maxTicks = 100;
    float dmgMult = 1;
    static double a = 0;
    LivingEntity lockOnEntity;
    public DualShotEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public DualShotEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntitiesRM.TYPE_DRAIN.get(), world);
    }

    public DualShotEntity(Level world) {
        super(ModEntitiesRM.TYPE_DRAIN.get(), world);
        this.blocksBuilding = true;
    }

    public DualShotEntity(Level world, LivingEntity player, float dmgMult, LivingEntity lockOnEntity) {
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


        if(tickCount > 0) {

            // Rotate

            //while (a < 0) {
                a = 1800;
                double r = 0.7D;

                double cx = getX() + 1;
                double cy = getY() + 1;
                double cz = getZ() + 1;

                a -= 5;
                double x = cx + (r * Math.cos(Math.toRadians(a)));
                double z = cz + (r * Math.sin(Math.toRadians(a)));

                double y = cy + (r * Math.sin(Math.toRadians(a)));
                double y2 = cy + (r * Math.sin(Math.toRadians(-a)));

                double x2 = cx + (r * Math.cos(Math.toRadians(-a)));
                double z2 = cz + (r * Math.sin(Math.toRadians(-a)));


                level().addParticle(new DustParticleOptions(new Vector3f(1F,1F,1F), 1F), x, y + level().random.nextDouble() - 0.2D, z, 0, 1,0);
                level().addParticle(new DustParticleOptions(new Vector3f(0.25F,0F,0.45F), 1F), x2, y2, z2, 0, 1, 0);
                //level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(1F, 1F, 1F), 1F), getX() + level().random.nextDouble() - 0.5D, getY(), getZ() + level().random.nextDouble() - 0.5D, 0, 0, 0);
                //level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.2F, 0.2F, 0.2F), 1F), getX() + level().random.nextDouble() - 0.5D, getY(), getZ() + level().random.nextDouble() - 0.5D, 0, 0, 0);
            //}
            super.tick();
        }
    }

}
