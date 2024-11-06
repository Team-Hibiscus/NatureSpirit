package net.hibiscus.naturespirit.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.hibiscus.naturespirit.registration.NSCriteria;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.entity.Entity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.entity.LootContextPredicateValidator;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public class CoconutHitCriterion extends AbstractCriterion<CoconutHitCriterion.Conditions> {

  public CoconutHitCriterion() {
  }

  @Override
  public Codec<CoconutHitCriterion.Conditions> getConditionsCodec() {
    return CoconutHitCriterion.Conditions.CODEC;
  }

  public void trigger(ServerPlayerEntity player, Entity projectile) {
    LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, projectile);
    this.trigger(player, (conditions) -> conditions.test(lootContext));
  }

  public record Conditions(
      Optional<LootContextPredicate> player, Optional<LootContextPredicate> projectile
  ) implements AbstractCriterion.Conditions {

    public static final Codec<CoconutHitCriterion.Conditions> CODEC = RecordCodecBuilder.create((instance) ->
        instance.group(
            EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
            EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("projectile").forGetter(Conditions::projectile)
        ).apply(instance, Conditions::new));

    public static AdvancementCriterion<CoconutHitCriterion.Conditions> create(Optional<LootContextPredicate> projectile) {
      return NSCriteria.COCONUT_HIT_CRITERION.create(new CoconutHitCriterion.Conditions(Optional.empty(), projectile));
    }

    public boolean test(LootContext projectileContext) {
      return this.projectile.isEmpty() || this.projectile.get().test(projectileContext);
    }

    @Override
    public void validate(@NotNull LootContextPredicateValidator validator) {
      validator.validateEntityPredicate(this.projectile, ".projectile");
    }

    @Override
    public Optional<LootContextPredicate> player() {
      return this.player;
    }

    public Optional<LootContextPredicate> projectile() {
      return this.projectile;
    }
  }
}
