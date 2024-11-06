package net.hibiscus.naturespirit.util;

import static net.hibiscus.naturespirit.NatureSpirit.MOD_ID;
import static net.minecraft.village.VillagerType.BIOME_TO_TYPE;
import static net.minecraft.village.VillagerType.DESERT;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerTypeHelper;
import net.hibiscus.naturespirit.registration.NSBiomes;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerType;

public class NSVillagers {

  public static final VillagerType WISTERIA = VillagerTypeHelper.register(Identifier.of(MOD_ID, "wisteria"));
  public static final VillagerType CYPRESS = VillagerTypeHelper.register(Identifier.of(MOD_ID, "cypress"));
  public static final VillagerType ADOBE = VillagerTypeHelper.register(Identifier.of(MOD_ID, "adobe"));
  public static final VillagerType COCONUT = VillagerTypeHelper.register(Identifier.of(MOD_ID, "coconut"));

  public static void registerVillagers() {
    BIOME_TO_TYPE.put(NSBiomes.WISTERIA_FOREST, WISTERIA);
    BIOME_TO_TYPE.put(NSBiomes.CYPRESS_FIELDS, CYPRESS);
    BIOME_TO_TYPE.put(NSBiomes.CARNATION_FIELDS, CYPRESS);
    BIOME_TO_TYPE.put(NSBiomes.LAVENDER_FIELDS, CYPRESS);
    BIOME_TO_TYPE.put(NSBiomes.STRATIFIED_DESERT, ADOBE);
    BIOME_TO_TYPE.put(NSBiomes.LIVELY_DUNES, ADOBE);
    BIOME_TO_TYPE.put(NSBiomes.BLOOMING_DUNES, ADOBE);
    BIOME_TO_TYPE.put(NSBiomes.DRYLANDS, DESERT);
    BIOME_TO_TYPE.put(NSBiomes.WOODED_DRYLANDS, DESERT);
    BIOME_TO_TYPE.put(NSBiomes.TROPICAL_SHORES, COCONUT);

    TradeOfferHelper.registerWanderingTraderOffers(1,
        factories -> {
          factories.add(new TradeOffers.SellItemFactory(NSMiscBlocks.RED_MOSS_BLOCK, 1, 2, 5, 1));
          factories.add(new TradeOffers.SellItemFactory(NSMiscBlocks.BLUEBELL.getFlowerBlock(), 1, 1, 12, 1));
          factories.add(new TradeOffers.SellItemFactory(NSMiscBlocks.ANEMONE.getFlowerBlock(), 1, 1, 12, 1));
          factories.add(new TradeOffers.SellItemFactory(NSMiscBlocks.LOTUS_FLOWER_ITEM, 1, 1, 12, 1));
        }
    );

  }

}
