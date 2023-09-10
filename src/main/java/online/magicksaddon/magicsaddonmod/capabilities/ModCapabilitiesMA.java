package online.magicksaddon.magicsaddonmod.capabilities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import online.magicksaddon.magicsaddonmod.handler.Provider;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = MagicksAddonMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilitiesMA {

    public static final Capability<IGlobalCapabilitiesMA> GLOBAL_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IPlayerCapabilitiesMA> PLAYER_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IWorldCapabilitiesMA> WORLD_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});

    public static IGlobalCapabilitiesMA getGlobal(LivingEntity e) {
        LazyOptional<IGlobalCapabilitiesMA> globalData = e.getCapability(ModCapabilitiesMA.GLOBAL_CAPABILITIES, null);
        return globalData.orElse(null);
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {

    }

    public static IPlayerCapabilitiesMA getPlayer(Player player) {
        LazyOptional<IPlayerCapabilitiesMA> playerData = player.getCapability(ModCapabilitiesMA.PLAYER_CAPABILITIES, null);
        return playerData.orElse(null);
    }

    public static IWorldCapabilities getWorld(Level w) {
        @NotNull LazyOptional<IWorldCapabilitiesMA> worldData = w.getCapability(ModCapabilitiesMA.WORLD_CAPABILITIES, null);
        return (IWorldCapabilities) worldData.orElse(null);
    }


    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(MagicksAddonMod.MODID, "global_capabilities"), new Provider());
    }
}

