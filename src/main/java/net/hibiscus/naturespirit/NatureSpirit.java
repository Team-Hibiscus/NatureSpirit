package net.hibiscus.naturespirit;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.entity.NSBoatEntity;
import net.hibiscus.naturespirit.items.NSBoatDispenserBehavior;
import net.hibiscus.naturespirit.mixin.StatsTypeAccessor;
import net.hibiscus.naturespirit.registration.*;
import net.hibiscus.naturespirit.util.NSCauldronBehavior;
import net.hibiscus.naturespirit.util.NSEvents;
import net.hibiscus.naturespirit.util.NSVillagers;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.passive.CatVariant;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class NatureSpirit implements ModInitializer {

   public static final String MOD_ID = "natures_spirit";
   public static final Logger LOGGER = LoggerFactory.getLogger("Nature's Spirit");
   public static final Identifier EAT_PIZZA_SLICE = StatsTypeAccessor.registerNew("eat_pizza_slice", StatFormatter.DEFAULT);
   public static final Identifier EAT_CHEESE = StatsTypeAccessor.registerNew("eat_cheese", StatFormatter.DEFAULT);
   public static final DefaultParticleType RED_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
   public static final DefaultParticleType ORANGE_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
   public static final DefaultParticleType YELLOW_MAPLE_LEAVES_PARTICLE = FabricParticleTypes.simple(false);
   public static final DefaultParticleType MILK_PARTICLE = FabricParticleTypes.simple(false);
   public static final DefaultParticleType CALCITE_BUBBLE_PARTICLE = FabricParticleTypes.simple(false);

   @Override public void onInitialize() {

      Optional <ModContainer> modContainer = FabricLoader.getInstance().getModContainer("natures_spirit");
      try {
         NSConfig.main();
      }
      catch(IOException e) {
         throw new RuntimeException(e);
      }
      if(modContainer.isPresent()) {
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_vanilla_trees"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_vanilla_trees"),
                 NSConfig.vanilla_trees_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_flower_forest"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_flower_forest"),
                 NSConfig.flower_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_birch_forest"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_birch_forest"),
                 NSConfig.birch_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_jungle"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_jungle"),
                 NSConfig.jungle_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_swamp"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_swamp"),
                 NSConfig.swamp_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_desert"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_desert"),
                 NSConfig.desert_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_badlands"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_badlands"),
                 NSConfig.badlands_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_mountain_biomes"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_mountain_biomes"),
                 NSConfig.mountain_biomes_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_savannas"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_savannas"),
                 NSConfig.savanna_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_dark_forest"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_dark_forest"),
                 NSConfig.dark_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "modified_windswept_hills"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.modified_windswept_hills"),
                 NSConfig.windswept_hills_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "plank_consistency"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.plank_consistency"),
                 ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "better_leaves_compatibility"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.bushy_leaves_compatibility"),
                 ResourcePackActivationType.NORMAL
         );
         ResourceManagerHelper.registerBuiltinResourcePack(
                 new Identifier(MOD_ID, "emissive_ores_compatibility"), modContainer.get(),
                 Text.translatable("pack.natures_spirit.emissive_ores_compatibility"),
                 ResourcePackActivationType.NORMAL
         );
         if(FabricLoader.getInstance().isModLoaded("dye_depot")) {
            ResourceManagerHelper.registerBuiltinResourcePack(
                    new Identifier(MOD_ID, "dye_depot_compatibility_dat"), modContainer.get(),
                    Text.translatable("pack.natures_spirit.dye_depot_compatibility"),
                    ResourcePackActivationType.ALWAYS_ENABLED
            );
            ResourceManagerHelper.registerBuiltinResourcePack(
                    new Identifier(MOD_ID, "dye_depot_compatibility_res"), modContainer.get(),
                    Text.translatable("pack.natures_spirit.dye_depot_compatibility"),
                    ResourcePackActivationType.ALWAYS_ENABLED
            );
         }
         if(FabricLoader.getInstance().isModLoaded("mint")) {
            ResourceManagerHelper.registerBuiltinResourcePack(
                    new Identifier(MOD_ID, "mint_compatibility_dat"), modContainer.get(),
                    Text.translatable("pack.natures_spirit.mint_compatibility"),
                    ResourcePackActivationType.ALWAYS_ENABLED
            );
            ResourceManagerHelper.registerBuiltinResourcePack(
                    new Identifier(MOD_ID, "mint_compatibility_res"), modContainer.get(),
                    Text.translatable("pack.natures_spirit.mint_compatibility"),
                    ResourcePackActivationType.ALWAYS_ENABLED
            );
         }
      }
      Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "red_maple_leaves"), RED_MAPLE_LEAVES_PARTICLE);
      Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "orange_maple_leaves"), ORANGE_MAPLE_LEAVES_PARTICLE);
      Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "yellow_maple_leaves"), YELLOW_MAPLE_LEAVES_PARTICLE);
      Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "milk"), MILK_PARTICLE);
      Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "calcite_bubble"), CALCITE_BUBBLE_PARTICLE);
      NSEntityTypes.registerEntityTypes();
      NSBiomes.registerBiomes();
      NSItemGroups.registerItemGroup();
      NSWoods.registerWoods();
      NSColoredBlocks.registerColoredBlocks();
      NSVillagers.registerVillagers();
      if (FabricLoader.getInstance().isModLoaded("dye_depot")) {
         NSDyeDepotCompatibility.registerColoredBlocks();
      }
      if (FabricLoader.getInstance().isModLoaded("mint")) {
         NSMintCompatibility.registerColoredBlocks();
      }
      NSEvents.registerEvents();
      NSWorldGen.registerWorldGen();
      NSSounds.registerSounds();
      NSCriteria.registerCriteria();
      NSCauldronBehavior.registerBehavior();

      for(NSBoatEntity.HibiscusBoat boat : NSBoatEntity.HibiscusBoat.values()) {
         DispenserBlock.registerBehavior(boat.boat(), new NSBoatDispenserBehavior(boat, false));
         DispenserBlock.registerBehavior(boat.chestBoat(), new NSBoatDispenserBehavior(boat, true));
      }

      Registry.register(Registries.CAT_VARIANT, "trans", new CatVariant(new Identifier(MOD_ID, "textures/entity/cat/trans" + ".png")));
   }
}
