package com.ethinicthv.testfabricmod.item;

import com.ethinicthv.testfabricmod.block.ForgingAnvil;
import com.ethinicthv.testfabricmod.client.gui.hud.DrawManager;
import com.ethinicthv.testfabricmod.client.gui.hud.ItemCustomHudRenderer;
import com.ethinicthv.testfabricmod.client.thread.HammerTimer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AirBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class HammerItem extends AbstractTestItem implements ItemCustomHudRenderer {

    public HammerItem(int maxdamage) {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(maxdamage));
    }


    @Override
    public Identifier getTexture() {
        return DrawManager.ICON;
    }

    @Override
    public void render(MatrixStack matrices, int scaledHeight, int scaledWidth) {
        setTexture();
        int y = scaledHeight / 2 - 8;
        int x = scaledWidth / 2 + 16 ;
        boolean bl = HammerTimer.getCooldownProgress() <= 0;
        assert MinecraftClient.getInstance().crosshairTarget != null;
        BlockPos pos = new BlockPos(MinecraftClient.getInstance().crosshairTarget.getPos());
        assert MinecraftClient.getInstance().world != null;
        boolean bl2 = MinecraftClient.getInstance().world.getBlockState(pos).getBlock() instanceof ForgingAnvil;
        if(bl2){
            if (bl){
                boolean bl3 = ForgingAnvil.canUse(pos, MinecraftClient.getInstance().world);
                if(bl3){
                    DrawableHelper.drawTexture(matrices, x, y, 48, 0, 16, 16, 64, 16);
                }else{
                    DrawableHelper.drawTexture(matrices, x, y, 32, 0, 16, 16, 64, 16);
                }
                return;
            }
        }
        DrawableHelper.drawTexture(matrices, x, y, 16, 0, 16, 16, 64, 16);
        if(!(bl))
        {
            int l = (int) (16f / HammerTimer.max_forging_cooldown * HammerTimer.getCooldownProgress());
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, l, 16, 64, 16);
        }
    }
}
