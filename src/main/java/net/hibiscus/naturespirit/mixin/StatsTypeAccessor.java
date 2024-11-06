package net.hibiscus.naturespirit.mixin;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Stats.class)
public interface StatsTypeAccessor {

  @Invoker("register")
  static Identifier registerNew(String id, StatFormatter formatter) {
    throw new AssertionError();
  }
}
