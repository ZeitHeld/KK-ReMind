package online.remind.remind.integration.epicfight.skills;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

public class RenewalBlock extends GuardSkill {

    public RenewalBlock(Builder builder) {
        super(builder);
    }


    @Override
    public void dealEvent(PlayerPatch<?> playerpatch, HurtEvent.Pre event, boolean advanced) {
        event.setCanceled(true);
        event.setResult(AttackResult.ResultType.BLOCKED);
        Player player = playerpatch.getOriginal();

        player.heal(player.getMaxHealth() * 0.05F);



        LivingEntityPatch<?> attackerpatch = EpicFightCapabilities.getEntityPatch(event.getDamageSource().getEntity(), LivingEntityPatch.class);

        if (attackerpatch != null) {
            attackerpatch.setLastAttackEntity(playerpatch.getOriginal());
        }

        Entity directEntity = event.getDamageSource().getDirectEntity();
        LivingEntityPatch<?> entitypatch = EpicFightCapabilities.getEntityPatch(directEntity, LivingEntityPatch.class);

        if (entitypatch != null) {
            entitypatch.onAttackBlocked(event.getDamageSource(), playerpatch);

        }
    }
}
