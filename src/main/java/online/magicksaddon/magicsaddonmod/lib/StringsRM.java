package online.magicksaddon.magicsaddonmod.lib;

import online.magicksaddon.magicsaddonmod.MagicksAddonMod;

public class StringsRM {

	public static final byte
		darkStepType = 0, 
		lightStepType = 1;
	
    public static final String
            // Prefixes
        ABMA_Prefix = "ability_",
        DFMA_Prefix = "form_",
        KBMA_Prefix = "keyblade_",
        KCMA_Prefix = "keychain_",
        SLMA_Prefix = "shotlock_",
        ARMA_Prefix = "armor_",
        ACMA_Prefix = "accessory_",
        MENU_Prefix = "menu_button",
        RCMA_Prefix = "rc_",


        //Forms
        rageForm = StringsRM.DFMA_Prefix+"rage",
        darkMode = StringsRM.DFMA_Prefix+"dark",
        light = StringsRM.DFMA_Prefix+"light",


        //Ability List
        darkPassage = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"dark_passage",
        darknessBoost = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"darkness_boost",
        lightBoost = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"light_boost",
        rageAwakened = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"rage_awakened",
        darkPower = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"dark_power",
        wayToLight = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"way_to_light",
        lightStep = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"light_step",
        darkStep = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"dark_step",
        adrenaline = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"adrenaline",
        critical_surge = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"critical_surge",
        lightWithin = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"light_within",
        darknessWithin = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"darkness_within",
        hpWalker = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"hp_walker",
        mpWalker = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"mp_walker",
        expWalker = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"exp_walker",
        riskCharge = MagicksAddonMod.MODID+":"+ StringsRM.ABMA_Prefix+"riskcharge",

        // Shotlocks
        flameSalvo = "flame_salvo",
        bubbleBlaster = "bubble_blaster",

        thunderStorm = "thunderstorm",
        bioBarrage = "bio_barrage",
        meteorShower = "meteor_shower",

        // Reaction Commands
        riskchargeRC = MagicksAddonMod.MODID+":"+StringsRM.RCMA_Prefix+"riskcharge",


        //Keyblades
        xephiroKeyblade = MagicksAddonMod.MODID+":"+ StringsRM.KBMA_Prefix+"xephiro_keyblade",

        //Keychains
        xephiroKeybladeChain = MagicksAddonMod.MODID+":"+ StringsRM.KCMA_Prefix+"xephiro_keyblade_chain",

        // KK Armor

        abasChain = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"abas_chain",
        acrisius = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"acrisius",
        acrisiusPlus = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"acrisius_plus",
        aegisChain = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"aegis_chain",
        blizzagaArmlet = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"blizzaga_armlet",
        blizzagunArmlet = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"blizzagun_armlet",
        blizzaraArmlet = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"blizzara_armlet",
        busterBand = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"buster_band",
        championBelt = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"champion_belt",
        chaosAnklet = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"chaos_anklet",
        cosmicBelt = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"cosmic_belt",
        cosmicChain = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"cosmic_chain",
        darkAnklet = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"dark_anklet",
        divineBandanna = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"divine_bandanna",
        elvenBandanna = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"elven_bandanna",
        firaBangle = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"fira_bangle",
        firagaBangle = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"firaga_bangle",
        firagunBangle = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"firagun_bangle",
        gaiaBelt = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"gaia_belt",
        midnightAnklet = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"midnight_anklet",
        shadowAnklet = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"shadow_anklet",
        shockCharm = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"shock_charm",
        shockCharmPlus = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"shock_charm_plus",
        thundaraTrinket = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"thundara_trinket",
        thundagaTrinket = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"thundaga_trinket",
        thundagunTrinket = MagicksAddonMod.MODID+":"+ StringsRM.ARMA_Prefix+"thundagun_trinket",




        // KK Accessories
        luckOfTheDraw = MagicksAddonMod.MODID+":"+ StringsRM.ACMA_Prefix+"luck_of_the_draw",
        lightHeart = MagicksAddonMod.MODID+":"+ StringsRM.ACMA_Prefix+"lightHeart",
        darkHeart = MagicksAddonMod.MODID+":"+ StringsRM.ACMA_Prefix+"darkHeart",

        // GUI
        Gui_Menu_Button_Prestige = StringsRM.MENU_Prefix + ".prestige",
        Gui_Menu_Button_PrestigeLevel = StringsRM.MENU_Prefix + ".prestigeLevel",
        Gui_Menu_Button_PrestigeConfirm = StringsRM.MENU_Prefix +".prestigeConfirm",
        Gui_Menu_Button_DreamEater = StringsRM.MENU_Prefix + ".dreamEater";
}
