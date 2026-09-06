package online.remind.remind.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.ability.ModAbilitiesRM;
import online.remind.remind.client.sound.ModSoundsRM;
import online.remind.remind.entity.magic.FaithEntity;
import online.remind.remind.lib.StringsRM;

public class magicFaith extends Magic {

    public magicFaith(ResourceLocation registryName, int tier, ResourceLocation gmAbility) {
        super(registryName, false, gmAbility);
        setTier(tier);
    }

    @Override
    public void magicUse(LivingEntity player, LivingEntity caster, float fullMPBlastMult, LivingEntity lockOnEntity){

        float dmgMult = getDamageMult() + abilityStacks(caster, ModAbilitiesRM.LIGHT_BOOST) * 0.25F;
        dmgMult *= fullMPBlastMult;

        // Casting Faith go here
        switch(getTier()){
            case 0-> {
                FaithEntity faith = new FaithEntity(player.level(), player, dmgMult, lockOnEntity);
                faith.setOwner(caster);
                faith.setPos(player.getX(), player.getY() + 1.8F, player.getZ());
                player.level().addFreshEntity(faith);
            }
        }

    }

    @Override
    public void playMagicCastSound(LivingEntity player, LivingEntity caster) {
        player.level().playSound(null,player.blockPosition(), ModSoundsRM.PLAYER_CAST.get(), SoundSource.PLAYERS,1,1);
    }
}
