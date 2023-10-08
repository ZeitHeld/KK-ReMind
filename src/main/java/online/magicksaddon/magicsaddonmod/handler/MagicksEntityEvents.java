package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;

public class MagicksEntityEvents {

	// Haste
	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
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
			if (globalData != null) {
				if (globalData.getSlowTicks() > 0) {
					globalData.remSlowTicks(1);
					// System.out.println("Slow Level: " + globalData.getSlowLevel() + " " + "Slow
					// Ticks Remaining: " + globalData.getSlowTicks());
					if (globalData.getSlowTicks() <= 0) {
						event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.25 + (0.25 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
						event.getEntity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.25 + (0.25 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
					}
				}
			}
			// Haste
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				if (globalData != null) {
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
			}
			// Next Spell
		}
	}
}