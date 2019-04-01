package team.reborn.tech.x.client.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
import team.reborn.tech.x.TechRebornX;

public class LeverButton extends ButtonWidget {
	private SlotMachineScreen slotMachineScreen;

	public LeverButton(SlotMachineScreen slotMachineScreen) {
		super(357, 112, 20, 32, "", button -> {
			if (slotMachineScreen.buttonDown == 0) {
				TechRebornX.giveItem(slotMachineScreen.slot1.get(2));
				slotMachineScreen.buttonDown = 20;
			}
		});
		this.slotMachineScreen = slotMachineScreen;
	}

	@Override
	public void render(int int_1, int int_2, float float_1) {
	}
}
