package online.magicksaddon.magicsaddonmod.handler;

import com.mojang.math.Vector3f;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLivingEvent;
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


		// Testing Stuff


		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.flameSalvo, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.bubbleBlaster, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.thunderStorm, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.bioBarrage, true);
		playerData.addShotlockToList(MagicksAddonMod.MODID+":"+ StringsX.meteorShower, true);
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
					System.out.println("Leveled dark form with "+formEXP+"xp points");
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
		if(event.getEntity() instanceof Player player) {
			
			IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
			IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(player);
			if(playerData != null) {
				updateDriveAbilities(player, StringsX.darkPower, MagicksAddonMod.MODID+":"+ StringsX.darkMode, globalData.getDarkModeEXP());
				//updateDriveAbilities(player, StringsX.rageAwakened, MagicksAddonMod.MODID+":"+ StringsX.rageForm, globalData.getRageModeEXP());
				updateDriveAbilities(player, StringsX.wayToLight, MagicksAddonMod.MODID+":"+ StringsX.light, globalData.getLightFormEXP());

				// Additional Forms Here
				// Light/Darkness Within

				int boostWithin = (playerData.getStrengthStat().getStat() + playerData.getMagicStat().getStat()) / 4;

				int darknessWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsX.darknessBoost) * 0.05F);
				int lightWithinBoost = (int) (boostWithin * ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsX.lightBoost) * 0.05F);

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

			if(globalData.getStepTicks() > 0) {
				globalData.remStepTicks(1);
				if (globalData.getStepTicks() <= 0) {
					PacketHandlerX.syncGlobalToAllAround((Player) event.getEntity(), (IGlobalCapabilitiesX) globalData);
					if(event.getEntity() instanceof Player player) {
						IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
						if (playerData.isAbilityEquipped(StringsX.darkStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
							player.level.playSound(null, player.blockPosition(), MagicSounds.DARKSTEP2.get(), SoundSource.PLAYERS, 1F, 1F);
						}
						if (playerData.isAbilityEquipped(StringsX.lightStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
							System.out.println(player.level.isClientSide);
							player.level.playSound(null, player.blockPosition(), MagicSounds.LIGHTSTEP2.get(), SoundSource.PLAYERS, 1F, 1F);
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
							playerData.getDefenseStat().removeModifier("berserk");
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
				/*
				System.out.println(player.getHealth() + " / " + player.getMaxHealth());
				playerData.getStrengthStat().removeModifier("adrenaline");
				System.out.println("Adrenaline Removed");
				playerData.getMagicStat().removeModifier("critical_surge");
				System.out.println("Critical Surge Removed");
				*/
				PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
			}



		}
	}


	@SubscribeEvent
	public void RenderEntity(RenderLivingEvent.Pre event){
		if (event.getEntity() != null){
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
				IGlobalCapabilitiesX globalData = ModCapabilitiesX.getGlobal(event.getEntity());
				if (playerData != null){
					// Light and Dark Step SFX
					if(globalData.getStepTicks() > 0){
						event.setCanceled(true);
					//	System.out.println(globalData.getStepTicks());
						if (playerData.isAbilityEquipped(StringsX.darkStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_dark")) {
							player.level.addAlwaysVisibleParticle(ParticleTypes.SQUID_INK, player.getX() + player.level.random.nextDouble() - 0.5D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.5D, 0, 0, 0);
							player.level.addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0F,0.5F),1F),player.getX() + player.level.random.nextDouble() - 0.5D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.5D, 0, 0, 0);
							player.level.addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0F,1F),1F),player.getX() + player.level.random.nextDouble() - 0.5D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.5D, 0, 0, 0);
							player.level.addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.2F,0F,0F),1F),player.getX() + player.level.random.nextDouble() - 0.55D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.55D, 0, 0, 0);
						} else if (playerData.isAbilityEquipped(StringsX.lightStep) || playerData.getActiveDriveForm().equals("magicksaddon:form_light")) {
							player.level.addAlwaysVisibleParticle(ParticleTypes.END_ROD, player.getX() + player.level.random.nextDouble() - 0.5D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.5D, 0, 0, 0);
							player.level.addAlwaysVisibleParticle(ParticleTypes.CLOUD, player.getX() + player.level.random.nextDouble() - 0.5D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.5D, 0, 0, 0);
							player.level.addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0F,0.9F,0.9F),1F),player.getX() + player.level.random.nextDouble() - 0.5D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.5D, 0, 0, 0);
							player.level.addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(1F,1F,0.7F),1F),player.getX() + player.level.random.nextDouble() - 0.55D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.55D, 0, 0, 0);
						}
					}
				}

				// Dark Mode Hand Particles?
				if (playerData.getActiveDriveForm().equals("magicksaddon:form_dark")){
					//player.level.addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0F,0.5F),1F),player.getX(), player.getY()+0.65, player.getZ()-0.5,0.5,0, 0);
				}

				// Rage Form Active and Walk particles
				if (playerData.getActiveDriveForm().equals("magicksaddon:form_rage")){
					player.level.addParticle(new DustParticleOptions(new Vector3f(0.1F,0F,0F),1F),player.getX() + player.level.random.nextDouble() - 0.55D, player.getY()+ player.level.random.nextDouble() *2D, player.getZ() + player.level.random.nextDouble() - 0.55D, 0, 0, 0);

					if (player.isOnGround()){
						player.level.addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.2F,0F,0F),1F),player.getX(), player.getY(), player.getZ(), 0, 0, 0);

					}
				}


			}
		}
	}
}