package online.remind.remind.reactioncommands;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.damagesource.DarknessDamageSource;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.driveform.ModDriveFormsRM;
import org.joml.Vector3f;

import java.util.List;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class RagingBurstRC extends ReactionCommand {


    public RagingBurstRC(ResourceLocation registryName, boolean constantCheck) {
        super(registryName, constantCheck);
    }

    int radius = 5;





    @Override
    public void onUse(Player player, LivingEntity livingEntity, LivingEntity livingEntity1) {
        if(conditionsToAppear(player,player)){
            IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
            IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);


            playerData.addFP(-1000);
            globalData.setRCCooldownTicks(20);

            // The Attack

            float dmg = DamageCalculation.getMagicDamage(player) + DamageCalculation.getStrengthDamage(player) * 0.25F;

            //System.out.println("Spell Damage - Splash (Before Mult): "+ dmg);
            //System.out.println(dmg);
            //System.out.println("Spell Damage - Splash (After Mult): "+ dmg*dmgMult);
            List<LivingEntity> targetList = Utils.getLivingEntitiesInRadiusExcludingParty((player), player, radius, radius, radius);
            for (LivingEntity e : targetList) {
                e.hurt(DarknessDamageSource.getDarknessDamage(e, player), dmg);
                playerData.setDriveFormExp(player, playerData.getActiveDriveForm(), (int) (playerData.getDriveFormExp(playerData.getActiveDriveForm()) + (1)));
                player.level().addParticle(new DustParticleOptions(new Vector3f(0.1F,0F,0F),1F),e.getX() + e.level().random.nextDouble() - 0.55D, e.getY()+ e.level().random.nextDouble() *2D, e.getZ() + e.level().random.nextDouble() - 0.55D, 0, 0, 0);

            }
        }
    }

    @Override
    public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
        if(playerData != null){
            if(playerData.getActiveDriveForm().equals(ModDriveFormsRM.RAGE.get().getRegistryName().toString())){
                if(globalData.getRiskchargeCount() == 3 && globalData.getRCCooldownTicks() == 0){
                    return true;
                }
            }
        }
        return false;
    }
}