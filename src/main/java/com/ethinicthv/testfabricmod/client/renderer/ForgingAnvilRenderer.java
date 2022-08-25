package com.ethinicthv.testfabricmod.client.renderer;

import com.ethinicthv.testfabricmod.block.blockentity.ForgingAnvilEntity;
import com.ethinicthv.testfabricmod.client.renderer.util.ForgingAnvilRenderModify;
import com.ethinicthv.testfabricmod.item.AbstractTestItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
            boolean bl = type instanceof AbstractTestItem || !(type instanceof BlockItem) ;
            if(bl){
                matrices.push();
                ForgingAnvilRenderModify.getModify(i.getItem()).getFunc().apply(matrices);
                int lightabove = WorldRenderer.getLightmapCoordinates(Objects.requireNonNull(entity.getWorld()),entity.getPos());
                context.getItemRenderer().renderItem(i, ModelTransformation.Mode.GROUND,lightabove,overlay,matrices,vertexConsumers,0);
                matrices.pop();
            }
        }
    }
}
