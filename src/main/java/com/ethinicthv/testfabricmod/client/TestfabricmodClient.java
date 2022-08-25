package com.ethinicthv.testfabricmod.client;

import com.ethinicthv.testfabricmod.Testfabricmod;
import com.ethinicthv.testfabricmod.client.renderer.TestRenderer;
import com.ethinicthv.testfabricmod.initializing.BlockProvider;
import com.ethinicthv.testfabricmod.initializing.Init;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class TestfabricmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Init.registerBlockDedicatedRenderer();
    }
}
