package com.ethinicthv.testfabricmod.networking;

import com.ethinicthv.testfabricmod.block.TestingBlock;
import com.ethinicthv.testfabricmod.block.blockentity.ForgingAnvilEntity;
import com.ethinicthv.testfabricmod.networking.packet.PacketIdentifier;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.Objects;

public class PacketHandler {

    public PacketHandler(){
    }

    public static void handleUseTestBlock(){
        ServerPlayNetworking.registerGlobalReceiver(PacketIdentifier.USE_TESTBLOCK, (server, player, handler, buf, responseSender) -> {
            BlockHitResult blockHitResult = buf.readBlockHitResult();
            boolean check = buf.readVarInt() == 1;
            BlockPos pos = blockHitResult.getBlockPos();
            player.sendMessage(Text.of("" + check));
            player.getWorld().setBlockState(pos, player.getWorld().getBlockState(pos).with(TestingBlock.COVERED, check), Block.NOTIFY_ALL);
        });
    }

    public static void handleForgingOnForgingAnvil(){
        ServerPlayNetworking.registerGlobalReceiver(PacketIdentifier.FORGING,((server, player, handler, buf, responseSender) -> {
            int power = buf.readVarInt();
            BlockPos pos = buf.readBlockPos();
            ServerWorld world = server.getWorld(World.OVERWORLD);
            Chunk chunk = Objects.requireNonNull(world).getChunk(pos);
            BlockEntity e1 = chunk.getBlockEntity(pos);
            if(e1 instanceof ForgingAnvilEntity forgingAnvilEntity){
                ItemStack stack = forgingAnvilEntity.getStoredItem().copy();
                int i = stack.getDamage();
                stack.setDamage(i - power);
                forgingAnvilEntity.setStoredItem(stack, world, world.getBlockState(pos));
            }
        }));
    }

}

