package com.example.rainfog;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

/** 0-100% arasi basit kaydirma cubugu. */
public class RainSlider extends AbstractSliderButton {
    private final String label;
    private final Consumer<Double> onChange;

    public RainSlider(int x, int y, int width, int height, String label, double value, Consumer<Double> onChange) {
        super(x, y, width, height, Component.empty(), value);
        this.label = label;
        this.onChange = onChange;
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.literal(label + ": " + (int) Math.round(this.value * 100.0) + "%"));
    }

    @Override
    protected void applyValue() {
        onChange.accept(this.value);
    }
}
