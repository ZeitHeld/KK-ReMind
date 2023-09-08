package online.magicksaddon.handler;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import online.magicksaddon.capabilities.GlobalCapabilitiesMA;
import online.magicksaddon.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.capabilities.ModCapabilitiesMA;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Provider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
    IGlobalCapabilitiesMA instance = new GlobalCapabilitiesMA();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ModCapabilitiesMA.GLOBAL_CAPABILITIES.orEmpty(cap, LazyOptional.of(() -> instance));
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.deserializeNBT();
    }
}

