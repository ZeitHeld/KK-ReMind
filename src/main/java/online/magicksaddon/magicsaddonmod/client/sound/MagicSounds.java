package online.magicksaddon.magicsaddonmod.client.sound;
import ca.weblite.objc.Proxy;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraftforge.registries.ForgeRegistries.Keys.SOUND_EVENTS;
import static online.kingdomkeys.kingdomkeys.client.sound.ModSounds.registerSound;

public class MagicSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MagicksAddonMod.MODID);

    public static final RegistryObject<SoundEvent>
            HASTE = registerSound("haste"),
            SLOW = registerSound("slow");


    public static RegistryObject<SoundEvent> registerSound(String name) {
        final ResourceLocation soundID = new ResourceLocation(MagicksAddonMod.MODID, name);
        return SOUNDS.register(name, () -> new SoundEvent(soundID));

    }
}
