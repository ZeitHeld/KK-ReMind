package online.remind.remind.shotlock;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.lib.StringsRM;

public class ModShotlocksRM {

    public static DeferredRegister<Shotlock> SHOTLOCKS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "shotlocks"), KingdomKeysReMind.MODID);


    static int order = 100;

    public static final RegistryObject<Shotlock>
    	FLAME_SALVO = SHOTLOCKS.register(StringsRM.flameSalvo, () -> new ShotlockFlameSalvo(KingdomKeysReMind.MODID + ":" + StringsRM.flameSalvo, order++, 2, 15)),
        BUBBLE_BLASTER = SHOTLOCKS.register(StringsRM.bubbleBlaster, () -> new ShotlockBubbleBlaster(KingdomKeysReMind.MODID + ":" + StringsRM.bubbleBlaster, order++, 2, 15)),
        THUNDERSTORM = SHOTLOCKS.register(StringsRM.thunderStorm, () -> new ShotlockThunderstorm(KingdomKeysReMind.MODID + ":" + StringsRM.thunderStorm, order++, 2, 5)),
        BIO_BARRAGE = SHOTLOCKS.register(StringsRM.bioBarrage, () -> new ShotlockBioBarrage(KingdomKeysReMind.MODID + ":" + StringsRM.bioBarrage, order++, 2,8)),
        METEOR_SHOWER = SHOTLOCKS.register(StringsRM.meteorShower, () -> new ShotlockMeteorShower(KingdomKeysReMind.MODID + ":" + StringsRM.meteorShower, order++, 2,30));


}
