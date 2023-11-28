package online.magicksaddon.magicsaddonmod.capabilities;

import net.minecraft.nbt.CompoundTag;
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;

public abstract class IWorldCapabilitiesX implements IWorldCapabilities {
    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
