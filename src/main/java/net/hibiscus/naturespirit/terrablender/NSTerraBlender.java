package net.hibiscus.naturespirit.terrablender;

import java.io.IOException;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;

import net.hibiscus.naturespirit.config.NSConfig;
import net.hibiscus.naturespirit.world.NSSurfaceRules;
import net.minecraft.util.Identifier;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class NSTerraBlender implements TerraBlenderApi {

  @Override
  public void onTerraBlenderInitialized() {
    {
      try {
        NSConfig.main();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      Regions.register(new TerraFeraxRegion(Identifier.of(MOD_ID, "terra_ferax"), NSConfig.terra_ferax_weight));
      Regions.register(new TerraSolarisRegion(Identifier.of(MOD_ID, "terra_solaris"), NSConfig.terra_solaris_weight));
      Regions.register(new TerraFlavaRegion(Identifier.of(MOD_ID, "terra_flava"), NSConfig.terra_flava_weight));
      Regions.register(new TerraMaterRegion(Identifier.of(MOD_ID, "terra_mater"), NSConfig.terra_mater_weight));
      Regions.register(new TerraLaetaRegion(Identifier.of(MOD_ID, "terra_laeta"), NSConfig.terra_laeta_weight));
      SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, NSSurfaceRules.makeRules());
    }
  }
}
