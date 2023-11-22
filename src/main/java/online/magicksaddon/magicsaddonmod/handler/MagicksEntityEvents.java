package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.handler.EntityEvents;
import online.kingdomkeys.kingdomkeys.capability.*;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.lib.Strings;

public class MagicksEntityEvents {


	private PlayerCapabilities playerData;


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

	@SubscribeEvent
	public void onJoin(PlayerLoggedInEvent e){
		Player player = e.getEntity();
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);

		// Testing Stuff

		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+Strings.flameSalvo, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+Strings.bubbleBlaster, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+Strings.thunderStorm, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+Strings.bioBarrage, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+Strings.meteorShower, true);
		


	}


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

				if(playerData.isAbilityEquipped(online.magicksaddon.magicsaddonmod.lib.Strings.wayToLight)) {
					if(!playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.light)) {
						playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+online.magicksaddon.magicsaddonmod.lib.Strings.light, 1);
					}
				} else {
					if (playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID + ":" + online.magicksaddon.magicsaddonmod.lib.Strings.light)) {
						playerData.getDriveFormMap().remove(MagicksAddonMod.MODID + ":" + online.magicksaddon.magicsaddonmod.lib.Strings.light);
					}
				}
				// Additional Forms Here

				// Additional Forms ^

				// Light/Darkness Within Code?

				int boostWithin = (playerData.getStrengthStat().getStat() + playerData.getMagicStat().getStat()) / 4;

				int darknessWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(Strings.darknessBoost) * 0.25F);
				int lightWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(Strings.lightBoost) * 0.25F);

				//System.out.println("Initial Within Boost: "+boostWithin);
				//System.out.println("Light Within Boost: "+lightWithinBoost);
				//System.out.println("Darkness Within Boost: "+darknessWithinBoost);

				if (playerData.isAbilityEquipped(Strings.lightWithin)) {
					playerData.getStrengthStat().addModifier("light_within", lightWithinBoost, false);
					playerData.getMagicStat().addModifier("light_within", lightWithinBoost, false);
				} else {
					playerData.getStrengthStat().removeModifier("light_within");
					playerData.getMagicStat().removeModifier("light_within");
				}
				if (playerData.isAbilityEquipped(Strings.darknessWithin)){
					playerData.getStrengthStat().addModifier("darkness_within", darknessWithinBoost, false);
					playerData.getMagicStat().addModifier("darkness_within", darknessWithinBoost, false);
				} else {
					playerData.getStrengthStat().removeModifier("darkness_within");
					playerData.getMagicStat().removeModifier("darkness_within");
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

						IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
						playerData.getStrengthStat().removeModifier("berserk");
						PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
					}
				}
			}

			// Next Tick Based Spell Goes Below

		}
	}
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event){
		IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(event.getEntity());
		if (event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if (1 == globalData.getAutoLifeActive()){
				if (player.getHealth() <= 0){
					event.setCanceled(true);
					player.setHealth(10.0F);
					globalData.remAutoLifeActive(1);
					player.removeAllEffects();
					player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 10));
					player.level.playSound(null, player.blockPosition(), MagicSounds.AUTOLIFE.get(), SoundSource.PLAYERS, 1F, 1F);

				}
			}
		}
	}

	@SubscribeEvent
	public void hurtEvent(LivingHurtEvent event){
		IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(event.getEntity());
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			// Adrenaline
			if (playerData.isAbilityEquipped(online.magicksaddon.magicsaddonmod.lib.Strings.adrenaline)) {
				if (player.getHealth() - event.getAmount() <= player.getMaxHealth() / 4){
					System.out.println(player.getHealth() + " / " + player.getMaxHealth());
					playerData.getStrengthStat().addModifier("adrenaline", 5, false);
					PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
					System.out.println("Strength Added");
				}
			}
			// Critical Surge
			if (playerData.isAbilityEquipped(online.magicksaddon.magicsaddonmod.lib.Strings.critical_surge)){
				if (player.getHealth() - event.getAmount() <= player.getMaxHealth() / 4){
					System.out.println(player.getHealth() + " / " + player.getMaxHealth());
					playerData.getMagicStat().addModifier("critical_surge", 5, false);
					PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
					System.out.println("Magic Added");
				}
			}
			// Remove If Statement
			// Utils.isPlayerLowHP(player) == false
			if (player.getHealth() + 1 >= player.getMaxHealth() / 4) {
				System.out.println(player.getHealth() + " / " + player.getMaxHealth());
				playerData.getStrengthStat().removeModifier("adrenaline");
				System.out.println("Adrenaline Removed");
				playerData.getMagicStat().removeModifier("critical_surge");
				System.out.println("Critical Surge Removed");
				PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
			}



		}
	}
}