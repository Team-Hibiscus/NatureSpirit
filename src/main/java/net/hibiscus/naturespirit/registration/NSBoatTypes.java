package net.hibiscus.naturespirit.registration;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;

public class NSBoatTypes {

  private static final Map<BoatEntity.Type, Pair<Item, Item>> BOAT_TYPES_TO_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

  public static void addBoatTypeItems(BoatEntity.Type type, Item boatItem, Item chestBoatItem) {
    BOAT_TYPES_TO_ITEMS.put(type, new Pair<>(boatItem, chestBoatItem));
  }

  public static Optional<Item> getBoatItem(BoatEntity.Type type) {
    return Optional.ofNullable(BOAT_TYPES_TO_ITEMS.getOrDefault(type, new Pair<>(null, null)).getLeft());
  }

  public static Optional<Item> getChestBoatItem(BoatEntity.Type type) {
    return Optional.ofNullable(BOAT_TYPES_TO_ITEMS.getOrDefault(type, new Pair<>(null, null)).getRight());
  }

  public static @NotNull List<Item> getAllBoatItems() {
    List<Item> items = new ArrayList<>();
    BOAT_TYPES_TO_ITEMS.forEach((type, pair) -> {
      items.add(pair.getLeft());
      items.add(pair.getRight());
    });
    return items;
  }

  public static void setBoatTypeBaseItem(BoatEntity.Type type, Block baseBlock) {
    type.baseBlock = baseBlock;
  }

  public static BoatEntity.Type REDWOOD;
  public static BoatEntity.Type SUGI;
  public static BoatEntity.Type WISTERIA;
  public static BoatEntity.Type FIR;
  public static BoatEntity.Type WILLOW;
  public static BoatEntity.Type ASPEN;
  public static BoatEntity.Type MAPLE;
  public static BoatEntity.Type CYPRESS;
  public static BoatEntity.Type OLIVE;
  public static BoatEntity.Type JOSHUA;
  public static BoatEntity.Type GHAF;
  public static BoatEntity.Type PALO_VERDE;
  public static BoatEntity.Type COCONUT;
  public static BoatEntity.Type CEDAR;
  public static BoatEntity.Type LARCH;
  public static BoatEntity.Type MAHOGANY;
  public static BoatEntity.Type SAXAUL;

  //CREDIT TO nyuppo/fabric-boat-example ON GITHUB
  static {
    BoatEntity.Type.values();
  }

  public static void init() {
  }
}
