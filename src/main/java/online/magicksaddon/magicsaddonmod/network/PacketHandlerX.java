package online.magicksaddon.magicsaddonmod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import online.magicksaddon.magicsaddonmod.MagicksAddonMod;
import online.magicksaddon.magicsaddonmod.capabilities.IGlobalCapabilitiesX;
import online.magicksaddon.magicsaddonmod.network.cts.CSPrestigePacket;
import online.magicksaddon.magicsaddonmod.network.stc.SCSyncGlobalCapabilityToAllPacketX;

public class PacketHandlerX {
    private static final String PROTOCOL_VERSION = Integer.toString(1);

	private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MagicksAddonMod.MODID, "main_channel")).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();

    public static void register() {
        int packetID = 0;
System.out.println("REGISTERING PACKETS");
        //ServerToClient
		HANDLER.registerMessage(packetID++, SCSyncGlobalCapabilityToAllPacketX.class, SCSyncGlobalCapabilityToAllPacketX::encode, SCSyncGlobalCapabilityToAllPacketX::decode, SCSyncGlobalCapabilityToAllPacketX::handle);

        // ClientToServer
        HANDLER.registerMessage(packetID++, CSPrestigePacket.class, CSPrestigePacket::encode, CSPrestigePacket::decode, CSPrestigePacket::handle);
    }


        //Saving this for another day...

        public static <MSG> void sendToServer(MSG msg) {
            HANDLER.sendToServer(msg);
        }

        public static <MSG> void sendTo(MSG msg, ServerPlayer player) {
            if (!(player instanceof FakePlayer)) {
                HANDLER.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        }


        public static <MSG> void sendToAllPlayers(MSG msg) {
            HANDLER.send(PacketDistributor.ALL.noArg(), msg);
        }

        public static void syncGlobalToAllAround(LivingEntity entity, IGlobalCapabilitiesX globalData) {
        	//System.out.println("Trying to sync");
            if (!entity.level.isClientSide) {
                for (Player playerFromList : entity.level.players()) {
                    sendTo(new SCSyncGlobalCapabilityToAllPacketX(entity.getId(), (IGlobalCapabilitiesX) globalData), (ServerPlayer) playerFromList);
                }
            }
        }
    }
