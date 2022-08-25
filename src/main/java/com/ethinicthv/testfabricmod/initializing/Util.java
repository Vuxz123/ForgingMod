package com.ethinicthv.testfabricmod.initializing;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class Util {

    public static BlockHitResult checkHit(VoxelShape shape, PlayerEntity player, BlockPos pos, double distance){
        Vec3d temp = player.getRotationVector().normalize();
        Vec3d start = player.getCameraPosVec(0.0f);
        Vec3d end = start.add(temp.getX()*distance, temp.getY()*distance, temp.getZ()*distance);
        return shape.raycast(start,end,pos);
    }

}
