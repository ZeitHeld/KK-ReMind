package online.remind.remind.integration.epicfight;

import net.minecraft.server.level.ServerPlayer;

import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class EpicFightCrossSlash {

    public static void play(
            ServerPlayer player,
            int slash
    ) {
        ServerPlayerPatch patch =
                EpicFightCapabilities.getEntityPatch(
                        player,
                        ServerPlayerPatch.class
                );

        if (patch == null) {
            return;
        }

        if (patch.isEpicFightMode()) {

            switch (slash) {

                case 1 -> patch.playAnimationSynchronized(
                        Animations.GREATSWORD_AIR_SLASH,
                        0.0F
                );

                case 2 -> patch.playAnimationSynchronized(
                        Animations.GREATSWORD_AUTO2,
                        0.0F
                );

                case 3 -> patch.playAnimationSynchronized(
                        Animations.LONGSWORD_AUTO3,
                        0.0F
                );
            }
        }
    }
}