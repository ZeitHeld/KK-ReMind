package online.remind.remind.shotlock;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import online.kingdomkeys.kingdomkeys.KingdomKeys;
import online.kingdomkeys.kingdomkeys.lib.KKSupplier;
import online.kingdomkeys.kingdomkeys.shotlock.Shotlock;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.lib.StringsRM;

import java.util.function.Supplier;

public class ModShotlocksRM {

    public static DeferredRegister<Shotlock> SHOTLOCKS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(KingdomKeys.MODID, "shotlocks"), KingdomKeysReMind.MODID);


    static int order = 100;

    public static final KKSupplier<Shotlock>

            // Series Shotlocks
        DARK_DIVIDE = register(StringsRM.darkDivide, () -> new ShotlockDarkDivide(KingdomKeysReMind.MODID + ":" + StringsRM.darkDivide, order++)),

            // Original Shotlocks
        HEARTLESS_ANGEL = register(StringsRM.heartlessAngel, () -> new ShotlockHeartlessAngel(KingdomKeysReMind.MODID + ":" + StringsRM.heartlessAngel, order++)),
        OMNISLASH = register(StringsRM.omnislash, () -> new ShotlockOmnislash(KingdomKeysReMind.MODID + ":" + StringsRM.omnislash, order++));

    private static KKSupplier<Shotlock> register(String name, Supplier<Shotlock> shotlockSupplier) {
        return new KKSupplier<>(ResourceLocation.fromNamespaceAndPath(KingdomKeysReMind.MODID, name), SHOTLOCKS.register(name, shotlockSupplier));
    }
}
