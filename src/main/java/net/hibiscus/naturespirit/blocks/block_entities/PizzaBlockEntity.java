package net.hibiscus.naturespirit.blocks.block_entities;

import java.util.ArrayList;
import net.hibiscus.naturespirit.NatureSpirit;
import net.hibiscus.naturespirit.blocks.PizzaBlock;
import net.hibiscus.naturespirit.registration.NSDataComponents;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;
import net.hibiscus.naturespirit.registration.NSTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PizzaBlockEntity extends BlockEntity {

  public ArrayList<PizzaToppingVariant> toppings = new ArrayList<>();
  public int toppingCount = 0;

  public PizzaBlockEntity(BlockPos pos, BlockState state) {
    super(NSMiscBlocks.PIZZA_BLOCK_ENTITY_TYPE, pos, state);
  }

  @Override
  protected void readComponents(BlockEntity.ComponentsAccess components) {
    super.readComponents(components);
    if (components.get(NSDataComponents.TOPPINGS) != null) {
      toppings = new ArrayList<>(components.get(NSDataComponents.TOPPINGS));
    }
    this.toppingCount = toppings != null ? toppings.size() : 0;
  }

  @Override
  protected void addComponents(ComponentMap.Builder componentMapBuilder) {
    super.addComponents(componentMapBuilder);
    componentMapBuilder.add(NSDataComponents.TOPPINGS, toppings);
  }

  @Override
  public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.writeNbt(nbt, registryLookup);
    nbt.put("toppings", (NbtElement)NSDataComponents.TOPPINGS.getCodec().encodeStart(NbtOps.INSTANCE, this.toppings).getOrThrow());
  }

  @Override
  public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
    super.readNbt(nbt, registryLookup);
    this.toppings.clear();
    if (nbt.contains("toppings")) {
      NSDataComponents.TOPPINGS.getCodec().parse(NbtOps.INSTANCE, nbt.get("toppings")).resultOrPartial().ifPresent((list) -> toppings.addAll(list));
    }
  }

  public boolean canPlaceTopping(ItemStack itemStack, World world, PizzaBlockEntity pizzaBlockEntity) {
    Identifier itemId = Registries.ITEM.getId(itemStack.getItem());
    PizzaToppingVariant toppingVariant = getVariantFromItem(itemId, world);
    boolean bl = pizzaBlockEntity.getCachedState().get(PizzaBlock.BITES) == 0 && pizzaBlockEntity.toppingCount < 4 && !(itemStack.isIn(NSTags.Items.DISABLED_PIZZA_TOPPINGS))
        && toppingVariant != null && !toppings.contains(toppingVariant);
    if (bl) {
      toppings.add(toppingVariant);
    }
    return bl;
  }

  @Nullable
  public static PizzaToppingVariant getVariantFromItem(Identifier itemId, World world) {
    for (PizzaToppingVariant pizzaToppingVariant : world.getRegistryManager().get(NatureSpirit.PIZZA_TOPPING_VARIANT)) {
      if (pizzaToppingVariant.itemId().equals(itemId)) {
        return pizzaToppingVariant;
      }
    }
    return null;
  }

  @Nullable
  @Override
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
    return createNbt(registryLookup);
  }
}
