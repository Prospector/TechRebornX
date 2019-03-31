package team.reborn.tech.x;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import team.reborn.tech.x.client.screen.RatingScreen;

public class TechRebornXClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickCallback.EVENT.register(minecraftClient -> {
			if(MinecraftClient.getInstance().player != null){
				if (!TechRebornX.didFirstOpen) {
					MinecraftClient.getInstance().openScreen(new RatingScreen());
					TechRebornX.waitABit++;
					if (MinecraftClient.getInstance().currentScreen instanceof RatingScreen) {
						TechRebornX.didFirstOpen = true;
					}
				}
			} else {
				//Reset it when you leave the game ;)
				TechRebornX.didFirstOpen = false;
			}
		});
	}
}
