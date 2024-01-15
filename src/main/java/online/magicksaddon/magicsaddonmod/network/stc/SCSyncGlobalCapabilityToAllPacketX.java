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
    public int berserkLvl, berserkTicks, prestige, strBonus, magBonus, defBonus, NGPlusWarriorCount, NGPlusMysticCount, NGPlusGuardianCount, stepTicks;

    public SCSyncGlobalCapabilityToAllPacketX() {

    }

    public SCSyncGlobalCapabilityToAllPacketX(int id, IGlobalCapabilitiesX capability) {
        this.id = id;
        this.berserkLvl= capability.getBerserkLevel();
        this.berserkTicks = capability.getBerserkTicks();
        this.prestige = capability.getPrestigeLvl();
        this.strBonus = capability.getSTRBonus();
        this.magBonus = capability.getMAGBonus();
        this.defBonus = capability.getDEFBonus();
        this.NGPlusWarriorCount = capability.getNGPWarriorCount();
        this.NGPlusMysticCount = capability.getNGPMysticCount();
        this.NGPlusGuardianCount = capability.getNGPGuardianCount();
        this.stepTicks = capability.getStepTicks();
    }

    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(id);
        buffer.writeInt(this.berserkLvl);
        buffer.writeInt(this.berserkTicks);
        buffer.writeInt(this.prestige);
        buffer.writeInt(this.strBonus);
        buffer.writeInt(this.magBonus);
        buffer.writeInt(this.defBonus);
        buffer.writeInt(this.NGPlusWarriorCount);
        buffer.writeInt(this.NGPlusMysticCount);
        buffer.writeInt(this.NGPlusGuardianCount);
        buffer.writeInt(this.stepTicks);
    }

    public static SCSyncGlobalCapabilityToAllPacketX decode(FriendlyByteBuf buffer){
        SCSyncGlobalCapabilityToAllPacketX msg = new SCSyncGlobalCapabilityToAllPacketX();
        msg.id = buffer.readInt();
        msg.berserkLvl = buffer.readInt();
        msg.berserkTicks = buffer.readInt();
        msg.prestige = buffer.readInt();
        msg.strBonus = buffer.readInt();
        msg.magBonus = buffer.readInt();
        msg.defBonus = buffer.readInt();
        msg.NGPlusWarriorCount = buffer.readInt();
        msg.NGPlusMysticCount = buffer.readInt();
        msg.NGPlusGuardianCount = buffer.readInt();
        msg.stepTicks = buffer.readInt();

        return msg;
    }

    public static void handle(final SCSyncGlobalCapabilityToAllPacketX message, Supplier<NetworkEvent.Context> ctx) {
    	ctx.get().enqueueWork(() -> {
			LivingEntity entity = (LivingEntity) Minecraft.getInstance().level.getEntity(message.id);
			
			if (entity != null) {
				LazyOptional<IGlobalCapabilitiesX> globalData = entity.getCapability(ModCapabilitiesX.GLOBAL_CAPABILITIES);
				globalData.ifPresent(cap -> {
					cap.setBerserkTicks(message.berserkTicks, message.berserkLvl);
					cap.setPrestigeLvl(message.prestige);
                    cap.setSTRBonus(message.strBonus);
                    cap.setMAGBonus(message.magBonus);
                    cap.setDEFBonus(message.defBonus);
                    cap.setNGPWarriorCount(message.NGPlusWarriorCount);
                    cap.setNGPMysticCount(message.NGPlusMysticCount);
                    cap.setNGPGuardianCount(message.NGPlusGuardianCount);
                    cap.setStepTicks(message.stepTicks);
				});
			}
		});
		ctx.get().setPacketHandled(true);
    }

}
