package online.remind.remind.handler;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.effect.DoomEffect;
import online.remind.remind.effect.ModMobEffectsRM;

import java.util.UUID;

@EventBusSubscriber(
        modid = KingdomKeysReMind.MODID,
        bus = EventBusSubscriber.Bus.GAME
)
public class DoomEffectHandler {

    /*
     * Normal removal:
     *
     * /effect clear
     * milk
     * Esuna
     * other effect-removal mechanics
     */
    @SubscribeEvent
    public static void onEffectRemoved(
            MobEffectEvent.Remove event
    ) {

        if (event.getEffect() == null) {
            return;
        }

        if (event.getEffect().value()
                != ModMobEffectsRM.DOOM) {
            return;
        }

        DoomEffect.removeCountdown(
                event.getEntity()
        );
    }

    /*
     * Natural expiration cleanup.
     */
    @SubscribeEvent
    public static void onEffectExpired(
            MobEffectEvent.Expired event
    ) {

        if (event.getEffectInstance() == null) {
            return;
        }

        if (event.getEffectInstance()
                .getEffect()
                .value()
                != ModMobEffectsRM.DOOM) {
            return;
        }

        DoomEffect.removeCountdown(
                event.getEntity()
        );
    }

    /*
     * Failsafe.
     *
     * Every 5 ticks, make sure every Doom TextDisplay
     * still belongs to something that ACTUALLY has Doom.
     *
     * This prevents orphaned countdowns even if some
     * command/mod removes the effect without our expected
     * MobEffectEvent path firing.
     */
    @SubscribeEvent
    public static void onLevelTick(
            LevelTickEvent.Post event
    ) {

        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }

        if (level.getGameTime() % 5L != 0L) {
            return;
        }

        for (Entity entity : level.getAllEntities()) {

            if (!(entity instanceof Display.TextDisplay display)) {
                continue;
            }

            if (!display
                    .getPersistentData()
                    .hasUUID(
                            DoomEffect.DOOM_OWNER_UUID
                    )) {

                continue;
            }

            UUID ownerUUID =
                    display
                            .getPersistentData()
                            .getUUID(
                                    DoomEffect.DOOM_OWNER_UUID
                            );

            Entity owner =
                    level.getEntity(
                            ownerUUID
                    );

            /*
             * Owner died, despawned, etc.
             */
            if (!(owner instanceof LivingEntity living)
                    || !living.isAlive()) {

                display.discard();
                continue;
            }

            /*
             * Owner exists, but Doom is gone.
             *
             * This is the important /effect clear fallback.
             */
            if (!living.hasEffect(
                    ModMobEffectsRM.DOOM
            )) {

                display.discard();

                living.getPersistentData().remove(
                        DoomEffect.DOOM_DISPLAY_UUID
                );
            }
        }
    }
}