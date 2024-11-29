package net.hibiscus.naturespirit;

import java.io.IOException;
import java.util.Optional;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.hibiscus.naturespirit.blocks.block_entities.PizzaToppingVariant;
import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.mixin.StatsTypeAccessor;
import net.hibiscus.naturespirit.registration.*;
import net.hibiscus.naturespirit.util.NSCauldronBehavior;
import net.hibiscus.naturespirit.util.NSEvents;
import net.hibiscus.naturespirit.util.NSVillagers;
import net.minecraft.entity.passive.CatVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NatureSpirit implements ModInitializer {

	public static final String MOD_ID = "natures_spirit";
	public static final Logger LOGGER = LoggerFactory.getLogger("Nature's Spirit");
	public static final Identifier EAT_PIZZA_SLICE = StatsTypeAccessor.registerNew("eat_pizza_slice", StatFormatter.DEFAULT);
	public static final Identifier EAT_CHEESE = StatsTypeAccessor.registerNew("eat_cheese", StatFormatter.DEFAULT);
	public static final RegistryKey<Registry<PizzaToppingVariant>> PIZZA_TOPPING_VARIANT = RegistryKey.ofRegistry(Identifier.of(MOD_ID, "pizza_topping_variant"));

	@Override
	public void onInitialize() {
		Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer("natures_spirit");
		try {
			NSConfig.main();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (modContainer.isPresent()) {
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_vanilla_trees"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_vanilla_trees"),
					NSConfig.vanilla_trees_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_flower_forest"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_flower_forest"),
					NSConfig.flower_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_birch_forest"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_birch_forest"),
					NSConfig.birch_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_jungle"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_jungle"),
					NSConfig.jungle_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_swamp"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_swamp"),
					NSConfig.swamp_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_desert"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_desert"),
					NSConfig.desert_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_badlands"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_badlands"),
					NSConfig.badlands_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_mountain_biomes"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_mountain_biomes"),
					NSConfig.mountain_biomes_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_savannas"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_savannas"),
					NSConfig.savanna_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_dark_forest"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_dark_forest"),
					NSConfig.dark_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "modified_windswept_hills"), modContainer.get(),
					Text.translatable("pack.natures_spirit.modified_windswept_hills"),
					NSConfig.windswept_hills_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "improved_biome_sizes"), modContainer.get(),
					Text.translatable("pack.natures_spirit.improved_biome_sizes"),
					NSConfig.improved_biome_sizes_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
			);
			ResourceManagerHelper.registerBuiltinResourcePack(
					Identifier.of(MOD_ID, "better_leaves_compatibility"), modContainer.get(),
					Text.translatable("pack.natures_spirit.bushy_leaves_compatibility"),
					ResourcePackActivationType.NORMAL
			);

			if (modContainer.isPresent()) {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_vanilla_trees"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_vanilla_trees"),
						NSConfig.vanilla_trees_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_flower_forest"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_flower_forest"),
						NSConfig.flower_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_birch_forest"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_birch_forest"),
						NSConfig.birch_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_jungle"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_jungle"),
						NSConfig.jungle_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_swamp"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_swamp"),
						NSConfig.swamp_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_desert"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_desert"),
						NSConfig.desert_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_badlands"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_badlands"),
						NSConfig.badlands_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_mountain_biomes"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_mountain_biomes"),
						NSConfig.mountain_biomes_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_savannas"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_savannas"),
						NSConfig.savanna_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_dark_forest"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_dark_forest"),
						NSConfig.dark_forest_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "modified_windswept_hills"), modContainer.get(),
						Text.translatable("pack.natures_spirit.modified_windswept_hills"),
						NSConfig.windswept_hills_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "improved_biome_sizes"), modContainer.get(),
						Text.translatable("pack.natures_spirit.improved_biome_sizes"),
						NSConfig.improved_biome_sizes_toggle ? ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
				);
				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "better_leaves_compatibility"), modContainer.get(),
						Text.translatable("pack.natures_spirit.bushy_leaves_compatibility"),
						ResourcePackActivationType.NORMAL
				);

				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "plank_consistency"), modContainer.get(),
						Text.translatable("pack.natures_spirit.plank_consistency"),
						ResourcePackActivationType.NORMAL
				);

				ResourceManagerHelper.registerBuiltinResourcePack(
						Identifier.of(MOD_ID, "emissive_ores_compatibility"), modContainer.get(),
						Text.translatable("pack.natures_spirit.emissive_ores_compatibility"),
						ResourcePackActivationType.NORMAL
				);
			}

			NSSounds.registerSounds();
			NSDataComponents.registerDataComponents();
			NSEntityTypes.registerEntityTypes();
			NSVillagers.registerVillagers();
			NSParticleTypes.registerParticleTypes();
			NSBiomes.registerBiomes();
			NSWoods.registerWoods();
			NSColoredBlocks.registerColoredBlocks();
			NSMiscBlocks.registerMiscBlocks();
			NSEvents.registerEvents();
			NSWorldGen.registerWorldGen();
			NSItemGroups.registerItemGroup();
			NSCriteria.registerCriteria();
			NSCauldronBehavior.registerBehavior();
			DynamicRegistries.registerSynced(PIZZA_TOPPING_VARIANT, PizzaToppingVariant.CODEC);

			if (NSConfig.cat_variants) {
				Registry.register(Registries.CAT_VARIANT, "trans", new CatVariant(Identifier.of(MOD_ID, "textures/entity/cat/trans" + ".png")));
			}
		}
	}
}
