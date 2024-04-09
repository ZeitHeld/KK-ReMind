package online.magicksaddon.magicsaddonmod.entity.magic;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesRM;
import org.joml.Vector3f;

import java.util.List;

import static net.minecraft.network.chat.ChatType.SAY_COMMAND;

public class WarpEntity extends ThrowableProjectile {
    int maxTicks = 100;
    float chanceMulti = 1;

    public WarpEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
    }

    public WarpEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(ModEntitiesRM.TYPE_WARP.get(), world);
    }

    public WarpEntity(Level world) {
        super(ModEntitiesRM.TYPE_WARP.get(), world);
        this.blocksBuilding = true;
    }

    public WarpEntity(Level world, Player player, float dmgMult) {
        super(ModEntitiesRM.TYPE_WARP.get(), player, world);
        this.chanceMulti = dmgMult;
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

        if (tickCount > 2)
            level().addParticle(ParticleTypes.DRAGON_BREATH, getX(), getY(), getZ(), 0, 0, 0);

        super.tick();
    }


    // This is where the fun begins.
    @Override
    protected void onHit(HitResult rtRes) {
        if (!level().isClientSide) {
            float radius = 4F;
            double X = getX();
            double Y = getY();
            double Z = getZ();

            // Random Chance Stuff
            double rand = Math.floor(Math.random() * (51 -1)) +1;
            //System.out.println(rand);

            // Pretty Stuff

            for (int t = 1; t < 360; t += 20) {
                for (int s = 1; s < 360 ; s += 20) {
                    double x = X + (radius * Math.cos(Math.toRadians(s)) * Math.sin(Math.toRadians(t)));
                    double z = Z + (radius * Math.sin(Math.toRadians(s)) * Math.sin(Math.toRadians(t)));
                    double y = Y + (radius * Math.cos(Math.toRadians(t)));
                    ((ServerLevel) level()).sendParticles(ParticleTypes.DRAGON_BREATH, x, y+1, z, 1, 0,0,0, 0);
                    ((ServerLevel) level()).sendParticles(new DustParticleOptions(new Vector3f(0F,0F,0F),6F),x,y+1 ,z,1,0,0,0,0);
                    ((ServerLevel) level()).sendParticles(new DustParticleOptions(new Vector3f(0.45F,0.35F,0F),6F),x,y+1 ,z,1,0,0,0,0);

                }
            }

            IWorldCapabilities worldData = ModCapabilities.getWorld(level());
            if (getOwner() != null && worldData != null) {
                List<Entity> list = level().getEntities(getOwner(), getBoundingBox().inflate(radius));
                Party casterParty = worldData.getPartyFromMember(getOwner().getUUID());

                if(casterParty != null && !casterParty.getFriendlyFire()) {
                    for(Party.Member m : casterParty.getMembers()) {
                        list.remove(level().getPlayerByUUID(m.getUUID()));
                    }
                } else {
                    list.remove(getOwner());
                }

                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Entity e = (Entity) list.get(i);
                        if (e instanceof LivingEntity) {

                            if(Utils.isHostile(e) || e instanceof ServerPlayer) {
                                if(e instanceof ServerPlayer){
                                    if(rand >= 45 || rand <= 5){
                                        //e.teleportRelative(0,-200,0);
                                        e.kill();
                                        e.shouldRender(0,0,0);
                                        e.level().playSound(null, e.blockPosition(), ModSoundsRM.WARPHITPLAYER.get(), SoundSource.PLAYERS,1F,1F);
                                    }
                                } else {
                                    if(rand >= 40 || rand <= 20) {
                                        e.kill();
                                        }
                                }
                            }

                        }
                    }
                }
                remove(RemovalReason.KILLED);
            }
        }
    }
}

