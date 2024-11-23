package net.hibiscus.naturespirit.registration;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.hibiscus.naturespirit.blocks.CoconutBlock;
import net.hibiscus.naturespirit.blocks.SproutingCoconutBlock;
import net.hibiscus.naturespirit.entity.NSBoatEntity;
import net.hibiscus.naturespirit.items.CoconutHalfItem;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.hibiscus.naturespirit.world.tree.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;
import static net.hibiscus.naturespirit.registration.NSRegistryHelper.*;

public class NSWoods {
   public static final WoodSet REDWOOD = new WoodSet(
           new Identifier(MOD_ID, "redwood"),
           MapColor.TERRACOTTA_BROWN,
           MapColor.RED,
           Blocks.CHERRY_LEAVES,
           Blocks.CHERRY_LOG,
           Blocks.BAMBOO_HANGING_SIGN,
           Items.BAMBOO_CHEST_RAFT,
           Blocks.BAMBOO_BUTTON,
           Blocks.CHERRY_SAPLING,
           NSBoatEntity.HibiscusBoat.REDWOOD,
           new RedwoodSaplingGenerator(),
           WoodSet.WoodPreset.FROSTABLE,
           false
   );
   public static final WoodSet SUGI = new WoodSet(
           new Identifier(MOD_ID, "sugi"),
           MapColor.DEEPSLATE_GRAY,
           MapColor.DIRT_BROWN,
           REDWOOD.getLeaves(),
           REDWOOD.getLog(),
           REDWOOD.getHangingSign(),
           REDWOOD.getChestBoatItem(),
           REDWOOD.getButton(),
           REDWOOD.getSapling(),
           NSBoatEntity.HibiscusBoat.SUGI,
           new SugiSaplingGenerator(),
           WoodSet.WoodPreset.FANCY,
           true
   );

   public static final WoodSet WISTERIA = new WoodSet(
           new Identifier(MOD_ID, "wisteria"),
           MapColor.GRAY,
           MapColor.TERRACOTTA_WHITE,
           SUGI.getLeaves(),
           SUGI.getLog(),
           SUGI.getHangingSign(),
           SUGI.getChestBoatItem(),
           SUGI.getButton(),
           SUGI.getSapling(),
           NSBoatEntity.HibiscusBoat.WISTERIA,
           new WhiteWisteriaSaplingGenerator(),
           WoodSet.WoodPreset.WISTERIA,
           false
   );
   public static final WoodSet FIR = new WoodSet(
           new Identifier(MOD_ID, "fir"),
           MapColor.GRAY,
           MapColor.DIRT_BROWN,
           WISTERIA.getPurpleLeaves(),
           WISTERIA.getLog(),
           WISTERIA.getHangingSign(),
           WISTERIA.getChestBoatItem(),
           WISTERIA.getButton(),
           WISTERIA.getPurpleSapling(),
           NSBoatEntity.HibiscusBoat.FIR,
           new FirSaplingGenerator(),
           WoodSet.WoodPreset.FROSTABLE,
           false
   );
   public static final WoodSet WILLOW = new WoodSet(
           new Identifier(MOD_ID, "willow"),
           MapColor.TERRACOTTA_BLACK,
           MapColor.TERRACOTTA_BROWN,
           FIR.getLeaves(),
           FIR.getLog(),
           FIR.getHangingSign(),
           FIR.getChestBoatItem(),
           FIR.getButton(),
           FIR.getSapling(),
           NSBoatEntity.HibiscusBoat.WILLOW,
           new WillowSaplingGenerator(),
           WoodSet.WoodPreset.WILLOW,
           false
   );
   public static final WoodSet ASPEN = new WoodSet(
           new Identifier(MOD_ID, "aspen"),
           MapColor.WHITE_GRAY,
           MapColor.PALE_YELLOW,
           WILLOW.getLeaves(),
           WILLOW.getLog(),
           WILLOW.getHangingSign(),
           WILLOW.getChestBoatItem(),
           WILLOW.getButton(),
           WILLOW.getSapling(),
           NSBoatEntity.HibiscusBoat.ASPEN,
           new AspenSaplingGenerator(),
           WoodSet.WoodPreset.DEFAULT,
           false
   );
   public static final WoodSet MAPLE = new WoodSet(
           new Identifier(MOD_ID,"maple"),
           MapColor.SPRUCE_BROWN,
           MapColor.ORANGE,
           ASPEN.getLeaves(),
           ASPEN.getLog(),
           ASPEN.getHangingSign(),
           ASPEN.getChestBoatItem(),
           ASPEN.getButton(),
           ASPEN.getSapling(),
           NSBoatEntity.HibiscusBoat.MAPLE,
           new RedMapleSaplingGenerator(),
           WoodSet.WoodPreset.MAPLE,
           false
   );
   public static final WoodSet CYPRESS = new WoodSet(
           new Identifier(MOD_ID, "cypress"),
           MapColor.SPRUCE_BROWN,
           MapColor.OAK_TAN,
           MAPLE.getYellowLeaves(),
           MAPLE.getLog(),
           MAPLE.getHangingSign(),
           MAPLE.getChestBoatItem(),
           MAPLE.getButton(),
           MAPLE.getYellowSapling(),
           NSBoatEntity.HibiscusBoat.CYPRESS,
           new CypressSaplingGenerator(),
           WoodSet.WoodPreset.DEFAULT,
           false
   );
   public static final WoodSet OLIVE = new WoodSet(
           new Identifier(MOD_ID, "olive"),
           MapColor.PALE_YELLOW,
           MapColor.PALE_GREEN,
           CYPRESS.getLeaves(),
           CYPRESS.getLog(),
           CYPRESS.getHangingSign(),
           CYPRESS.getChestBoatItem(),
           CYPRESS.getButton(),
           CYPRESS.getSapling(),
           NSBoatEntity.HibiscusBoat.OLIVE,
           new OliveSaplingGenerator(),
           WoodSet.WoodPreset.DEFAULT,
           false
   );
   public static final WoodSet JOSHUA = new WoodSet(
           new Identifier(MOD_ID, "joshua"),
           MapColor.PALE_GREEN,
           MapColor.DEEPSLATE_GRAY,
           OLIVE.getLeaves(),
           OLIVE.getLog(),
           OLIVE.getHangingSign(),
           OLIVE.getChestBoatItem(),
           OLIVE.getButton(),
           OLIVE.getSapling(),
           NSBoatEntity.HibiscusBoat.JOSHUA,
           new JoshuaSaplingGenerator(),
           WoodSet.WoodPreset.JOSHUA,
           true
   );
   public static final WoodSet GHAF = new WoodSet(
           new Identifier(MOD_ID, "ghaf"),
           MapColor.LIGHT_GRAY,
           MapColor.BROWN,
           JOSHUA.getLeaves(),
           JOSHUA.getLog(),
           JOSHUA.getHangingSign(),
           JOSHUA.getChestBoatItem(),
           JOSHUA.getButton(),
           JOSHUA.getSapling(),
           NSBoatEntity.HibiscusBoat.GHAF,
           new GhafSaplingGenerator(),
           WoodSet.WoodPreset.SANDY,
           false
   );

   public static final Block XERIC_THATCH = registerBlock("xeric_thatch",
           new Block(FabricBlockSettings.create().mapColor(MapColor.YELLOW).strength(0.4F).sounds(BlockSoundGroup.GRASS)), GHAF.getChestBoatItem());
   public static final Block XERIC_THATCH_STAIRS = registerBlock(
           "xeric_thatch_stairs",
           new StairsBlock(XERIC_THATCH.getDefaultState(), FabricBlockSettings.copy(XERIC_THATCH))
   );
   public static final Block XERIC_THATCH_SLAB = registerBlock("xeric_thatch_slab", new SlabBlock(FabricBlockSettings.create().mapColor(MapColor.YELLOW).sounds(BlockSoundGroup.GRASS).strength(0.4f)));
   public static final Block XERIC_THATCH_CARPET = registerBlock("xeric_thatch_carpet",
           new CarpetBlock(FabricBlockSettings.create().mapColor(MapColor.YELLOW).strength(0F).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GRASS))
   );
   public static final WoodSet PALO_VERDE = new WoodSet(
           new Identifier(MOD_ID, "palo_verde"),
           MapColor.YELLOW,
           MapColor.LICHEN_GREEN,
           GHAF.getLeaves(),
           GHAF.getLog(),
           GHAF.getHangingSign(),
           GHAF.getChestBoatItem(),
           GHAF.getButton(),
           GHAF.getSapling(),
           NSBoatEntity.HibiscusBoat.PALO_VERDE,
           new PaloVerdeSaplingGenerator(),
           WoodSet.WoodPreset.SANDY,
           false
   );
   public static final WoodSet COCONUT = new WoodSet(
           new Identifier(MOD_ID, "coconut"),
           MapColor.DULL_PINK,
           MapColor.BROWN,
           PALO_VERDE.getLeaves(),
           PALO_VERDE.getLog(),
           PALO_VERDE.getHangingSign(),
           PALO_VERDE.getChestBoatItem(),
           PALO_VERDE.getButton(),
           PALO_VERDE.getSapling(),
           NSBoatEntity.HibiscusBoat.COCONUT,
           new CoconutSaplingGenerator(),
           WoodSet.WoodPreset.NO_SAPLING,
           true
   );

   public static final Block COCONUT_THATCH = registerBlock("coconut_thatch",
           new Block(FabricBlockSettings.create().mapColor(MapColor.LIGHT_GRAY).strength(0.4F).sounds(BlockSoundGroup.GRASS)), COCONUT.getChestBoatItem());
   public static final Block COCONUT_THATCH_STAIRS = registerBlock(
           "coconut_thatch_stairs",
           new StairsBlock(COCONUT_THATCH.getDefaultState(), FabricBlockSettings.copy(COCONUT_THATCH))
   );
   public static final Block COCONUT_THATCH_SLAB = registerBlock("coconut_thatch_slab", new SlabBlock(FabricBlockSettings.create().mapColor(MapColor.LIGHT_GRAY).sounds(BlockSoundGroup.GRASS).strength(0.4f)));
   public static final Block COCONUT_THATCH_CARPET = registerBlock("coconut_thatch_carpet",
           new CarpetBlock(FabricBlockSettings.create().mapColor(MapColor.LIGHT_GRAY).strength(0F).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GRASS))
   );
   public static final Block COCONUT_BLOCK = registerPlantBlock("coconut", new CoconutBlock(
           FabricBlockSettings.create().strength(1.0F).sounds(BlockSoundGroup.GRASS).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)
   ), Items.SWEET_BERRIES, 0.2F);
   public static final Block COCONUT_SPROUT = registerPlantBlock("coconut_sprout", new SproutingCoconutBlock(
           new CoconutSaplingGenerator(),
           FabricBlockSettings.create().strength(1.0F).sounds(BlockSoundGroup.GRASS).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)
   ), PALO_VERDE.getSapling(), 0.2F);
   public static final FoodComponent COCONUT_COMPONENT = (new FoodComponent.Builder()).hunger(6).saturationModifier(0.6F).build();

   public static final Item COCONUT_SHELL = registerPlantItem("coconut_shell",
           new Item(new FabricItemSettings()),
           
           Items.BOWL,
           ItemGroups.INGREDIENTS,
           0.1F
   );
   public static final Item COCONUT_HALF = registerPlantItem("coconut_half",
           new CoconutHalfItem(new FabricItemSettings().food(COCONUT_COMPONENT), COCONUT_SHELL),
           
           Items.BEETROOT,
           ItemGroups.FOOD_AND_DRINK,
           0.1F
   );
   public static final WoodSet CEDAR = new WoodSet(
           new Identifier(MOD_ID, "cedar"),
           MapColor.TERRACOTTA_MAGENTA,
           MapColor.GRAY,
           COCONUT.getLeaves(),
           COCONUT.getLog(),
           COCONUT.getHangingSign(),
           COCONUT.getChestBoatItem(),
           COCONUT.getButton(),
           COCONUT_SPROUT,
           NSBoatEntity.HibiscusBoat.CEDAR,
           new CedarSaplingGenerator(),
           WoodSet.WoodPreset.DEFAULT,
           false
   );

   public static final WoodSet LARCH = new WoodSet(
           new Identifier(MOD_ID, "larch"),
           MapColor.BLUE,
           MapColor.LIGHT_GRAY,
           CEDAR.getLeaves(),
           CEDAR.getLog(),
           CEDAR.getHangingSign(),
           CEDAR.getChestBoatItem(),
           CEDAR.getButton(),
           CEDAR.getSapling(),
           NSBoatEntity.HibiscusBoat.LARCH,
           new LarchSaplingGenerator(),
           WoodSet.WoodPreset.FROSTABLE,
           false
   );
   public static final Block EVERGREEN_THATCH = registerBlock("evergreen_thatch",
           new Block(FabricBlockSettings.create().mapColor(MapColor.LIGHT_GRAY).strength(0.4F).sounds(BlockSoundGroup.GRASS)), LARCH.getChestBoatItem());
   public static final Block EVERGREEN_THATCH_STAIRS = registerBlock(
           "evergreen_thatch_stairs",
           new StairsBlock(EVERGREEN_THATCH.getDefaultState(), FabricBlockSettings.copy(EVERGREEN_THATCH))
   );
   public static final Block EVERGREEN_THATCH_SLAB = registerBlock("evergreen_thatch_slab", new SlabBlock(FabricBlockSettings.create().mapColor(MapColor.LIGHT_GRAY).sounds(BlockSoundGroup.GRASS).strength(0.4f)));
   public static final Block EVERGREEN_THATCH_CARPET = registerBlock("evergreen_thatch_carpet",
           new CarpetBlock(FabricBlockSettings.create().mapColor(MapColor.LIGHT_GRAY).strength(0F).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GRASS))
   );

      public static final WoodSet MAHOGANY = new WoodSet(
              new Identifier(MOD_ID, "mahogany"),
              MapColor.BROWN,
              MapColor.LIGHT_GRAY,
              LARCH.getLeaves(),
              LARCH.getLog(),
              LARCH.getHangingSign(),
              LARCH.getChestBoatItem(),
              LARCH.getButton(),
              LARCH.getSapling(),
              NSBoatEntity.HibiscusBoat.MAHOGANY,
              new MahoganySaplingGenerator(),
              WoodSet.WoodPreset.DEFAULT,
              true
      );

   public static final WoodSet SAXAUL = new WoodSet(
           new Identifier(MOD_ID, "saxaul"),
           MapColor.LIGHT_GRAY,
           MapColor.LIGHT_GRAY,
           MAHOGANY.getLeaves(),
           MAHOGANY.getLog(),
           MAHOGANY.getHangingSign(),
           MAHOGANY.getChestBoatItem(),
           MAHOGANY.getButton(),
           MAHOGANY.getSapling(),
           NSBoatEntity.HibiscusBoat.SAXAUL,
           new SaxaulSaplingGenerator(),
           WoodSet.WoodPreset.SANDY,
           false
   );

//   public static final WoodSet BANYAN = new WoodSet(
//           new Identifier(MOD_ID, "banyan"),
//           MapColor.BROWN,
//           MapColor.LIGHT_GRAY,
//           LARCH.getLeaves(),
//           LARCH.getLog(),
//           LARCH.getHangingSign(),
//           LARCH.getChestBoatItem(),
//           LARCH.getButton(),
//           LARCH.getSapling(),
//           NSBoatEntity.HibiscusBoat.BANYAN,
//           new BanyanSaplingGenerator(),
//           WoodSet.WoodPreset.DEFAULT,
//           false
//   );

   public static void registerWoods() {
      NSMiscBlocks.registerMiscBlocks();
   }
}
