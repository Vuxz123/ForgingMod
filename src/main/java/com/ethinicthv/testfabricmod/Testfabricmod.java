package com.ethinicthv.testfabricmod;

import com.ethinicthv.testfabricmod.block.ForgingAnvil;
import com.ethinicthv.testfabricmod.block.TestingBlock;
import com.ethinicthv.testfabricmod.block.blockentity.TestingBlockEntity;
import com.ethinicthv.testfabricmod.initializing.BlockProvider;
import com.ethinicthv.testfabricmod.initializing.Init;
import com.ethinicthv.testfabricmod.networking.PacketHandler;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.nio.charset.StandardCharsets;

public class Testfabricmod implements ModInitializer {

    public static String NAMESPACE = "testfabricmod";

    @Override
    public void onInitialize() {

        Init.registerBlock();
        Init.registerItem();

        PacketHandler.handleUseTestBlock();
        PacketHandler.handleForgingOnForgingAnvil();

    }

}
