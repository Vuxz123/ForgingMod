package com.ethinicthv.testfabricmod.initializing;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class Util {

    public static BlockHitResult checkHit(VoxelShape shape, PlayerEntity player, BlockPos pos, double distance){
        Vec3d temp = player.getRotationVector().normalize();
        Vec3d start = player.getCameraPosVec(0.0f);
        Vec3d end = start.add(temp.getX()*distance, temp.getY()*distance, temp.getZ()*distance);
        return shape.raycast(start,end,pos);
    }

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{ shape, VoxelShapes.empty() };

        int times = (to.getHorizontal() - from.getHorizontal() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.union(buffer[1], VoxelShapes.cuboid(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }

    public static VoxelShape[] rotateShape(Direction from, Direction to, VoxelShape[] shape){
        for(int i = 0; i < shape.length; i ++){
            VoxelShape temp = shape[i];
            temp = rotateShape(from, to, temp);
            shape[i] = temp;
        }
        return shape;
    }

}
