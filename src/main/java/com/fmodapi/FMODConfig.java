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
                     "Example: C:\\MyCustomPath\\FMOD\\api\\core\\lib\\x64\\",
                     "Required files: fmod.dll and fmodstudio.dll",
                     "Compatible with FMOD Engine version 2.02.16 or higher",
                     "Download from: https://www.fmod.com/download#fmodengine")
            .translation("config.fmodapi.custom_path")
            .define("fmodCustomPath", "");
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // When config loads, check if FMOD should be initialized
        FMODSystem.checkConfigAndInit();
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

            if (fmodEnabled && !fmodCurrentlyRunning) {
                // User enabled FMOD but it's not running - initialize it
                System.out.println("[FMOD API Config] FMOD enabled in config - initializing...");
                FMODSystem.checkConfigAndInit();
                // Cache invalidation happens automatically in notifyStatusChange()
            } else if (!fmodEnabled && fmodCurrentlyRunning) {
                // User disabled FMOD but it's running - shut it down
                System.out.println("[FMOD API Config] FMOD disabled in config - shutting down...");
                FMODSystem.shutdown();
                // Cache invalidation happens automatically in notifyStatusChange()
            } else {
                System.out.println("[FMOD API Config] No FMOD state change needed");
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