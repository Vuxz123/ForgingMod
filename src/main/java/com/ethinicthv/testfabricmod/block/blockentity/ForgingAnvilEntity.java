package com.ethinicthv.testfabricmod.block.blockentity;

import com.ethinicthv.testfabricmod.initializing.BlockProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ForgingAnvilEntity extends BlockEntity {
    private ItemStack stored_item;
    public boolean isclickable = false;
    public ForgingAnvilEntity(BlockPos pos, BlockState state) {
        super(BlockProvider.Entity.FORGING_ANVIL_ENTITY, pos, state);
        stored_item = ItemStack.EMPTY;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        stored_item = ItemStack.fromNbt(nbt.getCompound("store_item"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        NbtCompound compound = new NbtCompound();
        stored_item.writeNbt(compound);
        nbt.put("store_item", compound);
        super.writeNbt(nbt);
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

    public void setStoredItem(ItemStack item, World world, BlockState state){
        stored_item = item;
        this.markDirty();
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
    }

    public ItemStack getStoredItem(){
        return stored_item;
    }

    public void setIsclickable(boolean isclickable){
        this.isclickable = isclickable;
    }
}
