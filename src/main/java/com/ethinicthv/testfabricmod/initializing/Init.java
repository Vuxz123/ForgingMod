package com.ethinicthv.testfabricmod.initializing;

import com.ethinicthv.testfabricmod.Testfabricmod;
import com.ethinicthv.testfabricmod.block.blockentity.ForgingAnvilEntity;
import com.ethinicthv.testfabricmod.block.blockentity.TestingBlockEntity;
import com.ethinicthv.testfabricmod.client.renderer.ForgingAnvilRenderer;
import com.ethinicthv.testfabricmod.client.renderer.TestRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Init {
    public static void registerBlock(){
        Registry.register(Registry.BLOCK,createKey("test_block"), BlockProvider.TEST_BLOCK);
        Registry.register(Registry.BLOCK,createKey("forging_anvil"), BlockProvider.FORGING_ANVIL);

        //register block item;
        registerBlockItem();

        //register block entity;
        registerBlockEntity();
    }

    public static void registerBlockItem(){
        Registry.register(Registry.ITEM, createKey("test_block"), BlockProvider.Item.TEST_BLOCK_ITEM);
        Registry.register(Registry.ITEM, createKey("forging_anvil"), BlockProvider.Item.FORGING_ANVIL_ITEM);
    }

    public static void registerBlockEntity(){
        BlockProvider.Entity.DEMO_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, createKey("testing_block_entity"), FabricBlockEntityTypeBuilder.create(TestingBlockEntity::new, BlockProvider.TEST_BLOCK).build(null));
        BlockProvider.Entity.FORGING_ANVIL_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, createKey("forging_anvil_entity"), FabricBlockEntityTypeBuilder.create(ForgingAnvilEntity::new, BlockProvider.FORGING_ANVIL).build());
    }

    @Environment(EnvType.CLIENT)
    public static void registerBlockDedicatedRenderer(){
        BlockEntityRendererRegistry.register(BlockProvider.Entity.DEMO_BLOCK_ENTITY, TestRenderer::new);
        BlockEntityRendererRegistry.register(BlockProvider.Entity.FORGING_ANVIL_ENTITY, ForgingAnvilRenderer::new);
    }

    public static void registerItem(){
        Registry.register(Registry.ITEM, createKey("hammer"), ItemProvider.HAMMER);
    }

    public static Identifier createKey(String name){
        return new Identifier(Testfabricmod.NAMESPACE, name);
    }


}
