package online.magicksaddon.magicsaddonmod.network.stc;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import online.kingdomkeys.kingdomkeys.client.ClientUtils;
import online.kingdomkeys.kingdomkeys.network.stc.SCSyncCapabilityPacket;
import online.magicksaddon.magicsaddonmod.capabilities.IPlayerCapabilitiesX;

import java.util.function.Supplier;

public class SCSyncCapabilityPacketX extends SCSyncCapabilityPacket {

    public SCSyncCapabilityPacketX() {
    }

    public SCSyncCapabilityPacketX(IPlayerCapabilitiesX capabilitiesX){

    }

    public static void handle(final SCSyncCapabilityPacketX message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientUtils.syncCapability(message)));
        ctx.get().setPacketHandled(true);
    }
}
