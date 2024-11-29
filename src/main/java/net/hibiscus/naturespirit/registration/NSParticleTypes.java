package net.hibiscus.naturespirit.registration;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.hibiscus.naturespirit.NatureSpirit;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class NSParticleTypes {

  public static final SimpleParticleType RED_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
  public static final SimpleParticleType ORANGE_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
  public static final SimpleParticleType YELLOW_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
  public static final SimpleParticleType MILK_PARTICLE = FabricParticleTypes.simple(false);
  public static final SimpleParticleType CALCITE_BUBBLE_PARTICLE = FabricParticleTypes.simple(false);

  public static void registerParticleTypes() {
    Registry.register(Registries.PARTICLE_TYPE, Identifier.of(NatureSpirit.MOD_ID, "red_maple_leaves"), RED_MAPLE_LEAVES_PARTICLE);
    Registry.register(Registries.PARTICLE_TYPE, Identifier.of(NatureSpirit.MOD_ID, "orange_maple_leaves"), ORANGE_MAPLE_LEAVES_PARTICLE);
    Registry.register(Registries.PARTICLE_TYPE, Identifier.of(NatureSpirit.MOD_ID, "yellow_maple_leaves"), YELLOW_MAPLE_LEAVES_PARTICLE);
    Registry.register(Registries.PARTICLE_TYPE, Identifier.of(NatureSpirit.MOD_ID, "milk"), MILK_PARTICLE);
    Registry.register(Registries.PARTICLE_TYPE, Identifier.of(NatureSpirit.MOD_ID, "calcite_bubble"), CALCITE_BUBBLE_PARTICLE);
  }
}
