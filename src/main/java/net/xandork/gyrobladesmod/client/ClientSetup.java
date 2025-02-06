package net.xandork.gyrobladesmod.client;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        // Register item renderers in a proper way
        event.enqueueWork(() -> {
            net.xandork.gyrobladesmod.item.client.GyrobladeItemRenderer.registerItemRenderer();
        });
    }
}
