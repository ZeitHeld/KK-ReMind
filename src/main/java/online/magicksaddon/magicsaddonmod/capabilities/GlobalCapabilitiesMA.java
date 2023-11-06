package online.magicksaddon.magicsaddonmod.capabilities;


import net.minecraft.nbt.CompoundTag;

public class GlobalCapabilitiesMA implements IGlobalCapabilitiesMA {


    public CompoundTag serializeNBT() {
        CompoundTag storage = new CompoundTag();
        storage.putInt("haste_ticks", this.getHasteTicks());
        storage.putInt("haste_level", this.getHasteLevel());
        storage.putInt("slow_ticks", this.getSlowTicks());
        storage.putInt("slow_level", this.getSlowLevel());
        storage.putInt("berserk_ticks",this.getBerserkTicks());
        storage.putInt("berserk_level", this.getBerserkLevel());
        return storage;
    }

    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag properties = (CompoundTag) nbt;
        this.setHasteTicks(properties.getInt("haste_ticks"), properties.getInt("haste_level"));
        this.setSlowTicks(properties.getInt("slow_ticks"), properties.getInt("slow_level"));
    }


    private int hasteTicks, hasteLevel, slowTicks, slowLevel, berserkLevel, berserkTicks;

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


    //Slow
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
    public void setSlowTicks(int i, int level) {
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
    public int getBerserkLevel() {
        return berserkLevel;
    }

    @Override
    public void setBerserkLevel(int level) {
        this.berserkLevel = level;
    }

    @Override
    public int getBerserkTicks() {
        return berserkTicks;
    }

    @Override
    public void setBerserkTicks(int i, int level) {
        berserkTicks = i;
        berserkLevel = level;

    }

    @Override
    public void remBerserkTicks(int ticks) {
        berserkTicks -= ticks;
    }

    @Override
    public void deserializeNBT() {

    }
    // Not Slow


}
