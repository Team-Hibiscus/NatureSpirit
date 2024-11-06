package net.hibiscus.naturespirit.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;

import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.NotNull;

public class NSDataGen implements DataGeneratorEntrypoint {

  public static final String[] DYE_COLORS = {
      "white", "light_gray", "gray", "black", "brown", "red", "orange", "yellow", "lime",
      "green", "cyan", "light_blue", "blue", "purple", "magenta", "pink"
  };

  @Override
  public void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
    FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

    pack.addProvider(NSWorldGenerator::new);
    pack.addProvider(NSModelGenerator::new);
    pack.addProvider(NSLangGenerator::new);
    pack.addProvider(NSRecipeGenerator::new);
    pack.addProvider(NSBlockLootTableProvider::new);
    NSBlockTagGenerator blockTagProvider = pack.addProvider(NSBlockTagGenerator::new);
    pack.addProvider((output, registries) -> new NSItemTagGenerator(output, registries, blockTagProvider));
    System.out.println("Initialized Data Generator");
  }

  @Override
  public void buildRegistry(@NotNull RegistryBuilder registryBuilder) {
    registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, NSConfiguredFeatures::bootstrap);
    registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, NSPlacedFeatures::bootstrap);
    System.out.println("Built Registry");
  }


  @Override
  public String getEffectiveModId() {
    return MOD_ID;
  }
}

