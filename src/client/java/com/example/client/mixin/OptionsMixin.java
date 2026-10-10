package com.example.client.mixin;

import com.example.client.MuteModClient;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Options.class)
public class OptionsMixin {

    @Inject(method = "getSoundSourceVolume", at = @At("HEAD"), cancellable = true)
    private void mutemod$getSoundSourceVolume(SoundSource soundSource, CallbackInfoReturnable<Float> cir) {
        if (soundSource == SoundSource.MASTER && MuteModClient.isMuted()) {
            cir.setReturnValue(0.0f);
        }
    }

    @Inject(method = "getFinalSoundSourceVolume", at = @At("HEAD"), cancellable = true)
    private void mutemod$getFinalSoundSourceVolume(SoundSource soundSource, CallbackInfoReturnable<Float> cir) {
        if (MuteModClient.isMuted()) {
            cir.setReturnValue(0.0f);
        }
    }
}
