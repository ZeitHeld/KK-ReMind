package online.magicksaddon.magicsaddonmod.lib;

import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;

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
        darkPassage = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"dark_passage",
        darknessBoost = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"darkness_boost",
        lightBoost = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"light_boost",
        rageAwakened = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"rage_awakened",
        darkPower = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"dark_power",
        wayToLight = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"way_to_light",
        lightStep = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"light_step",
        darkStep = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"dark_step",
        adrenaline = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"adrenaline",
        critical_surge = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"critical_surge",
        lightWithin = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"light_within",
        darknessWithin = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"darkness_within",
        hpWalker = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"hp_walker",
        mpWalker = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"mp_walker",
        expWalker = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"exp_walker",
        riskCharge = KingdomKeysReMind.MODID+":"+ StringsRM.ABMA_Prefix+"riskcharge",

        // Shotlocks
        flameSalvo = "flame_salvo",
        bubbleBlaster = "bubble_blaster",

        thunderStorm = "thunderstorm",
        bioBarrage = "bio_barrage",
        meteorShower = "meteor_shower",

        // Reaction Commands
        riskchargeRC = KingdomKeysReMind.MODID+":"+StringsRM.RCMA_Prefix+"riskcharge",
        testRC = KingdomKeysReMind.MODID+":"+StringsRM.RCMA_Prefix+"test",
        LightBeamRC = KingdomKeysReMind.MODID+":"+StringsRM.RCMA_Prefix+"light_beam",
        DarkMineRC = KingdomKeysReMind.MODID+":"+StringsRM.RCMA_Prefix+"dark_mine",
        XemnasRC = KingdomKeysReMind.MODID+":"+StringsRM.RCMA_Prefix+"xemnas",
        XaldinRC = KingdomKeysReMind.MODID+":"+StringsRM.RCMA_Prefix+"xaldin",
        XigbarRC = KingdomKeysReMind.MODID+":"+StringsRM.RCMA_Prefix+"xigbar",
        vexenRC = KingdomKeysReMind.MODID+":"+StringsRM.RCMA_Prefix+"vexen",


        //Keyblades
        xephiroKeyblade = KingdomKeysReMind.MODID+":"+ StringsRM.KBMA_Prefix+"xephiro_keyblade",

        //Keychains
        xephiroKeybladeChain = KingdomKeysReMind.MODID+":"+ StringsRM.KCMA_Prefix+"xephiro_keyblade_chain",

        // KK Armor

        abasChain = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"abas_chain",
        acrisius = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"acrisius",
        acrisiusPlus = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"acrisius_plus",
        aegisChain = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"aegis_chain",
        blizzagaArmlet = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"blizzaga_armlet",
        blizzagunArmlet = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"blizzagun_armlet",
        blizzaraArmlet = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"blizzara_armlet",
        busterBand = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"buster_band",
        championBelt = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"champion_belt",
        chaosAnklet = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"chaos_anklet",
        cosmicBelt = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"cosmic_belt",
        cosmicChain = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"cosmic_chain",
        darkAnklet = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"dark_anklet",
        divineBandanna = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"divine_bandanna",
        elvenBandanna = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"elven_bandanna",
        firaBangle = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"fira_bangle",
        firagaBangle = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"firaga_bangle",
        firagunBangle = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"firagun_bangle",
        gaiaBelt = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"gaia_belt",
        midnightAnklet = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"midnight_anklet",
        shadowAnklet = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"shadow_anklet",
        shockCharm = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"shock_charm",
        shockCharmPlus = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"shock_charm_plus",
        thundaraTrinket = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"thundara_trinket",
        thundagaTrinket = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"thundaga_trinket",
        thundagunTrinket = KingdomKeysReMind.MODID+":"+ StringsRM.ARMA_Prefix+"thundagun_trinket",




        // KK Accessories
        luckOfTheDraw = KingdomKeysReMind.MODID+":"+ StringsRM.ACMA_Prefix+"luck_of_the_draw",
        lightHeart = KingdomKeysReMind.MODID+":"+ StringsRM.ACMA_Prefix+"lightHeart",
        darkHeart = KingdomKeysReMind.MODID+":"+ StringsRM.ACMA_Prefix+"darkHeart",

        // GUI
        Gui_Menu_Button_Prestige = StringsRM.MENU_Prefix + ".prestige",
        Gui_Menu_Button_PrestigeLevel = StringsRM.MENU_Prefix + ".prestigeLevel",
        Gui_Menu_Button_PrestigeConfirm = StringsRM.MENU_Prefix +".prestigeConfirm",
        Gui_Menu_Button_DreamEater = StringsRM.MENU_Prefix + ".dreamEater",
        Gui_Menu_Button_Credits = StringsRM.MENU_Prefix + ".creditsScreen";
}
