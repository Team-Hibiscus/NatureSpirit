package net.hibiscus.naturespirit.world.carver;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.CarverDebugConfig;
import net.minecraft.world.gen.heightprovider.HeightProvider;

public class ReplaceableRavineCarverConfig extends CarverConfig {

  public static final Codec<ReplaceableRavineCarverConfig> RAVINE_CODEC = RecordCodecBuilder.create((instance) -> {
    return instance.group(CarverConfig.CONFIG_CODEC.forGetter((replaceableRavineCarverConfig) -> {
      return replaceableRavineCarverConfig;
    }), FloatProvider.VALUE_CODEC.fieldOf("vertical_rotation").forGetter((config) -> {
      return config.verticalRotation;
    }), ReplaceableRavineCarverConfig.Shape.CODEC.fieldOf("shape").forGetter((config) -> {
      return config.shape;
    })).apply(instance, ReplaceableRavineCarverConfig::new);
  });
  public final FloatProvider verticalRotation;
  public final ReplaceableRavineCarverConfig.Shape shape;

  public ReplaceableRavineCarverConfig(float probability, HeightProvider y, FloatProvider yScale, YOffset lavaLevel, CarverDebugConfig debugConfig,
      RegistryEntryList<Block> replaceable, FloatProvider verticalRotation, ReplaceableRavineCarverConfig.Shape shape) {
    super(probability, y, yScale, lavaLevel, debugConfig, replaceable);
    this.verticalRotation = verticalRotation;
    this.shape = shape;
  }

  public ReplaceableRavineCarverConfig(CarverConfig config, FloatProvider verticalRotation, ReplaceableRavineCarverConfig.Shape shape) {
    this(config.probability, config.y, config.yScale, config.lavaLevel, config.debugConfig, config.replaceable, verticalRotation, shape);
  }

  public static class Shape {

    public static final Codec<ReplaceableRavineCarverConfig.Shape> CODEC = RecordCodecBuilder.create((instance) -> {
      return instance.group(FloatProvider.VALUE_CODEC.fieldOf("distance_factor").forGetter((shape) -> {
        return shape.distanceFactor;
      }), FloatProvider.VALUE_CODEC.fieldOf("thickness").forGetter((shape) -> {
        return shape.thickness;
      }), Codecs.NONNEGATIVE_INT.fieldOf("width_smoothness").forGetter((shape) -> {
        return shape.widthSmoothness;
      }), FloatProvider.VALUE_CODEC.fieldOf("horizontal_radius_factor").forGetter((shape) -> {
        return shape.horizontalRadiusFactor;
      }), Codec.FLOAT.fieldOf("vertical_radius_default_factor").forGetter((shape) -> {
        return shape.verticalRadiusDefaultFactor;
      }), Codec.FLOAT.fieldOf("vertical_radius_center_factor").forGetter((shape) -> {
        return shape.verticalRadiusCenterFactor;
      })).apply(instance, ReplaceableRavineCarverConfig.Shape::new);
    });
    public final FloatProvider distanceFactor;
    public final FloatProvider thickness;
    public final int widthSmoothness;
    public final FloatProvider horizontalRadiusFactor;
    public final float verticalRadiusDefaultFactor;
    public final float verticalRadiusCenterFactor;

    public Shape(FloatProvider distanceFactor, FloatProvider thickness, int widthSmoothness, FloatProvider horizontalRadiusFactor, float verticalRadiusDefaultFactor,
        float verticalRadiusCenterFactor) {
      this.widthSmoothness = widthSmoothness;
      this.horizontalRadiusFactor = horizontalRadiusFactor;
      this.verticalRadiusDefaultFactor = verticalRadiusDefaultFactor;
      this.verticalRadiusCenterFactor = verticalRadiusCenterFactor;
      this.distanceFactor = distanceFactor;
      this.thickness = thickness;
    }
  }
}
