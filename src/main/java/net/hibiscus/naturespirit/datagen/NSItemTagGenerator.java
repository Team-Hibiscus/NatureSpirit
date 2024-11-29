package net.hibiscus.naturespirit.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;

import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSTags;
import net.hibiscus.naturespirit.registration.NSTags.Blocks;
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
      copy(woodSet.getBlockLogsTag(), woodSet.getItemLogsTag());
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
    copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
    copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
    copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
    copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
    copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
    copy(NSTags.Blocks.STRIPPED_LOGS, NSTags.Items.STRIPPED_LOGS);
    copy(Blocks.ALLUAUDIA_BLOCKS, NSTags.Items.ALLUAUDIA_BLOCKS);
    copy(BlockTags.PLANKS, ItemTags.PLANKS);
    copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
    copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
    copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
    copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
    copy(BlockTags.LEAVES, ItemTags.LEAVES);
    copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
    copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
    copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
    copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
    copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
    copy(BlockTags.SAND, ItemTags.SAND);
    copy(BlockTags.SLABS, ItemTags.SLABS);
    copy(BlockTags.STAIRS, ItemTags.STAIRS);
    copy(BlockTags.WALLS, ItemTags.WALLS);
    copy(BlockTags.COAL_ORES, ItemTags.COAL_ORES);
    copy(BlockTags.COPPER_ORES, ItemTags.COPPER_ORES);
    copy(BlockTags.DIAMOND_ORES, ItemTags.DIAMOND_ORES);
    copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
    copy(BlockTags.EMERALD_ORES, ItemTags.EMERALD_ORES);
    copy(BlockTags.IRON_ORES, ItemTags.IRON_ORES);
    copy(BlockTags.LAPIS_ORES, ItemTags.LAPIS_ORES);
    copy(BlockTags.REDSTONE_ORES, ItemTags.REDSTONE_ORES);
    getOrCreateTagBuilder(ItemTags.ARROWS).add(CHEESE_ARROW);
    getOrCreateTagBuilder(ItemTags.SMELTS_TO_GLASS).add(PINK_SAND.asItem());
    getOrCreateTagBuilder(NSTags.Items.EVERGREEN_LEAVES).add(
        NSWoods.FIR.getLeaves().asItem(),
        NSWoods.REDWOOD.getLeaves().asItem(),
        NSWoods.LARCH.getLeaves().asItem(),
        NSWoods.CEDAR.getLeaves().asItem(),
        NSWoods.SUGI.getLeaves().asItem(),
        Items.SPRUCE_LEAVES
    );
    getOrCreateTagBuilder(NSTags.Items.XERIC_LEAVES).add(
        NSWoods.GHAF.getLeaves().asItem(),
        NSWoods.OLIVE.getLeaves().asItem(),
        NSWoods.PALO_VERDE.getLeaves().asItem(),
        NSWoods.JOSHUA.getLeaves().asItem(),
        Items.ACACIA_LEAVES
    );
    getOrCreateTagBuilder(ItemTags.DIRT).add(SANDY_SOIL.asItem(), CHERT.getBase().asItem(), RED_MOSS_BLOCK.asItem());
    getOrCreateTagBuilder(NSTags.Items.COCONUT_ITEMS).add(
        NSWoods.COCONUT_BLOCK.asItem(),
        NSWoods.COCONUT_HALF,
        NSWoods.COCONUT_SHELL
    );
  }
}
