package com.example.rainfog.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.example.rainfog.RainFogConfig;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Hava durumu ses kategorisinin (yagmur ve gok gurultusu) seviyesini ayar cubugundaki degerle carpar.
 */
@Mixin(targets = "net.minecraft.client.sounds.SoundEngine")
public abstract class SoundMixin {
    @ModifyReturnValue(method = "calculateVolume(FLnet/minecraft/sounds/SoundSource;)F", at = @At("RETURN"), require = 0)
    private float rainfog$weatherVolume(float original, float volume, SoundSource source) {
        if (source == SoundSource.WEATHER) {
            return original * RainFogConfig.rainVolume;
        }
        return original;
    }
}
