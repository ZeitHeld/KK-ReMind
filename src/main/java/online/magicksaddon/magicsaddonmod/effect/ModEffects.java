package online.magicksaddon.magicsaddonmod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import online.magicksaddon.magicsaddonmod.KingdomKeysReMind;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, KingdomKeysReMind.MODID);

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
