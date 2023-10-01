package online.magicksaddon.magicsaddonmod.ability;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
//KK Imports
import online.kingdomkeys.kingdomkeys.ability.Ability;
import online.kingdomkeys.kingdomkeys.ability.ModAbilities;

//Misc Imports
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;

public class AddonAbilities extends ModAbilities{

    public static DeferredRegister<Ability> ABILITIES = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "abilities"), MagicksAddonMod.MODID);

    //public static Supplier<IForgeRegistry<Ability>> registry = ABILITIES.makeRegistry(RegistryBuilder::new);

    static int order = 0;

        //New Abilities

    public static final RegistryObject<Ability>
    	DARK_PASSAGE = ABILITIES.register("dark_passage", () -> new Ability("dark_passage", 3, Ability.AbilityType.ACTION, 100));

        /*DARK_PASSAGE = createAbility(Strings.darkPassage, 3, Ability.AbilityType.ACTION),
        RAGE_AWAKENED = createAbility(Strings.rageAwakened, 3, Ability.AbilityType.SUPPORT),
        DARKNESS_BOOST = createAbility(Strings.darknessBoost, 5, Ability.AbilityType.SUPPORT);*/


}
