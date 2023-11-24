package online.magicksaddon.magicsaddonmod.capabilities;


import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IGlobalCapabilitiesMA extends INBTSerializable<CompoundTag> {

    //Haste
    int getHasteLevel();
    void setHasteLevel(int level);
    int getHasteTicks();
    void setHasteTicks(int i, int level);
    void remHasteTicks(int ticks);

    //Slow
    int getSlowLevel();
    void setSlowLevel(int level);
    void setSlowTicks(int i, int level);
    int getSlowTicks();
    void remSlowTicks(int ticks);
    void setSlowCaster(String name);

    //Berserk
    int getBerserkLevel();
    void setBerserkLevel(int level);
    int getBerserkTicks();
    void setBerserkTicks(int i, int level);
    void remBerserkTicks(int ticks);
    boolean activeBerserk();
    void berserkBoost (int level, int i);

    void setBerserkModelTicks(int berserkTicks);
    int getBerserkModelTicks();

    //AutoLife
    int setAutoLifeActive(int i);
    int getAutoLifeActive();
    void remAutoLifeActive(int use);

    void deserializeNBT();
}
