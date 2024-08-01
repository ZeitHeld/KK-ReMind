package online.remind.remind.integration.epicfight.skills;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.remind.remind.lib.StringsRM;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

import javax.annotation.Nullable;
import java.util.List;

public class RenewalBlock extends GuardSkill {


    public RenewalBlock(Builder builder) {
        super(builder);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
    }



    @Override
    public void dealEvent(PlayerPatch<?> playerpatch, HurtEvent.Pre event, boolean advanced) {

        event.setCanceled(true);
        event.setResult(AttackResult.ResultType.BLOCKED);
        Player player = playerpatch.getOriginal();
        IPlayerCapabilities playerCapabilities = ModCapabilities.getPlayer(player);

        if(playerCapabilities.isAbilityEquipped(StringsRM.renewalBlock)) {

            player.heal(player.getMaxHealth() * 0.05F);
            System.out.println("Healed on Block!");

        }

        if(playerCapabilities.isAbilityEquipped(StringsRM.focusBlock)) {

            playerCapabilities.addFocus(10);
            System.out.println("Focus Restored on Block!");

        }
    }

    @Override
    protected boolean isBlockableSource(DamageSource damageSource, boolean advanced) {
        return (damageSource.is(DamageTypeTags.IS_PROJECTILE) && advanced) || super.isBlockableSource(damageSource, false);
    }

    @Override
    protected boolean isAdvancedGuard() {
        return true;
    }


}
