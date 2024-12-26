package net.hibiscus.naturespirit.registration;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.hibiscus.naturespirit.NatureSpirit;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;

import net.hibiscus.naturespirit.blocks.CoconutBlock;
import net.hibiscus.naturespirit.blocks.OliveBranchBlock;
import net.hibiscus.naturespirit.blocks.SproutingCoconutBlock;
import net.hibiscus.naturespirit.datagen.NSConfiguredFeatures;
import net.hibiscus.naturespirit.items.CoconutHalfItem;

import static net.hibiscus.naturespirit.registration.NSRegistryHelper.*;

import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet.WoodPreset;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class NSWoods {

  public static final WoodSet REDWOOD = new WoodSet(
      Identifier.of(MOD_ID, "redwood"),
      MapColor.TERRACOTTA_BROWN,
      MapColor.RED,
      Blocks.CHERRY_LEAVES,
      Blocks.CHERRY_LOG,
      Blocks.BAMBOO_HANGING_SIGN,
      Items.BAMBOO_CHEST_RAFT,
      Blocks.BAMBOO_BUTTON,
      Blocks.CHERRY_SAPLING,
      () -> NSBoatTypes.REDWOOD,
      WoodSet.WoodPreset.FROSTABLE,
      false,
      Optional.of(NSConfiguredFeatures.REDWOOD_TREE),
      Optional.of(NSConfiguredFeatures.LARGE_REDWOOD_TREE)
  );

  public static final WoodSet SUGI = new WoodSet(
      Identifier.of(MOD_ID, "sugi"),
      MapColor.DEEPSLATE_GRAY,
      MapColor.DIRT_BROWN,
      REDWOOD.getLeaves(),
      REDWOOD.getLog(),
      REDWOOD.getHangingSign(),
      REDWOOD.getChestBoatItem(),
      REDWOOD.getButton(),
      REDWOOD.getSapling(),
      () -> NSBoatTypes.SUGI,
      WoodSet.WoodPreset.FANCY,
      true,
      Optional.of(NSConfiguredFeatures.SUGI_TREE),
      Optional.empty()
  );

  public static final WoodSet WISTERIA = new WoodSet(
      Identifier.of(MOD_ID, "wisteria"),
      MapColor.GRAY,
      MapColor.TERRACOTTA_WHITE,
      SUGI.getLeaves(),
      SUGI.getLog(),
      SUGI.getHangingSign(),
      SUGI.getChestBoatItem(),
      SUGI.getButton(),
      SUGI.getSapling(),
      () -> NSBoatTypes.WISTERIA,
      WoodSet.WoodPreset.WISTERIA,
      true,
      NSConfiguredFeatures.WHITE_WISTERIA_TREE
  );

  public static final WoodSet FIR = new WoodSet(
      Identifier.of(MOD_ID, "fir"),
      MapColor.GRAY,
      MapColor.DIRT_BROWN,
      WISTERIA.getPurpleLeaves(),
      WISTERIA.getLog(),
      WISTERIA.getHangingSign(),
      WISTERIA.getChestBoatItem(),
      WISTERIA.getButton(),
      WISTERIA.getPurpleSapling(),
      () -> NSBoatTypes.FIR,
      WoodSet.WoodPreset.FROSTABLE,
      false,
      NSConfiguredFeatures.FIR_TREE
  );

  public static final WoodSet WILLOW = new WoodSet(
      Identifier.of(MOD_ID, "willow"),
      MapColor.TERRACOTTA_BLACK,
      MapColor.TERRACOTTA_BROWN,
      FIR.getLeaves(),
      FIR.getLog(),
      FIR.getHangingSign(),
      FIR.getChestBoatItem(),
      FIR.getButton(),
      FIR.getSapling(),
      () -> NSBoatTypes.WILLOW,
      WoodSet.WoodPreset.WILLOW,
      false,
      NSConfiguredFeatures.WILLOW_TREE
  );

  public static final WoodSet ASPEN = new WoodSet(
      Identifier.of(MOD_ID, "aspen"),
      MapColor.WHITE_GRAY,
      MapColor.PALE_YELLOW,
      WILLOW.getLeaves(),
      WILLOW.getLog(),
      WILLOW.getHangingSign(),
      WILLOW.getChestBoatItem(),
      WILLOW.getButton(),
      WILLOW.getSapling(),
      () -> NSBoatTypes.ASPEN,
      WoodSet.WoodPreset.ASPEN,
      false,
      NSConfiguredFeatures.ASPEN_TREE
  );
  public static final WoodSet MAPLE = new WoodSet(
      Identifier.of(MOD_ID, "maple"),
      MapColor.SPRUCE_BROWN,
      MapColor.ORANGE,
      ASPEN.getLeaves(),
      ASPEN.getLog(),
      ASPEN.getHangingSign(),
      ASPEN.getChestBoatItem(),
      ASPEN.getButton(),
      ASPEN.getSapling(),
      () -> NSBoatTypes.MAPLE,
      WoodSet.WoodPreset.MAPLE,
      false,
      NSConfiguredFeatures.RED_MAPLE_TREE
  );

  public static final WoodSet CYPRESS = new WoodSet(
      Identifier.of(MOD_ID, "cypress"),
      MapColor.SPRUCE_BROWN,
      MapColor.OAK_TAN,
      MAPLE.getYellowLeaves(),
      MAPLE.getLog(),
      MAPLE.getHangingSign(),
      MAPLE.getChestBoatItem(),
      MAPLE.getButton(),
      MAPLE.getYellowSapling(),
      () -> NSBoatTypes.CYPRESS,
      WoodSet.WoodPreset.DEFAULT,
      false,
      NSConfiguredFeatures.CYPRESS_TREE
  );

  public static final WoodSet OLIVE = new WoodSet(
      Identifier.of(MOD_ID, "olive"),
      MapColor.PALE_YELLOW,
      MapColor.PALE_GREEN,
      CYPRESS.getLeaves(),
      CYPRESS.getLog(),
      CYPRESS.getHangingSign(),
      CYPRESS.getChestBoatItem(),
      CYPRESS.getButton(),
      CYPRESS.getSapling(),
      () -> NSBoatTypes.OLIVE,
      WoodSet.WoodPreset.DEFAULT,
      false,
      Optional.empty(),
      Optional.of(NSConfiguredFeatures.OLIVE_TREE)
  );

  public static final Block OLIVE_BRANCH = registerPlantBlock("olive_branch", new OliveBranchBlock(AbstractBlock.Settings.create().breakInstantly().noCollision().ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)), OLIVE.getLog(), 0.5F);

  public static final WoodSet JOSHUA = new WoodSet(
      Identifier.of(MOD_ID, "joshua"),
      MapColor.PALE_GREEN,
      MapColor.DEEPSLATE_GRAY,
      OLIVE.getLeaves(),
      OLIVE.getLog(),
      OLIVE.getHangingSign(),
      OLIVE.getChestBoatItem(),
      OLIVE.getButton(),
      OLIVE.getSapling(),
      () -> NSBoatTypes.JOSHUA,
      WoodSet.WoodPreset.JOSHUA,
      true,
      NSConfiguredFeatures.JOSHUA_TREE
  );

  public static final WoodSet GHAF = new WoodSet(
      Identifier.of(MOD_ID, "ghaf"),
      MapColor.LIGHT_GRAY,
      MapColor.BROWN,
      JOSHUA.getLeaves(),
      JOSHUA.getLog(),
      JOSHUA.getHangingSign(),
      JOSHUA.getChestBoatItem(),
      JOSHUA.getButton(),
      JOSHUA.getSapling(),
      () -> NSBoatTypes.GHAF,
      WoodSet.WoodPreset.SANDY,
      false,
      NSConfiguredFeatures.GHAF_TREE
  );

  public static final Block XERIC_THATCH = registerBlock("xeric_thatch",
      new Block(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(0.4F).sounds(BlockSoundGroup.GRASS)), GHAF.getChestBoatItem());
  public static final Block XERIC_THATCH_STAIRS = registerBlock(
      "xeric_thatch_stairs",
      new StairsBlock(XERIC_THATCH.getDefaultState(), AbstractBlock.Settings.copy(XERIC_THATCH))
  );
  public static final Block XERIC_THATCH_SLAB = registerBlock("xeric_thatch_slab",
      new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).sounds(BlockSoundGroup.GRASS).strength(0.4f)));
  public static final Block XERIC_THATCH_CARPET = registerBlock("xeric_thatch_carpet",
      new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(0F).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GRASS))
  );

  public static final WoodSet PALO_VERDE = new WoodSet(
      Identifier.of(MOD_ID, "palo_verde"),
      MapColor.YELLOW,
      MapColor.LICHEN_GREEN,
      GHAF.getLeaves(),
      GHAF.getLog(),
      GHAF.getHangingSign(),
      GHAF.getChestBoatItem(),
      GHAF.getButton(),
      GHAF.getSapling(),
      () -> NSBoatTypes.PALO_VERDE,
      WoodSet.WoodPreset.SANDY,
      false,
      NSConfiguredFeatures.PALO_VERDE_TREE
  );

  public static final WoodSet COCONUT = new WoodSet(
      Identifier.of(MOD_ID, "coconut"),
      MapColor.DULL_PINK,
      MapColor.BROWN,
      PALO_VERDE.getLeaves(),
      PALO_VERDE.getLog(),
      PALO_VERDE.getHangingSign(),
      PALO_VERDE.getChestBoatItem(),
      PALO_VERDE.getButton(),
      PALO_VERDE.getSapling(),
      () -> NSBoatTypes.COCONUT,
      WoodSet.WoodPreset.NO_SAPLING,
      true,
      NSConfiguredFeatures.COCONUT_TREE
  );

  public static final Block COCONUT_THATCH = registerBlock("coconut_thatch",
      new Block(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).strength(0.4F).sounds(BlockSoundGroup.GRASS)), COCONUT.getChestBoatItem());
  public static final Block COCONUT_THATCH_STAIRS = registerBlock(
      "coconut_thatch_stairs",
      new StairsBlock(COCONUT_THATCH.getDefaultState(), AbstractBlock.Settings.copy(COCONUT_THATCH))
  );
  public static final Block COCONUT_THATCH_SLAB = registerBlock("coconut_thatch_slab",
      new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).sounds(BlockSoundGroup.GRASS).strength(0.4f)));
  public static final Block COCONUT_THATCH_CARPET = registerBlock("coconut_thatch_carpet",
      new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).strength(0F).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GRASS))
  );
  public static final Block COCONUT_BLOCK = registerPlantBlock("coconut", new CoconutBlock(
      AbstractBlock.Settings.create().strength(1.0F).sounds(BlockSoundGroup.GRASS).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)
  ), Items.SWEET_BERRIES, 0.2F);
  public static final Block COCONUT_SPROUT = registerPlantBlock("coconut_sprout", new SproutingCoconutBlock(
      new SaplingGenerator(NatureSpirit.MOD_ID + "_coconut", Optional.empty(), Optional.of(NSConfiguredFeatures.COCONUT_TREE), Optional.empty()),
      AbstractBlock.Settings.create().strength(1.0F).sounds(BlockSoundGroup.GRASS).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)
  ), PALO_VERDE.getSapling(), 0.2F);
  public static final FoodComponent COCONUT_COMPONENT = (new FoodComponent.Builder()).nutrition(6).saturationModifier(0.6F).build();

  public static final Item COCONUT_SHELL = registerPlantItem("coconut_shell",
      new Item(new Item.Settings()),

      Items.BOWL,
      ItemGroups.INGREDIENTS,
      0.1F
  );
  public static final Item COCONUT_HALF = registerPlantItem("coconut_half",
      new CoconutHalfItem(new Item.Settings().food(COCONUT_COMPONENT), COCONUT_SHELL),

      Items.BEETROOT,
      ItemGroups.FOOD_AND_DRINK,
      0.1F
  );

  public static final WoodSet CEDAR = new WoodSet(
      Identifier.of(MOD_ID, "cedar"),
      MapColor.TERRACOTTA_MAGENTA,
      MapColor.GRAY,
      COCONUT.getLeaves(),
      COCONUT.getLog(),
      COCONUT.getHangingSign(),
      COCONUT.getChestBoatItem(),
      COCONUT.getButton(),
      COCONUT_SPROUT,
      () -> NSBoatTypes.CEDAR,
      WoodSet.WoodPreset.DEFAULT,
      false,
      NSConfiguredFeatures.CEDAR_TREE
  );

  public static final WoodSet LARCH = new WoodSet(
      Identifier.of(MOD_ID, "larch"),
      MapColor.BLUE,
      MapColor.LIGHT_GRAY,
      CEDAR.getLeaves(),
      CEDAR.getLog(),
      CEDAR.getHangingSign(),
      CEDAR.getChestBoatItem(),
      CEDAR.getButton(),
      CEDAR.getSapling(),
      () -> NSBoatTypes.LARCH,
      WoodPreset.DEFAULT,
      false,
      NSConfiguredFeatures.LARCH_TREE
  );

  public static final Block EVERGREEN_THATCH = registerBlock("evergreen_thatch",
      new Block(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).strength(0.4F).sounds(BlockSoundGroup.GRASS)), LARCH.getChestBoatItem());
  public static final Block EVERGREEN_THATCH_STAIRS = registerBlock(
      "evergreen_thatch_stairs",
      new StairsBlock(EVERGREEN_THATCH.getDefaultState(), AbstractBlock.Settings.copy(EVERGREEN_THATCH))
  );
  public static final Block EVERGREEN_THATCH_SLAB = registerBlock("evergreen_thatch_slab",
      new SlabBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).sounds(BlockSoundGroup.GRASS).strength(0.4f)));
  public static final Block EVERGREEN_THATCH_CARPET = registerBlock("evergreen_thatch_carpet",
      new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).strength(0F).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GRASS))
  );

  public static final WoodSet MAHOGANY = new WoodSet(
      Identifier.of(MOD_ID, "mahogany"),
      MapColor.BROWN,
      MapColor.LIGHT_GRAY,
      LARCH.getLeaves(),
      LARCH.getLog(),
      LARCH.getHangingSign(),
      LARCH.getChestBoatItem(),
      LARCH.getButton(),
      LARCH.getSapling(),
      () -> NSBoatTypes.MAHOGANY, WoodSet.WoodPreset.DEFAULT,
      true,
      Optional.empty(),
      Optional.of(NSConfiguredFeatures.MAHOGANY_TREE)
  );

  public static final WoodSet SAXAUL = new WoodSet(
      Identifier.of(MOD_ID, "saxaul"),
      MapColor.LIGHT_GRAY,
      MapColor.LIGHT_GRAY,
      MAHOGANY.getLeaves(),
      MAHOGANY.getLog(),
      MAHOGANY.getHangingSign(),
      MAHOGANY.getChestBoatItem(),
      MAHOGANY.getButton(),
      MAHOGANY.getSapling(),
      () -> NSBoatTypes.SAXAUL,
      WoodSet.WoodPreset.SANDY,
      false,
      NSConfiguredFeatures.SAXAUL_TREE
  );

//   public static final WoodSet BANYAN = new WoodSet(
//           Identifier.of(MOD_ID, "banyan"),
//           MapColor.BROWN,
//           MapColor.LIGHT_GRAY,
//           LARCH.getLeaves(),
//           LARCH.getLog(),
//           LARCH.getHangingSign(),
//           LARCH.getChestBoatItem(),
//           LARCH.getButton(),
//           LARCH.getSapling(),
//           () -> NSBoatTypes.BANYAN,
//           new BanyanSaplingGenerator(),
//           WoodSet.WoodPreset.DEFAULT,
//           false
//   );

  private static final List<WoodSet> WOOD_SETS = ImmutableList.of(
      REDWOOD,
      SUGI,
      WISTERIA,
      FIR,
      WILLOW,
      ASPEN,
      MAPLE,
      CYPRESS,
      OLIVE,
      JOSHUA,
      GHAF,
      PALO_VERDE,
      COCONUT,
      CEDAR,
      LARCH,
      MAHOGANY,
      SAXAUL
  );

  public static List<WoodSet> getWoodSets() {
    return WOOD_SETS;
  }

  static {
    NSBoatTypes.init();
  }

  public static void registerWoods() {
    FlammableBlockRegistry.getDefaultInstance().add(OLIVE_BRANCH, 6, 20);
    NSMiscBlocks.registerMiscBlocks();
  }
}
