package online.magicksaddon.magicsaddonmod.network.stc;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.ClientUtilsMA;

public class SCSyncGlobalCapabilityToAllPacket {

    public int id;
    public int berserkLvl, berserkTicks;

    public SCSyncGlobalCapabilityToAllPacket() {

    }

    public SCSyncGlobalCapabilityToAllPacket(int id, IGlobalCapabilitiesMA capability) {
        this.id = id;
        this.berserkLvl= capability.getBerserkLevel();
        this.berserkTicks = capability.getBerserkTicks();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(id);
        buffer.writeInt(this.berserkLvl);
        buffer.writeInt(this.berserkTicks);
    }

    public static SCSyncGlobalCapabilityToAllPacket decode(FriendlyByteBuf buffer){
        SCSyncGlobalCapabilityToAllPacket msg = new SCSyncGlobalCapabilityToAllPacket();
        msg.id = buffer.readInt();
        msg.berserkLvl = buffer.readInt();
        msg.berserkTicks = buffer.readInt();
        return msg;
    }

    public static void handle(final SCSyncGlobalCapabilityToAllPacket message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientUtilsMA.syncCapability(message)));
        ctx.get().setPacketHandled(true);
    }

}
