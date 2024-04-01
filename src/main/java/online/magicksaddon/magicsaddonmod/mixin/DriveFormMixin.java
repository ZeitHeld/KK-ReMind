package online.magicksaddon.magicsaddonmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import online.kingdomkeys.kingdomkeys.driveform.DriveForm;

@Mixin(DriveForm.class)
public interface DriveFormMixin {

    @Accessor("color")
    public void setColor(float[] color);

}
