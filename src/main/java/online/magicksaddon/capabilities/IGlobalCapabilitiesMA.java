package online.magicksaddon.capabilities;


import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IGlobalCapabilitiesMA extends INBTSerializable<CompoundTag> {

    void setLevel(int lvl);

    int getLevel();

    //Haste
    int getHasteLevel();
    void setHasteLevel(int level);
    int getHasteTicks();
    void setHasteTicks(int i, int level);
    void remHasteTicks(int ticks);

    //Slow
    int getSlowLevel();
    void setSlowLevel(int level);
    void setSlowTicks(int time, int level);
    int getSlowTicks();

    void setSlowTicks(int i);

    void remSlowTicks(int ticks);
    void setSlowCaster(String name);


    void deserializeNBT();


}
