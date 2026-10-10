package com.example.client

import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.client.KeyMapping
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundSource

object MuteModClient : ClientModInitializer {

    const val MOD_ID = "mutemod"

    // Register a custom keybind category and the toggle keybinding
    private val MUTE_MOD_CATEGORY: KeyMapping.Category =
        KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "category"))

    val TOGGLE_MUTE_KEY: KeyMapping = KeyMappingHelper.registerKeyMapping(
        KeyMapping(
            "key.mutemod.toggle",              // translation key
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.value,      // unbound by default (GLFW_KEY_UNKNOWN = -1)
            MUTE_MOD_CATEGORY
        )
    )

    // In-memory mute state — true if master sound is currently muted
    @JvmStatic
    var isMuted: Boolean = false
        private set

    // Track the master volume setting so that if the player manually adjusts the slider
    // in Options -> Music & Sounds, we automatically unmute and respect their new setting.
    private var lastKnownMasterOption: Double = -1.0

    override fun onInitializeClient() {
        // Automatically unmute when disconnecting from a server or quitting a singleplayer world
        ClientPlayConnectionEvents.DISCONNECT.register { _, client ->
            if (isMuted) {
                isMuted = false
                client.soundManager.refreshCategoryVolume(SoundSource.MASTER)
            }
        }

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            val masterOption = client.options.getSoundSourceOptionInstance(SoundSource.MASTER)
            val currentMasterOption = masterOption.get()

            // If the player moved the master volume slider in Options, unmute to the new value
            if (lastKnownMasterOption >= 0.0 && currentMasterOption != lastKnownMasterOption) {
                if (isMuted) {
                    isMuted = false
                    client.soundManager.refreshCategoryVolume(SoundSource.MASTER)
                }
            }
            lastKnownMasterOption = currentMasterOption

            // Only process keybind clicks when actively in-game with a player
            val player = client.player ?: return@register

            while (TOGGLE_MUTE_KEY.consumeClick()) {
                isMuted = !isMuted
                client.soundManager.refreshCategoryVolume(SoundSource.MASTER)

                if (isMuted) {
                    player.sendOverlayMessage(
                        Component.translatable("text.mutemod.muted")
                    )
                } else {
                    val percentage = (currentMasterOption * 100).toInt()
                    player.sendOverlayMessage(
                        Component.translatable("text.mutemod.restored", percentage)
                    )
                }
            }
        }
    }
}