package online.magicksaddon.magicsaddonmod.capabilities;

import net.minecraft.nbt.CompoundTag;

public class PlayerCapabilitiesMA implements IPlayerCapabilitiesMA{


    @Override
    public int getHasteLevel() {
        return 0;
    }

    @Override
    public void setHasteLevel(int level) {

    }

    @Override
    public void setHasteTicks(int ticks) {

    }

    @Override
    public void remHasteTicks(int ticks) {

    }

    @Override
    public int getHasteTicks() {
        return 0;
    }

    @Override
    public void setHasteActive(boolean active) {

    }

    @Override
    public boolean getHasteActive() {
        return false;
    }

    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
