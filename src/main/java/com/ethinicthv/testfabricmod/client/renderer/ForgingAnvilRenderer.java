package com.ethinicthv.testfabricmod.client.renderer;

import com.ethinicthv.testfabricmod.block.blockentity.ForgingAnvilEntity;
import com.ethinicthv.testfabricmod.client.renderer.util.ForgingAnvilRenderModify;
import com.ethinicthv.testfabricmod.item.AbstractTestItem;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;

import java.util.Objects;

public class ForgingAnvilRenderer implements BlockEntityRenderer<ForgingAnvilEntity> {

    private final BlockEntityRendererFactory.Context context;

    public ForgingAnvilRenderer(BlockEntityRendererFactory.Context ctx) {
        context = ctx;
    }

    @Override
    public void render(ForgingAnvilEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack i = entity.getStoredItem();
        if(i != ItemStack.EMPTY){
            Item type = i.getItem();
            @SuppressWarnings("ConditionCoveredByFurtherCondition") boolean bl = type instanceof AbstractTestItem || !(type instanceof BlockItem) ;
            if(bl){
                matrices.push();
                ForgingAnvilRenderModify.getModify(i.getItem()).getFunc().apply(matrices, getFacingRotation(entity));
                //rotate(matrices,entity);
                int lightabove = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(entity.getWorld()),entity.getPos());
                context.getItemRenderer().renderItem(i, ModelTransformation.Mode.GROUND,lightabove,overlay,matrices,vertexConsumers,0);
                matrices.pop();
            }
        }
    }

    public static float getFacingRotation(ForgingAnvilEntity entity){
        float r = 0;
        if(Objects.requireNonNull(entity.getWorld()).getBlockState(entity.getPos()) == Blocks.AIR.getDefaultState()){
            return r;
        }
        switch (entity.getWorld().getBlockState(entity.getPos()).get(Properties.HORIZONTAL_FACING)){
            case EAST -> r = 90f;
            case SOUTH -> r = 180f;
            case WEST -> r = 270f;
        }
        return r;
    }

    public static void rotate(MatrixStack matrices, ForgingAnvilEntity entity){
        float r = getFacingRotation(entity);
        matrices.multiply(new Quaternion(Direction.UP.getUnitVector(), r, true));
    }
}
