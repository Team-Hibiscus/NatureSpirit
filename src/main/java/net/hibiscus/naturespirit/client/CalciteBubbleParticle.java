//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.hibiscus.naturespirit.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CalciteBubbleParticle extends SpriteBillboardParticle {

  private final SpriteProvider spriteProvider;

  CalciteBubbleParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
    super(world, x, y, z, 0.0, 0.0, 0.0);
    this.velocityMultiplier = 0.96F;
    this.spriteProvider = spriteProvider;
    float f = 2.5F;
    this.velocityX *= 0.02000000149011612;
    this.velocityY *= 0.02000000149011612;
    this.velocityZ *= 0.02000000149011612;
    this.velocityX += velocityX;
    this.velocityY += velocityY;
    this.velocityZ += velocityZ;
    float g = 1.0F - (float) (Math.random() * 0.30000001192092896);
    this.red = g;
    this.green = g;
    this.blue = g;
    this.scale *= .9525F;
    int i = (int) (8.0 / (Math.random() * 0.8 + 0.3));
    this.maxAge = (int) Math.max((float) i * 2.5F, 1.0F);
    this.collidesWithWorld = false;
    this.setSpriteForAge(spriteProvider);
  }

  public ParticleTextureSheet getType() {
    return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
  }

  public float getSize(float tickDelta) {
    return this.scale * MathHelper.clamp(((float) this.age + tickDelta) / (float) this.maxAge * 32.0F, 0.0F, 1.0F);
  }

  public void tick() {
    super.tick();
    if (!this.dead) {
      this.setSpriteForAge(this.spriteProvider);
      PlayerEntity playerEntity = this.world.getClosestPlayer(this.x, this.y, this.z, 2.0, false);
      if (playerEntity != null) {
        double d = playerEntity.getY();
        if (this.y > d) {
          this.y += (d - this.y) * 0.005;
          this.velocityY += (playerEntity.getVelocity().y - this.velocityY) * 0.005;
          this.setPos(this.x, this.y, this.z);
        }
      }
    }

  }


  @Environment(EnvType.CLIENT)
  public static class BubbleFactory implements ParticleFactory<SimpleParticleType> {

    private final SpriteProvider spriteProvider;

    public BubbleFactory(SpriteProvider spriteProvider) {
      this.spriteProvider = spriteProvider;
    }

    @Override
    public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
      return new CalciteBubbleParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
    }
  }
}
