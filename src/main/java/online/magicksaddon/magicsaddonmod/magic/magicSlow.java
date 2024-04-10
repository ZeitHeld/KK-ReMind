package online.magicksaddon.magicsaddonmod.magic;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.kingdomkeys.kingdomkeys.lib.Party.Member;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;

public class magicSlow extends Magic {
	public magicSlow(ResourceLocation registryName, boolean hasToSelect, int maxLevel) {
		super(registryName, hasToSelect, maxLevel, null);
	}

	@Override
	protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnTarget) {

		float radius = 2 + (level);
		List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(radius, radius, radius));
		Party casterParty = ModCapabilities.getWorld(player.level()).getPartyFromMember(player.getUUID());

		if (casterParty != null && !casterParty.getFriendlyFire()) {
			for (Member m : casterParty.getMembers()) {
				list.remove(player.level().getPlayerByUUID(m.getUUID()));
			}
		}
		int time = (int) (ModCapabilities.getPlayer(caster).getMaxMP() * ((level * 0.75) + 5) + 5);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Entity e = (Entity) list.get(i);
				if (e instanceof LivingEntity lEntity) {
					IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(lEntity);
					if (globalData != null) {
						lEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,time, level + 1));
						lEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,time, level + 1));
System.out.println(time);
						globalData.setSlowTicks(time, level); // Slow Time
						globalData.setSlowCaster(player.getDisplayName().getString());
					}
				}
			}
			player.swing(InteractionHand.MAIN_HAND);
			player.level().playSound(null, player.blockPosition(), ModSoundsRM.SLOW.get(), SoundSource.PLAYERS, 1F, 1F);
		}
	}
}
