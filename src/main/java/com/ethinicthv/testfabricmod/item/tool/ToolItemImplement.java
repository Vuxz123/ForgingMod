package com.ethinicthv.testfabricmod.item.tool;

import com.ethinicthv.testfabricmod.initializing.Util;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

@SuppressWarnings("EmptyMethod")
public interface ToolItemImplement {
    VoxelShape[] getHitPoint();
    void setHitPoint(VoxelShape[] hit);
    @SuppressWarnings("AssignmentUsedAsCondition")
    default int checkHit(BlockPos pos, PlayerEntity player, double distance){
        for(int i = 0; i < this.getHitPoint().length; i++){
            boolean bl;
            BlockHitResult bhr = Util.checkHit(this.getHitPoint()[i],player,pos, distance);
            if(bl = (bhr != null && bhr.getType() != HitResult.Type.MISS)){
                return i;
            }
        }
        return -1;
    }
}
