package team.reborn.techrebornx.container;

import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;

public class SlotMachineContainer extends Container {
	private final PlayerEntity player;

	public SlotMachineContainer(int syncId, PlayerEntity player) {
		super(null, syncId);
		this.player = player;

		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(player.inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
			}
		}

		for (int hotbarSlot = 0; hotbarSlot < 9; ++hotbarSlot) {
			this.addSlot(new Slot(player.inventory, hotbarSlot, 8 + hotbarSlot * 18, 142));
		}
	}

	@Override
	public boolean canUse(PlayerEntity var1) {
		return true;
	}
}
