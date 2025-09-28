package com.fmodapi;

import org.lwjgl.PointerBuffer;
import org.lwjgl.fmod.FMOD;
import org.lwjgl.fmod.FMODStudio;
import org.lwjgl.fmod.FMOD_3D_ATTRIBUTES;
import org.lwjgl.system.MemoryStack;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Main API class for other mods to interact with FMOD.
 * Provides simplified methods for playing sounds, managing banks, and 3D audio.
 *
 * @author alexiokay
 */
public class FMODAPI {

    /**
     * Check if FMOD system is available and initialized.
     * External mods should not need to call this - all FMOD API methods handle availability internally.
     */
    public static boolean isAvailable() {
        return FMODSystem.isInitialized() && !FMODSystem.hasInitializationFailed();
    }

    /**
     * Get current FMOD status information
     */
    public static FMODStatus getStatus() {
        return new FMODStatus(
            FMODSystem.getCurrentStatus(),
            FMODSystem.getCurrentAudioSystem(),
            FMODSystem.getCurrentErrorCode(),
            FMODSystem.isInitialized()
        );
    }

    /**
     * Play a sound event by name
     * @param eventName The FMOD event name (e.g., "event:/weapons/rifle_shot")
     * @param position 3D position for the sound (null for 2D sound)
     * @return Sound instance ID for controlling the sound, or null if failed
     */
    public static String playEvent(String eventName, Vec3D position) {
        return playEvent(eventName, position, 1.0f, 1.0f);
    }

    /**
     * Play a sound event with separate coordinates (for mods that don't want Vec3D imports)
     * @param eventName The FMOD event name
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @param volume Volume multiplier (0.0 to 1.0)
     * @param pitch Pitch multiplier (0.1 to 3.0)
     * @return Sound instance ID for controlling the sound, or null if failed
     */
    public static String playEventAt(String eventName, double x, double y, double z, float volume, float pitch) {
        return playEvent(eventName, new Vec3D(x, y, z), volume, pitch);
    }

    /**
     * Simplified method for mods - fire and forget, no instance tracking exposed
     * @param eventName FMOD event name (without "event:/" prefix)
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return true if sound was played successfully, false if failed
     */
    public static boolean playEventSimple(String eventName, double x, double y, double z) {
        System.out.println(">>> FMOD API: playEventSimple called for: " + eventName);
        System.out.println(">>> FMOD API: playEventSimple called for: " + eventName);
        System.out.println(">>> FMOD API: playEventSimple called for: " + eventName);
        String instanceId = playEventAt(eventName, x, y, z, 1.0f, 1.0f);
        boolean result = instanceId != null;
        System.out.println(">>> FMOD API: playEventSimple returning: " + result);
        return result;
    }

    /**
     * Play a sound event with volume and pitch control
     * @param eventName The FMOD event name
     * @param position 3D position for the sound (null for 2D sound)
     * @param volume Volume multiplier (0.0 to 1.0)
     * @param pitch Pitch multiplier (0.1 to 3.0)
     * @return Sound instance ID for controlling the sound, or null if failed
     */
    public static String playEvent(String eventName, Vec3D position, float volume, float pitch) {
        System.out.println("[FMOD API] playEvent called with: " + eventName + ", position: " + position + ", volume: " + volume + ", pitch: " + pitch);

        // Check if FMOD routing is enabled - if not, return null to trigger OpenAL fallback
        try {
            if (!FMODConfig.FMOD_ENABLED.get()) {
                System.out.println("[FMOD API] FMOD routing disabled - sound will use OpenAL fallback");
                return null;
            }
        } catch (Exception e) {
            System.out.println("[FMOD API] Config not available, defaulting to FMOD routing");
        }

        if (!isAvailable()) {
            System.err.println("[FMOD API] playEvent failed - FMOD not available");
            return null;
        }

        System.out.println("[FMOD API] FMOD is available, proceeding with event playback");

        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Get event description
            PointerBuffer descPtr = stack.mallocPointer(1);
            ByteBuffer eventNameBuffer = stack.UTF8("event:/" + eventName);
            System.out.println("[FMOD API] Looking for event: event:/" + eventName);

            int result = FMODStudio.FMOD_Studio_System_GetEvent(
                FMODSystem.getSystemHandle(),
                eventNameBuffer,
                descPtr
            );

            System.out.println("[FMOD API] GetEvent result: " + result + " (FMOD_OK=" + FMOD.FMOD_OK + ")");

            if (result != FMOD.FMOD_OK) {
                System.err.println("[FMOD API] Failed to find event: " + eventName + " (error: " + result + ")");
                return null;
            }

            long description = descPtr.get(0);

            // Create instance
            PointerBuffer instancePtr = stack.mallocPointer(1);
            result = FMODStudio.FMOD_Studio_EventDescription_CreateInstance(description, instancePtr);

            if (result != FMOD.FMOD_OK) {
                System.err.println("[FMOD API] Failed to create instance for: " + eventName + " (error: " + result + ")");
                return null;
            }

            long instance = instancePtr.get(0);

            // Set 3D position if provided
            if (position != null) {
                FMOD_3D_ATTRIBUTES attributes = FMOD_3D_ATTRIBUTES.calloc(stack);
                attributes.position$().set((float) position.x, (float) position.y, (float) position.z);
                attributes.velocity().set(0f, 0f, 0f);
                attributes.forward().set(0f, 0f, 1f);
                attributes.up().set(0f, 1f, 0f);

                result = FMODStudio.FMOD_Studio_EventInstance_Set3DAttributes(instance, attributes);
                if (result != FMOD.FMOD_OK) {
                    System.err.println("[FMOD API] Failed to set 3D attributes for: " + eventName);
                } else {
                    System.out.println("[FMOD API] Set 3D position for " + eventName + " at: " +
                        String.format("(%.1f, %.1f, %.1f)", position.x, position.y, position.z));
                }
            } else {
                System.out.println("[FMOD API] Playing " + eventName + " as 2D sound (no position)");
            }

            // Set volume
            if (volume != 1.0f) {
                result = FMODStudio.FMOD_Studio_EventInstance_SetVolume(instance, volume);
                if (result != FMOD.FMOD_OK) {
                    System.err.println("[FMOD API] Failed to set volume for: " + eventName);
                }
            }

            // Start the event
            result = FMODStudio.FMOD_Studio_EventInstance_Start(instance);
            if (result != FMOD.FMOD_OK) {
                System.err.println("[FMOD API] Failed to start event: " + eventName + " (error: " + result + ")");
                FMODStudio.FMOD_Studio_EventInstance_Release(instance);
                return null;
            }

            // Track instance internally only - no external access needed
            String instanceId = eventName + "_" + System.nanoTime();
            FMODSystem.getActiveInstances().put(instanceId, instance);

            // Return success indicator for fire-and-forget usage
            return instanceId;

        } catch (Exception e) {
            System.err.println("[FMOD API] Exception playing event " + eventName + ": " + e.getMessage());
            return null;
        }
    }

    // stopEvent() removed - FMOD API handles all instance management internally

    /**
     * Load a sound bank from file path
     * @param bankPath Path to the .bank file (can be absolute path or relative to working directory)
     * @return true if loaded successfully
     */
    public static boolean loadBank(String bankPath) {
        if (!isAvailable()) {
            return false;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer bankPtr = stack.mallocPointer(1);
            int result = FMODStudio.FMOD_Studio_System_LoadBankFile(
                FMODSystem.getSystemHandle(),
                stack.UTF8(bankPath, true),
                FMODStudio.FMOD_STUDIO_LOAD_BANK_NORMAL,
                bankPtr
            );

            if (result == FMOD.FMOD_OK) {
                System.out.println("[FMOD API] Successfully loaded bank: " + bankPath);
                return true;
            } else {
                System.err.println("[FMOD API] Failed to load bank: " + bankPath + " (error: " + result + ")");
                return false;
            }
        } catch (Exception e) {
            System.err.println("[FMOD API] Exception loading bank " + bankPath + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Load a sound bank from file path with name tracking for reloading
     * @param bankPath Path to the .bank file
     * @param bankName Name to use for tracking (for reloading)
     * @return true if loaded successfully
     */
    private static boolean loadBankWithName(String bankPath, String bankName) {
        if (!isAvailable()) {
            return false;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            PointerBuffer bankPtr = stack.mallocPointer(1);
            int result = FMODStudio.FMOD_Studio_System_LoadBankFile(
                FMODSystem.getSystemHandle(),
                stack.UTF8(bankPath, true),
                FMODStudio.FMOD_STUDIO_LOAD_BANK_NORMAL,
                bankPtr
            );

            if (result == FMOD.FMOD_OK) {
                long bankHandle = bankPtr.get(0);
                System.out.println("[FMOD API] Successfully loaded bank: " + bankPath + " (handle: " + bankHandle + ")");
                return true;
            } else {
                System.err.println("[FMOD API] Failed to load bank: " + bankPath + " (error: " + result + ")");
                return false;
            }
        } catch (Exception e) {
            System.err.println("[FMOD API] Exception loading bank " + bankPath + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Load a sound bank from JAR resource
     * @param modClass A class from the mod's JAR to load resources from
     * @param resourcePath Path to the .bank file within the JAR (e.g., "/assets/modid/sounds/fmod/Master.bank")
     * @return true if loaded successfully
     */
    public static boolean loadBankFromResource(Class<?> modClass, String resourcePath) {
        if (!isAvailable()) {
            return false;
        }

        try (InputStream bankStream = modClass.getResourceAsStream(resourcePath)) {
            if (bankStream == null) {
                // Try alternative path formats
                String altPath = resourcePath.startsWith("/") ? resourcePath.substring(1) : "/" + resourcePath;
                try (InputStream altStream = modClass.getResourceAsStream(altPath)) {
                    if (altStream == null) {
                        System.err.println("[FMOD API] Bank resource not found: " + resourcePath + " (also tried: " + altPath + ")");
                        return false;
                    }
                    return loadBankFromStream(altStream, resourcePath);
                }
            }
            return loadBankFromStream(bankStream, resourcePath);

        } catch (Exception e) {
            System.err.println("[FMOD API] Exception loading bank from resource " + resourcePath + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Load a bank from an input stream (internal method)
     */
    private static boolean loadBankFromStream(InputStream bankStream, String resourcePath) throws Exception {
        // Read all bytes from the input stream
        byte[] bankData = bankStream.readAllBytes();

        System.out.println("[FMOD API] Read bank data from resource: " + resourcePath + ", size=" + bankData.length + " bytes");

        // Extract filename for storage key
        String fileName = resourcePath.substring(resourcePath.lastIndexOf('/') + 1);

        // ALWAYS store bank data for automatic reloading, regardless of current FMOD state
        FMODSystem.storeBankData(fileName, bankData);
        System.out.println("[FMOD API] Stored bank data for automatic reloading: " + fileName + " (stored regardless of FMOD state)");

        // Extract to temporary file for FMOD to load
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("fmodapi_" + fileName.replace(".", "_"), ".bank");
        tempFile.toFile().deleteOnExit();

        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFile.toFile())) {
            fos.write(bankData);
        }

        System.out.println("[FMOD API] Extracted bank to temp file: " + tempFile.toAbsolutePath());

        // Load the bank using the file path method (only if FMOD is available)
        boolean success = loadBankWithName(tempFile.toAbsolutePath().toString(), fileName);

        if (success) {
            System.out.println("[FMOD API] Successfully loaded bank into FMOD: " + fileName);
        } else {
            System.out.println("[FMOD API] Failed to load bank into FMOD (will retry on next FMOD init): " + fileName);
        }

        return success;
    }

    /**
     * Set the 3D listener position (usually the player)
     * @param position Listener position
     * @param forward Forward direction vector
     * @param up Up direction vector
     * @param velocity Listener velocity
     */
    public static void setListenerPosition(Vec3D position, Vec3D forward, Vec3D up, Vec3D velocity) {
        // Check availability internally - external mods don't need to worry about this
        if (!isAvailable()) {
            return; // Silently ignore if FMOD not available
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FMOD_3D_ATTRIBUTES attrs = FMOD_3D_ATTRIBUTES.malloc(stack);
            attrs.position$().set((float) position.x, (float) position.y, (float) position.z);
            attrs.velocity().set((float) velocity.x, (float) velocity.y, (float) velocity.z);
            attrs.forward().set((float) forward.x, (float) forward.y, (float) forward.z);
            attrs.up().set((float) up.x, (float) up.y, (float) up.z);

            int result = FMODStudio.FMOD_Studio_System_SetListenerAttributes(
                FMODSystem.getSystemHandle(), 0, attrs, null);

            if (result != FMOD.FMOD_OK) {
                System.err.println("[FMOD API] Failed to set listener attributes (error: " + result + ")");
            }
        } catch (Exception e) {
            System.err.println("[FMOD API] Exception setting listener position: " + e.getMessage());
        }
    }

    /**
     * Set the 3D listener position with separate coordinates (simpler for mods)
     * @param posX X position
     * @param posY Y position
     * @param posZ Z position
     * @param forwardX Forward X direction
     * @param forwardY Forward Y direction
     * @param forwardZ Forward Z direction
     * @param velX Velocity X
     * @param velY Velocity Y
     * @param velZ Velocity Z
     */
    public static void setListenerPosition(double posX, double posY, double posZ,
                                         double forwardX, double forwardY, double forwardZ,
                                         double velX, double velY, double velZ) {
        // Check availability internally - external mods don't need to worry about this
        if (!isAvailable()) {
            return; // Silently ignore if FMOD not available
        }

        setListenerPosition(
            new Vec3D(posX, posY, posZ),
            new Vec3D(forwardX, forwardY, forwardZ),
            new Vec3D(0, 1, 0), // standard up vector
            new Vec3D(velX, velY, velZ)
        );
    }

    // updateInstancePosition() removed - use setListenerPosition() for 3D audio updates

    /**
     * INTERNAL: Get number of currently active sound instances
     * WARNING: This method is for internal FMOD API use only!
     */
    public static int getActiveInstanceCount() {
        return FMODSystem.getActiveInstances().size();
    }

    /**
     * INTERNAL: Get maximum allowed instances
     * WARNING: This method is for internal FMOD API use only!
     */
    public static int getMaxInstanceCount() {
        return FMODSystem.getMaxInstances();
    }

    /**
     * INTERNAL: Stop all currently playing sounds
     * WARNING: This method is for internal FMOD API use only!
     */
    public static void stopAllSounds() {
        if (!isAvailable()) {
            return;
        }

        Map<String, Long> instances = FMODSystem.getActiveInstances();
        for (String instanceId : instances.keySet().toArray(new String[0])) {
            Long instance = instances.remove(instanceId);
            if (instance != null) {
                try {
                    FMODStudio.FMOD_Studio_EventInstance_Stop(instance, FMODStudio.FMOD_STUDIO_STOP_IMMEDIATE);
                    FMODStudio.FMOD_Studio_EventInstance_Release(instance);
                } catch (Exception e) {
                    // Ignore cleanup errors during shutdown
                }
            }
        }
    }

    /**
     * Register a bank to be loaded automatically when FMOD initializes.
     * This is the preferred method for mods to register their banks.
     *
     * @param modClass Class from the mod (for resource loading)
     * @param resourcePath Path to bank within mod JAR (e.g., "/assets/modid/sounds/fmod/Master.bank")
     * @return true if registered successfully (always returns true)
     */
    public static boolean registerBank(Class<?> modClass, String resourcePath) {
        return FMODSystem.registerBank(modClass, resourcePath);
    }

    /**
     * Simple 3D vector class for positions and directions
     */
    public static class Vec3D {
        public final double x, y, z;

        public Vec3D(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    /**
     * FMOD status information
     */
    public static class FMODStatus {
        public final String status;
        public final String audioSystem;
        public final int errorCode;
        public final boolean isInitialized;

        public FMODStatus(String status, String audioSystem, int errorCode, boolean isInitialized) {
            this.status = status;
            this.audioSystem = audioSystem;
            this.errorCode = errorCode;
            this.isInitialized = isInitialized;
        }

        @Override
        public String toString() {
            return String.format("FMODStatus{status='%s', audioSystem='%s', errorCode=%d, initialized=%s}",
                status, audioSystem, errorCode, isInitialized);
        }
    }
}