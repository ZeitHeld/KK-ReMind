package online.remind.remind.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.capabilities.GlobalDataRM;
import online.remind.remind.capabilities.ModDataRM;
import online.remind.remind.effect.ModMobEffectsRM;
import online.remind.remind.entity.attacks.ChaosBladeCollider;
import online.remind.remind.integration.epicfight.EpicFightEvents;
import online.remind.remind.magic.attacks.attackChaosBlade;

public class ChaosBladeRC extends ReactionCommand {

    public ChaosBladeRC(ResourceLocation registryName, boolean constantCheck) {
        super(registryName, constantCheck, 20, 0xFFD700);
    }

    @Override
    public void onUse(
            Player player,
            LivingEntity livingEntity,
            LivingEntity livingEntity1
    ) {

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        GlobalDataRM globalData =
                ModDataRM.getGlobal(player);

        if (globalData == null) {
            return;
        }

        MobEffectInstance chain =
                player.getEffect(
                        ModMobEffectsRM.CHAOS_BLADE_CHAIN
                );

        if (chain == null) {
            return;
        }

        int chainStep =
                chain.getAmplifier();

        /*
         * Retrieve the ORIGINAL Chaos Blade target.
         *
         * The chain keeps attacking the same enemy.
         */
        LivingEntity target =
                attackChaosBlade.getStoredTarget(
                        serverPlayer
                );

        /*
         * Target died, despawned, changed dimensions, etc.
         *
         * Kill the chain cleanly.
         */
        if (target == null) {

            player.removeEffect(
                    ModMobEffectsRM.CHAOS_BLADE_CHAIN
            );

            attackChaosBlade.clearChaosBladeData(
                    serverPlayer
            );

            return;
        }

        /*
         * Consume the current RC window.
         *
         * ChaosBladeCollider will reopen it if this
         * follow-up successfully connects.
         */
        player.removeEffect(
                ModMobEffectsRM.CHAOS_BLADE_CHAIN
        );

        /*
         * Epic Fight is only handling the animation.
         *
         * Keep this guarded because EFM is optional.
         *
         * For now this can use the same dash animation
         * hook as Sonic Blade. We can give Chaos Blade
         * its own animation hook afterward.
         */
        if (KingdomKeysReMind.efmLoaded) {

            EpicFightEvents.playSonicBladeAnimation(
                    serverPlayer
            );
        }

        /*
         * THIS is the major Chaos Blade difference:
         *
         * teleport relative to the stored target
         * THEN dash directly through them.
         */
        attackChaosBlade.startChaosBladeStep(
                serverPlayer,
                target,
                chainStep
        );

        /*
         * Use the damage calculated by the ORIGINAL cast.
         *
         * Don't fall back to vanilla ATTACK_DAMAGE or the
         * follow-ups will become chip damage.
         */
        float dmg =
                serverPlayer
                        .getPersistentData()
                        .getFloat(
                                attackChaosBlade.CHAOS_BLADE_DAMAGE
                        );

        /*
         * Spawn the collider for this specific chain step.
         */
        ChaosBladeCollider collider =
                new ChaosBladeCollider(
                        serverPlayer.level(),
                        serverPlayer,
                        dmg,
                        chainStep
                );

        serverPlayer
                .level()
                .addFreshEntity(
                        collider
                );

        /*
         * Prevent the same input from activating another RC
         * immediately.
         */
        globalData.setRCCooldownTicks(4);
    }

    @Override
    public boolean conditionsToAppear(
            Player player,
            LivingEntity livingEntity
    ) {

        GlobalDataRM globalData =
                ModDataRM.getGlobal(player);

        if (globalData == null) {
            return false;
        }

        if (globalData.getRCCooldownTicks() > 0) {
            return false;
        }

        return player.hasEffect(
                ModMobEffectsRM.CHAOS_BLADE_CHAIN
        );
    }
}