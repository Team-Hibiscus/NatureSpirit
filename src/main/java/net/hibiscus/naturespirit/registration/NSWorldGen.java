package net.hibiscus.naturespirit.registration;

import com.mojang.serialization.MapCodec;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;

import net.hibiscus.naturespirit.world.carver.ReplaceableCaveCarver;
import net.hibiscus.naturespirit.world.carver.ReplaceableCaveCarverConfig;
import net.hibiscus.naturespirit.world.carver.ReplaceableRavineCarver;
import net.hibiscus.naturespirit.world.carver.ReplaceableRavineCarverConfig;
import net.hibiscus.naturespirit.world.feature.AlluaudiaFeature;
import net.hibiscus.naturespirit.world.feature.HugeBrownMushroomFeature;
import net.hibiscus.naturespirit.world.feature.HugeRedMushroomFeature;
import net.hibiscus.naturespirit.world.feature.HugeShiitakeMushroomFeature;
import net.hibiscus.naturespirit.world.feature.JoshuaTreeFeature;
import net.hibiscus.naturespirit.world.feature.LargePumpkinFeature;
import net.hibiscus.naturespirit.world.feature.LeveledRandomPatch;
import net.hibiscus.naturespirit.world.feature.LotusPlantFeature;
import net.hibiscus.naturespirit.world.feature.NSDeltaFeature;
import net.hibiscus.naturespirit.world.feature.PolyporeFeature;
import net.hibiscus.naturespirit.world.feature.PumpkinPatchFeature;
import net.hibiscus.naturespirit.world.feature.TurnipRootFeature;
import net.hibiscus.naturespirit.world.feature.TurnipRootFeatureConfig;
import net.hibiscus.naturespirit.world.foliage_placer.AspenFoliagePlacer;
import net.hibiscus.naturespirit.world.foliage_placer.BirchFoliagePlacer;
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
import net.hibiscus.naturespirit.world.tree_decorator.OliveBranchTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.PolyporeTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.RedwoodBranchTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.SnowTreeDecorator;
import net.hibiscus.naturespirit.world.tree_decorator.WisteriaVinesTreeDecorator;
import net.hibiscus.naturespirit.world.trunk.BanyanTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.BushTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.CoconutTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.GhafTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.MahoganyTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.MapleTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.MegaSugiTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.OliveTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.PaloVerdeTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.RedwoodTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.SaxaulTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.SugiTrunkPlacer;
import net.hibiscus.naturespirit.world.trunk.WisteriaTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.feature.BlockPileFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.DeltaFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class NSWorldGen {

  public static final TrunkPlacerType<WisteriaTrunkPlacer> WISTERIA_TRUNK_PLACER = registerTrunkPlacer("wisteria_trunk_placer", WisteriaTrunkPlacer.CODEC);
  public static final TrunkPlacerType<BushTrunkPlacer> BUSH_TRUNK_PLACER = registerTrunkPlacer("bush_trunk_placer", BushTrunkPlacer.CODEC);
  public static final TrunkPlacerType<SugiTrunkPlacer> SUGI_TRUNK_PLACER = registerTrunkPlacer("sugi_trunk_placer", SugiTrunkPlacer.CODEC);
  public static final TrunkPlacerType<OliveTrunkPlacer> OLIVE_TRUNK_PLACER = registerTrunkPlacer("olive_trunk_placer", OliveTrunkPlacer.CODEC);
  public static final TrunkPlacerType<PaloVerdeTrunkPlacer> PALO_VERDE_TRUNK_PLACER = registerTrunkPlacer("palo_verde_trunk_placer", PaloVerdeTrunkPlacer.CODEC);
  public static final TrunkPlacerType<BanyanTrunkPlacer> BANYAN_TRUNK_PLACER = registerTrunkPlacer("banyan_trunk_placer", BanyanTrunkPlacer.CODEC);
  public static final TrunkPlacerType<SaxaulTrunkPlacer> SAXAUL_TRUNK_PLACER = registerTrunkPlacer("saxaul_trunk_placer", SaxaulTrunkPlacer.CODEC);
  public static final TrunkPlacerType<GhafTrunkPlacer> GHAF_TRUNK_PLACER = registerTrunkPlacer("ghaf_trunk_placer", GhafTrunkPlacer.CODEC);
  public static final TrunkPlacerType<MapleTrunkPlacer> MAPLE_TRUNK_PLACER = registerTrunkPlacer("maple_trunk_placer", MapleTrunkPlacer.CODEC);
  public static final TrunkPlacerType<MahoganyTrunkPlacer> MAHOGANY_TRUNK_PLACER = registerTrunkPlacer("mahogany_trunk_placer", MahoganyTrunkPlacer.CODEC);
  public static final TrunkPlacerType<MegaSugiTrunkPlacer> MEGA_SUGI_TRUNK_PLACER = registerTrunkPlacer("mega_sugi_trunk_placer", MegaSugiTrunkPlacer.CODEC);
  public static final TrunkPlacerType<CoconutTrunkPlacer> COCONUT_TRUNK_PLACER = registerTrunkPlacer("coconut_trunk_placer", CoconutTrunkPlacer.CODEC);
  public static final TrunkPlacerType<RedwoodTrunkPlacer> REDWOOD_TRUNK_PLACER = registerTrunkPlacer("redwood_trunk_placer", RedwoodTrunkPlacer.CODEC);


  public static final TreeDecoratorType<WisteriaVinesTreeDecorator> WISTERIA_VINES_TREE_DECORATOR = registerTreeDecorator("wisteria_vines_tree_decorator",
      WisteriaVinesTreeDecorator.CODEC
  );
  public static final TreeDecoratorType<MapleGroundTreeDecorator> MAPLE_GROUND_TREE_DECORATOR = registerTreeDecorator("maple_ground_tree_decorator",
      MapleGroundTreeDecorator.CODEC);
  public static final TreeDecoratorType<CoconutTreeDecorator> COCONUT_TREE_DECORATOR = registerTreeDecorator("coconut_tree_decorator", CoconutTreeDecorator.CODEC);
  public static final TreeDecoratorType<RedwoodBranchTreeDecorator> REDWOOD_BRANCH_DECORATOR = registerTreeDecorator("redwood_branch_decorator", RedwoodBranchTreeDecorator.CODEC);
  public static final TreeDecoratorType<OliveBranchTreeDecorator> OLIVE_BRANCH_DECORATOR = registerTreeDecorator("olive_branch_decorator", OliveBranchTreeDecorator.CODEC);
  public static final TreeDecoratorType<SnowTreeDecorator> SNOW_DECORATOR = registerTreeDecorator("snow_decorator", SnowTreeDecorator.CODEC);
  public static final TreeDecoratorType<PolyporeTreeDecorator> POLYPORE_DECORATOR = registerTreeDecorator("polypore_decorator", PolyporeTreeDecorator.CODEC);


  public static final FoliagePlacerType<WisteriaFoliagePlacer> WISTERIA_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("wisteria_foliage_placer", WisteriaFoliagePlacer.CODEC);
  public static final FoliagePlacerType<SugiFoliagePlacer> SUGI_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("sugi_foliage_placer", SugiFoliagePlacer.CODEC);
  public static final FoliagePlacerType<AspenFoliagePlacer> ASPEN_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("aspen_foliage_placer", AspenFoliagePlacer.CODEC);
  public static final FoliagePlacerType<FirFoliagePlacer> FIR_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("fir_foliage_placer", FirFoliagePlacer.CODEC);
  public static final FoliagePlacerType<LarchFoliagePlacer> LARCH_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("larch_foliage_placer", LarchFoliagePlacer.CODEC);
  public static final FoliagePlacerType<RedwoodFoliagePlacer> REDWOOD_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("redwood_foliage_placer", RedwoodFoliagePlacer.CODEC);
  public static final FoliagePlacerType<CypressFoliagePlacer> CYPRESS_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("cypress_foliage_placer", CypressFoliagePlacer.CODEC);
  public static final FoliagePlacerType<MapleFoliagePlacer> MAPLE_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("maple_foliage_placer", MapleFoliagePlacer.CODEC);
  public static final FoliagePlacerType<CoconutFoliagePlacer> COCONUT_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("coconut_foliage_placer", CoconutFoliagePlacer.CODEC);
  public static final FoliagePlacerType<BirchFoliagePlacer> BIRCH_FOLIAGE_PLACER_TYPE = registerFoliagePlacer("birch_foliage_placer", BirchFoliagePlacer.CODEC);
  public static final FoliagePlacerType<GroundedBushFoliagePlacer> GROUNDED_BUSH_PLACER_TYPE = registerFoliagePlacer("grounded_bush_foliage_placer", GroundedBushFoliagePlacer.CODEC);


  public static final Carver<ReplaceableCaveCarverConfig> REPLACEABLE_CAVE_CARVER = registerCaveCarver("replaceable_cave",
      new ReplaceableCaveCarver(ReplaceableCaveCarverConfig.CAVE_CODEC));
  public static final Carver<ReplaceableRavineCarverConfig> REPLACEABLE_RAVINE_CARVER = registerCaveCarver("replaceable_canyon",
      new ReplaceableRavineCarver(ReplaceableRavineCarverConfig.RAVINE_CODEC));

  public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SUGI_PILLAR = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, Identifier.of(MOD_ID, "sugi_pillar"));
  public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SUGI_PILLAR_ROOF = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS,
      Identifier.of(MOD_ID, "sugi_pillar_roof"));
  public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> SUGI_SURFACE = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS, Identifier.of(MOD_ID, "sugi_surface"));

  public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> STRATIFIED_DESERT_PILLAR = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS,
      Identifier.of(MOD_ID, "stratified_desert_pillar"));
  public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> STRATIFIED_DESERT_PILLAR_ROOF = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS,
      Identifier.of(MOD_ID, "stratified_desert_pillar_roof"));
  public static final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> STRATIFIED_DESERT_SURFACE = RegistryKey.of(RegistryKeys.NOISE_PARAMETERS,
      Identifier.of(MOD_ID, "stratified_desert_surface"));

  public static final Feature<DeltaFeatureConfig> HIBISCUS_DELTA_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "water_delta_feature"),
      new NSDeltaFeature(DeltaFeatureConfig.CODEC)
  );
  public static final Feature<OreFeatureConfig> HIBISCUS_PUMPKIN_PATCH_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "pumpkin_patch_feature"),
      new PumpkinPatchFeature(OreFeatureConfig.CODEC)
  );
  public static final Feature<BlockPileFeatureConfig> HIBISCUS_LARGE_PUMPKIN_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "large_pumpkin_feature"),
      new LargePumpkinFeature(BlockPileFeatureConfig.CODEC)
  );
  public static final Feature<TurnipRootFeatureConfig> HIBISCUS_TURNIP_ROOT_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "turnip_root_feature"),
      new TurnipRootFeature(TurnipRootFeatureConfig.CODEC)
  );
  public static final Feature<DefaultFeatureConfig> JOSHUA_TREE_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "joshua_tree_feature"),
      new JoshuaTreeFeature(DefaultFeatureConfig.CODEC)
  );
  public static final Feature<DefaultFeatureConfig> ALLUAUDIA_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "alluaudia_feature"),
      new AlluaudiaFeature(DefaultFeatureConfig.CODEC)
  );
  public static final Feature<HugeMushroomFeatureConfig> HUGE_SHIITAKE_MUSHROOM_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "huge_shiitake_mushroom_feature"),
      new HugeShiitakeMushroomFeature(HugeMushroomFeatureConfig.CODEC)
  );
  public static final Feature<net.hibiscus.naturespirit.world.feature.HugeMushroomFeatureConfig> HUGE_RED_MUSHROOM_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "huge_red_mushroom_feature"),
      new HugeRedMushroomFeature(net.hibiscus.naturespirit.world.feature.HugeMushroomFeatureConfig.CODEC)
  );
  public static final Feature<net.hibiscus.naturespirit.world.feature.HugeMushroomFeatureConfig> HUGE_BROWN_MUSHROOM_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "huge_brown_mushroom_feature"),
      new HugeBrownMushroomFeature(net.hibiscus.naturespirit.world.feature.HugeMushroomFeatureConfig.CODEC)
  );
  public static final Feature<DefaultFeatureConfig> POLYPORE_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "polypore_feature"),
      new PolyporeFeature(DefaultFeatureConfig.CODEC)
  );
  public static final Feature<DefaultFeatureConfig> LOTUS_PLANT_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "lotus_plant_feature"),
      new LotusPlantFeature(DefaultFeatureConfig.CODEC)
  );
  public static final Feature<RandomPatchFeatureConfig> LEVELED_RANDOM_PATCH_FEATURE = Registry.register(Registries.FEATURE,
      Identifier.of(MOD_ID, "leveled_random_patch_feature"),
      new LeveledRandomPatch(RandomPatchFeatureConfig.CODEC)
  );

  private static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliagePlacer(String id, MapCodec<P> codec) {
    return (FoliagePlacerType) Registry.register(Registries.FOLIAGE_PLACER_TYPE, Identifier.of(MOD_ID, id), new FoliagePlacerType(codec));
  }

  private static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunkPlacer(String id, MapCodec<P> codec) {
    return (TrunkPlacerType) Registry.register(Registries.TRUNK_PLACER_TYPE, Identifier.of(MOD_ID, id), new TrunkPlacerType(codec));
  }

  private static <P extends TreeDecorator> TreeDecoratorType<P> registerTreeDecorator(String id, MapCodec<P> codec) {
    return (TreeDecoratorType) Registry.register(Registries.TREE_DECORATOR_TYPE, Identifier.of(MOD_ID, id), new TreeDecoratorType(codec));
  }

  private static <C extends CarverConfig, F extends Carver<C>> Carver<C> registerCaveCarver(String id, F carver) {
    return Registry.register(Registries.CARVER, Identifier.of(MOD_ID, id), carver);
  }

  public static void registerWorldGen() {
//            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SANDY), GenerationStep.Feature.VEGETAL_DECORATION, NSPlacedFeatures.ROOTED_DESERT_TURNIP);
  }

}
