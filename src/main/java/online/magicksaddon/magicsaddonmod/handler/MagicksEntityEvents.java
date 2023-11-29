package online.magicksaddon.magicsaddonmod.handler;

import java.awt.Color;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.SoAState;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.lib.StringsX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;

public class MagicksEntityEvents {
	@SubscribeEvent
	public void onJoin(PlayerLoggedInEvent e){
		Player player = e.getEntity();
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);

		// Testing Stuff


		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.flameSalvo, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.bubbleBlaster, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.thunderStorm, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.bioBarrage, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.meteorShower, true);
			if (playerData.getSoAState() == SoAState.COMPLETE){
				playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.darkMode, 1);
				playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.light, 1);
				playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.rageForm, 1);

				System.out.println(playerData.getDriveFormMap());
			}
	}


	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		if(event.getEntity() instanceof Player player) {
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			if(playerData != null) {
				if(playerData.isAbilityEquipped(StringsX.darkPower)) {
					if(!playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+ StringsX.darkMode)) {
						playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.darkMode, 1);
					}
				} else {
					if(playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+ StringsX.darkMode)) {
						playerData.getDriveFormMap().remove(MagicksAddonMod.MODID+":"+ StringsX.darkMode);
					}
				}

				if(playerData.isAbilityEquipped(StringsX.rageAwakened)) {
					if(!playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+ StringsX.rageForm)) {
						playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.rageForm, 1);
					}
				} else {
					if(playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+ StringsX.rageForm)) {
						playerData.getDriveFormMap().remove(MagicksAddonMod.MODID+":"+ StringsX.rageForm);
					}
				}

				if(playerData.isAbilityEquipped(StringsX.wayToLight)) {
					if(!playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID+":"+ StringsX.light)) {
						playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.light, 1);
					}
				} else {
					if (playerData.getDriveFormMap().containsKey(MagicksAddonMod.MODID + ":" + StringsX.light)) {
						playerData.getDriveFormMap().remove(MagicksAddonMod.MODID + ":" + StringsX.light);
					}
				}
				// Additional Forms Here

				// Additional Forms ^

				// Light/Darkness Within Code?

				int boostWithin = (playerData.getStrengthStat().getStat() + playerData.getMagicStat().getStat()) / 4;

				int darknessWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsX.darknessBoost) * 0.25F);
				int lightWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsX.lightBoost) * 0.25F);

				//System.out.println("Initial Within Boost: "+boostWithin);
				//System.out.println("Light Within Boost: "+lightWithinBoost);
				//System.out.println("Darkness Within Boost: "+darknessWithinBoost);

				if (playerData.isAbilityEquipped(StringsX.lightWithin)) {
					playerData.getStrengthStat().addModifier("light_within", lightWithinBoost, false);
					playerData.getMagicStat().addModifier("light_within", lightWithinBoost, false);
				} else {
					playerData.getStrengthStat().removeModifier("light_within");
					playerData.getMagicStat().removeModifier("light_within");
				}
				if (playerData.isAbilityEquipped(StringsX.darknessWithin)){
					playerData.getStrengthStat().addModifier("darkness_within", darknessWithinBoost, false);
					playerData.getMagicStat().addModifier("darkness_within", darknessWithinBoost, false);
				} else {
					playerData.getStrengthStat().removeModifier("darkness_within");
					playerData.getMagicStat().removeModifier("darkness_within");
				}

			}

		}

		IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(event.getEntity());
		//globalData.setBerserkTicks(100, 0);

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
					//System.out.println("Berserk Level: " + globalData.getBerserkLevel() + " " + "Berserk Ticks Remaining: " + globalData.getBerserkTicks());
					if(!event.getEntity().level.isClientSide) {
						if (globalData.getBerserkTicks() <= 0) {
							IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
							playerData.getStrengthStat().removeModifier("berserk");
							PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player); //Sync KK stat packet
							PacketHandlerX.syncGlobalToAllAround((Player) event.getEntity(), (IGlobalCapabilitiesX) globalData);
						}
					}
				}
			}

			// Next Tick Based Spell Goes Below

		}
	}
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event){
		IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(event.getEntity());
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
		IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(event.getEntity());
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			// Adrenaline
			if (playerData.isAbilityEquipped(StringsX.adrenaline)) {
				if (player.getHealth() - event.getAmount() <= player.getMaxHealth() / 4){
					System.out.println(player.getHealth() + " / " + player.getMaxHealth());
					playerData.getStrengthStat().addModifier("adrenaline", 5, false);
					PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
					System.out.println("Strength Added");
				}
			}
			// Critical Surge
			if (playerData.isAbilityEquipped(StringsX.critical_surge)){
				if (player.getHealth() - event.getAmount() <= player.getMaxHealth() / 4){
					System.out.println(player.getHealth() + " / " + player.getMaxHealth());
					playerData.getMagicStat().addModifier("critical_surge", 5, false);
					PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
					System.out.println("Magic Added");
				}
			}
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

	// Berserk Color Changer
	/*@SubscribeEvent
	public void PlayerRender(RenderPlayerEvent.Pre event){
		IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(event.getEntity());
		if (event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);

			

			if (globalData.getBerserkTicks() > 0) {
				Color berserk = new Color(0xA3D50606, true);

			}
			else {
				//RenderSystem.setShaderColor(125,125,125,125);
			}
		}
	}*/
}