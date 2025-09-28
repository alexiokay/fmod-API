package com.fmodapi;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * FMOD API commands for debugging and status information.
 */
@EventBusSubscriber(modid = "fmodapi")
public class FMODCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
        System.out.println("[FMOD API] Commands registered");
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fmodstatus")
            .executes(context -> {
                CommandSourceStack source = context.getSource();

                try {
                    FMODAPI.FMODStatus status = FMODAPI.getStatus();

                    source.sendSuccess(() -> Component.literal("=== FMOD API Status ==="), false);
                    source.sendSuccess(() -> Component.literal("Status: " + status.status), false);
                    source.sendSuccess(() -> Component.literal("Audio System: " + status.audioSystem), false);
                    source.sendSuccess(() -> Component.literal("Error Code: " + status.errorCode +
                        (status.errorCode == 0 ? " (Success)" :
                         status.errorCode == 20 ? " (Hardware Conflict)" : " (Error)")), false);
                    source.sendSuccess(() -> Component.literal("Active Instances: " +
                        FMODAPI.getActiveInstanceCount() + "/" + FMODAPI.getMaxInstanceCount()), false);
                    source.sendSuccess(() -> Component.literal("FMOD Enabled: " + FMODConfig.FMOD_ENABLED.get()), false);
                    source.sendSuccess(() -> Component.literal("Debug Logging: " + FMODConfig.DEBUG_LOGGING.get()), false);

                } catch (Exception e) {
                    source.sendFailure(Component.literal("Failed to get FMOD status: " + e.getMessage()));
                }

                return 1;
            })
        );
    }
}