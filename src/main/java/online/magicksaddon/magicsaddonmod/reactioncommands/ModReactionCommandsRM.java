package online.magicksaddon.magicsaddonmod.reactioncommands;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;

public class ModReactionCommandsRM {
    public static DeferredRegister<ReactionCommand> REACTION_COMMANDS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "reactioncommands"), "magicksaddon");

    public static final RegistryObject<ReactionCommand>
        RISKCHARGE = REACTION_COMMANDS.register(StringsRM.RCMA_Prefix+"riskcharge", () -> new RiskchargeReaction(new ResourceLocation(StringsRM.riskchargeRC), true)),
        TEST_ORG = REACTION_COMMANDS.register(StringsRM.RCMA_Prefix+"test", () -> new TestReaction(new ResourceLocation(StringsRM.testRC),false)),
        LIGHT_BEAM = REACTION_COMMANDS.register(StringsRM.RCMA_Prefix+"light_beam", () -> new LightBeamRC(new ResourceLocation(StringsRM.LightBeamRC), true)),
        DARK_MINE_RC = REACTION_COMMANDS.register(StringsRM.RCMA_Prefix+"dark_mine", () -> new DarkMineRC(new ResourceLocation(StringsRM.DarkMineRC),true)),
        XEMNAS_RC = REACTION_COMMANDS.register(StringsRM.RCMA_Prefix+"xemnas", () -> new XemnasRC(new ResourceLocation(StringsRM.XemnasRC),true));

}







