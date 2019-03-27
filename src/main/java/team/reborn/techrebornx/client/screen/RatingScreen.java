package team.reborn.techrebornx.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;
import team.reborn.techrebornx.TechRebornX;

import java.util.Random;

public class RatingScreen extends Screen {

	public static final Identifier RATING_TEXTURES = new Identifier(TechRebornX.MOD_ID, "textures/gui/rating.png");
	public int rating = 0;
	public int x = 0;
	public int y = 0;

	public RatingScreen() {
		super(new StringTextComponent("Rate Tech Reborn X"));
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void initialize(MinecraftClient client, int int_1, int int_2) {
		super.initialize(client, int_1, int_2);
		x = (this.screenWidth - 184) / 2;
		y = (this.screenHeight - 116) / 2;
		for (int i = 1; i <= 5; i++) {
			addButton(new RateButton(x + 33 + 24 * (i - 1), y + 31, i));
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float elapsedTicks) {
		boolean buttonHovered = false;
		for (AbstractButtonWidget button : buttons) {
			if (button.isHovered()) {
				buttonHovered = true;
				break;
			}
		}
		if (!buttonHovered) {
			rating = 0;
		}
		this.client.getTextureManager().bindTexture(RATING_TEXTURES);
		this.drawTexturedRect(x, y, 0, 0, 184, 116);
		String pls = "Please rate your experience with";
		String trx = "Tech Reborn X";
		long time = MinecraftClient.getInstance().world.getTime();
		this.fontRenderer.draw(pls, (this.screenWidth - this.fontRenderer.getStringWidth(pls)) / 2 + 1, y + 4 + 1, new Random(time + 100 - time % 50).nextInt(0xAAAAAA));
		this.fontRenderer.draw(pls, (this.screenWidth - this.fontRenderer.getStringWidth(pls)) / 2, y + 4, new Random(time + 150 - time % 50).nextInt(0xAAAAAA));
		this.fontRenderer.draw(trx, (this.screenWidth - this.fontRenderer.getStringWidth(trx)) / 2 + 1, y + 4 + 1 + 10, new Random(time - time % 50).nextInt(0xAAAAAA));
		this.fontRenderer.draw(trx, (this.screenWidth - this.fontRenderer.getStringWidth(trx)) / 2, y + 4 + 10, new Random(time + 50 - time % 50).nextInt(0xAAAAAA));

		String hax = "Please note that username,";
		String hax2 = "password, and social security";
		String hax3 = "data is sent to Team Reborn (©)";
		String hax4 = "servers as a result of rating.";
		String hax5 = "Rate at your own risk.";
		this.fontRenderer.draw(hax, (this.screenWidth - this.fontRenderer.getStringWidth(hax)) / 2, y + 4 + 57, 0xAAAAAA);
		this.fontRenderer.draw(hax2, (this.screenWidth - this.fontRenderer.getStringWidth(hax2)) / 2, y + 4 + 67, 0xAAAAAA);
		this.fontRenderer.draw(hax3, (this.screenWidth - this.fontRenderer.getStringWidth(hax3)) / 2, y + 4 + 77, 0xAAAAAA);
		this.fontRenderer.draw(hax4, (this.screenWidth - this.fontRenderer.getStringWidth(hax4)) / 2, y + 4 + 87, 0xAAAAAA);
		this.fontRenderer.draw(hax5, (this.screenWidth - this.fontRenderer.getStringWidth(hax5)) / 2, y + 4 + 97, 0xAA8888);
		super.render(mouseX, mouseY, elapsedTicks);
	}

	@Override
	public boolean doesEscapeKeyClose() {
		return false;
	}

	public class RateButton extends ButtonWidget {

		int buttonRating;

		public RateButton(int x, int y, int rating) {
			super(x, y, 22, 21, "Rate " + rating + " Star", button -> {
				if (rating != 5) {
					System.out.println("nope");
					MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_HORSE_DEATH, 1F, 1F);
				} else {
					MinecraftClient.getInstance().player.closeGui();
				}
			});
			this.buttonRating = rating;
		}

		@Override
		public void render(int int_1, int int_2, float float_1) {
			super.render(int_1, int_2, float_1);
			if (this.isHovered()) {
				rating = buttonRating;
			}
		}

		@Override
		public void renderButton(int int_1, int int_2, float float_1) {
			MinecraftClient client = MinecraftClient.getInstance();
			client.getTextureManager().bindTexture(RATING_TEXTURES);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			if (isHovered() || rating >= buttonRating) {
				this.drawTexturedRect(this.x, this.y, 0, 116, 22, 21);
			} else {
				this.drawTexturedRect(this.x, this.y, 22, 116, 22, 21);
			}
		}
	}
}
