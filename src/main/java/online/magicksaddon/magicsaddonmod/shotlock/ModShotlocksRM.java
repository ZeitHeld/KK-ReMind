package online.magicksaddon.magicsaddonmod.shotlock;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.StringsRM;

public class ModShotlocksRM {

    public static DeferredRegister<Shotlock> SHOTLOCKS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "shotlocks"), MagicksAddonMod.MODID);


    static int order = 100;

    public static final RegistryObject<Shotlock>
    	FLAME_SALVO = SHOTLOCKS.register(StringsRM.flameSalvo, () -> new ShotlockFlameSalvo(MagicksAddonMod.MODID + ":" + StringsRM.flameSalvo, order++, 2, 15)),
        BUBBLE_BLASTER = SHOTLOCKS.register(StringsRM.bubbleBlaster, () -> new ShotlockBubbleBlaster(MagicksAddonMod.MODID + ":" + StringsRM.bubbleBlaster, order++, 2, 15)),
        THUNDERSTORM = SHOTLOCKS.register(StringsRM.thunderStorm, () -> new ShotlockThunderstorm(MagicksAddonMod.MODID + ":" + StringsRM.thunderStorm, order++, 2, 5)),
        BIO_BARRAGE = SHOTLOCKS.register(StringsRM.bioBarrage, () -> new ShotlockBioBarrage(MagicksAddonMod.MODID + ":" + StringsRM.bioBarrage, order++, 2,8)),
        METEOR_SHOWER = SHOTLOCKS.register(StringsRM.meteorShower, () -> new ShotlockMeteorShower(MagicksAddonMod.MODID + ":" + StringsRM.meteorShower, order++, 4,30));


}
