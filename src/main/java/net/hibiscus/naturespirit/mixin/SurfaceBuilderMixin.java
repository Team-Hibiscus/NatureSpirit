package net.hibiscus.naturespirit.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.registration.NSBiomes;
import net.hibiscus.naturespirit.registration.NSWorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.random.RandomSplitter;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.chunk.BlockColumn;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;
import net.minecraft.world.gen.noise.NoiseConfig;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SurfaceBuilder.class)
public class SurfaceBuilderMixin {

  @Final
  @Shadow
  private BlockState defaultState;
  @Unique
  private DoublePerlinNoiseSampler sugiPillarNoise;
  @Unique
  private DoublePerlinNoiseSampler sugiPillarRoofNoise;
  @Unique
  private DoublePerlinNoiseSampler sugiSurfaceNoise;
  @Unique
  private DoublePerlinNoiseSampler stratifiedDesertPillarNoise;
  @Unique
  private DoublePerlinNoiseSampler stratifiedDesertPillarRoofNoise;
  @Unique
  private DoublePerlinNoiseSampler stratifiedDesertSurfaceNoise;

  @Inject(method = "<init>", at = @At(value = "TAIL"))
  private void injectNoise(NoiseConfig noiseConfig, BlockState defaultState, int seaLevel, RandomSplitter randomDeriver, CallbackInfo ci) {
    sugiPillarNoise = noiseConfig.getOrCreateSampler(NSWorldGen.SUGI_PILLAR);
    sugiPillarRoofNoise = noiseConfig.getOrCreateSampler(NSWorldGen.SUGI_PILLAR_ROOF);
    sugiSurfaceNoise = noiseConfig.getOrCreateSampler(NSWorldGen.SUGI_SURFACE);
    stratifiedDesertPillarNoise = noiseConfig.getOrCreateSampler(NSWorldGen.STRATIFIED_DESERT_PILLAR);
    stratifiedDesertPillarRoofNoise = noiseConfig.getOrCreateSampler(NSWorldGen.STRATIFIED_DESERT_PILLAR_ROOF);
    stratifiedDesertSurfaceNoise = noiseConfig.getOrCreateSampler(NSWorldGen.STRATIFIED_DESERT_SURFACE);
  }

  @Inject(method = "buildSurface", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/entry/RegistryEntry;matchesKey(Lnet/minecraft/registry/RegistryKey;)Z", ordinal = 0))
  private void injectPillars(NoiseConfig noiseConfig, BiomeAccess biomeAccess, Registry<Biome> biomeRegistry, boolean useLegacyRandom, HeightContext heightContext,
      Chunk chunk, ChunkNoiseSampler chunkNoiseSampler, MaterialRules.MaterialRule materialRule,
      CallbackInfo ci,
      @Local RegistryEntry<Biome> registryEntry, @Local(ordinal = 2) int k, @Local(ordinal = 3) int l, @Local(ordinal = 4) int m, @Local(ordinal = 5) int n,
      @Local BlockColumn blockColumn) {
    if (NSConfig.sugi_and_stratified_pillars) {
      int o = chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR_WG, k, l) + 1;
      if (registryEntry.matchesKey(NSBiomes.SUGI_FOREST) || registryEntry.matchesKey(NSBiomes.BLOOMING_SUGI_FOREST)) {
        this.placeSugiPillar(blockColumn, m, n, o, chunk);
      }
      if (registryEntry.matchesKey(NSBiomes.STRATIFIED_DESERT) || registryEntry.matchesKey(NSBiomes.LIVELY_DUNES) || registryEntry.matchesKey(NSBiomes.BLOOMING_DUNES)) {
        this.placeStratifiedDesertPillar(blockColumn, m, n, o, chunk);
      }
    }
  }

  @Unique
  private void placeSugiPillar(BlockColumn column, int x, int z, int surfaceY, HeightLimitView chunk) {
    double e = Math.min(Math.abs(sugiSurfaceNoise.sample(x, 0.0, z) * 8.5), sugiPillarRoofNoise.sample((double) x * 0.2, 0.0, (double) z * 0.2) * 12.0);
    if (e > -10.0) {
      double h = Math.abs(sugiPillarNoise.sample((double) x * 0.9, 0.0, (double) z * 0.8) * 2.05);
      double i = 32.0 + Math.min(e * e * 6.75, Math.ceil(h * 30.0) + 48.0);
      int j = MathHelper.floor(i);
      if (surfaceY <= j) {
        int k;
        for (k = j; k >= chunk.getBottomY(); --k) {
          BlockState blockState = column.getState(k);
          if (blockState.isOf(this.defaultState.getBlock())) {
            break;
          }
        }

        for (k = j; k >= chunk.getBottomY() && (column.getState(k).isAir() || column.getState(k).isOf(Blocks.WATER)); --k) {
          column.setState(k, this.defaultState);
        }


      }
    }
  }

  @Unique
  private void placeStratifiedDesertPillar(BlockColumn column, int x, int z, int surfaceY, HeightLimitView chunk) {
    double e = Math.min(Math.abs(stratifiedDesertSurfaceNoise.sample(x, 0.0, z) * 8.5), stratifiedDesertPillarNoise.sample((double) x * 0.2, 0.0, (double) z * 0.2) * 14.0);
    if (!(e <= 0.0)) {
      double h = Math.abs(stratifiedDesertPillarRoofNoise.sample((double) x * 0.75, 0.0, (double) z * 0.75) * 2.25);
      double i = 54.0 + Math.min(e * e * 3.5, Math.ceil(h * 30.0) + 38.0);
      int j = MathHelper.floor(i);
      if (surfaceY <= j) {
        int k;
        for (k = j; k >= chunk.getBottomY(); --k) {
          BlockState blockState = column.getState(k);
          if (blockState.isOf(this.defaultState.getBlock())) {
            break;
          }
        }

        for (k = j; k >= chunk.getBottomY() && (column.getState(k).isAir() || column.getState(k).isOf(Blocks.WATER)); --k) {
          column.setState(k, this.defaultState);
        }

      }
    }
  }

}
