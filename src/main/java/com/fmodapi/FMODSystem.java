package com.fmodapi;

import org.lwjgl.PointerBuffer;
import org.lwjgl.fmod.FMOD;
import org.lwjgl.fmod.FMODStudio;
import org.lwjgl.fmod.FMOD_3D_ATTRIBUTES;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Core FMOD system management.
 * Handles initialization, shutdown, and provides centralized FMOD functionality.
 */
public class FMODSystem {
    private static long fmodSystem = 0;
    private static boolean isInitialized = false;
    private static boolean initializationFailed = false;

    // Status tracking
    private static String currentStatus = "Not initialized";
    private static String currentAudioSystem = "Pending";
    private static int currentErrorCode = -1;

    // Instance management
    private static final Map<String, Long> activeInstances = new ConcurrentHashMap<>();
    private static final int MAX_INSTANCES = 128;

    // Track loaded banks for automatic reloading
    private static final Map<String, byte[]> loadedBanks = new ConcurrentHashMap<>();

    // Bank registration system - stores banks to be loaded when FMOD initializes
    private static final List<BankRegistration> registeredBanks = new CopyOnWriteArrayList<>();

    // Event listeners for status changes
    private static final List<StatusChangeListener> statusListeners = new CopyOnWriteArrayList<>();

    /**
     * Represents a registered bank that will be loaded when FMOD initializes
     */
    private static class BankRegistration {
        final Class<?> modClass;
        final String resourcePath;

        BankRegistration(Class<?> modClass, String resourcePath) {
            this.modClass = modClass;
            this.resourcePath = resourcePath;
        }
    }

    public interface StatusChangeListener {
        void onStatusChanged();
    }

    // Color codes for logging
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    /**
     * Pre-initialization - load native libraries with fallback support
     */
    public static void preInit() {
        try {
            // Try loading from JAR resources first
            loadNativeLibraries();
            log(GREEN + "FMOD native libraries loaded successfully from JAR resources" + RESET);
        } catch (Exception jarException) {
            log(YELLOW + "JAR library loading failed: " + jarException.getMessage() + RESET);
            log(YELLOW + "Attempting fallback to system-installed FMOD libraries..." + RESET);

            try {
                // Fallback to system-installed FMOD libraries
                loadSystemLibraries();
                log(GREEN + "FMOD native libraries loaded successfully from system PATH" + RESET);
            } catch (Exception systemException) {
                logError(RED + "Failed to load FMOD libraries from both JAR and system:" + RESET);
                logError(RED + "  JAR error: " + jarException.getMessage() + RESET);
                logError(RED + "  System error: " + systemException.getMessage() + RESET);
                logError(RED + "FMOD will not be available - falling back to OpenAL" + RESET);
                initializationFailed = true;
            }
        }
    }

    /**
     * Initialize FMOD system
     */
    public static void init() {
        if (isInitialized) {
            return;
        }

        // Reset initialization failed flag when explicitly called
        initializationFailed = false;

        // Skip on server side
        if (!isClientSide()) {
            log(GREEN + "FMOD initialization skipped on server side" + RESET);
            currentStatus = "Skipped (server side)";
            currentAudioSystem = "None";
            currentErrorCode = 0;
            return;
        }

        // Check if FMOD is disabled in config
        boolean fmodEnabled = true; // Default to ENABLED
        try {
            fmodEnabled = FMODConfig.FMOD_ENABLED.get();
            if (!fmodEnabled) {
                log(YELLOW + "FMOD initialization skipped - disabled in config" + RESET);
                currentStatus = "Disabled in config";
                currentAudioSystem = "OpenAL";
                currentErrorCode = 0;
                initializationFailed = true; // Prevent further attempts
                return;
            }
        } catch (Exception e) {
            // Config not ready yet, default to ENABLED for immediate availability
            log(YELLOW + "Config not available during early initialization, defaulting to ENABLED" + RESET);
            log(YELLOW + "FMOD will initialize now and can be disabled later via config" + RESET);
            // Continue with initialization
        }

        try {
            // Ensure native libraries are loaded before accessing FMOD classes
            preInit();
        } catch (Exception e) {
            logError(RED + "Failed to pre-initialize FMOD native libraries: " + e.getMessage() + RESET);
            currentStatus = "PreInit failed";
            currentAudioSystem = "None";
            currentErrorCode = -1;
            initializationFailed = true;
            return;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Create FMOD Studio system
            PointerBuffer systemPtr = stack.mallocPointer(1);
            int result = FMODStudio.FMOD_Studio_System_Create(systemPtr, FMOD.FMOD_VERSION);

            if (result != FMOD.FMOD_OK) {
                logError(RED + "FMOD system creation failed: error code=" + result + RESET);
                currentStatus = "Create failed";
                currentAudioSystem = "None";
                currentErrorCode = result;
                initializationFailed = true;
                return;
            }

            fmodSystem = systemPtr.get(0);

            // Configure output for better compatibility
            configureLowLevelSystem(stack);

            // Initialize the system
            int maxChannels = 128;
            int studioFlags = FMODStudio.FMOD_STUDIO_INIT_NORMAL;
            int flags = FMOD.FMOD_INIT_NORMAL | FMOD.FMOD_INIT_MIX_FROM_UPDATE;

            result = FMODStudio.FMOD_Studio_System_Initialize(
                fmodSystem, maxChannels, studioFlags, flags, 0);

            if (result != FMOD.FMOD_OK) {
                logError(RED + "FMOD system initialization failed: error code=" + result + RESET);
                currentStatus = "Initialize failed";
                currentAudioSystem = "None";
                currentErrorCode = result;
                initializationFailed = true;
                fmodSystem = 0;
                return;
            }

            // Configure 3D audio settings for proper distance attenuation
            PointerBuffer coreSystemPtr = stack.mallocPointer(1);
            result = FMODStudio.FMOD_Studio_System_GetCoreSystem(fmodSystem, coreSystemPtr);
            if (result == FMOD.FMOD_OK) {
                long coreSystem = coreSystemPtr.get(0);

                // Set 3D settings - distance units in meters (Minecraft blocks)
                // distancefactor: 1.0 = 1 meter per unit
                // rolloffscale: 1.0 = normal rolloff
                // dopplerscale: 1.0 = normal doppler
                result = FMOD.FMOD_System_Set3DSettings(coreSystem, 1.0f, 1.0f, 1.0f);
                if (result == FMOD.FMOD_OK) {
                    log(GREEN + "FMOD 3D audio settings configured - 1 unit = 1 block" + RESET);
                } else {
                    log(YELLOW + "FMOD 3D audio settings failed: " + result + RESET);
                }

                // Set number of 3D listeners to 1 (for the player)
                result = FMOD.FMOD_System_Set3DNumListeners(coreSystem, 1);
                if (result != FMOD.FMOD_OK) {
                    log(YELLOW + "FMOD 3D listener count setting failed: " + result + RESET);
                }
            } else {
                log(YELLOW + "Failed to get FMOD core system for 3D configuration: " + result + RESET);
            }

            isInitialized = true;
            currentStatus = "Successfully initialized";
            currentAudioSystem = "FMOD";
            currentErrorCode = 0;

            log(GREEN + "FMOD API system successfully initialized with 3D audio" + RESET);

            // Load all registered banks
            loadRegisteredBanks();

        } catch (Exception e) {
            logError(RED + "FMOD system initialization failed with exception: " + e.getMessage() + RESET);
            currentStatus = "Exception: " + e.getMessage();
            currentAudioSystem = "None";
            currentErrorCode = -1;
            initializationFailed = true;
            fmodSystem = 0;
        }
    }

    /**
     * Configure low-level FMOD system for better compatibility
     */
    private static void configureLowLevelSystem(MemoryStack stack) {
        try {
            PointerBuffer lowLevelSystem = stack.mallocPointer(1);
            int result = FMODStudio.FMOD_Studio_System_GetCoreSystem(fmodSystem, lowLevelSystem);

            if (result == FMOD.FMOD_OK) {
                long coreSystem = lowLevelSystem.get(0);
                // Set output type to WASAPI for better shared mode support on Windows
                result = FMOD.FMOD_System_SetOutput(coreSystem, FMOD.FMOD_OUTPUTTYPE_WASAPI);
                if (result != FMOD.FMOD_OK) {
                    log(YELLOW + "FMOD failed to set WASAPI output (code=" + result + "), using default output" + RESET);
                } else {
                    log(GREEN + "FMOD using WASAPI output for shared mode compatibility" + RESET);
                }
            }
        } catch (Exception e) {
            log(YELLOW + "Failed to configure low-level FMOD system: " + e.getMessage() + RESET);
        }
    }


    /**
     * Load native FMOD libraries from resources
     */
    private static void loadNativeLibraries() throws Exception {
        loadLibraryFromResource("/libraries/fmod.dll", "fmod");
        loadLibraryFromResource("/libraries/fmodstudio.dll", "fmodstudio");
    }

    /**
     * Fallback method to load FMOD libraries from system installation
     */
    private static void loadSystemLibraries() throws Exception {
        // Try loading in priority order:
        // 1. Custom path from config (highest priority)
        // 2. System PATH
        // 3. Common installation paths

        // 1. Try custom path first
        if (tryLoadFromCustomPath()) {
            return;
        }

        // 2. Try system PATH
        if (tryLoadFromSystemPath()) {
            return;
        }

        // 3. Try common installation paths
        if (tryLoadFromCommonPaths()) {
            return;
        }

        throw new Exception("Failed to load FMOD libraries from custom path, system PATH, and common installation locations");
    }

    /**
     * Try loading from user-configured custom path
     */
    private static boolean tryLoadFromCustomPath() {
        try {
            String customPath = FMODConfig.FMOD_CUSTOM_PATH.get();
            if (customPath == null || customPath.trim().isEmpty()) {
                return false; // No custom path configured
            }

            String normalizedPath = customPath.trim();
            if (!normalizedPath.endsWith("\\") && !normalizedPath.endsWith("/")) {
                normalizedPath += "\\";
            }

            loadLibrariesFromPath(normalizedPath);
            log(GREEN + "Successfully loaded FMOD libraries from custom path: " + normalizedPath + RESET);
            return true;

        } catch (Exception e) {
            log(YELLOW + "Custom path loading failed: " + e.getMessage() + RESET);
            return false;
        }
    }

    /**
     * Try loading from system PATH
     */
    private static boolean tryLoadFromSystemPath() {
        try {
            System.loadLibrary("fmod");
            System.loadLibrary("fmodstudio");
            log(GREEN + "Successfully loaded FMOD libraries from system PATH" + RESET);
            return true;
        } catch (UnsatisfiedLinkError e) {
            log(YELLOW + "System PATH loading failed: " + e.getMessage() + RESET);
            return false;
        }
    }

    /**
     * Try loading from common installation paths
     */
    private static boolean tryLoadFromCommonPaths() {
        String[] commonPaths = {
            "C:\\Program Files (x86)\\FMOD SoundSystem\\FMOD Studio API Windows\\api\\core\\lib\\x64\\",
            "C:\\Program Files\\FMOD SoundSystem\\FMOD Studio API Windows\\api\\core\\lib\\x64\\",
            "C:\\Program Files (x86)\\FMOD\\api\\core\\lib\\x64\\",
            "C:\\Program Files\\FMOD\\api\\core\\lib\\x64\\",
            ".\\fmod\\",
            ".\\libraries\\"
        };

        for (String path : commonPaths) {
            try {
                loadLibrariesFromPath(path);
                log(GREEN + "Successfully loaded FMOD libraries from: " + path + RESET);
                return true;
            } catch (Exception e) {
                log(YELLOW + "Failed to load from " + path + ": " + e.getMessage() + RESET);
            }
        }

        return false;
    }

    /**
     * Load FMOD libraries from a specific path (with recursive scanning)
     */
    private static void loadLibrariesFromPath(String basePath) throws Exception {
        // First try direct path (for exact folder specification)
        if (tryLoadDirectPath(basePath)) {
            return;
        }

        // If direct path fails, scan subfolders recursively
        String[] foundPaths = scanForFMODLibraries(basePath, 4); // Max 4 levels deep
        if (foundPaths != null) {
            loadLibrariesFromFoundPaths(foundPaths[0], foundPaths[1]);
            return;
        }

        throw new Exception("FMOD DLLs not found in " + basePath + " or its subfolders (searched 4 levels deep)");
    }

    /**
     * Try loading from direct path (exact folder)
     */
    private static boolean tryLoadDirectPath(String basePath) {
        try {
            String fmodPath = basePath + "fmod.dll";
            String fmodStudioPath = basePath + "fmodstudio.dll";

            java.io.File fmodFile = new java.io.File(fmodPath);
            java.io.File fmodStudioFile = new java.io.File(fmodStudioPath);

            if (fmodFile.exists() && fmodStudioFile.exists()) {
                loadLibrariesFromFoundPaths(fmodPath, fmodStudioPath);
                return true;
            }
        } catch (Exception e) {
            // Continue to subfolder scanning
        }
        return false;
    }

    /**
     * Recursively scan for FMOD DLLs in subfolders
     */
    private static String[] scanForFMODLibraries(String basePath, int maxDepth) {
        return scanForFMODLibrariesRecursive(new java.io.File(basePath), maxDepth, 0);
    }

    private static String[] scanForFMODLibrariesRecursive(java.io.File dir, int maxDepth, int currentDepth) {
        if (currentDepth > maxDepth || !dir.exists() || !dir.isDirectory()) {
            return null;
        }

        // Check current directory for both DLLs
        java.io.File fmodFile = new java.io.File(dir, "fmod.dll");
        java.io.File fmodStudioFile = new java.io.File(dir, "fmodstudio.dll");

        if (fmodFile.exists() && fmodStudioFile.exists()) {
            log(GREEN + "Found FMOD DLLs in: " + dir.getAbsolutePath() + RESET);
            return new String[]{fmodFile.getAbsolutePath(), fmodStudioFile.getAbsolutePath()};
        }

        // Recursively check subdirectories
        java.io.File[] subdirs = dir.listFiles(java.io.File::isDirectory);
        if (subdirs != null) {
            for (java.io.File subdir : subdirs) {
                String[] result = scanForFMODLibrariesRecursive(subdir, maxDepth, currentDepth + 1);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    /**
     * Load FMOD libraries from found absolute paths
     */
    private static void loadLibrariesFromFoundPaths(String fmodPath, String fmodStudioPath) throws Exception {
        try {
            System.load(fmodPath);
            log("Loaded fmod.dll from: " + fmodPath);
        } catch (UnsatisfiedLinkError e) {
            throw new Exception("Failed to load fmod.dll from " + fmodPath + ": " + e.getMessage());
        }

        try {
            System.load(fmodStudioPath);
            log("Loaded fmodstudio.dll from: " + fmodStudioPath);
        } catch (UnsatisfiedLinkError e) {
            throw new Exception("Failed to load fmodstudio.dll from " + fmodStudioPath + ": " + e.getMessage());
        }
    }

    private static void loadLibraryFromResource(String resourcePath, String libraryName) throws Exception {
        try (InputStream in = FMODSystem.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new RuntimeException("Could not find native library resource: " + resourcePath);
            }

            // Create temporary directory
            Path tempDir = Files.createTempDirectory("fmodapi-libs");
            tempDir.toFile().deleteOnExit();

            // Create temporary file for the library
            Path libraryFile = tempDir.resolve(libraryName + ".dll");
            libraryFile.toFile().deleteOnExit();

            // Extract library to temporary file
            try (FileOutputStream out = new FileOutputStream(libraryFile.toFile())) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            // Load the library using absolute path
            System.load(libraryFile.toAbsolutePath().toString());
            log("Loaded native library: " + libraryName);
        }
    }


    /**
     * Update FMOD system - should be called every tick
     */
    public static void update() {
        if (!isInitialized || fmodSystem == 0) {
            return;
        }

        try {
            // Clean up finished instances
            cleanupFinishedInstances();

            // Update FMOD system
            int result = FMODStudio.FMOD_Studio_System_Update(fmodSystem);
            if (result != FMOD.FMOD_OK) {
                logError(RED + "FMOD system update failed: error code=" + result + RESET);
            }
        } catch (Exception e) {
            logError(RED + "Exception during FMOD update: " + e.getMessage() + RESET);
        }
    }

    /**
     * Check config and initialize FMOD if needed, then set routing behavior
     * FMOD system stays initialized once loaded, only routing changes based on config
     */
    public static void checkConfigAndInit() {
        try {
            boolean fmodEnabled = FMODConfig.FMOD_ENABLED.get();

            // Initialize FMOD once if not already initialized
            if (!isInitialized) {
                log(GREEN + "Initializing FMOD system (one-time initialization)..." + RESET);
                initializationFailed = false;
                init();
            }

            // Update routing behavior based on config
            if (fmodEnabled) {
                log(GREEN + "FMOD routing ENABLED - sounds will use FMOD system" + RESET);
                currentAudioSystem = "FMOD";

                // Load banks when enabling FMOD routing
                if (isInitialized) {
                    log(GREEN + "Loading banks after enabling FMOD routing..." + RESET);
                    loadRegisteredBanks();
                }
            } else {
                log(YELLOW + "FMOD routing DISABLED - sounds will use OpenAL fallback" + RESET);
                currentAudioSystem = "OpenAL";
                currentStatus = "Routing disabled";
            }
        } catch (Exception e) {
            log(YELLOW + "Config still not available: " + e.getMessage() + RESET);
        }
    }

    /**
     * Shutdown FMOD system
     */
    public static void shutdown() {
        if (!isInitialized || fmodSystem == 0) {
            return;
        }

        try {
            // Clean up all instances
            cleanupAllInstances();

            // Release FMOD system
            int result = FMODStudio.FMOD_Studio_System_Release(fmodSystem);
            if (result != FMOD.FMOD_OK) {
                logError(RED + "FMOD system release failed: error code=" + result + RESET);
            } else {
                log(GREEN + "FMOD system shutdown successfully" + RESET);
            }
        } catch (Exception e) {
            logError(RED + "Exception during FMOD shutdown: " + e.getMessage() + RESET);
        } finally {
            fmodSystem = 0;
            isInitialized = false;
            currentStatus = "Shutdown";
            currentAudioSystem = "None";

        }
    }


    /**
     * Clean up finished FMOD instances
     */
    private static void cleanupFinishedInstances() {
        if (activeInstances.isEmpty()) return;

        Iterator<Map.Entry<String, Long>> iterator = activeInstances.entrySet().iterator();
        int cleanedCount = 0;

        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            long instance = entry.getValue();

            try (MemoryStack stack = MemoryStack.stackPush()) {
                var playbackState = stack.mallocInt(1);
                int result = FMODStudio.FMOD_Studio_EventInstance_GetPlaybackState(instance, playbackState);

                if (result == FMOD.FMOD_OK) {
                    int state = playbackState.get(0);
                    if (state == FMODStudio.FMOD_STUDIO_PLAYBACK_STOPPED ||
                        state == FMODStudio.FMOD_STUDIO_PLAYBACK_STOPPING) {
                        FMODStudio.FMOD_Studio_EventInstance_Release(instance);
                        iterator.remove();
                        cleanedCount++;
                    }
                } else {
                    // Invalid instance, clean it up
                    FMODStudio.FMOD_Studio_EventInstance_Release(instance);
                    iterator.remove();
                    cleanedCount++;
                }
            } catch (Exception e) {
                // Error accessing instance, remove it
                iterator.remove();
                cleanedCount++;
            }
        }

        if (cleanedCount > 0) {
            log(GREEN + "Cleaned up " + cleanedCount + " finished FMOD instances" + RESET);
        }
    }

    /**
     * Clean up all active instances
     */
    private static void cleanupAllInstances() {
        for (long instance : activeInstances.values()) {
            try {
                FMODStudio.FMOD_Studio_EventInstance_Stop(instance, FMODStudio.FMOD_STUDIO_STOP_IMMEDIATE);
                FMODStudio.FMOD_Studio_EventInstance_Release(instance);
            } catch (Exception e) {
                // Ignore cleanup errors
            }
        }
        activeInstances.clear();
    }

    // Getter methods for status
    public static boolean isInitialized() { return isInitialized; }
    public static boolean hasInitializationFailed() { return initializationFailed; }
    public static String getCurrentStatus() { return currentStatus; }
    public static String getCurrentAudioSystem() { return currentAudioSystem; }
    public static int getCurrentErrorCode() { return currentErrorCode; }
    public static long getSystemHandle() { return fmodSystem; }
    public static Map<String, Long> getActiveInstances() { return activeInstances; }
    public static int getMaxInstances() { return MAX_INSTANCES; }

    /**
     * Store bank data for automatic reloading
     * @param bankName Name of the bank file
     * @param bankData Bank file data
     */
    public static void storeBankData(String bankName, byte[] bankData) {
        System.out.println("[FMOD API] storeBankData() called for: " + bankName + " with " + bankData.length + " bytes");
        loadedBanks.put(bankName, bankData.clone());
        System.out.println("[FMOD API] loadedBanks map now contains " + loadedBanks.size() + " banks");
        log(GREEN + "Stored bank data for reloading: " + bankName + " (" + bankData.length + " bytes)" + RESET);
    }


    /**
     * Register a listener for FMOD status changes
     */
    public static void addStatusListener(StatusChangeListener listener) {
        statusListeners.add(listener);
    }

    /**
     * Unregister a status change listener
     */
    public static void removeStatusListener(StatusChangeListener listener) {
        statusListeners.remove(listener);
    }


    /**
     * Load all banks that have been registered by mods
     */
    private static void loadRegisteredBanks() {
        if (registeredBanks.isEmpty()) {
            log(GREEN + "No registered banks to load" + RESET);
            return;
        }

        log(GREEN + "Loading " + registeredBanks.size() + " registered banks..." + RESET);
        int successCount = 0;

        for (BankRegistration registration : registeredBanks) {
            try {
                boolean success = FMODAPI.loadBankFromResource(registration.modClass, registration.resourcePath);
                if (success) {
                    successCount++;
                    log(GREEN + "Loaded registered bank: " + registration.resourcePath + RESET);
                } else {
                    logError(RED + "Failed to load registered bank: " + registration.resourcePath + RESET);
                }
            } catch (Exception e) {
                logError(RED + "Exception loading registered bank " + registration.resourcePath + ": " + e.getMessage() + RESET);
            }
        }

        log(GREEN + "Registered bank loading complete: " + successCount + "/" + registeredBanks.size() + " banks loaded successfully" + RESET);
    }

    /**
     * Register a bank to be loaded automatically when FMOD initializes.
     * This method is safe to call during mod initialization, regardless of FMOD state.
     *
     * @param modClass Class from the mod (for resource loading)
     * @param resourcePath Path to bank within mod JAR
     * @return true (always succeeds)
     */
    public static boolean registerBank(Class<?> modClass, String resourcePath) {
        registeredBanks.add(new BankRegistration(modClass, resourcePath));
        log(GREEN + "Registered bank for automatic loading: " + resourcePath + " from " + modClass.getSimpleName() + RESET);

        // If FMOD is already initialized, load the bank immediately
        if (isInitialized) {
            try {
                boolean success = FMODAPI.loadBankFromResource(modClass, resourcePath);
                log(success ? GREEN + "Immediately loaded registered bank: " + resourcePath + RESET
                           : YELLOW + "Failed to immediately load registered bank: " + resourcePath + RESET);
            } catch (Exception e) {
                logError(RED + "Exception loading registered bank: " + resourcePath + " - " + e.getMessage() + RESET);
            }
        }

        return true;
    }

    // Utility methods
    private static boolean isClientSide() {
        // Simple check - in a real implementation you'd check the environment
        try {
            Class.forName("net.minecraft.client.Minecraft");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static void log(String message) {
        System.out.println("[FMOD API] " + message);
    }

    private static void logError(String message) {
        System.err.println("[FMOD API] " + message);
    }
}