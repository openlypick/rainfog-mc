package com.example.rainfog;

import net.fabricmc.api.ClientModInitializer;

public class RainFogClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        RainFogConfig.load();
    }
}
