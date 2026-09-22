package online.remind.remind.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import online.remind.remind.KingdomKeysReMind;
import online.remind.remind.util.FormMagicOverrideRegistry;

@EventBusSubscriber(modid = KingdomKeysReMind.MODID)
public final class FormMagicOverrideReloadHandler {

    private FormMagicOverrideReloadHandler() {
    }

    @SubscribeEvent
    public static void addReloadListeners(
            AddReloadListenerEvent event
    ) {
        event.addListener(
                FormMagicOverrideRegistry.INSTANCE
        );
    }
}