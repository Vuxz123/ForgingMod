package com.ethinicthv.testfabricmod.client.thread;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class HammerTimer {
    @Environment(EnvType.CLIENT)
    public static int forging_cooldown = 0;
    @Environment(EnvType.CLIENT)
    public static final int max_forging_cooldown = 10;
    @Environment(EnvType.CLIENT)
    public static PlayerEntity p;

    @Environment(EnvType.CLIENT)
    public static Thread a = null;

    @Environment(EnvType.CLIENT)
    public static boolean isCoolDown(){
        return a.isAlive();
    }
    @SuppressWarnings({"UnusedReturnValue", "BusyWait"})
    @Environment(EnvType.CLIENT)
    public static boolean runTimer(){
        a = new Thread(() -> {
            try {
                while(MinecraftClient.getInstance().options.attackKey.isPressed() && MinecraftClient.getInstance().mouse.isCursorLocked()){
                    for(int i = 0; i < max_forging_cooldown; i ++){
                        Thread.sleep(100);
                        if(forging_cooldown > 0){
                            forging_cooldown --;
                        }
                    }

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"timer");

        try {
            a.start();
            forging_cooldown = max_forging_cooldown;
        }catch (RuntimeException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Environment(EnvType.CLIENT)
    public static int getCooldownProgress(){
        return forging_cooldown;
    }
}
