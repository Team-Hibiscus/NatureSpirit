package net.hibiscus.naturespirit.blocks.block_entities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.hibiscus.naturespirit.NatureSpirit;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

public record PizzaToppingVariant(
    int hunger, float saturation, Identifier itemId, Identifier texturePath, String translationKey
) {

  public static final Codec<PizzaToppingVariant> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
          Codecs.rangedInt(0, 20).fieldOf("hunger").forGetter(PizzaToppingVariant::hunger),
          Codecs.POSITIVE_FLOAT.fieldOf("saturation").forGetter(PizzaToppingVariant::saturation),
          Identifier.CODEC.fieldOf("item").forGetter(PizzaToppingVariant::itemId),
          Identifier.CODEC.fieldOf("texture_path").forGetter(PizzaToppingVariant::texturePath),
          Codecs.NON_EMPTY_STRING.fieldOf("translation_key").forGetter(PizzaToppingVariant::translationKey)
      ).apply(instance, PizzaToppingVariant::new)
  );
  public static final PacketCodec<ByteBuf, PizzaToppingVariant> PACKET_CODEC;
  public static final Codec<RegistryEntry<PizzaToppingVariant>> ENTRY_CODEC;
  public static final PacketCodec<RegistryByteBuf, RegistryEntry<PizzaToppingVariant>> ENTRY_PACKET_CODEC;

  public PizzaToppingVariant(int hunger, float saturation, Identifier itemId, Identifier texturePath, String translationKey) {
    this.hunger = hunger;
    this.saturation = saturation;
    this.itemId = itemId;
    this.texturePath = texturePath;
    this.translationKey = translationKey;
  }

  public int hunger() {
    return this.hunger;
  }

  public float saturation() {
    return this.saturation;
  }

  public Identifier texturePath() {
    return this.texturePath;
  }

  public Identifier itemId() {
    return this.itemId;
  }

  static {
    PACKET_CODEC = PacketCodec.tuple(PacketCodecs.VAR_INT, PizzaToppingVariant::hunger, PacketCodecs.FLOAT, PizzaToppingVariant::saturation, Identifier.PACKET_CODEC,
        PizzaToppingVariant::itemId, Identifier.PACKET_CODEC, PizzaToppingVariant::texturePath, PacketCodecs.STRING, PizzaToppingVariant::translationKey, PizzaToppingVariant::new);
    ENTRY_CODEC = RegistryElementCodec.of(NatureSpirit.PIZZA_TOPPING_VARIANT, CODEC);
    ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(NatureSpirit.PIZZA_TOPPING_VARIANT, PACKET_CODEC);
  }
}
