//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.hibiscus.naturespirit.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.CherryLeavesParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
public class MapleLeavesParticle extends CherryLeavesParticle {

  protected MapleLeavesParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
    super(world, x, y, z, spriteProvider);
    float f = this.random.nextBoolean() ? 0.133333F : 0.19999999995F;
    this.scale = f;
    this.setBoundingBoxSpacing(f, f);
    this.velocityMultiplier = 0.78F;
  }
}
