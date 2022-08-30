package com.ethinicthv.testfabricmod.mixin;

import net.minecraft.item.ToolItem;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ToolItem.class)
public class ToolItemMixin implements ToolItemImplement {
    @Override
    public VoxelShape[] getHitPoint() {
        VoxelShape shape[] = new VoxelShape[0];
        shape[0] = VoxelShapes.cuboid(0,0,0.25,1,0.125,0.75);
        return shape;
    }

    @Override
    public void setHitPoint(VoxelShape[] hit) {
    }
}
