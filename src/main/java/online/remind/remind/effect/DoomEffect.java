package online.remind.remind.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.CustomData;

import java.util.UUID;

public class DoomEffect extends MobEffect {

    public static final String DOOM_DISPLAY_UUID =
            "kkremind_doom_display";

    public static final String DOOM_OWNER_UUID =
            "kkremind_doom_owner";

    /*
     * Change this to make the countdown larger/smaller.
     *
     * 1.0 = normal TextDisplay size
     * 1.75 = current Doom size
     * 2.0 = chunky
     */
    private static final float DOOM_TEXT_SCALE = 1.75F;

    public DoomEffect(MobEffectCategory pCategory, int pColor){
        super(pCategory, pColor);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(
            int duration,
            int amplifier
    ) {
        return true;
    }

    @Override
    public boolean applyEffectTick(
            LivingEntity entity,
            int amplifier
    ) {

        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return true;
        }

        MobEffectInstance doom =
                entity.getEffect(
                        ModMobEffectsRM.DOOM
                );

        if (doom == null) {

            removeCountdown(
                    entity
            );

            return true;
        }

        int remaining =
                doom.getDuration();

        int seconds =
                Math.max(
                        1,
                        (remaining + 19) / 20
                );

        /*
         * Create/update floating number.
         */
        updateCountdown(
                entity,
                serverLevel,
                seconds
        );

        /*
         * Once-per-second Doom pulse.
         */
        if (remaining % 20 == 0) {

            serverLevel.sendParticles(
                    ParticleTypes.REVERSE_PORTAL,
                    entity.getX(),
                    entity.getY()
                            + entity.getBbHeight() * 0.5D,
                    entity.getZ(),
                    8,
                    0.30D,
                    0.40D,
                    0.30D,
                    0.03D
            );

            serverLevel.playSound(
                    null,
                    entity.blockPosition(),
                    SoundEvents.NOTE_BLOCK_HAT.value(),
                    SoundSource.HOSTILE,
                    0.5F,
                    0.6F
            );
        }

        /*
         * Doom expires.
         */
        if (remaining <= 1) {

            removeCountdown(
                    entity
            );

            serverLevel.sendParticles(
                    ParticleTypes.LARGE_SMOKE,
                    entity.getX(),
                    entity.getY()
                            + entity.getBbHeight() * 0.5D,
                    entity.getZ(),
                    30,
                    0.45D,
                    0.60D,
                    0.45D,
                    0.08D
            );

            serverLevel.sendParticles(
                    ParticleTypes.REVERSE_PORTAL,
                    entity.getX(),
                    entity.getY()
                            + entity.getBbHeight() * 0.5D,
                    entity.getZ(),
                    25,
                    0.40D,
                    0.50D,
                    0.40D,
                    0.10D
            );

            serverLevel.playSound(
                    null,
                    entity.blockPosition(),
                    SoundEvents.WITHER_DEATH,
                    SoundSource.HOSTILE,
                    0.8F,
                    0.8F
            );

            entity.invulnerableTime = 0;

            entity.hurt(
                    entity.damageSources().genericKill(),
                    Float.MAX_VALUE
            );
        }

        return true;
    }

    private static void updateCountdown(
            LivingEntity target,
            ServerLevel level,
            int seconds
    ) {

        Display.TextDisplay display =
                getCountdownDisplay(
                        target,
                        level
                );

        /*
         * Create the TextDisplay if there isn't one yet.
         */
        if (display == null) {

            display =
                    EntityType.TEXT_DISPLAY.create(
                            level
                    );

            if (display == null) {
                return;
            }

            display.setNoGravity(true);
            display.setInvulnerable(true);
            display.setSilent(true);

            /*
             * Configure:
             *
             * - camera-facing
             * - transparent background
             * - larger scale
             * - visible through models
             */
            configureDisplay(
                    display,
                    seconds
            );

            /*
             * TextDisplay itself doesn't have the extra
             * ArmorStand nametag offset, so this can sit
             * much closer to the mob's actual head.
             */
            display.setPos(
                    target.getX(),
                    target.getY()
                            + target.getBbHeight()
                            + 0.25D,
                    target.getZ()
            );

            display.getPersistentData().putUUID(
                    DOOM_OWNER_UUID,
                    target.getUUID()
            );

            level.addFreshEntity(
                    display
            );

            target.getPersistentData().putUUID(
                    DOOM_DISPLAY_UUID,
                    display.getUUID()
            );
        }

        /*
         * Make the number follow the mob.
         */
        display.setPos(
                target.getX(),
                target.getY()
                        + target.getBbHeight()
                        + 0.25D,
                target.getZ()
        );

        /*
         * Update 5 -> 4 -> 3 -> 2 -> 1.
         */
        updateDisplayText(
                display,
                seconds
        );
    }

    private static void configureDisplay(
            Display.TextDisplay display,
            int seconds
    ) {

        CompoundTag tag =
                new CompoundTag();

        /*
         * Always face the player's camera.
         */
        tag.putString(
                "billboard",
                "center"
        );

        /*
         * Let it render from farther away.
         */
        tag.putFloat(
                "view_range",
                4.0F
        );

        /*
         * No gray rectangle behind it.
         */
        tag.putInt(
                "background",
                0
        );

        /*
         * Slight drop shadow makes white numbers
         * readable against bright skies.
         */
        tag.putBoolean(
                "shadow",
                true
        );

        /*
         * Keep Doom readable.
         */
        tag.putBoolean(
                "see_through",
                true
        );

        tag.putInt(
                "line_width",
                100
        );

        /*
         * Size control.
         */
        CompoundTag transformation =
                new CompoundTag();

        transformation.put(
                "translation",
                floatList(
                        0.0F,
                        0.0F,
                        0.0F
                )
        );

        transformation.put(
                "scale",
                floatList(
                        DOOM_TEXT_SCALE,
                        DOOM_TEXT_SCALE,
                        DOOM_TEXT_SCALE
                )
        );

        transformation.put(
                "left_rotation",
                floatList(
                        0.0F,
                        0.0F,
                        0.0F,
                        1.0F
                )
        );

        transformation.put(
                "right_rotation",
                floatList(
                        0.0F,
                        0.0F,
                        0.0F,
                        1.0F
                )
        );

        tag.put(
                "transformation",
                transformation
        );

        tag.putString(
                "text",
                getTextJson(seconds)
        );

        /*
         * TextDisplay's public Mojmap setters for these fields
         * aren't exposed, so apply its normal entity NBT.
         */
        CustomData.of(tag)
                .loadInto(display);
    }

    private static void updateDisplayText(
            Display.TextDisplay display,
            int seconds
    ) {

        CompoundTag tag =
                new CompoundTag();

        tag.putString(
                "text",
                getTextJson(seconds)
        );

        CustomData.of(tag)
                .loadInto(display);
    }

    private static String getTextJson(
            int seconds
    ) {

        /*
         * Color escalation.
         */
        String color;

        if (seconds <= 1) {

            color = "red";

        } else if (seconds == 2) {

            color = "light_purple";

        } else {

            color = "white";
        }

        return """
                {
                  "text":"☠ %d",
                  "color":"%s",
                  "bold":true,
                  "italic":false
                }
                """.formatted(
                seconds,
                color
        );
    }

    private static ListTag floatList(
            float... values
    ) {

        ListTag list =
                new ListTag();

        for (float value : values) {

            list.add(
                    FloatTag.valueOf(value)
            );
        }

        return list;
    }

    private static Display.TextDisplay getCountdownDisplay(
            LivingEntity target,
            ServerLevel level
    ) {

        if (!target
                .getPersistentData()
                .hasUUID(DOOM_DISPLAY_UUID)) {

            return null;
        }

        UUID uuid =
                target
                        .getPersistentData()
                        .getUUID(
                                DOOM_DISPLAY_UUID
                        );

        Entity entity =
                level.getEntity(uuid);

        if (entity instanceof Display.TextDisplay textDisplay
                && textDisplay.isAlive()) {

            return textDisplay;
        }

        /*
         * This also cleans up an old ArmorStand countdown
         * from the previous implementation.
         */
        if (entity != null) {
            entity.discard();
        }

        target.getPersistentData().remove(
                DOOM_DISPLAY_UUID
        );

        return null;
    }

    public static void removeCountdown(
            LivingEntity target
    ) {

        if (!(target.level() instanceof ServerLevel level)) {
            return;
        }

        if (!target
                .getPersistentData()
                .hasUUID(DOOM_DISPLAY_UUID)) {

            return;
        }

        UUID uuid =
                target
                        .getPersistentData()
                        .getUUID(
                                DOOM_DISPLAY_UUID
                        );

        Entity entity =
                level.getEntity(uuid);

        if (entity != null) {
            entity.discard();
        }

        target.getPersistentData().remove(
                DOOM_DISPLAY_UUID
        );
    }
}