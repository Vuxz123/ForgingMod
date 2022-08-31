package com.ethinicthv.testfabricmod.item.tool;

import com.ethinicthv.testfabricmod.initializing.Util;
import com.ethinicthv.testfabricmod.item.AbstractTestItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

public class ToolItem extends AbstractTestItem {
    private VoxelShape[] hit;

    public VoxelShape[] getHitPoint() {
        return hit;
    }

    public  ToolItem(Settings settings) {
        super(settings);
        hit = new VoxelShape[0];
    }

    public void setHitPoint(VoxelShape[] hit){
        this.hit = hit;
    }

    @SuppressWarnings("AssignmentUsedAsCondition")
    public int checkHit(BlockPos pos, PlayerEntity player, double distance){
        for(int i = 0; i < this.getHitPoint().length; i++){
            boolean bl;
            if(bl = (Util.checkHit(hit[i],player,pos, 2).getType() != HitResult.Type.MISS)){
                return i;
            }
        }
        return -1;
    }

}
