package com.ethinicthv.testfabricmod.block.blockentity;

import com.ethinicthv.testfabricmod.initializing.BlockProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TestingBlockEntity extends BlockEntity {
    private ItemStack item;

    public TestingBlockEntity(BlockPos pos, BlockState state) {
        super(BlockProvider.Entity.DEMO_BLOCK_ENTITY, pos, state);
        item = ItemStack.EMPTY;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("item", item.getNbt());
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        item = ItemStack.fromNbt(nbt.getCompound("item"));
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void onClick(ItemStack i){
        this.item = i;
    }

    public ItemStack getItem() {
        return item;
    }
}
