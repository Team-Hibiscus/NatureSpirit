package net.hibiscus.naturespirit.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;

import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

public class NSItemTagGenerator extends FabricTagProvider.ItemTagProvider {

  public NSItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable BlockTagProvider blockTagProvider) {
    super(output, completableFuture, blockTagProvider);
  }


  @Override
  protected void configure(RegistryWrapper.WrapperLookup arg) {

    for (WoodSet woodSet : NSRegistryHelper.WoodHashMap.values()) {
      this.copy(woodSet.getBlockLogsTag(), woodSet.getItemLogsTag());
      getOrCreateTagBuilder(ItemTags.BOATS).add(woodSet.getBoatItem());
      getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(woodSet.getChestBoatItem());
    }

    for (StoneSet stoneSet : NSRegistryHelper.StoneHashMap.values()) {
      if (stoneSet.hasCobbled()) {
        getOrCreateTagBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(stoneSet.getCobbled().asItem());
        getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS).add(stoneSet.getCobbled().asItem());
      }
    }
    getOrCreateTagBuilder(ItemTags.STONE_TOOL_MATERIALS).add(CHERT.getBase().asItem());
    getOrCreateTagBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(CHERT.getBase().asItem());
    this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
    this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
    this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
    this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
    this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
    this.copy(NSTags.Blocks.STRIPPED_LOGS, NSTags.Items.STRIPPED_LOGS);
    this.copy(NSTags.Blocks.ALLUAUDIA_BUNDLES, NSTags.Items.ALLUAUDIA_BUNDLES);
    this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
    this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
    this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
    this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
    this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
    this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
    this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
    this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
    this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
    this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
    this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
    this.copy(BlockTags.SAND, ItemTags.SAND);
    this.copy(BlockTags.SLABS, ItemTags.SLABS);
    this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
    this.copy(BlockTags.WALLS, ItemTags.WALLS);
    this.copy(BlockTags.COAL_ORES, ItemTags.COAL_ORES);
    this.copy(BlockTags.COPPER_ORES, ItemTags.COPPER_ORES);
    this.copy(BlockTags.DIAMOND_ORES, ItemTags.DIAMOND_ORES);
    this.copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
    this.copy(BlockTags.EMERALD_ORES, ItemTags.EMERALD_ORES);
    this.copy(BlockTags.IRON_ORES, ItemTags.IRON_ORES);
    this.copy(BlockTags.LAPIS_ORES, ItemTags.LAPIS_ORES);
    this.copy(BlockTags.REDSTONE_ORES, ItemTags.REDSTONE_ORES);
    this.getOrCreateTagBuilder(ItemTags.ARROWS).add(CHEESE_ARROW);
    this.getOrCreateTagBuilder(ItemTags.SMELTS_TO_GLASS).add(PINK_SAND.asItem());
    this.getOrCreateTagBuilder(NSTags.Items.EVERGREEN_LEAVES).add(
        NSWoods.FIR.getLeaves().asItem(),
        NSWoods.REDWOOD.getLeaves().asItem(),
        NSWoods.LARCH.getLeaves().asItem(),
        NSWoods.CEDAR.getLeaves().asItem(),
        Items.SPRUCE_LEAVES
    );
    this.getOrCreateTagBuilder(NSTags.Items.XERIC_LEAVES).add(
        NSWoods.GHAF.getLeaves().asItem(),
        NSWoods.OLIVE.getLeaves().asItem(),
        NSWoods.PALO_VERDE.getLeaves().asItem(),
        NSWoods.JOSHUA.getLeaves().asItem(),
        Items.ACACIA_LEAVES
    );
    this.getOrCreateTagBuilder(NSTags.Items.COCONUT_ITEMS).add(
        NSWoods.COCONUT_BLOCK.asItem(),
        NSWoods.COCONUT_HALF,
        NSWoods.COCONUT_SHELL
    );
  }
}
