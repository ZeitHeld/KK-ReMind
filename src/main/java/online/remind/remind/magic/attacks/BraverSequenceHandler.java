package online.remind.remind.magic.attacks;

import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.client.sound.ModSoundsRM;
import online.remind.remind.integration.BraverAnimationBridge;


import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class BraverSequenceHandler {

    private static final double MAX_RANGE = 7.0D;

    private static final Map<UUID, BraverState> ACTIVE =
            new HashMap<>();

    private static final Set<UUID> SCRIPTED_DAMAGE =
            new HashSet<>();

    private record BraverState(
            UUID targetId,
            float damage,
            int tick
    ) {}

    // ------------------------------------------------------------
    // Start Braver
    // ------------------------------------------------------------
    public static void start(
            ServerPlayer player,
            LivingEntity target,
            float damage
    ) {
        if (target == null || !target.isAlive()) {
            return;
        }

        // Don't launch across the map
        if (player.distanceToSqr(target) > MAX_RANGE * MAX_RANGE) {
            return;
        }

        ACTIVE.put(
                player.getUUID(),
                new BraverState(
                        target.getUUID(),
                        damage,
                        0
                )
        );

        System.out.println(
                "Braver sequence started -> "
                        + target.getName().getString()
        );
    }

    // ------------------------------------------------------------
    // Sequence
    // ------------------------------------------------------------
    @SubscribeEvent
    public static void onPlayerTick(
            PlayerTickEvent.Post event
    ) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        BraverState state =
                ACTIVE.get(player.getUUID());

        if (state == null) {
            return;
        }

        ServerLevel level =
                player.serverLevel();

        Entity entity =
                level.getEntity(state.targetId());

        // Target died/disappeared
        if (!(entity instanceof LivingEntity target)
                || !target.isAlive()) {

            ACTIVE.remove(player.getUUID());
            return;
        }

        int tick = state.tick() + 1;

        // Keep fall damage from accumulating during the move
        player.fallDistance = 0.0F;

        // ------------------------------------------------------------
        // Tick 3: LEAP
        // ------------------------------------------------------------
        if (tick == 3) {
            leapTowardTarget(
                    player,
                    target
            );

            System.out.println("Braver -> LEAP");
        }

        // ------------------------------------------------------------
        // Tick 9: ABOVE TARGET
        // ------------------------------------------------------------
        if (tick == 9) {
            moveAboveTarget(
                    player,
                    target
            );

            System.out.println("Braver -> ABOVE TARGET");
        }

        // ------------------------------------------------------------
        // Tick 12: BEGIN SLAM
        // ------------------------------------------------------------
        if (tick == 12) {

            BraverAnimationBridge.play(
                    player,
                    1
            );

            beginSlam(
                    player,
                    target
            );

            spawnBraverSlashVfx(
                    level,
                    player,
                    target
            );

            System.out.println("Braver -> SLAM");
        }

        // ------------------------------------------------------------
        // Tick 15: IMPACT
        // ------------------------------------------------------------
        if (tick == 15) {
            landAtTarget(
                    player,
                    target
            );

            hitTarget(
                    player,
                    target,
                    state.damage()
            );

            spawnBraverImpactVfx(
                    level,
                    target
            );

            System.out.println("Braver -> IMPACT");
        }

        // ------------------------------------------------------------
        // End
        // ------------------------------------------------------------
        if (tick >= 24) {
            player.setDeltaMovement(Vec3.ZERO);
            player.fallDistance = 0.0F;

            ACTIVE.remove(player.getUUID());

            System.out.println("Braver -> END");
            return;
        }

        ACTIVE.put(
                player.getUUID(),
                new BraverState(
                        state.targetId(),
                        state.damage(),
                        tick
                )
        );
    }

    // ------------------------------------------------------------
    // Initial leap
    // ------------------------------------------------------------
    private static void leapTowardTarget(
            ServerPlayer player,
            LivingEntity target
    ) {
        Vec3 direction =
                target.position()
                        .subtract(player.position());

        Vec3 horizontal =
                new Vec3(
                        direction.x,
                        0.0D,
                        direction.z
                );

        if (horizontal.lengthSqr() > 0.001D) {
            horizontal =
                    horizontal.normalize();
        }

        /*
         * Forward movement + upward launch.
         *
         * Adjust these later for feel.
         */
        Vec3 velocity =
                horizontal.scale(0.65D)
                        .add(
                                0.0D,
                                0.75D,
                                0.0D
                        );

        player.setDeltaMovement(velocity);

        player.lookAt(
                EntityAnchorArgument.Anchor.EYES,
                target.getEyePosition()
        );
    }

    // ------------------------------------------------------------
    // Position above target
    // ------------------------------------------------------------
    private static void moveAboveTarget(
            ServerPlayer player,
            LivingEntity target
    ) {
        /*
         * Keep the player slightly in front of the target rather
         * than directly inside its hitbox.
         */
        Vec3 awayFromTarget =
                player.position()
                        .subtract(target.position());

        awayFromTarget =
                new Vec3(
                        awayFromTarget.x,
                        0.0D,
                        awayFromTarget.z
                );

        if (awayFromTarget.lengthSqr() < 0.001D) {
            awayFromTarget =
                    target.getLookAngle()
                            .multiply(
                                    -1.0D,
                                    0.0D,
                                    -1.0D
                            );
        }

        if (awayFromTarget.lengthSqr() > 0.001D) {
            awayFromTarget =
                    awayFromTarget.normalize();
        }

        Vec3 destination =
                target.position()
                        .add(
                                awayFromTarget.scale(0.8D)
                        )
                        .add(
                                0.0D,
                                target.getBbHeight() + 1.5D,
                                0.0D
                        );

        player.teleportTo(
                destination.x,
                destination.y,
                destination.z
        );

        player.setDeltaMovement(Vec3.ZERO);

        player.lookAt(
                EntityAnchorArgument.Anchor.EYES,
                target.getEyePosition()
        );
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundsRM.DASH.get(), SoundSource.PLAYERS, 1.5F, 1F);
    }

    // ------------------------------------------------------------
    // Downward attack
    // ------------------------------------------------------------
    private static void beginSlam(
            ServerPlayer player,
            LivingEntity target
    ) {
        player.lookAt(
                EntityAnchorArgument.Anchor.EYES,
                target.getEyePosition()
        );

        /*
         * Strong downward velocity.
         */
        player.setDeltaMovement(
                0.0D,
                -1.35D,
                0.0D
        );

    }




    // ------------------------------------------------------------
    // Final landing position
    // ------------------------------------------------------------
    private static void landAtTarget(
            ServerPlayer player,
            LivingEntity target
    ) {
        Vec3 awayFromTarget =
                player.position()
                        .subtract(target.position());

        awayFromTarget =
                new Vec3(
                        awayFromTarget.x,
                        0.0D,
                        awayFromTarget.z
                );

        if (awayFromTarget.lengthSqr() < 0.001D) {
            awayFromTarget =
                    target.getLookAngle()
                            .multiply(
                                    -1.0D,
                                    0.0D,
                                    -1.0D
                            );
        }

        if (awayFromTarget.lengthSqr() > 0.001D) {
            awayFromTarget =
                    awayFromTarget.normalize();
        }

        // Land close enough to visually connect the sword hit
        Vec3 destination =
                target.position()
                        .add(
                                awayFromTarget.scale(1.25D)
                        );

        player.teleportTo(
                destination.x,
                destination.y,
                destination.z
        );

        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0.0F;

        player.lookAt(
                EntityAnchorArgument.Anchor.EYES,
                target.getEyePosition()
        );
    }

    // ------------------------------------------------------------
    // Damage
    // ------------------------------------------------------------
    private static void hitTarget(
            ServerPlayer player,
            LivingEntity target,
            float damage
    ) {
        if (!target.isAlive()) {
            return;
        }

        UUID playerId =
                player.getUUID();

        SCRIPTED_DAMAGE.add(playerId);

        try {
            target.invulnerableTime = 0;

            target.hurt(
                    player.damageSources()
                            .playerAttack(player),
                    damage
            );

        } finally {
            SCRIPTED_DAMAGE.remove(playerId);
        }
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSoundsRM.CRITSLASH.get(), SoundSource.PLAYERS, 1.5F, 1F);

    }

    @SubscribeEvent
    public static void onIncomingDamage(
            LivingIncomingDamageEvent event
    ) {
        Entity attacker =
                event.getSource().getEntity();

        if (!(attacker instanceof ServerPlayer player)) {
            return;
        }

        BraverState state =
                ACTIVE.get(player.getUUID());

        if (state == null) {
            return;
        }

        // Only interfere with the Braver target.
        if (!event.getEntity().getUUID().equals(state.targetId())) {
            return;
        }

        // Our scripted damage is allowed.
        if (SCRIPTED_DAMAGE.contains(player.getUUID())) {
            return;
        }

        // EFM attack-animation damage during Braver gets suppressed.
        event.setCanceled(true);
    }

    private static void spawnBraverSlashVfx(
            ServerLevel level,
            ServerPlayer player,
            LivingEntity target
    ) {
        double x =
                (player.getX() + target.getX()) * 0.5D;

        double y =
                target.getY()
                        + target.getBbHeight() * 1.25D;

        double z =
                (player.getZ() + target.getZ()) * 0.5D;

        // Main sword-swing streak
        level.sendParticles(
                ParticleTypes.SWEEP_ATTACK,
                x,
                y,
                z,
                2,
                0.15D,
                0.35D,
                0.15D,
                0.0D
        );

        // Small bright sparks around the descending blade
        level.sendParticles(
                ParticleTypes.CRIT,
                x,
                y,
                z,
                10,
                0.20D,
                0.40D,
                0.20D,
                0.08D
        );
    }

    private static void spawnBraverImpactVfx(
            ServerLevel level,
            LivingEntity target
    ) {
        double x = target.getX();
        double y = target.getY() + 0.15D;
        double z = target.getZ();

        // Bright impact flash
        level.sendParticles(
                ParticleTypes.FLASH,
                x,
                y + 0.5D,
                z,
                1,
                0.0D,
                0.0D,
                0.0D,
                0.0D
        );

        // Heavy hit burst
        level.sendParticles(
                ParticleTypes.CRIT,
                x,
                y + 0.5D,
                z,
                24,
                0.45D,
                0.35D,
                0.45D,
                0.18D
        );

        // Ground dust
        level.sendParticles(
                ParticleTypes.CLOUD,
                x,
                y,
                z,
                14,
                0.55D,
                0.08D,
                0.55D,
                0.08D
        );

        // Small shockwave ring
        int points = 16;
        double radius = 1.25D;

        for (int i = 0; i < points; i++) {
            double angle =
                    (Math.PI * 2.0D * i) / points;

            double px =
                    x + Math.cos(angle) * radius;

            double pz =
                    z + Math.sin(angle) * radius;

            level.sendParticles(
                    ParticleTypes.CLOUD,
                    px,
                    y + 0.05D,
                    pz,
                    1,
                    0.0D,
                    0.02D,
                    0.0D,
                    0.02D
            );
        }
    }
}