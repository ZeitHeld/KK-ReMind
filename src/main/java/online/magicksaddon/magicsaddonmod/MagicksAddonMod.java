package online.magicksaddon.magicsaddonmod;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;

import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.CreativeModeTabEvent;
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
import online.kingdomkeys.kingdomkeys.block.ModBlocks;
import online.kingdomkeys.kingdomkeys.item.KeybladeItem;
import online.kingdomkeys.kingdomkeys.item.KeychainItem;
import online.kingdomkeys.kingdomkeys.item.ModItems;
import online.kingdomkeys.kingdomkeys.item.organization.IOrgWeapon;
import online.magicksaddon.magicsaddonmod.ability.AddonAbilities;
import online.magicksaddon.magicsaddonmod.capabilities.ModCapabilitiesMA;
import online.magicksaddon.magicsaddonmod.client.sound.MagicSounds;
import online.magicksaddon.magicsaddonmod.driveform.AddonForms;
import online.magicksaddon.magicsaddonmod.entity.ModEntitiesMA;
import online.magicksaddon.magicsaddonmod.handler.MAInputHandler;
import online.magicksaddon.magicsaddonmod.handler.MagicksEntityEvents;
import online.magicksaddon.magicsaddonmod.item.ModItemsMA;
import online.magicksaddon.magicsaddonmod.lib.Strings;
import online.magicksaddon.magicsaddonmod.magic.ModMagicks;

import static net.minecraft.world.item.CreativeModeTab.builder;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MagicksAddonMod.MODID)
public class MagicksAddonMod
{

    public static final String MODID = "magicksaddon";
    public static final String MODNAME = "Magicks Addon Mod";
    public static final String MODVER = "0.6.3a";
    public static final String MCVER = "1.20.1";

    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	private static final Supplier<List<ItemStack>> maItems = Suppliers.memoize(() -> ModItemsMA.ITEMS.getEntries().stream().map(RegistryObject::get).map(ItemStack::new).toList());
//		private static final Supplier<List<ItemStack>> misc = Suppliers.memoize(() -> kkItems.get().stream().filter(item -> !(item.getItem() instanceof KeybladeItem) && !(item.getItem() instanceof IOrgWeapon) && !(item.getItem() instanceof KeychainItem)).toList());


        public static final RegistryObject<CreativeModeTab>
        
		misc_tab = TABS.register("magicksaddontab", () -> CreativeModeTab.builder()
				.title(Component.translatable("itemGroup.magicksaddontab"))
				.icon(() -> new ItemStack(ModItemsMA.hasteSpell.get()))
				.displayItems(((params, output) -> {
					maItems.get().forEach(output::accept);
				}))
				.build());
        
    public MagicksAddonMod(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new MagicksEntityEvents());
        ModMagicks.MAGIC.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new ModCapabilitiesMA());
        MagicSounds.SOUNDS.register(modEventBus);
        ModItemsMA.ITEMS.register(modEventBus);
        ModEntitiesMA.ENTITIES.register(modEventBus);
        AddonAbilities.ABILITIES.register(modEventBus);
        AddonForms.DRIVE_FORMS.register(modEventBus);
		//modEventBus.addListener(this::creativeTabRegistry);
        TABS.register(modEventBus);


    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
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
