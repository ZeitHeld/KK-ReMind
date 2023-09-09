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
        float dmgMult = getDamageMult(level);
        System.out.println("Kek");
        
        switch(level) {

            case 0:
                ThrowableProjectile blizzard = new BlizzardEntity(player.level, player, dmgMult);
                player.level.addFreshEntity(blizzard);
                blizzard.shootFromRotation(player, player.getXRot(),player.getYRot(), 0,2F,0);
                // sound line when I find the sound I want
                break;
            case 1: // -ra will have 2 extra projectiles.
                for(int i = -1; i < 2; i++) {
                    ThrowableProjectile blizzard1 = new BlizzardEntity(player.level, player, dmgMult);
                    ThrowableProjectile blizzard2 = new BlizzardEntity(player.level, player, dmgMult);
                    ThrowableProjectile blizzard3 = new BlizzardEntity(player.level, player, dmgMult);
                    player.level.addFreshEntity(blizzard1);
                    player.level.addFreshEntity(blizzard2);
                    player.level.addFreshEntity(blizzard3);
                    blizzard1.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2F, i*3);
                    blizzard2.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2F, i*3);
                    blizzard3.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2F, i*3);
                    break;
                }
            case 2: //-ga will have 5 total projectiles
                for(int i = -1; i <2; i++) {
                    ThrowableProjectile blizzard4 = new BlizzardEntity(player.level, player, dmgMult);
                    ThrowableProjectile blizzard5 = new BlizzardEntity(player.level, player, dmgMult);
                    ThrowableProjectile blizzard6 = new BlizzardEntity(player.level, player, dmgMult);
                    player.level.addFreshEntity(blizzard4);
                    player.level.addFreshEntity(blizzard5);
                    player.level.addFreshEntity(blizzard6);
                    blizzard4.shootFromRotation(player, player.getXRot(), player.getYRot(), i*6, 2F, 0);
                    blizzard5.shootFromRotation(player, player.getXRot(), player.getYRot(), i*7, 2F, 0);
                    blizzard6.shootFromRotation(player, player.getXRot(), player.getYRot(), i*8, 2F, 0);
                    break;
                }





            }
    }
}
