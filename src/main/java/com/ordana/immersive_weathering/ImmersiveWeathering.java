package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ImmersiveWeathering implements ModInitializer {

    public static final String MOD_ID = "immersive_weathering";
    public static final Tag<Block> MOSSY = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "mossy"));
    public static final Tag<Block> MOSSABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "mossable"));
    public static final Tag<Block> CRACKED = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "cracked"));
    public static final Tag<Block> CRACKABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "crackable"));
    public static final Tag<Block> MOSS_SOURCE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "moss_source"));
    public static final Tag<Block> RAW_LOGS = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "raw_logs"));

    public static final Tag<Block> CLEAN_IRON = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "clean_iron"));
    public static final Tag<Block> EXPOSED_IRON = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "exposed_iron"));
    public static final Tag<Block> WEATHERED_IRON = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "weathered_iron"));
    public static final Tag<Block> RUSTABLE = TagFactory.BLOCK.create(new Identifier(ImmersiveWeathering.MOD_ID, "rustable"));

    public static final Tag<Item> BARK = TagFactory.ITEM.create(new Identifier(ImmersiveWeathering.MOD_ID, "bark"));
    public static final Tag<Item> SCALES = TagFactory.ITEM.create(new Identifier(ImmersiveWeathering.MOD_ID, "scales"));

    @Override
    public void onInitialize() {
        ModEvents.registerEvents();
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModCompostable.registerCompostable();
        ModFuel.registerFuel();
        ModWaxable.registerWaxable();
        FabricLoader.getInstance().getModContainer(ImmersiveWeathering.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("immersive_weathering:better_brick_items"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("immersive_weathering:better_brick_blocks"), modContainer, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier("immersive_weathering:visual_waxed_iron_items"), modContainer, ResourcePackActivationType.NORMAL);
        });
    }
}
