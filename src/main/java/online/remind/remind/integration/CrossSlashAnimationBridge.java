package online.remind.remind.integration;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;

public class CrossSlashAnimationBridge {

    public static void play(
            ServerPlayer player,
            int slash
    ) {
        if (!ModList.get().isLoaded("epicfight")) {
            return;
        }

        try {
            Class<?> clazz = Class.forName(
                    "online.remind.remind.integration.epicfight.EpicFightCrossSlash"
            );

            clazz.getMethod(
                    "play",
                    ServerPlayer.class,
                    int.class
            ).invoke(
                    null,
                    player,
                    slash
            );

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}