package online.magicksaddon.magicsaddonmod.shotlock;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.magicksaddon.magicsaddonmod.DawnCrossDuskMod;
import online.magicksaddon.magicsaddonmod.lib.StringsX;

public class AddonShotlocks {

    public static DeferredRegister<Shotlock> SHOTLOCKS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "shotlocks"), DawnCrossDuskMod.MODID);

    static int order = 100;
    public static final RegistryObject<Shotlock>
    	FLAME_SALVO = SHOTLOCKS.register(StringsX.flameSalvo, () -> new ShotlockFlameSalvo(DawnCrossDuskMod.MODID + ":" + StringsX.flameSalvo, order++, 2, 15)),
        BUBBLE_BLASTER = SHOTLOCKS.register(StringsX.bubbleBlaster, () -> new ShotlockBubbleBlaster(DawnCrossDuskMod.MODID + ":" + StringsX.bubbleBlaster, order++, 2, 15)),
        THUNDERSTORM = SHOTLOCKS.register(StringsX.thunderStorm, () -> new ShotlockThunderstorm(DawnCrossDuskMod.MODID + ":" + StringsX.thunderStorm, order++, 2, 5)),
        BIO_BARRAGE = SHOTLOCKS.register(StringsX.bioBarrage, () -> new ShotlockBioBarrage(DawnCrossDuskMod.MODID + ":" + StringsX.bioBarrage, order++, 2,8)),
        METEOR_SHOWER = SHOTLOCKS.register(StringsX.meteorShower, () -> new ShotlockMeteorShower(DawnCrossDuskMod.MODID + ":" + StringsX.meteorShower, order++, 4,30));


}
