package com.fmodapi;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = "fmodapi", dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = "fmodapi", value = Dist.CLIENT)
public class FMODAPIClient {
    public FMODAPIClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        System.out.println("[FMOD API] Config screen factory registered for client");
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Client setup for FMOD API
        System.out.println("[FMOD API] Client setup started");

        // Initialize Minecraft integration after client setup
        event.enqueueWork(() -> {
            FMODMinecraftIntegration.initialize();
        });

        System.out.println("[FMOD API] Client setup completed");
    }
}