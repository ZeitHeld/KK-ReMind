package online.remind.remind.network.cts;

import com.google.common.base.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import online.kingdomkeys.kingdomkeys.capability.IPlayerCapabilities;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.entity.ModEntitiesRM;
import online.remind.remind.entity.mob.ChirithyEntity;

import java.util.UUID;

public class CSSummonSpirit {

    ResourceLocation spirit;

    public CSSummonSpirit(){
        ;
    }

    public CSSummonSpirit(ResourceLocation spirit){
        this.spirit = spirit;

    }

    public void encode(FriendlyByteBuf buffer) {
        if (spirit != null)
            buffer.writeResourceLocation(spirit);
    }

    public static CSSummonSpirit decode(FriendlyByteBuf buffer) {
        CSSummonSpirit msg = new CSSummonSpirit();
        msg.spirit = buffer.readResourceLocation();
        return msg;
    }

    public static void handle(CSSummonSpirit message, final Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Player owner = ctx.get().getSender();

            System.out.println(owner);

            UUID uuid = owner.getUUID();
            ChirithyEntity chirithy = new ChirithyEntity(owner.level());
            chirithy.setOwnerUUID(uuid);
            chirithy.level().addFreshEntity(chirithy);
            chirithy.setPos(owner.getX(),owner.getY()+2,owner.getZ());
            return;
        });
    }
}
