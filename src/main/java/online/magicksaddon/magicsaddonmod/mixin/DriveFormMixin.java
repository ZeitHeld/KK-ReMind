package online.magicksaddon.magicsaddonmod.mixin;

import online.kingdomkeys.kingdomkeys.driveform.DriveForm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DriveForm.class)
public interface DriveFormMixin {

    @Accessor("color")
    public void setColor(float[] color);

}
