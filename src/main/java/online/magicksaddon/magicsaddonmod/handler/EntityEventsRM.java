package online.magicksaddon.magicsaddonmod.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;
import online.magicksaddon.magicsaddonmod.network.stc.SCSyncGlobalCapabilityToAllPacketRM;
import org.apache.logging.log4j.core.jmx.Server;

public class EntityEventsRM {
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		Player oPlayer = event.getOriginal();
		Player nPlayer = event.getEntity();
				
		oPlayer.reviveCaps();
		IGlobalCapabilitiesRM oldPlayerData = ModCapabilitiesRM.getGlobal(oPlayer);
		IGlobalCapabilitiesRM newPlayerData = ModCapabilitiesRM.getGlobal(nPlayer);
		
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
	private void updateDriveAbilities(Player player, String AbilityName, String formName) {
		IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);

		if(playerData.isAbilityEquipped(AbilityName)) { //if ability to use dark form is equipped
			if(!playerData.getDriveFormMap().containsKey(formName)) {
				playerData.setDriveFormLevel(formName, 1); //We give the form to the player
			}
			playerData.addVisibleDriveForm(formName);
		} else { // If ability to use dark form is NOT equipped
			playerData.remVisibleDriveForm(formName);
		}		
	}


	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(event.getEntity());

		if(event.getEntity() instanceof Player player) {
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			if(playerData != null && globalData != null) {
				updateDriveAbilities(player, StringsRM.darkPower, KingdomKeysReMind.MODID+":"+ StringsRM.darkMode);
				updateDriveAbilities(player, StringsRM.rageAwakened, KingdomKeysReMind.MODID+":"+ StringsRM.rageForm);
				updateDriveAbilities(player, StringsRM.wayToLight, KingdomKeysReMind.MODID+":"+ StringsRM.light);

				// Light/Darkness Within

				int boostWithin = (playerData.getStrengthStat().getStat() + playerData.getMagicStat().getStat()) / 3;

				int darknessWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsRM.darknessBoost) * 0.05F);
				int lightWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsRM.lightBoost) * 0.05F);

				if (playerData.isAbilityEquipped(StringsRM.lightWithin)) {
					playerData.getStrengthStat().addModifier("light_within", lightWithinBoost, false);
					playerData.getMagicStat().addModifier("light_within", lightWithinBoost, false);
				} else {
					playerData.getStrengthStat().removeModifier("light_within");
					playerData.getMagicStat().removeModifier("light_within");
				}
				if (playerData.isAbilityEquipped(StringsRM.darknessWithin)){
					playerData.getStrengthStat().addModifier("darkness_within", darknessWithinBoost, false);
					playerData.getMagicStat().addModifier("darkness_within", darknessWithinBoost, false);
				} else {
					playerData.getStrengthStat().removeModifier("darkness_within");
					playerData.getMagicStat().removeModifier("darkness_within");
				}

				if (!playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
					playerData.getStrengthStat().removeModifier("Riskcharge");
				}

			}

		}

		if (globalData != null) {

			// RC Cooldown mechanic

			if (globalData.getRCCooldownTicks() > 0){
				globalData.setRCCooldownTicks(globalData.getRCCooldownTicks() - 1);
				//if (globalData.getRCCooldownTicks() <= 0){

				//}
			}

			// Spells go Down Below
			if(globalData.getStepTicks() > 0) {
				globalData.remStepTicks(1);
				if (globalData.getStepTicks() <= 0) { // Step has finished, notify all the clients about it
					PacketHandlerRM.syncGlobalToAllAround((Player) event.getEntity(), (IGlobalCapabilitiesRM) globalData);
					if(event.getEntity() instanceof Player player) {
						IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
						if (playerData.isAbilityEquipped(StringsRM.darkStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
							player.level().playSound(null, player.blockPosition(), ModSoundsRM.DARKSTEP2.get(), SoundSource.PLAYERS, 1F, 1F);
						}
						if (playerData.isAbilityEquipped(StringsRM.lightStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
							player.level().playSound(null, player.blockPosition(), ModSoundsRM.LIGHTSTEP2.get(), SoundSource.PLAYERS, 1F, 1F);
						}
					}
				}
			}

			// Slow
			if (globalData.getSlowTicks() > 0) {
				globalData.remSlowTicks(1);
				if (globalData.getSlowTicks() > 0){

				}
				if (globalData.getSlowTicks() <= 0) {
					event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.25 + (0.25 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
					event.getEntity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Slow", 0.25 + (0.25 * globalData.getSlowLevel()), AttributeModifier.Operation.MULTIPLY_BASE));
				}
			}

			// Haste
			if (event.getEntity() instanceof Player player){
				if (globalData.getHasteTicks() > 0) {
					globalData.remHasteTicks(1);
					System.out.println(globalData.getHasteTicks());
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
					if (globalData.getBerserkTicks() <= 0) {
						IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
						playerData.getStrengthStat().removeModifier("berserk");
						playerData.getDefenseStat().removeModifier("berserk");
						if(!event.getEntity().level().isClientSide) {
							PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player); //Sync KK stat packet
							PacketHandlerRM.syncGlobalToAllAround((Player) event.getEntity(), (IGlobalCapabilitiesRM) globalData);
						}
					}
				}
			}

			// HP / MP / EXP Walker
			if (event.getEntity() instanceof Player player) {
				IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
				if(playerData != null) {
					if (player.isSprinting()) {
						if (player.tickCount % 40 == 0 && playerData.isAbilityEquipped(StringsRM.hpWalker)) {
							int hpWalkerMult = playerData.getNumberOfAbilitiesEquipped(StringsRM.hpWalker);
							player.heal(1 * hpWalkerMult);
						}
						if (player.tickCount % 50 == 0 && playerData.isAbilityEquipped(StringsRM.mpWalker)) {
							if (!playerData.getRecharge()) {
								int mpWalkerMult = playerData.getNumberOfAbilitiesEquipped(StringsRM.mpWalker);
								playerData.addMP(0.5 * mpWalkerMult);
							}
						}
						if (!player.level().isClientSide && player.tickCount % 20 == 0 && playerData.isAbilityEquipped(StringsRM.expWalker)) {
							playerData.addExperience(player, 1, false, true);
						}
					}
				}
			}


		}
	}


	
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event){
		IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(event.getEntity());
		if (event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if (1 == globalData.getAutoLifeActive()){
				if (player.getHealth() <= 0){
					globalData.remAutoLifeActive(1);
					PacketHandlerRM.syncGlobalToAllAround((Player) event.getEntity(), (IGlobalCapabilitiesRM) globalData);
					event.setCanceled(true);
					player.setHealth(10.0F);
					player.invulnerableTime = 10;
					player.removeAllEffects();
					player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 10));
					player.level().playSound(null, player.blockPosition(), ModSoundsRM.AUTOLIFE.get(), SoundSource.PLAYERS, 1F, 1F);
				}
			}
		}
	}

	@SubscribeEvent
	public void hurtEvent(LivingHurtEvent event){
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			// Adrenaline
			if (playerData.isAbilityEquipped(StringsRM.adrenaline)) {
				if (player.getHealth() - event.getAmount() <= player.getMaxHealth() / 4){
					playerData.getStrengthStat().addModifier("adrenaline", 5, false);
					PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
				}
			}
			// Critical Surge
			if (playerData.isAbilityEquipped(StringsRM.critical_surge)){
				if (player.getHealth() - event.getAmount() <= player.getMaxHealth() / 4){
					playerData.getMagicStat().addModifier("critical_surge", 5, false);
					PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
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