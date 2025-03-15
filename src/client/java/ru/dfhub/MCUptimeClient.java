package ru.dfhub;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.util.Identifier;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static ru.dfhub.MCUptime.MOD_ID;

public class MCUptimeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		Config.reload();
		Thread siteCheckThread = new SiteCheckThread();
		siteCheckThread.start();
		HudLayerRegistrationCallback.EVENT.register(drawer -> drawer.attachLayerBefore(IdentifiedLayer.CHAT, Identifier.of(MOD_ID, "STATUS_TEXT"), GameNotifier::hud));
		Config.registerReloadCommand();
	}
}