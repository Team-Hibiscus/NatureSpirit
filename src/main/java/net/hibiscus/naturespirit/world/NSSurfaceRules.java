package net.hibiscus.naturespirit.world;

import static net.minecraft.world.gen.surfacebuilder.MaterialRules.STONE_DEPTH_FLOOR;
import static net.minecraft.world.gen.surfacebuilder.MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH;
import static net.minecraft.world.gen.surfacebuilder.MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_30;
import static net.minecraft.world.gen.surfacebuilder.MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6;

import com.google.common.collect.ImmutableList;
import net.hibiscus.naturespirit.registration.NSBiomes;
import net.hibiscus.naturespirit.registration.NSColoredBlocks;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class NSSurfaceRules {

  private static final MaterialRules.MaterialRule GRAVEL = makeStateRule(Blocks.GRAVEL);
  private static final MaterialRules.MaterialRule STONE = makeStateRule(Blocks.STONE);
  private static final MaterialRules.MaterialRule GRASS = makeStateRule(Blocks.GRASS_BLOCK);
  private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
  private static final MaterialRules.MaterialRule ROOTED_DIRT = makeStateRule(Blocks.ROOTED_DIRT);
  private static final MaterialRules.MaterialRule GRANITE = makeStateRule(Blocks.GRANITE);
  private static final MaterialRules.MaterialRule PODZOL = makeStateRule(Blocks.PODZOL);
  //   private static final MaterialRules.MaterialRule SAND = makeStateRule(Blocks.SAND);
  //   private static final MaterialRules.MaterialRule SANDSTONE = makeStateRule(Blocks.SANDSTONE);
  private static final MaterialRules.MaterialRule CALCITE = makeStateRule(Blocks.CALCITE);
  private static final MaterialRules.MaterialRule SANDY_SOIL = makeStateRule(NSMiscBlocks.SANDY_SOIL);
  private static final MaterialRules.MaterialRule RED_MOSS_BLOCK = makeStateRule(NSMiscBlocks.RED_MOSS_BLOCK);
  private static final MaterialRules.MaterialRule COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
  private static final MaterialRules.MaterialRule MOSS_BLOCK = makeStateRule(Blocks.MOSS_BLOCK);
  private static final MaterialRules.MaterialRule WHITE_KAOLIN = makeStateRule(NSColoredBlocks.WHITE_KAOLIN);
  private static final MaterialRules.MaterialRule PINK_SANDSTONE = makeStateRule(NSMiscBlocks.PINK_SANDSTONE);
  private static final MaterialRules.MaterialRule RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
  private static final MaterialRules.MaterialRule RED_SAND = makeStateRule(Blocks.RED_SAND);
  private static final MaterialRules.MaterialRule TRAVERTINE = makeStateRule(NSMiscBlocks.TRAVERTINE.getBase());
  private static final MaterialRules.MaterialRule KAOLIN = makeStateRule(NSColoredBlocks.KAOLIN);
  private static final MaterialRules.MaterialRule WHITE_CHALK = makeStateRule(NSColoredBlocks.WHITE_CHALK);
  private static final MaterialRules.MaterialRule PINK_SAND = makeStateRule(NSMiscBlocks.PINK_SAND);
  private static final MaterialRules.MaterialRule CHERT = makeStateRule(NSMiscBlocks.CHERT.getBase());
  private static final MaterialRules.MaterialRule SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
  private static final MaterialRules.MaterialRule WATER = makeStateRule(Blocks.WATER);
  private static final MaterialRules.MaterialRule POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);

  public static MaterialRules.MaterialRule makeRules() {
    MaterialRules.MaterialCondition materialCondition = MaterialRules.aboveY(YOffset.fixed(256), 0);
    MaterialRules.MaterialCondition materialCondition2 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(63), -1);
    MaterialRules.MaterialCondition materialCondition3 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(70), 1);
    MaterialRules.MaterialCondition materialCondition4 = MaterialRules.aboveY(YOffset.fixed(63), 0);
    MaterialRules.MaterialCondition above25 = MaterialRules.aboveY(YOffset.fixed(25), -1);
    MaterialRules.MaterialCondition above62 = MaterialRules.aboveY(YOffset.fixed(62), 0);
    MaterialRules.MaterialCondition chalkGrassCondition = MaterialRules.aboveY(YOffset.fixed(65), 0);
    MaterialRules.MaterialCondition materialCondition5 = MaterialRules.water(
        -1,
        0
    );
    MaterialRules.MaterialCondition materialCondition6 = MaterialRules.aboveY(YOffset.fixed(60), 0);
    MaterialRules.MaterialCondition materialCondition7 = MaterialRules.water(
        0,
        0
    );
    MaterialRules.MaterialCondition holeCondition = MaterialRules.hole();
    MaterialRules.MaterialCondition noiseCondition1 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -0.909D, -0.5454D);
    MaterialRules.MaterialCondition noiseCondition2 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -0.5454D, -0.3818D);
    MaterialRules.MaterialCondition noiseCondition3 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 0.5454D, 0.909D);
    MaterialRules.MaterialCondition noiseCondition4 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -0.5454D, 0.0454D);
    MaterialRules.MaterialCondition noiseCondition5 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 0.2454D, 6D);
    MaterialRules.MaterialCondition noiseCondition6 = MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -0.0454D, 6D);

    MaterialRules.MaterialCondition above76 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(76), 1);
    MaterialRules.MaterialCondition above80 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(80), 1);
    MaterialRules.MaterialCondition belowWater = MaterialRules.waterWithStoneDepth(-6, -1);

    MaterialRules.MaterialRule stoneOrGravel = MaterialRules.sequence(
        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, STONE),
        GRAVEL
    );
    MaterialRules.MaterialRule pinkSandstoneOrPinkSand = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, PINK_SANDSTONE), PINK_SAND);
    MaterialRules.MaterialRule redSandstoneOrRedSand = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, RED_SANDSTONE), RED_SAND);
    MaterialRules.MaterialRule pinkSandstoneOrSoil = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, PINK_SANDSTONE), SANDY_SOIL);
    MaterialRules.MaterialRule travertineOrSoil = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, TRAVERTINE), SANDY_SOIL);
    MaterialRules.MaterialRule chertOrSoil = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, CHERT), SANDY_SOIL);
    MaterialRules.MaterialRule stoneOrMoss = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, STONE), RED_MOSS_BLOCK);
    MaterialRules.MaterialRule stoneOrSnow = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, STONE), SNOW_BLOCK);
    MaterialRules.MaterialRule powderSnow = MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.POWDER_SNOW, 0.35, 0.6),
        MaterialRules.condition(materialCondition7, POWDER_SNOW));

    MaterialRules.MaterialRule stratifiedDesertRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.STRATIFIED_DESERT),
        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
            MaterialRules.condition(materialCondition, CHERT),
            MaterialRules.condition(materialCondition3, MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.not(above76),
                    MaterialRules.sequence(
                        MaterialRules.condition(noiseCondition1, GRASS),
                        MaterialRules.condition(noiseCondition2, COARSE_DIRT)
                    )),
                MaterialRules.condition(noiseCondition3, SANDY_SOIL),
                MaterialRules.terracottaBands()
            )),
            MaterialRules.condition(materialCondition5, MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, CHERT),
                SANDY_SOIL
            )),
            MaterialRules.condition(MaterialRules.not(holeCondition), CHERT),
            MaterialRules.condition(belowWater, WHITE_KAOLIN),
            stoneOrGravel
        ))

    );
    MaterialRules.MaterialRule livelyDunesRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.LIVELY_DUNES, NSBiomes.BLOOMING_DUNES),
        MaterialRules.condition(belowWater, MaterialRules.sequence(
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, SANDY_SOIL),
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6, SANDY_SOIL),
            MaterialRules.condition(MaterialRules.stoneDepth(8, true, VerticalSurfaceType.FLOOR), MaterialRules.terracottaBands())
        ))
    );
    MaterialRules.MaterialRule stratifiedUndergroundRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.STRATIFIED_DESERT),
        MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(50), -1), MaterialRules.terracottaBands())
    );
    MaterialRules.MaterialRule chaparralRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.CHAPARRAL),
        MaterialRules.condition(belowWater, MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.PATCH, 0.0),
                MaterialRules.sequence(
                    MaterialRules.condition(STONE_DEPTH_FLOOR, GRASS),
                    MaterialRules.condition(MaterialRules.stoneDepth(1, false, VerticalSurfaceType.FLOOR), DIRT)
                )

            ),
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, SANDY_SOIL),
            MaterialRules.condition(MaterialRules.stoneDepth(8, true, VerticalSurfaceType.FLOOR), MaterialRules.terracottaBands())
        ))
    );

    MaterialRules.MaterialRule desertSteppeRule = MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(NSBiomes.ARID_HIGHLANDS),
        MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                MaterialRules.condition(materialCondition, CHERT),
                MaterialRules.condition(materialCondition3, MaterialRules.sequence(
                    MaterialRules.condition(noiseCondition3, SANDY_SOIL),
                    MaterialRules.terracottaBands())),
                MaterialRules.condition(
                    materialCondition5,
                    MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, CHERT), PINK_SAND)
                ),
                MaterialRules.condition(MaterialRules.not(holeCondition), CHERT),
                MaterialRules.condition(belowWater, CHERT)

            )),
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(belowWater, CHERT))
        )
    ));

    MaterialRules.MaterialRule shrubbySteppeRule = MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(NSBiomes.SHRUBBY_HIGHLANDS),
        MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(MaterialRules.condition(materialCondition, CHERT),
                MaterialRules.condition(materialCondition3,
                    MaterialRules.sequence(MaterialRules.condition(noiseCondition3, SANDY_SOIL), MaterialRules.condition(MaterialRules.not(above80),
                        MaterialRules.sequence(MaterialRules.condition(noiseCondition6, GRASS))
                    ), MaterialRules.terracottaBands())),
                MaterialRules.condition(
                    materialCondition5,
                    MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, CHERT), SANDY_SOIL)
                ),
                MaterialRules.condition(MaterialRules.not(holeCondition), CHERT),
                MaterialRules.condition(belowWater, CHERT)

            )),
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(belowWater, CHERT))
        )
    ));

    MaterialRules.MaterialRule woodySteppeRule = MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(NSBiomes.WOODY_HIGHLANDS, NSBiomes.BLOOMING_HIGHLANDS),
        MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(MaterialRules.condition(materialCondition, CHERT),
                MaterialRules.condition(materialCondition3, MaterialRules.sequence(MaterialRules.condition(noiseCondition3, GRASS), MaterialRules.condition(MaterialRules.not(above76),
                    MaterialRules.sequence(MaterialRules.condition(noiseCondition6, SANDY_SOIL))
                ), MaterialRules.terracottaBands())),
                MaterialRules.condition(
                    materialCondition5,
                    MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, CHERT), GRASS)
                ),
                MaterialRules.condition(MaterialRules.not(holeCondition), CHERT),
                MaterialRules.condition(belowWater, CHERT)

            )),
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(belowWater, CHERT))
        )
    ));

    MaterialRules.MaterialRule snowySteppeRule = MaterialRules.sequence(
        MaterialRules.condition(MaterialRules.biome(NSBiomes.SLEETED_SLOPES, NSBiomes.SNOWCAPPED_RED_PEAKS),
            MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.steepSlope(), CHERT),
                MaterialRules.condition(materialCondition7, SNOW_BLOCK),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                    MaterialRules.condition(materialCondition, CHERT),
                    MaterialRules.condition(materialCondition3, MaterialRules.terracottaBands()),
                    MaterialRules.condition(materialCondition5, CHERT),
                    MaterialRules.condition(MaterialRules.not(holeCondition), CHERT),
                    MaterialRules.condition(belowWater, CHERT)

                )),
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(belowWater, CHERT))
            )),
        MaterialRules.condition(
            MaterialRules.biome(NSBiomes.SLEETED_SLOPES),
            MaterialRules.sequence(powderSnow)
        )
    );

    MaterialRules.MaterialRule steppeUndergroundRule = MaterialRules.condition(
        MaterialRules.biome(NSBiomes.SLEETED_SLOPES, NSBiomes.BLOOMING_HIGHLANDS, NSBiomes.SNOWCAPPED_RED_PEAKS, NSBiomes.SHRUBBY_HIGHLANDS, NSBiomes.WOODY_HIGHLANDS,
            NSBiomes.ARID_HIGHLANDS),
        MaterialRules.condition(above25, MaterialRules.terracottaBands())
    );
    MaterialRules.MaterialRule chalkCliffsRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.WHITE_CLIFFS),
        MaterialRules.sequence(
            MaterialRules.condition(chalkGrassCondition, MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.stoneDepth(0, false, VerticalSurfaceType.FLOOR), GRASS),
                MaterialRules.condition(MaterialRules.stoneDepth(1, false, VerticalSurfaceType.FLOOR), DIRT)
            ))
        )
    );

    MaterialRules.MaterialRule dustyRule = MaterialRules.sequence(
        MaterialRules.condition(MaterialRules.biome(NSBiomes.DUSTY_SLOPES, NSBiomes.RED_PEAKS),
            MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.steepSlope(), CHERT),
                MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(256), 1), SNOW_BLOCK),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
                    MaterialRules.condition(materialCondition, CHERT),
                    MaterialRules.condition(materialCondition3, MaterialRules.terracottaBands()),
                    MaterialRules.condition(materialCondition5, CHERT),
                    MaterialRules.condition(MaterialRules.not(holeCondition), CHERT),
                    MaterialRules.condition(belowWater, CHERT)

                )),
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(belowWater, CHERT))
            ))
    );
    MaterialRules.MaterialRule chalkUndergroundRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.WHITE_CLIFFS),
        MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(60), -1), WHITE_CHALK),
            MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(45), -1), CALCITE)
        )
    );
    MaterialRules.MaterialRule aspenRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.ASPEN_FOREST),
        MaterialRules.sequence(
            MaterialRules.condition(above62,
                MaterialRules.condition(MaterialRules.stoneDepth(4, false, VerticalSurfaceType.FLOOR),
                    MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 2.4 / 8.25, Double.MAX_VALUE), COARSE_DIRT),
                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 1.6 / 8.25, Double.MAX_VALUE), ROOTED_DIRT),
                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -.6 / 8.25, Double.MAX_VALUE),
                            MaterialRules.condition(MaterialRules.stoneDepth(0, false, VerticalSurfaceType.FLOOR), GRASS)),
                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -1.4 / 8.25, Double.MAX_VALUE), ROOTED_DIRT),
                        MaterialRules.condition(MaterialRules.stoneDepth(0, false, VerticalSurfaceType.FLOOR), GRASS),
                        DIRT
                    ))),
            MaterialRules.condition(MaterialRules.stoneDepth(36, false, VerticalSurfaceType.FLOOR), GRANITE))
    );
    MaterialRules.MaterialRule drylandsRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.DRYLANDS),
        MaterialRules.condition(belowWater,
            MaterialRules.sequence(MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, pinkSandstoneOrPinkSand),
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_30, PINK_SANDSTONE)
            )
        )
    );
    MaterialRules.MaterialRule scorchedDunesRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.SCORCHED_DUNES),
        MaterialRules.condition(belowWater,
            MaterialRules.sequence(
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.sequence(MaterialRules.condition(noiseCondition4, redSandstoneOrRedSand), chertOrSoil)),
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_30, RED_SANDSTONE)
            )
        )
    );
    MaterialRules.MaterialRule woodedDrylandsRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.WOODED_DRYLANDS),
        MaterialRules.condition(belowWater,
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(noiseCondition4, pinkSandstoneOrSoil))
        )
    );
    MaterialRules.MaterialRule shoresRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.TROPICAL_SHORES),
        MaterialRules.condition(belowWater,
            MaterialRules.sequence(MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, pinkSandstoneOrPinkSand),
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6, PINK_SANDSTONE)
            )
        )
    );
    MaterialRules.MaterialRule xericRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.XERIC_PLAINS, NSBiomes.CEDAR_THICKET),
        MaterialRules.condition(belowWater,
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(noiseCondition4, travertineOrSoil))
        )
    );
    MaterialRules.MaterialRule aridRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.ARID_SAVANNA),
        MaterialRules.condition(belowWater,
            MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(noiseCondition4, chertOrSoil))
        )
    );
    MaterialRules.MaterialRule tundraRule = MaterialRules.condition(belowWater,
        MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
            MaterialRules.condition(MaterialRules.biome(NSBiomes.SNOWY_FIR_FOREST, NSBiomes.TUNDRA, NSBiomes.BOREAL_TAIGA),
                MaterialRules.sequence(MaterialRules.condition(noiseCondition4, MaterialRules.condition(MaterialRules.biome(NSBiomes.TUNDRA, NSBiomes.BOREAL_TAIGA), stoneOrMoss)),
                    MaterialRules.condition(MaterialRules.biome(
                        NSBiomes.SNOWY_FIR_FOREST, NSBiomes.TUNDRA), MaterialRules.condition(noiseCondition5, stoneOrSnow)))))
    );
    MaterialRules.MaterialRule tropicalBasinRule = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.biome(NSBiomes.MARSH, NSBiomes.TROPICAL_BASIN, NSBiomes.BAMBOO_WETLANDS), MaterialRules.condition(materialCondition6,
                MaterialRules.condition(MaterialRules.not(materialCondition4), MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE_SWAMP, 0.0), WATER))))
        ))
    );
    MaterialRules.MaterialRule redwoodForestRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.REDWOOD_FOREST, NSBiomes.MAPLE_WOODLANDS),
        MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 1.75 / 8.25, Double.MAX_VALUE),
                MaterialRules.condition(MaterialRules.stoneDepth(1, true, VerticalSurfaceType.FLOOR), COARSE_DIRT)),
            MaterialRules.condition(MaterialRules.stoneDepth(0, false, VerticalSurfaceType.FLOOR),
                MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -.95 / 8.25, Double.MAX_VALUE),
                    MaterialRules.condition(materialCondition7, PODZOL))),
            MaterialRules.sequence(MaterialRules.condition(MaterialRules.stoneDepth(0, false, VerticalSurfaceType.FLOOR), MaterialRules.condition(materialCondition7, GRASS)),
                MaterialRules.condition(MaterialRules.stoneDepth(1, true, VerticalSurfaceType.FLOOR), DIRT))
        )
    );

    MaterialRules.MaterialRule alpineRule = MaterialRules.condition(
        MaterialRules.biome(NSBiomes.ALPINE_CLEARINGS, NSBiomes.ALPINE_HIGHLANDS, NSBiomes.CONIFEROUS_COVERT, NSBiomes.HEATHER_FIELDS, NSBiomes.GOLDEN_WILDS),
        MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.stoneDepth(0, true, VerticalSurfaceType.FLOOR),
                MaterialRules.condition(noiseCondition2, MaterialRules.condition(materialCondition7, COARSE_DIRT))),
            MaterialRules.condition(MaterialRules.stoneDepth(0, false, VerticalSurfaceType.FLOOR), MaterialRules.condition(materialCondition7, GRASS))));

    MaterialRules.MaterialRule sugiRule = MaterialRules.condition(MaterialRules.biome(NSBiomes.SUGI_FOREST),
        MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.stoneDepth(0, true, VerticalSurfaceType.FLOOR),
                MaterialRules.condition(noiseCondition2, MaterialRules.condition(MaterialRules.stoneDepth(1, true, VerticalSurfaceType.FLOOR), COARSE_DIRT))),
            MaterialRules.condition(MaterialRules.stoneDepth(0, false, VerticalSurfaceType.FLOOR), MaterialRules.condition(materialCondition7, GRASS))));

    ImmutableList.Builder<MaterialRules.MaterialRule> builder = ImmutableList.builder();
    MaterialRules.MaterialRule dustySurfaceRule = MaterialRules.condition(MaterialRules.surface(), dustyRule);
    MaterialRules.MaterialRule stratifiedDesertSurfaceRule = MaterialRules.condition(MaterialRules.surface(), stratifiedDesertRule);
    MaterialRules.MaterialRule livelyDunesSurfaceRule = MaterialRules.condition(MaterialRules.surface(), livelyDunesRule);
    MaterialRules.MaterialRule chaparralSurfaceRule = MaterialRules.condition(MaterialRules.surface(), chaparralRule);
    MaterialRules.MaterialRule chalkCliffsSurfaceRule = MaterialRules.condition(MaterialRules.surface(), chalkCliffsRule);
    MaterialRules.MaterialRule drylandsSurfaceRule = MaterialRules.condition(MaterialRules.surface(), drylandsRule);
    MaterialRules.MaterialRule woodedDrylandsSurfaceRule = MaterialRules.condition(MaterialRules.surface(), woodedDrylandsRule);
    MaterialRules.MaterialRule shoresSurfaceRule = MaterialRules.condition(MaterialRules.surface(), shoresRule);
    MaterialRules.MaterialRule xericSurfaceRule = MaterialRules.condition(MaterialRules.surface(), xericRule);
    MaterialRules.MaterialRule aridSurfaceRule = MaterialRules.condition(MaterialRules.surface(), aridRule);
    MaterialRules.MaterialRule scorchedDunesSurfaceRule = MaterialRules.condition(MaterialRules.surface(), scorchedDunesRule);
    MaterialRules.MaterialRule tundraSurfaceRule = MaterialRules.condition(MaterialRules.surface(), tundraRule);
    MaterialRules.MaterialRule tropicalBasinSurfaceRule = MaterialRules.condition(MaterialRules.surface(), tropicalBasinRule);
    MaterialRules.MaterialRule aspenSurfaceRule = MaterialRules.condition(MaterialRules.surface(), aspenRule);
    MaterialRules.MaterialRule desertSteppeSurfaceRule = MaterialRules.condition(MaterialRules.surface(), desertSteppeRule);
    MaterialRules.MaterialRule shrubbySteppeSurfaceRule = MaterialRules.condition(MaterialRules.surface(), shrubbySteppeRule);
    MaterialRules.MaterialRule woodySteppeSurfaceRule = MaterialRules.condition(MaterialRules.surface(), woodySteppeRule);
    MaterialRules.MaterialRule snowySteppeSurfaceRule = MaterialRules.condition(MaterialRules.surface(), snowySteppeRule);
    MaterialRules.MaterialRule redwoodForestSurfaceRule = MaterialRules.condition(MaterialRules.surface(), redwoodForestRule);
    MaterialRules.MaterialRule alpineSurfaceRule = MaterialRules.condition(MaterialRules.surface(), alpineRule);
    MaterialRules.MaterialRule sugiSurfaceRule = MaterialRules.condition(MaterialRules.surface(), sugiRule);
    builder.add(dustySurfaceRule);
    builder.add(stratifiedDesertSurfaceRule);
    builder.add(stratifiedUndergroundRule);
    builder.add(chaparralSurfaceRule);
    builder.add(livelyDunesSurfaceRule);
    builder.add(chalkCliffsSurfaceRule);
    builder.add(chalkUndergroundRule);
    builder.add(drylandsSurfaceRule);
    builder.add(woodedDrylandsSurfaceRule);
    builder.add(shoresSurfaceRule);
    builder.add(xericSurfaceRule);
    builder.add(aridSurfaceRule);
    builder.add(tundraSurfaceRule);
    builder.add(tropicalBasinSurfaceRule);
    builder.add(scorchedDunesSurfaceRule);
    builder.add(aspenSurfaceRule);
    builder.add(desertSteppeSurfaceRule);
    builder.add(shrubbySteppeSurfaceRule);
    builder.add(woodySteppeSurfaceRule);
    builder.add(snowySteppeSurfaceRule);
    builder.add(steppeUndergroundRule);
    builder.add(redwoodForestSurfaceRule);
    builder.add(alpineSurfaceRule);
    builder.add(sugiSurfaceRule);
    return MaterialRules.sequence(builder
        .build()
        .toArray(MaterialRules.MaterialRule[]::new));
  }

  private static MaterialRules.MaterialRule makeStateRule(Block block) {
    return MaterialRules.block(block.getDefaultState());
  }
}
