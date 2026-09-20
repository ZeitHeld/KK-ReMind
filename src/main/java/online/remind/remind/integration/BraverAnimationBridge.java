package online.remind.remind.integration;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;

public class BraverAnimationBridge {

    public static void play(
            ServerPlayer player,
            int phase
    ) {
        if (!ModList.get().isLoaded("epicfight")) {
            return;
        }

        try {
            Class<?> clazz = Class.forName(
                    "online.remind.remind.integration.epicfight.EpicFightBraver"
            );

            clazz.getMethod(
                    "play",
                    ServerPlayer.class,
                    int.class
            ).invoke(
                    null,
                    player,
                    phase
            );

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}