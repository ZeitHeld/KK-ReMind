package online.remind.remind.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.remind.remind.lib.StringsRM;

public class magicFaith extends Magic {

    public magicFaith(ResourceLocation registryName, int maxLevel, String gmAbility) {
        super(registryName, false, maxLevel, gmAbility);
    }

    @Override
    public void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnEntity){

        float dmgMult = getDamageMult(level) + ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(StringsRM.lightBoost) * 0.2F;
        dmgMult *= fullMPBlastMult;

        // Casting Faith go here
        switch(level){
            case 0:

                break;
        }

    }

    @Override
    protected void playMagicCastSound(Player player, Player player1, int i) {

    }
}
