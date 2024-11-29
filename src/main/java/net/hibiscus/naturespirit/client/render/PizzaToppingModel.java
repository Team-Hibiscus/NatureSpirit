package net.hibiscus.naturespirit.client.render;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class PizzaToppingModel extends Model {

  public final ModelPart slice0;
  public final ModelPart slice1;
  public final ModelPart slice2;
  public final ModelPart slice3;

  public PizzaToppingModel(ModelPart root) {
    super(RenderLayer::getEntityCutout);
    this.slice0 = root.getChild("slice0");
    this.slice1 = root.getChild("slice1");
    this.slice2 = root.getChild("slice2");
    this.slice3 = root.getChild("slice3");
  }

  public static TexturedModelData getTexturedModelData() {
    ModelData modelData = new ModelData();
    ModelPartData modelPartData = modelData.getRoot();
    modelPartData.addChild("slice0", ModelPartBuilder.create().uv(-7, 11).cuboid(3.0F, 2.04F, 3.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.0F)),
        ModelTransform.pivot(0.0F, 0.0F, 0.0F));
    modelPartData.addChild("slice1", ModelPartBuilder.create().uv(-2, 11).cuboid(3.0F, 2.04F, 3.0F, 5.0F, 0.0F, 10.0F, new Dilation(0.0F)).uv(8, 16)
        .cuboid(8.0F, 2.04F, 3.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
    modelPartData.addChild("slice2", ModelPartBuilder.create().uv(-2, 11).cuboid(3.0F, 2.04F, 3.0F, 5.0F, 0.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
    modelPartData.addChild("slice3", ModelPartBuilder.create().uv(3, 11).cuboid(3.0F, 2.04F, 8.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
    return TexturedModelData.of(modelData, 32, 32);
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
  }
}
