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
import net.minecraft.world.gen.surfacebuilder.MaterialRules.MaterialCondition;

public class NSSurfaceRules {

  private static final MaterialRules.MaterialRule GRAVEL = makeStateRule(Blocks.GRAVEL);
  private static final MaterialRules.MaterialRule STONE = makeStateRule(Blocks.STONE);
  private static final MaterialRules.MaterialRule GRASS = makeStateRule(Blocks.GRASS_BLOCK);
  private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
  private static final MaterialRules.MaterialRule ROOTED_DIRT = makeStateRule(Blocks.ROOTED_DIRT);
  private static final MaterialRules.MaterialRule GRANITE = makeStateRule(Blocks.GRANITE);
  private static final MaterialRules.MaterialRule PODZOL = makeStateRule(Blocks.PODZOL);
  private static final MaterialRules.MaterialRule CALCITE = makeStateRule(Blocks.CALCITE);
  private static final MaterialRules.MaterialRule SANDY_SOIL = makeStateRule(NSMiscBlocks.SANDY_SOIL);
  private static final MaterialRules.MaterialRule RED_MOSS_BLOCK = makeStateRule(NSMiscBlocks.RED_MOSS_BLOCK);
  private static final MaterialRules.MaterialRule COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
  private static final MaterialRules.MaterialRule WHITE_KAOLIN = makeStateRule(NSColoredBlocks.WHITE_KAOLIN);
  private static final MaterialRules.MaterialRule PINK_SANDSTONE = makeStateRule(NSMiscBlocks.PINK_SANDSTONE);
  private static final MaterialRules.MaterialRule RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
  private static final MaterialRules.MaterialRule RED_SAND = makeStateRule(Blocks.RED_SAND);
  private static final MaterialRules.MaterialRule TRAVERTINE = makeStateRule(NSMiscBlocks.TRAVERTINE.getBase());
  private static final MaterialRules.MaterialRule WHITE_CHALK = makeStateRule(NSColoredBlocks.WHITE_CHALK);
  private static final MaterialRules.MaterialRule PINK_SAND = makeStateRule(NSMiscBlocks.PINK_SAND);
  private static final MaterialRules.MaterialRule CHERT = makeStateRule(NSMiscBlocks.CHERT.getBase());
  private static final MaterialRules.MaterialRule SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
  private static final MaterialRules.MaterialRule WATER = makeStateRule(Blocks.WATER);
  private static final MaterialRules.MaterialRule POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
  
  private static final MaterialCondition STONE_DEPTH_FLOOR_DOWN_1 = MaterialRules.stoneDepth(1, false, VerticalSurfaceType.FLOOR);
  private static final MaterialCondition STONE_DEPTH_FLOOR_DOWN_1_WITH_DEPTH = MaterialRules.stoneDepth(1, true, VerticalSurfaceType.FLOOR);

  public static MaterialRules.MaterialRule makeRules() {
    MaterialRules.MaterialCondition above25 = MaterialRules.aboveY(YOffset.fixed(25), -1);
    MaterialRules.MaterialCondition above60 = MaterialRules.aboveY(YOffset.fixed(60), 0);
    MaterialRules.MaterialCondition above63 = MaterialRules.aboveY(YOffset.fixed(63), 0);
    MaterialRules.MaterialCondition above65 = MaterialRules.aboveY(YOffset.fixed(65), 0);
    MaterialRules.MaterialCondition above70 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(70), 1);
    MaterialRules.MaterialCondition above76 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(76), 1);
    MaterialRules.MaterialCondition above80 = MaterialRules.aboveYWithStoneDepth(YOffset.fixed(80), 1);
    MaterialRules.MaterialCondition above256 = MaterialRules.aboveY(YOffset.fixed(256), 0);
    MaterialRules.MaterialCondition materialCondition5 = MaterialRules.water(
        -1,
        0
    );
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

    MaterialRules.MaterialCondition belowWater = MaterialRules.waterWithStoneDepth(-6, -1);

    MaterialRules.MaterialRule stoneOrGravel = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, STONE), GRAVEL);
    MaterialRules.MaterialRule pinkSandstoneOrPinkSand = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, PINK_SANDSTONE), PINK_SAND);
    MaterialRules.MaterialRule redSandstoneOrRedSand = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, RED_SANDSTONE), RED_SAND);
    MaterialRules.MaterialRule pinkSandstoneOrSoil = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, PINK_SANDSTONE), SANDY_SOIL);
    MaterialRules.MaterialRule travertineOrSoil = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, TRAVERTINE), SANDY_SOIL);
    MaterialRules.MaterialRule chertOrSoil = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, CHERT), SANDY_SOIL);
    MaterialRules.MaterialRule stoneOrMoss = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, STONE), RED_MOSS_BLOCK);
    MaterialRules.MaterialRule stoneOrSnow = MaterialRules.sequence(MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, STONE), SNOW_BLOCK);
    MaterialRules.MaterialRule powderSnow = MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.POWDER_SNOW, 0.35, 0.6), MaterialRules.condition(materialCondition7, POWDER_SNOW));

    MaterialRules.MaterialRule stratifiedDesertRule = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,
        MaterialRules.condition(MaterialRules.biome(NSBiomes.STRATIFIED_DESERT), MaterialRules.sequence(
            MaterialRules.condition(above256, CHERT),
            MaterialRules.condition(above70, MaterialRules.sequence(
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
    MaterialRules.MaterialRule steppeUndergroundRule = MaterialRules.condition(
        MaterialRules.biome(NSBiomes.SLEETED_SLOPES, NSBiomes.BLOOMING_HIGHLANDS, NSBiomes.SNOWCAPPED_RED_PEAKS, NSBiomes.SHRUBBY_HIGHLANDS, NSBiomes.WOODY_HIGHLANDS,
            NSBiomes.ARID_HIGHLANDS, NSBiomes.STRATIFIED_DESERT),
        MaterialRules.condition(above25, MaterialRules.terracottaBands())
    );

    MaterialRules.MaterialRule desertSteppeRule = MaterialRules.sequence(
        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,  MaterialRules.condition(MaterialRules.biome(NSBiomes.ARID_HIGHLANDS, NSBiomes.SHRUBBY_HIGHLANDS, NSBiomes.WOODY_HIGHLANDS, NSBiomes.BLOOMING_HIGHLANDS), MaterialRules.sequence(
                MaterialRules.condition(above256, CHERT),
                MaterialRules.condition(above70, MaterialRules.sequence(
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.SHRUBBY_HIGHLANDS, NSBiomes.ARID_HIGHLANDS), MaterialRules.condition(noiseCondition3, SANDY_SOIL)),
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.WOODY_HIGHLANDS, NSBiomes.BLOOMING_HIGHLANDS), MaterialRules.condition(noiseCondition3, GRASS)),
                    MaterialRules.terracottaBands(),
                    MaterialRules.condition(MaterialRules.not(above80), MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.SHRUBBY_HIGHLANDS), MaterialRules.condition(noiseCondition6, GRASS)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.WOODY_HIGHLANDS, NSBiomes.BLOOMING_HIGHLANDS), MaterialRules.condition(noiseCondition6, SANDY_SOIL))
                    ))
                )),
                MaterialRules.condition(materialCondition5, MaterialRules.sequence(
                    MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, CHERT),
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.SHRUBBY_HIGHLANDS), SANDY_SOIL),
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.ARID_HIGHLANDS), PINK_SAND)
                )),
                MaterialRules.condition(MaterialRules.not(holeCondition), CHERT)
            ))),
        MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MaterialRules.condition(MaterialRules.biome(NSBiomes.ARID_HIGHLANDS, NSBiomes.SHRUBBY_HIGHLANDS, NSBiomes.WOODY_HIGHLANDS, NSBiomes.BLOOMING_HIGHLANDS), MaterialRules.condition(belowWater, CHERT)))
        );
    
    MaterialRules.MaterialRule aspenRule = MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.stoneDepth(4, false, VerticalSurfaceType.FLOOR),
                MaterialRules.condition(MaterialRules.biome(NSBiomes.ASPEN_FOREST),
                    MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 2.4 / 8.25, Double.MAX_VALUE), COARSE_DIRT),
                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 1.6 / 8.25, Double.MAX_VALUE), ROOTED_DIRT),
                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -1.4 / 8.25, Double.MAX_VALUE), ROOTED_DIRT),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.condition(materialCondition7, GRASS)),
                        DIRT
                    ))),
            MaterialRules.condition(MaterialRules.stoneDepth(36, false, VerticalSurfaceType.FLOOR), MaterialRules.condition(MaterialRules.biome(NSBiomes.ASPEN_FOREST), GRANITE)));
    
    MaterialRules.MaterialRule xericRule = 
        MaterialRules.condition(belowWater,
            MaterialRules.sequence(
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                    MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.XERIC_PLAINS, NSBiomes.CEDAR_THICKET), MaterialRules.condition(noiseCondition4, travertineOrSoil)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.ARID_SAVANNA), MaterialRules.condition(noiseCondition4, chertOrSoil)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.WOODED_DRYLANDS), MaterialRules.condition(noiseCondition4, pinkSandstoneOrSoil)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.TUNDRA, NSBiomes.BOREAL_TAIGA), MaterialRules.condition(noiseCondition4, stoneOrMoss)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.SCORCHED_DUNES), MaterialRules.sequence(MaterialRules.condition(noiseCondition4, redSandstoneOrRedSand), chertOrSoil)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.TROPICAL_SHORES, NSBiomes.DRYLANDS), pinkSandstoneOrPinkSand),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.SNOWY_FIR_FOREST, NSBiomes.TUNDRA), MaterialRules.condition(noiseCondition5, stoneOrSnow)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.CHAPARRAL), SANDY_SOIL),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.SLEETED_SLOPES, NSBiomes.SNOWCAPPED_RED_PEAKS), MaterialRules.condition(materialCondition7, SNOW_BLOCK)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.SLEETED_SLOPES, NSBiomes.SNOWCAPPED_RED_PEAKS, NSBiomes.DUSTY_SLOPES, NSBiomes.RED_PEAKS),
                            MaterialRules.sequence(
                                MaterialRules.condition(above256, CHERT),
                                MaterialRules.condition(above70, MaterialRules.terracottaBands()),
                                MaterialRules.condition(materialCondition5, CHERT),
                                MaterialRules.condition(MaterialRules.not(holeCondition), CHERT)
                            ))
                    )),
                MaterialRules.condition(MaterialRules.stoneDepth(8, true, VerticalSurfaceType.FLOOR),
                    MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.LIVELY_DUNES, NSBiomes.BLOOMING_DUNES, NSBiomes.CHAPARRAL), MaterialRules.terracottaBands()),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.DUSTY_SLOPES, NSBiomes.RED_PEAKS), MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(256), 1), SNOW_BLOCK)),
                        MaterialRules.condition(MaterialRules.biome(NSBiomes.SLEETED_SLOPES, NSBiomes.SNOWCAPPED_RED_PEAKS, NSBiomes.DUSTY_SLOPES, NSBiomes.RED_PEAKS),
                            MaterialRules.sequence(
                                MaterialRules.condition(MaterialRules.steepSlope(), CHERT),
                                MaterialRules.condition(belowWater, CHERT),
                                MaterialRules.condition(MaterialRules.biome(NSBiomes.SLEETED_SLOPES), powderSnow)
                            ))
                    )),
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6, MaterialRules.sequence(
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.TROPICAL_SHORES), PINK_SANDSTONE),
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.LIVELY_DUNES, NSBiomes.BLOOMING_DUNES), SANDY_SOIL)
                )),
                MaterialRules.condition(STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_30, MaterialRules.sequence(
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.SCORCHED_DUNES), RED_SANDSTONE),
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.DRYLANDS), PINK_SANDSTONE)
                ))
            )
        );
    MaterialRules.MaterialRule chaparralRule = MaterialRules.condition(belowWater, MaterialRules.sequence(
        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.condition(MaterialRules.biome(NSBiomes.CHAPARRAL), MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.PATCH, 0.0), GRASS))),
        MaterialRules.condition(STONE_DEPTH_FLOOR_DOWN_1, MaterialRules.condition(MaterialRules.biome(NSBiomes.CHAPARRAL), MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.PATCH, 0.0), DIRT)))
    ));

    MaterialRules.MaterialRule tropicalBasinRule = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, 
        MaterialRules.condition(above60, MaterialRules.condition(MaterialRules.not(above63),
                MaterialRules.condition(MaterialRules.biome(NSBiomes.MARSH, NSBiomes.TROPICAL_BASIN, NSBiomes.BAMBOO_WETLANDS), MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE_SWAMP, 0.0), WATER))))
        );
    
    MaterialRules.MaterialRule chalkCliffsRule = MaterialRules.condition(above65,
        MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.condition(MaterialRules.biome(NSBiomes.WHITE_CLIFFS), GRASS)),
            MaterialRules.condition(STONE_DEPTH_FLOOR_DOWN_1, MaterialRules.condition(MaterialRules.biome(NSBiomes.WHITE_CLIFFS), DIRT))
        )
    );
    MaterialRules.MaterialRule chalkUndergroundRule =
        MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(60), -1), MaterialRules.condition(MaterialRules.biome(NSBiomes.WHITE_CLIFFS), WHITE_CHALK)),
            MaterialRules.condition(MaterialRules.aboveY(YOffset.fixed(45), -1), MaterialRules.condition(MaterialRules.biome(NSBiomes.WHITE_CLIFFS), CALCITE))
        );
    MaterialRules.MaterialRule redwoodForestRule =
        MaterialRules.sequence(
            MaterialRules.condition(STONE_DEPTH_FLOOR_DOWN_1_WITH_DEPTH,
                MaterialRules.condition(MaterialRules.biome(NSBiomes.REDWOOD_FOREST, NSBiomes.MAPLE_WOODLANDS),
                    MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, 1.75 / 8.25, Double.MAX_VALUE), COARSE_DIRT))),
            MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,
                MaterialRules.condition(materialCondition7,
                    MaterialRules.condition(MaterialRules.biome(NSBiomes.REDWOOD_FOREST, NSBiomes.MAPLE_WOODLANDS), MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, -.95 / 8.25, Double.MAX_VALUE), PODZOL),
                        GRASS
                )))),
            MaterialRules.condition(STONE_DEPTH_FLOOR_DOWN_1_WITH_DEPTH, MaterialRules.condition(MaterialRules.biome(NSBiomes.REDWOOD_FOREST, NSBiomes.MAPLE_WOODLANDS), DIRT))
        );

    MaterialRules.MaterialRule alpineRule = MaterialRules.sequence(
            MaterialRules.condition(STONE_DEPTH_FLOOR_DOWN_1_WITH_DEPTH, MaterialRules.condition(MaterialRules.biome(NSBiomes.ALPINE_CLEARINGS, NSBiomes.ALPINE_HIGHLANDS, NSBiomes.CONIFEROUS_COVERT, NSBiomes.HEATHER_FIELDS, NSBiomes.GOLDEN_WILDS, NSBiomes.SUGI_FOREST),
                MaterialRules.condition(noiseCondition2, COARSE_DIRT))),
            MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, MaterialRules.condition(materialCondition7,
                MaterialRules.condition(MaterialRules.biome(NSBiomes.ALPINE_CLEARINGS, NSBiomes.ALPINE_HIGHLANDS, NSBiomes.CONIFEROUS_COVERT, NSBiomes.HEATHER_FIELDS, NSBiomes.GOLDEN_WILDS, NSBiomes.SUGI_FOREST), GRASS))));

    ImmutableList.Builder<MaterialRules.MaterialRule> builder = ImmutableList.builder();
    MaterialRules.MaterialRule stratifiedDesertSurfaceRule = MaterialRules.condition(MaterialRules.surface(), stratifiedDesertRule);
    MaterialRules.MaterialRule chaparralSurfaceRule = MaterialRules.condition(MaterialRules.surface(), chaparralRule);
    MaterialRules.MaterialRule chalkCliffsSurfaceRule = MaterialRules.condition(MaterialRules.surface(), chalkCliffsRule);
    MaterialRules.MaterialRule xericSurfaceRule = MaterialRules.condition(MaterialRules.surface(), xericRule);
    MaterialRules.MaterialRule tropicalBasinSurfaceRule = MaterialRules.condition(MaterialRules.surface(), tropicalBasinRule);
    MaterialRules.MaterialRule aspenSurfaceRule = MaterialRules.condition(MaterialRules.surface(), aspenRule);
    MaterialRules.MaterialRule desertSteppeSurfaceRule = MaterialRules.condition(MaterialRules.surface(), desertSteppeRule);
    MaterialRules.MaterialRule redwoodForestSurfaceRule = MaterialRules.condition(MaterialRules.surface(), redwoodForestRule);
    MaterialRules.MaterialRule alpineSurfaceRule = MaterialRules.condition(MaterialRules.surface(), alpineRule);
    builder.add(stratifiedDesertSurfaceRule);
    builder.add(chaparralSurfaceRule);
    builder.add(chalkCliffsSurfaceRule);
    builder.add(chalkUndergroundRule);
    builder.add(xericSurfaceRule);
    builder.add(tropicalBasinSurfaceRule);
    builder.add(aspenSurfaceRule);
    builder.add(desertSteppeSurfaceRule);
    builder.add(steppeUndergroundRule);
    builder.add(redwoodForestSurfaceRule);
    builder.add(alpineSurfaceRule);
    return MaterialRules.sequence(builder
        .build()
        .toArray(MaterialRules.MaterialRule[]::new));
  }

  private static MaterialRules.MaterialRule makeStateRule(Block block) {
    return MaterialRules.block(block.getDefaultState());
  }
}
