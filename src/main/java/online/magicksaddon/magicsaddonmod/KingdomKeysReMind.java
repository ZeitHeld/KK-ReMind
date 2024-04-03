package online.magicksaddon.magicsaddonmod;

import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;

import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import online.magicksaddon.magicsaddonmod.ability.ModAbilitiesRM;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesRM;
import online.magicksaddon.magicsaddonmod.client.sound.ModSoundsRM;
import online.magicksaddon.magicsaddonmod.driveform.ModDriveFormsRM;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesRM;
import online.magicksaddon.magicsaddonmod.handler.EntityEventsRM;
import online.magicksaddon.magicsaddonmod.handler.InputHandlerRM;
import online.magicksaddon.magicsaddonmod.item.ModItemsRM;
import online.magicksaddon.magicsaddonmod.magic.ModMagicsRM;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerRM;
import online.magicksaddon.magicsaddonmod.reactioncommands.ModReactionCommandsRM;
import online.magicksaddon.magicsaddonmod.shotlock.ModShotlocksRM;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(KingdomKeysReMind.MODID)
public class KingdomKeysReMind {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "magicksaddon";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    
    public KingdomKeysReMind(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EntityEventsRM());
        ModMagicsRM.MAGIC.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new ModCapabilitiesRM());
        ModSoundsRM.SOUNDS.register(modEventBus);
        ModItemsRM.ITEMS.register(modEventBus);
        ModEntitiesRM.ENTITIES.register(modEventBus);
        ModAbilitiesRM.ABILITIES.register(modEventBus);
        ModDriveFormsRM.DRIVE_FORMS.register(modEventBus);
        ModShotlocksRM.SHOTLOCKS.register(modEventBus);
        ModReactionCommandsRM.REACTION_COMMANDS.register(modEventBus);
        modEventBus.addListener(this::setup);
        TABS.register(modEventBus);

    }

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    private static final Supplier<List<ItemStack>> maItems = Suppliers.memoize(() -> ModItemsRM.ITEMS.getEntries().stream().map(RegistryObject::get).map(ItemStack::new).toList());
//		private static final Supplier<List<ItemStack>> misc = Suppliers.memoize(() -> kkItems.get().stream().filter(item -> !(item.getItem() instanceof KeybladeItem) && !(item.getItem() instanceof IOrgWeapon) && !(item.getItem() instanceof KeychainItem)).toList());


    public static final RegistryObject<CreativeModeTab>

            misc_tab = TABS.register("magicksaddontab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.magicksaddontab"))
            .icon(() -> new ItemStack(ModItemsRM.hasteSpell.get()))
            .displayItems(((params, output) -> {
                maItems.get().forEach(output::accept);
            }))
            .build());

    private void setup(final FMLCommonSetupEvent event){
        // Some common setup code
		event.enqueueWork(PacketHandlerRM::register);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
    		MinecraftForge.EVENT_BUS.register(new InputHandlerRM());

            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }


    }
}
