package online.remind.remind.integration.epicfight;

import net.minecraft.server.level.ServerPlayer;

import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class EpicFightBraver {

    public static void play(
            ServerPlayer player,
            int phase
    ) {
        ServerPlayerPatch patch =
                EpicFightCapabilities.getEntityPatch(
                        player,
                        ServerPlayerPatch.class
                );

        if (patch == null) {
            return;
        }

        switch (phase) {

            case 1 -> patch.playAnimationSynchronized(
                    Animations.GREATSWORD_AIR_SLASH,
                    0.0F
            );
        }
    }
}