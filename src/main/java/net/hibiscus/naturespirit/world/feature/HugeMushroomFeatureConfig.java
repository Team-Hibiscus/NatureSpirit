package net.hibiscus.naturespirit.world.feature;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class HugeMushroomFeatureConfig implements FeatureConfig {

  public static final Codec<HugeMushroomFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
    return instance.group(BlockStateProvider.TYPE_CODEC.fieldOf("cap_provider").forGetter((hugeMushroomFeatureConfig) -> {
          return hugeMushroomFeatureConfig.capProvider;
        }), BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter((hugeMushroomFeatureConfig) -> {
          return hugeMushroomFeatureConfig.stemProvider;
        }), Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter((hugeMushroomFeatureConfig) -> {
          return hugeMushroomFeatureConfig.foliageRadius;
        }), Codec.intRange(0, 32).fieldOf("base_height").forGetter((placer) -> {
          return placer.baseHeight;
        }), Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter((placer) -> {
          return placer.firstRandomHeight;
        }), Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter((placer) -> {
          return placer.secondRandomHeight;
        })

    ).apply(instance, HugeMushroomFeatureConfig::new);
  });
  public final BlockStateProvider capProvider;
  public final BlockStateProvider stemProvider;
  public final int foliageRadius;
  public final int baseHeight;
  public final int firstRandomHeight;
  public final int secondRandomHeight;

  public HugeMushroomFeatureConfig(BlockStateProvider capProvider, BlockStateProvider stemProvider, int foliageRadius, int baseHeight, int firstRandomHeight,
      int secondRandomHeight) {
    this.capProvider = capProvider;
    this.stemProvider = stemProvider;
    this.foliageRadius = foliageRadius;
    this.baseHeight = baseHeight;
    this.firstRandomHeight = firstRandomHeight;
    this.secondRandomHeight = secondRandomHeight;
  }
}
