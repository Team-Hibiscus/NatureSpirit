package net.hibiscus.naturespirit.datagen;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.hibiscus.naturespirit.blocks.DesertTurnipStemBlock;
import net.hibiscus.naturespirit.blocks.OliveBranchBlock;
import net.hibiscus.naturespirit.registration.NSBoatTypes;
import net.hibiscus.naturespirit.registration.NSColoredBlocks;
import net.hibiscus.naturespirit.registration.NSMiscBlocks;

import static net.hibiscus.naturespirit.registration.NSMiscBlocks.*;

import net.hibiscus.naturespirit.registration.NSRegistryHelper;
import net.hibiscus.naturespirit.registration.NSWoods;
import net.hibiscus.naturespirit.registration.sets.FlowerSet;
import net.hibiscus.naturespirit.registration.sets.StoneSet;
import net.hibiscus.naturespirit.registration.sets.WoodSet;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;

import static net.minecraft.data.client.BlockStateModelGenerator.*;

import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.MultipartBlockStateSupplier;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.TexturedModel;

import static net.minecraft.data.client.TexturedModel.makeFactory;

import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantSettings.Rotation;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.data.client.When;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

class NSModelGenerator extends FabricModelProvider {

  private static final Model TALL_LARGE_CROSS = block("tall_large_cross", TextureKey.CROSS);
  private static final Model TINTED_TALL_LARGE_CROSS = block("tinted_tall_large_cross", TextureKey.CROSS);
  private static final Model LARGE_CROSS = block("large_cross", TextureKey.CROSS);
  private static final Model TINTED_LARGE_CROSS = block("tinted_large_cross", TextureKey.CROSS);
  private static final Model TALL_CROSS = block("tall_cross", TextureKey.CROSS);
  private static final Model FLOWER_POT_TALL_CROSS = block("flower_pot_tall_cross", TextureKey.PLANT);
  private static final Model FLOWER_POT_LARGE_CROSS = block("flower_pot_large_cross", TextureKey.PLANT);
  private static final Model SUCCULENT = block("succulent", TextureKey.PLANT);
  private static final Model POLYPORE = block("polypore", TextureKey.PLANT);
  private static final Model SUCCULENT_WALL = block("succulent_wall", TextureKey.PLANT);
  private static final Model FLOWER_POT_SUCCULENT = block("flower_pot_succulent", TextureKey.PLANT);
  private static final Model FLOWER_POT_TINTED_LARGE_CROSS = block("tinted_flower_pot_large_cross", TextureKey.PLANT);
  private static final Model CROP = block("crop", TextureKey.CROP);
  private static final Model PAPER_LANTERN = block("template_paper_lantern", TextureKey.TOP, TextureKey.SIDE);
  private static final Model HANGING_PAPER_LANTERN = block("template_hanging_paper_lantern", "_hanging", TextureKey.TOP, TextureKey.SIDE);

  private static final Model AZOLLA_1 = block("template_azolla_1", "_1", TextureKey.PLANT);
  private static final Model AZOLLA_2 = block("template_azolla_2", "_2", TextureKey.PLANT);
  private static final Model AZOLLA_3 = block("template_azolla_3", "_3", TextureKey.PLANT);
  private static final Model AZOLLA_4 = block("template_azolla_4", "_4", TextureKey.PLANT);

  public static final Model[] OLIVE_BRANCH_STAGES = IntStream.range(0, 4).mapToObj((stage) -> minecraftBlock("cross", "_stage" + stage, TextureKey.CROSS)).toArray(Model[]::new);

  public static TextureMap paperLantern(Block block) {
    return (new TextureMap()).put(TextureKey.SIDE, getId(block)).put(TextureKey.TOP, getId(block).withSuffixedPath("_top"));
  }


  public static final TexturedModel.Factory TEXTURED_SUCCULENT = makeFactory(TextureMap::plant, SUCCULENT);
  public static final TexturedModel.Factory TEXTURED_POLYPORE = makeFactory(TextureMap::plant, POLYPORE);
  public static final TexturedModel.Factory TEMPLATE_PAPER_LANTERN = makeFactory(NSModelGenerator::paperLantern, PAPER_LANTERN);
  public static final TexturedModel.Factory TEMPLATE_HANGING_PAPER_LANTERN = makeFactory(NSModelGenerator::paperLantern, HANGING_PAPER_LANTERN);

  public static final TexturedModel.Factory TEXTURED_AZOLLA_1 = makeFactory(TextureMap::plant, AZOLLA_1);
  public static final TexturedModel.Factory TEXTURED_AZOLLA_2 = makeFactory(TextureMap::plant, AZOLLA_2);
  public static final TexturedModel.Factory TEXTURED_AZOLLA_3 = makeFactory(TextureMap::plant, AZOLLA_3);
  public static final TexturedModel.Factory TEXTURED_AZOLLA_4 = makeFactory(TextureMap::plant, AZOLLA_4);



  public NSModelGenerator(FabricDataOutput output) {
    super(output);
  }

  private static Model block(String parent, TextureKey... requiredTextureKeys) {
    return new Model(Optional.of(Identifier.of("natures_spirit", "block/" + parent)), Optional.empty(), requiredTextureKeys);
  }

  private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
    return new Model(Optional.of(Identifier.of("natures_spirit", "block/" + parent)), Optional.of(variant), requiredTextureKeys);
  }
  private static Model minecraftBlock(String parent, String variant, TextureKey... requiredTextureKeys) {
    return new Model(Optional.of(Identifier.of("minecraft", "block/" + parent)), Optional.of(variant), requiredTextureKeys);
  }

  public static Identifier getId(Block block) {
    Identifier identifier = Registries.BLOCK.getId(block);
    return identifier.withPrefixedPath("block/");
  }

  private void createSlab(Block block, Block slab, BlockStateModelGenerator blockStateModelGenerator) {
    Identifier resourceLocation = ModelIds.getBlockModelId(block);
    TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block);
    Identifier resourceLocation2 = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier resourceLocation3 = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(createSlabBlockState(slab, resourceLocation2, resourceLocation3, resourceLocation));
  }

  private void createStairs(Block block, Block stairs, BlockStateModelGenerator blockStateModelGenerator) {
    TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block);
    Identifier resourceLocation = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier resourceLocation2 = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier resourceLocation3 = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(createStairsBlockState(stairs, resourceLocation, resourceLocation2, resourceLocation3));
    blockStateModelGenerator.registerParentedItemModel(stairs, resourceLocation2);
  }

  public void createWoodDoor(Block doorBlock, BlockStateModelGenerator blockStateModelGenerator) {
    TextureMap textureMapping = TextureMap.topBottom(doorBlock);
    Identifier resourceLocation = Models.DOOR_BOTTOM_LEFT.upload(doorBlock, textureMapping, blockStateModelGenerator.modelCollector);
    Identifier resourceLocation2 = Models.DOOR_BOTTOM_LEFT_OPEN.upload(doorBlock, textureMapping, blockStateModelGenerator.modelCollector);
    Identifier resourceLocation3 = Models.DOOR_BOTTOM_RIGHT.upload(doorBlock, textureMapping, blockStateModelGenerator.modelCollector);
    Identifier resourceLocation4 = Models.DOOR_BOTTOM_RIGHT_OPEN.upload(doorBlock, textureMapping, blockStateModelGenerator.modelCollector);
    Identifier resourceLocation5 = Models.DOOR_TOP_LEFT.upload(doorBlock, textureMapping, blockStateModelGenerator.modelCollector);
    Identifier resourceLocation6 = Models.DOOR_TOP_LEFT_OPEN.upload(doorBlock, textureMapping, blockStateModelGenerator.modelCollector);
    Identifier resourceLocation7 = Models.DOOR_TOP_RIGHT.upload(doorBlock, textureMapping, blockStateModelGenerator.modelCollector);
    Identifier resourceLocation8 = Models.DOOR_TOP_RIGHT_OPEN.upload(doorBlock, textureMapping, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.registerItemModel(doorBlock.asItem());
    blockStateModelGenerator.blockStateCollector.accept(createDoorBlockState(doorBlock,
        resourceLocation,
        resourceLocation2,
        resourceLocation3,
        resourceLocation4,
        resourceLocation5,
        resourceLocation6,
        resourceLocation7,
        resourceLocation8
    ));
  }

  public final void createOliveBranch(Block block, BlockStateModelGenerator blockStateModelGenerator) {
    blockStateModelGenerator.registerItemModel(block.asItem());
    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(block)))
        .coordinate(blockStateModelGenerator.createUpDefaultFacingVariantMap()).coordinate(BlockStateVariantMap.create(Properties.AGE_3).register((age) ->
            BlockStateVariant.create().put(VariantSettings.MODEL, OLIVE_BRANCH_STAGES[age].upload(block, TextureMap.cross(TextureMap.getSubId(block, "_stage" + age)), blockStateModelGenerator.modelCollector)))));
  }

  public void createWoodTrapdoor(Block orientableTrapdoorBlock, BlockStateModelGenerator blockStateModelGenerators) {
    TextureMap textureMapping = TextureMap.texture(orientableTrapdoorBlock);
    Identifier resourceLocation = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_TOP.upload(orientableTrapdoorBlock, textureMapping, blockStateModelGenerators.modelCollector);
    Identifier resourceLocation2 = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_BOTTOM.upload(orientableTrapdoorBlock, textureMapping, blockStateModelGenerators.modelCollector);
    Identifier resourceLocation3 = Models.TEMPLATE_ORIENTABLE_TRAPDOOR_OPEN.upload(orientableTrapdoorBlock, textureMapping, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createOrientableTrapdoorBlockState(orientableTrapdoorBlock, resourceLocation, resourceLocation2, resourceLocation3));
    blockStateModelGenerators.registerParentedItemModel(orientableTrapdoorBlock, resourceLocation2);
  }

  public void createWoodFenceGate(Block planks, Block fenceGateBlock, BlockStateModelGenerator blockStateModelGenerator) {
    TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(planks);
    Identifier resourceLocation = Models.TEMPLATE_FENCE_GATE_OPEN.upload(fenceGateBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier resourceLocation2 = Models.TEMPLATE_FENCE_GATE.upload(fenceGateBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier resourceLocation3 = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(fenceGateBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier resourceLocation4 = Models.TEMPLATE_FENCE_GATE_WALL.upload(fenceGateBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(fenceGateBlock,
        resourceLocation,
        resourceLocation2,
        resourceLocation3,
        resourceLocation4,
        true
    ));
  }

  public void createWoodFence(Block planks, Block fenceBlock, BlockStateModelGenerator blockStateModelGenerators) {
    TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(planks);
    Identifier resourceLocation = Models.FENCE_POST.upload(fenceBlock, texturedModel.getTextures(), blockStateModelGenerators.modelCollector);
    Identifier resourceLocation2 = Models.FENCE_SIDE.upload(fenceBlock, texturedModel.getTextures(), blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(fenceBlock, resourceLocation, resourceLocation2));
    Identifier resourceLocation3 = Models.FENCE_INVENTORY.upload(fenceBlock, texturedModel.getTextures(), blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.registerParentedItemModel(fenceBlock, resourceLocation3);
  }

  public void createWoodPressurePlate(Block planks, Block pressurePlateBlock, BlockStateModelGenerator blockStateModelGenerator) {
    TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(planks);
    Identifier resourceLocation = Models.PRESSURE_PLATE_UP.upload(pressurePlateBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier resourceLocation2 = Models.PRESSURE_PLATE_DOWN.upload(pressurePlateBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(pressurePlateBlock, resourceLocation, resourceLocation2));
  }

  public void createWoodSign(Block signBlock, Block wallSignBlock, BlockStateModelGenerator blockStateModelGenerator) {
    TextureMap textureMapping = TextureMap.texture(signBlock);
    Identifier resourceLocation = Models.PARTICLE.upload(signBlock, textureMapping, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(signBlock, resourceLocation));
    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(wallSignBlock, resourceLocation));
    blockStateModelGenerator.registerItemModel(signBlock.asItem());
    blockStateModelGenerator.excludeFromSimpleItemModelGeneration(wallSignBlock);
  }

  public void createWall(Block block, Block wallBlock, BlockStateModelGenerator blockStateModelGenerator) {
    TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block);
    Identifier identifier = Models.TEMPLATE_WALL_POST.upload(wallBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier identifier2 = Models.TEMPLATE_WALL_SIDE.upload(wallBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    Identifier identifier3 = Models.TEMPLATE_WALL_SIDE_TALL.upload(wallBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(wallBlock, identifier, identifier2, identifier3));
    Identifier identifier4 = Models.WALL_INVENTORY.upload(wallBlock, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.registerParentedItemModel(wallBlock, identifier4);
  }

  public void createWoodButton(Block planks, Block buttonBlock, BlockStateModelGenerator blockStateModelGenerators) {
    TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(planks);
    Identifier resourceLocation = Models.BUTTON.upload(buttonBlock, texturedModel.getTextures(), blockStateModelGenerators.modelCollector);
    Identifier resourceLocation2 = Models.BUTTON_PRESSED.upload(buttonBlock, texturedModel.getTextures(), blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(buttonBlock, resourceLocation, resourceLocation2));
    Identifier resourceLocation3 = Models.BUTTON_INVENTORY.upload(buttonBlock, texturedModel.getTextures(), blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.registerParentedItemModel(buttonBlock, resourceLocation3);
  }

  public void createHangingSign(Block strippedLog, Block hangingSign, Block wallHangingSign, BlockStateModelGenerator blockStateModelGenerator) {
    TextureMap textureMap = TextureMap.particle(strippedLog);
    Identifier identifier = Models.PARTICLE.upload(hangingSign, textureMap, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(createSingletonBlockState(hangingSign, identifier));
    blockStateModelGenerator.blockStateCollector.accept(createSingletonBlockState(wallHangingSign, identifier));
    blockStateModelGenerator.registerItemModel(hangingSign.asItem());
    blockStateModelGenerator.excludeFromSimpleItemModelGeneration(wallHangingSign);
  }

  private void generateWoodBlockStateModels(HashMap<String, WoodSet> woods, BlockStateModelGenerator blockStateModelGenerator) {
    for (WoodSet woodSet : woods.values()) {
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.BAMBOO) {
        blockStateModelGenerator.registerLog(woodSet.getLog()).log(woodSet.getLog());
        blockStateModelGenerator.registerLog(woodSet.getStrippedLog()).log(woodSet.getStrippedLog());
      } else if (woodSet.getWoodPreset() == WoodSet.WoodPreset.JOSHUA) {
        blockStateModelGenerator.registerLog(woodSet.getBundle()).log(woodSet.getBundle());
        blockStateModelGenerator.registerLog(woodSet.getStrippedBundle()).log(woodSet.getStrippedBundle());
      } else if (woodSet.hasBark()) {
        blockStateModelGenerator.registerLog(woodSet.getLog()).log(woodSet.getLog()).wood(woodSet.getWood());
        blockStateModelGenerator.registerLog(woodSet.getStrippedLog()).log(woodSet.getStrippedLog()).wood(woodSet.getStrippedWood());
      }
      if (woodSet.hasMosaic()) {
        blockStateModelGenerator.registerSingleton(woodSet.getMosaic(), TexturedModel.CUBE_ALL);
        createSlab(woodSet.getMosaic(), woodSet.getMosaicSlab(), blockStateModelGenerator);
        createStairs(woodSet.getMosaic(), woodSet.getMosaicStairs(), blockStateModelGenerator);
      }
      if (woodSet.getWoodPreset() == WoodSet.WoodPreset.WILLOW) {
        generateVineBlockStateModels(woodSet.getVines(), woodSet.getVinesPlant(), blockStateModelGenerator);
      }
      blockStateModelGenerator.registerSingleton(woodSet.getPlanks(), TexturedModel.CUBE_ALL);
      createSlab(woodSet.getPlanks(), woodSet.getSlab(), blockStateModelGenerator);
      createStairs(woodSet.getPlanks(), woodSet.getStairs(), blockStateModelGenerator);
      createWoodDoor(woodSet.getDoor(), blockStateModelGenerator);
      createWoodTrapdoor(woodSet.getTrapDoor(), blockStateModelGenerator);
      createWoodFenceGate(woodSet.getPlanks(), woodSet.getFenceGate(), blockStateModelGenerator);
      createWoodFence(woodSet.getPlanks(), woodSet.getFence(), blockStateModelGenerator);
      createWoodButton(woodSet.getPlanks(), woodSet.getButton(), blockStateModelGenerator);
      createWoodPressurePlate(woodSet.getPlanks(), woodSet.getPressurePlate(), blockStateModelGenerator);
      createWoodSign(woodSet.getSign(), woodSet.getWallSign(), blockStateModelGenerator);
      createHangingSign(woodSet.getStrippedLog(), woodSet.getHangingSign(), woodSet.getHangingWallSign(), blockStateModelGenerator);
    }
  }

  private void generateFlowerSetBlockStateModels(HashMap<String, FlowerSet> flowers, BlockStateModelGenerator blockStateModelGenerator) {
    for (FlowerSet flowerSet : flowers.values()) {
      if (Objects.equals(flowerSet.getName(), "protea")) {
        continue;
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.SMALL) {
        generateFlowerBlockStateModels(flowerSet.getFlowerBlock(), flowerSet.getPottedFlowerBlock(), blockStateModelGenerator);
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.MID_SMALL) {
        generatePottedAnemone(flowerSet.getFlowerBlock(), flowerSet.getPottedFlowerBlock(), blockStateModelGenerator);
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.TALL) {
        blockStateModelGenerator.registerDoubleBlock(flowerSet.getFlowerBlock(), TintType.NOT_TINTED);
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.BIG_TALL) {
        generateTallLargeFlower(flowerSet.getFlowerBlock(), blockStateModelGenerator);
      }
      if (flowerSet.getPreset() == FlowerSet.FlowerPreset.BIG_SMALL) {
        generateLargeFlower(flowerSet.getFlowerBlock(), flowerSet.getPottedFlowerBlock(), blockStateModelGenerator);
      }
    }
  }

  private void generateStoneBlockStateModels(HashMap<String, StoneSet> stones, BlockStateModelGenerator blockStateModelGenerator) {
    for (StoneSet stoneSet : stones.values()) {
      if (stoneSet.hasTiles()) {
        createWall(stoneSet.getTiles(), stoneSet.getTilesWall(), blockStateModelGenerator);
        createSlab(stoneSet.getTiles(), stoneSet.getTilesSlab(), blockStateModelGenerator);
        createStairs(stoneSet.getTiles(), stoneSet.getTilesStairs(), blockStateModelGenerator);
        blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getTiles());
      }
      if (stoneSet.hasCobbled()) {
        createWall(stoneSet.getCobbled(), stoneSet.getCobbledWall(), blockStateModelGenerator);
        createSlab(stoneSet.getCobbled(), stoneSet.getCobbledSlab(), blockStateModelGenerator);
        createStairs(stoneSet.getCobbled(), stoneSet.getCobbledStairs(), blockStateModelGenerator);
        blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getCobbled());
        if (stoneSet.hasMossy()) {
          createWall(stoneSet.getMossyCobbled(), stoneSet.getMossyCobbledWall(), blockStateModelGenerator);
          createSlab(stoneSet.getMossyCobbled(), stoneSet.getMossyCobbledSlab(), blockStateModelGenerator);
          createStairs(stoneSet.getMossyCobbled(), stoneSet.getMossyCobbledStairs(), blockStateModelGenerator);
          blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getMossyCobbled());
        }
      }
      if (stoneSet.hasMossy()) {
        createWall(stoneSet.getMossyBricks(), stoneSet.getMossyBricksWall(), blockStateModelGenerator);
        createSlab(stoneSet.getMossyBricks(), stoneSet.getMossyBricksSlab(), blockStateModelGenerator);
        createStairs(stoneSet.getMossyBricks(), stoneSet.getMossyBricksStairs(), blockStateModelGenerator);
        blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getMossyBricks());
      }
      if (stoneSet.hasCracked()) {
        blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getCrackedBricks());
      }
      createWall(stoneSet.getBricks(), stoneSet.getBricksWall(), blockStateModelGenerator);
      createSlab(stoneSet.getBricks(), stoneSet.getBricksSlab(), blockStateModelGenerator);
      createStairs(stoneSet.getBricks(), stoneSet.getBricksStairs(), blockStateModelGenerator);
      blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getBricks());

      createWall(stoneSet.getPolished(), stoneSet.getPolishedWall(), blockStateModelGenerator);
      createSlab(stoneSet.getPolished(), stoneSet.getPolishedSlab(), blockStateModelGenerator);
      createStairs(stoneSet.getPolished(), stoneSet.getPolishedStairs(), blockStateModelGenerator);
      blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getPolished());

      createSlab(stoneSet.getBase(), stoneSet.getBaseSlab(), blockStateModelGenerator);
      createStairs(stoneSet.getBase(), stoneSet.getBaseStairs(), blockStateModelGenerator);
      if (stoneSet.isRotatable()) {
        blockStateModelGenerator.registerLog(stoneSet.getBase()).log(stoneSet.getBase());
      } else {
        blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getBase());
      }

      blockStateModelGenerator.registerSimpleCubeAll(stoneSet.getChiseled());
    }
  }

  private void generateTreeBlockStateModels(HashMap<String, Block[]> saplings, HashMap<String, Block> leaves, BlockStateModelGenerator blockStateModelGenerator) {
    for (String i : leaves.keySet()) {
      Block[] saplingType = saplings.get(i);
      Block leavesType = leaves.get(i);
      if (!Objects.equals(i, "coconut") && !Objects.equals(i, "joshua")) {
        blockStateModelGenerator.registerSingleton(leavesType, TexturedModel.LEAVES);
        if (i.equals("redwood")) {
          generatePottedAnemone(saplingType[0], saplingType[1], blockStateModelGenerator);
        } else if (!Objects.equals(i, "wisteria") && !Objects.equals(i, "yellow_aspen") && !i.startsWith("part") && !i.startsWith("frosty")) {
          blockStateModelGenerator.registerFlowerPotPlant(saplingType[0], saplingType[1], TintType.NOT_TINTED);
        }
      } else if (Objects.equals(i, "joshua")) {
        blockStateModelGenerator.registerFlowerPotPlant(saplingType[0], saplingType[1], TintType.NOT_TINTED);
      }
    }
  }

  public final void registerTallCrossBlockState(Block block, TextureMap crossTexture, BlockStateModelGenerator blockStateModelGenerators) {
    Identifier identifier = TALL_CROSS.upload(block, crossTexture, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createSingletonBlockState(block, identifier));
  }

  public final void registerPaperLantern(Block lantern, BlockStateModelGenerator blockStateModelGenerator) {
    Identifier identifier = TEMPLATE_PAPER_LANTERN.upload(lantern, blockStateModelGenerator.modelCollector);
    Identifier identifier2 = TEMPLATE_HANGING_PAPER_LANTERN.upload(lantern, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.registerItemModel(lantern.asItem());
    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(lantern).coordinate(createBooleanModelMap(Properties.HANGING, identifier2, identifier)));
  }

  public final void registerVineBlockState(Block block, TextureMap crossTexture, BlockStateModelGenerator blockStateModelGenerators) {
    Identifier identifier = CROP.upload(block, crossTexture, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createSingletonBlockState(block, identifier));
  }

  public final void registerTallLargeBlockState(Block block, TextureMap crossTexture, BlockStateModelGenerator blockStateModelGenerators) {
    Identifier identifier = TALL_LARGE_CROSS.upload(block, crossTexture, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createSingletonBlockState(block, identifier));
  }

  public final void registerTintedTallLargeBlockState(Block block, TextureMap crossTexture, BlockStateModelGenerator blockStateModelGenerators) {
    Identifier identifier = TINTED_TALL_LARGE_CROSS.upload(block, crossTexture, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createSingletonBlockState(block, identifier));
  }

  public final void registerSpecificFlowerItemModel(Block block, BlockStateModelGenerator blockStateModelGenerators) {
    Item item = block.asItem();
    Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(item), blockStateModelGenerators.modelCollector);
  }

  private void generateFlowerBlockStateModels(Block block, Block block2, BlockStateModelGenerator blockStateModelGenerator) {
    blockStateModelGenerator.registerFlowerPotPlant(block, block2, TintType.NOT_TINTED);
  }

  private void generateTintedFlowerBlockStateModels(Block block, Block block2, BlockStateModelGenerator blockStateModelGenerator) {
    blockStateModelGenerator.registerFlowerPotPlant(block, block2, TintType.TINTED);
  }

  private void generatePottedAnemone(Block block, Block flowerPot, BlockStateModelGenerator blockStateModelGenerators) {
    registerSpecificFlowerItemModel(block, blockStateModelGenerators);
    TextureMap textureMap1 = TextureMap.cross(block);
    registerTallCrossBlockState(block, textureMap1, blockStateModelGenerators);
    TextureMap textureMap = TextureMap.plant(block);
    Identifier identifier = FLOWER_POT_TALL_CROSS.upload(flowerPot, textureMap, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createSingletonBlockState(flowerPot, identifier));
  }

  public final void generateVineBlockStateModels(Block plant, Block plantStem, BlockStateModelGenerator blockStateModelGenerators) {
    TextureMap textureMap1 = TextureMap.crop(getId(plant));
    this.registerVineBlockState(plant, textureMap1, blockStateModelGenerators);
    TextureMap textureMap2 = TextureMap.crop(getId(plantStem));
    this.registerVineBlockState(plantStem, textureMap2, blockStateModelGenerators);
    blockStateModelGenerators.registerItemModel(plant, "_plant");
  }

  public final void generateTallLargeFlower(Block doubleBlock, BlockStateModelGenerator blockStateModelGenerators) {
    registerSpecificFlowerItemModel(doubleBlock, blockStateModelGenerators);
    Identifier identifier = blockStateModelGenerators.createSubModel(doubleBlock, "_top", LARGE_CROSS, TextureMap::cross);
    Identifier identifier2 = blockStateModelGenerators.createSubModel(doubleBlock, "_bottom", LARGE_CROSS, TextureMap::cross);
    blockStateModelGenerators.registerDoubleBlock(doubleBlock, identifier, identifier2);
  }

  public final void generateTintedTallLargeFlower(Block doubleBlock, BlockStateModelGenerator blockStateModelGenerators) {
    registerSpecificFlowerItemModel(doubleBlock, blockStateModelGenerators);
    Identifier identifier = blockStateModelGenerators.createSubModel(doubleBlock, "_top", TINTED_LARGE_CROSS, TextureMap::cross);
    Identifier identifier2 = blockStateModelGenerators.createSubModel(doubleBlock, "_bottom", TINTED_LARGE_CROSS, TextureMap::cross);
    blockStateModelGenerators.registerDoubleBlock(doubleBlock, identifier, identifier2);
  }

  public final void generateLargeFlower(Block block, Block flowerPot, BlockStateModelGenerator blockStateModelGenerators) {
    registerSpecificFlowerItemModel(block, blockStateModelGenerators);
    registerTallLargeBlockState(block, TextureMap.cross(block), blockStateModelGenerators);
    TextureMap textureMap = TextureMap.plant(block);
    Identifier identifier = FLOWER_POT_LARGE_CROSS.upload(flowerPot, textureMap, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createSingletonBlockState(flowerPot, identifier));
  }

  public final void generateSucculent(Block block, Block wall, Block flowerPot, BlockStateModelGenerator blockStateModelGenerators) {
    TexturedModel texturedModel = TEXTURED_SUCCULENT.get(block);
    TextureMap textureMap = TextureMap.plant(block);
    Identifier identifier = texturedModel.upload(block, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, identifier));
    Identifier identifier2 = SUCCULENT_WALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(VariantsBlockStateSupplier.create(wall, BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
        .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    blockStateModelGenerators.registerItemModel(block);
    Identifier identifier3 = FLOWER_POT_SUCCULENT.upload(flowerPot, textureMap, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createSingletonBlockState(flowerPot, identifier3));
  }

  public final void generatePolypore(Block wall, BlockStateModelGenerator blockStateModelGenerators) {
    Identifier identifier2 = TEXTURED_POLYPORE.upload(wall, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(VariantsBlockStateSupplier.create(wall, BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
        .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    blockStateModelGenerators.registerItemModel(wall);
  }

  public final void generateTintedLargeFlower(Block block, Block flowerPot, BlockStateModelGenerator blockStateModelGenerators) {
    registerSpecificFlowerItemModel(block, blockStateModelGenerators);
    registerTintedTallLargeBlockState(block, TextureMap.cross(block), blockStateModelGenerators);
    TextureMap textureMap = TextureMap.plant(block);
    Identifier identifier = FLOWER_POT_TINTED_LARGE_CROSS.upload(flowerPot, textureMap, blockStateModelGenerators.modelCollector);
    blockStateModelGenerators.blockStateCollector.accept(createSingletonBlockState(flowerPot, identifier));
  }

  public final void generateLargeFlower(Block block, BlockStateModelGenerator blockStateModelGenerators) {
    registerSpecificFlowerItemModel(block, blockStateModelGenerators);
    registerTallLargeBlockState(block, TextureMap.cross(block), blockStateModelGenerators);
  }

  public final void registerCropWithoutItem(Block crop, Property<Integer> ageProperty, BlockStateModelGenerator blockStateModelGenerator, int... ageTextureIndices) {
    if (ageProperty.getValues().size() != ageTextureIndices.length) {
      throw new IllegalArgumentException();
    } else {
      Int2ObjectMap<Identifier> int2ObjectMap = new Int2ObjectOpenHashMap();
      BlockStateVariantMap blockStateVariantMap = BlockStateVariantMap.create(ageProperty).register((integer) -> {
        int i = ageTextureIndices[integer];
        Identifier identifier = int2ObjectMap.computeIfAbsent(i, (j) -> blockStateModelGenerator.createSubModel(crop, "_stage" + i, Models.CROP, TextureMap::crop));
        return BlockStateVariant.create().put(VariantSettings.MODEL, identifier);
      });
      blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop).coordinate(blockStateVariantMap));
    }
  }

  public final void registerMushroomBlock(Block mushroomBlock, BlockStateModelGenerator blockStateModelGenerator) {
    Identifier identifier = Models.TEMPLATE_SINGLE_FACE.upload(mushroomBlock, TextureMap.texture(mushroomBlock), blockStateModelGenerator.modelCollector);
    Identifier identifier2 = ModelIds.getMinecraftNamespacedBlock("mushroom_block_inside");
    blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier
        .create(mushroomBlock)
        .with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
        .with(When.create().set(Properties.EAST, true), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier)
            .put(VariantSettings.Y, VariantSettings.Rotation.R90)
            .put(VariantSettings.UVLOCK, true))
        .with(When.create().set(Properties.SOUTH, true), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier)
            .put(VariantSettings.Y, VariantSettings.Rotation.R180)
            .put(VariantSettings.UVLOCK, true))
        .with(When.create().set(Properties.WEST, true), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier)
            .put(VariantSettings.Y, VariantSettings.Rotation.R270)
            .put(VariantSettings.UVLOCK, true))
        .with(When.create().set(Properties.UP, true), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier)
            .put(VariantSettings.X, VariantSettings.Rotation.R270)
            .put(VariantSettings.UVLOCK, true))
        .with(When.create().set(Properties.DOWN, true), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier)
            .put(VariantSettings.X, VariantSettings.Rotation.R90)
            .put(VariantSettings.UVLOCK, true))
        .with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
        .with(When.create().set(Properties.EAST, false), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier2)
            .put(VariantSettings.Y, VariantSettings.Rotation.R90)
            .put(VariantSettings.UVLOCK, false))
        .with(When.create().set(Properties.SOUTH, false), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier2)
            .put(VariantSettings.Y, VariantSettings.Rotation.R180)
            .put(VariantSettings.UVLOCK, false))
        .with(When.create().set(Properties.WEST, false), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier2)
            .put(VariantSettings.Y, VariantSettings.Rotation.R270)
            .put(VariantSettings.UVLOCK, false))
        .with(When.create().set(Properties.UP, false), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier2)
            .put(VariantSettings.X, VariantSettings.Rotation.R270)
            .put(VariantSettings.UVLOCK, false))
        .with(When.create().set(Properties.DOWN, false), BlockStateVariant
            .create()
            .put(VariantSettings.MODEL, identifier2)
            .put(VariantSettings.X, VariantSettings.Rotation.R90)
            .put(VariantSettings.UVLOCK, false)));
    blockStateModelGenerator.registerParentedItemModel(mushroomBlock, TexturedModel.CUBE_ALL.upload(mushroomBlock, "_inventory", blockStateModelGenerator.modelCollector));
  }

  private void registerCheese(BlockStateModelGenerator blockStateModelGenerator) {
    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(CHEESE_BLOCK).coordinate(
        BlockStateVariantMap.create(Properties.BITES).register(0, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(CHEESE_BLOCK)))
            .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CHEESE_BLOCK, "_slice1")))
            .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CHEESE_BLOCK, "_slice2")))
            .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CHEESE_BLOCK, "_slice3")))
            .register(4, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CHEESE_BLOCK, "_slice4")))
            .register(5, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CHEESE_BLOCK, "_slice5")))
            .register(6, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(CHEESE_BLOCK, "_slice6")))));
    blockStateModelGenerator.excludeFromSimpleItemModelGeneration(CHEESE_BLOCK);
    blockStateModelGenerator.blockStateCollector.accept(createSingletonBlockState(CHEESE_CAULDRON,
        Models.TEMPLATE_CAULDRON_FULL.upload(CHEESE_CAULDRON, TextureMap.cauldron(TextureMap.getId(CHEESE_BLOCK)), blockStateModelGenerator.modelCollector)));
    blockStateModelGenerator.excludeFromSimpleItemModelGeneration(CHEESE_CAULDRON);
  }

  public final void registerPaperPanels(Block block, Block paperPanel, BlockStateModelGenerator blockStateModelGenerator) {
    TextureMap textureMap = TextureMap.paneAndTopForEdge(block, paperPanel);
    Identifier identifier = Models.TEMPLATE_GLASS_PANE_POST.upload(paperPanel, textureMap, blockStateModelGenerator.modelCollector);
    Identifier identifier2 = Models.TEMPLATE_GLASS_PANE_SIDE.upload(paperPanel, textureMap, blockStateModelGenerator.modelCollector);
    Identifier identifier3 = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(paperPanel, textureMap, blockStateModelGenerator.modelCollector);
    Identifier identifier4 = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(paperPanel, textureMap, blockStateModelGenerator.modelCollector);
    Identifier identifier5 = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(paperPanel, textureMap, blockStateModelGenerator.modelCollector);
    Item item = paperPanel.asItem();
    Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(block), blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(paperPanel).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
        .with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
        .with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3))
        .with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4))
        .with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5))
        .with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
  }

  public final void registerNorthDefaultHorizontalFacing(TexturedModel.Factory modelFactory, Block block, BlockStateModelGenerator blockStateModelGenerator) {
    Identifier identifier = modelFactory.upload(block, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(
        VariantsBlockStateSupplier.create(block, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).coordinate(createNorthDefaultHorizontalRotationStates()));
  }

  public final void registerAzolla(Item item, Block flowerbed, BlockStateModelGenerator blockStateModelGenerator) {
    blockStateModelGenerator.registerItemModel(item);
    Identifier identifier = TEXTURED_AZOLLA_1.upload(flowerbed, blockStateModelGenerator.modelCollector);
    Identifier identifier2 = TEXTURED_AZOLLA_2.upload(flowerbed, blockStateModelGenerator.modelCollector);
    Identifier identifier3 = TEXTURED_AZOLLA_3.upload(flowerbed, blockStateModelGenerator.modelCollector);
    Identifier identifier4 = TEXTURED_AZOLLA_4.upload(flowerbed, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(flowerbed)
        .with(When.create().set(Properties.FLOWER_AMOUNT, 1, new Integer[]{2, 3, 4}).set(Properties.HORIZONTAL_FACING, Direction.NORTH),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 1, new Integer[]{2, 3, 4}).set(Properties.HORIZONTAL_FACING, Direction.EAST),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 1, new Integer[]{2, 3, 4}).set(Properties.HORIZONTAL_FACING, Direction.SOUTH),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier).put(VariantSettings.Y, VariantSettings.Rotation.R180))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 1, new Integer[]{2, 3, 4}).set(Properties.HORIZONTAL_FACING, Direction.WEST),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier).put(VariantSettings.Y, VariantSettings.Rotation.R270))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 2, new Integer[]{3, 4}).set(Properties.HORIZONTAL_FACING, Direction.NORTH),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier2))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 2, new Integer[]{3, 4}).set(Properties.HORIZONTAL_FACING, Direction.EAST),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 2, new Integer[]{3, 4}).set(Properties.HORIZONTAL_FACING, Direction.SOUTH),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R180))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 2, new Integer[]{3, 4}).set(Properties.HORIZONTAL_FACING, Direction.WEST),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R270))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 3, new Integer[]{4}).set(Properties.HORIZONTAL_FACING, Direction.NORTH),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier3))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 3, new Integer[]{4}).set(Properties.HORIZONTAL_FACING, Direction.EAST),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 3, new Integer[]{4}).set(Properties.HORIZONTAL_FACING, Direction.SOUTH),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R180))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 3, new Integer[]{4}).set(Properties.HORIZONTAL_FACING, Direction.WEST),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R270))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 4).set(Properties.HORIZONTAL_FACING, Direction.NORTH), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 4).set(Properties.HORIZONTAL_FACING, Direction.EAST),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 4).set(Properties.HORIZONTAL_FACING, Direction.SOUTH),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R180))
        .with(When.create().set(Properties.FLOWER_AMOUNT, 4).set(Properties.HORIZONTAL_FACING, Direction.WEST),
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    generateWoodBlockStateModels(NSRegistryHelper.WoodHashMap, blockStateModelGenerator);
    generateFlowerSetBlockStateModels(NSRegistryHelper.FlowerHashMap, blockStateModelGenerator);
    generateStoneBlockStateModels(NSRegistryHelper.StoneHashMap, blockStateModelGenerator);
    generateTreeBlockStateModels(NSRegistryHelper.SaplingHashMap, NSRegistryHelper.LeavesHashMap, blockStateModelGenerator);

    createOliveBranch(NSWoods.OLIVE_BRANCH, blockStateModelGenerator);

    blockStateModelGenerator.registerAmethyst(CALCITE_CLUSTER);
    blockStateModelGenerator.registerAmethyst(SMALL_CALCITE_BUD);
    blockStateModelGenerator.registerAmethyst(LARGE_CALCITE_BUD);

    registerCheese(blockStateModelGenerator);
    generateFlowerBlockStateModels(FLAXEN_FERN, POTTED_FLAXEN_FERN, blockStateModelGenerator);
    generateFlowerBlockStateModels(SHIITAKE_MUSHROOM, POTTED_SHIITAKE_MUSHROOM, blockStateModelGenerator);
    registerMushroomBlock(SHIITAKE_MUSHROOM_BLOCK, blockStateModelGenerator);
    generatePolypore(GRAY_POLYPORE, blockStateModelGenerator);
    registerMushroomBlock(GRAY_POLYPORE_BLOCK, blockStateModelGenerator);
    registerCropWithoutItem(NSMiscBlocks.DESERT_TURNIP_STEM, DesertTurnipStemBlock.AGE, blockStateModelGenerator, 0, 1, 2, 3, 4, 5, 6, 7);
    generateTallLargeFlower(NSMiscBlocks.TALL_SCORCHED_GRASS, blockStateModelGenerator);
    generateTallLargeFlower(TALL_BEACH_GRASS, blockStateModelGenerator);
    generateTallLargeFlower(TALL_FRIGID_GRASS, blockStateModelGenerator);
    generateTintedTallLargeFlower(LARGE_LUSH_FERN, blockStateModelGenerator);
    generateTallLargeFlower(TALL_SEDGE_GRASS, blockStateModelGenerator);
    generateTallLargeFlower(TALL_OAT_GRASS, blockStateModelGenerator);
    generateTallLargeFlower(TALL_MELIC_GRASS, blockStateModelGenerator);
    generateLargeFlower(NSMiscBlocks.SCORCHED_GRASS, POTTED_SCORCHED_GRASS, blockStateModelGenerator);
    generateLargeFlower(RED_BEARBERRIES, POTTED_RED_BEARBERRIES, blockStateModelGenerator);
    generateLargeFlower(PURPLE_BEARBERRIES, POTTED_PURPLE_BEARBERRIES, blockStateModelGenerator);
    generateLargeFlower(GREEN_BEARBERRIES, POTTED_GREEN_BEARBERRIES, blockStateModelGenerator);
    generateLargeFlower(GREEN_BITTER_SPROUTS, blockStateModelGenerator);
    generateLargeFlower(RED_BITTER_SPROUTS, blockStateModelGenerator);
    generateLargeFlower(PURPLE_BITTER_SPROUTS, blockStateModelGenerator);
    generateLargeFlower(BEACH_GRASS, POTTED_BEACH_GRASS, blockStateModelGenerator);
    generateLargeFlower(SEDGE_GRASS, POTTED_SEDGE_GRASS, blockStateModelGenerator);
    generateLargeFlower(OAT_GRASS, POTTED_OAT_GRASS, blockStateModelGenerator);
    generateLargeFlower(FRIGID_GRASS, POTTED_FRIGID_GRASS, blockStateModelGenerator);
    generateTintedLargeFlower(LUSH_FERN, POTTED_LUSH_FERN, blockStateModelGenerator);
    generateTintedLargeFlower(MELIC_GRASS, POTTED_MELIC_GRASS, blockStateModelGenerator);
    generateVineBlockStateModels(NSWoods.WISTERIA.getBlueVines(), NSWoods.WISTERIA.getBlueVinesPlant(), blockStateModelGenerator);
    generateVineBlockStateModels(NSWoods.WISTERIA.getWhiteVines(), NSWoods.WISTERIA.getWhiteVinesPlant(), blockStateModelGenerator);
    generateVineBlockStateModels(NSWoods.WISTERIA.getPurpleVines(), NSWoods.WISTERIA.getPurpleVinesPlant(), blockStateModelGenerator);
    generateVineBlockStateModels(NSWoods.WISTERIA.getPinkVines(), NSWoods.WISTERIA.getPinkVinesPlant(), blockStateModelGenerator);
    registerAzolla(AZOLLA_ITEM, AZOLLA, blockStateModelGenerator);

    blockStateModelGenerator.registerLog(ALLUAUDIA_BUNDLE).log(ALLUAUDIA_BUNDLE);
    blockStateModelGenerator.registerLog(STRIPPED_ALLUAUDIA_BUNDLE).log(STRIPPED_ALLUAUDIA_BUNDLE);

    createWoodDoor(PAPER_DOOR, blockStateModelGenerator);
    createWoodTrapdoor(PAPER_TRAPDOOR, blockStateModelGenerator);
    createWoodDoor(FRAMED_PAPER_DOOR, blockStateModelGenerator);
    createWoodTrapdoor(FRAMED_PAPER_TRAPDOOR, blockStateModelGenerator);
    createWoodDoor(BLOOMING_PAPER_DOOR, blockStateModelGenerator);
    createWoodTrapdoor(BLOOMING_PAPER_TRAPDOOR, blockStateModelGenerator);
    createWoodSign(PAPER_SIGN, PAPER_WALL_SIGN, blockStateModelGenerator);
    createHangingSign(PAPER_BLOCK, PAPER_HANGING_SIGN, PAPER_WALL_HANGING_SIGN, blockStateModelGenerator);
    blockStateModelGenerator.registerSingleton(PAPER_BLOCK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(FRAMED_PAPER_BLOCK, TexturedModel.CUBE_ALL);
    registerNorthDefaultHorizontalFacing(TexturedModel.TEMPLATE_GLAZED_TERRACOTTA, BLOOMING_PAPER_BLOCK, blockStateModelGenerator);
    registerPaperPanels(PAPER_BLOCK, PAPER_PANEL, blockStateModelGenerator);
    registerPaperPanels(FRAMED_PAPER_BLOCK, FRAMED_PAPER_PANEL, blockStateModelGenerator);
    registerPaperPanels(BLOOMING_PAPER_BLOCK, BLOOMING_PAPER_PANEL, blockStateModelGenerator);

    registerPaperLantern(NSColoredBlocks.PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.WHITE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.LIGHT_GRAY_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.GRAY_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.BLACK_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.BROWN_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.RED_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.ORANGE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.YELLOW_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.LIME_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.GREEN_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.CYAN_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.LIGHT_BLUE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.BLUE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.PURPLE_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.MAGENTA_PAPER_LANTERN, blockStateModelGenerator);
    registerPaperLantern(NSColoredBlocks.PINK_PAPER_LANTERN, blockStateModelGenerator);

    createSlab(NSColoredBlocks.KAOLIN, NSColoredBlocks.KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.WHITE_KAOLIN, NSColoredBlocks.WHITE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_GRAY_KAOLIN, NSColoredBlocks.LIGHT_GRAY_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GRAY_KAOLIN, NSColoredBlocks.GRAY_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLACK_KAOLIN, NSColoredBlocks.BLACK_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BROWN_KAOLIN, NSColoredBlocks.BROWN_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.RED_KAOLIN, NSColoredBlocks.RED_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.ORANGE_KAOLIN, NSColoredBlocks.ORANGE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.YELLOW_KAOLIN, NSColoredBlocks.YELLOW_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIME_KAOLIN, NSColoredBlocks.LIME_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GREEN_KAOLIN, NSColoredBlocks.GREEN_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.CYAN_KAOLIN, NSColoredBlocks.CYAN_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_BLUE_KAOLIN, NSColoredBlocks.LIGHT_BLUE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLUE_KAOLIN, NSColoredBlocks.BLUE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PURPLE_KAOLIN, NSColoredBlocks.PURPLE_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.MAGENTA_KAOLIN, NSColoredBlocks.MAGENTA_KAOLIN_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PINK_KAOLIN, NSColoredBlocks.PINK_KAOLIN_SLAB, blockStateModelGenerator);

    createStairs(NSColoredBlocks.KAOLIN, NSColoredBlocks.KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.WHITE_KAOLIN, NSColoredBlocks.WHITE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_GRAY_KAOLIN, NSColoredBlocks.LIGHT_GRAY_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GRAY_KAOLIN, NSColoredBlocks.GRAY_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLACK_KAOLIN, NSColoredBlocks.BLACK_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BROWN_KAOLIN, NSColoredBlocks.BROWN_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.RED_KAOLIN, NSColoredBlocks.RED_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.ORANGE_KAOLIN, NSColoredBlocks.ORANGE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.YELLOW_KAOLIN, NSColoredBlocks.YELLOW_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIME_KAOLIN, NSColoredBlocks.LIME_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GREEN_KAOLIN, NSColoredBlocks.GREEN_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.CYAN_KAOLIN, NSColoredBlocks.CYAN_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_BLUE_KAOLIN, NSColoredBlocks.LIGHT_BLUE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLUE_KAOLIN, NSColoredBlocks.BLUE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PURPLE_KAOLIN, NSColoredBlocks.PURPLE_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.MAGENTA_KAOLIN, NSColoredBlocks.MAGENTA_KAOLIN_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PINK_KAOLIN, NSColoredBlocks.PINK_KAOLIN_STAIRS, blockStateModelGenerator);

    blockStateModelGenerator.registerSingleton(NSColoredBlocks.KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.WHITE_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIGHT_GRAY_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.GRAY_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BLACK_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BROWN_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.RED_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.ORANGE_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.YELLOW_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIME_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.GREEN_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.CYAN_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIGHT_BLUE_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BLUE_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.PURPLE_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.MAGENTA_KAOLIN, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.PINK_KAOLIN, TexturedModel.CUBE_ALL);

    createSlab(NSColoredBlocks.KAOLIN_BRICKS, NSColoredBlocks.KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.WHITE_KAOLIN_BRICKS, NSColoredBlocks.WHITE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS, NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GRAY_KAOLIN_BRICKS, NSColoredBlocks.GRAY_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLACK_KAOLIN_BRICKS, NSColoredBlocks.BLACK_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BROWN_KAOLIN_BRICKS, NSColoredBlocks.BROWN_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.RED_KAOLIN_BRICKS, NSColoredBlocks.RED_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.ORANGE_KAOLIN_BRICKS, NSColoredBlocks.ORANGE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.YELLOW_KAOLIN_BRICKS, NSColoredBlocks.YELLOW_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIME_KAOLIN_BRICKS, NSColoredBlocks.LIME_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GREEN_KAOLIN_BRICKS, NSColoredBlocks.GREEN_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.CYAN_KAOLIN_BRICKS, NSColoredBlocks.CYAN_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS, NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLUE_KAOLIN_BRICKS, NSColoredBlocks.BLUE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PURPLE_KAOLIN_BRICKS, NSColoredBlocks.PURPLE_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.MAGENTA_KAOLIN_BRICKS, NSColoredBlocks.MAGENTA_KAOLIN_BRICK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PINK_KAOLIN_BRICKS, NSColoredBlocks.PINK_KAOLIN_BRICK_SLAB, blockStateModelGenerator);

    createStairs(NSColoredBlocks.KAOLIN_BRICKS, NSColoredBlocks.KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.WHITE_KAOLIN_BRICKS, NSColoredBlocks.WHITE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS, NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GRAY_KAOLIN_BRICKS, NSColoredBlocks.GRAY_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLACK_KAOLIN_BRICKS, NSColoredBlocks.BLACK_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BROWN_KAOLIN_BRICKS, NSColoredBlocks.BROWN_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.RED_KAOLIN_BRICKS, NSColoredBlocks.RED_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.ORANGE_KAOLIN_BRICKS, NSColoredBlocks.ORANGE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.YELLOW_KAOLIN_BRICKS, NSColoredBlocks.YELLOW_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIME_KAOLIN_BRICKS, NSColoredBlocks.LIME_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GREEN_KAOLIN_BRICKS, NSColoredBlocks.GREEN_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.CYAN_KAOLIN_BRICKS, NSColoredBlocks.CYAN_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS, NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLUE_KAOLIN_BRICKS, NSColoredBlocks.BLUE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PURPLE_KAOLIN_BRICKS, NSColoredBlocks.PURPLE_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.MAGENTA_KAOLIN_BRICKS, NSColoredBlocks.MAGENTA_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PINK_KAOLIN_BRICKS, NSColoredBlocks.PINK_KAOLIN_BRICK_STAIRS, blockStateModelGenerator);

    blockStateModelGenerator.registerSingleton(NSColoredBlocks.KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.WHITE_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIGHT_GRAY_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.GRAY_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BLACK_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BROWN_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.RED_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.ORANGE_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.YELLOW_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIME_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.GREEN_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.CYAN_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIGHT_BLUE_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BLUE_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.PURPLE_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.MAGENTA_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.PINK_KAOLIN_BRICKS, TexturedModel.CUBE_ALL);

    createSlab(NSColoredBlocks.WHITE_CHALK, NSColoredBlocks.WHITE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_GRAY_CHALK, NSColoredBlocks.LIGHT_GRAY_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GRAY_CHALK, NSColoredBlocks.GRAY_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLACK_CHALK, NSColoredBlocks.BLACK_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BROWN_CHALK, NSColoredBlocks.BROWN_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.RED_CHALK, NSColoredBlocks.RED_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.ORANGE_CHALK, NSColoredBlocks.ORANGE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.YELLOW_CHALK, NSColoredBlocks.YELLOW_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIME_CHALK, NSColoredBlocks.LIME_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.GREEN_CHALK, NSColoredBlocks.GREEN_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.CYAN_CHALK, NSColoredBlocks.CYAN_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.LIGHT_BLUE_CHALK, NSColoredBlocks.LIGHT_BLUE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.BLUE_CHALK, NSColoredBlocks.BLUE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PURPLE_CHALK, NSColoredBlocks.PURPLE_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.MAGENTA_CHALK, NSColoredBlocks.MAGENTA_CHALK_SLAB, blockStateModelGenerator);
    createSlab(NSColoredBlocks.PINK_CHALK, NSColoredBlocks.PINK_CHALK_SLAB, blockStateModelGenerator);

    createStairs(NSColoredBlocks.WHITE_CHALK, NSColoredBlocks.WHITE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_GRAY_CHALK, NSColoredBlocks.LIGHT_GRAY_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GRAY_CHALK, NSColoredBlocks.GRAY_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLACK_CHALK, NSColoredBlocks.BLACK_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BROWN_CHALK, NSColoredBlocks.BROWN_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.RED_CHALK, NSColoredBlocks.RED_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.ORANGE_CHALK, NSColoredBlocks.ORANGE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.YELLOW_CHALK, NSColoredBlocks.YELLOW_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIME_CHALK, NSColoredBlocks.LIME_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.GREEN_CHALK, NSColoredBlocks.GREEN_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.CYAN_CHALK, NSColoredBlocks.CYAN_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.LIGHT_BLUE_CHALK, NSColoredBlocks.LIGHT_BLUE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.BLUE_CHALK, NSColoredBlocks.BLUE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PURPLE_CHALK, NSColoredBlocks.PURPLE_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.MAGENTA_CHALK, NSColoredBlocks.MAGENTA_CHALK_STAIRS, blockStateModelGenerator);
    createStairs(NSColoredBlocks.PINK_CHALK, NSColoredBlocks.PINK_CHALK_STAIRS, blockStateModelGenerator);

    blockStateModelGenerator.registerSingleton(NSColoredBlocks.WHITE_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIGHT_GRAY_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.GRAY_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BLACK_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BROWN_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.RED_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.ORANGE_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.YELLOW_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIME_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.GREEN_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.CYAN_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.LIGHT_BLUE_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.BLUE_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.PURPLE_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.MAGENTA_CHALK, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(NSColoredBlocks.PINK_CHALK, TexturedModel.CUBE_ALL);

    blockStateModelGenerator.registerAxisRotated(NSMiscBlocks.DESERT_TURNIP_ROOT_BLOCK, TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
    blockStateModelGenerator.registerSingleton(CHERT_COAL_ORE, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(CHERT_COPPER_ORE, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(CHERT_DIAMOND_ORE, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(CHERT_GOLD_ORE, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(CHERT_EMERALD_ORE, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(CHERT_IRON_ORE, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(CHERT_LAPIS_ORE, TexturedModel.CUBE_ALL);
    blockStateModelGenerator.registerSingleton(CHERT_REDSTONE_ORE, TexturedModel.CUBE_ALL);

    generateSucculent(ORNATE_SUCCULENT, ORNATE_WALL_SUCCULENT, POTTED_ORNATE_SUCCULENT, blockStateModelGenerator);
    generateSucculent(DROWSY_SUCCULENT, DROWSY_WALL_SUCCULENT, POTTED_DROWSY_SUCCULENT, blockStateModelGenerator);
    generateSucculent(AUREATE_SUCCULENT, AUREATE_WALL_SUCCULENT, POTTED_AUREATE_SUCCULENT, blockStateModelGenerator);
    generateSucculent(SAGE_SUCCULENT, SAGE_WALL_SUCCULENT, POTTED_SAGE_SUCCULENT, blockStateModelGenerator);
    generateSucculent(FOAMY_SUCCULENT, FOAMY_WALL_SUCCULENT, POTTED_FOAMY_SUCCULENT, blockStateModelGenerator);
    generateSucculent(IMPERIAL_SUCCULENT, IMPERIAL_WALL_SUCCULENT, POTTED_IMPERIAL_SUCCULENT, blockStateModelGenerator);
    generateSucculent(REGAL_SUCCULENT, REGAL_WALL_SUCCULENT, POTTED_REGAL_SUCCULENT, blockStateModelGenerator);
  }

  @Override
  public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    itemModelGenerator.register(NSMiscBlocks.OLIVES, Models.GENERATED);
    itemModelGenerator.register(NSMiscBlocks.DESERT_TURNIP, Models.GENERATED);
    itemModelGenerator.register(NSWoods.COCONUT_SHELL, Models.GENERATED);
    itemModelGenerator.register(NSWoods.COCONUT_HALF, Models.GENERATED);
    itemModelGenerator.register(CALCITE_SHARD, Models.GENERATED);
    itemModelGenerator.register(CHALK_POWDER, Models.GENERATED);
    itemModelGenerator.register(CHEESE_BUCKET, Models.GENERATED);
    itemModelGenerator.register(CHEESE_ARROW, Models.GENERATED);
    itemModelGenerator.register(HELVOLA_FLOWER_ITEM, Models.GENERATED);
    itemModelGenerator.register(HELVOLA_PAD_ITEM, Models.GENERATED);

    NSBoatTypes.getAllBoatItems().forEach(item -> itemModelGenerator.register(item, Models.GENERATED));
  }
}
