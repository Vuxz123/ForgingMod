package com.ethinicthv.testfabricmod.client;

import com.ethinicthv.testfabricmod.initializing.Init;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TestfabricmodClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Init.registerBlockDedicatedRenderer();
    }
}
