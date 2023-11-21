package online.magicksaddon.magicsaddonmod.shotlock;

import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.DamageCalculation;
import online.kingdomkeys.kingdomkeys.lib.Strings;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IPlayerCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.entity.shotlock.FlameSalvoCoreEntity;

@Mod.EventBusSubscriber(modid = MagicksAddonMod.MODID)
public class ShotlockFlameSalvo extends Shotlock {

    public ShotlockFlameSalvo(String registeryName, int order, int cooldown, int max){
        super(registeryName,order,cooldown,max);
    }

    @Override
    public void onUse(Player player, List<Entity> targetList) {

        IPlayerCapabilitiesMA playerData = ModCapabilitiesMA.getPlayer(player);


        float damage = (float) (DamageCalculation.getMagicDamage(player) * 1.5 + ModCapabilities.getPlayer(player).getNumberOfAbilitiesEquipped(Strings.fireBoost) * 0.2F);
        FlameSalvoCoreEntity core = new FlameSalvoCoreEntity(player.level, player, targetList, damage);
        core.setPos(player.getX(), player.getY(), player.getZ());
        player.level.addFreshEntity(core);



    }
}
