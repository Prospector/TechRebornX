package team.reborn.techrebornx.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.reborn.techrebornx.TechRebornX;

public class SlotMachineBlock extends HorizontalFacingBlock {
	public SlotMachineBlock() {
		super(FabricBlockSettings.copy(Blocks.IRON_BLOCK).build());
	}

	@Override
	public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
		if (!world_1.isClient) {
			ContainerProviderRegistry.INSTANCE.openContainer(TechRebornX.SLOT_MACHINE_CONTAINER, playerEntity_1, packetByteBuf -> {
			});
		}
		return super.activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
	}
}
