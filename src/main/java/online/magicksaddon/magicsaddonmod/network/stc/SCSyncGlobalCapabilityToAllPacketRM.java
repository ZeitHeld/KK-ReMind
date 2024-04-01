package online.magicksaddon.magicsaddonmod.network.stc;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkEvent;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;

public class SCSyncGlobalCapabilityToAllPacketRM {

    public int id;
    public int berserkLvl, berserkTicks, prestige, strBonus, magBonus, defBonus, NGPlusWarriorCount, NGPlusMysticCount, NGPlusGuardianCount, stepTicks, riskchargeCount, autoLife;
    public byte stepType;

    public SCSyncGlobalCapabilityToAllPacketRM() {

    }

    public SCSyncGlobalCapabilityToAllPacketRM(int id, IGlobalCapabilitiesRM capability) {
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
        this.stepType = capability.getStepType();
        this.riskchargeCount = capability.getRiskchargeCount();
        this.autoLife = capability.getAutoLifeActive();
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
        buffer.writeByte(this.stepType);
        buffer.writeInt(this.riskchargeCount);
        buffer.writeInt(this.autoLife);
    }

    public static SCSyncGlobalCapabilityToAllPacketRM decode(FriendlyByteBuf buffer){
        SCSyncGlobalCapabilityToAllPacketRM msg = new SCSyncGlobalCapabilityToAllPacketRM();
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
        msg.stepType = buffer.readByte();
        msg.riskchargeCount = buffer.readInt();
        msg.autoLife = buffer.readInt();

        return msg;
    }

    public static void handle(final SCSyncGlobalCapabilityToAllPacketRM message, Supplier<NetworkEvent.Context> ctx) {
    	ctx.get().enqueueWork(() -> {
			LivingEntity entity = (LivingEntity) Minecraft.getInstance().level.getEntity(message.id);
			
			if (entity != null) {
				LazyOptional<IGlobalCapabilitiesRM> globalData = entity.getCapability(ModCapabilitiesRM.GLOBAL_CAPABILITIES);
				globalData.ifPresent(cap -> {
					cap.setBerserkTicks(message.berserkTicks, message.berserkLvl);
					cap.setPrestigeLvl(message.prestige);
                    cap.setSTRBonus(message.strBonus);
                    cap.setMAGBonus(message.magBonus);
                    cap.setDEFBonus(message.defBonus);
                    cap.setNGPWarriorCount(message.NGPlusWarriorCount);
                    cap.setNGPMysticCount(message.NGPlusMysticCount);
                    cap.setNGPGuardianCount(message.NGPlusGuardianCount);
                    cap.setStepTicks(message.stepTicks, message.stepType);
                    cap.setRiskchargeCount(message.riskchargeCount);
                    cap.setAutoLifeActive(message.autoLife);
				});
			}
		});
		ctx.get().setPacketHandled(true);
    }

}
