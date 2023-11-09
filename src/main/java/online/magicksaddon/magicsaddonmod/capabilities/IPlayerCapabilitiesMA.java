package online.magicksaddon.magicsaddonmod.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPlayerCapabilitiesMA  extends INBTSerializable<CompoundTag> {


    // Status Effect Spell Values
    int getHasteLevel();
    void setHasteLevel(int level);
    void setHasteTicks(int ticks);
    void remHasteTicks(int ticks);
    int getHasteTicks();
    void setHasteActive(boolean active);
    boolean getHasteActive();
    boolean getautoLife();
    boolean AutoLife();


    // Abilities Stuff

}
