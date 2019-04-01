package team.reborn.tech.x.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeverBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.tech.x.TechRebornX;
import team.reborn.tech.x.container.SlotMachineContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlotMachineScreen extends ContainerScreen<SlotMachineContainer> {
	private static final Identifier BG_TEX = new Identifier(TechRebornX.MOD_ID, "textures/gui/slot_machine.png");
	public final List<ItemStack> stacks = new ArrayList<>();
	public List<ItemStack> currentStacks = new ArrayList<>();
	public List<ItemStack> slot1 = new ArrayList<>();
	public List<ItemStack> slot2 = new ArrayList<>();
	public List<ItemStack> slot3 = new ArrayList<>();
	public Random rand = new Random();
	public static int time = 0;
	public int buttonDown = 0;

	public SlotMachineScreen(SlotMachineContainer container) {
		super(container, MinecraftClient.getInstance().player.inventory, new StringTextComponent("Slot Machine"));
		for (Item item : Registry.ITEM) {
			if (Registry.ITEM.getId(item).getNamespace().equals(TechRebornX.MOD_ID)) {
				for (int i = 0; i < rand.nextInt(5); i++) {
					stacks.add(new ItemStack(item, 1));
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			currentStacks.add(stacks.get(rand.nextInt(stacks.size() - 1)).copy());
		}
		reset();

	}

	static {
		ClientTickCallback.EVENT.register(minecraftClient -> time++);
	}

	protected void init() {
		super.init();
		addButton(new LeverButton(this));
	}

	int lastTick = -1;

	@Override
	public void render(int mouseX, int mouseY, float delta) {
		this.renderBackground();
		super.render(mouseX, mouseY, delta);
		this.drawMouseoverTooltip(mouseX, mouseY);

	}

	public void reset() {
		slot1.clear();
		slot2.clear();
		slot3.clear();
		for (int i = 0; i <= 3; i++) {
			slot1.add(currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy());
			slot2.add(currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy());
			slot3.add(currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy());
		}
	}

	@Override
	protected void drawForeground(int int_1, int int_2) {
		GlStateManager.pushMatrix();
		GlStateManager.enableDepthTest();
		GlStateManager.translatef(142, 43, 512);
		GlStateManager.scalef(32F, 32F, 32F);
		GlStateManager.translatef(0.5F, 0.5F, 0.5F);
		GlStateManager.scalef(-1, -1, -1);
		MinecraftClient.getInstance().getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
		BlockState state = Blocks.LEVER.getDefaultState().with(LeverBlock.POWERED, buttonDown != 0);
		BakedModel model = MinecraftClient.getInstance().getBlockRenderManager().getModel(state);
		MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(model, state, 1F, false);
		GlStateManager.disableDepthTest();
		GlStateManager.popMatrix();
		int speed = 5;

		if (time != lastTick) {
			lastTick = time;
			if (time % speed == 0) {
				slot1.remove(slot1.size() - 1);
				slot1.add(0, currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy());
			}
			if (time % speed == (speed / 2)) {
				slot2.remove(slot2.size() - 1);
				slot2.add(0, currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy());
			}
			if (time % speed == (speed / 3) * 2) {
				slot3.remove(slot3.size() - 1);
				slot3.add(0, currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy());
			}
			if (buttonDown > 0) {
				buttonDown--;
			}
		}

		int slot1Track = 0;
		int slot2Track = 0;
		int slot3Track = 0;
		drawSlot(56 + 0 * 22, slot1Track, slot1.get(0));
		drawSlot(56 + 0 * 22, slot1Track + 18, slot1.get(1));
		drawSlot(56 + 0 * 22, slot1Track + 36, slot1.get(2));
		drawSlot(56 + 0 * 22, slot1Track + 54, slot1.get(3));
		drawSlot(56 + 1 * 22, slot2Track, slot2.get(0));
		drawSlot(56 + 1 * 22, slot2Track + 18, slot2.get(1));
		drawSlot(56 + 1 * 22, slot2Track + 36, slot2.get(2));
		drawSlot(56 + 1 * 22, slot2Track + 54, slot2.get(3));
		drawSlot(56 + 2 * 22, slot3Track, slot3.get(0));
		drawSlot(56 + 2 * 22, slot3Track + 18, slot3.get(1));
		drawSlot(56 + 2 * 22, slot3Track + 36, slot3.get(2));
		drawSlot(56 + 2 * 22, slot3Track + 54, slot3.get(3));
	}

	@Override
	protected void drawBackground(float var1, int var2, int var3) {
		this.minecraft.getTextureManager().bindTexture(BG_TEX);
		this.blit(this.left, this.top, 0, 0, this.containerWidth, this.containerHeight);
	}

	public void drawSlot(int x, int y, ItemStack item) {
		this.minecraft.getTextureManager().bindTexture(BG_TEX);
		this.blit(x, y, 176, 0, 20, 18);
		itemRenderer.renderGuiItem(item, x + 2, y + 1);
	}

	@Override
	protected void drawMouseoverTooltip(int int_1, int int_2) {
		GlStateManager.disableRescaleNormal();
		GuiLighting.disable();
		GlStateManager.disableLighting();
		GlStateManager.disableDepthTest();
		GlStateManager.color4f(1F, 1f, 1f, 1f);
		itemRenderer.zOffset = 300;
		this.minecraft.getTextureManager().bindTexture(BG_TEX);
		this.blit(this.left, this.top, 0, 0, this.containerWidth, 21);
		this.blit(this.left, this.top + 63, 0, 63, this.containerWidth, 20);
		itemRenderer.zOffset = 0;
		GlStateManager.enableDepthTest();
		this.font.draw(this.title.getFormattedText(), (this.width - font.getStringWidth(this.title.getFormattedText())) / 2, top + 5.0F, 0x404040);
		this.font.draw(this.playerInventory.getDisplayName().getFormattedText(), left + 8.0F, top + (float) (this.containerHeight - 96 + 2), 0x404040);
		super.drawMouseoverTooltip(int_1, int_2);

	}

}
