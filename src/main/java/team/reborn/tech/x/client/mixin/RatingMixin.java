package team.reborn.tech.x.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.reborn.tech.x.TechRebornX;
import team.reborn.tech.x.client.screen.RatingScreen;

@Mixin(ClientPlayerEntity.class)
public class RatingMixin {
	@Shadow
	@Final
	protected MinecraftClient client;

	@Inject(method = "tick", at = @At("HEAD"))
	public void onTick(CallbackInfo info) {
//		if (client.world.getTime() % 1200 == 0 || !TechRebornX.didFirstOpen && TechRebornX.waitABit >= 400) {
//			client.openScreen(new RatingScreen());
//		}
		if (!TechRebornX.didFirstOpen) {
			TechRebornX.waitABit++;
			if (client.currentScreen instanceof RatingScreen) {
				TechRebornX.didFirstOpen = true;
			}
		}
	}
}
