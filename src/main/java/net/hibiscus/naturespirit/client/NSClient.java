package net.hibiscus.naturespirit.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hibiscus.naturespirit.client.render.CheeseArrowEntityRenderer;
import net.hibiscus.naturespirit.client.render.NSEntityModelLayers;
import net.hibiscus.naturespirit.client.render.PizzaBlockEntityRenderer;
import net.hibiscus.naturespirit.client.render.PizzaToppingModel;
import net.hibiscus.naturespirit.registration.NSEntityTypes;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSParticleTypes;
import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.block.Block;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.particle.SuspendParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;

@Environment(EnvType.CLIENT)
public class NSClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    BlockEntityRendererFactories.register(NSMiscBlocks.PIZZA_BLOCK_ENTITY_TYPE, PizzaBlockEntityRenderer::new);

    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getGrassColor(blockAndTintGetter,
            blockState.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER ? blockPos.down() : blockPos
        ) : -1, NSMiscBlocks.CATTAIL);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getFoliageColor(blockAndTintGetter,
            blockPos
        ) : -1, NSWoods.SUGI.getLeaves());
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getFoliageColor(blockAndTintGetter,
            blockPos
        ) : -1, NSWoods.MAHOGANY.getLeaves());
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getFoliageColor(blockAndTintGetter,
            blockPos
        ) : -1, NSWoods.LARCH.getLeaves());
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getFoliageColor(blockAndTintGetter,
            blockPos
        ) : -1, NSWoods.ASPEN.getLeaves());
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.LOTUS_STEM);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.LARGE_LUSH_FERN);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.LUSH_FERN);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.POTTED_LUSH_FERN);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getGrassColor(blockAndTintGetter,
            blockPos
        ) : -1, NSMiscBlocks.LOTUS_STEM);
    ColorProviderRegistry.BLOCK.register(
        (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getGrassColor(blockAndTintGetter,
            new BlockPos(blockPos.getX(), -64, blockPos.getZ())
        ) : -1, NSMiscBlocks.LOTUS_FLOWER);

    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), NSWoods.SUGI.getLeaves());
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), NSWoods.LARCH.getLeaves());
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), NSWoods.MAHOGANY.getLeaves());
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), NSWoods.ASPEN.getLeaves());
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getDefaultColor(), NSMiscBlocks.LUSH_FERN);
    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getDefaultColor(), NSMiscBlocks.LARGE_LUSH_FERN);

    NSEntityModelLayers.registerEntityModelLayers();
    EntityModelLayerRegistry.registerModelLayer(NSEntityModelLayers.PIZZA_TOPPING, PizzaToppingModel::getTexturedModelData);

    BlockRenderLayerMap.INSTANCE.putBlock(NSMiscBlocks.PIZZA_BLOCK, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSMiscBlocks.LARGE_CALCITE_BUD, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSMiscBlocks.SMALL_CALCITE_BUD, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSMiscBlocks.CALCITE_CLUSTER, RenderLayer.getCutout());

    BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), NSRegistryHelper.RenderLayerHashMap.values().toArray(new Block[0]));
    BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), NSRegistryHelper.LeavesHashMap.values().toArray(new Block[0]));

    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.COCONUT_THATCH_CARPET, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.COCONUT_THATCH_SLAB, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.COCONUT_THATCH, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.COCONUT_THATCH_STAIRS, RenderLayer.getCutout());

    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.EVERGREEN_THATCH_CARPET, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.EVERGREEN_THATCH_SLAB, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.EVERGREEN_THATCH, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.EVERGREEN_THATCH_STAIRS, RenderLayer.getCutout());

    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.XERIC_THATCH_CARPET, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.XERIC_THATCH_SLAB, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.XERIC_THATCH, RenderLayer.getCutout());
    BlockRenderLayerMap.INSTANCE.putBlock(NSWoods.XERIC_THATCH_STAIRS, RenderLayer.getCutout());

    EntityRendererRegistry.register(NSEntityTypes.CHEESE_ARROW, CheeseArrowEntityRenderer::new);

    ParticleFactoryRegistry.getInstance().register(NSParticleTypes.RED_MAPLE_LEAVES_PARTICLE,
        ((spriteProvider) -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new MapleLeavesParticle(world, x, y, z, spriteProvider))
    );
    ParticleFactoryRegistry.getInstance().register(NSParticleTypes.ORANGE_MAPLE_LEAVES_PARTICLE,
        ((spriteProvider) -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new MapleLeavesParticle(world, x, y, z, spriteProvider))
    );
    ParticleFactoryRegistry.getInstance().register(NSParticleTypes.YELLOW_MAPLE_LEAVES_PARTICLE,
        ((spriteProvider) -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new MapleLeavesParticle(world, x, y, z, spriteProvider))
    );
    ParticleFactoryRegistry.getInstance().register(NSParticleTypes.MILK_PARTICLE, SuspendParticle.Factory::new);
//    ParticleFactoryRegistry.getInstance().register(CALCITE_BUBBLE_PARTICLE, CalciteBubbleParticle.BubbleFactory::new);
  }
}

