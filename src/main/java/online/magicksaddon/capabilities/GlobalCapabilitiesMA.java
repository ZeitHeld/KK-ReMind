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
    }
    private int hasteTicks, hasteLevel;

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
        return 0;
    }

    @Override
    public void setSlowLevel(int level) {

    }

    @Override
    public int getSlowTicks() {
        return 0;
    }

    @Override
    public void setSlowTicks(int i, int level) {

    }

    @Override
    public void remSlowTicks(int ticks) {

    }

    @Override
    public void setSlowCaster(String name) {

    }

    @Override
    public void deserializeNBT() {

    }
}
