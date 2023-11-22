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
import online.magicksaddon.magicsaddonmod.lib.Strings;

public class AddonAbilities extends ModAbilities{

    public static DeferredRegister<Ability> ABILITIES = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "abilities"), MagicksAddonMod.MODID);

    static int order = 100;

        //New Abilities

    public static final RegistryObject<Ability>
            // Action
    	DARK_PASSAGE = ABILITIES.register(Strings.ABMA_Prefix+"dark_passage", () -> new Ability(new ResourceLocation(Strings.darkPassage), 3, Ability.AbilityType.ACTION, order++)),
        RAGE_AWAKENED = ABILITIES.register(Strings.ABMA_Prefix+"rage_awakened", () -> new Ability(new ResourceLocation(Strings.rageAwakened), 5,Ability.AbilityType.ACTION, order++)),
        WAY_TO_LIGHT = ABILITIES.register(Strings.ABMA_Prefix+"way_to_light", () -> new Ability(new ResourceLocation(Strings.wayToLight), 0, Ability.AbilityType.ACTION, order++)),
        DARK_POWER = ABILITIES.register(Strings.ABMA_Prefix+"dark_power", () -> new Ability(new ResourceLocation(Strings.darkPower), 0, Ability.AbilityType.ACTION, order++)),

            // Growth
        LIGHT_STEP = ABILITIES.register(Strings.ABMA_Prefix+"light_step", () -> new Ability(new ResourceLocation(Strings.lightStep), 3, Ability.AbilityType.GROWTH, order++)),
        DARK_STEP = ABILITIES.register(Strings.ABMA_Prefix+"dark_step", () -> new Ability(new ResourceLocation(Strings.darkStep), 3, Ability.AbilityType.GROWTH, order++)),

            // Support
        DARKNESS_BOOST = ABILITIES.register(Strings.ABMA_Prefix+"darkness_boost", () -> new Ability(new ResourceLocation(Strings.darknessBoost), 3, Ability.AbilityType.SUPPORT, order++)),
        LIGHT_BOOST = ABILITIES.register(Strings.ABMA_Prefix+"light_boost", () -> new Ability(new ResourceLocation(Strings.lightBoost), 3, Ability.AbilityType.SUPPORT, order++)),
        ADRENALINE = ABILITIES.register(Strings.ABMA_Prefix+"adrenaline", () -> new Ability(new ResourceLocation(Strings.adrenaline), 4, Ability.AbilityType.SUPPORT, order++)),
        CRITICAL_SURGE = ABILITIES.register(Strings.ABMA_Prefix+"critical_surge", () -> new Ability(new ResourceLocation(Strings.critical_surge), 4, Ability.AbilityType.SUPPORT, order++)),
        LIGHT_WITHIN = ABILITIES.register(Strings.ABMA_Prefix+"light_within", () -> new Ability(new ResourceLocation(Strings.lightWithin), 5, Ability.AbilityType.SUPPORT, order++)),
        DARKNESS_WITHIN = ABILITIES.register(Strings.ABMA_Prefix+"darkness_within", () -> new Ability(new ResourceLocation(Strings.darknessWithin), 5, Ability.AbilityType.SUPPORT, order++));

}
