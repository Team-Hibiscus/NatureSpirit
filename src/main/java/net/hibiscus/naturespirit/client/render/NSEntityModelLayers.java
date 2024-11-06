package net.hibiscus.naturespirit.client.render;

import net.hibiscus.naturespirit.NatureSpirit;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class NSEntityModelLayers {

  public static void registerEntityModelLayers() {
  }

  public static final EntityModelLayer PIZZA_TOPPING = register("pizza", "toppings");

  private static EntityModelLayer registerMain(String id) {
    return register(id, "main");
  }

  private static EntityModelLayer register(String id, String layer) {
    return create(id, layer);
  }

  private static EntityModelLayer create(String id, String layer) {
    return new EntityModelLayer(Identifier.of(NatureSpirit.MOD_ID, id), layer);
  }
}
