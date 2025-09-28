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

    // Status Information (read-only)
    public static final ModConfigSpec.ConfigValue<String> FMOD_STATUS;
    public static final ModConfigSpec.ConfigValue<String> AUDIO_SYSTEM;
    public static final ModConfigSpec.IntValue FMOD_ERROR_CODE;
    public static final ModConfigSpec.IntValue ACTIVE_INSTANCES;
    public static final ModConfigSpec.IntValue MAX_INSTANCES;

    // Control Options
    public static final ModConfigSpec.BooleanValue FMOD_ENABLED;
    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING;

    static {
        BUILDER.push("Status");
        FMOD_STATUS = BUILDER
            .comment("Current FMOD initialization status (read-only)")
            .translation("config.fmodapi.status.fmod_status")
            .define("fmodStatus", "Not initialized");
        AUDIO_SYSTEM = BUILDER
            .comment("Current active audio system (read-only)")
            .translation("config.fmodapi.status.audio_system")
            .define("audioSystem", "Pending");
        FMOD_ERROR_CODE = BUILDER
            .comment("Last FMOD error code - 0 = success, 20 = hardware conflict (read-only)")
            .translation("config.fmodapi.status.error_code")
            .defineInRange("fmodErrorCode", -1, -1, 999);
        ACTIVE_INSTANCES = BUILDER
            .comment("Number of currently active sound instances (read-only)")
            .translation("config.fmodapi.status.active_instances")
            .defineInRange("activeInstances", 0, 0, 999);
        MAX_INSTANCES = BUILDER
            .comment("Maximum allowed sound instances (read-only)")
            .translation("config.fmodapi.status.max_instances")
            .defineInRange("maxInstances", 128, 0, 999);
        BUILDER.pop();

        BUILDER.push("Settings");
        FMOD_ENABLED = BUILDER
            .comment("Enable FMOD audio system (takes effect immediately)")
            .translation("config.fmodapi.settings.fmod_enabled")
            .define("fmodEnabled", true);
        DEBUG_LOGGING = BUILDER
            .comment("Enable debug logging for FMOD API")
            .translation("config.fmodapi.settings.debug_logging")
            .define("debugLogging", false);
        BUILDER.pop();
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // When config loads, check if FMOD should be initialized
        FMODSystem.checkConfigAndInit();

        // Update status when config loads
        updateStatus();
    }

    @SubscribeEvent
    static void onReload(final ModConfigEvent.Reloading event) {
        System.out.println("=== FMOD API CONFIG CHANGE DETECTED ===");
        System.out.println("=== FMOD API CONFIG CHANGE DETECTED ===");
        System.out.println("=== FMOD API CONFIG CHANGE DETECTED ===");
        System.out.println("[FMOD API Config] ModConfigEvent.Reloading fired - handling runtime config change");
        // When config is changed in-game, handle FMOD enable/disable
        handleRuntimeConfigChange();
        updateStatus();

        // Force config screen refresh by updating all status values
        forceConfigScreenRefresh();
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
     * Force config screen refresh by triggering value change notifications
     */
    private static void forceConfigScreenRefresh() {
        try {
            // Force all status values to update by setting them again
            // This triggers the config screen to refresh
            var status = FMODAPI.getStatus();
            FMOD_STATUS.set(status.status);
            AUDIO_SYSTEM.set(status.audioSystem);
            FMOD_ERROR_CODE.set(status.errorCode);
            ACTIVE_INSTANCES.set(FMODAPI.getActiveInstanceCount());
            MAX_INSTANCES.set(FMODAPI.getMaxInstanceCount());

            System.out.println("[FMOD API Config] Forced config screen refresh - status updated");
        } catch (Exception e) {
            System.err.println("[FMOD API Config] Failed to force config refresh: " + e.getMessage());
        }
    }

    /**
     * Updates the config status values from current FMOD system state.
     */
    public static void updateStatus() {
        try {
            FMODAPI.FMODStatus status = FMODAPI.getStatus();
            FMOD_STATUS.set(status.status);
            AUDIO_SYSTEM.set(status.audioSystem);
            FMOD_ERROR_CODE.set(status.errorCode);
            ACTIVE_INSTANCES.set(FMODAPI.getActiveInstanceCount());
            MAX_INSTANCES.set(FMODAPI.getMaxInstanceCount());
        } catch (Exception e) {
            // Config not ready yet during initialization
            System.out.println("[FMOD API Config] Status update skipped during initialization");
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
            updateStatus();
            System.out.println("[FMOD API Config] FMOD system reinitialized");
        } catch (Exception e) {
            System.err.println("[FMOD API Config] Failed to reinitialize FMOD: " + e.getMessage());
        }
    }
}