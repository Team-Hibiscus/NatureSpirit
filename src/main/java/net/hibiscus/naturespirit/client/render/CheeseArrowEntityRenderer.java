package net.hibiscus.naturespirit.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.entity.CheeseArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CheeseArrowEntityRenderer extends ProjectileEntityRenderer<CheeseArrowEntity> {

  public static final Identifier TEXTURE = Identifier.of(NatureSpirit.MOD_ID, "textures/entity/projectiles/cheese_arrow.png");

  public CheeseArrowEntityRenderer(EntityRendererFactory.Context context) {
    super(context);
  }

  @Override
  public Identifier getTexture(CheeseArrowEntity spectralArrowEntity) {
    return TEXTURE;
  }
}
