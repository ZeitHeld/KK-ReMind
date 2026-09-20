package online.remind.remind.magic.attacks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.client.sound.ModSoundsRM;

public class attackBraver extends Magic {

    public attackBraver(ResourceLocation registryName, boolean hasToSelect, int tier, ResourceLocation gmAbility) {
        super(registryName, hasToSelect, gmAbility);
        setTier(tier);
    }
    @Override
    public void magicUse(
            LivingEntity player,
            LivingEntity caster,
            float fullMPBlastMult,
            LivingEntity lockOnEntity
    ) {
        if (lockOnEntity == null || !lockOnEntity.isAlive()) {
            return;
        }

        if (!(caster instanceof ServerPlayer serverPlayer)) {
            return;
        }

        float dmg;

        switch (getTier()) {
            case 0 -> dmg = casterStrengthStat(caster) * 1.50F;
            case 1 -> dmg = casterStrengthStat(caster) * 1.75F;
            case 2 -> dmg = casterStrengthStat(caster) * 2.00F;
            default -> dmg = casterStrengthStat(caster);
        }

        dmg *= fullMPBlastMult;

        BraverSequenceHandler.start(
                serverPlayer,
                lockOnEntity,
                dmg
        );
    }

    @Override
    public void playMagicCastSound(LivingEntity livingEntity, LivingEntity livingEntity1) {
        livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), ModSoundsRM.LIMIT_BREAK.get(), SoundSource.PLAYERS, 1F, 1F);
    }
}