package online.magicksaddon.magicsaddonmod.magic;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.IPlayerCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;

import online.kingdomkeys.kingdomkeys.capability.PlayerCapabilities;




public class magicBerserk extends Magic {

    IPlayerCapabilities playerData;

    public magicBerserk(ResourceLocation registryName, boolean hasToSelect, int maxLevel) {
        super(registryName, hasToSelect, maxLevel, null);
    }


    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult, LivingEntity lockOnTarget) {

        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal(player);

        if(globalData != null) {
            int time = (int) (ModCapabilities.getPlayer(caster).getMaxMP() * ((level * 0.75) + 5));
            caster.swing(InteractionHand.MAIN_HAND);
            player.level().playSound(null, player.blockPosition(), MagicSounds.HASTE.get(), SoundSource.PLAYERS, 1F, 1F);
            // Effect and Level Modifier

            if (globalData.getBerserkTicks() <= 0) {
                playerData.getStrengthStat().addModifier("buff", +5 , false);
                PacketHandler.sendTo(new SCSyncCapabilityPacket(playerData), (ServerPlayer) player);
                player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier("Berserk", 0.5 + (0.5 * level), AttributeModifier.Operation.ADDITION));
            }
            globalData.setBerserkTicks(time, level);
        }
    }


}