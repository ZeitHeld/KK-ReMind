package online.magicksaddon.magicsaddonmod.capabilities;

import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraft.world.entity.LivingEntity;
import online.kingdomkeys.kingdomkeys.capability.IWorldCapabilities;

public abstract class IWorldCapabilitiesMA implements IWorldCapabilities {
    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
