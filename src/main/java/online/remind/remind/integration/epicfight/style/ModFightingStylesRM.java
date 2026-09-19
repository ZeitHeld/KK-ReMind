package online.remind.remind.integration.epicfight.style;

import net.minecraft.resources.ResourceLocation;
//import online.kingdomkeys.kingdomkeys.api.event.RegisterFightingStylesEvent;
import online.kingdomkeys.kingdomkeys.integration.epicfight.enums.HandStyle;
//import online.kingdomkeys.kingdomkeys.integration.epicfight.style.KKFightingStyle;
import online.remind.remind.KingdomKeysReMind;

public class ModFightingStylesRM {

	public static final ResourceLocation XEPHIRO = ResourceLocation.fromNamespaceAndPath(KingdomKeysReMind.MODID, "xephiro");

	private ModFightingStylesRM() {
	}

	/*
	public static void registerStyles(RegisterFightingStylesEvent event) {
		event.register(KKFightingStyle.builder(XEPHIRO, HandStyle.SINGLE).build());
	}*/
}
