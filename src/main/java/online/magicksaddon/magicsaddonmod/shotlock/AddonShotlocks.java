package online.magicksaddon.magicsaddonmod.shotlock;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.shotlock.ModShotlocks;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.lib.Strings;

public class AddonShotlocks {

    public static DeferredRegister<Shotlock> SHOTLOCKS = DeferredRegister.create(new ResourceLocation(KingdomKeys.MODID, "shotlocks"), MagicksAddonMod.MODID);

    static int order = 100;
    public static final RegistryObject<Shotlock>
    	FLAME_SALVO = SHOTLOCKS.register(Strings.flameSalvo, () -> new ShotlockFlameSalvo(MagicksAddonMod.MODID + ":" + Strings.flameSalvo, order++, 2, 15)),
        BUBBLE_BLASTER = SHOTLOCKS.register(Strings.bubbleBlaster, () -> new ShotlockBubbleBlaster(MagicksAddonMod.MODID + ":" + Strings.bubbleBlaster, order++, 2, 15)),
        THUNDERSTORM = SHOTLOCKS.register(Strings.thunderStorm, () -> new ShotlockThunderstorm(MagicksAddonMod.MODID + ":" + Strings.thunderStorm, order++, 2, 5)),
        BIO_BARRAGE = SHOTLOCKS.register(Strings.bioBarrage, () -> new ShotlockBioBarrage(MagicksAddonMod.MODID + ":" + Strings.bioBarrage, order++, 2,8)),
        METEOR_SHOWER = SHOTLOCKS.register(Strings.meteorShower, () -> new ShotlockMeteorShower(MagicksAddonMod.MODID + ":" + Strings.meteorShower, order++, 4,30));


}
