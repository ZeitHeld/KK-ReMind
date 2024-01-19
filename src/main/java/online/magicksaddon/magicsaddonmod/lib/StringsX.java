package online.magicksaddon.magicsaddonmod.lib;

import online.magicksaddon.magicsaddonmod.MagicksAddonMod;

public class StringsX {

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
        rageForm = StringsX.DFMA_Prefix+"rage",
        darkMode = StringsX.DFMA_Prefix+"dark",
        light = StringsX.DFMA_Prefix+"light",


        //Ability List
        darkPassage = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"dark_passage",
        darknessBoost = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"darkness_boost",
        lightBoost = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"light_boost",
        rageAwakened = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"rage_awakened",
        darkPower = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"dark_power",
        wayToLight = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"way_to_light",
        lightStep = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"light_step",
        darkStep = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"dark_step",
        adrenaline = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"adrenaline",
        critical_surge = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"critical_surge",
        lightWithin = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"light_within",
        darknessWithin = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"darkness_within",
        hpWalker = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"hp_walker",
        mpWalker = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"mp_walker",
        expWalker = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"exp_walker",
        riskCharge = MagicksAddonMod.MODID+":"+ StringsX.ABMA_Prefix+"riskcharge",

        // Shotlocks
        flameSalvo = "flame_salvo",
        bubbleBlaster = "bubble_blaster",

        thunderStorm = "thunderstorm",
        bioBarrage = "bio_barrage",
        meteorShower = "meteor_shower",

        // Reaction Commands
        riskchargeRC = MagicksAddonMod.MODID+":"+StringsX.RCMA_Prefix+"riskcharge",


        //Keyblades
        xephiroKeyblade = MagicksAddonMod.MODID+":"+ StringsX.KBMA_Prefix+"xephiro_keyblade",

        //Keychains
        xephiroKeybladeChain = MagicksAddonMod.MODID+":"+ StringsX.KCMA_Prefix+"xephiro_keyblade_chain",

        // KK Armor

        abasChain = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"abas_chain",
        acrisius = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"acrisius",
        acrisiusPlus = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"acrisius_plus",
        aegisChain = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"aegis_chain",
        blizzagaArmlet = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"blizzaga_armlet",
        blizzagunArmlet = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"blizzagun_armlet",
        blizzaraArmlet = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"blizzara_armlet",
        busterBand = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"buster_band",
        championBelt = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"champion_belt",
        chaosAnklet = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"chaos_anklet",
        cosmicBelt = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"cosmic_belt",
        cosmicChain = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"cosmic_chain",
        darkAnklet = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"dark_anklet",
        divineBandanna = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"divine_bandanna",
        elvenBandanna = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"elven_bandanna",
        firaBangle = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"fira_bangle",
        firagaBangle = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"firaga_bangle",
        firagunBangle = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"firagun_bangle",
        gaiaBelt = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"gaia_belt",
        midnightAnklet = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"midnight_anklet",
        shadowAnklet = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"shadow_anklet",
        shockCharm = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"shock_charm",
        shockCharmPlus = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"shock_charm_plus",
        thundaraTrinket = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"thundara_trinket",
        thundagaTrinket = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"thundaga_trinket",
        thundagunTrinket = MagicksAddonMod.MODID+":"+ StringsX.ARMA_Prefix+"thundagun_trinket",




        // KK Accessories
        luckOfTheDraw = MagicksAddonMod.MODID+":"+ StringsX.ACMA_Prefix+"luck_of_the_draw",
        lightHeart = MagicksAddonMod.MODID+":"+ StringsX.ACMA_Prefix+"lightHeart",
        darkHeart = MagicksAddonMod.MODID+":"+ StringsX.ACMA_Prefix+"darkHeart",

        // GUI
        Gui_Menu_Button_Prestige = StringsX.MENU_Prefix + ".prestige",
        Gui_Menu_Button_PrestigeLevel = StringsX.MENU_Prefix + ".prestigeLevel",
        Gui_Menu_Button_PrestigeConfirm = StringsX.MENU_Prefix +".prestigeConfirm";
}
