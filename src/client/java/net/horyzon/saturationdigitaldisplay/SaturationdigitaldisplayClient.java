package net.horyzon.saturationdigitaldisplay;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.horyzon.SerialHandler;

public class SaturationdigitaldisplayClient implements ClientModInitializer {
    private int tick = 0;

    @Override
    public void onInitializeClient() {

        SerialHandler.connect("COM3");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {
                tick++;
                if (tick >= 10) {
                    tick = 0;
                    Saturation.saveSaturationData(client);
                }
            }
        });
    }
}
