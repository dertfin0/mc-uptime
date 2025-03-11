package ru.dfhub;

import net.fabricmc.api.ClientModInitializer;

public class MCUptimeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		Config.reload();
		Thread siteCheckThread = new SiteCheckThread();
		siteCheckThread.start();
	}
}