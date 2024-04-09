package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;

public class magicEsuna extends Magic {


    public magicEsuna(ResourceLocation registryName, boolean hasToSelect, int maxLevel) {
        super(registryName, hasToSelect, maxLevel, null);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnTarget) {
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);

        if (globalData != null) {
            caster.swing(InteractionHand.MAIN_HAND);
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.SONIC_BOOM.getType(), player.getX(), player.getY()+2.3D, player.getZ(), 5, 0D, 0D, 0D, 0D);
            player.level().playSound(null, player.blockPosition(), ModSoundsRM.ESUNA.get(), SoundSource.PLAYERS, 1F, 1F);

            for(MobEffectInstance e : player.getActiveEffects()) {
            	if(e.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
            		player.removeEffect(e.getEffect());
            	}
            }
            
            /*player.removeEffect(MobEffects.BAD_OMEN);
            player.removeEffect(MobEffects.POISON);
            player.removeEffect(MobEffects.HUNGER);
            player.removeEffect(MobEffects.WITHER);
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
            player.removeEffect(MobEffects.BLINDNESS);
            player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            player.removeEffect(MobEffects.CONFUSION);
            player.removeEffect(MobEffects.WEAKNESS);
            player.removeEffect(MobEffects.UNLUCK);*/

            // KK & ReMind Effects
            globalData.setSlowTicks(0,level);


            PacketHandlerRM.syncGlobalToAllAround(player, globalData);
        }
    }


}
