package net.hibiscus.naturespirit.world.feature;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ChunkSectionCache;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;


public class PumpkinPatchFeature extends Feature<OreFeatureConfig> {

  public PumpkinPatchFeature(Codec<OreFeatureConfig> codec) {
    super(codec);
  }

  public boolean generate(FeatureContext<OreFeatureConfig> context) {
    Random random = context.getRandom();
    BlockPos blockPos = context.getOrigin();
    StructureWorldAccess structureWorldAccess = context.getWorld();
    OreFeatureConfig oreFeatureConfig = context.getConfig();
    float f = random.nextFloat() * 3.1415927F;
    float g = (float) oreFeatureConfig.size / 8.0F;
    int i = MathHelper.ceil(((float) oreFeatureConfig.size / 16.0F * 2.0F + 1.0F) / 2.0F);
    double d = (double) blockPos.getX() + Math.sin(f) * (double) g;
    double e = (double) blockPos.getX() - Math.sin(f) * (double) g;
    double h = (double) blockPos.getZ() + Math.cos(f) * (double) g;
    double j = (double) blockPos.getZ() - Math.cos(f) * (double) g;
    double l = blockPos.getY();
    //      double m = (double)(blockPos.getY() + random.nextInt(3) - 2);
    int n = blockPos.getX() - MathHelper.ceil(g) - i;
    int o = blockPos.getY() - 2 - i;
    int p = blockPos.getZ() - MathHelper.ceil(g) - i;
    int q = 2 * (MathHelper.ceil(g) + i);
    int r = 2;

    for (int s = n; s <= n + q; ++s) {
      for (int t = p; t <= p + q; ++t) {
        if (o <= structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t)) {
          return this.generateVeinPart(structureWorldAccess, random, oreFeatureConfig, d, e, h, j, l, l + 1, n, o, p, q, r);
        }
      }
    }

    return false;
  }

  protected boolean generateVeinPart(StructureWorldAccess world, Random random, OreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY,
      double endY, int x, int y, int z, int horizontalSize, int verticalSize) {
    int i = 0;
    BitSet bitSet = new BitSet(horizontalSize * verticalSize * horizontalSize);
    BlockPos.Mutable mutable = new BlockPos.Mutable();
    int j = config.size;
    double[] ds = new double[j * 4];

    int k;
    double d;
    double e;
    double g;
    double h;
    for (k = 0; k < j; ++k) {
      float f = (float) k / (float) j;
      d = MathHelper.lerp(f, startX, endX);
      e = MathHelper.lerp(f, startY, endY);
      g = MathHelper.lerp(f, startZ, endZ);
      h = random.nextDouble() * (double) j / 16.0D;
      double l = ((double) (MathHelper.sin(3.1415927F * f) + 1.0F) * h + 1.0D) / 2.0D;
      ds[k * 4] = d;
      ds[k * 4 + 1] = e;
      ds[k * 4 + 2] = g;
      ds[k * 4 + 3] = l;
    }

    int m;
    for (k = 0; k < j - 1; ++k) {
      if (!(ds[k * 4 + 3] <= 0.0D)) {
        for (m = k + 1; m < j; ++m) {
          if (!(ds[m * 4 + 3] <= 0.0D)) {
            d = ds[k * 4] - ds[m * 4];
            e = ds[k * 4 + 1] - ds[m * 4 + 1];
            g = ds[k * 4 + 2] - ds[m * 4 + 2];
            h = ds[k * 4 + 3] - ds[m * 4 + 3];
            if (h * h > d * d + e * e + g * g) {
              if (h > 0.0D) {
                ds[m * 4 + 3] = -1.0D;
              } else {
                ds[k * 4 + 3] = -1.0D;
              }
            }
          }
        }
      }
    }

    ChunkSectionCache chunkSectionCache = new ChunkSectionCache(world);

    try {
      for (m = 0; m < j; ++m) {
        d = ds[m * 4 + 3];
        if (!(d < 0.0D)) {
          e = ds[m * 4];
          g = ds[m * 4 + 1];
          h = ds[m * 4 + 2];
          int n = Math.max(MathHelper.floor(e - d), x);
          int o = Math.max(MathHelper.floor(g - d), y);
          int p = Math.max(MathHelper.floor(h - d), z);
          int q = Math.max(MathHelper.floor(e + d), n);
          int r = Math.max(MathHelper.floor(g + d), o);
          int s = Math.max(MathHelper.floor(h + d), p);

          for (int t = n; t <= q; ++t) {
            double u = ((double) t + 0.5D - e) / d;
            if (u * u < 1.0D) {
              for (int v = o; v <= r; ++v) {
                double w = ((double) v + 0.5D - g) / d;
                if (u * u + w * w < 1.0D) {
                  for (int aa = p; aa <= s; ++aa) {
                    double ab = ((double) aa + 0.5D - h) / d;
                    if (u * u + w * w + ab * ab < 1.0D && !world.isOutOfHeightLimit(v)) {
                      int ac = t - x + (v - y) * horizontalSize + (aa - z) * horizontalSize * verticalSize;
                      if (!bitSet.get(ac)) {
                        bitSet.set(ac);
                        mutable.set(t, v, aa);
                        if (world.isValidForSetBlock(mutable)) {
                          ChunkSection chunkSection = chunkSectionCache.getSection(mutable);
                          if (chunkSection != null) {
                            int ad = ChunkSectionPos.getLocalCoord(t);
                            int ae = ChunkSectionPos.getLocalCoord(v);
                            int af = ChunkSectionPos.getLocalCoord(aa);
                            BlockState blockState = chunkSection.getBlockState(ad, ae, af);
                            Iterator var57 = config.targets.iterator();

                            while (var57.hasNext()) {
                              OreFeatureConfig.Target target = (OreFeatureConfig.Target) var57.next();
                              Objects.requireNonNull(chunkSectionCache);
                              if (shouldPlace(blockState, chunkSectionCache::getBlockState, random, config, target, mutable)) {
                                chunkSection.setBlockState(ad, ae, af, target.state, false);
                                ++i;
                                if (t == q) {
                                  //                                                   world.setBlockState(mutable, Blocks.PUMPKIN.getDefaultState(), 1);
                                  //                                                   world.setBlockState(mutable.offset(Direction.NORTH), Blocks.PUMPKIN.getDefaultState(), 1);
                                  //                                                   world.setBlockState(mutable.offset(Direction.EAST), Blocks.PUMPKIN.getDefaultState(), 1);
                                  //                                                   world.setBlockState(mutable.offset(Direction.NORTH).offset(Direction.EAST), Blocks.PUMPKIN.getDefaultState(), 1);
                                  //                                                   world.setBlockState(mutable.up(), Blocks.PUMPKIN.getDefaultState(), 1);
                                  //                                                   world.setBlockState(mutable.offset(Direction.NORTH).up(), Blocks.PUMPKIN.getDefaultState(), 1);
                                  //                                                   world.setBlockState(mutable.offset(Direction.EAST).up(), Blocks.PUMPKIN.getDefaultState(), 1);
                                  //                                                   world.setBlockState(mutable.offset(Direction.NORTH).offset(Direction.EAST).up(), Blocks.PUMPKIN.getDefaultState(), 1);
                                }
                                break;
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    } catch (Throwable var60) {
      try {
        chunkSectionCache.close();
      } catch (Throwable var59) {
        var60.addSuppressed(var59);
      }

      throw var60;
    }

    chunkSectionCache.close();
    return i > 0;
  }

  public static boolean shouldPlace(BlockState state, Function<BlockPos, BlockState> posToState, Random random, OreFeatureConfig config, OreFeatureConfig.Target target,
      BlockPos.Mutable pos) {
    if (!target.target.test(state, random)) {
      return false;
    } else if (shouldNotDiscard(random, config.discardOnAirChance)) {
      return true;
    } else {
      return !isExposedToAir(posToState, pos);
    }
  }

  protected static boolean shouldNotDiscard(Random random, float chance) {
    if (chance <= 0.0F) {
      return true;
    } else if (chance >= 1.0F) {
      return false;
    } else {
      return random.nextFloat() >= chance;
    }
  }
}
