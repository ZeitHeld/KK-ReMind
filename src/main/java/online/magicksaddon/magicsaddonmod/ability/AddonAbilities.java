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

    //public static Supplier<IForgeRegistry<Ability>> registry = ABILITIES.makeRegistry(RegistryBuilder::new);

    static int order = 100;

        //New Abilities

    public static final RegistryObject<Ability>
    	DARK_PASSAGE = ABILITIES.register("dark_passage", () -> new Ability(new ResourceLocation(Strings.darkPassage), 3, Ability.AbilityType.ACTION, order++)),
    	DARKNESS_BOOST = ABILITIES.register("darkness_boost", () -> new Ability(new ResourceLocation(Strings.darknessBoost), 3, Ability.AbilityType.SUPPORT, order++)),
        RAGE_AWAKENED = ABILITIES.register("rage_awakened", () -> new Ability(new ResourceLocation(Strings.rageAwakened), 5,Ability.AbilityType.SUPPORT, order++));

        /*DARK_PASSAGE = createAbility(Strings.darkPassage, 3, Ability.AbilityType.ACTION),
        RAGE_AWAKENED = createAbility(Strings.rageAwakened, 3, Ability.AbilityType.SUPPORT),
        DARKNESS_BOOST = createAbility(Strings.darknessBoost, 5, Ability.AbilityType.SUPPORT);*/


}
