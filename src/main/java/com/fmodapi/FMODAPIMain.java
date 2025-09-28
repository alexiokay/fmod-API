package com.fmodapi;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

/**
 * Main FMOD API mod class.
 * Provides centralized FMOD Audio Engine functionality for other mods.
 *
 * @author alexiokay
 */
@Mod("fmodapi")
public class FMODAPIMain {

    static {
        System.out.println("[FMOD API] Static block - Class being loaded...");
    }

    public FMODAPIMain(IEventBus modEventBus, ModContainer modContainer) {
        // FMOD Attribution (Required by FMOD License)
        System.out.println("=====================================");
        System.out.println("FMOD API Mod - Audio powered by FMOD Studio");
        System.out.println("FMOD Studio, copyright © Firelight Technologies Pty Ltd, 1994-2024");
        System.out.println("FMOD License: https://www.fmod.com/legal");
        System.out.println("=====================================");

        // Debug logging
        System.out.println("[FMOD API] Constructor called - initializing mod...");

        // Register setup event
        modEventBus.addListener(this::commonSetup);

        // Register client setup event (only on client)
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(this::clientSetup);
        }

        System.out.println("[FMOD API] Constructor starting...");

        // Register config
        modContainer.registerConfig(ModConfig.Type.COMMON, FMODConfig.SPEC);

        System.out.println("[FMOD API] Constructor completed - FMOD init will happen in commonSetup");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        System.out.println("[FMOD API] Common setup called");
        event.enqueueWork(() -> {
            System.out.println("[FMOD API] Common setup - initializing FMOD system...");

            // Initialize FMOD system
            FMODSystem.init();

            System.out.println("[FMOD API] Common setup complete");
        });
    }


    private void clientSetup(final FMLClientSetupEvent event) {
        // Client setup tasks
        event.enqueueWork(() -> {
            // Client-specific initialization can go here
        });
    }
}