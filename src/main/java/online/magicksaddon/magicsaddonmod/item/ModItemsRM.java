package online.magicksaddon.magicsaddonmod.item;

import com.google.common.collect.ImmutableMap;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.item.KKAccessoryItem;
import online.kingdomkeys.kingdomkeys.item.KKArmorItem;
import online.kingdomkeys.kingdomkeys.item.KKResistanceType;
import online.kingdomkeys.kingdomkeys.item.KeybladeItem;
import online.kingdomkeys.kingdomkeys.item.KeychainItem;
import online.kingdomkeys.kingdomkeys.item.MagicSpellItem;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;


public class ModItemsRM{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, KingdomKeysReMind.MODID);

    public static final RegistryObject<Item>
            // Spell Orbs
        hasteSpell = ITEMS.register("haste_spell", () -> new MagicSpellItem(new Item.Properties(), KingdomKeysReMind.MODID+":magic_haste")),
        slowSpell = ITEMS.register("slow_spell", () -> new MagicSpellItem(new Item.Properties(), KingdomKeysReMind.MODID+":magic_slow")),
        holySpell = ITEMS.register("holy_spell", () -> new MagicSpellItem(new Item.Properties(), KingdomKeysReMind.MODID+":magic_holy")),
        ruinSpell = ITEMS.register("ruin_spell", () -> new MagicSpellItem(new Item.Properties(), KingdomKeysReMind.MODID+":magic_ruin")),
        balloonSpell = ITEMS.register("balloon_spell", () -> new MagicSpellItem(new Item.Properties(), KingdomKeysReMind.MODID+":magic_balloon")),
        ultimaSpell = ITEMS.register("ultima_spell", () -> new MagicSpellItem(new Item.Properties(), KingdomKeysReMind.MODID+":magic_ultima")),
        cometSpell = ITEMS.register("comet_spell", () -> new MagicSpellItem(new Item.Properties(), KingdomKeysReMind.MODID+":magic_comet")),
        berserkSpell = ITEMS.register("berserk_spell",() -> new MagicSpellItem(new Item.Properties(),KingdomKeysReMind.MODID+":magic_berserk")),
        autoLifeSpell = ITEMS.register("autolife_spell", () -> new MagicSpellItem(new Item.Properties(), KingdomKeysReMind.MODID+":magic_auto-life")),

            // Shotlock Orbs
        flameSalvo = ITEMS.register("flame_salvo_shotlock",() -> new ShotlockOrbItem(new Item.Properties(),KingdomKeysReMind.MODID+":flame_salvo")),
        bubbleBlaster = ITEMS.register("bubble_blaster_shotlock",() -> new ShotlockOrbItem(new Item.Properties(),KingdomKeysReMind.MODID+":bubble_blaster")),
        thunderStorm = ITEMS.register("thunderstorm_shotlock",() -> new ShotlockOrbItem(new Item.Properties(),KingdomKeysReMind.MODID+":thunderstorm")),
        bioBarrage = ITEMS.register("bio_barrage_shotlock",() -> new ShotlockOrbItem(new Item.Properties(),KingdomKeysReMind.MODID+":bio_barrage")),
        meteorShower = ITEMS.register("meteor_shower_shotlock",() -> new ShotlockOrbItem(new Item.Properties(),KingdomKeysReMind.MODID+":meteor_shower")),

            // Keyblades
        xephiroKeyblade = ITEMS.register("xephiro_keyblade", () -> new KeybladeItem(new Item.Properties())),
        pureblood = ITEMS.register("pureblood", () -> new KeybladeItem(new Item.Properties())),
            // Keychains
        xephiroKeybladeChain = ITEMS.register("xephiro_keyblade_chain", () -> new KeychainItem()),
        purebloodChain = ITEMS.register("pureblood_chain", () -> new KeychainItem()),

            // KK Armors
        aquaChaplet = ITEMS.register("aqua_chaplet", () -> new KKArmorItem(new Item.Properties().stacksTo(1),1, ImmutableMap.of(KKResistanceType.ice,50))),
        herosGlove = ITEMS.register("heros_glove", () -> new KKArmorItem(new Item.Properties().stacksTo(1),4, ImmutableMap.of(KKResistanceType.fire,20,KKResistanceType.ice,20,KKResistanceType.darkness,20))),
        herosBelt = ITEMS.register("heros_belt", () -> new KKArmorItem(new Item.Properties().stacksTo(1),3, ImmutableMap.of(KKResistanceType.lightning,20,KKResistanceType.ice,20,KKResistanceType.darkness,20))),
        masterBelt = ITEMS.register("master_belt", () -> new KKArmorItem(new Item.Properties().stacksTo(1),7, ImmutableMap.of(KKResistanceType.darkness,20))),
        ultima_ribbon = ITEMS.register("ultima_ribbon", () -> new KKArmorItem(new Item.Properties().stacksTo(1),5, ImmutableMap.of(KKResistanceType.fire,55,KKResistanceType.ice,55,KKResistanceType.lightning,55,KKResistanceType.darkness,55))),

            // KK Accessories
        luckOfTheDraw = ITEMS.register("luck_of_the_draw", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1), 0,0,0,new String[] {Strings.luckyLucky,Strings.treasureMagnet})),
        lightHeart = ITEMS.register("light_heart", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1), 5,0,0,new String[] {StringsRM.wayToLight})),
        darkHeart = ITEMS.register("dark_heart", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1), 5,0,0,new String[] {StringsRM.darkPower})),
        ragingHeart = ITEMS.register("raging_heart", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1), 5,0,0,new String[] {StringsRM.rageAwakened})),
        celestriad = ITEMS.register("celestriad", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1), 5,2,3,new String[] {Strings.fireBoost,Strings.blizzardBoost,Strings.thunderBoost})),
        forestClasp = ITEMS.register("forest_clasp", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1),8,2,3,new String[] {StringsRM.hpWalker})),
        laughterPin = ITEMS.register("laughter_pin", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1),6,3,2,new String[] {StringsRM.mpWalker})),
        crystalRegalia = ITEMS.register("crystal_regalia", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1),16,5,5,new String[] {Strings.mpHastega})),
        crystalRegaliaPlus = ITEMS.register("crystal_regalia_plus", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1),25,6,6,new String[] {Strings.mpHastega})),
        flanniversaryBadge = ITEMS.register("flanniversary_badge", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1),0,4,4,new String[] {Strings.mpHastera,Strings.mpThrift})),
        mickeyClasp = ITEMS.register("mickey_clasp", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1),0,3,5,new String[] {Strings.mpHastega,Strings.endlessMagic})),
        breakthrough = ITEMS.register("breakthrough", () -> new KKAccessoryItem(new Item.Properties().stacksTo(1),15,7,0, null));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
