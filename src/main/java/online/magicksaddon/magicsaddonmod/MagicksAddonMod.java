package online.magicksaddon.magicsaddonmod;


import com.google.common.base.Suppliers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesX;
import online.magicksaddon.magicsaddonmod.network.PacketHandlerX;
import online.magicksaddon.magicsaddonmod.shotlock.AddonShotlocks;
import org.slf4j.Logger;
import net.minecraftforge.registries.RegistryObject;
import com.mojang.logging.LogUtils;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import online.kingdomkeys.kingdomkeys.block.ModBlocks;
import online.kingdomkeys.kingdomkeys.item.KeybladeItem;
import online.kingdomkeys.kingdomkeys.item.KeychainItem;
import online.kingdomkeys.kingdomkeys.item.ModItems;
import online.kingdomkeys.kingdomkeys.item.organization.IOrgWeapon;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import online.magicksaddon.magicsaddonmod.ability.AddonAbilities;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.driveform.AddonForms;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesMA;
import online.magicksaddon.magicsaddonmod.handler.MAInputHandler;
import online.magicksaddon.magicsaddonmod.handler.MagicksEntityEvents;
import online.magicksaddon.magicsaddonmod.item.ModItemsMA;
import online.magicksaddon.magicsaddonmod.magic.ModMagicks;
import online.magicksaddon.magicsaddonmod.lib.StringsX;

import java.util.List;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MagicksAddonMod.MODID)
public class MagicksAddonMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "magicksaddon";
    public static final String MODNAME = "Magicks Addon Mod";
    public static final String MODVER = "0.7a";
    public static final String MCVER = "1.19.4";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    
    public MagicksAddonMod(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new MagicksEntityEvents());
        ModMagicks.MAGIC.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new ModCapabilitiesX());
        MagicSounds.SOUNDS.register(modEventBus);
        ModItemsMA.ITEMS.register(modEventBus);
        ModEntitiesMA.ENTITIES.register(modEventBus);
        AddonAbilities.ABILITIES.register(modEventBus);
        AddonForms.DRIVE_FORMS.register(modEventBus);
        AddonShotlocks.SHOTLOCKS.register(modEventBus);
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::creativeTabRegistry);

    }

    @SubscribeEvent
    public void creativeTabRegistry(CreativeModeTabEvent.Register event) {
        final List<ItemStack> kkItems = ModItemsMA.ITEMS.getEntries().stream().map(RegistryObject::get).map(ItemStack::new).toList();
        final Supplier<List<ItemStack>> misc = Suppliers.memoize(() -> kkItems.stream().filter(item -> !(item.getItem() instanceof KeybladeItem) && !(item.getItem() instanceof IOrgWeapon) && !(item.getItem() instanceof KeychainItem)).toList());

        event.registerCreativeModeTab(new ResourceLocation(MODID, "magicksaddontab"), builder -> {
            builder
                    .title(Component.translatable("itemGroup.magicksaddontab"))
                    .icon(() -> new ItemStack(ModItemsMA.hasteSpell.get()))
                    .displayItems(((params, output) -> {
                        misc.get().forEach(output::accept);
                    }));
        });
    }

    private void setup(final FMLCommonSetupEvent event){
        // Some common setup code
		event.enqueueWork(PacketHandlerX::register);
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
    		MinecraftForge.EVENT_BUS.register(new MAInputHandler());

            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }


    }
}
