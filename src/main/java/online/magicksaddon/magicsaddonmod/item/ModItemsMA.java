package online.magicksaddon.magicsaddonmod.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.item.*;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.StringsX;


public class ModItemsMA{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MagicksAddonMod.MODID);

    public static final RegistryObject<Item>
            // Spell Orbs
        hasteSpell = ITEMS.register("haste_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB), MagicksAddonMod.MODID+":magic_haste")),
        slowSpell = ITEMS.register("slow_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB), MagicksAddonMod.MODID+":magic_slow")),
        holySpell = ITEMS.register("holy_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB), MagicksAddonMod.MODID+":magic_holy")),
        ruinSpell = ITEMS.register("ruin_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB), MagicksAddonMod.MODID+":magic_ruin")),
        balloonSpell = ITEMS.register("balloon_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB), MagicksAddonMod.MODID+":magic_balloon")),
        ultimaSpell = ITEMS.register("ultima_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB), MagicksAddonMod.MODID+":magic_ultima")),
        cometSpell = ITEMS.register("comet_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB), MagicksAddonMod.MODID+":magic_comet")),
        berserkSpell = ITEMS.register("berserk_spell",() -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB),MagicksAddonMod.MODID+":magic_berserk")),
        autoLifeSpell = ITEMS.register("autolife_spell", () -> new MagicSpellItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_MAGIC_TAB), MagicksAddonMod.MODID+":magic_auto-life")),

            // Keyblades
        xephiroKeyblade = ITEMS.register("xephiro_keyblade", () -> new KeybladeItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_KEYBLADES_TAB))),
        darkDesires = ITEMS.register("dark_desires", () -> new KeybladeItem(new Item.Properties())),
            // Keychains
        xephiroKeybladeChain = ITEMS.register("xephiro_keyblade_chain", () -> new KeychainItem()),
        darkDesiresChain = ITEMS.register("dark_desires_chain", () -> new KeychainItem()),

            // KK Armors
        abasChain = ITEMS.register("abas_chain", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),0, ImmutableMap.of(KKResistanceType.fire, 20,KKResistanceType.ice,20,KKResistanceType.lightning,20))),
        acrisius = ITEMS.register("acrisius", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.fire, 20,KKResistanceType.ice,20,KKResistanceType.lightning,20))),
        acrisiusPlus = ITEMS.register("acrisius_plus", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.fire, 25,KKResistanceType.ice,25,KKResistanceType.lightning,25))),
        aegisChain = ITEMS.register("aegis_chain", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),2, ImmutableMap.of(KKResistanceType.fire, 20,KKResistanceType.ice,20,KKResistanceType.lightning,20))),
        blizzaraArmlet = ITEMS.register("blizzara_armlet", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),2, ImmutableMap.of(KKResistanceType.ice,20))),
        blizzagaArmlet = ITEMS.register("blizzaga_armlet", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.ice,20))),
        blizzagunArmlet = ITEMS.register("blizzagun_armlet", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.ice,25))),
        busterBand = ITEMS.register("buster_band", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),5, ImmutableMap.of(KKResistanceType.darkness,0))),
        championBelt = ITEMS.register("champion_belt", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),0, ImmutableMap.of(KKResistanceType.fire, 20,KKResistanceType.ice,20,KKResistanceType.lightning,20))),
        chaosAnklet = ITEMS.register("chaos_anklet", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.darkness, 25))),
        cosmicBelt = ITEMS.register("cosmic_belt", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),6, ImmutableMap.of(KKResistanceType.darkness,0))),
        cosmicChain = ITEMS.register("cosmic_chain", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.fire, 25,KKResistanceType.ice,25,KKResistanceType.lightning,25))),
        darkAnklet = ITEMS.register("dark_anklet", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),2, ImmutableMap.of(KKResistanceType.darkness, 20))),
        divineBandanna = ITEMS.register("divine_bandanna", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),2, ImmutableMap.of(KKResistanceType.darkness,0))),
        elvenBandanna = ITEMS.register("elven_bandanna", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),1, ImmutableMap.of(KKResistanceType.darkness,0))),
        firaBangle = ITEMS.register("fira_bangle", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),2, ImmutableMap.of(KKResistanceType.fire,20))),
        firagaBangle = ITEMS.register("firaga_bangle", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.fire,20))),
        firagunBangle = ITEMS.register("firagun_bangle", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.fire,25))),
        gaiaBelt = ITEMS.register("gaia_belt", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.lightning,20,KKResistanceType.darkness,20))),
        midnightAnklet = ITEMS.register("midnight_anklet", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.darkness,20))),
        shadowAnklet = ITEMS.register("shadow_anklet", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),2, ImmutableMap.of(KKResistanceType.darkness,20))),
        shockCharm = ITEMS.register("shock_charm", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.lightning,40))),
        shockCharmPlus = ITEMS.register("shock_charm_plus", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),4, ImmutableMap.of(KKResistanceType.lightning,50))),
        thundaraTrinket = ITEMS.register("thundara_trinket", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),2, ImmutableMap.of(KKResistanceType.lightning,20))),
        thundagaTrinket = ITEMS.register("thundaga_trinket", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.lightning,20))),
        thundagunTrinket = ITEMS.register("thundagun_trinket", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.lightning,25))),


        aquaChaplet = ITEMS.register("aqua_chaplet", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),1, ImmutableMap.of(KKResistanceType.ice,50))),
        herosGlove = ITEMS.register("heros_glove", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),4, ImmutableMap.of(KKResistanceType.fire,20,KKResistanceType.ice,20,KKResistanceType.darkness,20))),
        herosBelt = ITEMS.register("heros_belt", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),3, ImmutableMap.of(KKResistanceType.lightning,20,KKResistanceType.ice,20,KKResistanceType.darkness,20))),
        masterBelt = ITEMS.register("master_belt", () -> new KKArmorItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ARMOR_TAB).stacksTo(1),7, ImmutableMap.of(KKResistanceType.darkness,20))),

            // KK Accessories
        luckOfTheDraw = ITEMS.register("luck_of_the_draw", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1), 0,0,0,new String[] {Strings.luckyLucky,Strings.treasureMagnet})),
        lightHeart = ITEMS.register("light_heart", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1), 5,0,0,new String[] {StringsX.wayToLight})),
        darkHeart = ITEMS.register("dark_heart", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1), 5,0,0,new String[] {StringsX.darkPower})),
        ragingHeart = ITEMS.register("raging_heart", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1), 5,0,0,new String[] {StringsX.rageAwakened})),
        celestriad = ITEMS.register("celestriad", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1), 5,2,3,new String[] {Strings.fireBoost,Strings.blizzardBoost,Strings.thunderBoost})),
        forestClasp = ITEMS.register("forest_clasp", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1),8,2,3,new String[] {StringsX.hpWalker})),
        laughterPin = ITEMS.register("laughter_pin", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1),6,3,2,new String[] {StringsX.mpWalker})),
        crystalRegalia = ITEMS.register("crystal_regalia", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1),16,5,5,new String[] {Strings.mpHastega})),
        crystalRegaliaPlus = ITEMS.register("crystal_regalia_plus", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1),25,6,6,new String[] {Strings.mpHastega})),
        flanniversaryBadge = ITEMS.register("flanniversary_badge", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1),0,4,4,new String[] {Strings.mpHastera,Strings.mpThrift})),
        mickeyClasp = ITEMS.register("mickey_clasp", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1),0,3,5,new String[] {Strings.mpHastega,Strings.endlessMagic})),
        breakthrough = ITEMS.register("breakthrough", () -> new KKAccessoryItem(new Item.Properties().tab(ModCreativeModTabMA.ADDON_ACCESSORY_TAB).stacksTo(1),15,7,0, null));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
