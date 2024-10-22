package net.hibiscus.naturespirit.datagen;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.OptionalInt;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.blocks.DesertTurnipStemBlock;
import net.hibiscus.naturespirit.blocks.DownwardVineBlock;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSWoods;
import static net.hibiscus.naturespirit.registration.NSWorldGen.*;
import net.hibiscus.naturespirit.world.feature.NSSimpleBlockStateProvider;
import net.hibiscus.naturespirit.world.feature.TurnipRootFeatureConfig;
import net.hibiscus.naturespirit.world.foliage_placer.AspenFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.CoconutFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.CypressFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.FirFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.GroundedBushFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.LarchFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.MapleFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.RedwoodFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.SugiFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.WisteriaFoliagePlacer;
import net.hibiscus.naturespirit.world.tree_decorator.CoconutTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.MapleGroundTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.PolyporeTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.RedwoodBranchTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.SnowTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.WisteriaVinesTreeDecorator;
import net.hibiscus.naturespirit.world.trunk.CoconutTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.GhafTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.MahoganyTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.MapleTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.MegaSugiTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.OliveTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.PaloVerdeTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.SaxaulTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.SugiTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.WisteriaTrunkPlacer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.BlockPileFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DeltaFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomFeatureConfig;
import net.minecraft.world.gen.feature.RandomFeatureEntry;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.TreePlacedFeatures;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.NoiseBlockStateProvider;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class NSConfiguredFeatures {

	public static final RegistryKey<ConfiguredFeature<?, ?>> RED_MOSS_PATCH_BONEMEAL = registerKey("red_moss_patch_bonemeal");
	public static final RegistryKey<ConfiguredFeature<?, ?>> WISTERIA_DELTA = registerKey("water_delta");
	public static final RegistryKey<ConfiguredFeature<?, ?>> SWAMP_DELTA = registerKey("swamp_delta");
	public static final RegistryKey<ConfiguredFeature<?, ?>> MARSH_DELTA = registerKey("marsh_delta");
	public static final RegistryKey<ConfiguredFeature<?, ?>> RIVER_DELTA = registerKey("river_delta");

	public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_REDWOOD_TREE = registerKey("large_redwood_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> REDWOOD_TREE = registerKey("redwood_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_REDWOOD_TREE_SPAWN = registerKey("large_redwood_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> REDWOOD_TREE_SPAWN = registerKey("redwood_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_FROSTY_REDWOOD_TREE = registerKey("large_frosty_redwood_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FROSTY_REDWOOD_TREE = registerKey("frosty_redwood_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_FROSTY_REDWOOD_TREE_SPAWN = registerKey("large_frosty_redwood_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FROSTY_REDWOOD_TREE_SPAWN = registerKey("frosty_redwood_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> WILLOW_TREE = registerKey("willow_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> WILLOW_TREE_SPAWN = registerKey("willow_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> WHITE_WISTERIA_TREE = registerKey("white_wisteria_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> BLUE_WISTERIA_TREE = registerKey("blue_wisteria_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> PURPLE_WISTERIA_TREE = registerKey("purple_wisteria_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> PINK_WISTERIA_TREE = registerKey("pink_wisteria_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> ASPEN_TREE = registerKey("aspen_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> ASPEN_TREE_BEES = registerKey("aspen_tree_bees");
	public static final RegistryKey<ConfiguredFeature<?, ?>> ASPEN_TREE_SPAWN = registerKey("aspen_tree_spawn");
  public static final RegistryKey<ConfiguredFeature<?, ?>> YELLOW_ASPEN_TREE = registerKey("yellow_aspen_tree");
  public static final RegistryKey<ConfiguredFeature<?, ?>> YELLOW_ASPEN_TREE_BEES = registerKey("yellow_aspen_tree_bees");
  public static final RegistryKey<ConfiguredFeature<?, ?>> YELLOW_ASPEN_TREE_SPAWN = registerKey("yellow_aspen_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> RED_MAPLE_TREE = registerKey("red_maple_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_TREE = registerKey("orange_maple_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_TREE = registerKey("yellow_maple_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_SPAWN = registerKey("maple_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FIR_TREE = registerKey("fir_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FIR_TREE_SPAWN = registerKey("fir_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> LARCH_TREE = registerKey("larch_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> LARCH_TREE_SPAWN = registerKey("larch_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> WISTERIA_SPAWN = registerKey("wisteria_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> SUGI_TREE = registerKey("sugi_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_SUGI_TREE = registerKey("large_sugi_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> SUGI_SPAWN = registerKey("sugi_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> LARGE_SUGI_SPAWN = registerKey("large_sugi_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> CYPRESS_TREE = registerKey("cypress_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> CYPRESS_TREE_SPAWN = registerKey("cypress_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> OLIVE_TREE = registerKey("olive_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> OLIVE_TREE_SPAWN = registerKey("olive_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> GHAF_TREE = registerKey("ghaf_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> GHAF_TREE_SPAWN = registerKey("ghaf_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> PALO_VERDE_TREE = registerKey("palo_verde_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> PALO_VERDE_TREE_SPAWN = registerKey("palo_verde_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> MAHOGANY_TREE = registerKey("mahogany_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> MAHOGANY_TREE_SPAWN = registerKey("mahogany_tree_spawn");
	//   public static final RegistryKey <ConfiguredFeature <?, ?>> BANYAN_TREE = registerKey("banyan_tree");
//   public static final RegistryKey <ConfiguredFeature <?, ?>> BANYAN_TREE_SPAWN = registerKey("banyan_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> SAXAUL_TREE = registerKey("saxaul_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> SAXAUL_TREE_SPAWN = registerKey("saxaul_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> JOSHUA_TREE = registerKey("joshua_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> JOSHUA_TREE_SPAWN = registerKey("joshua_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> ALLUAUDIA = registerKey("alluaudia");
	public static final RegistryKey<ConfiguredFeature<?, ?>> ALLUAUDIA_SPAWN = registerKey("alluaudia_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> COCONUT_TREE = registerKey("coconut_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> COCONUT_TREE_SPAWN = registerKey("coconut_tree_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> CEDAR_TREE = registerKey("cedar_tree");
	public static final RegistryKey<ConfiguredFeature<?, ?>> CEDAR_TREE_SPAWN = registerKey("cedar_tree_spawn");

	public static final RegistryKey<ConfiguredFeature<?, ?>> OAK_BUSH = registerKey("oak_bush");
	public static final RegistryKey<ConfiguredFeature<?, ?>> SPRUCE_BUSH = registerKey("spruce_bush");
	public static final RegistryKey<ConfiguredFeature<?, ?>> OAK_BUSH_SPAWN = registerKey("oak_bush_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> SPRUCE_BUSH_SPAWN = registerKey("spruce_bush_spawn");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FANCY_OAK_TREE_SPAWN = registerKey("custom_fancy_oak_tree_spawn");


	public static final RegistryKey<ConfiguredFeature<?, ?>> PUMPKIN_PATCH_FEATURE = registerKey("pumpkin_patch_feature");
	public static final RegistryKey<ConfiguredFeature<?, ?>> PUMPKIN_FEATURE = registerKey("pumpkin_feature");

	public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_WISTERIA_FOREST = registerKey("flower_wisteria_forest");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_SUGI_FOREST = registerKey("flower_sugi_forest");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_ERODED_RIVER = registerKey("flower_eroded_river");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_GOLDEN_WILDS = registerKey("flower_golden_wilds");
	public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_SHIITAKE_MUSHROOM = registerKey("huge_shiitake_mushroom");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_CYPRESS_FIELDS = registerKey("flower_cypress_fields");
	public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_GRASS = registerKey("patch_scorched_grass");
	public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TALL_SCORCHED_GRASS = registerKey("patch_tall_scorched_grass");
	public static final RegistryKey<ConfiguredFeature<?, ?>> FLOWER_STRATIFIED_DESERT = registerKey("flower_stratified_desert");
	public static final RegistryKey<ConfiguredFeature<?, ?>> CATTAILS = registerKey("cattails");
	public static final RegistryKey<ConfiguredFeature<?, ?>> LOTUS_PLANT = registerKey("lotus_plant");
	public static final RegistryKey<ConfiguredFeature<?, ?>> ROOTED_DESERT_TURNIP = registerKey("rooted_desert_turnip");
	public static final RegistryKey<ConfiguredFeature<?, ?>> GRAY_POLYPORE = registerKey("gray_polypore");


	public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
		var placedFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
		var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
		RegistryEntryLookup<Block> holderGetter = context.getRegistryLookup(RegistryKeys.BLOCK);

		register(context, REDWOOD_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.REDWOOD.getLog()),
			new StraightTrunkPlacer(20, 1, 10),
			BlockStateProvider.of(NSWoods.REDWOOD.getLeaves()),
			new RedwoodFoliagePlacer(UniformIntProvider.create(1, 3), UniformIntProvider.create(1, 2), UniformIntProvider.create(22, 32)),
			new TwoLayersFeatureSize(2, 0, 2)
		).ignoreVines().decorators(ImmutableList.of(new RedwoodBranchTreeDecorator(.1f, BlockStateProvider.of(NSWoods.REDWOOD.getLeaves())), new PolyporeTreeDecorator(.3f, .2f, .05f, BlockStateProvider.of(
			NSMiscBlocks.GRAY_POLYPORE_BLOCK), BlockStateProvider.of(NSMiscBlocks.GRAY_POLYPORE)))).build());

		register(
			context,
			REDWOOD_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.REDWOOD_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.REDWOOD_CHECKED)
			)
		);

		register(context, LARGE_REDWOOD_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.REDWOOD.getLog()),
			new GiantTrunkPlacer(25, 1, 6),
			BlockStateProvider.of(NSWoods.REDWOOD.getLeaves()),
			new RedwoodFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(27, 31)),
			new TwoLayersFeatureSize(2, 0, 2)
		).decorators(ImmutableList.of(new AlterGroundTreeDecorator(BlockStateProvider.of(Blocks.PODZOL)), new RedwoodBranchTreeDecorator(.05f, BlockStateProvider.of(NSWoods.REDWOOD.getLeaves())), new PolyporeTreeDecorator(.2f, .1f, .05f, BlockStateProvider.of(
			NSMiscBlocks.GRAY_POLYPORE_BLOCK), BlockStateProvider.of(NSMiscBlocks.GRAY_POLYPORE)))).build());

		register(
			context,
			LARGE_REDWOOD_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_REDWOOD_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_REDWOOD_CHECKED)
			)
		);

		register(context, FROSTY_REDWOOD_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.REDWOOD.getLog()),
			new StraightTrunkPlacer(20, 1, 10),
			BlockStateProvider.of(NSWoods.REDWOOD.getFrostyLeaves()),
			new RedwoodFoliagePlacer(UniformIntProvider.create(1, 3), UniformIntProvider.create(1, 2), UniformIntProvider.create(22, 32)),
			new TwoLayersFeatureSize(2, 0, 2)
		).ignoreVines().decorators(ImmutableList.of(new RedwoodBranchTreeDecorator(.1f, BlockStateProvider.of(NSWoods.REDWOOD.getFrostyLeaves())), new SnowTreeDecorator())).build());

		register(
			context,
			FROSTY_REDWOOD_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.FROSTY_REDWOOD_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.FROSTY_REDWOOD_CHECKED)
			)
		);

		register(context, LARGE_FROSTY_REDWOOD_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.REDWOOD.getLog()),
			new GiantTrunkPlacer(25, 1, 6),
			BlockStateProvider.of(NSWoods.REDWOOD.getFrostyLeaves()),
			new RedwoodFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(27, 31)),
			new TwoLayersFeatureSize(2, 0, 2)
		).decorators(ImmutableList.of(new AlterGroundTreeDecorator(BlockStateProvider.of(Blocks.PODZOL)), new RedwoodBranchTreeDecorator(.05f, BlockStateProvider.of(NSWoods.REDWOOD.getFrostyLeaves())), new SnowTreeDecorator())).build());

		register(
			context,
			LARGE_FROSTY_REDWOOD_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_FROSTY_REDWOOD_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_FROSTY_REDWOOD_CHECKED)
			)
		);

		register(context, ASPEN_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.ASPEN.getLog()),
			new StraightTrunkPlacer(14, 2, 5),
			BlockStateProvider.of(NSWoods.ASPEN.getLeaves()),
			new AspenFoliagePlacer(UniformIntProvider.create(2, 2), UniformIntProvider.create(2, 3), UniformIntProvider.create(4, 18)),
			new TwoLayersFeatureSize(1, 0, 1)
		).ignoreVines().build());
		register(context, ASPEN_TREE_BEES, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.ASPEN.getLog()),
			new StraightTrunkPlacer(14, 2, 5),
			BlockStateProvider.of(NSWoods.ASPEN.getLeaves()),
			new AspenFoliagePlacer(UniformIntProvider.create(2, 2), UniformIntProvider.create(2, 3), UniformIntProvider.create(4, 18)),
			new TwoLayersFeatureSize(1, 0, 1)
		).ignoreVines().decorators(List.of(new BeehiveTreeDecorator(1.0F))).build());

     register(
             context,
             ASPEN_TREE_SPAWN,
             Feature.RANDOM_SELECTOR,
             new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ASPEN_BEES_CHECKED), 0.001f)),
                     placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ASPEN_CHECKED)
             )
     );

     register(context, YELLOW_ASPEN_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.ASPEN.getLog()),
             new StraightTrunkPlacer(14, 2, 5),
             BlockStateProvider.of(NSWoods.ASPEN.getYellowLeaves()),
             new AspenFoliagePlacer(UniformIntProvider.create(2, 2), UniformIntProvider.create(2, 3), UniformIntProvider.create(4, 18)),
             new TwoLayersFeatureSize(1, 0, 1)
     ).ignoreVines().build());
     register(context, YELLOW_ASPEN_TREE_BEES, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.ASPEN.getLog()),
             new StraightTrunkPlacer(14, 2, 5),
             BlockStateProvider.of(NSWoods.ASPEN.getYellowLeaves()),
             new AspenFoliagePlacer(UniformIntProvider.create(2, 2), UniformIntProvider.create(2, 3), UniformIntProvider.create(4, 18)),
             new TwoLayersFeatureSize(1, 0, 1)
     ).ignoreVines().decorators(List.of(new BeehiveTreeDecorator(1.0F))).build());

     register(
             context,
             YELLOW_ASPEN_TREE_SPAWN,
             Feature.RANDOM_SELECTOR,
             new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.YELLOW_ASPEN_BEES_CHECKED), 0.001f)),
                     placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.YELLOW_ASPEN_CHECKED)
             )
     );


		register(context, RED_MAPLE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.MAPLE.getLog()),
			new MapleTrunkPlacer(9, 2, 0, new WeightedListIntProvider(DataPool
				.<IntProvider>builder()
				.add(ConstantIntProvider.create(5), 1)
				.add(ConstantIntProvider.create(2), 1)
				.add(ConstantIntProvider.create(3), 1)
				.add(ConstantIntProvider.create(4), 1)
				.build()), UniformIntProvider.create(1, 3), UniformIntProvider.create(-5, -4), UniformIntProvider.create(-4, -1)),
			BlockStateProvider.of(NSWoods.MAPLE.getRedLeaves()),
			new MapleFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(0), ConstantIntProvider.create(5), 0.26666667F, 0.53333334F),
			new TwoLayersFeatureSize(1, 0, 2)
		).ignoreVines().decorators(ImmutableList.of(new MapleGroundTreeDecorator(SimpleBlockStateProvider.of(Blocks.PODZOL), SimpleBlockStateProvider.of(Blocks.COARSE_DIRT)))).build());
		register(context, ORANGE_MAPLE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.MAPLE.getLog()),
			new MapleTrunkPlacer(
				9,
				2,
				0,
				new WeightedListIntProvider(DataPool.<IntProvider>builder().add(ConstantIntProvider.create(5), 1).add(
					ConstantIntProvider.create(2),
					1
				).add(ConstantIntProvider.create(3), 1).add(ConstantIntProvider.create(4), 1).build()),
				UniformIntProvider.create(1, 3),
				UniformIntProvider.create(-5, -4),
				UniformIntProvider.create(-4, -1)
			),
			BlockStateProvider.of(NSWoods.MAPLE.getOrangeLeaves()),
			new MapleFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(0), ConstantIntProvider.create(5), 0.26666667F, 0.53333334F),
			new TwoLayersFeatureSize(1, 0, 2)
		).ignoreVines().decorators(ImmutableList.of(new MapleGroundTreeDecorator(SimpleBlockStateProvider.of(Blocks.PODZOL), SimpleBlockStateProvider.of(Blocks.COARSE_DIRT)))).build());
		register(context, YELLOW_MAPLE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.MAPLE.getLog()),
			new MapleTrunkPlacer(
				9,
				2,
				0,
				new WeightedListIntProvider(DataPool.<IntProvider>builder().add(ConstantIntProvider.create(5), 1).add(
					ConstantIntProvider.create(2),
					1
				).add(ConstantIntProvider.create(3), 1).add(ConstantIntProvider.create(4), 1).build()),
				UniformIntProvider.create(1, 3),
				UniformIntProvider.create(-5, -4),
				UniformIntProvider.create(-4, -1)
			),
			BlockStateProvider.of(NSWoods.MAPLE.getYellowLeaves()),
			new MapleFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(0), ConstantIntProvider.create(5), 0.26666667F, 0.53333334F),
			new TwoLayersFeatureSize(1, 0, 2)
		).ignoreVines().decorators(ImmutableList.of(new MapleGroundTreeDecorator(SimpleBlockStateProvider.of(Blocks.PODZOL), SimpleBlockStateProvider.of(Blocks.COARSE_DIRT)))).build());

		register(context,
			MAPLE_SPAWN,
			Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfig(RegistryEntryList.of(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.RED_MAPLE_CHECKED),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ORANGE_MAPLE_CHECKED),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.YELLOW_MAPLE_CHECKED),
				placedFeatureRegistryEntryLookup.getOrThrow(TreePlacedFeatures.FANCY_OAK_CHECKED)
			))
		);

		register(context, CYPRESS_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.CYPRESS.getLog()),
			new StraightTrunkPlacer(11, 1, 2),
			BlockStateProvider.of(NSWoods.CYPRESS.getLeaves()),
			new CypressFoliagePlacer(UniformIntProvider.create(2, 2), UniformIntProvider.create(3, 3), UniformIntProvider.create(11, 13)),
			new TwoLayersFeatureSize(1, 0, 1)
		).ignoreVines().build());


		register(context, FIR_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.FIR.getLog()),
			new StraightTrunkPlacer(10, 1, 2),
			BlockStateProvider.of(NSWoods.FIR.getLeaves()),
			new FirFoliagePlacer(UniformIntProvider.create(2, 2), UniformIntProvider.create(2, 3), UniformIntProvider.create(3, 12)),
			new TwoLayersFeatureSize(1, 0, 1)
		).ignoreVines().build());

		register(context,
			FIR_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.FIR_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.FIR_CHECKED)
			)
		);

		register(context, MAHOGANY_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.MAHOGANY.getLog()),
			new MahoganyTrunkPlacer(18, 0, 5),
			BlockStateProvider.of(NSWoods.MAHOGANY.getLeaves()),
			new AcaciaFoliagePlacer(UniformIntProvider.create(2, 2), UniformIntProvider.create(2, 2)),
			new TwoLayersFeatureSize(1, 0, 1)
		).ignoreVines().decorators(ImmutableList.of(new LeavesVineTreeDecorator(.4f))).build());

		register(context,
			MAHOGANY_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.MAHOGANY_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.MAHOGANY_CHECKED)
			)
		);

		register(context, LARCH_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.LARCH.getLog()),
			new StraightTrunkPlacer(12, 0, 4),
			BlockStateProvider.of(NSWoods.LARCH.getLeaves()),
			new LarchFoliagePlacer(UniformIntProvider.create(2, 2), UniformIntProvider.create(2, 2), UniformIntProvider.create(13, 15)),
			new TwoLayersFeatureSize(1, 0, 1)
		).ignoreVines().build());

		register(context,
			LARCH_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARCH_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARCH_CHECKED)
			)
		);

		register(context,
			WISTERIA_DELTA,
			HIBISCUS_DELTA_FEATURE,
			new DeltaFeatureConfig(Blocks.WATER.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), UniformIntProvider.create(5, 8), UniformIntProvider.create(0, 4))
		);
		register(context,
			SWAMP_DELTA,
			HIBISCUS_DELTA_FEATURE,
			new DeltaFeatureConfig(Blocks.WATER.getDefaultState(), Blocks.MUD.getDefaultState(), UniformIntProvider.create(2, 12), UniformIntProvider.create(1, 3))
		);
		register(context,
			MARSH_DELTA,
			HIBISCUS_DELTA_FEATURE,
			new DeltaFeatureConfig(Blocks.WATER.getDefaultState(), Blocks.MUD.getDefaultState(), UniformIntProvider.create(3, 14), UniformIntProvider.create(1, 3))
		);
		register(context,
			RIVER_DELTA,
			HIBISCUS_DELTA_FEATURE,
			new DeltaFeatureConfig(Blocks.WATER.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), UniformIntProvider.create(2, 6), UniformIntProvider.create(1, 3))
		);


		register(context, WILLOW_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.WILLOW.getLog()),
			new LargeOakTrunkPlacer(10, 3, 5),
			BlockStateProvider.of(NSWoods.WILLOW.getLeaves()),
			new WisteriaFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(3, 0, 2, OptionalInt.of(5))
		).decorators(List.of(new WisteriaVinesTreeDecorator(0.65F,
			new NSSimpleBlockStateProvider(NSWoods.WILLOW.getVinesPlant().getDefaultState()),
			new RandomizedIntBlockStateProvider(BlockStateProvider.of(NSWoods.WILLOW.getVines().getDefaultState()), DownwardVineBlock.AGE, UniformIntProvider.create(23, 25)),
			new NSSimpleBlockStateProvider(NSWoods.WILLOW.getLeaves().getDefaultState()),
			new NSSimpleBlockStateProvider(NSWoods.WILLOW.getLeaves().getDefaultState()),
			5
		))).ignoreVines().build());
		register(context,
			WILLOW_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.WILLOW_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.WILLOW_CHECKED)
			)
		);


		register(context, WHITE_WISTERIA_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.WISTERIA.getLog()),
			new WisteriaTrunkPlacer(7, 3, 4, UniformIntProvider.create(1, 6), 0.80F, UniformIntProvider.create(7, 10), holderGetter.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
			BlockStateProvider.of(NSWoods.WISTERIA.getLeaves()),
			new WisteriaFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(2, 0, 2)
		).decorators(List.of(new WisteriaVinesTreeDecorator(0.45F,
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getWhiteVinesPlant().getDefaultState()),
			new RandomizedIntBlockStateProvider(BlockStateProvider.of(NSWoods.WISTERIA.getWhiteVines().getDefaultState()), DownwardVineBlock.AGE, UniformIntProvider.create(22, 25)),
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPartWhiteLeaves().getDefaultState()),
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getWhiteLeaves().getDefaultState()),
			2
		))).ignoreVines().build());

		register(context, PINK_WISTERIA_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.WISTERIA.getLog()),
			new WisteriaTrunkPlacer(7,
				3,
				4,
				UniformIntProvider.create(1, 6),
				0.80F,
				UniformIntProvider.create(7, 10),
				Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
			),
			BlockStateProvider.of(NSWoods.WISTERIA.getLeaves()),
			new WisteriaFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(2, 0, 2)
		).decorators(List.of(new WisteriaVinesTreeDecorator(0.45F,
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPinkVinesPlant().getDefaultState()),
			new RandomizedIntBlockStateProvider(BlockStateProvider.of(NSWoods.WISTERIA.getPinkVines().getDefaultState()), DownwardVineBlock.AGE, UniformIntProvider.create(22, 25)),
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPartPinkLeaves().getDefaultState()),
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPinkLeaves().getDefaultState()),
			2
		))).ignoreVines().build());

		register(context, BLUE_WISTERIA_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.WISTERIA.getLog()),
			new WisteriaTrunkPlacer(7,
				3,
				4,
				UniformIntProvider.create(1, 6),
				0.80F,
				UniformIntProvider.create(7, 10),
				Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
			),
			BlockStateProvider.of(NSWoods.WISTERIA.getLeaves()),
			new WisteriaFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(2, 0, 2)
		).decorators(List.of(new WisteriaVinesTreeDecorator(0.45F,
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getBlueVinesPlant().getDefaultState()),
			new RandomizedIntBlockStateProvider(BlockStateProvider.of(NSWoods.WISTERIA.getBlueVines().getDefaultState()), DownwardVineBlock.AGE, UniformIntProvider.create(22, 25)),
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPartBlueLeaves().getDefaultState()),
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getBlueLeaves().getDefaultState()),
			2
		))).ignoreVines().build());

		register(context, PURPLE_WISTERIA_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.WISTERIA.getLog()),
			new WisteriaTrunkPlacer(7,
				3,
				4,
				UniformIntProvider.create(1, 6),
				0.80F,
				UniformIntProvider.create(7, 10),
				Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)
			),
			BlockStateProvider.of(NSWoods.WISTERIA.getLeaves()),
			new WisteriaFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(2, 0, 2)
		).decorators(List.of(new WisteriaVinesTreeDecorator(0.45F,
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPurpleVinesPlant().getDefaultState()),
			new RandomizedIntBlockStateProvider(BlockStateProvider.of(NSWoods.WISTERIA.getPurpleVines().getDefaultState()), DownwardVineBlock.AGE, UniformIntProvider.create(22, 25)),
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPartPurpleLeaves().getDefaultState()),
			new NSSimpleBlockStateProvider(NSWoods.WISTERIA.getPurpleLeaves().getDefaultState()),
			2
		))).ignoreVines().build());

		register(
			context,
			WISTERIA_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.WHITE_WISTERIA_CHECKED), 0.10f),
				new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.BLUE_WISTERIA_CHECKED), 0.325f),
				new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.PURPLE_WISTERIA_CHECKED), 0.325f),
				new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.PINK_WISTERIA_CHECKED), 0.25f)
			), placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.WHITE_WISTERIA_CHECKED))
		);

      register(context, SUGI_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.SUGI.getLog()),
              new SugiTrunkPlacer(12, 1, 12, UniformIntProvider.create(4, 6), .85F, UniformIntProvider.create(4, 5), Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
              BlockStateProvider.of(NSWoods.SUGI.getLeaves()),
              new SugiFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
              new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(5))
      ).ignoreVines().build());

		register(context,
			SUGI_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SUGI_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SUGI_CHECKED)
			)
		);

		register(context, LARGE_SUGI_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.SUGI.getLog()),
			new MegaSugiTrunkPlacer(18, 0, 5),
			BlockStateProvider.of(NSWoods.SUGI.getLeaves()),
			new SugiFoliagePlacer(UniformIntProvider.create(1, 1), UniformIntProvider.create(0, 0)),
			new TwoLayersFeatureSize(1, 0, 1)
		).ignoreVines().build());
		register(context,
			LARGE_SUGI_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_SUGI_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.LARGE_SUGI_CHECKED)
			)
		);

		register(context, OLIVE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.OLIVE.getLog()),
			new OliveTrunkPlacer(4, 1, 2, UniformIntProvider.create(4, 6), .85F, UniformIntProvider.create(4, 5), Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
			BlockStateProvider.of(NSWoods.OLIVE.getLeaves()),
			new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 60),
			new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(5))
		).ignoreVines().build());

		register(context,
			OLIVE_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.OLIVE_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.OLIVE_CHECKED)
			)
		);

		register(context, PALO_VERDE_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.PALO_VERDE.getLog()),
			new PaloVerdeTrunkPlacer(6, 1, 3, UniformIntProvider.create(4, 6), .95F, UniformIntProvider.create(3, 6), Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
			BlockStateProvider.of(NSWoods.PALO_VERDE.getLeaves()),
			new RandomSpreadFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 70),
			new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(6))
		).ignoreVines().build());

		register(context,
			PALO_VERDE_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.PALO_VERDE_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.PALO_VERDE_CHECKED)
			)
		);

//      register(context, BANYAN_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.GHAF.getLog()),
//              new BanyanTrunkPlacer(7, 0, 2, UniformIntProvider.create(4, 6), .95F, UniformIntProvider.create(3, 6), Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
//              BlockStateProvider.of(NSWoods.LARCH.getLeaves()),
//              new SugiFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
//              new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(4))
//      ).ignoreVines().build());

//      register(context,
//              BANYAN_TREE_SPAWN,
//              Feature.RANDOM_SELECTOR,
//              new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.BANYAN_CHECKED), 0.325f)),
//                      placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.BANYAN_CHECKED)
//              )
//      );

		register(context, SAXAUL_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.SAXAUL.getLog()),
			new SaxaulTrunkPlacer(3, 0, 2, UniformIntProvider.create(4, 6), .65F, UniformIntProvider.create(3, 6), Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
			BlockStateProvider.of(NSWoods.SAXAUL.getLeaves()),
			new RandomSpreadFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 20),
			new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(1))
		).ignoreVines().build());

		register(context,
			SAXAUL_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SAXAUL_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SAXAUL_CHECKED)
			)
		);

		register(context, CEDAR_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.CEDAR.getLog()),
			new PaloVerdeTrunkPlacer(6, 1, 3, UniformIntProvider.create(3, 5), .85F, UniformIntProvider.create(2, 5), Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
			BlockStateProvider.of(NSWoods.CEDAR.getLeaves()),
			new SugiFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(6))
		).ignoreVines().build());

		register(context,
			CEDAR_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.CEDAR_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.CEDAR_CHECKED)
			)
		);

		register(context, COCONUT_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.COCONUT.getLog()),
			new CoconutTrunkPlacer(6, 2, 3, UniformIntProvider.create(1, 3), .35F, Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
			BlockStateProvider.of(NSWoods.COCONUT.getLeaves()),
			new CoconutFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
			new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(6))
		).ignoreVines().decorators(List.of(new CoconutTreeDecorator(1.0F))).build());

		register(context,
			COCONUT_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.COCONUT_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.COCONUT_CHECKED)
			)
		);

		register(context, GHAF_TREE, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(NSWoods.GHAF.getLog()),
			new GhafTrunkPlacer(4, 1, 2, UniformIntProvider.create(4, 6), .95F, UniformIntProvider.create(4, 6), Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)),
			BlockStateProvider.of(NSWoods.GHAF.getLeaves()),
			new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 60),
			new TwoLayersFeatureSize(1, 0, 1, OptionalInt.of(3))
		).ignoreVines().build());

		register(context,
			GHAF_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.GHAF_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.GHAF_CHECKED)
			)
		);

		register(context, JOSHUA_TREE, JOSHUA_TREE_FEATURE, FeatureConfig.DEFAULT);
		register(context, GRAY_POLYPORE, POLYPORE_FEATURE, FeatureConfig.DEFAULT);
		register(context, ALLUAUDIA, ALLUAUDIA_FEATURE, FeatureConfig.DEFAULT);

		register(
			context,
			JOSHUA_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.JOSHUA_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.JOSHUA_CHECKED)
			)
		);

		register(
			context,
			ALLUAUDIA_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ALLUAUDIA_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.ALLUAUDIA_CHECKED)
			)
		);

		register(
			context,
			CYPRESS_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.CYPRESS_CHECKED), 0.325f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.CYPRESS_CHECKED)
			)
		);

		register(context,
			FANCY_OAK_TREE_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(TreePlacedFeatures.FANCY_OAK_BEES), 0.03F)),
				placedFeatureRegistryEntryLookup.getOrThrow(TreePlacedFeatures.FANCY_OAK_CHECKED)
			)
		);

		register(context, OAK_BUSH, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.OAK_LOG),
			new StraightTrunkPlacer(1, 0, 0),
			BlockStateProvider.of(Blocks.OAK_LEAVES),
			new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
			new TwoLayersFeatureSize(0, 0, 0)
		).build());
		register(context,
			OAK_BUSH_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.OAK_BUSH_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.OAK_BUSH_CHECKED)
			)
		);

		register(context, SPRUCE_BUSH, Feature.TREE, new TreeFeatureConfig.Builder(BlockStateProvider.of(Blocks.SPRUCE_LOG),
			new StraightTrunkPlacer(1, 0, 0),
			BlockStateProvider.of(Blocks.SPRUCE_LEAVES),
			new GroundedBushFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 75),
			new TwoLayersFeatureSize(1, 0, 1)
		).build());
		register(
			context,
			SPRUCE_BUSH_SPAWN,
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SPRUCE_BUSH_CHECKED), 0.5f)),
				placedFeatureRegistryEntryLookup.getOrThrow(NSPlacedFeatures.SPRUCE_BUSH_CHECKED)
			)
		);

		register(context,
			FLOWER_WISTERIA_FOREST,
			Feature.FLOWER,
			new RandomPatchFeatureConfig(96,
				6,
				2,
				PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2445L,
						new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D),
						0.030833334F,
						List.of(Blocks.ALLIUM.getDefaultState(),
							NSMiscBlocks.BLUEBELL.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.ANEMONE.getFlowerBlock().getDefaultState(),
							Blocks.OXEYE_DAISY.getDefaultState(),
							Blocks.PINK_TULIP.getDefaultState(),
							NSMiscBlocks.SNAPDRAGON.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.GARDENIA.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.LAVENDER.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.HIBISCUS.getFlowerBlock().getDefaultState(),
							Blocks.CORNFLOWER.getDefaultState()
						)
					))
				)
			)
		);

		register(context,
			FLOWER_SUGI_FOREST,
			Feature.FLOWER,
			new RandomPatchFeatureConfig(45,
				8,
				2,
				PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2445L,
						new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D),
						0.030833334F,
						List.of(Blocks.LILY_OF_THE_VALLEY.getDefaultState(),
							Blocks.LILY_OF_THE_VALLEY.getDefaultState(),
							NSMiscBlocks.GARDENIA.getFlowerBlock().getDefaultState(),
							Blocks.AZURE_BLUET.getDefaultState()
						)
					))
				)
			)
		);


		register(context,
			FLOWER_ERODED_RIVER,
			Feature.FLOWER,
			new RandomPatchFeatureConfig(60,
				6,
				2,
				PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2445L,
						new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D),
						0.030833334F,
						List.of(Blocks.LILY_OF_THE_VALLEY.getDefaultState(),
							NSMiscBlocks.BLEEDING_HEART.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.ANEMONE.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.HIBISCUS.getFlowerBlock().getDefaultState()
						)
					))
				)
			)
		);

		register(context,
			FLOWER_GOLDEN_WILDS,
			Feature.FLOWER,
			new RandomPatchFeatureConfig(36,
				6,
				2,
				PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2445L,
						new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D),
						0.030833334F,
						List.of(
							NSMiscBlocks.TIGER_LILY.getFlowerBlock().getDefaultState(),
							Blocks.ORANGE_TULIP.getDefaultState(),
							NSMiscBlocks.FLAXEN_FERN.getDefaultState(),
							Blocks.DANDELION.getDefaultState(),
							NSMiscBlocks.MARIGOLD.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.LARGE_FLAXEN_FERN.getDefaultState(),
							NSMiscBlocks.CARNATION.getFlowerBlock().getDefaultState()
						)
					))
				)
			)
		);

		register(context,
			HUGE_SHIITAKE_MUSHROOM,
			HUGE_SHIITAKE_MUSHROOM_FEATURE,
			new HugeMushroomFeatureConfig(BlockStateProvider.of(NSMiscBlocks.SHIITAKE_MUSHROOM_BLOCK.getDefaultState().with(MushroomBlock.UP, true).with(MushroomBlock.DOWN, false)),
				BlockStateProvider.of(Blocks.MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false)),
				2
			)
		);

		register(context,
			FLOWER_CYPRESS_FIELDS,
			Feature.FLOWER,
			new RandomPatchFeatureConfig(120,
				6,
				2,
				PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2445L,
						new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D),
						0.030833334F,
						List.of(Blocks.SHORT_GRASS.getDefaultState(),
							Blocks.TALL_GRASS.getDefaultState(),
							Blocks.POPPY.getDefaultState(),
							NSMiscBlocks.CARNATION.getFlowerBlock().getDefaultState(),
							Blocks.POPPY.getDefaultState(),
							Blocks.SHORT_GRASS.getDefaultState(),
							Blocks.POPPY.getDefaultState(),
							NSMiscBlocks.CARNATION.getFlowerBlock().getDefaultState(),
							Blocks.POPPY.getDefaultState(),
							Blocks.TALL_GRASS.getDefaultState(),
							Blocks.SHORT_GRASS.getDefaultState()
						)
					))
				)
			)
		);

		register(context,
			PATCH_SCORCHED_GRASS,
			Feature.RANDOM_PATCH,
			new RandomPatchFeatureConfig(32, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(NSMiscBlocks.SCORCHED_GRASS))))
		);
		register(context,
			PATCH_TALL_SCORCHED_GRASS,
			Feature.RANDOM_PATCH,
			new RandomPatchFeatureConfig(32, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(NSMiscBlocks.SCORCHED_GRASS))))
		);

		register(context,
			FLOWER_STRATIFIED_DESERT,
			Feature.FLOWER,
			new RandomPatchFeatureConfig(10,
				6,
				2,
				PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2445L,
						new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D),
						0.030833334F,
						List.of(
							NSMiscBlocks.SCORCHED_GRASS.getDefaultState(),
							NSMiscBlocks.YELLOW_WILDFLOWER.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.TALL_SCORCHED_GRASS.getDefaultState(),
							NSMiscBlocks.PURPLE_WILDFLOWER.getFlowerBlock().getDefaultState(),
							NSMiscBlocks.SCORCHED_GRASS.getDefaultState()
						)
					))
				)
			)
		);

		register(context,
			CATTAILS,
			Feature.RANDOM_PATCH,
			new RandomPatchFeatureConfig(90,
				6,
				2,
				PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
					new SimpleBlockFeatureConfig(BlockStateProvider.of(NSMiscBlocks.CATTAIL)),
					BlockPredicate.allOf(BlockPredicate.wouldSurvive(NSMiscBlocks.CATTAIL.getDefaultState(), BlockPos.ORIGIN), BlockPredicate.IS_AIR_OR_WATER, BlockPredicate.anyOf(
						BlockPredicate.matchingFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
						BlockPredicate.matchingFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
						BlockPredicate.matchingFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
						BlockPredicate.matchingFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER),
						BlockPredicate.matchingFluids(new BlockPos(0, 0, 0), Fluids.WATER, Fluids.FLOWING_WATER)
					))
				)
			)
		);


		register(context, LOTUS_PLANT, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(20, 6, 2, PlacedFeatures.createEntry(LOTUS_PLANT_FEATURE, FeatureConfig.DEFAULT)));

		register(context,
			ROOTED_DESERT_TURNIP,
			HIBISCUS_TURNIP_ROOT_FEATURE,
			new TurnipRootFeatureConfig(PlacedFeatures.createEntry(Feature.NO_BONEMEAL_FLOWER, new RandomPatchFeatureConfig(30, 3, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
				new SimpleBlockFeatureConfig(BlockStateProvider.of(NSMiscBlocks.DESERT_TURNIP_STEM.getDefaultState().with(DesertTurnipStemBlock.AGE, 7))),
				PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP
			)), new PlacementModifier[0]),
				3,
				3,
				NSTags.Blocks.TURNIP_ROOT_REPLACEABLE,
				BlockStateProvider.of(NSMiscBlocks.DESERT_TURNIP_ROOT_BLOCK),
				BlockStateProvider.of(NSMiscBlocks.DESERT_TURNIP_BLOCK),
				20,
				20,
				3,
				2,
				BlockStateProvider.of(Blocks.HANGING_ROOTS),
				20,
				35,
				2,
				BlockPredicate.bothOf(
					BlockPredicate.eitherOf(BlockPredicate.matchingBlocks(List.of(Blocks.AIR, Blocks.CAVE_AIR, Blocks.VOID_AIR)), BlockPredicate.matchingBlockTag(BlockTags.REPLACEABLE)),
					BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), NSTags.Blocks.TURNIP_STEM_GROWS_ON)
				)
			)
		);

		register(context,
			PUMPKIN_PATCH_FEATURE,
			HIBISCUS_PUMPKIN_PATCH_FEATURE,
			new OreFeatureConfig(new BlockMatchRuleTest(Blocks.AIR), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 43, 0)
		);
		register(context, PUMPKIN_FEATURE, HIBISCUS_LARGE_PUMPKIN_FEATURE, new BlockPileFeatureConfig(new NSSimpleBlockStateProvider(Blocks.PUMPKIN.getDefaultState())));

	}

	private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}

	public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(NatureSpirit.MOD_ID, name));
	}
}


