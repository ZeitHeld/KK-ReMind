package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;

public class MagicksEntityEvents {

	
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent e) {
		/*Player player = e.getEntity();
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		
		if(playerData != null) {
			if (!player.level.isClientSide) { // Sync from server to client		
				if(!playerData.getDriveFormMap().containsKey(Strings.Form_Anti)) {
					//playerData.setDriveFormLevel(Strings.Form_Anti, 1);
				}
			}
		}*/		
	}

	// Haste
	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		if(event.getEntity() instanceof Player player) {
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			if(playerData != null) {
				if(playerData.isAbilityEquipped(online.magicksaddon.magicsaddonmod.lib.Strings.darkPower)) {
					if(!playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.darkMode)) {
						playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.darkMode, 1);
					}
				} else {
					if(playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.darkMode)) {
						playerData.getDriveFormMap().remove(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.darkMode);
					}
				}
				
				if(playerData.isAbilityEquipped(online.magicksaddon.magicsaddonmod.lib.Strings.rageAwakened)) {
					if(!playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.rageForm)) {
						playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.rageForm, 1);
					}
				} else {
					if(playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.rageForm)) {
						playerData.getDriveFormMap().remove(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.rageForm);
					}
				}
			}
		}
		/*if(event.getEntity() instanceof Player player) {
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			//System.out.println(playerData.getActiveDriveForm());
			@Nullable
			DriveForm df = ModDriveForms.registry.get().getValue(new ResourceLocation(playerData.getActiveDriveForm()));
			System.out.println(df.getTextureLocation());
		}*/

		IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(event.getEntity());
		if (globalData != null) {
			// Spells go Down Below

			// Slow
			if (globalData.getSlowTicks() > 0) {
				globalData.remSlowTicks(1);
				// System.out.println("Slow Level: " + globalData.getSlowLevel() + " " + "Slow
				// Ticks Remaining: " + globalData.getSlowTicks());
				if (globalData.getSlowTicks() <= 0) {
					event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.25 + (0.25 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
					event.getEntity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.25 + (0.25 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
				}
			}
			
			// Haste
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				if (globalData.getHasteTicks() > 0) {
					globalData.remHasteTicks(1);
					// System.out.println("Haste Level: " + globalData.getHasteLevel() + " " +
					// "Haste Ticks Remaining: " + globalData.getHasteTicks());
					if (globalData.getHasteTicks() <= 0) {
						player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Haste", -(0.25 + (0.25 * globalData.getHasteLevel())), AttributeModifier.Operation.MULTIPLY_BASE));
						player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Haste", -(0.25 + (0.25 * globalData.getHasteLevel())), AttributeModifier.Operation.MULTIPLY_BASE));
					}
				}				
			}
			// Berserk
			if (event.getEntity() instanceof Player){
				Player player = (Player) event.getEntity();
				if (globalData.getBerserkTicks() > 0) {
					globalData.remBerserkTicks(1);
					System.out.println("Berserk Level: " + globalData.getBerserkLevel() + " " +
					"Berserk Ticks Remaining: " + globalData.getBerserkTicks());
					if (globalData.getBerserkTicks() <= 0) {
						player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier("Berserk", -0.25 + (-0.25 * globalData.getBerserkLevel()), AttributeModifier.Operation.ADDITION));
					}
				}
			}
		}
	}
}