package online.magicksaddon.magicsaddonmod.shotlock;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.magicksaddon.magicsaddonmod.capabilities.IPlayerCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.entity.shotlock.BioBarrageCoreEntity;
import online.magicksaddon.magicsaddonmod.entity.shotlock.BubbleBlasterCoreEntity;

import java.util.List;

public class ShotlockBioBarrage extends Shotlock {

    public ShotlockBioBarrage(String registeryName, int order, int cooldown, int max){
        super(registeryName,order,cooldown,max);
    }

    @Override
    public void onUse(Player player, List<Entity> targetList) {

        IPlayerCapabilitiesMA playerData = ModCapabilitiesMA.getPlayer(player);


        float damage = (float) (DamageCalculation.getMagicDamage(player) * 1.5);
        BioBarrageCoreEntity core = new BioBarrageCoreEntity(player.level, player, targetList, damage);
        core.setPos(player.getX(), player.getY(), player.getZ());
        player.level.addFreshEntity(core);



    }
}
