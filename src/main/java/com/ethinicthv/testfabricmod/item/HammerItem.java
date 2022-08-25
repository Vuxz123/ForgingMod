package com.ethinicthv.testfabricmod.item;

import com.ethinicthv.testfabricmod.Testfabricmod;
import com.ethinicthv.testfabricmod.block.ForgingAnvil;
import com.ethinicthv.testfabricmod.client.gui.hud.ItemCustomHudRenderer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class HammerItem extends AbstractTestItem implements ItemCustomHudRenderer {

    public HammerItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS));
    }


    @Override
    public Identifier getTexture() {
        return new Identifier(Testfabricmod.NAMESPACE, "textures/gui/icon.png");
    }

    @Override
    public void render(MatrixStack matrices, int scaledHeight, int scaledWidth) {
        setTexture();
        int y = scaledHeight / 2 - 8;
        int x = scaledWidth / 2 + 16 ;
        DrawableHelper.drawTexture(matrices, x, y, 16, 0, 16, 16, 32, 16);
        if(! (ForgingAnvil.getCooldownProgress() <= 0))
        {
            int l = (int) (14f / ForgingAnvil.max_forging_cooldown * ForgingAnvil.getCooldownProgress());
            DrawableHelper.drawTexture(matrices, x, y, 0, 0, l, 16, 32, 16);
        }
    }
}
