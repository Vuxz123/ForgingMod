package com.ethinicthv.testfabricmod.client.renderer.util;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.Quaternion;

public enum ForgingAnvilRenderModify {
    SWORD((matrices, rotation)->{
        //matrices.translate(0.2f,1.03f,0.325f);
        matrices.translate(0,1.03f,0);
        double x = 0,y = 0;

        switch ((int) rotation){
            case 0 -> {x=0.2;y=0.325;}
            case 90 -> {x=1-0.325;y=0.2;}
            case 180 -> {x=0.8;y=1-0.325;}
            case 270 -> {x=0.325;y=0.8;}
        }

        matrices.translate(x,0,y);

        matrices.multiply(new Quaternion(90,0,-45 + rotation,true));
        matrices.scale(2,2,2);
    }),
    TOOL((matrices, rotation)->{
        //matrices.translate(0.2f,1.03f,0.325f);
        matrices.translate(0,1.03f,0);
        double x = 0,y = 0;

        switch ((int) rotation){
            case 0 -> {x=0.1;y=0.325;}
            case 90 -> {x=1-0.325;y=0.1;}
            case 180 -> {x=0.9;y=1-0.325;}
            case 270 -> {x=0.325;y=0.9;}
        }

        matrices.translate(x,0,y);

        matrices.multiply(new Quaternion(90,0,-45 + rotation,true));
        matrices.scale(2,2,2);
    })
    ,
    DEFAULT((matrices, rotation)->{
        matrices.translate(0.5f,1.03f,0.42f);
        matrices.multiply(new Quaternion(90,0,0 + rotation,true));
    });
    final Fc func;

    ForgingAnvilRenderModify(Fc func){
        this.func = func;
    }

    public Fc getFunc(){
        return func;
    }

    public static ForgingAnvilRenderModify getModify(Item type){
        if(type instanceof SwordItem){
            return SWORD;
        }else if(type instanceof ToolItem){
            return TOOL;
        }
        return DEFAULT;
    }

    public interface Fc{
        void apply(MatrixStack matrices, float rotation);
    }
}
