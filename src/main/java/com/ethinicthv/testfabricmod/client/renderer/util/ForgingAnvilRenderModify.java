package com.ethinicthv.testfabricmod.client.renderer.util;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.Quaternion;

public enum ForgingAnvilRenderModify {
    SWORD((matrices)->{
        matrices.translate(0.2f,1.03f,0.325f);
        matrices.multiply(new Quaternion(90,0,-45,true));
        matrices.scale(2,2,2);
    }),
    DEFAULT((matrices)->{
        matrices.translate(0.5f,1.03f,0.42f);
        matrices.multiply(new Quaternion(90,0,0,true));
    });
    final Fc func;

    ForgingAnvilRenderModify(Fc func){
        this.func = func;
    }

    public Fc getFunc(){
        return func;
    }

    public static ForgingAnvilRenderModify getModify(Item type){
        if(type instanceof ToolItem){
            return SWORD;
        }
        return DEFAULT;
    }

    public interface Fc{
        void apply(MatrixStack matrices);
    }
}
