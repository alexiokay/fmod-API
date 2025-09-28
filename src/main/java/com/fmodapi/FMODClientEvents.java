package com.fmodapi;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

/**
 * Client-side event handler for FMOD system updates.
 */
@EventBusSubscriber(modid = "fmodapi", bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class FMODClientEvents {

    private static int tickCounter = 0;

    /**
     * Update FMOD system every client tick
     */
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        try {
            FMODSystem.update();

            // Update config status periodically (every 60 ticks = 3 seconds)
            tickCounter++;
            if (tickCounter >= 60) {
                FMODConfig.updateStatus();
                tickCounter = 0;
            }
        } catch (Exception e) {
            System.err.println("[FMOD API] Exception during client tick update: " + e.getMessage());
        }
    }

    /**
     * Cleanup when world is unloaded
     */
    @SubscribeEvent
    public static void onWorldUnload(LevelEvent.Unload event) {
        if (event.getLevel().isClientSide()) {
            try {
                // Stop all sounds when world is unloaded
                FMODAPI.stopAllSounds();
            } catch (Exception e) {
                System.err.println("[FMOD API] Exception during world unload cleanup: " + e.getMessage());
            }
        }
    }
}