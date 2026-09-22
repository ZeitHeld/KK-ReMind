package online.remind.remind.integration.epicfight.init;

import net.neoforged.bus.api.IEventBus;

public class EpicFightIntegrationRM {

    public static void initIntegrationRM(IEventBus modEventBus) {
        EpicRMWeapons.WEAPONS.register(modEventBus);
        EpicRMConditionals.CONDITIONALS.register(modEventBus);
        EpicRMModifiers.WEAPON_MODIFIERS.register(modEventBus);
        EpicRMMovesets.MOVESETS.register(modEventBus);
    }
}
