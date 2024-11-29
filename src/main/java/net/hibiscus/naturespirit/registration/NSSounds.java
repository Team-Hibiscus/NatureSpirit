package net.hibiscus.naturespirit.registration;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class NSSounds {

  public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_ASPEN = registerReference("music.overworld.aspen");
  public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_MAPLE = registerReference("music.overworld.maple");
  public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_WISTERIA = registerReference("music.overworld.wisteria");
  public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_REDWOOD = registerReference("music.overworld.redwood");
  public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_DESERT = registerReference("music.overworld.desert");
  public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_ARID = registerReference("music.overworld.arid");
  public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_TROPICAL = registerReference("music.overworld.tropical");
  public static final RegistryEntry.Reference<SoundEvent> MUSIC_OVERWORLD_ALPINE = registerReference("music.overworld.alpine");

  private static RegistryEntry.Reference<SoundEvent> registerReference(String id) {
    return registerReference(Identifier.of(MOD_ID, id));
  }

  private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id) {
    return registerReference(id, id);
  }

  private static RegistryEntry.Reference<SoundEvent> registerReference(Identifier id, Identifier soundId) {
    return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
  }

  public static void registerSounds() {
  }
}
