package online.remind.remind.handler;

import org.joml.Vector3f;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.driveform.ModDriveFormsRM;
import online.remind.remind.lib.StringsRM;

public class ClientEventsRM {

    @SubscribeEvent
	public void RenderEntity(RenderLivingEvent.Pre event){
		if (event.getEntity() != null){
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
				IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(event.getEntity());
				if (playerData != null){
					// Light and Dark Step SFX
					if(globalData.getStepTicks() > 0){
						event.setCanceled(true);
						player.invulnerableTime = globalData.getStepTicks();
						if (globalData.getStepType() == StringsRM.twilightStepType){
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(1F,1F,1F),1F),player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0F,0F,0F),1F),player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0.5F,0.5F),1F),player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
						} else if (globalData.getStepType() == StringsRM.rageStepType){
							player.level().addParticle(new DustParticleOptions(new Vector3f(0.1F,0F,0F),1F),player.getX() + player.level().random.nextDouble() - 0.55D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.55D, 0, 0, 0);
							player.level().addParticle(new DustParticleOptions(new Vector3f(0.3F,0F,0F),1F),player.getX() + player.level().random.nextDouble() - 0.55D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.55D, 0, 0, 0);
							player.level().addParticle(new DustParticleOptions(new Vector3f(0F,0F,0F),1F),player.getX() + player.level().random.nextDouble() - 0.55D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.55D, 0, 0, 0);
						} else if (globalData.getStepType() == StringsRM.darkStepType && !playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.twilight)) {
							player.level().addAlwaysVisibleParticle(ParticleTypes.SQUID_INK, player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0F,0.5F),1F),player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0F,1F),1F),player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.2F,0F,0F),1F),player.getX() + player.level().random.nextDouble() - 0.55D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.55D, 0, 0, 0);
						} else if (globalData.getStepType() == StringsRM.lightStepType && !playerData.getActiveDriveForm().equals(KingdomKeysReMind.MODID + ":" + StringsRM.twilight)) {
							player.level().addAlwaysVisibleParticle(ParticleTypes.END_ROD, player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
							player.level().addAlwaysVisibleParticle(ParticleTypes.CLOUD, player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0F,0.9F,0.9F),1F),player.getX() + player.level().random.nextDouble() - 0.5D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(1F,1F,0.7F),1F),player.getX() + player.level().random.nextDouble() - 0.55D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.55D, 0, 0, 0);
						}
					}

					// Rage Form Active and Walk particles
					if (playerData.getActiveDriveForm().equals(ModDriveFormsRM.RAGE.get().getRegistryName().toString())){
						player.level().addParticle(new DustParticleOptions(new Vector3f(0.1F,0F,0F),1F),player.getX() + player.level().random.nextDouble() - 0.55D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.55D, 0, 0, 0);
	
						if (player.onGround()){
							player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.2F,0F,0F),1F),player.getX(), player.getY(), player.getZ(), 0, 0, 0);
	
						}
					}

					// Twilight Form Active
					if (playerData.getActiveDriveForm().equals(ModDriveFormsRM.TWILIGHT.get().getRegistryName().toString())){
						player.level().addParticle(new DustParticleOptions(new Vector3f(0.45F,0.45F,0.45F),0.25F),player.getX() + player.level().random.nextDouble() - 0.45D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.45D, -1, -1, -1);
						player.level().addParticle(new DustParticleOptions(new Vector3f(0.55F,0.55F,0.55F),0.25F),player.getX() + player.level().random.nextDouble() - 0.45D, player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.45D, -1, -1, -1);

					}

					// When I can get particles in other hand
					//if (playerData.getActiveDriveForm().equals(ModDriveFormsRM.DARK.get().getRegistryName().toString())){
						//player.level().addAlwaysVisibleParticle(new DustParticleOptions(new Vector3f(0.5F,0F,0.5F),1F),player.getX(), player.getY()+ player.level().random.nextDouble() *2D, player.getZ() + player.level().random.nextDouble() - 0.5D, 0, 0, 0);
					//}
				}
			}
		}
	}
}
