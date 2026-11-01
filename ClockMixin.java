package com.example.rainfog.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.example.rainfog.RainFogConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Sadece istemcide dunya saatini okuyan yeri keser: "gece gorunumu" aciksa
 * Overworld'de gokyuzu gece yarisi (18000) gibi cizilir. Sunucudaki gercek saat degismez.
 */
@Mixin(targets = "net.minecraft.client.ClientClockManager")
public abstract class ClockMixin {
    @ModifyReturnValue(method = "getTotalTicks", at = @At("RETURN"), require = 0)
    private long rainfog$night(long original) {
        if (RainFogConfig.night) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null && mc.level.dimension() == Level.OVERWORLD) {
                return 18000L;
            }
        }
        return original;
    }
}
