package online.remind.remind.capabilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;
import online.remind.remind.KingdomKeysReMind;

@Mod.EventBusSubscriber(modid = KingdomKeysReMind.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilitiesRM {

    public static final Capability<IGlobalCapabilitiesRM> GLOBAL_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});
   // public static final Capability<IPlayerCapabilitiesX> PLAYER_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IWorldCapabilitiesRM> WORLD_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});

    public static IGlobalCapabilitiesRM getGlobal(LivingEntity e) {
        LazyOptional<IGlobalCapabilitiesRM> globalData = e.getCapability(ModCapabilitiesRM.GLOBAL_CAPABILITIES, null);
        return globalData.orElse(null);
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {

    }

/*    public static IPlayerCapabilitiesX getPlayer(Player player) {
        LazyOptional<IPlayerCapabilitiesX> playerData = player.getCapability(ModCapabilitiesX.PLAYER_CAPABILITIES, null);
        return playerData.orElse(null);
    }*/

    public static IWorldCapabilities getWorld(Level w) {
        @NotNull LazyOptional<IWorldCapabilitiesRM> worldData = w.getCapability(ModCapabilitiesRM.WORLD_CAPABILITIES, null);
        return (IWorldCapabilities) worldData.orElse(null);
    }


    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(KingdomKeysReMind.MODID, "global_capabilities"), new GlobalCapabilitiesRMProvider());
    }
}

