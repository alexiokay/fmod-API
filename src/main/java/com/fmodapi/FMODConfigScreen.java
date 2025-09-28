package com.fmodapi;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Configuration screen for FMOD API.
 * Shows real-time status and provides control options.
 */
@OnlyIn(Dist.CLIENT)
public class FMODConfigScreen extends Screen implements FMODSystem.StatusChangeListener {
    private final Screen parent;
    private Button reinitializeButton;
    private Component statusText;
    private Component systemText;
    private Component errorText;
    private Component instancesText;

    public FMODConfigScreen(Screen parent) {
        super(Component.literal("FMOD API Configuration"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        updateStatusInfo();

        // Register for status change events
        FMODSystem.addStatusListener(this);

        // Title
        int centerX = this.width / 2;
        int startY = 40;

        // Status display (read-only)
        addRenderableWidget(Button.builder(Component.literal("FMOD Status"), (button) -> {})
            .pos(centerX - 100, startY)
            .size(200, 20)
            .tooltip(Tooltip.create(statusText))
            .build());

        addRenderableWidget(Button.builder(Component.literal("Audio System"), (button) -> {})
            .pos(centerX - 100, startY + 25)
            .size(200, 20)
            .tooltip(Tooltip.create(systemText))
            .build());

        addRenderableWidget(Button.builder(Component.literal("Error Code"), (button) -> {})
            .pos(centerX - 100, startY + 50)
            .size(200, 20)
            .tooltip(Tooltip.create(errorText))
            .build());

        addRenderableWidget(Button.builder(Component.literal("Active Instances"), (button) -> {})
            .pos(centerX - 100, startY + 75)
            .size(200, 20)
            .tooltip(Tooltip.create(instancesText))
            .build());

        // Control buttons
        reinitializeButton = addRenderableWidget(Button.builder(
            Component.literal("Reinitialize FMOD"),
            (button) -> {
                FMODConfig.reinitializeFMOD();
                updateStatusInfo();
                this.clearWidgets();
                this.init(); // Refresh the screen
            })
            .pos(centerX - 75, startY + 110)
            .size(150, 20)
            .tooltip(Tooltip.create(Component.literal("Restart FMOD system (useful for troubleshooting)")))
            .build());

        // Back button
        addRenderableWidget(Button.builder(
            Component.literal("Done"),
            (button) -> this.minecraft.setScreen(parent))
            .pos(centerX - 50, startY + 140)
            .size(100, 20)
            .build());
    }

    private void updateStatusInfo() {
        try {
            FMODAPI.FMODStatus status = FMODAPI.getStatus();
            statusText = Component.literal("Status: " + status.status);
            systemText = Component.literal("System: " + status.audioSystem);
            errorText = Component.literal("Error: " + status.errorCode + (status.errorCode == 0 ? " (Success)" :
                status.errorCode == 20 ? " (Hardware Conflict)" : " (Error)"));
            instancesText = Component.literal("Instances: " + FMODAPI.getActiveInstanceCount() + "/" + FMODAPI.getMaxInstanceCount());
        } catch (Exception e) {
            statusText = Component.literal("Status: Error reading status");
            systemText = Component.literal("System: Unknown");
            errorText = Component.literal("Error: N/A");
            instancesText = Component.literal("Instances: N/A");
        }
    }

    @Override
    public void render(net.minecraft.client.gui.GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        // Draw title
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

        // Draw status information
        int centerX = this.width / 2;
        int startY = 40;

        guiGraphics.drawString(this.font, statusText, centerX - 95, startY + 6, 0xFFFFFF);
        guiGraphics.drawString(this.font, systemText, centerX - 95, startY + 31, 0xFFFFFF);
        guiGraphics.drawString(this.font, errorText, centerX - 95, startY + 56, 0xFFFFFF);
        guiGraphics.drawString(this.font, instancesText, centerX - 95, startY + 81, 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        // Unregister from status change events
        FMODSystem.removeStatusListener(this);
        super.onClose();
    }

    @Override
    public void onStatusChanged() {
        // Update status info when FMOD status changes
        System.out.println("[FMOD API] FMODConfigScreen.onStatusChanged() called - updating status info");
        updateStatusInfo();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}