package com.example.rainfog.mixin;

import com.example.rainfog.RainFogConfig;
import com.example.rainfog.RainSlider;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Envanter ekraninin sol altina yagmur ayar paneli ekler:
 * yagmur ac/kapa, gece gorunumu ac/kapa, siddet ve ses kaydirma cubuklari.
 */
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractContainerScreen<InventoryMenu> {

    protected InventoryScreenMixin(InventoryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Inject(method = "init", at = @At("TAIL"), require = 0)
    private void rainfog$addPanel(CallbackInfo ci) {
        final int w = 112;
        final int h = 20;
        final int gap = 2;
        final int x = 6;
        int y = this.height - 6 - (4 * h + 3 * gap);

        Button rainButton = Button.builder(rainLabel(), b -> {
            RainFogConfig.enabled = !RainFogConfig.enabled;
            RainFogConfig.save();
            b.setMessage(rainLabel());
        }).bounds(x, y, w, h).build();
        this.addRenderableWidget(rainButton);
        y += h + gap;

        Button nightButton = Button.builder(nightLabel(), b -> {
            RainFogConfig.night = !RainFogConfig.night;
            RainFogConfig.save();
            b.setMessage(nightLabel());
        }).bounds(x, y, w, h).build();
        this.addRenderableWidget(nightButton);
        y += h + gap;

        this.addRenderableWidget(new RainSlider(x, y, w, h, "Şiddet", RainFogConfig.rainIntensity, v -> {
            RainFogConfig.rainIntensity = v.floatValue();
            RainFogConfig.save();
        }));
        y += h + gap;

        this.addRenderableWidget(new RainSlider(x, y, w, h, "Ses", RainFogConfig.rainVolume, v -> {
            RainFogConfig.rainVolume = v.floatValue();
            RainFogConfig.save();
        }));
    }

    private static Component rainLabel() {
        return Component.literal("Yağmur: " + (RainFogConfig.enabled ? "AÇIK" : "KAPALI"));
    }

    private static Component nightLabel() {
        return Component.literal("Gece görünümü: " + (RainFogConfig.night ? "AÇIK" : "KAPALI"));
    }
}
