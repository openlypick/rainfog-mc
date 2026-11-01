package com.example.rainfog.mixin;

import com.example.rainfog.RainFogConfig;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Sadece ISTEMCI tarafindaki dunyada yagmur siddetini ayarlanan degere sabitler.
 * Sunucu (ve tek oyuncuda dahili sunucu) dunyasi etkilenmez.
 * Iris/Unbound "rainStrength", yagmur sesi, gokyuzu kararmasi ve getRainLevel()
 * okuyan diger modlar bu degeri gorur.
 */
@Mixin(Level.class)
public abstract class WorldMixin {
    @Inject(method = "getRainLevel", at = @At("HEAD"), cancellable = true)
    private void rainfog$alwaysRain(float partialTick, CallbackInfoReturnable<Float> cir) {
        Level self = (Level) (Object) this;
        if (RainFogConfig.enabled && self.isClientSide() && self.dimension() == Level.OVERWORLD) {
            cir.setReturnValue(RainFogConfig.rainIntensity);
        }
    }
}
