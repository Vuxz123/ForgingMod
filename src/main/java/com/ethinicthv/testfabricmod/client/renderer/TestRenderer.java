package com.ethinicthv.testfabricmod.client.renderer;

import com.ethinicthv.testfabricmod.block.blockentity.TestingBlockEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.BlockItem;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class TestRenderer implements BlockEntityRenderer<TestingBlockEntity> {

    private BlockEntityRendererFactory.Context context;

    public TestRenderer(BlockEntityRendererFactory.Context ctx) {
        context = ctx;
    }

    @Override
    public boolean rendersOutsideBoundingBox(TestingBlockEntity blockEntity) {
        return true;
    }

    @Override
    public void render(TestingBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(entity.getItem() == ItemStack.EMPTY ) {
            return;
        }

        ItemStack itemStack = entity.getItem();

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.depthMask(false);
        MinecraftClient.getInstance().getTextureManager().bindTexture(TextureMap.getId(itemStack.getItem()));


        matrices.push();
        matrices.translate(0.5,1.25,0.5);

        float x = (float) (1 - Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 2.0);
        matrices.scale(x,x,x);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 4));
        int lightabove = WorldRenderer.getLightmapCoordinates(entity.getWorld(),entity.getPos().up());

        context.getItemRenderer().renderItem( itemStack , ModelTransformation.Mode.GROUND,lightabove,overlay,matrices,vertexConsumers,0);

        matrices.pop();

        Item type = itemStack.getItem();

        if(type instanceof BlockItem){

            Block b = ((BlockItem) type).getBlock();

            matrices.push();
            Random random = Random.create();
            //BufferBuilder bufferBuilder = (BufferBuilder) vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));
            BufferBuilder bufferBuilder = (BufferBuilder) vertexConsumers.getBuffer(RenderLayer.getTranslucent());
            //context.getRenderManager().renderBlock(Blocks.BLACK_STAINED_GLASS.getDefaultState(), entity.getPos(),entity.getWorld(),matrices, bufferBuilder, false, random);



            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            //matrix4f.multiply(new Quaternion(new Vec3f(0,1,0), 180, true));

            Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(new Identifier("minecraft:block/acacia_leaves"));


            bufferBuilder.vertex(matrix4f,0,0,0).color(255,255,0,50).texture(sprite.getMinU(),sprite.getMinV()).overlay(OverlayTexture.DEFAULT_UV).light(lightabove).normal(matrices.peek().getNormalMatrix(),0,0,-1).next();
            bufferBuilder.vertex(matrix4f,0,1,0).color(255,0,255,50).texture(sprite.getMinU(),sprite.getMaxV()).overlay(OverlayTexture.DEFAULT_UV).light(lightabove).normal(matrices.peek().getNormalMatrix(),0,0,-1).next();
            bufferBuilder.vertex(matrix4f,1,1,0).color(0,255,255,50).texture(sprite.getMaxU(),sprite.getMaxV()).overlay(OverlayTexture.DEFAULT_UV).light(lightabove).normal(matrices.peek().getNormalMatrix(),0,0,-1).next();
            bufferBuilder.vertex(matrix4f,1,0,0).color(127,127,127,50).texture(sprite.getMaxU(),sprite.getMinV()).overlay(OverlayTexture.DEFAULT_UV).light(lightabove).normal(matrices.peek().getNormalMatrix(),0,0,-1).next();

            matrices.pop();

        }

        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);

    }

    @Override
    public int getRenderDistance() {
        return 64;
    }

    @Override
    public boolean isInRenderDistance(TestingBlockEntity blockEntity, Vec3d pos) {
        return BlockEntityRenderer.super.isInRenderDistance(blockEntity, pos);
    }
}
