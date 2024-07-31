package online.remind.remind.integration.epicfight.skills;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.remind.remind.KingdomKeysReMind;

import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class KKRMSkills{
    public static Skill renewalBlock;
    public static Skill focusBlock;

    @SubscribeEvent
    public void buildSkillsEvent(SkillBuildEvent event) {
        SkillBuildEvent.ModRegistryWorker modRegistry = event.createRegistryWorker(KingdomKeysReMind.MODID);

        renewalBlock = modRegistry.build("renewal_block", RenewalBlock::new, Skill.createBuilder());
        //focusBlock = modRegistry.build("focus_block");
    }

    public static void register()
    {
        SkillManager.register(RenewalBlock::new, Skill.createBuilder().setCategory(SkillCategories.GUARD).setResource(Skill.Resource.NONE), KingdomKeysReMind.MODID, "combo_extender");
    }

}
