package team.reborn.techrebornx.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;
import team.reborn.techrebornx.TechRebornX;
import team.reborn.techrebornx.container.SlotMachineContainer;

public class SlotMachineScreen extends ContainerScreen<SlotMachineContainer> {
	private static final Identifier BG_TEX = new Identifier(TechRebornX.MOD_ID, "textures/gui/slot_machine.png");

	public SlotMachineScreen(SlotMachineContainer container) {
		super(container, MinecraftClient.getInstance().player.inventory, new StringTextComponent("Slot Machine"));
	}

	protected void init() {
		super.init();
	}

	@Override
	public void render(int mouseX, int mouseY, float delta) {
		this.renderBackground();
		super.render(mouseX, mouseY, delta);
		this.drawMouseoverTooltip(mouseX, mouseY);
	}

	@Override
	protected void drawForeground(int int_1, int int_2) {
		this.font.draw(this.title.getFormattedText(), 28.0F, 6.0F, 0x404040);
		this.font.draw(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.containerHeight - 96 + 2), 0x404040);
	}

	@Override
	protected void drawBackground(float var1, int var2, int var3) {
		this.minecraft.getTextureManager().bindTexture(BG_TEX);
		this.blit(this.left, this.top, 0, 0, this.containerWidth, this.containerHeight);
	}

	public void drawSlot(int x, int y, ItemStack item) {

	}

	@Override
	protected void drawMouseoverTooltip(int int_1, int int_2) {
		this.minecraft.getTextureManager().bindTexture(BG_TEX);
		this.blit(this.left, this.top, 0, 0, this.containerWidth, 21);
		this.blit(this.left, this.top + 63, 0, 63, this.containerWidth, 20);
		super.drawMouseoverTooltip(int_1, int_2);
	}
}
