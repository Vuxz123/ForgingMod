package com.ethinicthv.testfabricmod.initializing;

import com.ethinicthv.testfabricmod.block.ForgingAnvil;
import com.ethinicthv.testfabricmod.block.TestingBlock;
import com.ethinicthv.testfabricmod.block.blockentity.ForgingAnvilEntity;
import com.ethinicthv.testfabricmod.block.blockentity.TestingBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

public class BlockProvider {
    public static Block TEST_BLOCK = new TestingBlock();
    public static Block FORGING_ANVIL = new ForgingAnvil();

    public static class Item{
        public static BlockItem TEST_BLOCK_ITEM = new BlockItem(TEST_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS));
        public static BlockItem FORGING_ANVIL_ITEM = new BlockItem(FORGING_ANVIL, new FabricItemSettings().group(ItemGroup.DECORATIONS));
    }

    public static class Entity{
        public static BlockEntityType<TestingBlockEntity> DEMO_BLOCK_ENTITY;

        public static BlockEntityType<ForgingAnvilEntity> FORGING_ANVIL_ENTITY ;
    }
}
