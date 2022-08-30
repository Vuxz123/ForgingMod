package com.ethinicthv.testfabricmod.item.tool;

import com.ethinicthv.testfabricmod.initializing.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Implements;

public interface ToolItemImplement {
    public VoxelShape[] getHitPoint();
    public void setHitPoint(VoxelShape[] hit);
    public default int checkHit(BlockPos pos, PlayerEntity player, double distance){
        for(int i = 0; i < this.getHitPoint().length; i++){
            boolean bl;
            if(bl = (Util.checkHit(this.getHitPoint()[i],player,pos, 2).getType() != HitResult.Type.MISS)){
                return i;
            }
        }
        return -1;
    }
}
