package online.magicksaddon.client.sound;
import ca.weblite.objc.Proxy;
import net.minecraftforge.registries.RegistryObject;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static online.kingdomkeys.kingdomkeys.client.sound.ModSounds.registerSound;

public class MagicSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MagicksAddonMod.MODID);

    public static final RegistryObject<SoundEvent>
        what =registerSound("what"),
        haste = registerSound("haste"),
        slow = registerSound("slow");
}
