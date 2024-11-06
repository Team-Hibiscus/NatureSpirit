package net.hibiscus.naturespirit.registration;

import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.advancements.CoconutHitCriterion;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class NSCriteria {

  public static final CoconutHitCriterion COCONUT_HIT_CRITERION = register("coconut_hit", new CoconutHitCriterion());

  public static void registerCriteria() {
  }

  public static <T extends Criterion<?>> T register(String id, T criterion) {
    return Registry.register(Registries.CRITERION, Identifier.of(NatureSpirit.MOD_ID, id), criterion);
  }
}
