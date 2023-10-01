package online.magicksaddon.magicsaddonmod.ability;

import java.util.ResourceBundle;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

//KK Imports
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.ability.Ability;
import online.kingdomkeys.kingdomkeys.ability.ModAbilities;

//Misc Imports
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.Strings;

public class AddonAbilities extends ModAbilities{

    public static DeferredRegister<Ability> ABILITIES = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "abilities"), MagicksAddonMod.MODID);

    public static Supplier<IForgeRegistry<Ability>> registry = ABILITIES.makeRegistry(RegistryBuilder::new);

    static int order = 0;

    public static final RegistryObject<Ability>

        //New Abilities

        DARK_PASSAGE = createAbility(Strings.darkPassage, 3, Ability.AbilityType.ACTION),
        RAGE_AWAKENED = createAbility(Strings.rageAwakened, 3, Ability.AbilityType.SUPPORT),
        DARKNESS_BOOST = createAbility(Strings.darknessBoost, 5, Ability.AbilityType.SUPPORT);


}
