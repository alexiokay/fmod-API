package com.fmodapi;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Configuration for FMOD API.
 * Provides status information and control options for FMOD system.
 */
@EventBusSubscriber(modid = "fmodapi")
public class FMODConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Configuration Options
    public static final ModConfigSpec.BooleanValue FMOD_ENABLED;
    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING;
    public static final ModConfigSpec.IntValue MAX_INSTANCES;
    public static final ModConfigSpec.ConfigValue<String> FMOD_CUSTOM_PATH;

    static {
        FMOD_ENABLED = BUILDER
            .comment("Enable FMOD audio system (takes effect immediately)")
            .translation("config.fmodapi.fmod_enabled")
            .define("fmodEnabled", true);
        DEBUG_LOGGING = BUILDER
            .comment("Enable debug logging for FMOD API - useful for troubleshooting audio issues")
            .translation("config.fmodapi.debug_logging")
            .define("debugLogging", false);
        MAX_INSTANCES = BUILDER
            .comment("Maximum number of concurrent FMOD sound instances (32-4096, default: 512)")
            .translation("config.fmodapi.max_instances")
            .defineInRange("maxInstances", 512, 32, 4096);
        FMOD_CUSTOM_PATH = BUILDER
            .comment("Custom FMOD installation path (leave empty for auto-detection)",
                     "If you installed FMOD Engine in a non-standard location, specify the path here.",
                     "Example: C:\\Program Files (x86)\\FMOD SoundSystem\\FMOD Studio API Windows",
                     "Required files: fmod.dll and fmodstudio.dll",
                     "Compatible with FMOD Engine version 2.02.16",
                     "Download from: https://www.fmod.com/download#fmodengine")
            .translation("config.fmodapi.custom_path")
            .define("fmodCustomPath", "");
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        try {
            // When config loads, check if FMOD should be initialized
            FMODSystem.checkConfigAndInit();
        } catch (Throwable e) {
            // Prevent FMOD initialization failures from crashing Minecraft
            System.err.println("[FMOD API] Failed to initialize FMOD during config load - FMOD will be disabled");
            System.err.println("[FMOD API] This is not critical - audio will fall back to OpenAL");
            System.err.println("[FMOD API] Error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();

            // Ensure FMOD system is marked as failed so it gracefully falls back
            FMODSystem.markInitializationFailed();
        }
    }

    @SubscribeEvent
    static void onReload(final ModConfigEvent.Reloading event) {
        System.out.println("=== FMOD API CONFIG CHANGE DETECTED ===");
        System.out.println("=== FMOD API CONFIG CHANGE DETECTED ===");
        System.out.println("=== FMOD API CONFIG CHANGE DETECTED ===");
        System.out.println("[FMOD API Config] ModConfigEvent.Reloading fired - handling runtime config change");
        // When config is changed in-game, handle FMOD enable/disable
        handleRuntimeConfigChange();
    }

    /**
     * Handle config changes during runtime (in-game config changes)
     */
    private static void handleRuntimeConfigChange() {
        try {
            boolean fmodEnabled = FMOD_ENABLED.get();
            boolean fmodCurrentlyRunning = FMODSystem.isInitialized();

            System.out.println("[FMOD API Config] Runtime config check: fmodEnabled=" + fmodEnabled + ", fmodCurrentlyRunning=" + fmodCurrentlyRunning);

            // Use checkConfigAndInit() which handles routing properly without reinitializing
            // FMOD system stays initialized once loaded, only routing changes based on config
            System.out.println("[FMOD API Config] Updating FMOD routing based on config...");
            try {
                FMODSystem.checkConfigAndInit();
            } catch (Throwable e) {
                System.err.println("[FMOD API Config] Failed to update FMOD routing: " + e.getMessage());
                e.printStackTrace();
            }

            // Status change notifications are handled automatically by FMODSystem
        } catch (Exception e) {
            System.err.println("[FMOD API Config] Failed to handle runtime config change: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Reinitialize FMOD system (useful for troubleshooting).
     */
    public static void reinitializeFMOD() {
        try {
            FMODSystem.shutdown();
            Thread.sleep(500); // Small delay
            FMODSystem.init();
            System.out.println("[FMOD API Config] FMOD system reinitialized");
        } catch (Exception e) {
            System.err.println("[FMOD API Config] Failed to reinitialize FMOD: " + e.getMessage());
        }
    }
}