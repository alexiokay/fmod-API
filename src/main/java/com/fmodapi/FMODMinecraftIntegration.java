package com.fmodapi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.sounds.SoundManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

/**
 * Integrates FMOD with Minecraft's native audio system.
 * Handles volume synchronization and pause/resume based on game state.
 */
@EventBusSubscriber(modid = "fmodapi", value = Dist.CLIENT)
public class FMODMinecraftIntegration {

    private static boolean wasPaused = false;
    private static float lastMasterVolume = 1.0f;
    private static float lastMusicVolume = 1.0f;
    private static float lastSoundVolume = 1.0f;

    /**
     * Monitor Minecraft's game state and volume settings every tick
     */
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        if (!FMODAPI.isAvailable()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null) {
            return;
        }

        // Handle pause/resume - simple and reliable approach
        boolean hasScreenOpen = minecraft.screen != null;
        if (hasScreenOpen != wasPaused) {
            if (hasScreenOpen) {
                System.out.println("[FMOD Integration] Screen detected - pausing FMOD sounds");
                FMODAPI.pauseAllSounds();
            } else {
                System.out.println("[FMOD Integration] No screen - resuming FMOD sounds");
                FMODAPI.resumeAllSounds();
            }
            wasPaused = hasScreenOpen;
        }

        // Handle volume changes using the correct NeoForge 1.21.1 API
        Options options = minecraft.options;
        if (options != null) {
            // Get the volume values from game options (0.0 to 1.0)
            float masterVolume = options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.MASTER);
            float musicVolume = options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.MUSIC);
            float soundVolume = options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.BLOCKS);

            // Check if any volume has changed
            if (masterVolume != lastMasterVolume || musicVolume != lastMusicVolume || soundVolume != lastSoundVolume) {
                // Calculate combined volume (master * average of music/sound)
                float combinedVolume = masterVolume * ((musicVolume + soundVolume) / 2.0f);

                System.out.println("[FMOD Integration] Volume changed - Master: " + masterVolume +
                                 ", Music: " + musicVolume + ", Sound: " + soundVolume +
                                 " -> Combined: " + combinedVolume);

                FMODAPI.setMasterVolume(combinedVolume);

                lastMasterVolume = masterVolume;
                lastMusicVolume = musicVolume;
                lastSoundVolume = soundVolume;
            }
        }
    }


    /**
     * Reset integration state when world changes
     */
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        // Reset pause state when world changes
        if (event.getLevel().isClientSide()) {
            wasPaused = false;
            // Ensure sounds are not stuck in paused state
            if (FMODAPI.isAvailable()) {
                FMODAPI.resumeAllSounds();
            }
        }
    }

    /**
     * Initialize integration - call this once during mod setup
     */
    public static void initialize() {
        System.out.println("[FMOD Integration] Minecraft native integration initialized");

        // Set initial volume from current Minecraft settings
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft != null && minecraft.options != null) {
            Options options = minecraft.options;
            float masterVolume = options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.MASTER);
            float musicVolume = options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.MUSIC);
            float soundVolume = options.getSoundSourceVolume(net.minecraft.sounds.SoundSource.BLOCKS);

            float combinedVolume = masterVolume * ((musicVolume + soundVolume) / 2.0f);
            FMODAPI.setMasterVolume(combinedVolume);

            lastMasterVolume = masterVolume;
            lastMusicVolume = musicVolume;
            lastSoundVolume = soundVolume;

            System.out.println("[FMOD Integration] Initial volume set to: " + combinedVolume +
                             " (Master: " + masterVolume + ", Music: " + musicVolume + ", Sound: " + soundVolume + ")");
        }
    }
}