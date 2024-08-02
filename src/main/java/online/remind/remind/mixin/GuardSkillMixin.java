package online.remind.remind.mixin;

import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.remind.remind.lib.StringsRM;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

@Mixin(GuardSkill.class)
public class GuardSkillMixin {

    public void dealEvent(PlayerPatch<?> playerpatch, HurtEvent.Pre event, boolean advanced, CallbackInfo ci) {

        event.setCanceled(true);
        event.setResult(AttackResult.ResultType.BLOCKED);
        Player player = playerpatch.getOriginal();
        IPlayerCapabilities playerCapabilities = ModCapabilities.getPlayer(player);


        // Block Abilities Effects


        if(playerCapabilities.isAbilityEquipped(StringsRM.renewalBlock)) {
            player.heal(player.getMaxHealth() * 0.05F);
            System.out.println("Healed on Block!");

        }

        if(playerCapabilities.isAbilityEquipped(StringsRM.focusBlock)) {
            playerCapabilities.addFocus(10);
            System.out.println("Focus Restored on Block!");

        }

    }
}
