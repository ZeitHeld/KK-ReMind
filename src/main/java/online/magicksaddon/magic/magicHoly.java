package online.magicksaddon.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.magicksaddon.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.capabilities.ModCapabilitiesMA;
import online.magicksaddon.client.sound.MagicSounds;

//Temporary Entities
import online.kingdomkeys.kingdomkeys.entity.magic.BlizzardEntity;
//Deleting After Testing and custom entities are made

import online.kingdomkeys.kingdomkeys.lib.Strings;



public class magicHoly extends Magic {

    public magicHoly(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility, int order) {
        super(registryName, hasToSelect, maxLevel, gmAbility, order);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult){
        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);
        float dmgMult = getDamageMult(level) + 5 * 0.2F;
    }
}
