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

public class ReplaceableRavineCarver extends Carver<ReplaceableRavineCarverConfig> {

  public ReplaceableRavineCarver(Codec<ReplaceableRavineCarverConfig> codec) {
    super(codec);
  }

  public boolean shouldCarve(ReplaceableRavineCarverConfig replaceableRavineCarverConfig, Random random) {
    return random.nextFloat() <= replaceableRavineCarverConfig.probability;
  }

  public boolean carve(CarverContext carverContext, ReplaceableRavineCarverConfig replaceableRavineCarverConfig, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> function,
      Random random, AquiferSampler aquiferSampler, ChunkPos chunkPos, CarvingMask carvingMask) {
    int i = (this.getBranchFactor() * 2 - 1) * 16;
    double d = chunkPos.getOffsetX(random.nextInt(16));
    int j = replaceableRavineCarverConfig.y.get(random, carverContext);
    double e = chunkPos.getOffsetZ(random.nextInt(16));
    float f = random.nextFloat() * 6.2831855F;
    float g = replaceableRavineCarverConfig.verticalRotation.get(random);
    double h = replaceableRavineCarverConfig.yScale.get(random);
    float k = replaceableRavineCarverConfig.shape.thickness.get(random);
    int l = (int) ((float) i * replaceableRavineCarverConfig.shape.distanceFactor.get(random));
    boolean m = false;
    this.carveRavine(carverContext, replaceableRavineCarverConfig, chunk, function, random.nextLong(), aquiferSampler, d, j, e, k, f, g, 0, l, h, carvingMask);
    return true;
  }

  private void carveRavine(CarverContext context, ReplaceableRavineCarverConfig config, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> posToBiome, long seed,
      AquiferSampler aquiferSampler, double x, double y, double z, float width, float yaw, float pitch, int branchStartIndex, int branchCount, double yawPitchRatio,
      CarvingMask mask) {
    Random random = Random.create(seed);
    float[] fs = this.createHorizontalStretchFactors(context, config, random);
    float f = 0.0F;
    float g = 0.0F;

    for (int i = branchStartIndex; i < branchCount; ++i) {
      double d = 1.5 + (double) (MathHelper.sin((float) i * 3.1415927F / (float) branchCount) * width);
      double e = d * yawPitchRatio;
      d *= config.shape.horizontalRadiusFactor.get(random);
      e = this.getVerticalScale(config, random, e, (float) branchCount, (float) i);
      float h = MathHelper.cos(pitch);
      float j = MathHelper.sin(pitch);
      x += MathHelper.cos(yaw) * h;
      y += j;
      z += MathHelper.sin(yaw) * h;
      pitch *= 0.7F;
      pitch += g * 0.05F;
      yaw += f * 0.05F;
      g *= 0.8F;
      f *= 0.5F;
      g += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
      f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
      if (random.nextInt(4) != 0) {
        if (!canCarveBranch(chunk.getPos(), x, z, i, branchCount, width)) {
          return;
        }

        this.carveRegion(context, config, chunk, posToBiome, aquiferSampler, x, y, z, d, e, mask, (contextx, scaledRelativeX, scaledRelativeY, scaledRelativeZ, yx) -> {
          return this.isPositionExcluded(contextx, fs, scaledRelativeX, scaledRelativeY, scaledRelativeZ, yx);
        });
      }
    }

  }

  protected boolean carveAtPoint(CarverContext context, ReplaceableRavineCarverConfig config, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> posToBiome, CarvingMask mask,
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
  private BlockState getState(CarverContext context, ReplaceableRavineCarverConfig config, BlockPos pos, AquiferSampler sampler, Chunk chunk) {
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

  private float[] createHorizontalStretchFactors(CarverContext context, ReplaceableRavineCarverConfig config, Random random) {
    int i = context.getHeight();
    float[] fs = new float[i];
    float f = 1.0F;

    for (int j = 0; j < i; ++j) {
      if (j == 0 || random.nextInt(config.shape.widthSmoothness) == 0) {
        f = 1.0F + random.nextFloat() * random.nextFloat();
      }

      fs[j] = f * f;
    }

    return fs;
  }

  private double getVerticalScale(ReplaceableRavineCarverConfig config, Random random, double pitch, float branchCount, float branchIndex) {
    float f = 1.0F - MathHelper.abs(0.5F - branchIndex / branchCount) * 2.0F;
    float g = config.shape.verticalRadiusDefaultFactor + config.shape.verticalRadiusCenterFactor * f;
    return (double) g * pitch * (double) MathHelper.nextBetween(random, 0.75F, 1.0F);
  }

  private boolean isPositionExcluded(CarverContext context, float[] horizontalStretchFactors, double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
    int i = y - context.getMinY();
    return (scaledRelativeX * scaledRelativeX + scaledRelativeZ * scaledRelativeZ) * (double) horizontalStretchFactors[i - 1] + scaledRelativeY * scaledRelativeY / 6.0 >= 1.0;
  }
}
