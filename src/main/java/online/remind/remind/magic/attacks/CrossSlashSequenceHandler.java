package online.remind.remind.magic.attacks;

import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.client.sound.ModSoundsRM;
import online.remind.remind.entity.attacks.CrossSlashEffectEntity;
import online.remind.remind.integration.CrossSlashAnimationBridge;

import java.util.*;

@EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class CrossSlashSequenceHandler {

    private static final Map<UUID, CrossSlashState> ACTIVE = new HashMap<>();
    private static final Set<UUID> SCRIPTED_DAMAGE = new HashSet<>();

    private record CrossSlashState(
            UUID targetId,
            UUID effectId,
            float damage,
            int tick
    ) {}

    public static void start(
            ServerPlayer player,
            LivingEntity target,
            float damage
    ) {
        ACTIVE.put(
                player.getUUID(),
                new CrossSlashState(
                        target.getUUID(),
                        null,
                        damage,
                        0
                )
        );

        CrossSlashAnimationBridge.play(player, 0);

        System.out.println(
                "Cross Slash sequence started -> "
                        + target.getName().getString()
        );
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        CrossSlashState state = ACTIVE.get(player.getUUID());

        if (state == null) {
            return;
        }

        ServerLevel level = player.serverLevel();

        Entity entity = level.getEntity(state.targetId());

        // Target disappeared or died
        if (!(entity instanceof LivingEntity target) || !target.isAlive()) {
            ACTIVE.remove(player.getUUID());
            return;
        }

        int tick = state.tick() + 1;

        // ------------------------------------------------------------
        // Tick 4: Move into position
        // ------------------------------------------------------------
        if (tick == 4) {

            if (!moveToTarget(player, target)) {
                removeEffect(level, state);
                ACTIVE.remove(player.getUUID());

                System.out.println("Cross Slash -> TARGET TOO FAR");
                return;
            }

            System.out.println("Cross Slash -> POSITION");
        }

        // ------------------------------------------------------------
        // Tick 8: Slash 1
        // ------------------------------------------------------------
        if (tick == 8) {
            CrossSlashAnimationBridge.play(player, 1);
            System.out.println("Cross Slash -> HIT 1");

            hitTarget(
                    player,
                    target,
                    state.damage() * 0.5F
            );

            CrossSlashEffectEntity effect =
                    CrossSlashEffectEntity.spawn(
                            level,
                            target,
                            player
                    );

            UUID effectId =
                    effect != null
                            ? effect.getUUID()
                            : null;

            ACTIVE.put(
                    player.getUUID(),
                    new CrossSlashState(
                            state.targetId(),
                            effectId,
                            state.damage(),
                            tick
                    )
            );

            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundsRM.SLASH.get(), SoundSource.PLAYERS, 1F, 1F);


            return;
        }

        // ------------------------------------------------------------
        // Tick 24: Slash 2
        // ------------------------------------------------------------
        if (tick == 24) {
            CrossSlashAnimationBridge.play(player, 2);
            System.out.println("Cross Slash -> HIT 2");

            hitTarget(
                    player,
                    target,
                    state.damage() * 0.55f
            );

            if (state.effectId() != null) {
                Entity effectEntity =
                        level.getEntity(state.effectId());

                if (effectEntity instanceof CrossSlashEffectEntity effect) {
                    effect.setStage(2);
                }
            }

            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundsRM.SLASH.get(), SoundSource.PLAYERS, 1F, 1F);

        }

        // ------------------------------------------------------------
        // Tick 42: Slash 3
        // ------------------------------------------------------------
        if (tick == 42) {
            CrossSlashAnimationBridge.play(player, 3);
            System.out.println("Cross Slash -> HIT 3");

            hitTarget(
                    player,
                    target,
                    state.damage() * 0.65F
            );

            if (state.effectId() != null) {
                Entity effectEntity =
                        level.getEntity(state.effectId());

                if (effectEntity instanceof CrossSlashEffectEntity effect) {
                    effect.setStage(3);
                }
            }

            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundsRM.SLASH.get(), SoundSource.PLAYERS, 1F, 1F);

        }

        // ------------------------------------------------------------
        // End sequence
        // ------------------------------------------------------------
        if (tick >= 60) {

            if (state.effectId() != null) {
                Entity effectEntity =
                        level.getEntity(state.effectId());

                if (effectEntity instanceof CrossSlashEffectEntity effect) {
                    effect.discard();
                }
            }

            ACTIVE.remove(player.getUUID());

            System.out.println("Cross Slash -> END");
            return;
        }

        ACTIVE.put(
                player.getUUID(),
                new CrossSlashState(
                        state.targetId(),
                        state.effectId(),
                        state.damage(),
                        tick
                )
        );
    }

    private static boolean moveToTarget(
            ServerPlayer player,
            LivingEntity target
    ) {
        double maxRange = 6.0D;

        // Too far away: do not teleport across the map
        if (player.distanceToSqr(target) > maxRange * maxRange) {
            return false;
        }

        Vec3 awayFromTarget =
                player.position()
                        .subtract(target.position());

        if (awayFromTarget.lengthSqr() < 0.001D) {
            awayFromTarget = target.getLookAngle().scale(-1.0D);
        }

        awayFromTarget = awayFromTarget.normalize();

        // End up about 1.6 blocks from the target
        Vec3 destination =
                target.position()
                        .add(awayFromTarget.scale(1.6D));

        player.teleportTo(
                destination.x,
                destination.y,
                destination.z
        );

        player.lookAt(
                EntityAnchorArgument.Anchor.EYES,
                target.getEyePosition()
        );

        player.setDeltaMovement(Vec3.ZERO);

        return true;
    }

    private static void hitTarget(
            ServerPlayer player,
            LivingEntity target,
            float damage
    ) {
        if (!target.isAlive()) {
            return;
        }

        UUID playerId = player.getUUID();

        SCRIPTED_DAMAGE.add(playerId);

        try {
            target.invulnerableTime = 0;

            target.hurt(
                    player.damageSources().playerAttack(player),
                    damage
            );
        } finally {
            SCRIPTED_DAMAGE.remove(playerId);
        }
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        Entity attacker = event.getSource().getEntity();

        if (!(attacker instanceof ServerPlayer player)) {
            return;
        }

        UUID playerId = player.getUUID();

        if (!ACTIVE.containsKey(playerId)) {
            return;
        }


        if (SCRIPTED_DAMAGE.contains(playerId)) {
            return;
        }


        event.setCanceled(true);
    }

    private static void removeEffect(
            ServerLevel level,
            CrossSlashState state
    ) {
        if (state.effectId() == null) {
            return;
        }

        Entity effectEntity =
                level.getEntity(state.effectId());

        if (effectEntity instanceof CrossSlashEffectEntity effect) {
            effect.discard();
        }
    }
}