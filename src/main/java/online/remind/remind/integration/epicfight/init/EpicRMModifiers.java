package online.remind.remind.integration.epicfight.init;

import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.integration.epicfight.enums.KKStyles;
import online.kingdomkeys.kingdomkeys.integration.epicfight.init.EpicKKWeapons;
import online.kingdomkeys.kingdomkeys.integration.epicfight.init.KKMoveSets;
import online.kingdomkeys.kingdomkeys.integration.epicfight.init.KKProviderConditionals;
import online.remind.remind.KingdomKeysReMind;
import yesman.epicfight.api.ex_cap.data.modifier.WeaponModifier;
import yesman.epicfight.registry.deferred.ModifierRegister;
import yesman.epicfight.registry.deferred.holders.DeferredModifier;
import yesman.epicfight.registry.entries.EpicFightItemCapabilityPresets;

public class EpicRMModifiers {
    public static final ModifierRegister WEAPON_MODIFIERS = ModifierRegister.create(KingdomKeysReMind.MODID);

    public static final DeferredModifier FIST_MODIFIER = WEAPON_MODIFIERS.registerModifier("fist",
            () -> WeaponModifier.builder()
                    .target(EpicFightItemCapabilityPresets.FIST)
                    .addMovesetModifier(RMStyle.RAGE_FORM, KKMoveSets.ANTI_FORM_MOVESET)
                    .addConditionalModifier(KKProviderConditionals.ANTI_FORM_STYLE)
    );
    public static final DeferredModifier KEYBLADE_MODIFIER = WEAPON_MODIFIERS.registerModifier("kk_keyblade",
            () -> WeaponModifier.builder()
                    .target(EpicKKWeapons.KEYBLADE)
                    .addMovesetModifier(RMStyle.TWILIGHT_FORM, EpicRMMovesets.TWILIGHT_FORM_MOVESET)
                    .addConditionalModifier(EpicRMConditionals.TWILIGHT_FORM_STYLE)
                    .addMovesetModifier(RMStyle.DARK_FORM, EpicRMMovesets.DARK_FORM_MOVESET)
                    .addConditionalModifier(EpicRMConditionals.DARK_FORM_STYLE)
                    .addMovesetModifier(RMStyle.LIGHT_FORM, EpicRMMovesets.LIGHT_FORM_MOVESET)
                    .addConditionalModifier(EpicRMConditionals.LIGHT_FORM_STYLE)
    );

}
