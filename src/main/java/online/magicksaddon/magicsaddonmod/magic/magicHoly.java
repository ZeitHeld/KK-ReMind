package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import online.kingdomkeys.kingdomkeys.magic.Magic;


import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.entity.magic.HolyEntity;
import online.magicksaddon.magicsaddonmod.client.model.HolyModel;
//Deleting After Testing and custom entities are made


public class magicHoly extends Magic {

    public magicHoly(ResourceLocation registryName, boolean hasToSelect, int maxLevel, String gmAbility, int order) {
        super(registryName, hasToSelect, maxLevel, gmAbility, order);
    }

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult){
        //IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);
        System.out.println("Casting!");
        float dmgMult = getDamageMult(level);
        dmgMult *= fullMPBlastMult;
        System.out.println(dmgMult);


        if (level == 0) {
            System.out.println(level);
            for(int i = -1; i <1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()-2, 0, 1F, 0);
                // sound line when I find the sound I want
            }
                for (int i = -1; i < 1; i++) {
                    HolyEntity holy = new HolyEntity(player.level, player, dmgMult);
                    holy.setCaster(player.getDisplayName().getString());
                    player.level.addFreshEntity(holy);
                    holy.shootFromRotation(player, player.getXRot(), player.getYRot()+2, 0, 0.75F, 0);
                    // sound line when I find the sound I want
                }
                for (int i = -1; i < 0; i++) {
                    HolyEntity holy = new HolyEntity(player.level, player, dmgMult);
                    holy.setCaster(player.getDisplayName().getString());
                    player.level.addFreshEntity(holy);
                    holy.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 0.75F, 0);
                    // sound line when I find the sound I want
                }
        } else if (level == 1) {
            System.out.println(level);
            for(int i = -1; i < 2; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.25F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1F, 0);
                }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.25F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()+4, 0, 0.75F, 0);
                }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.25F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()+2, 0, 0.75F, 0);
                }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.25F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()-4, 0, 0.65F, 0);
                }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.25F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()-2, 0, 0.65F, 0);
                }
        } else if (level == 2) {
            System.out.println(level);
            for(int i = -1; i < 2; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.5F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1F, 0);
            }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.5F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()+4, 0, 0.75F, 0);
            }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.5F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()-4, 0, 0.75F, 0);
            }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.5F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()-8, 0, 0.65F, 0);
            }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.5F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()+8, 0, 0.65F, 0);
            }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.5F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()+12, 0, 0.55F, 0);
            }
            for(int i = -1; i < 1; i++) {
                HolyEntity holy = new HolyEntity(player.level, player, dmgMult * 0.5F);
                holy.setCaster(player.getDisplayName().getString());
                player.level.addFreshEntity(holy);
                holy.shootFromRotation(player, player.getXRot(), player.getYRot()-12, 0, 0.55F, 0);
            }
        }
    }
}
