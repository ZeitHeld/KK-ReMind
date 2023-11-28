package online.magicksaddon.magicsaddonmod.capabilities;

import org.jetbrains.annotations.NotNull;

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
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.handler.Provider;

@Mod.EventBusSubscriber(modid = MagicksAddonMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilitiesX {

    public static final Capability<IGlobalCapabilitiesX> GLOBAL_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IPlayerCapabilitiesX> PLAYER_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IWorldCapabilitiesX> WORLD_CAPABILITIES = CapabilityManager.get(new CapabilityToken<>() {});

    public static IGlobalCapabilitiesX getGlobal(LivingEntity e) {
        LazyOptional<IGlobalCapabilitiesX> globalData = e.getCapability(ModCapabilitiesX.GLOBAL_CAPABILITIES, null);
        return globalData.orElse(null);
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {

    }

    public static IPlayerCapabilitiesX getPlayer(Player player) {
        LazyOptional<IPlayerCapabilitiesX> playerData = player.getCapability(ModCapabilitiesX.PLAYER_CAPABILITIES, null);
        return playerData.orElse(null);
    }

    public static IWorldCapabilities getWorld(Level w) {
        @NotNull LazyOptional<IWorldCapabilitiesX> worldData = w.getCapability(ModCapabilitiesX.WORLD_CAPABILITIES, null);
        return (IWorldCapabilities) worldData.orElse(null);
    }


    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(MagicksAddonMod.MODID, "global_capabilities"), new Provider());
    }
}

