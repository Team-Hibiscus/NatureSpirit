package net.hibiscus.naturespirit.datagen;


import java.util.List;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

public class NSPlacedFeatures {


  public static final RegistryKey<PlacedFeature> LARGE_REDWOOD_CHECKED = registerKey("large_redwood_checked");
  public static final RegistryKey<PlacedFeature> REDWOOD_CHECKED = registerKey("redwood_checked");
  public static final RegistryKey<PlacedFeature> LARGE_FROSTY_REDWOOD_CHECKED = registerKey("large_frosty_redwood_checked");
  public static final RegistryKey<PlacedFeature> FROSTY_REDWOOD_CHECKED = registerKey("frosty_redwood_checked");
  public static final RegistryKey<PlacedFeature> ASPEN_CHECKED = registerKey("aspen_checked");
  public static final RegistryKey<PlacedFeature> YELLOW_ASPEN_CHECKED = registerKey("yellow_aspen_checked");
  public static final RegistryKey<PlacedFeature> ASPEN_BEES_CHECKED = registerKey("aspen_bees_checked");
  public static final RegistryKey<PlacedFeature> YELLOW_ASPEN_BEES_CHECKED = registerKey("yellow_aspen_bees_checked");
  public static final RegistryKey<PlacedFeature> RED_MAPLE_CHECKED = registerKey("red_maple_checked");
  public static final RegistryKey<PlacedFeature> ORANGE_MAPLE_CHECKED = registerKey("orange_maple_checked");
  public static final RegistryKey<PlacedFeature> YELLOW_MAPLE_CHECKED = registerKey("yellow_maple_checked");
  public static final RegistryKey<PlacedFeature> CYPRESS_CHECKED = registerKey("cypress_checked");
  public static final RegistryKey<PlacedFeature> FIR_CHECKED = registerKey("fir_checked");
  public static final RegistryKey<PlacedFeature> LARCH_CHECKED = registerKey("larch_checked");
  public static final RegistryKey<PlacedFeature> WILLOW_CHECKED = registerKey("willow_checked");
  public static final RegistryKey<PlacedFeature> WILLOW_PLACED = registerKey("willow_placed");
  public static final RegistryKey<PlacedFeature> WHITE_WISTERIA_CHECKED = registerKey("white_wisteria_checked");
  public static final RegistryKey<PlacedFeature> BLUE_WISTERIA_CHECKED = registerKey("blue_wisteria_checked");
  public static final RegistryKey<PlacedFeature> PINK_WISTERIA_CHECKED = registerKey("pink_wisteria_checked");
  public static final RegistryKey<PlacedFeature> PURPLE_WISTERIA_CHECKED = registerKey("purple_wisteria_checked");
  public static final RegistryKey<PlacedFeature> SUGI_CHECKED = registerKey("sugi_checked");
  public static final RegistryKey<PlacedFeature> LARGE_SUGI_CHECKED = registerKey("large_sugi_checked");
  public static final RegistryKey<PlacedFeature> OLIVE_CHECKED = registerKey("olive_checked");
  public static final RegistryKey<PlacedFeature> GHAF_CHECKED = registerKey("ghaf_checked");
  public static final RegistryKey<PlacedFeature> PALO_VERDE_CHECKED = registerKey("palo_verde_checked");
  public static final RegistryKey<PlacedFeature> MAHOGANY_CHECKED = registerKey("mahogany_checked");
  public static final RegistryKey<PlacedFeature> SAXAUL_CHECKED = registerKey("saxaul_checked");
  //   public static final RegistryKey <PlacedFeature> BANYAN_CHECKED = registerKey("banyan_checked");
  public static final RegistryKey<PlacedFeature> COCONUT_CHECKED = registerKey("coconut_checked");
  public static final RegistryKey<PlacedFeature> CEDAR_CHECKED = registerKey("cedar_checked");
  public static final RegistryKey<PlacedFeature> JOSHUA_CHECKED = registerKey("joshua_checked");
  public static final RegistryKey<PlacedFeature> ALLUAUDIA_CHECKED = registerKey("alluaudia_checked");

  public static final RegistryKey<PlacedFeature> OAK_BUSH_CHECKED = registerKey("oak_bush_checked");
  public static final RegistryKey<PlacedFeature> SPRUCE_BUSH_CHECKED = registerKey("spruce_bush_checked");

  public static final RegistryKey<PlacedFeature> FLOWER_WISTERIA_PLACED = registerKey("flower_wisteria_placed");
  public static final RegistryKey<PlacedFeature> FLOWER_SUGI_PLACED = registerKey("flower_sugi_placed");
  public static final RegistryKey<PlacedFeature> FLOWER_RIVER_PLACED = registerKey("flower_river_placed");
  public static final RegistryKey<PlacedFeature> FLOWER_GOLDEN_PLACED = registerKey("flower_golden_placed");
  public static final RegistryKey<PlacedFeature> FLOWER_GOLDEN2_PLACED = registerKey("flower_golden2_placed");
  public static final RegistryKey<PlacedFeature> FLOWER_CYPRESS_PLACED = registerKey("flower_cypress_placed");
  public static final RegistryKey<PlacedFeature> FLOWER_CARNATION_PLACED = registerKey("flower_carnation_placed");
  public static final RegistryKey<PlacedFeature> PATCH_SCORCHED_GRASS_PLACED = registerKey("patch_scorched_grass_placed");
  public static final RegistryKey<PlacedFeature> PATCH_TALL_SCORCHED_GRASS_PLACED = registerKey("patch_tall_scorched_grass_placed");
  public static final RegistryKey<PlacedFeature> FLOWER_STRATIFIED_DESERT_PLACED = registerKey("flower_stratified_desert_placed");

  public static final RegistryKey<PlacedFeature> WISTERIA_WATER = registerKey("wisteria_water_placed");
  public static final RegistryKey<PlacedFeature> LAVENDER_WATER = registerKey("lavender_water_placed");
  public static final RegistryKey<PlacedFeature> SWAMP_WATER = registerKey("swamp_water_placed");
  public static final RegistryKey<PlacedFeature> MARSH_WATER = registerKey("marsh_water_placed");
  public static final RegistryKey<PlacedFeature> RIVER_WATER = registerKey("river_water_placed");

  public static final RegistryKey<PlacedFeature> LARGE_REDWOOD_PLACED = registerKey("large_redwood_placed");
  public static final RegistryKey<PlacedFeature> REDWOOD_PLACED = registerKey("redwood_placed");
  public static final RegistryKey<PlacedFeature> LARGE_FROSTY_REDWOOD_PLACED = registerKey("large_frosty_redwood_placed");
  public static final RegistryKey<PlacedFeature> FROSTY_REDWOOD_PLACED = registerKey("frosty_redwood_placed");
  public static final RegistryKey<PlacedFeature> ASPEN_PLACED = registerKey("aspen_placed");
  public static final RegistryKey<PlacedFeature> YELLOW_ASPEN_PLACED = registerKey("yellow_aspen_placed");
  public static final RegistryKey<PlacedFeature> MAPLE_PLACED = registerKey("maple_placed");
  public static final RegistryKey<PlacedFeature> MAPLE2_PLACED = registerKey("maple2_placed");
  public static final RegistryKey<PlacedFeature> FEW_ASPEN_PLACED = registerKey("few_aspen_placed");
  public static final RegistryKey<PlacedFeature> DENSE_CYPRESS_PLACED = registerKey("dense_cypress_placed");
  public static final RegistryKey<PlacedFeature> CYPRESS_PLACED = registerKey("cypress_placed");
  public static final RegistryKey<PlacedFeature> FIR_PLACED = registerKey("fir_placed");
  public static final RegistryKey<PlacedFeature> DENSE_FIR_PLACED = registerKey("dense_fir_placed");
  public static final RegistryKey<PlacedFeature> SPRUCE_BUSH_PLACED = registerKey("spruce_bush_placed");
  public static final RegistryKey<PlacedFeature> WISTERIA_PLACED = registerKey("wisteria_placed");
  public static final RegistryKey<PlacedFeature> SUGI_PLACED = registerKey("sugi_placed");
  public static final RegistryKey<PlacedFeature> OLIVE_PLACED = registerKey("olive_placed");
  public static final RegistryKey<PlacedFeature> GHAF_PLACED = registerKey("ghaf_placed");
  public static final RegistryKey<PlacedFeature> PALO_VERDE_PLACED = registerKey("palo_verde_placed");
  public static final RegistryKey<PlacedFeature> SAXAUL_PLACED = registerKey("saxaul_placed");
  public static final RegistryKey<PlacedFeature> SPARSE_SAXAUL_PLACED = registerKey("sparse_saxaul_placed");
  public static final RegistryKey<PlacedFeature> JOSHUA_PLACED = registerKey("joshua_placed");
  public static final RegistryKey<PlacedFeature> ALLUAUDIA_PLACED = registerKey("alluaudia_placed");
  public static final RegistryKey<PlacedFeature> COCONUT_PLACED = registerKey("coconut_placed");
  public static final RegistryKey<PlacedFeature> CEDAR_PLACED = registerKey("cedar_placed");
  public static final RegistryKey<PlacedFeature> DENSE_CEDAR_PLACED = registerKey("dense_cedar_placed");
  public static final RegistryKey<PlacedFeature> MAHOGANY_PLACED = registerKey("mahogany_placed");
  public static final RegistryKey<PlacedFeature> SPARSE_OLIVE_PLACED = registerKey("sparse_olive_placed");

  public static final RegistryKey<PlacedFeature> OAK_BUSH_PLACED = registerKey("oak_bush_placed");
  public static final RegistryKey<PlacedFeature> CUSTOM_FANCY_OAK_TREE_PLACED = registerKey("custom_fancy_oak_tree_placed");
  public static final RegistryKey<PlacedFeature> CUSTOM_FANCY_OAK_TREE2_PLACED = registerKey("custom_fancy_oak_tree2_placed");
  public static final RegistryKey<PlacedFeature> CATTAILS = registerKey("cattails_placed");
  public static final RegistryKey<PlacedFeature> LOTUS_PLANT_PLACED = registerKey("lotus_plant_placed");
  public static final RegistryKey<PlacedFeature> ROOTED_DESERT_TURNIP = registerKey("rooted_desert_turnip_placed");

  private static final PlacementModifier TREE_THRESHOLD = SurfaceWaterDepthFilterPlacementModifier.of(0);

  public static void bootstrap(Registerable<PlacedFeature> context) {
    var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

    registerKey(context,
        LARGE_REDWOOD_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_REDWOOD_TREE),
        List.of(PlacedFeatures.wouldSurvive(NSWoods.REDWOOD.getSapling()))
    );
    registerKey(context,
        LARGE_FROSTY_REDWOOD_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_FROSTY_REDWOOD_TREE),
        List.of(PlacedFeatures.wouldSurvive(NSWoods.REDWOOD.getSapling()))
    );
    registerKey(context, REDWOOD_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.REDWOOD_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.REDWOOD.getSapling()));
    registerKey(context, FROSTY_REDWOOD_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FROSTY_REDWOOD_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.REDWOOD.getSapling()));
    registerKey(context, ASPEN_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ASPEN_TREE), PlacedFeatures.wouldSurvive(NSWoods.ASPEN.getSapling()));
    registerKey(context,
        ASPEN_BEES_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ASPEN_TREE_BEES),
        PlacedFeatures.wouldSurvive(NSWoods.ASPEN.getSapling())
    );
    registerKey(context, YELLOW_ASPEN_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.YELLOW_ASPEN_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.ASPEN.getSapling()));
    registerKey(context,
        YELLOW_ASPEN_BEES_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.YELLOW_ASPEN_TREE_BEES),
        PlacedFeatures.wouldSurvive(NSWoods.ASPEN.getSapling())
    );
    registerKey(context,
        RED_MAPLE_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.RED_MAPLE_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.MAPLE.getRedSapling())
    );
    registerKey(context,
        ORANGE_MAPLE_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ORANGE_MAPLE_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.MAPLE.getOrangeSapling())
    );
    registerKey(context,
        YELLOW_MAPLE_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.YELLOW_MAPLE_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.MAPLE.getYellowSapling())
    );
    registerKey(context, CYPRESS_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CYPRESS_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.CYPRESS.getSapling()));
    registerKey(context, JOSHUA_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.JOSHUA_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.JOSHUA.getSapling()));
    registerKey(context, ALLUAUDIA_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ALLUAUDIA),
        PlacedFeatures.wouldSurvive(NSWoods.JOSHUA.getSapling()));
    registerKey(context, GHAF_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.GHAF_TREE), PlacedFeatures.wouldSurvive(NSWoods.GHAF.getSapling()));
    registerKey(context, PALO_VERDE_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PALO_VERDE_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.PALO_VERDE.getSapling()));
    registerKey(context, CEDAR_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CEDAR_TREE), PlacedFeatures.wouldSurvive(NSWoods.CEDAR.getSapling()));
    registerKey(context, COCONUT_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.COCONUT_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.JOSHUA.getSapling()));
    registerKey(context, FIR_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FIR_TREE), PlacedFeatures.wouldSurvive(NSWoods.FIR.getSapling()));
    registerKey(context, LARCH_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARCH_TREE), PlacedFeatures.wouldSurvive(NSWoods.LARCH.getSapling()));
    registerKey(context, WILLOW_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WILLOW_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.WILLOW.getSapling()));
    registerKey(context, MAHOGANY_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MAHOGANY_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.MAHOGANY.getSapling()));
    registerKey(context, LARGE_SUGI_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_SUGI_SPAWN),
        PlacedFeatures.wouldSurvive(NSWoods.SUGI.getSapling()));
    registerKey(context, SAXAUL_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SAXAUL_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.SAXAUL.getSapling()));
    registerKey(context,
        WHITE_WISTERIA_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WHITE_WISTERIA_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.WISTERIA.getWhiteSapling())
    );
    registerKey(context,
        BLUE_WISTERIA_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.BLUE_WISTERIA_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.WISTERIA.getBlueSapling())
    );
    registerKey(context,
        PINK_WISTERIA_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PINK_WISTERIA_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.WISTERIA.getPinkSapling())
    );
    registerKey(context,
        PURPLE_WISTERIA_CHECKED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PURPLE_WISTERIA_TREE),
        PlacedFeatures.wouldSurvive(NSWoods.WISTERIA.getPurpleSapling())
    );
    registerKey(context, SUGI_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SUGI_TREE), PlacedFeatures.wouldSurvive(NSWoods.SUGI.getSapling()));
//      registerKey(context, BANYAN_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.BANYAN_TREE), PlacedFeatures.wouldSurvive(NSWoods.BANYAN.getSapling()));
    registerKey(context, OLIVE_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OLIVE_TREE), PlacedFeatures.wouldSurvive(NSWoods.OLIVE.getSapling()));

    registerKey(context, OAK_BUSH_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OAK_BUSH), BlockFilterPlacementModifier.of(BlockPredicate.anyOf(
            BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN),
            BlockPredicate.matchingBlockTag(BlockPos.ORIGIN.down(), NSTags.Blocks.KAOLIN),
            BlockPredicate.matchingBlockTag(BlockPos.ORIGIN.down(), BlockTags.BASE_STONE_OVERWORLD),
            BlockPredicate.matchingBlockTag(BlockPos.ORIGIN.east(), BlockTags.BASE_STONE_OVERWORLD),
            BlockPredicate.matchingBlockTag(BlockPos.ORIGIN.west(), BlockTags.BASE_STONE_OVERWORLD),
            BlockPredicate.matchingBlockTag(BlockPos.ORIGIN.south(), BlockTags.BASE_STONE_OVERWORLD),
            BlockPredicate.matchingBlockTag(BlockPos.ORIGIN.north(), BlockTags.BASE_STONE_OVERWORLD)
        )
    ));
    registerKey(context, SPRUCE_BUSH_CHECKED, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SPRUCE_BUSH),
        PlacedFeatures.wouldSurvive(Blocks.SPRUCE_SAPLING));

    registerKey(context,
        FLOWER_WISTERIA_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_WISTERIA_FOREST),
        CountPlacementModifier.of(3),
        RarityFilterPlacementModifier.of(2),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FLOWER_SUGI_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_SUGI_FOREST),
        CountPlacementModifier.of(4),
        RarityFilterPlacementModifier.of(8),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FLOWER_RIVER_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_ERODED_RIVER),
        CountPlacementModifier.of(5),
        RarityFilterPlacementModifier.of(2),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FLOWER_GOLDEN_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_GOLDEN_WILDS),
        CountPlacementModifier.of(3),
        RarityFilterPlacementModifier.of(5),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FLOWER_GOLDEN2_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_GOLDEN_WILDS),
        CountPlacementModifier.of(5),
        RarityFilterPlacementModifier.of(1),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FLOWER_CYPRESS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_CYPRESS_FIELDS),
        CountPlacementModifier.of(3),
        RarityFilterPlacementModifier.of(15),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FLOWER_CARNATION_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_CYPRESS_FIELDS),
        CountPlacementModifier.of(5),
        RarityFilterPlacementModifier.of(2),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        PATCH_SCORCHED_GRASS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PATCH_SCORCHED_GRASS),
        CountPlacementModifier.of(4),
        RarityFilterPlacementModifier.of(2),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        PATCH_TALL_SCORCHED_GRASS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PATCH_TALL_SCORCHED_GRASS),
        CountPlacementModifier.of(3),
        RarityFilterPlacementModifier.of(2),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );

    registerKey(context,
        FLOWER_STRATIFIED_DESERT_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FLOWER_STRATIFIED_DESERT),
        CountPlacementModifier.of(3),
        RarityFilterPlacementModifier.of(15),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );

    registerKey(context,
        WISTERIA_WATER,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WISTERIA_DELTA),
        CountMultilayerPlacementModifier.of(20),
        BiomePlacementModifier.of()
    );
    registerKey(context,
        LAVENDER_WATER,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WISTERIA_DELTA),
        CountMultilayerPlacementModifier.of(12),
        BiomePlacementModifier.of()
    );
    registerKey(context, SWAMP_WATER, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SWAMP_DELTA), CountMultilayerPlacementModifier.of(10),
        BiomePlacementModifier.of());
    registerKey(context, MARSH_WATER, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MARSH_DELTA), CountMultilayerPlacementModifier.of(20),
        BiomePlacementModifier.of());
    registerKey(context, RIVER_WATER, configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.RIVER_DELTA), CountMultilayerPlacementModifier.of(20),
        BiomePlacementModifier.of());

    registerKey(context,
        LARGE_REDWOOD_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_REDWOOD_TREE_SPAWN),
        CountPlacementModifier.of(3),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        REDWOOD_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.REDWOOD_TREE_SPAWN),
        CountPlacementModifier.of(7),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        LARGE_FROSTY_REDWOOD_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LARGE_FROSTY_REDWOOD_TREE_SPAWN),
        CountPlacementModifier.of(3),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FROSTY_REDWOOD_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FROSTY_REDWOOD_TREE_SPAWN),
        CountPlacementModifier.of(7),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        ASPEN_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ASPEN_TREE_SPAWN),
        NoiseThresholdCountPlacementModifier.of(.5D, 0, 5),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        YELLOW_ASPEN_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.YELLOW_ASPEN_TREE_SPAWN),
        NoiseThresholdCountPlacementModifier.of(.5D, 5, 0),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        DENSE_CYPRESS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CYPRESS_TREE_SPAWN),
        CountPlacementModifier.of(20),
        RarityFilterPlacementModifier.of(80),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        CEDAR_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CEDAR_TREE_SPAWN),
        NoiseBasedCountPlacementModifier.of(3, 60, .25F),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        DENSE_CEDAR_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CEDAR_TREE_SPAWN),
        CountPlacementModifier.of(30),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        SPARSE_OLIVE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OLIVE_TREE_SPAWN),
        CountPlacementModifier.of(3),
        RarityFilterPlacementModifier.of(50),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        CYPRESS_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CYPRESS_TREE_SPAWN),
        CountPlacementModifier.of(20),
        RarityFilterPlacementModifier.of(110),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FEW_ASPEN_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ASPEN_TREE_SPAWN),
        CountPlacementModifier.of(20),
        RarityFilterPlacementModifier.of(80),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        MAPLE2_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MAPLE_SPAWN),
        CountPlacementModifier.of(7),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        MAPLE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MAPLE_SPAWN),
        NoiseBasedCountPlacementModifier.of(3, 100, 0),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        FIR_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FIR_TREE_SPAWN),
        NoiseBasedCountPlacementModifier.of(3, 200, 0),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        DENSE_FIR_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FIR_TREE_SPAWN),
        CountPlacementModifier.of(6),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        WILLOW_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WILLOW_TREE_SPAWN),
        CountPlacementModifier.of(2),
        SquarePlacementModifier.of(),
        SurfaceWaterDepthFilterPlacementModifier.of(2),
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        SPRUCE_BUSH_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SPRUCE_BUSH_SPAWN),
        CountPlacementModifier.of(4),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        WISTERIA_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.WISTERIA_SPAWN),
        CountPlacementModifier.of(15),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        SUGI_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SUGI_SPAWN),
        CountPlacementModifier.of(5),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        MAHOGANY_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.MAHOGANY_TREE_SPAWN),
        CountPlacementModifier.of(2),
        SquarePlacementModifier.of(),
        SurfaceWaterDepthFilterPlacementModifier.of(7),
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        OLIVE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OLIVE_TREE_SPAWN),
        CountPlacementModifier.of(20),
        RarityFilterPlacementModifier.of(80),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        GHAF_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.GHAF_TREE_SPAWN),
        CountPlacementModifier.of(10),
        RarityFilterPlacementModifier.of(100),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        PALO_VERDE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.PALO_VERDE_TREE_SPAWN),
        CountPlacementModifier.of(11),
        RarityFilterPlacementModifier.of(85),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        SAXAUL_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SAXAUL_TREE_SPAWN),
        NoiseBasedCountPlacementModifier.of(6, 130, 0),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        SPARSE_SAXAUL_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.SAXAUL_TREE_SPAWN),
        CountPlacementModifier.of(2),
        RarityFilterPlacementModifier.of(20),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        JOSHUA_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.JOSHUA_TREE_SPAWN),
        CountPlacementModifier.of(20),
        RarityFilterPlacementModifier.of(80),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        ALLUAUDIA_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ALLUAUDIA_SPAWN),
        CountPlacementModifier.of(10),
        RarityFilterPlacementModifier.of(80),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        COCONUT_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.COCONUT_TREE_SPAWN),
        CountPlacementModifier.of(3),
        RarityFilterPlacementModifier.of(3),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );

    registerKey(context,
        OAK_BUSH_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.OAK_BUSH_SPAWN),
        CountPlacementModifier.of(1),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );

    registerKey(context,
        CUSTOM_FANCY_OAK_TREE_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FANCY_OAK_TREE_SPAWN),
        CountPlacementModifier.of(1),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        CUSTOM_FANCY_OAK_TREE2_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.FANCY_OAK_TREE_SPAWN),
        RarityFilterPlacementModifier.of(35),
        CountPlacementModifier.of(1),
        SquarePlacementModifier.of(),
        TREE_THRESHOLD,
        PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        CATTAILS,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.CATTAILS),
        RarityFilterPlacementModifier.of(1),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );
    registerKey(context,
        LOTUS_PLANT_PLACED,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.LOTUS_PLANT),
        RarityFilterPlacementModifier.of(15),
        SquarePlacementModifier.of(),
        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
        BiomePlacementModifier.of()
    );

    registerKey(context,
        ROOTED_DESERT_TURNIP,
        configuredFeatureRegistryEntryLookup.getOrThrow(NSConfiguredFeatures.ROOTED_DESERT_TURNIP),
        CountPlacementModifier.of(UniformIntProvider.create(1, 2)),
        SquarePlacementModifier.of(),
        RarityFilterPlacementModifier.of(30),
        HeightRangePlacementModifier.uniform(YOffset.fixed(30), YOffset.fixed(226)),
        EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.solid(), 12),
        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
        BiomePlacementModifier.of()
    );
  }

  public static RegistryKey<PlacedFeature> registerKey(String name) {
    return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(NatureSpirit.MOD_ID, name));
  }

  private static void registerKey(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
      List<PlacementModifier> modifiers) {
    context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
  }

  private static <FC extends FeatureConfig, F extends Feature<FC>> void registerKey(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
      RegistryEntry<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
    registerKey(context, key, configuration, List.of(modifiers));
  }

}
