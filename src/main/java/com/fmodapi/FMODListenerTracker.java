package com.fmodapi;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Automatically tracks Minecraft player position and updates FMOD listener.
 * Only updates when player actually moves or rotates for optimal performance.
 * This provides out-of-the-box 3D audio for all mods using FMOD API.
 *
 * @author alexiokay
 */
@EventBusSubscriber(modid = "fmodapi", value = Dist.CLIENT)
public class FMODListenerTracker {

    // Track last known position/rotation to detect changes
    private static double lastX = Double.NaN;
    private static double lastY = Double.NaN;
    private static double lastZ = Double.NaN;
    private static float lastYaw = Float.NaN;
    private static float lastPitch = Float.NaN;

    // Thresholds for change detection
    private static final double POSITION_THRESHOLD = 0.01; // 1cm movement
    private static final float ROTATION_THRESHOLD = 0.1f;   // 0.1 degree rotation

    /**
     * Updates FMOD listener position only when player moves or rotates.
     * Called once per tick for the client player.
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        System.out.println("[FMOD API] PlayerTickEvent.Post fired"); // Debug

        // Only process client-side player
        if (!event.getEntity().level().isClientSide()) {
            return;
        }

        // Only proceed if FMOD is available
        if (!FMODAPI.isAvailable()) {
            return;
        }

        // Only track the local client player
        Player clientPlayer = Minecraft.getInstance().player;
        if (clientPlayer == null || !clientPlayer.equals(event.getEntity())) {
            return;
        }

        // Get current position/rotation
        double currentX = clientPlayer.getX();
        double currentY = clientPlayer.getY();
        double currentZ = clientPlayer.getZ();
        float currentYaw = clientPlayer.getYRot();
        float currentPitch = clientPlayer.getXRot();

        // Check if position changed significantly
        boolean positionChanged = (
            Double.isNaN(lastX) || // First time
            Math.abs(currentX - lastX) > POSITION_THRESHOLD ||
            Math.abs(currentY - lastY) > POSITION_THRESHOLD ||
            Math.abs(currentZ - lastZ) > POSITION_THRESHOLD
        );

        // Check if rotation changed significantly
        boolean rotationChanged = (
            Float.isNaN(lastYaw) || // First time
            Math.abs(currentYaw - lastYaw) > ROTATION_THRESHOLD ||
            Math.abs(currentPitch - lastPitch) > ROTATION_THRESHOLD
        );

        // Only update if something actually changed
        if (!positionChanged && !rotationChanged) {
            return; // No update needed - save CPU cycles
        }

        // Convert rotation to forward vector
        double pitchRad = Math.toRadians(currentPitch);
        double yawRad = Math.toRadians(currentYaw);

        // Calculate forward vector (Minecraft coordinate system)
        double forwardX = -Math.sin(yawRad) * Math.cos(pitchRad);
        double forwardY = -Math.sin(pitchRad);
        double forwardZ = Math.cos(yawRad) * Math.cos(pitchRad);

        // Get player velocity
        double velX = clientPlayer.getDeltaMovement().x;
        double velY = clientPlayer.getDeltaMovement().y;
        double velZ = clientPlayer.getDeltaMovement().z;

        try {
            // Update FMOD listener position
            FMODAPI.setListenerPosition(
                currentX, currentY, currentZ,
                forwardX, forwardY, forwardZ,
                velX, velY, velZ
            );

            // Debug logging (temporarily always enabled for testing)
            if (positionChanged) {
                System.out.printf("[FMOD API] Auto-listener update - Pos:(%.1f, %.1f, %.1f) Vel:(%.2f, %.2f, %.2f)%n",
                    currentX, currentY, currentZ, velX, velY, velZ);
            }

        } catch (Exception e) {
            System.err.println("[FMOD API] Failed to auto-update listener: " + e.getMessage());
        }

        // Store current values for next comparison
        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        lastYaw = currentYaw;
        lastPitch = currentPitch;
    }

    /**
     * Reset tracking when player disconnects/reconnects
     */
    public static void resetTracking() {
        lastX = Double.NaN;
        lastY = Double.NaN;
        lastZ = Double.NaN;
        lastYaw = Float.NaN;
        lastPitch = Float.NaN;
    }
}