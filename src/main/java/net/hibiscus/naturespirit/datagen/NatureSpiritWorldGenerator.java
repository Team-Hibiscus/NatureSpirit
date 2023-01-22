package net.hibiscus.naturespirit.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.hibiscus.naturespirit.NatureSpirit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class NatureSpiritWorldGenerator extends FabricDynamicRegistryProvider {
    public NatureSpiritWorldGenerator(FabricDataOutput output, CompletableFuture <HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));
//        entries.addAll(registries.lookupOrThrow(Registries.BIOME));
    }

    @Override
    public String getName() {
        return NatureSpirit.MOD_ID;
    }
}
