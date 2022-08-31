package com.ethinicthv.testfabricmod.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@SuppressWarnings("ALL")
@Environment(EnvType.CLIENT)
public interface ItemCustomHudRenderer {
    default void setTexture(){
        RenderSystem.setShaderTexture(0, this.getTexture());
    }
    Identifier getTexture();

    void render(MatrixStack matrices, int scaledHeight, int scaledWidth);
}
