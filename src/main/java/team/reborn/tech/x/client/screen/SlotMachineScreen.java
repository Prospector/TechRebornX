package team.reborn.tech.x.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
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
	public ItemStack[] slot1 = new ItemStack[4];
	public ItemStack[] slot2 = new ItemStack[4];
	public ItemStack[] slot3 = new ItemStack[4];
	public Random rand = new Random();
	public boolean cycleSlot1 = false;
	public boolean cycleSlot2 = false;
	public boolean cycleSlot3 = false;
	public float time = 0;

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

	protected void init() {
		super.init();
	}

	@Override
	public void render(int mouseX, int mouseY, float delta) {
		this.renderBackground();
		super.render(mouseX, mouseY, delta);
		time += delta;
		if (cycleSlot1) {
			slot1[3] = slot1[2];
			slot1[2] = slot1[1];
			slot1[1] = slot1[0];
			slot1[0] = currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy();
			cycleSlot1 = false;
		}
		if (cycleSlot2) {
			slot2[3] = slot2[2];
			slot2[2] = slot2[1];
			slot2[1] = slot2[0];
			slot2[0] = currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy();
			cycleSlot2 = false;
		}
		if (cycleSlot3) {
			slot3[3] = slot3[2];
			slot3[2] = slot3[1];
			slot3[1] = slot3[0];
			slot3[0] = currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy();
			cycleSlot3 = false;
		}
		int slot1Track = 0;
		int slot2Track = 0;
		int slot3Track = 0;
		drawSlot(56 + 0 * 22, slot1Track, slot1[0]);
		drawSlot(56 + 0 * 22, slot1Track + 18, slot1[1]);
		drawSlot(56 + 0 * 22, slot1Track + 36, slot1[2]);
		drawSlot(56 + 0 * 22, slot1Track + 54, slot1[3]);
		drawSlot(56 + 1 * 22, slot2Track, slot2[0]);
		drawSlot(56 + 1 * 22, slot2Track + 18, slot2[1]);
		drawSlot(56 + 1 * 22, slot2Track + 36, slot2[2]);
		drawSlot(56 + 1 * 22, slot2Track + 54, slot2[3]);
		drawSlot(56 + 2 * 22, slot3Track, slot3[0]);
		drawSlot(56 + 2 * 22, slot3Track + 18, slot3[1]);
		drawSlot(56 + 2 * 22, slot3Track + 36, slot3[2]);
		drawSlot(56 + 2 * 22, slot3Track + 54, slot3[3]);
		if ((int) Math.floor(time) % 10 == 0) {
			cycleSlot1 = true;
		}
		if ((int) Math.floor(time) % 7 == 3) {
			cycleSlot2 = true;
		}
		if ((int) Math.floor(time) % 14 == 3) {
			cycleSlot3 = true;
		}
		this.drawMouseoverTooltip(mouseX, mouseY);
	}

	public void reset() {
		for (int i = 0; i <= 3; i++) {
			slot1[i] = currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy();
			slot2[i] = currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy();
			slot3[i] = currentStacks.get(rand.nextInt(currentStacks.size() - 1)).copy();
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
		BlockState state = Blocks.LEVER.getDefaultState().with(LeverBlock.POWERED, false);
		BakedModel model = MinecraftClient.getInstance().getBlockRenderManager().getModel(state);
		MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(model, state, 1F, false);
		GlStateManager.disableDepthTest();
		GlStateManager.popMatrix();
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
