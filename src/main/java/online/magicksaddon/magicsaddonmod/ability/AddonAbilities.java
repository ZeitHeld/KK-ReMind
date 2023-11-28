package online.magicksaddon.magicsaddonmod.ability;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
//KK Imports
import online.kingdomkeys.kingdomkeys.ability.Ability;
import online.kingdomkeys.kingdomkeys.ability.ModAbilities;
//Misc Imports
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.StringsX;

public class AddonAbilities extends ModAbilities{

    public static DeferredRegister<Ability> ABILITIES = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "abilities"), MagicksAddonMod.MODID);

    static int order = 100;

        //New Abilities

    public static final RegistryObject<Ability>
            // Action
    	DARK_PASSAGE = ABILITIES.register(StringsX.ABMA_Prefix+"dark_passage", () -> new Ability(new ResourceLocation(StringsX.darkPassage), 3, Ability.AbilityType.ACTION, order++)),
        RAGE_AWAKENED = ABILITIES.register(StringsX.ABMA_Prefix+"rage_awakened", () -> new Ability(new ResourceLocation(StringsX.rageAwakened), 5,Ability.AbilityType.ACTION, order++)),
        WAY_TO_LIGHT = ABILITIES.register(StringsX.ABMA_Prefix+"way_to_light", () -> new Ability(new ResourceLocation(StringsX.wayToLight), 0, Ability.AbilityType.ACTION, order++)),
        DARK_POWER = ABILITIES.register(StringsX.ABMA_Prefix+"dark_power", () -> new Ability(new ResourceLocation(StringsX.darkPower), 0, Ability.AbilityType.ACTION, order++)),

            // Growth
        LIGHT_STEP = ABILITIES.register(StringsX.ABMA_Prefix+"light_step", () -> new Ability(new ResourceLocation(StringsX.lightStep), 3, Ability.AbilityType.GROWTH, order++)),
        DARK_STEP = ABILITIES.register(StringsX.ABMA_Prefix+"dark_step", () -> new Ability(new ResourceLocation(StringsX.darkStep), 3, Ability.AbilityType.GROWTH, order++)),

            // Support
        DARKNESS_BOOST = ABILITIES.register(StringsX.ABMA_Prefix+"darkness_boost", () -> new Ability(new ResourceLocation(StringsX.darknessBoost), 3, Ability.AbilityType.SUPPORT, order++)),
        LIGHT_BOOST = ABILITIES.register(StringsX.ABMA_Prefix+"light_boost", () -> new Ability(new ResourceLocation(StringsX.lightBoost), 3, Ability.AbilityType.SUPPORT, order++)),
        ADRENALINE = ABILITIES.register(StringsX.ABMA_Prefix+"adrenaline", () -> new Ability(new ResourceLocation(StringsX.adrenaline), 4, Ability.AbilityType.SUPPORT, order++)),
        CRITICAL_SURGE = ABILITIES.register(StringsX.ABMA_Prefix+"critical_surge", () -> new Ability(new ResourceLocation(StringsX.critical_surge), 4, Ability.AbilityType.SUPPORT, order++)),
        LIGHT_WITHIN = ABILITIES.register(StringsX.ABMA_Prefix+"light_within", () -> new Ability(new ResourceLocation(StringsX.lightWithin), 5, Ability.AbilityType.SUPPORT, order++)),
        DARKNESS_WITHIN = ABILITIES.register(StringsX.ABMA_Prefix+"darkness_within", () -> new Ability(new ResourceLocation(StringsX.darknessWithin), 5, Ability.AbilityType.SUPPORT, order++));

}
