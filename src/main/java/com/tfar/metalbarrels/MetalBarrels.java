package com.tfar.metalbarrels;

import com.tfar.metalbarrels.block.*;
import com.tfar.metalbarrels.container.*;
import com.tfar.metalbarrels.screen.*;
import com.tfar.metalbarrels.tile.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MetalBarrels.MODID)
public class MetalBarrels
{
  // Directly reference a log4j logger.
  private static final Logger LOGGER = LogManager.getLogger();

  public static final String MODID = "metalbarrels";

  public MetalBarrels() {
    // Register the doClientStuff method for modloading
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void doClientStuff(final FMLClientSetupEvent event) {
    // do something that can only be done on the client
    ScreenManager.registerFactory(MetalBarrels.ObjectHolders.COPPER_CONTAINER, CopperBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.IRON_CONTAINER, IronBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.SILVER_CONTAINER, SilverBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.GOLD_CONTAINER, GoldBarrelScreen::new);
    ScreenManager.registerFactory(ObjectHolders.DIAMOND_CONTAINER, DiamondBarrelScreen::new);
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    private static final Set<Block> MOD_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
      Block.Properties metal = Block.Properties.create(Material.IRON).hardnessAndResistance(2.5f,6);
      Block.Properties obsidian = Block.Properties.create(Material.ROCK).hardnessAndResistance(20,6000);
      registerBlock(new CopperBarrelBlock(metal),"copper_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new IronBarrelBlock(metal),"iron_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new SilverBarrelBlock(metal),"silver_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new GoldBarrelBlock(metal),"gold_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new DiamondBarrelBlock(metal),"diamond_barrel",blockRegistryEvent.getRegistry());
      registerBlock(new DiamondBarrelBlock(obsidian),"obsidian_barrel",blockRegistryEvent.getRegistry());
    }
    private static void registerBlock(Block block, String name, IForgeRegistry<Block> registry) {
      registry.register(block.setRegistryName(name));
      MOD_BLOCKS.add(block);
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {

      IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
      Item.Properties properties1 = new Item.Properties().group(ItemGroup.DECORATIONS);
      for (Block block : MOD_BLOCKS) {
        registerItem(new BlockItem(block, properties1), block.getRegistryName().toString(), registry);
      }
    }

    private static void registerItem(Item item, String name, IForgeRegistry<Item> registry) {
      registry.register(item.setRegistryName(name));
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {

      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new CopperBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("copper_container"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new IronBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("iron_container"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new SilverBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("silver_container"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new GoldBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("gold_container"));
      event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> new DiamondBarrelContainer(windowId, inv.player.world, data.readBlockPos(), inv, inv.player)).setRegistryName("diamond_container"));

    }



    @SubscribeEvent
    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event) {

      event.getRegistry().register(TileEntityType.Builder.create(CopperBarrelTile::new, ObjectHolders.COPPER_BARREL).build(null).setRegistryName("copper_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(IronBarrelTile::new, ObjectHolders.IRON_BARREL).build(null).setRegistryName("iron_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(SilverBarrelTile::new, ObjectHolders.SILVER_BARREL).build(null).setRegistryName("silver_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(GoldBarrelTile::new, ObjectHolders.GOLD_BARREL).build(null).setRegistryName("gold_tile"));
      event.getRegistry().register(TileEntityType.Builder.create(DiamondBarrelTile::new, ObjectHolders.DIAMOND_BARREL,ObjectHolders.OBSIDIAN_BARREL).build(null).setRegistryName("diamond_tile"));

    }
  }

  @ObjectHolder(MetalBarrels.MODID)
  public static class ObjectHolders{

    public static final Block COPPER_BARREL = null;
    public static final ContainerType<CopperBarrelContainer> COPPER_CONTAINER = null;
    public static final TileEntityType<CopperBarrelTile> COPPER_TILE = null;

    public static final Block IRON_BARREL = null;
    public static final ContainerType<IronBarrelContainer> IRON_CONTAINER = null;
    public static final TileEntityType<IronBarrelTile> IRON_TILE = null;

    public static final Block GOLD_BARREL = null;
    public static final ContainerType<GoldBarrelContainer> GOLD_CONTAINER = null;
    public static final TileEntityType<GoldBarrelTile> GOLD_TILE = null;

    public static final Block DIAMOND_BARREL = null;
    public static final ContainerType<DiamondBarrelContainer> DIAMOND_CONTAINER = null;
    public static final TileEntityType<DiamondBarrelTile> DIAMOND_TILE = null;

    public static final Block OBSIDIAN_BARREL = null;

    public static final Block SILVER_BARREL = null;
    public static final ContainerType<SilverBarrelContainer> SILVER_CONTAINER = null;
    public static final TileEntityType<SilverBarrelTile> SILVER_TILE = null;
  }
}
