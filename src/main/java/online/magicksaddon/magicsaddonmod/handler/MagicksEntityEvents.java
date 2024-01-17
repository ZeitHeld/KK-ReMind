package online.magicksaddon.magicsaddonmod.handler;

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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
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
			//if (playerData.getSoAState() == SoAState.COMPLETE){
				/*
				playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.darkMode, 1);
				playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.light, 1);
				playerData.setDriveFormLevel(MagicksAddonMod.MODID+":"+ StringsX.rageForm, 1);
				 */
				//System.out.println(playerData.getDriveFormMap());

			//}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		Player oPlayer = event.getOriginal();
		Player nPlayer = event.getEntity();
				
		oPlayer.reviveCaps();
		IGlobalCapabilitiesX oldPlayerData = ModCapabilitiesX.getGlobal(oPlayer);
		IGlobalCapabilitiesX newPlayerData = ModCapabilitiesX.getGlobal(nPlayer);
		
		newPlayerData.setPrestigeLvl(oldPlayerData.getPrestigeLvl());
		newPlayerData.setNGPWarriorCount(oldPlayerData.getNGPWarriorCount());
		newPlayerData.setNGPMysticCount(oldPlayerData.getNGPMysticCount());
		newPlayerData.setNGPGuardianCount(oldPlayerData.getNGPGuardianCount());
		newPlayerData.setSTRBonus(oldPlayerData.getSTRBonus());
		newPlayerData.setMAGBonus(oldPlayerData.getMAGBonus());
		newPlayerData.setDEFBonus(oldPlayerData.getDEFBonus());

		oPlayer.invalidateCaps();
	}
	
	/**
	 * 
	 * @param player
	 * @param AbilityName StringsX.darkPower
	 * @param formName ModID + StringsX.darkMode
	 * @param formEXP getDarkModeEXP()
	 */
	private void updateDriveAbilities(Player player, String AbilityName, String formName, int formEXP) {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
		IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);

		if(playerData.isAbilityEquipped(AbilityName)) { //if ability to use dark form is equipped
			if(!playerData.getDriveFormMap().containsKey(formName)) {
				playerData.setDriveFormLevel(formName, 1); //We give the form to the player
				if(globalData.getDarkModeEXP() > 0) { //If we have some amount of exp stored in the new capability give it to the KK form so it properly gets leveled up
					playerData.setDriveFormExp(player, formName, formEXP);
					//System.out.println("Leveled dark form with "+formEXP+"xp points");
				}
			}
		} else { // If ability to use dark form is NOT equipped
			if(playerData.getDriveFormMap().containsKey(formName)) {
				playerData.getDriveFormMap().remove(formName);
			}
		}		
	}


	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(event.getEntity());

		if(event.getEntity() instanceof Player player) {
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			if(playerData != null && globalData != null) {

				updateDriveAbilities(player, StringsX.darkPower, MagicksAddonMod.MODID+":"+ StringsX.darkMode, globalData.getDarkModeEXP());
				//TODO uncomment this 
				// updateDriveAbilities(player, StringsX.rageAwakened, MagicksAddonMod.MODID+":"+ StringsX.rageForm, globalData.getRageModeEXP());
				updateDriveAbilities(player, StringsX.wayToLight, MagicksAddonMod.MODID+":"+ StringsX.light, globalData.getLightFormEXP());
				//TODO Remove this line when Rage Form can get EXP
				updateDriveAbilities(player, StringsX.rageAwakened, MagicksAddonMod.MODID+":"+ StringsX.rageForm, 0);

				// Additional Forms Here
				// Light/Darkness Within

				int boostWithin = (playerData.getStrengthStat().getStat() + playerData.getMagicStat().getStat()) / 4;

				int darknessWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsX.darknessBoost) * 0.05F);
				int lightWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsX.lightBoost) * 0.05F);

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

		if (globalData != null) {
			// Spells go Down Below
			if(globalData.getStepTicks() > 0) {
				globalData.remStepTicks(1);
				if (globalData.getStepTicks() <= 0) { // Step has finished, notify all the clients about it
					PacketHandlerX.syncGlobalToAllAround((Player) event.getEntity(), (IGlobalCapabilitiesX) globalData);
					if(event.getEntity() instanceof Player player) {
						IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
						if (playerData.isAbilityEquipped(StringsX.darkStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
							player.level().playSound(null, player.blockPosition(), MagicSounds.DARKSTEP2.get(), SoundSource.PLAYERS, 1F, 1F);
						}
						if (playerData.isAbilityEquipped(StringsX.lightStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
							//System.out.println(player.level.isClientSide);
							player.level().playSound(null, player.blockPosition(), MagicSounds.LIGHTSTEP2.get(), SoundSource.PLAYERS, 1F, 1F);
						}
					}
				}
			}

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
			if (event.getEntity() instanceof Player player){
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
			if (event.getEntity() instanceof Player player){
				if (globalData.getBerserkTicks() > 0) {
					globalData.remBerserkTicks(1);
					//System.out.println("Berserk Level: " + globalData.getBerserkLevel() + " " + "Berserk Ticks Remaining: " + globalData.getBerserkTicks());
					if (globalData.getBerserkTicks() <= 0) {
						IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
						playerData.getStrengthStat().removeModifier("berserk");
						playerData.getDefenseStat().removeModifier("berserk");
						if(!event.getEntity().level().isClientSide) {
							//I think you might not need this one I commented
							PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player); //Sync KK stat packet
							PacketHandlerX.syncGlobalToAllAround((Player) event.getEntity(), (IGlobalCapabilitiesX) globalData);
						}
					}
				}
			}

			// HP / MP / EXP Walker
			if (event.getEntity() instanceof Player player) {
				IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
				if(playerData != null) {
					if (player.isSprinting()) {
						if (player.tickCount % 40 == 0 && playerData.isAbilityEquipped(StringsX.hpWalker)) {
							int hpWalkerMult = playerData.getNumberOfAbilitiesEquipped(StringsX.hpWalker);
							player.heal(1 * hpWalkerMult);
						}
						if (player.tickCount % 50 == 0 && playerData.isAbilityEquipped(StringsX.mpWalker)) {
							if (!playerData.getRecharge()) {
								int mpWalkerMult = playerData.getNumberOfAbilitiesEquipped(StringsX.mpWalker);
								playerData.addMP(0.5 * mpWalkerMult);
							}
						}
						if (!player.level().isClientSide && player.tickCount % 20 == 0 && playerData.isAbilityEquipped(StringsX.expWalker)) {
							playerData.addExperience(player, 1, false, true);
						}
					}
				}
			}


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
					player.invulnerableTime = 10;
					globalData.remAutoLifeActive(1);
					player.removeAllEffects();
					player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 10));
					player.level().playSound(null, player.blockPosition(), MagicSounds.AUTOLIFE.get(), SoundSource.PLAYERS, 1F, 1F);
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
					//System.out.println(player.getHealth() + " / " + player.getMaxHealth());
					playerData.getStrengthStat().addModifier("adrenaline", 5, false);
					PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
					//System.out.println("Strength Added");
				}
			}
			// Critical Surge
			if (playerData.isAbilityEquipped(StringsX.critical_surge)){
				if (player.getHealth() - event.getAmount() <= player.getMaxHealth() / 4){
					//System.out.println(player.getHealth() + " / " + player.getMaxHealth());
					playerData.getMagicStat().addModifier("critical_surge", 5, false);
					PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
					//System.out.println("Magic Added");
				}
			}
			if (player.getHealth() + 1 >= player.getMaxHealth() / 4) {
				playerData.getStrengthStat().removeModifier("adrenaline");
				playerData.getMagicStat().removeModifier("critical_surge");
				PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
			}

			//Protect Abilities


		}
	}

}