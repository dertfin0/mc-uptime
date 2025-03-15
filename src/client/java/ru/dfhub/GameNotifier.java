package ru.dfhub;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class GameNotifier {

    private static boolean isDownLastMinute = false;
    private static boolean isConnected = true;

    public static void notify(String sites) {
        isDownLastMinute = true;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        client.player.playSound(SoundEvent.of(Identifier.of("minecraft:block.anvil.land")), 1.0F, 1.0F);

        if (client.world == null) return;
        Text msg = Text.of("§c It looks like some sites are down: §b%s§c!".formatted(sites));
        client.player.sendMessage(msg, false);
    }

    public static void statusOk() {
        isDownLastMinute = false;
    }

    public static void setConnected(boolean status) {
        isConnected = status;
    }

    public static void hud(DrawContext ctx, RenderTickCounter tick) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;
        Text text = Text.of(isConnected ? (isDownLastMinute ? "§c✘ Something down" : "§a✔ All sites are OK") : "§e No connection!") ;

        int x = client.getWindow().getScaledWidth() - client.textRenderer.getWidth(text) - 10;
        int y = client.getWindow().getScaledHeight() - client.textRenderer.fontHeight - 10;
        ctx.drawText(client.textRenderer, text, x, y, isDownLastMinute ? Colors.RED : Colors.GREEN, true);
    }
}
