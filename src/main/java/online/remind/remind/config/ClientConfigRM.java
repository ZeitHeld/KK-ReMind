package online.remind.remind.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import online.remind.remind.KingdomKeysReMind;

import java.util.List;

public class ClientConfigRM {
    // X, Y, width, height, scaleX, scaleY, rotation, anchor ordinal, visible
    public static final List<Float> DREAM_EATER_HUD_DEFAULTS = List.of(70F, 30F, 25F, 22F, 1F, 1F, 0F, 8F, 1F);
    public static ModConfigSpec.BooleanValue chirithySpellFeedback;


    public ModConfigSpec.ConfigValue<List<? extends Float>> dreamEaterHUDData;

    public ClientConfigRM(ModConfigSpec.Builder builder) {
        builder.push("hud_data");
        dreamEaterHUDData = builder
                .comment("Summoned Dream Eater HUD Data")
                .translation(KingdomKeysReMind.MODID + ".config.dream_eater_hud_data") //X,Y,Width,Height,xScale,yScale,rotation,anchor (ordinal),visible
                .defineList("dreamEaterHUDData", () -> DREAM_EATER_HUD_DEFAULTS, o -> o instanceof Number);
        builder.pop();

        builder.push("dream_eater_settings");
        chirithySpellFeedback = builder
                .comment("Allows Chirithy to send casting Dialog/Feedback")
                .comment("Default: true")
                .define("Chirithy Feedback Enabled", true);
    }
}
