package net.hibiscus.naturespirit.registration;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.blocks.*;
import net.hibiscus.naturespirit.blocks.block_entities.PizzaBlockEntity;
import net.hibiscus.naturespirit.blocks.SemiTallGrassBlock;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.datagen.NSConfiguredFeatures;
import net.hibiscus.naturespirit.items.AzollaItem;
import net.hibiscus.naturespirit.items.CheeseArrowItem;
import net.hibiscus.naturespirit.items.DesertTurnipItem;
import net.hibiscus.naturespirit.items.PizzaItem;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;
import static net.hibiscus.naturespirit.registration.NSRegistryHelper.*;

public class NSMiscBlocks {

   public static final Block RED_MOSS_BLOCK = registerBlock("red_moss_block", new RedMossBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).strength(0.1F).sounds(BlockSoundGroup.MOSS_BLOCK).pistonBehavior(PistonBehavior.DESTROY)),  Items.MOSS_BLOCK, ItemGroups.NATURAL);
   public static final Block RED_MOSS_CARPET = registerBlock("red_moss_carpet", new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).strength(0.1F).sounds(BlockSoundGroup.MOSS_CARPET).pistonBehavior(PistonBehavior.DESTROY)),  Items.MOSS_CARPET, ItemGroups.NATURAL);

   public static final Block SANDY_SOIL = registerBlock("sandy_soil",
           new Block(FabricBlockSettings.create().mapColor(MapColor.DIRT_BROWN).instrument(Instrument.BASEDRUM).strength(0.5F).sounds(BlockSoundGroup.GRAVEL)),
           
           Blocks.FARMLAND,
           ItemGroups.NATURAL
   );
   public static final Block PINK_SAND = registerBlock("pink_sand",
           new SandBlock(14331784, FabricBlockSettings.create().mapColor(MapColor.RAW_IRON_PINK).instrument(Instrument.SNARE).strength(0.5F).sounds(BlockSoundGroup.SAND))
   );

   public static final Block PINK_SANDSTONE = registerBlock("pink_sandstone",
           new Block(FabricBlockSettings.create().mapColor(MapColor.PALE_YELLOW).instrument(Instrument.BASEDRUM).requiresTool().strength(0.8F))
   );

   public static final Block CHISELED_PINK_SANDSTONE = registerBlock("chiseled_pink_sandstone", new Block(FabricBlockSettings.create().mapColor(MapColor.PALE_YELLOW).instrument(
           Instrument.BASEDRUM).requiresTool().strength(0.8F)));

   public static final Block CUT_PINK_SANDSTONE = registerBlock("cut_pink_sandstone",
           new Block(FabricBlockSettings.create().mapColor(MapColor.PALE_YELLOW).instrument(Instrument.BASEDRUM).requiresTool().strength(0.8F))
   );

   public static final Block SMOOTH_PINK_SANDSTONE = registerBlock("smooth_pink_sandstone", new Block(FabricBlockSettings.create().mapColor(MapColor.PALE_YELLOW).instrument(
           Instrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F)));

   public static final Block PINK_SANDSTONE_STAIRS = registerBlock(
           "pink_sandstone_stairs",
           new StairsBlock(PINK_SANDSTONE.getDefaultState(), FabricBlockSettings.copy(PINK_SANDSTONE))
   );

   public static final Block SMOOTH_PINK_SANDSTONE_STAIRS = registerBlock("smooth_pink_sandstone_stairs",
           new StairsBlock(SMOOTH_PINK_SANDSTONE.getDefaultState(), FabricBlockSettings.copy(SMOOTH_PINK_SANDSTONE))
   );

   public static final Block PINK_SANDSTONE_SLAB = registerBlock("pink_sandstone_slab", new SlabBlock(FabricBlockSettings.create().mapColor(MapColor.PALE_YELLOW).instrument(
           Instrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F)));

   public static final Block CUT_PINK_SANDSTONE_SLAB = registerBlock("cut_pink_sandstone_slab",
           new SlabBlock(FabricBlockSettings.create().mapColor(MapColor.PALE_YELLOW).instrument(Instrument.BASEDRUM).requiresTool().strength(2.0F, 6.0F))
   );

   public static final Block SMOOTH_PINK_SANDSTONE_SLAB = registerBlock("smooth_pink_sandstone_slab", new SlabBlock(FabricBlockSettings.copy(SMOOTH_PINK_SANDSTONE)));

   public static final Block PINK_SANDSTONE_WALL = registerBlock("pink_sandstone_wall", new WallBlock(FabricBlockSettings.copy(PINK_SANDSTONE).solid()));

   public static final Block TALL_FRIGID_GRASS = registerPlantBlock("tall_frigid_grass",
           new SemiTallGrassBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.LICHEN_GREEN)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           Blocks.LARGE_FERN,
           0.3f
   );

   public static final Block FRIGID_GRASS = registerPlantBlock("frigid_grass",
           new HibiscusFernBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.LICHEN_GREEN)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY), (TallPlantBlock) TALL_FRIGID_GRASS),
           
           Blocks.FERN,
           0.3f
   );

   public static final Block TALL_SCORCHED_GRASS = registerPlantBlock("tall_scorched_grass",
           new TallLargeDesertFernBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.LICHEN_GREEN)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           Blocks.LARGE_FERN,
           0.3f
   );

   public static final Block SCORCHED_GRASS = registerPlantBlock("scorched_grass",
           new LargeDesertFernBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.LICHEN_GREEN)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY), (TallPlantBlock) TALL_SCORCHED_GRASS),
           
           Blocks.FERN,
           0.3f
   );
   public static final Block TALL_BEACH_GRASS = registerPlantBlock("tall_beach_grass",
           new TallLargeDesertFernBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.YELLOW)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           Blocks.LARGE_FERN,
           0.3f
   );

   public static final Block BEACH_GRASS = registerPlantBlock("beach_grass",
           new LargeDesertFernBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.YELLOW)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY), (TallPlantBlock) TALL_BEACH_GRASS),
           
           Blocks.FERN,
           0.3f
   );

   public static final Block TALL_SEDGE_GRASS = registerPlantBlock("tall_sedge_grass",
           new TallSedgeGrassBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.DARK_GREEN)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           TALL_SCORCHED_GRASS,
           0.3f
   );

   public static final Block SEDGE_GRASS = registerPlantBlock("sedge_grass",
           new SedgeGrassBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.DARK_GREEN)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           SCORCHED_GRASS,
           0.3f
   );

   public static final Block LARGE_FLAXEN_FERN = registerPlantBlock("large_flaxen_fern",
           new TallPlantBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           TALL_SEDGE_GRASS,
           0.3f
   );

   public static final Block FLAXEN_FERN = registerPlantBlock("flaxen_fern",
           new HibiscusFernBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY), (TallPlantBlock) LARGE_FLAXEN_FERN),
           
           SEDGE_GRASS,
           0.3f
   );
   public static final Block TALL_OAT_GRASS = registerPlantBlock("tall_oat_grass",
           new SemiTallGrassBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.PALE_YELLOW)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           LARGE_FLAXEN_FERN,
           0.3f
   );

   public static final Block OAT_GRASS = registerPlantBlock("oat_grass",
           new HibiscusFernBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.PALE_YELLOW)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY), (TallPlantBlock) TALL_OAT_GRASS),
           
           FLAXEN_FERN,
           0.3f
   );

   public static final Block LARGE_LUSH_FERN = registerPlantBlock("large_lush_fern",
           new TallPlantBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           TALL_OAT_GRASS,
           0.3f
   );

   public static final Block LUSH_FERN = registerPlantBlock("lush_fern",
           new HibiscusFernBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY), (TallPlantBlock) LARGE_LUSH_FERN),
           
           OAT_GRASS,
           0.3f
   );
   public static final Block TALL_MELIC_GRASS = registerPlantBlock("tall_melic_grass",
           new TallPlantBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.GREEN)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           LARGE_LUSH_FERN,
           0.3f
   );

   public static final Block MELIC_GRASS = registerPlantBlock("melic_grass",
           new HibiscusFernBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.GREEN)
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY), (TallPlantBlock) TALL_MELIC_GRASS),
           
           LUSH_FERN,
           0.3f
   );
   public static final Block GREEN_BEARBERRIES = registerPlantBlock("green_bearberries",
           new BearberryBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           MELIC_GRASS,
           0.3f
   );
   public static final Block RED_BEARBERRIES = registerPlantBlock("red_bearberries",
           new BearberryBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           GREEN_BEARBERRIES,
           0.3f
   );
   public static final Block PURPLE_BEARBERRIES = registerPlantBlock("purple_bearberries",
           new BearberryBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XYZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           RED_BEARBERRIES,
           0.3f
   );
   public static final Block GREEN_BITTER_SPROUTS = registerPlantBlock("green_bitter_sprouts",
           new LargeSproutsBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           PURPLE_BEARBERRIES,
           0.3f
   );
   public static final Block RED_BITTER_SPROUTS = registerPlantBlock("red_bitter_sprouts",
           new LargeSproutsBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           GREEN_BITTER_SPROUTS,
           0.3f
   );
   public static final Block PURPLE_BITTER_SPROUTS = registerPlantBlock("purple_bitter_sprouts",
           new LargeSproutsBlock(FabricBlockSettings
                   .create()
                   .noCollision()
                   .replaceable()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .offset(AbstractBlock.OffsetType.XZ)
                   .pistonBehavior(PistonBehavior.DESTROY)),
           
           RED_BITTER_SPROUTS,
           0.3f
   );
   public static final Block CATTAIL = registerPlantBlock("cattail",
           new CattailBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.DARK_GREEN)
                   .noCollision()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .burnable()
                   .pistonBehavior(PistonBehavior.DESTROY)),
           Blocks.LARGE_FERN,
           0.4f
   );

   public static final Block AZOLLA = registerTransparentBlockWithoutTab("azolla",
           new AzollaBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.GREEN)
                   .pistonBehavior(PistonBehavior.DESTROY)
                   .ticksRandomly()
                   .nonOpaque()
                   .breakInstantly()
                   .noCollision()
                   .sounds(BlockSoundGroup.LILY_PAD))
   );

   public static final Item AZOLLA_ITEM = registerItem("azolla",
           new AzollaItem(AZOLLA, new Item.Settings()),
           Items.LILY_PAD,
           ItemGroups.NATURAL
   );

   public static final Block ORNATE_SUCCULENT = registerTransparentBlockWithoutTab("ornate_succulent", new SucculentBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
   public static final Block DROWSY_SUCCULENT = registerTransparentBlockWithoutTab("drowsy_succulent", new SucculentBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
   public static final Block AUREATE_SUCCULENT = registerTransparentBlockWithoutTab("aureate_succulent", new SucculentBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
   public static final Block SAGE_SUCCULENT = registerTransparentBlockWithoutTab("sage_succulent", new SucculentBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
   public static final Block FOAMY_SUCCULENT = registerTransparentBlockWithoutTab("foamy_succulent", new SucculentBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
   public static final Block IMPERIAL_SUCCULENT = registerTransparentBlockWithoutTab("imperial_succulent", new SucculentBlock(AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
   public static final Block REGAL_SUCCULENT = registerTransparentBlockWithoutTab("regal_succulent", new SucculentBlock(AbstractBlock.Settings.create().mapColor(MapColor.PINK).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
   public static final Block ORNATE_WALL_SUCCULENT = registerTransparentBlockWithoutTab("ornate_wall_succulent", new SucculentWallBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly().dropsLike(ORNATE_SUCCULENT)));
   public static final Block DROWSY_WALL_SUCCULENT = registerTransparentBlockWithoutTab("drowsy_wall_succulent", new SucculentWallBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly().dropsLike(DROWSY_SUCCULENT)));
   public static final Block AUREATE_WALL_SUCCULENT = registerTransparentBlockWithoutTab("aureate_wall_succulent", new SucculentWallBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly().dropsLike(AUREATE_SUCCULENT)));
   public static final Block SAGE_WALL_SUCCULENT = registerTransparentBlockWithoutTab("sage_wall_succulent", new SucculentWallBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly().dropsLike(SAGE_SUCCULENT)));
   public static final Block FOAMY_WALL_SUCCULENT = registerTransparentBlockWithoutTab("foamy_wall_succulent", new SucculentWallBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_BLUE).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly().dropsLike(FOAMY_SUCCULENT)));
   public static final Block IMPERIAL_WALL_SUCCULENT = registerTransparentBlockWithoutTab("imperial_wall_succulent", new SucculentWallBlock(AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly().dropsLike(IMPERIAL_SUCCULENT)));
   public static final Block REGAL_WALL_SUCCULENT = registerTransparentBlockWithoutTab("regal_wall_succulent", new SucculentWallBlock(AbstractBlock.Settings.create().mapColor(MapColor.PINK).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly().dropsLike(REGAL_SUCCULENT)));

   public static final Item ORNATE_SUCCULENT_ITEM = registerItem("ornate_succulent", new VerticallyAttachableBlockItem(ORNATE_SUCCULENT, ORNATE_WALL_SUCCULENT, new FabricItemSettings(), Direction.DOWN),  Items.PITCHER_PLANT, ItemGroups.NATURAL);
   public static final Item DROWSY_SUCCULENT_ITEM = registerItem("drowsy_succulent", new VerticallyAttachableBlockItem(DROWSY_SUCCULENT, DROWSY_WALL_SUCCULENT, new FabricItemSettings(), Direction.DOWN),  ORNATE_SUCCULENT_ITEM, ItemGroups.NATURAL);
   public static final Item AUREATE_SUCCULENT_ITEM = registerItem("aureate_succulent", new VerticallyAttachableBlockItem(AUREATE_SUCCULENT, AUREATE_WALL_SUCCULENT, new FabricItemSettings(), Direction.DOWN),  DROWSY_SUCCULENT_ITEM, ItemGroups.NATURAL);
   public static final Item SAGE_SUCCULENT_ITEM = registerItem("sage_succulent", new VerticallyAttachableBlockItem(SAGE_SUCCULENT, SAGE_WALL_SUCCULENT, new FabricItemSettings(), Direction.DOWN),  AUREATE_SUCCULENT_ITEM, ItemGroups.NATURAL);
   public static final Item FOAMY_SUCCULENT_ITEM = registerItem("foamy_succulent", new VerticallyAttachableBlockItem(FOAMY_SUCCULENT, FOAMY_WALL_SUCCULENT, new FabricItemSettings(), Direction.DOWN),  SAGE_SUCCULENT_ITEM, ItemGroups.NATURAL);
   public static final Item IMPERIAL_SUCCULENT_ITEM = registerItem("imperial_succulent", new VerticallyAttachableBlockItem(IMPERIAL_SUCCULENT, IMPERIAL_WALL_SUCCULENT, new FabricItemSettings(), Direction.DOWN),  FOAMY_SUCCULENT_ITEM, ItemGroups.NATURAL);
   public static final Item REGAL_SUCCULENT_ITEM = registerItem("regal_succulent", new VerticallyAttachableBlockItem(REGAL_SUCCULENT, REGAL_WALL_SUCCULENT, new FabricItemSettings(), Direction.DOWN),  IMPERIAL_SUCCULENT_ITEM, ItemGroups.NATURAL);
   
   public static final Block SHIITAKE_MUSHROOM = registerPlantBlock("shiitake_mushroom", new ShiitakeMushroomPlantBlock(
           AbstractBlock.Settings
                   .create()
                   .mapColor(MapColor.BROWN)
                   .noCollision()
                   .ticksRandomly()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .luminance((state) -> 1)
                   .postProcess(Blocks::always)
                   .pistonBehavior(PistonBehavior.DESTROY),
           NSConfiguredFeatures.HUGE_SHIITAKE_MUSHROOM
   ),  Blocks.RED_MUSHROOM, 0.1F);

   public static final Block SHIITAKE_MUSHROOM_BLOCK = registerBlock("shiitake_mushroom_block",
           new MushroomBlock(FabricBlockSettings.create().mapColor(MapColor.DIRT_BROWN).instrument(Instrument.BASS).strength(0.2F).sounds(BlockSoundGroup.WOOD).burnable()),

           Blocks.RED_MUSHROOM_BLOCK,
           ItemGroups.NATURAL
   );
   public static final Block GRAY_POLYPORE = registerPlantBlock("gray_polypore", new PolyporeBlock(
           AbstractBlock.Settings
                   .create()
                   .mapColor(MapColor.BROWN)
                   .noCollision()
                   .ticksRandomly()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.GRASS)
                   .luminance((state) -> 1)
                   .postProcess(Blocks::always)
                   .pistonBehavior(PistonBehavior.DESTROY),
           NSConfiguredFeatures.GRAY_POLYPORE
   ),  SHIITAKE_MUSHROOM, 0.1F);

   public static final Block GRAY_POLYPORE_BLOCK = registerBlock("gray_polypore_block",
           new MushroomBlock(FabricBlockSettings.create().mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).instrument(Instrument.BASS).strength(0.2F).sounds(BlockSoundGroup.WOOD).burnable()),

           SHIITAKE_MUSHROOM_BLOCK,
           ItemGroups.NATURAL
   );
   
   public static final Block POTTED_SCORCHED_GRASS = registerTransparentBlockWithoutTab("potted_scorched_grass", new FlowerPotBlock(SCORCHED_GRASS, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_BEACH_GRASS = registerTransparentBlockWithoutTab("potted_beach_grass", new FlowerPotBlock(BEACH_GRASS, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_SEDGE_GRASS = registerTransparentBlockWithoutTab("potted_sedge_grass", new FlowerPotBlock(SEDGE_GRASS, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_FLAXEN_FERN = registerTransparentBlockWithoutTab("potted_flaxen_fern", new FlowerPotBlock(FLAXEN_FERN, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_OAT_GRASS = registerTransparentBlockWithoutTab("potted_oat_grass", new FlowerPotBlock(OAT_GRASS, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_MELIC_GRASS = registerTransparentBlockWithoutTab("potted_melic_grass", new FlowerPotBlock(MELIC_GRASS, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_LUSH_FERN = registerTransparentBlockWithoutTab("potted_lush_fern", new FlowerPotBlock(LUSH_FERN, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_FRIGID_GRASS = registerTransparentBlockWithoutTab("potted_frigid_grass", new FlowerPotBlock(FRIGID_GRASS, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_GREEN_BEARBERRIES = registerTransparentBlockWithoutTab("potted_green_bearberries", new FlowerPotBlock(GREEN_BEARBERRIES, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_RED_BEARBERRIES = registerTransparentBlockWithoutTab("potted_red_bearberries", new FlowerPotBlock(RED_BEARBERRIES, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_PURPLE_BEARBERRIES = registerTransparentBlockWithoutTab("potted_purple_bearberries", new FlowerPotBlock(PURPLE_BEARBERRIES, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_ORNATE_SUCCULENT = registerTransparentBlockWithoutTab("potted_ornate_succulent", new FlowerPotBlock(ORNATE_SUCCULENT, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_DROWSY_SUCCULENT = registerTransparentBlockWithoutTab("potted_drowsy_succulent", new FlowerPotBlock(DROWSY_SUCCULENT, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_AUREATE_SUCCULENT = registerTransparentBlockWithoutTab("potted_aureate_succulent", new FlowerPotBlock(AUREATE_SUCCULENT, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_SAGE_SUCCULENT = registerTransparentBlockWithoutTab("potted_sage_succulent", new FlowerPotBlock(SAGE_SUCCULENT, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_FOAMY_SUCCULENT = registerTransparentBlockWithoutTab("potted_foamy_succulent", new FlowerPotBlock(FOAMY_SUCCULENT, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_IMPERIAL_SUCCULENT = registerTransparentBlockWithoutTab("potted_imperial_succulent", new FlowerPotBlock(IMPERIAL_SUCCULENT, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_REGAL_SUCCULENT = registerTransparentBlockWithoutTab("potted_regal_succulent", new FlowerPotBlock(REGAL_SUCCULENT, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
   public static final Block POTTED_SHIITAKE_MUSHROOM = registerTransparentBlockWithoutTab("potted_shiitake_mushroom", new FlowerPotBlock(SHIITAKE_MUSHROOM, FabricBlockSettings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

   public static final FlowerSet LAVENDER = new FlowerSet("lavender", Items.PURPLE_DYE, Items.PEONY, FlowerSet.FlowerPreset.BIG_TALL);
   public static final FlowerSet BLEEDING_HEART = new FlowerSet("bleeding_heart", Items.PINK_DYE, LAVENDER.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_TALL);
   public static final FlowerSet BLUE_BULBS = new FlowerSet("blue_bulbs", Items.BLUE_DYE, BLEEDING_HEART.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_TALL);
   public static final FlowerSet CARNATION = new FlowerSet("carnation", Items.RED_DYE, BLUE_BULBS.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_TALL);

   public static final FlowerSet GARDENIA = new FlowerSet("gardenia", Items.WHITE_DYE, CARNATION.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.TALL);
   public static final FlowerSet SNAPDRAGON = new FlowerSet("snapdragon", Items.PINK_DYE, GARDENIA.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.TALL);
   public static final FlowerSet MARIGOLD = new FlowerSet("marigold", Items.ORANGE_DYE, SNAPDRAGON.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.TALL);
   public static final FlowerSet FOXGLOVE = new FlowerSet("foxglove", Items.PURPLE_DYE, MARIGOLD.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.TALL);

   public static final FlowerSet BLUEBELL = new FlowerSet("bluebell", Items.BLUE_DYE, StatusEffects.HASTE, Items.LILY_OF_THE_VALLEY, FlowerSet.FlowerPreset.BIG_SMALL);
   public static final FlowerSet TIGER_LILY = new FlowerSet("tiger_lily", Items.ORANGE_DYE, StatusEffects.FIRE_RESISTANCE, BLUEBELL.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_SMALL);
   public static final FlowerSet PURPLE_WILDFLOWER = new FlowerSet("purple_wildflower", Items.PURPLE_DYE, StatusEffects.SLOW_FALLING, TIGER_LILY.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_SMALL);
   public static final FlowerSet YELLOW_WILDFLOWER = new FlowerSet("yellow_wildflower", Items.YELLOW_DYE, StatusEffects.SLOW_FALLING, PURPLE_WILDFLOWER.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_SMALL);
   public static final FlowerSet RED_HEATHER = new FlowerSet("red_heather", Items.RED_DYE, StatusEffects.FIRE_RESISTANCE, YELLOW_WILDFLOWER.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_SMALL);
   public static final FlowerSet WHITE_HEATHER = new FlowerSet("white_heather", Items.WHITE_DYE, StatusEffects.FIRE_RESISTANCE, RED_HEATHER.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_SMALL);
   public static final FlowerSet PURPLE_HEATHER = new FlowerSet("purple_heather", Items.PURPLE_DYE, StatusEffects.FIRE_RESISTANCE, WHITE_HEATHER.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_SMALL);
   public static final FlowerSet ANEMONE = new FlowerSet("anemone", Items.MAGENTA_DYE, StatusEffects.RESISTANCE, PURPLE_HEATHER.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.MID_SMALL);
   public static final FlowerSet DWARF_BLOSSOMS = new FlowerSet("dwarf_blossoms", Items.PINK_DYE, StatusEffects.RESISTANCE, ANEMONE.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.MID_SMALL);
   public static final FlowerSet PROTEA = new FlowerSet("protea", Items.PINK_DYE, StatusEffects.WATER_BREATHING, DWARF_BLOSSOMS.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.MID_SMALL);
   public static final FlowerSet HIBISCUS = new FlowerSet("hibiscus", Items.RED_DYE, StatusEffects.LUCK, PROTEA.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.SMALL);
   public static final FlowerSet BLUE_IRIS = new FlowerSet("blue_iris", Items.BLUE_DYE, StatusEffects.STRENGTH, HIBISCUS.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.SMALL);
   public static final FlowerSet BLACK_IRIS = new FlowerSet("black_iris", Items.BLACK_DYE, StatusEffects.STRENGTH, BLUE_IRIS.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.SMALL);
   public static final FlowerSet RUBY_BLOSSOMS = new FlowerSet("ruby_blossoms", Items.RED_DYE, StatusEffects.JUMP_BOOST, BLACK_IRIS.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_SMALL);
   public static final FlowerSet SILVERBUSH = new FlowerSet("silverbush", RUBY_BLOSSOMS.getFlowerBlock().asItem(), FlowerSet.FlowerPreset.BIG_TALL);


   public static final Block HELVOLA = NSRegistryHelper.registerTransparentBlock("helvola",
           new WaterFlowerbedBlock(FabricBlockSettings
                   .create()
                   .pistonBehavior(PistonBehavior.DESTROY)
                   .ticksRandomly()
                   .nonOpaque()
                   .breakInstantly().slipperiness(0.8F)
                   .sounds(BlockSoundGroup.LILY_PAD))
   );

   public static final Item HELVOLA_PAD_ITEM = registerItem("helvola_pad",
           new PlaceableOnWaterItem(HELVOLA, new Item.Settings()),
           
           Items.LILY_PAD,
           ItemGroups.NATURAL
   );

   public static final Item HELVOLA_FLOWER_ITEM = registerItem("helvola_flower",
           new Item(new Item.Settings()),
           
           HELVOLA_PAD_ITEM,
           ItemGroups.NATURAL
   );

   public static final Block LOTUS_FLOWER = NSRegistryHelper.registerTransparentBlockWithoutTab("lotus_flower",
           new LotusFlowerBlock(FabricBlockSettings
                   .create()
                   .mapColor(MapColor.PINK)
                   .pistonBehavior(PistonBehavior.DESTROY)
                   .ticksRandomly()
                   .nonOpaque()
                   .breakInstantly()
                   .sounds(BlockSoundGroup.LILY_PAD))
   );

   public static final Item LOTUS_FLOWER_ITEM = registerItem("lotus_flower",
           new PlaceableOnWaterItem(LOTUS_FLOWER, new Item.Settings()),
           HELVOLA_FLOWER_ITEM,
           ItemGroups.NATURAL
   );

   public static final Block LOTUS_STEM = registerPlantBlock("lotus_stem", new LotusStemBlock(FabricBlockSettings
           .create()
           .mapColor(MapColor.GREEN)
           .pistonBehavior(PistonBehavior.DESTROY)
           .noCollision()
           .nonOpaque()
           .breakInstantly()
           .sounds(BlockSoundGroup.LILY_PAD), LOTUS_FLOWER),  LOTUS_FLOWER, 0.2F);

   public static final Block ALLUAUDIA = registerPlantBlock("alluaudia", new GrowingBranchingTrunkBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.GREEN).nonOpaque().sounds(BlockSoundGroup.VINE).hardness(.5f).strength(.5f).notSolid()),  Items.CACTUS, .2f);
   public static final Block STRIPPED_ALLUAUDIA = registerPlantBlock("stripped_alluaudia", new GrowingBranchingTrunkBlock(FabricBlockSettings.create().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.OAK_TAN).nonOpaque().sounds(BlockSoundGroup.VINE).hardness(.5f).strength(.5f).notSolid()),  NSMiscBlocks.ALLUAUDIA, .2f);
   public static final Block ALLUAUDIA_BUNDLE = registerPlantBlock("alluaudia_bundle", new PillarBlock(FabricBlockSettings.create().mapColor(MapColor.GREEN).nonOpaque().sounds(BlockSoundGroup.VINE).hardness(.6f).strength(.6f)),  STRIPPED_ALLUAUDIA, .2f);
   public static final Block STRIPPED_ALLUAUDIA_BUNDLE = registerPlantBlock("stripped_alluaudia_bundle", new PillarBlock(FabricBlockSettings.create().mapColor(MapColor.OAK_TAN).nonOpaque().sounds(BlockSoundGroup.VINE).hardness(.6f).strength(.6f)),  ALLUAUDIA_BUNDLE, .2f);

   
   public static final FoodComponent GREEN_OLIVE_COMPONENT = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.4F).build();
   public static final FoodComponent BLACK_OLIVE_COMPONENT = (new FoodComponent.Builder()).hunger(3).saturationModifier(0.5F).build();

   public static final Item GREEN_OLIVES = registerPlantItem("green_olives",
           new Item(new FabricItemSettings().food(GREEN_OLIVE_COMPONENT)),
           Items.BEETROOT,
           ItemGroups.FOOD_AND_DRINK,
           0.3F
   );

   public static final Item BLACK_OLIVES = registerPlantItem("black_olives",
           new Item(new FabricItemSettings().food(BLACK_OLIVE_COMPONENT)),
           GREEN_OLIVES,
           ItemGroups.FOOD_AND_DRINK,
           0.3F
   );

   public static final Block DESERT_TURNIP_ROOT_BLOCK = registerBlock("desert_turnip_root_block",
           new PillarBlock(FabricBlockSettings.create().burnable().instrument(Instrument.BASS).mapColor(MapColor.SPRUCE_BROWN).strength(1.0F).sounds(BlockSoundGroup.ROOTS)),
           Blocks.SHROOMLIGHT,
           ItemGroups.NATURAL
   );

   public static final Block DESERT_TURNIP_BLOCK = registerBlock("desert_turnip_block",
           new DesertTurnipBlock(FabricBlockSettings.create().burnable().instrument(Instrument.BASS).mapColor(MapColor.PALE_PURPLE).strength(1.0F).sounds(BlockSoundGroup.ROOTS)),
           NSMiscBlocks.DESERT_TURNIP_ROOT_BLOCK,
           ItemGroups.NATURAL
   );

   public static final Block DESERT_TURNIP_STEM = registerTransparentBlock("desert_turnip_stem", new DesertTurnipStemBlock(
           (DesertTurnipBlock) DESERT_TURNIP_BLOCK,
           DESERT_TURNIP_ROOT_BLOCK,
           FabricBlockSettings.create().noCollision().breakInstantly().ticksRandomly().sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY)
   ));

   public static final FoodComponent DESERT_TURNIP_FOOD_COMPONENT = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).snack().build();

   public static final Item DESERT_TURNIP = registerItem("desert_turnip",
           new DesertTurnipItem(DESERT_TURNIP_STEM, (new Item.Settings()).food(DESERT_TURNIP_FOOD_COMPONENT)),
           Items.BEETROOT,
           ItemGroups.FOOD_AND_DRINK
   );

   public static final Item CHALK_POWDER = registerItem(
           "chalk_powder",
           new Item((new Item.Settings())),
           Items.HONEYCOMB,
           ItemGroups.INGREDIENTS
   );

   public static final Block CHEESE_BLOCK = registerBlockWithoutTab("cheese_block",
           new CheeseBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).strength(2.0F, 1.0F).sounds(BlockSoundGroup.AZALEA_LEAVES))
   );

   public static final Item CHEESE_BUCKET = registerItem("cheese_bucket",
           new PowderSnowBucketItem(CHEESE_BLOCK, SoundEvents.ITEM_BUCKET_EMPTY, (new Item.Settings()).maxCount(1).recipeRemainder(Items.BUCKET)),
           
           Items.MILK_BUCKET,
           ItemGroups.FOOD_AND_DRINK
   );
   public static final Item CHEESE_ARROW = NSConfig.cheese_arrow ? registerItem("cheese_arrow", new CheeseArrowItem(new Item.Settings()),  Items.SPECTRAL_ARROW, ItemGroups.COMBAT) : null;

   public static final Block MILK_CAULDRON = registerBlockWithoutTab("milk_cauldron", new MilkCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON).dropsLike(Blocks.CAULDRON)));

   public static final Block CHEESE_CAULDRON = registerBlockWithoutTab("cheese_cauldron", new CheeseCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON).dropsLike(Blocks.CAULDRON)));

   public static final FoodComponent STANDARD_PIZZA_COMPONENT = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.2F).build();

   public static final Block PIZZA_BLOCK = registerBlockWithoutTab("pizza_block", new PizzaBlock(FabricBlockSettings.copy(Blocks.CAKE)));
   public static final BlockEntityType <PizzaBlockEntity> PIZZA_BLOCK_ENTITY_TYPE = NSRegistryHelper.registerBlockEntity("pizza_block_entity", FabricBlockEntityTypeBuilder.create(PizzaBlockEntity::new, PIZZA_BLOCK));

   public static final Item WHOLE_PIZZA = registerItem("whole_pizza",
           new PizzaItem(PIZZA_BLOCK, new Item.Settings().maxCount(1).food(STANDARD_PIZZA_COMPONENT)),
           
           Items.BREAD,
           ItemGroups.FOOD_AND_DRINK
   );

   public static final Item THREE_QUARTERS_PIZZA = registerItemWithoutTab("three_quarters_pizza", new PizzaItem(PIZZA_BLOCK, new Item.Settings().maxCount(1).food(STANDARD_PIZZA_COMPONENT)));

   public static final Item HALF_PIZZA = registerItemWithoutTab("half_pizza", new PizzaItem(PIZZA_BLOCK, new Item.Settings().maxCount(1).food(STANDARD_PIZZA_COMPONENT)));

   public static final Item QUARTER_PIZZA = registerItemWithoutTab("quarter_pizza", new PizzaItem(PIZZA_BLOCK, new Item.Settings().maxCount(1).food(STANDARD_PIZZA_COMPONENT)));

   public static final Item CALCITE_SHARD = registerItem(
           "calcite_shard",
           new Item(new Item.Settings()),
           
           Items.AMETHYST_SHARD,
           ItemGroups.INGREDIENTS
   );

   public static final Block CALCITE_CLUSTER = registerBlock("calcite_cluster", new AmethystClusterBlock(7,
           3,
           FabricBlockSettings
                   .create()
                   .mapColor(MapColor.WHITE)
                   .solid()
                   .nonOpaque()
                   .ticksRandomly()
                   .sounds(BlockSoundGroup.CALCITE)
                   .strength(1.5F)
                   .pistonBehavior(PistonBehavior.DESTROY)
   ),  Blocks.AMETHYST_CLUSTER, ItemGroups.NATURAL);

   public static final Block LARGE_CALCITE_BUD = registerBlock("large_calcite_bud",
           new AmethystClusterBlock(4, 3, FabricBlockSettings.copy(CALCITE_CLUSTER).sounds(BlockSoundGroup.CALCITE).solid().pistonBehavior(PistonBehavior.DESTROY)),
           
           CALCITE_CLUSTER,
           ItemGroups.NATURAL
   );

   public static final Block SMALL_CALCITE_BUD = registerBlock("small_calcite_bud",
           new AmethystClusterBlock(3, 4, FabricBlockSettings.copy(CALCITE_CLUSTER).sounds(BlockSoundGroup.CALCITE).solid().pistonBehavior(PistonBehavior.DESTROY)),
           
           LARGE_CALCITE_BUD,
           ItemGroups.NATURAL
   );


   public static final StoneSet TRAVERTINE = new StoneSet(new Identifier(MOD_ID, "travertine"), MapColor.LIGHT_GRAY, Items.POLISHED_ANDESITE_SLAB, Items.ANDESITE, 1.5F, true, false, true, true);
   public static final StoneSet CHERT = new StoneSet(new Identifier(MOD_ID, "chert"), MapColor.OAK_TAN, TRAVERTINE.getTilesSlab().asItem(), TRAVERTINE.getBase().asItem(), .9F, false, false, false, true, true);

   public static final Block CHERT_GOLD_ORE = registerBlock("chert_gold_ore",
           new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.GOLD_ORE).mapColor(MapColor.OAK_TAN).strength(.9f), ConstantIntProvider.create(0)),
           
           Items.GOLD_ORE,
           ItemGroups.NATURAL
   );
   public static final Block CHERT_IRON_ORE = registerBlock("chert_iron_ore",
           new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.IRON_ORE).mapColor(MapColor.OAK_TAN).strength(.9f), ConstantIntProvider.create(0)),
           
           Items.IRON_ORE,
           ItemGroups.NATURAL
   );
   public static final Block CHERT_COAL_ORE = registerBlock("chert_coal_ore",
           new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COAL_ORE).mapColor(MapColor.OAK_TAN).strength(.9f), UniformIntProvider.create(0, 2)),
           
           Items.COAL_ORE,
           ItemGroups.NATURAL
   );
   public static final Block CHERT_LAPIS_ORE = registerBlock("chert_lapis_ore",
           new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.LAPIS_ORE).mapColor(MapColor.OAK_TAN).strength(.9f), UniformIntProvider.create(2, 5)),
           
           Items.LAPIS_ORE,
           ItemGroups.NATURAL
   );
   public static final Block CHERT_DIAMOND_ORE = registerBlock("chert_diamond_ore",
           new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE).mapColor(MapColor.OAK_TAN).strength(.9f), UniformIntProvider.create(3, 7)),
           
           Items.DIAMOND_ORE,
           ItemGroups.NATURAL
   );
   public static final Block CHERT_REDSTONE_ORE = registerBlock("chert_redstone_ore",
           new RedstoneOreBlock(FabricBlockSettings.copy(Blocks.REDSTONE_ORE).mapColor(MapColor.OAK_TAN).strength(.6f)),
           
           Items.REDSTONE_ORE,
           ItemGroups.NATURAL
   );
   public static final Block CHERT_EMERALD_ORE = registerBlock("chert_emerald_ore",
           new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.EMERALD_ORE).mapColor(MapColor.OAK_TAN).strength(.6f), UniformIntProvider.create(3, 7)),
           
           Items.EMERALD_ORE,
           ItemGroups.NATURAL
   );
   public static final Block CHERT_COPPER_ORE = registerBlock("chert_copper_ore",
           new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COPPER_ORE).mapColor(MapColor.OAK_TAN).strength(.6f), ConstantIntProvider.create(0)),
           
           Items.COPPER_ORE,
           ItemGroups.NATURAL
   );


   public static final BlockSetType PAPER_BLOCK_SET = BlockSetTypeBuilder.copyOf(BlockSetType.CHERRY).register(new Identifier(MOD_ID, "paper"));
   public static final WoodType PAPER_WOOD_TYPE = new WoodTypeBuilder().register(new Identifier(MOD_ID, "paper"), PAPER_BLOCK_SET);

   public static final Block PAPER_BLOCK = registerBlock("paper_block",
           new Block(FabricBlockSettings.copy(NSWoods.SUGI.getPlanks())),
           
           Items.WARPED_BUTTON,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block PAPER_PANEL = registerBlock("paper_panel",
           new PaneBlock(FabricBlockSettings.copy(NSWoods.SUGI.getPlanks())),
           
           PAPER_BLOCK,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block PAPER_DOOR = registerTransparentBlock("paper_door",
           new DoorBlock(FabricBlockSettings.copy(NSWoods.SUGI.getDoor()), PAPER_BLOCK_SET),
           PAPER_PANEL,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block PAPER_TRAPDOOR = registerTransparentBlock("paper_trapdoor", new TrapdoorBlock(
           FabricBlockSettings.create().burnable().instrument(Instrument.BASS).strength(3.0f).sounds(BlockSoundGroup.WOOD).allowsSpawning(NSRegistryHelper::never).nonOpaque(),
           PAPER_BLOCK_SET
   ),  PAPER_DOOR, ItemGroups.BUILDING_BLOCKS);
   public static final Block FRAMED_PAPER_BLOCK = registerBlock("framed_paper_block",
           new Block(FabricBlockSettings.copy(NSWoods.SUGI.getPlanks())),
           PAPER_TRAPDOOR,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block FRAMED_PAPER_PANEL = registerBlock("framed_paper_panel",
           new PaneBlock(FabricBlockSettings.copy(NSWoods.SUGI.getPlanks())),
           
           FRAMED_PAPER_BLOCK,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block FRAMED_PAPER_DOOR = registerTransparentBlock("framed_paper_door",
           new DoorBlock(FabricBlockSettings.copy(NSWoods.SUGI.getDoor()), PAPER_BLOCK_SET),
           FRAMED_PAPER_PANEL,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block FRAMED_PAPER_TRAPDOOR = registerTransparentBlock("framed_paper_trapdoor", new TrapdoorBlock(
           FabricBlockSettings.create().burnable().instrument(Instrument.BASS).strength(3.0f).sounds(BlockSoundGroup.WOOD).allowsSpawning(NSRegistryHelper::never).nonOpaque(),
           PAPER_BLOCK_SET
   ),  FRAMED_PAPER_DOOR, ItemGroups.BUILDING_BLOCKS);
   public static final Block BLOOMING_PAPER_BLOCK = registerBlock("blooming_paper_block",
           new GlazedTerracottaBlock(FabricBlockSettings.copy(NSWoods.SUGI.getPlanks())),
           FRAMED_PAPER_TRAPDOOR,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block BLOOMING_PAPER_PANEL = registerBlock("blooming_paper_panel",
           new PaneBlock(FabricBlockSettings.copy(NSWoods.SUGI.getPlanks())),
           
           BLOOMING_PAPER_BLOCK,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block BLOOMING_PAPER_DOOR = registerTransparentBlock("blooming_paper_door",
           new DoorBlock(FabricBlockSettings.copy(NSWoods.SUGI.getDoor()), PAPER_BLOCK_SET),
           BLOOMING_PAPER_PANEL,
           ItemGroups.BUILDING_BLOCKS
   );
   public static final Block BLOOMING_PAPER_TRAPDOOR = registerTransparentBlock("blooming_paper_trapdoor", new TrapdoorBlock(
           FabricBlockSettings.create().burnable().instrument(Instrument.BASS).strength(3.0f).sounds(BlockSoundGroup.WOOD).allowsSpawning(NSRegistryHelper::never).nonOpaque(),
           PAPER_BLOCK_SET
   ),  BLOOMING_PAPER_DOOR, ItemGroups.BUILDING_BLOCKS);


   public static final Block PAPER_SIGN = registerBlockWithoutTab("paper_sign", new SignBlock(AbstractBlock.Settings.copy(NSWoods.SUGI.getSign()), PAPER_WOOD_TYPE));
   public static final Block PAPER_WALL_SIGN = registerBlockWithoutTab("paper_wall_sign", new WallSignBlock(AbstractBlock.Settings.copy(NSWoods.SUGI.getSign()).dropsLike(PAPER_SIGN), PAPER_WOOD_TYPE));
   public static final Block PAPER_HANGING_SIGN =  registerBlockWithoutTab("paper_hanging_sign", new HangingSignBlock(AbstractBlock.Settings.copy(NSWoods.SUGI.getHangingSign()), PAPER_WOOD_TYPE));
   public static final Block PAPER_WALL_HANGING_SIGN = registerBlockWithoutTab("paper_wall_hanging_sign", new WallHangingSignBlock(AbstractBlock.Settings.copy(NSWoods.SUGI.getHangingSign()).dropsLike(PAPER_HANGING_SIGN), PAPER_WOOD_TYPE));
   public static final Item PAPER_SIGN_ITEM = registerItem( "paper_sign", new SignItem(new FabricItemSettings().maxCount(16), PAPER_SIGN, PAPER_WALL_SIGN), Items.WARPED_SIGN, ItemGroups.FUNCTIONAL);
   public static final Item PAPER_HANGING_SIGN_ITEM = registerItem( "paper_hanging_sign", new HangingSignItem(PAPER_HANGING_SIGN, PAPER_WALL_HANGING_SIGN, new FabricItemSettings().maxCount(16)), PAPER_SIGN_ITEM, ItemGroups.FUNCTIONAL);

   public static void registerMiscBlocks() {
      NSColoredBlocks.registerColoredBlocks();
      NatureSpirit.LOGGER.debug("Registering ModBlocks for " + MOD_ID);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.COCONUT_THATCH, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.COCONUT_THATCH_SLAB, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.COCONUT_THATCH_STAIRS, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.COCONUT_THATCH_CARPET, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.XERIC_THATCH, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.XERIC_THATCH_SLAB, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.XERIC_THATCH_STAIRS, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.XERIC_THATCH_CARPET, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.EVERGREEN_THATCH, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.EVERGREEN_THATCH_SLAB, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.EVERGREEN_THATCH_STAIRS, 5, 20);
      FlammableBlockRegistry.getDefaultInstance().add(NSWoods.EVERGREEN_THATCH_CARPET, 5, 20);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.COCONUT_THATCH, 0.65F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.COCONUT_THATCH_CARPET, 0.1F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.COCONUT_THATCH_STAIRS, 0.5F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.COCONUT_THATCH_SLAB, 0.3F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.XERIC_THATCH, 0.65F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.XERIC_THATCH_CARPET, 0.1F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.XERIC_THATCH_STAIRS, 0.5F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.XERIC_THATCH_SLAB, 0.3F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.EVERGREEN_THATCH, 0.65F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.EVERGREEN_THATCH_CARPET, 0.1F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.EVERGREEN_THATCH_STAIRS, 0.5F);
      CompostingChanceRegistry.INSTANCE.add(NSWoods.EVERGREEN_THATCH_SLAB, 0.3F);
      CompostingChanceRegistry.INSTANCE.add(RED_MOSS_BLOCK, 0.65F);
      CompostingChanceRegistry.INSTANCE.add(RED_MOSS_CARPET, 0.3F);
      CompostingChanceRegistry.INSTANCE.add(LOTUS_FLOWER_ITEM, 0.65F);
      CompostingChanceRegistry.INSTANCE.add(AZOLLA_ITEM, 0.3F);
      CompostingChanceRegistry.INSTANCE.add(HELVOLA_FLOWER_ITEM, 0.3F);
      CompostingChanceRegistry.INSTANCE.add(HELVOLA_PAD_ITEM, 0.3F);
      CompostingChanceRegistry.INSTANCE.add(DESERT_TURNIP, 0.3F);
      CompostingChanceRegistry.INSTANCE.add(DESERT_TURNIP_BLOCK, 0.65F);
      CompostingChanceRegistry.INSTANCE.add(DESERT_TURNIP_ROOT_BLOCK, 0.5F);
      CompostingChanceRegistry.INSTANCE.add(WHOLE_PIZZA, 1.0F);
      CompostingChanceRegistry.INSTANCE.add(THREE_QUARTERS_PIZZA, .85F);
      CompostingChanceRegistry.INSTANCE.add(HALF_PIZZA, .5F);
      CompostingChanceRegistry.INSTANCE.add(QUARTER_PIZZA, .35F);
      CompostingChanceRegistry.INSTANCE.add(AUREATE_SUCCULENT_ITEM, .65F);
      CompostingChanceRegistry.INSTANCE.add(DROWSY_SUCCULENT_ITEM, .65F);
      CompostingChanceRegistry.INSTANCE.add(FOAMY_SUCCULENT_ITEM, .65F);
      CompostingChanceRegistry.INSTANCE.add(SAGE_SUCCULENT_ITEM, .65F);
      CompostingChanceRegistry.INSTANCE.add(IMPERIAL_SUCCULENT_ITEM, .65F);
      CompostingChanceRegistry.INSTANCE.add(ORNATE_SUCCULENT_ITEM, .65F);
      CompostingChanceRegistry.INSTANCE.add(REGAL_SUCCULENT_ITEM, .65F);
      StrippableBlockRegistry.register(ALLUAUDIA_BUNDLE, STRIPPED_ALLUAUDIA_BUNDLE);
   }
}
