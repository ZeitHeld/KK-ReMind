package online.magicksaddon.magicsaddonmod.network.stc;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import online.kingdomkeys.kingdomkeys.capability.IGlobalCapabilities;
import online.kingdomkeys.kingdomkeys.capability.ModCapabilities;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.client.ClientUtilsMA;

public class SCSyncGlobalCapabilityToAllPacketX {

    public int id;
    public int berserkLvl, berserkTicks;

    public SCSyncGlobalCapabilityToAllPacketX() {

    }

    public SCSyncGlobalCapabilityToAllPacketX(int id, IGlobalCapabilitiesX capability) {
        this.id = id;
        this.berserkLvl= capability.getBerserkLevel();
        this.berserkTicks = capability.getBerserkTicks();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(id);
        buffer.writeInt(this.berserkLvl);
        buffer.writeInt(this.berserkTicks);
    }

    public static SCSyncGlobalCapabilityToAllPacketX decode(FriendlyByteBuf buffer){
        SCSyncGlobalCapabilityToAllPacketX msg = new SCSyncGlobalCapabilityToAllPacketX();
        msg.id = buffer.readInt();
        msg.berserkLvl = buffer.readInt();
        msg.berserkTicks = buffer.readInt();
        return msg;
    }

    public static void handle(final SCSyncGlobalCapabilityToAllPacketX message, Supplier<NetworkEvent.Context> ctx) {
    	ctx.get().enqueueWork(() -> {
			LivingEntity entity = (LivingEntity) Minecraft.getInstance().level.getEntity(message.id);
			
			if (entity != null) {
				LazyOptional<IGlobalCapabilitiesX> globalData = entity.getCapability(ModCapabilitiesX.GLOBAL_CAPABILITIES);
				globalData.ifPresent(cap -> {
					cap.setBerserkTicks(message.berserkTicks, message.berserkLvl);
				});
			}
		});
		ctx.get().setPacketHandled(true);
    }

}
