package net.hibiscus.naturespirit.mixin.entity.boat;

import java.util.ArrayList;
import java.util.Arrays;
import net.hibiscus.naturespirit.registration.NSBoatTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.vehicle.BoatEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatEntity.Type.class)
public class BoatTypeMixin {

  //CREDIT TO nyuppo/fabric-boat-example ON GITHUB

  @SuppressWarnings("ShadowTarget")
  @Final
  @Shadow
  @Mutable
  private static BoatEntity.Type[] field_7724;

  @SuppressWarnings("InvokerTarget")
  @Invoker("<init>")
  private static BoatEntity.Type newType(String internalName, int internalId, Block baseBlock, String name) {
    throw new AssertionError("Mixin injection failed - Nature's Spirit BoatTypeMixin.");
  }

  @Inject(
      method = "<clinit>",
      at = @At(
          value = "FIELD",
          opcode = Opcodes.PUTSTATIC,
          target = "Lnet/minecraft/entity/vehicle/BoatEntity$Type;field_7724:[Lnet/minecraft/entity/vehicle/BoatEntity$Type;",
          shift = At.Shift.AFTER
      )
  )
  private static void addCustomBoatType(CallbackInfo info) {
    var types = new ArrayList<>(Arrays.asList(field_7724));
    var last = types.get(types.size() - 1);

    var redwood = newType("NATURES_SPIRIT_REDWOOD", last.ordinal() + 1, Blocks.OAK_PLANKS, "natures_spirit_redwood");
    NSBoatTypes.REDWOOD = redwood;
    types.add(redwood);

    var sugi = newType("NATURES_SPIRIT_SUGI", last.ordinal() + 2, Blocks.OAK_PLANKS, "natures_spirit_sugi");
    NSBoatTypes.SUGI = sugi;
    types.add(sugi);

    var wisteria = newType("NATURES_SPIRIT_WISTERIA", last.ordinal() + 3, Blocks.OAK_PLANKS, "natures_spirit_wisteria");
    NSBoatTypes.WISTERIA = wisteria;
    types.add(wisteria);

    var fir = newType("NATURES_SPIRIT_FIR", last.ordinal() + 4, Blocks.OAK_PLANKS, "natures_spirit_fir");
    NSBoatTypes.FIR = fir;
    types.add(fir);

    var willow = newType("NATURES_SPIRIT_WILLOW", last.ordinal() + 5, Blocks.OAK_PLANKS, "natures_spirit_willow");
    NSBoatTypes.WILLOW = willow;
    types.add(willow);

    var aspen = newType("NATURES_SPIRIT_ASPEN", last.ordinal() + 6, Blocks.OAK_PLANKS, "natures_spirit_aspen");
    NSBoatTypes.ASPEN = aspen;
    types.add(aspen);

    var maple = newType("NATURES_SPIRIT_MAPLE", last.ordinal() + 7, Blocks.OAK_PLANKS, "natures_spirit_maple");
    NSBoatTypes.MAPLE = maple;
    types.add(maple);

    var cypress = newType("NATURES_SPIRIT_CYPRESS", last.ordinal() + 8, Blocks.OAK_PLANKS, "natures_spirit_cypress");
    NSBoatTypes.CYPRESS = cypress;
    types.add(cypress);

    var olive = newType("NATURES_SPIRIT_OLIVE", last.ordinal() + 9, Blocks.OAK_PLANKS, "natures_spirit_olive");
    NSBoatTypes.OLIVE = olive;
    types.add(olive);

    var joshua = newType("NATURES_SPIRIT_JOSHUA", last.ordinal() + 10, Blocks.OAK_PLANKS, "natures_spirit_joshua");
    NSBoatTypes.JOSHUA = joshua;
    types.add(joshua);

    var ghaf = newType("NATURES_SPIRIT_GHAF", last.ordinal() + 11, Blocks.OAK_PLANKS, "natures_spirit_ghaf");
    NSBoatTypes.GHAF = ghaf;
    types.add(ghaf);

    var paloVerde = newType("NATURES_SPIRIT_PALO_VERDE", last.ordinal() + 12, Blocks.OAK_PLANKS, "natures_spirit_palo_verde");
    NSBoatTypes.PALO_VERDE = paloVerde;
    types.add(paloVerde);

    var coconut = newType("NATURES_SPIRIT_COCONUT", last.ordinal() + 13, Blocks.OAK_PLANKS, "natures_spirit_coconut");
    NSBoatTypes.COCONUT = coconut;
    types.add(coconut);

    var cedar = newType("NATURES_SPIRIT_CEDAR", last.ordinal() + 14, Blocks.OAK_PLANKS, "natures_spirit_cedar");
    NSBoatTypes.CEDAR = cedar;
    types.add(cedar);

    var larch = newType("NATURES_SPIRIT_LARCH", last.ordinal() + 15, Blocks.OAK_PLANKS, "natures_spirit_larch");
    NSBoatTypes.LARCH = larch;
    types.add(larch);

    var mahogany = newType("NATURES_SPIRIT_MAHOGANY", last.ordinal() + 16, Blocks.OAK_PLANKS, "natures_spirit_mahogany");
    NSBoatTypes.MAHOGANY = mahogany;
    types.add(mahogany);

    var saxaul = newType("NATURES_SPIRIT_SAXAUL", last.ordinal() + 17, Blocks.OAK_PLANKS, "natures_spirit_saxaul");
    NSBoatTypes.SAXAUL = saxaul;
    types.add(saxaul);

    field_7724 = types.toArray(new BoatEntity.Type[0]);
  }

}
