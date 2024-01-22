package online.magicksaddon.magicsaddonmod.shotlock;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.StringsX;

import java.util.function.Supplier;

public class AddonShotlocks {

    public static DeferredRegister<Shotlock> SHOTLOCKS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "shotlocks"), MagicksAddonMod.MODID);
    
    public static Supplier<IForgeRegistry<Shotlock>> registry = SHOTLOCKS.makeRegistry(RegistryBuilder::new);

    static int order = 100;

    public static final RegistryObject<Shotlock>
    	FLAME_SALVO = SHOTLOCKS.register(StringsX.flameSalvo, () -> new ShotlockFlameSalvo(MagicksAddonMod.MODID + ":" + StringsX.flameSalvo, order++, 2, 15)),
        BUBBLE_BLASTER = SHOTLOCKS.register(StringsX.bubbleBlaster, () -> new ShotlockBubbleBlaster(MagicksAddonMod.MODID + ":" + StringsX.bubbleBlaster, order++, 2, 15)),
        THUNDERSTORM = SHOTLOCKS.register(StringsX.thunderStorm, () -> new ShotlockThunderstorm(MagicksAddonMod.MODID + ":" + StringsX.thunderStorm, order++, 2, 5)),
        BIO_BARRAGE = SHOTLOCKS.register(StringsX.bioBarrage, () -> new ShotlockBioBarrage(MagicksAddonMod.MODID + ":" + StringsX.bioBarrage, order++, 2,8)),
        METEOR_SHOWER = SHOTLOCKS.register(StringsX.meteorShower, () -> new ShotlockMeteorShower(MagicksAddonMod.MODID + ":" + StringsX.meteorShower, order++, 4,30));


}
