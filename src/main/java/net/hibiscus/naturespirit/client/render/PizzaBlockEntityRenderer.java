package net.hibiscus.naturespirit.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hibiscus.naturespirit.blocks.PizzaBlock;
import net.hibiscus.naturespirit.blocks.block_entities.PizzaBlockEntity;
import net.hibiscus.naturespirit.blocks.block_entities.PizzaToppingVariant;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class PizzaBlockEntityRenderer implements BlockEntityRenderer<PizzaBlockEntity> {

  private final PizzaToppingModel pizzaToppingModel;

  public PizzaBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    this.pizzaToppingModel = new PizzaToppingModel(ctx.getLayerModelPart(NSEntityModelLayers.PIZZA_TOPPING));
  }

  @Override
  public void render(PizzaBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    BlockState cachedState = entity.getCachedState();
    if (cachedState.getBlock() instanceof PizzaBlock) {
      int shape = cachedState.get(PizzaBlock.BITES);
      ModelPart model;
      switch (shape) {
        case 0 -> model = pizzaToppingModel.slice0;
        case 1 -> model = pizzaToppingModel.slice1;
        case 2 -> model = pizzaToppingModel.slice2;
        default -> model = pizzaToppingModel.slice3;
      }
      for (PizzaToppingVariant pizzaToppingVariant : entity.toppings) {
        VertexConsumer vertexConsumer = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, pizzaToppingVariant.texturePath()).getVertexConsumer(vertexConsumers,
            RenderLayer::getEntityCutout);
        model.render(matrices, vertexConsumer, light, overlay, -1);
      }
    }
  }
}
