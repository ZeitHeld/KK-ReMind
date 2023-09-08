package online.magicksaddon.capabilities;


import net.minecraft.nbt.CompoundTag;

public class GlobalCapabilitiesMA implements IGlobalCapabilitiesMA {


    private int level;
    public CompoundTag serializeNBT() {
        CompoundTag storage = new CompoundTag();
        storage.putInt("haste_ticks", this.getHasteTicks());
        storage.putInt("haste_level", this.gethasteLevel());
        storage.putInt("level", this.getLevel());
        return storage;
    }

    private int gethasteLevel() {

        return hasteLevel;
    }

    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag properties = (CompoundTag) nbt;
        this.setHasteTicks(properties.getInt("haste_ticks"), level);
        this.setSlowTicks(properties.getInt("slow_ticks"));
    }
    private int hasteTicks, hasteLevel, slowTicks, slowLevel;

    @Override
    public void setLevel(int lvl) {

        this.level = lvl;
    }

    @Override
    public int getLevel() {

        return level;
    }

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
    public int getSlowTicks() {

        return slowTicks;
    }

    @Override
    public void setSlowTicks(int i) {
        slowTicks = i;
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
