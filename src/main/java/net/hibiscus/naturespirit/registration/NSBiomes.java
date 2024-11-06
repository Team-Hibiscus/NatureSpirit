package net.hibiscus.naturespirit.registration;


import java.util.HashMap;
import net.hibiscus.naturespirit.NatureSpirit;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class NSBiomes {

  public static HashMap<String, RegistryKey<Biome>> BiomesHashMap = new HashMap<>();
  public static final RegistryKey<Biome> SUGI_FOREST = register("sugi_forest");
  public static final RegistryKey<Biome> WINDSWEPT_SUGI_FOREST = register("windswept_sugi_forest");
  public static final RegistryKey<Biome> BLOOMING_SUGI_FOREST = register("blooming_sugi_forest");
  public static final RegistryKey<Biome> LAVENDER_FIELDS = register("lavender_fields");
  public static final RegistryKey<Biome> MARSH = register("marsh");
  public static final RegistryKey<Biome> BAMBOO_WETLANDS = register("bamboo_wetlands");
  public static final RegistryKey<Biome> WISTERIA_FOREST = register("wisteria_forest");
  public static final RegistryKey<Biome> REDWOOD_FOREST = register("redwood_forest");
  public static final RegistryKey<Biome> SNOWY_REDWOOD_FOREST = register("snowy_redwood_forest");
  public static final RegistryKey<Biome> ASPEN_FOREST = register("aspen_forest");
  public static final RegistryKey<Biome> MAPLE_WOODLANDS = register("maple_woodlands");
  public static final RegistryKey<Biome> GOLDEN_WILDS = register("golden_wilds");
  public static final RegistryKey<Biome> MARIGOLD_MEADOWS = register("marigold_meadows");
  public static final RegistryKey<Biome> FIR_FOREST = register("fir_forest");
  public static final RegistryKey<Biome> SNOWY_FIR_FOREST = register("snowy_fir_forest");
  public static final RegistryKey<Biome> CYPRESS_FIELDS = register("cypress_fields");
  public static final RegistryKey<Biome> CARNATION_FIELDS = register("carnation_fields");
  public static final RegistryKey<Biome> STRATIFIED_DESERT = register("stratified_desert");
  public static final RegistryKey<Biome> BLOOMING_DUNES = register("blooming_dunes");
  public static final RegistryKey<Biome> LIVELY_DUNES = register("lively_dunes");
  public static final RegistryKey<Biome> DRYLANDS = register("drylands");
  public static final RegistryKey<Biome> WOODED_DRYLANDS = register("wooded_drylands");
  public static final RegistryKey<Biome> XERIC_PLAINS = register("xeric_plains");
  public static final RegistryKey<Biome> WHITE_CLIFFS = register("white_cliffs");
  public static final RegistryKey<Biome> PRAIRIE = register("prairie");
  public static final RegistryKey<Biome> OAK_SAVANNA = register("oak_savanna");
  public static final RegistryKey<Biome> HEATHER_FIELDS = register("heather_fields");
  public static final RegistryKey<Biome> TUNDRA = register("tundra");
  public static final RegistryKey<Biome> ALPINE_CLEARINGS = register("alpine_clearings");
  public static final RegistryKey<Biome> ALPINE_HIGHLANDS = register("alpine_highlands");
  public static final RegistryKey<Biome> CONIFEROUS_COVERT = register("coniferous_covert");
  public static final RegistryKey<Biome> BOREAL_TAIGA = register("boreal_taiga");
  public static final RegistryKey<Biome> TROPICAL_SHORES = register("tropical_shores");
  public static final RegistryKey<Biome> TROPICAL_WOODS = register("tropical_woods");
  public static final RegistryKey<Biome> SPARSE_TROPICAL_WOODS = register("sparse_tropical_woods");
  public static final RegistryKey<Biome> TROPICAL_BASIN = register("tropical_basin");
  public static final RegistryKey<Biome> ARID_SAVANNA = register("arid_savanna");
  public static final RegistryKey<Biome> SCORCHED_DUNES = register("scorched_dunes");
  public static final RegistryKey<Biome> FLOWERING_SHRUBLAND = register("flowering_shrubland");
  public static final RegistryKey<Biome> SHRUBLAND = register("shrubland");
  public static final RegistryKey<Biome> ARID_HIGHLANDS = register("arid_highlands");
  public static final RegistryKey<Biome> SHRUBBY_HIGHLANDS = register("shrubby_highlands");
  public static final RegistryKey<Biome> WOODY_HIGHLANDS = register("woody_highlands");
  public static final RegistryKey<Biome> RED_PEAKS = register("red_peaks");
  public static final RegistryKey<Biome> DUSTY_SLOPES = register("dusty_slopes");
  public static final RegistryKey<Biome> SNOWCAPPED_RED_PEAKS = register("snowcapped_red_peaks");
  public static final RegistryKey<Biome> SLEETED_SLOPES = register("sleeted_slopes");
  public static final RegistryKey<Biome> BLOOMING_HIGHLANDS = register("blooming_highlands");
  public static final RegistryKey<Biome> CHAPARRAL = register("chaparral");
  public static final RegistryKey<Biome> FLORAL_RIDGES = register("floral_ridges");

  private static RegistryKey<Biome> register(String name) {
    System.out.println("Registered Resource Key for biome: " + name);
    RegistryKey<Biome> registryKey = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(NatureSpirit.MOD_ID, name));
    BiomesHashMap.put(name, registryKey);
    return registryKey;
  }

  public static void registerBiomes() {
  }
}
