package online.remind.remind.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.entity.enemies.BombEntity;
import online.remind.remind.entity.enemies.CactuarEntity;
import online.remind.remind.entity.enemies.TonberryEntity;

@EventBusSubscriber(
        modid = KingdomKeysReMind.MODID,
        bus = EventBusSubscriber.Bus.MOD
)
public class ModSpawnPlacementsRM {

    private static final int LAVA_SEARCH_RADIUS = 8;

    private ModSpawnPlacementsRM() {
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ModEntitiesRM.TYPE_CACTUAR.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CactuarEntity::checkCactuarSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(
                ModEntitiesRM.TYPE_BOMB.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ModSpawnPlacementsRM::checkBombFamilySpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(
                ModEntitiesRM.TYPE_GRENADE.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ModSpawnPlacementsRM::checkBombFamilySpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );

        event.register(
                ModEntitiesRM.TYPE_VOLCANO.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ModSpawnPlacementsRM::checkBombFamilySpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    private static boolean checkBombFamilySpawnRules(
            EntityType<? extends BombEntity> type,
            ServerLevelAccessor level,
            MobSpawnType spawnType,
            BlockPos pos,
            RandomSource random
    ) {
        if (!Monster.checkAnyLightMonsterSpawnRules(
                type,
                level,
                spawnType,
                pos,
                random
        )) {
            return false;
        }

        if (level.getLevel()
                .dimension()
                .equals(Level.NETHER)) {
            return true;
        }

        return hasNearbyLava(
                level,
                pos,
                LAVA_SEARCH_RADIUS
        );
    }

    private static boolean hasNearbyLava(
            ServerLevelAccessor level,
            BlockPos origin,
            int radius
    ) {
        BlockPos min =
                origin.offset(
                        -radius,
                        -radius,
                        -radius
                );

        BlockPos max =
                origin.offset(
                        radius,
                        radius,
                        radius
                );

        for (BlockPos checkPos :
                BlockPos.betweenClosed(min, max)) {

            var fluidState =
                    level.getFluidState(checkPos);

            if (fluidState.is(FluidTags.LAVA)) {

                return true;
            }
        }

        return false;
    }
}
