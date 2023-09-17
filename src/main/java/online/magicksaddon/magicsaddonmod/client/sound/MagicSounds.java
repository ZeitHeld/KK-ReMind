package online.magicksaddon.magicsaddonmod.client.sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;

public class MagicSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MagicksAddonMod.MODID);

    public static final RegistryObject<SoundEvent>
            HASTE = registerSound("haste"),
            SLOW = registerSound("slow"),
            HOLY = registerSound("holy"),
            RUIN = registerSound("ruin"),
            BALLOON = registerSound("balloon"),
            BALLOON_BOUNCE = registerSound("balloon_bounce");


    public static RegistryObject<SoundEvent> registerSound(String name) {
        final ResourceLocation soundID = new ResourceLocation(MagicksAddonMod.MODID, name);
        return SOUNDS.register(name, () -> new SoundEvent(soundID));

    }
}
