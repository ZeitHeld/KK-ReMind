package online.magicksaddon.magicsaddonmod.network.stc;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkEvent;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;

import java.util.function.Supplier;

public class SCSyncGlobalCapabilityToAllPacket {

    int id;
    private int berserkModelTicks;

    public SCSyncGlobalCapabilityToAllPacket() {

    }

    public SCSyncGlobalCapabilityToAllPacket(int id, IGlobalCapabilitiesMA capability) {
        this.id = id;
        this.berserkModelTicks = capability.getBerserkModelTicks();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(id);
        buffer.writeInt(this.berserkModelTicks);
    }

    public static SCSyncGlobalCapabilityToAllPacket decode(FriendlyByteBuf buffer){
        SCSyncGlobalCapabilityToAllPacket msg = new SCSyncGlobalCapabilityToAllPacket();
        msg.id = buffer.readInt();
        msg.berserkModelTicks = buffer.readInt();
        return msg;
    }

    public static void handle(final SCSyncGlobalCapabilityToAllPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LivingEntity entity = (LivingEntity) Minecraft.getInstance().level.getEntity(message.id);

            if (entity != null) {
                LazyOptional<IGlobalCapabilitiesMA> globalData = entity.getCapability(ModCapabilitiesMA.GLOBAL_CAPABILITIES);
                globalData.ifPresent(cap -> {
                    cap.setBerserkModelTicks(message.berserkModelTicks);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
