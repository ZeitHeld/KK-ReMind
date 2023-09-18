package online.magicksaddon.magicsaddonmod.magic;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.kingdomkeys.kingdomkeys.lib.Party;
import online.kingdomkeys.kingdomkeys.lib.Party.Member;
import online.kingdomkeys.kingdomkeys.magic.Magic;
import online.kingdomkeys.kingdomkeys.network.PacketHandler;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncGlobalCapabilityPacket;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;

public class magicSlow extends Magic {
    public magicSlow(ResourceLocation registryName, boolean hasToSelect, int maxLevel, int order) {
        super(registryName, hasToSelect, maxLevel, null, order);
    }

    IGlobalCapabilitiesMA globalData;

    @Override
    protected void magicUse(Player player, Player caster, int level, float fullMPBlastMult) {



        float radius = 2 + (level * 1.5F);
        List<Entity> list = player.level.getEntities(player, player.getBoundingBox().inflate(radius, radius, radius));
        Party casterParty = ModCapabilities.getWorld(player.level).getPartyFromMember(player.getUUID());
        if (casterParty != null && !casterParty.getFriendlyFire()) {
            for (Member m : casterParty.getMembers()){
                list.remove(player.level.getPlayerByUUID(m.getUUID()));
            }
        }

        if (!list.isEmpty()){
            for (int i = 0; i < list.size(); i++){
                Entity e = (Entity) list.get(i);
                if (e instanceof LivingEntity){
                    if(globalData != null) {

                        IGlobalCapabilitiesMA globalData = ModCapabilitiesMA.getGlobal((LivingEntity) e);
                    ((LivingEntity)e).getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(new AttributeModifier("Slow", -(0.25 + (0.25 * level)), AttributeModifier.Operation.MULTIPLY_BASE));
                    ((LivingEntity)e).getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier("Slow", -(0.25 + (0.25 * level)), AttributeModifier.Operation.MULTIPLY_BASE));
                    int time = (int) (ModCapabilities.getPlayer(caster).getMaxMP() * ((level * 0.75) + 5) + 5);
                    globalData.setSlowTicks(time, level); //Slow Time
                    globalData.setSlowCaster(player.getDisplayName().getString());
                    if (e instanceof ServerPlayer)
                        PacketHandler.sendTo(new SCSyncGlobalCapabilityPacket(), (ServerPlayer) e);
                }
            }
        }
        player.swing(InteractionHand.MAIN_HAND);
        player.level.playSound(null, player.blockPosition(), MagicSounds.SLOW.get(), SoundSource.PLAYERS, 1F, 1F);
        }
    }
}
