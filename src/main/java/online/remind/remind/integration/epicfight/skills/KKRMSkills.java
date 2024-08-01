package online.remind.remind.integration.epicfight.skills;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.remind.remind.KingdomKeysReMind;

import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.guard.GuardSkill;

public class KKRMSkills {
    public static Skill renewalBlock;
    public static Skill focusBlock;

    public static void register() {
        SkillManager.register(RenewalBlock::new, GuardSkill.createGuardBuilder(), KingdomKeysReMind.MODID, "renewal_block");
    }

    @SubscribeEvent
    public void buildSkillsEvent(SkillBuildEvent event) {
        renewalBlock = event.build(KingdomKeysReMind.MODID,"renewal_block");
        focusBlock = event.build(KingdomKeysReMind.MODID,"focus_block");
    }

}