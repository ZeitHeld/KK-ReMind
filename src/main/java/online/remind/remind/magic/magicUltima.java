package online.remind.remind.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.client.sound.ModSoundsRM;
import online.remind.remind.entity.magic.UltimaEntity;

public class magicUltima extends Magic {
	public magicUltima(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility) {
		super(registryName, hasToSelect, maxLevel, gmAbility);
	}

	@Override
	public void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnTarget) {
		float dmgMult = getDamageMult(level);
		dmgMult *= fullMPBlastMult;

		switch (level) {
		case 0:
			ThrowableProjectile ultima = new UltimaEntity(player.level(), player, dmgMult);
			ultima.setOwner(caster);
			ultima.setPos(player.getX(), player.getY() + 4, player.getZ());
			player.level().addFreshEntity(ultima);
			ultima.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 0.75F, 0);
			break;
		case 1:
			// Apocalypse
			

			break;
		}

	}

	@Override
	protected void playMagicCastSound(Player player, Player caster, int level) {
		player.level().playSound(null, player.blockPosition(), ModSoundsRM.PLAYER_CAST.get(), SoundSource.PLAYERS, 1F, 1F);
	}

}
