package net.hibiscus.naturespirit.world.carver;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.densityfunction.DensityFunction;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.jetbrains.annotations.Nullable;

public class ReplaceableCaveCarver extends Carver<ReplaceableCaveCarverConfig> {

  public ReplaceableCaveCarver(Codec<ReplaceableCaveCarverConfig> codec) {
    super(codec);
  }

  public boolean shouldCarve(ReplaceableCaveCarverConfig replaceableCaveCarverConfig, Random random) {
    return random.nextFloat() <= replaceableCaveCarverConfig.probability;
  }

  public boolean carve(CarverContext carverContext, ReplaceableCaveCarverConfig replaceableCaveCarverConfig, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> function,
      Random random, AquiferSampler aquiferSampler, ChunkPos chunkPos, CarvingMask carvingMask) {
    int i = ChunkSectionPos.getBlockCoord(this.getBranchFactor() * 2 - 1);
    int j = random.nextInt(random.nextInt(random.nextInt(this.getMaxCaveCount()) + 1) + 1);

    for (int k = 0; k < j; ++k) {
      double d = chunkPos.getOffsetX(random.nextInt(16));
      double e = replaceableCaveCarverConfig.y.get(random, carverContext);
      double f = chunkPos.getOffsetZ(random.nextInt(16));
      double g = replaceableCaveCarverConfig.horizontalRadiusMultiplier.get(random);
      double h = replaceableCaveCarverConfig.verticalRadiusMultiplier.get(random);
      double l = replaceableCaveCarverConfig.floorLevel.get(random);
      Carver.SkipPredicate skipPredicate = (contextx, scaledRelativeX, scaledRelativeY, scaledRelativeZ, y) -> {
        return isPositionExcluded(scaledRelativeX, scaledRelativeY, scaledRelativeZ, l);
      };
      int m = 1;
      float o;
      if (random.nextInt(4) == 0) {
        double n = replaceableCaveCarverConfig.yScale.get(random);
        o = 1.0F + random.nextFloat() * 6.0F;
        this.carveCave(carverContext, replaceableCaveCarverConfig, chunk, function, aquiferSampler, d, e, f, o, n, carvingMask, skipPredicate);
        m += random.nextInt(4);
      }

      for (int p = 0; p < m; ++p) {
        float q = random.nextFloat() * 6.2831855F;
        o = (random.nextFloat() - 0.5F) / 4.0F;
        float r = this.getTunnelSystemWidth(random);
        int s = i - random.nextInt(i / 4);
        this.carveTunnels(carverContext, replaceableCaveCarverConfig, chunk, function, random.nextLong(), aquiferSampler, d, e, f, g, h, r, q, o, 0, s,
            this.getTunnelSystemHeightWidthRatio(), carvingMask, skipPredicate);
      }
    }

    return true;
  }

  protected boolean carveAtPoint(CarverContext context, ReplaceableCaveCarverConfig config, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> posToBiome, CarvingMask mask,
      BlockPos.Mutable pos, BlockPos.Mutable tmp, AquiferSampler aquiferSampler, MutableBoolean replacedGrassy) {
    BlockState blockState = chunk.getBlockState(pos);
    if (blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isOf(Blocks.MYCELIUM) || blockState.isOf(NSMiscBlocks.RED_MOSS_BLOCK)) {
      replacedGrassy.setTrue();
    }

    if (!this.canAlwaysCarveBlock(config, blockState) && !isDebug(config)) {
      return false;
    } else {
      BlockState blockState2 = this.getState(context, config, pos, aquiferSampler, chunk);
      if (blockState2 == null) {
        return false;
      } else {
        chunk.setBlockState(pos, blockState2, false);
        if (aquiferSampler.needsFluidTick() && !blockState2.getFluidState().isEmpty()) {
          chunk.markBlockForPostProcessing(pos);
        }

        if (replacedGrassy.isTrue()) {
          tmp.set(pos, Direction.DOWN);
          if (chunk.getBlockState(tmp).isOf(Blocks.DIRT)) {
            context.applyMaterialRule(posToBiome, chunk, tmp, !blockState2.getFluidState().isEmpty()).ifPresent((state) -> {
              chunk.setBlockState(tmp, state, false);
              if (!state.getFluidState().isEmpty()) {
                chunk.markBlockForPostProcessing(tmp);
              }

            });
          }
        }

        return true;
      }
    }
  }

  @Nullable
  private BlockState getState(CarverContext context, ReplaceableCaveCarverConfig config, BlockPos pos, AquiferSampler sampler, Chunk chunk) {
    if (pos.getY() <= config.lavaLevel.getY(context)) {
      return LAVA.getBlockState();
    } else {
      BlockState blockState = sampler.apply(new DensityFunction.UnblendedNoisePos(pos.getX(), pos.getY(), pos.getZ()), 0.0);
      if (blockState == null) {
        return isDebug(config) ? config.debugConfig.getBarrierState() : Blocks.ICE.getDefaultState();
      } else {
        return isDebug(config) ? getDebugState(config, blockState) : (
            chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) == pos.getY() ? Blocks.ICE.getDefaultState() : blockState);
      }
    }
  }

  private static BlockState getDebugState(CarverConfig config, BlockState state) {
    if (state.isOf(Blocks.AIR)) {
      return config.debugConfig.getAirState();
    } else if (state.isOf(Blocks.WATER)) {
      BlockState blockState = config.debugConfig.getWaterState();
      return blockState.contains(Properties.WATERLOGGED) ? blockState.with(Properties.WATERLOGGED, true) : blockState;
    } else {
      return state.isOf(Blocks.LAVA) ? config.debugConfig.getLavaState() : state;
    }
  }

  private static boolean isDebug(CarverConfig config) {
    return config.debugConfig.isDebugMode();
  }

  protected int getMaxCaveCount() {
    return 15;
  }

  protected float getTunnelSystemWidth(Random random) {
    float f = random.nextFloat() * 2.0F + random.nextFloat();
    if (random.nextInt(10) == 0) {
      f *= random.nextFloat() * random.nextFloat() * 3.0F + 1.0F;
    }

    return f;
  }

  protected double getTunnelSystemHeightWidthRatio() {
    return 1.0;
  }

  protected void carveCave(CarverContext context, ReplaceableCaveCarverConfig config, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> posToBiome,
      AquiferSampler aquiferSampler, double d, double e, double f, float g, double h, CarvingMask mask, Carver.SkipPredicate skipPredicate) {
    double i = 1.5 + (double) (MathHelper.sin(1.5707964F) * g);
    double j = i * h;
    this.carveRegion(context, config, chunk, posToBiome, aquiferSampler, d + 1.0, e, f, i, j, mask, skipPredicate);
  }

  protected void carveTunnels(CarverContext context, ReplaceableCaveCarverConfig config, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> posToBiome, long seed,
      AquiferSampler aquiferSampler, double x, double y, double z, double horizontalScale, double verticalScale, float width, float yaw, float pitch, int branchStartIndex,
      int branchCount, double yawPitchRatio, CarvingMask mask, Carver.SkipPredicate skipPredicate) {
    Random random = Random.create(seed);
    int i = random.nextInt(branchCount / 2) + branchCount / 4;
    boolean bl = random.nextInt(6) == 0;
    float f = 0.0F;
    float g = 0.0F;

    for (int j = branchStartIndex; j < branchCount; ++j) {
      double d = 1.5 + (double) (MathHelper.sin(3.1415927F * (float) j / (float) branchCount) * width);
      double e = d * yawPitchRatio;
      float h = MathHelper.cos(pitch);
      x += MathHelper.cos(yaw) * h;
      y += MathHelper.sin(pitch);
      z += MathHelper.sin(yaw) * h;
      pitch *= bl ? 0.92F : 0.7F;
      pitch += g * 0.1F;
      yaw += f * 0.1F;
      g *= 0.9F;
      f *= 0.75F;
      g += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
      f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
      if (j == i && width > 1.0F) {
        this.carveTunnels(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x, y, z, horizontalScale, verticalScale, random.nextFloat() * 0.5F + 0.5F,
            yaw - 1.5707964F, pitch / 3.0F, j, branchCount, 1.0, mask, skipPredicate);
        this.carveTunnels(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x, y, z, horizontalScale, verticalScale, random.nextFloat() * 0.5F + 0.5F,
            yaw + 1.5707964F, pitch / 3.0F, j, branchCount, 1.0, mask, skipPredicate);
        return;
      }

      if (random.nextInt(4) != 0) {
        if (!canCarveBranch(chunk.getPos(), x, z, j, branchCount, width)) {
          return;
        }

        this.carveRegion(context, config, chunk, posToBiome, aquiferSampler, x, y, z, d * horizontalScale, e * verticalScale, mask, skipPredicate);
      }
    }

  }

  private static boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, double floorY) {
    if (scaledRelativeY <= floorY) {
      return true;
    } else {
      return scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 1.0;
    }
  }
}
