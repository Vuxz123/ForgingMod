package com.ethinicthv.testfabricmod;

import com.ethinicthv.testfabricmod.initializing.Init;
import com.ethinicthv.testfabricmod.networking.PacketHandler;
import net.fabricmc.api.ModInitializer;

public class Testfabricmod implements ModInitializer {

    public static final String NAMESPACE = "testfabricmod";

    @Override
    public void onInitialize() {

        Init.registerBlock();
        Init.registerItem();

        PacketHandler.handleUseTestBlock();
        PacketHandler.handleForgingOnForgingAnvil();

    }

}
