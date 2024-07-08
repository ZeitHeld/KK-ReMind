package online.remind.remind.integration.epicfight.skills;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

public class FocusBlock extends GuardSkill {

    public FocusBlock(Builder builder) {
        super(builder);
    }


    @Override
    public void dealEvent(PlayerPatch<?> playerpatch, HurtEvent.Pre event, boolean advanced) {
        event.setCanceled(true);
        event.setResult(AttackResult.ResultType.BLOCKED);
        Player player = playerpatch.getOriginal();
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        playerData.addFocus(10);




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
