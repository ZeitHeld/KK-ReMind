package online.remind.remind.magic.attacks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.entity.attacks.SlotEdgeCollider;
import online.remind.remind.integration.epicfight.RMIntegrationHooks;

public class attackSlotEdge extends Magic {

    public attackSlotEdge(ResourceLocation registryName, boolean hasToSelect, int tier, ResourceLocation gmAbility) {
        super(registryName, hasToSelect, gmAbility);
setTier(tier);
    }

    @Override
    public void magicUse(LivingEntity player, LivingEntity caster, float fullMPBlastMult, LivingEntity lockOnEntity) {


        float dmg = switch (getTier()) {
            case 0 -> casterStrengthStat(caster) * 0.85F;
            case 1 -> casterStrengthStat(caster) * 1.0F;
            case 2 -> casterStrengthStat(caster) * 1.15F;
            default -> casterStrengthStat(caster) * 0.85F;
        };

        dmg *= fullMPBlastMult;

        launchSlotEdgeDash(caster, 0);

        SlotEdgeCollider slotEdge = new SlotEdgeCollider(
                player.level(),
                caster,
                dmg,
                0
        );

        caster.level().addFreshEntity(slotEdge);

        // Epic Fight only animates players
        if (caster instanceof Player p) {
            RMIntegrationHooks.playHeavyCommandAnimation(p, "slot_edge", 0);
        }
    }

    private void launchSlotEdgeDash(LivingEntity caster, int chainStep) {
        double speed = switch (chainStep) {
            case 0 -> 1.55D;
            case 1 -> 1.75D;
            case 2 -> 1.95D;
            default -> 1.55D;
        };

        double jump = 0.25D;
        double yawRad = Math.toRadians(caster.getYRot());
        double dx = -Math.sin(yawRad) * speed;
        double dz = Math.cos(yawRad) * speed;

        caster.hurtMarked = true;
        caster.fallDistance = 0.0F;

        if (KingdomKeysReMind.efmLoaded) {
            caster.setDeltaMovement(dx / 2.25D, jump, dz / 2.25D);
        } else {
            caster.setDeltaMovement(dx, jump, dz);
        }
    }

    @Override
    public void playMagicCastSound(LivingEntity player, LivingEntity caster) {
        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.PLAYER_ATTACK_SWEEP,
                SoundSource.PLAYERS,
                1.0F,
                1.15F
        );
    }
}