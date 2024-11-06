package net.hibiscus.naturespirit.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.hibiscus.naturespirit.NatureSpirit;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public class NSWorldGenerator extends FabricDynamicRegistryProvider {

  public NSWorldGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
    entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
    entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
    entries.addAll(registries.getWrapperOrThrow(RegistryKeys.BIOME));
  }

  @Override
  public String getName() {
    return NatureSpirit.MOD_ID;
  }
}
