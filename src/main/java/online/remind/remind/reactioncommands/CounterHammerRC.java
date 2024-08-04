package online.remind.remind.reactioncommands;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.damagesource.KeybladeDamageSource;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.kingdomkeys.kingdomkeys.reactioncommands.ReactionCommand;
import online.kingdomkeys.kingdomkeys.util.Utils;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.lib.StringsRM;
import online.remind.remind.network.PacketHandlerRM;
import org.joml.Vector3f;

import java.util.List;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public class CounterHammerRC extends ReactionCommand {
    public CounterHammerRC(ResourceLocation registryName, boolean constantCheck) {
        super(registryName, constantCheck);
    }

    @Override
    public void onUse(Player player, LivingEntity target, LivingEntity lockedOnEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
        float dmg = (float) playerData.getStrengthStat().get();
        float dmgMult = 1 + (ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(Strings.criticalBoost) * 0.25F);
        float radius = 3 + (ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(Strings.criticalBoost) * 0.5F);

        double X = player.getX();
        double Z = player.getZ();

        globalData.remCanCounter(1);
        System.out.println(globalData.getCanCounter());
        player.swing(InteractionHand.MAIN_HAND);
        PacketHandlerRM.syncGlobalToAllAround(player, globalData);

        List<LivingEntity> targetList = Utils.getLivingEntitiesInRadiusExcludingParty((player), player, radius, radius, radius);
        for (LivingEntity e : targetList) {
            for (int t = 1; t < 360; t += 20) {
                for (int s = 1; s < 360; s += 20) {
                    double x = X + (radius * Math.cos(Math.toRadians(s)) * Math.sin(Math.toRadians(t)));
                    double z = Z + (radius * Math.sin(Math.toRadians(s)) * Math.sin(Math.toRadians(t)));
                    ((ServerLevel) player.level()).sendParticles(new DustParticleOptions(new Vector3f(1F,1F,1F),1F),x,player.getY() ,z,1,0,0,0,0);
                    
                    e.hurt(e.damageSources().generic(), dmg * dmgMult);
                }
            }
        }
    }

    @Override
    public boolean conditionsToAppear(Player player, LivingEntity livingEntity) {
        IPlayerCapabilities playerData = ModCapabilities.getPlayer(player);
        IGlobalCapabilitiesRM globalData = ModCapabilitiesRM.getGlobal(player);
        if (playerData != null ){
           if (playerData.isAbilityEquipped(StringsRM.counterHammer) && globalData.getCanCounter() >= 1) {
               return true;
            }
        }
        return false;
    }
}

