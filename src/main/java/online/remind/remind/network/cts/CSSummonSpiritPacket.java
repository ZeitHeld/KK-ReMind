package online.remind.remind.network.cts;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import online.remind.remind.capabilities.IGlobalCapabilitiesRM;
import online.remind.remind.capabilities.ModCapabilitiesRM;
import online.remind.remind.entity.mob.ChirithyEntity;

import java.util.function.Supplier;

public class CSSummonSpiritPacket {


    public CSSummonSpiritPacket(){

    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static CSSummonSpiritPacket decode(FriendlyByteBuf buffer) {
        CSSummonSpiritPacket msg = new CSSummonSpiritPacket();
        return msg;
    }

    public static void handle(CSSummonSpiritPacket message, final Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Player owner = ctx.get().getSender();

            IGlobalCapabilitiesRM playerData = ModCapabilitiesRM.getGlobal(owner);
            if(playerData == null)
                return;

            //if(playerData.summonedDreamEater)

            ChirithyEntity chirithy = new ChirithyEntity(owner.level(),owner);
            owner.level().addFreshEntity(chirithy);
            chirithy.setPos(owner.getX(),owner.getY()+2,owner.getZ());
            return;
        });
    }
}
