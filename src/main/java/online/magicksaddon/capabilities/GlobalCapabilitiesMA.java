package online.magicksaddon.capabilities;


import net.minecraft.nbt.CompoundTag;

public class GlobalCapabilitiesMA implements IGlobalCapabilitiesMA {


    private int level;
    public CompoundTag serializeNBT() {
        CompoundTag storage = new CompoundTag();
        storage.putInt("haste_ticks", this.getHasteTicks());
        storage.putInt("haste_level", this.gethasteLevel());
        storage.putInt("slow_ticks", this.getSlowTicks());
        storage.putInt("slow_level", this.getSlowLevel());
        return storage;
    }

    private int gethasteLevel() {

        return hasteLevel;
    }

    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag properties = (CompoundTag) nbt;
        this.setHasteTicks(properties.getInt("haste_ticks"), hasteLevel);
        this.setSlowTicks(properties.getInt("slow_ticks"), slowLevel);
    }


    private int hasteTicks, hasteLevel, slowTicks, slowLevel;

    //Haste
    public int getHasteLevel() {

        return hasteLevel;
    }
    @Override
    public void setHasteLevel(int level) {

        this.hasteLevel = level;
    }
    @Override
    public int getHasteTicks() {

        return hasteTicks;
    }
    @Override
    public void setHasteTicks(int i, int level) {
            hasteTicks = i;
            hasteLevel = level;
    }
    @Override
    public void remHasteTicks(int ticks) {

        hasteTicks -= ticks;
    }

    @Override
    public int getSlowLevel() {

        return slowLevel;
    }

    @Override
    public void setSlowLevel(int level) {
        this.slowLevel = level;
    }

    @Override
    public void setSlowTicks(int time, int level) {

    }

    @Override
    public int getSlowTicks() {

        return slowTicks;
    }

    @Override
    public void setSlowTicks(int i) {
        slowTicks = i;
        slowLevel = level;
    }

    @Override
    public void remSlowTicks(int ticks) {
        slowTicks -= ticks;
    }

    @Override
    public void setSlowCaster(String name) {

    }

    @Override
    public void deserializeNBT() {

    }
}
