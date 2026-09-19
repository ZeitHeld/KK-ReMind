package online.remind.remind.integration.epicfight.init;

import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.integration.epicfight.init.KKAnimations;
import online.kingdomkeys.kingdomkeys.integration.epicfight.skills.KKSkills;
import online.remind.remind.KingdomKeysReMind;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.ex_cap.data.Moveset;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.registry.deferred.MovesetRegister;
import yesman.epicfight.registry.deferred.holders.DeferredMoveset;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class EpicRMMovesets {
    public static final MovesetRegister MOVESETS = MovesetRegister.create(KingdomKeysReMind.MODID);


    public static final DeferredMoveset TWILIGHT_FORM_MOVESET = MOVESETS.registerMoveset("twilight_form", () -> Moveset.builder()
            .addComboAttacks(KKAnimations.FINAL_AUTO1, KKAnimations.FINAL_AUTO1, KKAnimations.FINAL_AUTO1,
                    Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .addLivingMotionModifier(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .addLivingMotionModifier(LivingMotions.IDLE, KKAnimations.FINAL_FORM_IDLE)
            .addLivingMotionModifier(LivingMotions.RUN, KKAnimations.FINAL_FORM_IDLE)
            .addLivingMotionModifier(LivingMotions.WALK, KKAnimations.FINAL_FORM_IDLE)
            .addMountAttacks(Animations.SWORD_MOUNT_ATTACK)
            .setPassiveSkill(KKSkills.comboExtender)
            .addGuardAnimations(GuardSkill.BlockType.GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.ADVANCED_GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.GUARD_BREAK, Animations.BIPED_COMMON_NEUTRALIZED));


    public static final DeferredMoveset LIGHT_FORM_MOVESET = MOVESETS.registerMoveset("light_form", () -> Moveset.builder()
            .addComboAttacks(KKAnimations.FINAL_AUTO1, KKAnimations.FINAL_AUTO1, Animations.SWORD_AUTO2,
                    Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .addLivingMotionModifier(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .addLivingMotionModifier(LivingMotions.RUN, KKAnimations.ROXAS_RUN)
            .addLivingMotionModifier(LivingMotions.WALK, KKAnimations.ROXAS_RUN)
            .addLivingMotionModifier(LivingMotions.IDLE, KKAnimations.AQUA_IDLE)
            .addMountAttacks(Animations.SWORD_MOUNT_ATTACK)
            .setPassiveSkill(KKSkills.comboExtender)
            .addGuardAnimations(GuardSkill.BlockType.GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.ADVANCED_GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.GUARD_BREAK, Animations.BIPED_COMMON_NEUTRALIZED));


    public static final DeferredMoveset DARK_FORM_MOVESET = MOVESETS.registerMoveset("dark_form", () -> Moveset.builder()
            .addComboAttacks(KKAnimations.FINAL_AUTO1, KKAnimations.FINAL_AUTO1, Animations.SWORD_AUTO1,
                    Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .addLivingMotionModifier(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .addLivingMotionModifier(LivingMotions.RUN, KKAnimations.ROXAS_RUN)
            .addLivingMotionModifier(LivingMotions.WALK, KKAnimations.ROXAS_RUN)
            .addLivingMotionModifier(LivingMotions.IDLE, KKAnimations.AQUA_IDLE)
            .addMountAttacks(Animations.SWORD_MOUNT_ATTACK)
            .setPassiveSkill(KKSkills.comboExtender)
            .addGuardAnimations(GuardSkill.BlockType.GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.ADVANCED_GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.GUARD_BREAK, Animations.BIPED_COMMON_NEUTRALIZED));

    public static final DeferredMoveset  XEPHIRO_1HAND_MOVESET = MOVESETS.registerMoveset("xephiro_1hand", () -> Moveset.builder()
            .addComboAttacks(Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.TACHI_AUTO1, KKAnimations.SORA_FINISHER1, KKAnimations.SORA_AUTO3,
                    Animations.SWORD_DUAL_DASH,  Animations.GREATSWORD_AIR_SLASH)
            .addLivingMotionModifier(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .addLivingMotionModifier(LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .addLivingMotionModifier(LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
            .addLivingMotionModifier(LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .addMountAttacks(Animations.SWORD_MOUNT_ATTACK)
            .setPassiveSkill(KKSkills.comboExtender)
            .addGuardAnimations(GuardSkill.BlockType.GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.ADVANCED_GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.GUARD_BREAK, Animations.BIPED_COMMON_NEUTRALIZED));

    public static final DeferredMoveset  XEPHIRO_2HAND_MOVESET = MOVESETS.registerMoveset("xephiro_2hand", () -> Moveset.builder()
            .addComboAttacks(Animations.SWORD_DUAL_AUTO1, KKAnimations.VALOR_AUTO1, KKAnimations.VALOR_AUTO2, KKAnimations.VALOR_AUTO3,
                    Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .addLivingMotionModifier(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .addLivingMotionModifier(LivingMotions.IDLE, KKAnimations.FINAL_FORM_IDLE)
            .addLivingMotionModifier(LivingMotions.RUN, KKAnimations.FINAL_FORM_IDLE)
            .addLivingMotionModifier(LivingMotions.WALK, KKAnimations.FINAL_FORM_IDLE)
            .addMountAttacks(Animations.SWORD_MOUNT_ATTACK)
            .setPassiveSkill(KKSkills.comboExtender)
            .addGuardAnimations(GuardSkill.BlockType.GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.ADVANCED_GUARD, Animations.SWORD_DUAL_GUARD_HIT).addGuardAnimations(GuardSkill.BlockType.GUARD_BREAK, Animations.BIPED_COMMON_NEUTRALIZED));
}
