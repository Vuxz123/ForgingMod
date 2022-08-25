package com.ethinicthv.testfabricmod.mixin;

import com.ethinicthv.testfabricmod.client.gui.hud.ItemCustomHudRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow private int scaledHeight;

    @Shadow private int scaledWidth;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderCrosshair", at = @At("HEAD"))
    private void renderCrosshair(MatrixStack matrices, CallbackInfo info){
        if (client.player.getMainHandStack().getItem() instanceof ItemCustomHudRenderer i){
            i.render(matrices, scaledHeight, scaledWidth);
        }
        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
    }
}
