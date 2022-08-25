package com.ethinicthv.testfabricmod.networking.packet;

import com.ethinicthv.testfabricmod.Testfabricmod;
import net.minecraft.util.Identifier;

public class PacketIdentifier {
    public static Identifier USE_TESTBLOCK = new Identifier(Testfabricmod.NAMESPACE, "usetestblock");
    public static Identifier FORGING = new Identifier(Testfabricmod.NAMESPACE, "forging");
}
