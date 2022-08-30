package com.ethinicthv.testfabricmod.mixin;

import com.ethinicthv.testfabricmod.item.tool.ToolItemImplement;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ToolItem.class)
public abstract class ToolItemMixin extends Item implements ToolItemImplement {
    public ToolItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape[] getHitPoint() {
        VoxelShape shape[] = new VoxelShape[1];
        shape[0] = VoxelShapes.cuboid(0,0,0.25,1,0.125,0.75);
        return shape;
    }

    @Override
    public void setHitPoint(VoxelShape[] hit) {
    }
}
