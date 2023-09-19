package online.magicksaddon.magicsaddonmod.entity.magic;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesMA;

public class CometEntity extends ThrowableProjectile {
        int maxTicks = 200;
        float dmgMult = 1;


        public CometEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
                super(type, world);
                this.blocksBuilding = true;
        }

        public CometEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
                super(ModEntitiesMA.TYPE_RUIN.get(), world);
        }

        public CometEntity(Level world) {
                super(ModEntitiesMA.TYPE_RUIN.get(), world);
                this.blocksBuilding = true;
        }

        public CometEntity(Level world, LivingEntity player, float dmgMult) {
                super(ModEntitiesMA.TYPE_RUIN.get(), player, world);
                this.dmgMult = dmgMult;
        }

        @Override
        protected void defineSynchedData() {

        }
}
