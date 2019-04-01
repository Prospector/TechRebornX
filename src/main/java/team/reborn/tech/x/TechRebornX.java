package team.reborn.tech.x;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.block.BlockItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import team.reborn.tech.x.block.SlotMachineBlock;
import team.reborn.tech.x.client.screen.SlotMachineScreen;
import team.reborn.tech.x.container.SlotMachineContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TechRebornX implements ModInitializer {
	public static final String MOD_ID = "techrebornx";

	public static boolean didFirstOpen = false;
	public static int waitABit = 0;

	public static final String[] ITEMS = { "cloaking_device", "lapotronic_orbpack", "lithium_batpack", "energy_crystal", "energy_crystal_empty", "lapotronic_orb", "lapotronic_orb_empty", "lapotron_crystal", "lapotron_crystal_empty",
		"lithium_battery", "lithium_battery_empty", "re_battery", "re_battery_empty", "copper", "glassfiber", "gold", "hv", "insulatedcopper", "insulatedgold", "insulatedhv", "superconductor", "tin", "cell_base", "cell_cover", "cell_empty",
		"cell_fluid", "almandine_dust", "aluminum_dust", "andesite_dust", "andradite_dust", "ashes_dust", "basalt_dust", "bauxite_dust", "brass_dust", "bronze_dust", "calcite_dust", "charcoal_dust", "chrome_dust", "cinnabar_dust", "clay_dust",
		"coal_dust", "copper_dust", "dark_ashes_dust", "diamond_dust", "diorite_dust", "electrum_dust", "emerald_dust", "ender_eye_dust", "ender_pearl_dust", "endstone_dust", "flint_dust", "galena_dust", "gold_dust", "granite_dust", "grossular_dust",
		"invar_dust", "iron_dust", "lazurite_dust", "lead_dust", "magnesium_dust", "manganese_dust", "marble_dust", "netherrack_dust", "nickel_dust", "obsidian_dust", "olivine_dust", "peridot_dust", "phosphorous_dust", "platinum_dust", "pyrite_dust",
		"pyrope_dust", "red_garnet_dust", "ruby_dust", "saltpeter_dust", "sapphire_dust", "saw_dust_dust", "silver_dust", "sodalite_dust", "spessartine_dust", "sphalerite_dust", "steel_dust", "sulfur_dust", "tin_dust", "titanium_dust",
		"tungsten_dust", "uvarovite_dust", "yellow_garnet_dust", "zinc_dust", "peridot", "red_garnet", "ruby", "sapphire", "yellow_garnet", "advanced_alloy_ingot", "aluminum_ingot", "brass_ingot", "bronze_ingot", "chrome_ingot", "copper_ingot",
		"electrum_ingot", "hot_tungstensteel_ingot", "invar_ingot", "iridium_alloy_ingot", "iridium_ingot", "lead_ingot", "mixed_metal_ingot", "nickel_ingot", "platinum_ingot", "refined_iron_ingot", "silver_ingot", "steel_ingot", "tin_ingot",
		"titanium_ingot", "tungstensteel_ingot", "tungsten_ingot", "zinc_ingot", "manual", "missing_recipe", "rubber_sapling", "scrapbox", "uu_matter", "aluminum_nugget", "brass_nugget", "bronze_nugget", "chrome_nugget", "copper_nugget",
		"diamond_nugget", "electrum_nugget", "hot_tungstensteel_nugget", "invar_nugget", "iridium_nugget", "iron_nugget", "lead_nugget", "nickel_nugget", "platinum_nugget", "refined_iron_nugget", "silver_nugget", "steel_nugget", "tin_nugget",
		"titanium_nugget", "tungstensteel_nugget", "tungsten_nugget", "zinc_nugget", "advanced_circuit", "carbon_fiber", "carbon_mesh", "computer_monitor", "coolant_simple", "coolant_six", "coolant_triple", "cupronickel_heating_coil",
		"data_control_circuit", "data_orb", "data_storage_circuit", "depleted_cell", "diamond_grinding_head", "diamond_saw_blade", "double_depleted_cell", "double_plutonium_cell", "double_thorium_cell", "double_uranium_cell", "electronic_circuit",
		"energy_flow_circuit", "helium_coolant_simple", "helium_coolant_six", "helium_coolant_triple", "iridium_neutron_reflector", "kanthal_heating_coil", "machine_parts", "nak_coolant_simple", "nak_coolant_six", "nak_coolant_triple",
		"neutron_reflector", "nichrome_heating_coil", "plutonium_cell", "quad_depleted_cell", "quad_plutonium_cell", "quad_thorium_cell", "quad_uranium_cell", "rubber", "sap", "scrap", "super_conductor", "thick_neutron_reflector", "thorium_cell",
		"tungsten_grinding_head", "uranium_cell", "advanced_alloy_plate", "aluminum_plate", "brass_plate", "bronze_plate", "carbon_plate", "chrome_plate", "coal_plate", "copper_plate", "diamond_plate", "electrum_plate", "emerald_plate", "gold_plate",
		"invar_plate", "iridium_alloy_plate", "iridium_plate", "iron_plate", "lapis_plate", "lazurite_plate", "lead_plate", "magnalium_plate", "nickel_plate", "obsidian_plate", "peridot_plate", "platinum_plate", "redstone_plate", "red_garnet_plate",
		"refined_iron_plate", "ruby_plate", "sapphire_plate", "silicon_plate", "silver_plate", "steel_plate", "tin_plate", "titanium_plate", "tungstensteel_plate", "tungsten_plate", "wood_plate", "yellow_garnet_plate", "zinc_plate",
		"almandine_smalldust", "aluminum_smalldust", "andesite_smalldust", "andradite_smalldust", "ashes_smalldust", "basalt_smalldust", "bauxite_smalldust", "brass_smalldust", "bronze_smalldust", "calcite_smalldust", "charcoal_smalldust",
		"chrome_smalldust", "cinnabar_smalldust", "clay_smalldust", "coal_smalldust", "copper_smalldust", "dark_ashes_smalldust", "diamond_smalldust", "diorite_smalldust", "electrum_smalldust", "emerald_smalldust", "ender_eye_smalldust",
		"ender_pearl_smalldust", "endstone_smalldust", "flint_smalldust", "galena_smalldust", "glowstone_smalldust", "gold_smalldust", "granite_smalldust", "grossular_smalldust", "invar_smalldust", "iron_smalldust", "lazurite_smalldust",
		"lead_smalldust", "magnesium_smalldust", "manganese_smalldust", "marble_smalldust", "netherrack_smalldust", "nickel_smalldust", "obsidian_smalldust", "olivine_smalldust", "peridot_smalldust", "phosphorous_smalldust", "platinum_smalldust",
		"pyrite_smalldust", "pyrope_smalldust", "redstone_smalldust", "red_garnet_smalldust", "ruby_smalldust", "saltpeter_smalldust", "sapphire_smalldust", "saw_dust_smalldust", "silver_smalldust", "sodalite_smalldust", "spessartine_smalldust",
		"sphalerite_smalldust", "steel_smalldust", "sulfur_smalldust", "tin_smalldust", "titanium_smalldust", "tungsten_smalldust", "uvarovite_smalldust", "yellow_garnet_smalldust", "zinc_smalldust", "advanced_chainsaw", "advanced_chainsaw_animated",
		"advanced_drill", "advanced_jackhammer", "bronze_axe", "bronze_boots", "bronze_chestplate", "bronze_helmet", "bronze_hoe", "bronze_leggings", "bronze_pickaxe", "bronze_spade", "bronze_sword", "debug", "diamond_chainsaw",
		"diamond_chainsaw_animated", "diamond_drill", "diamond_jackhammer", "electric_treetap", "electric_wrench", "frequency_transmitter", "frequency_transmitter_coords", "nanosaber_low", "nanosaber_off", "nanosaber_on", "omni_tool", "peridot_axe",
		"peridot_boots", "peridot_chestplate", "peridot_helmet", "peridot_hoe", "peridot_leggings", "peridot_pickaxe", "peridot_spade", "peridot_sword", "rock_cutter", "ruby_axe", "ruby_boots", "ruby_chestplate", "ruby_helmet", "ruby_hoe",
		"ruby_leggings", "ruby_pickaxe", "ruby_spade", "ruby_sword", "sapphire_axe", "sapphire_boots", "sapphire_chestplate", "sapphire_helmet", "sapphire_hoe", "sapphire_leggings", "sapphire_pickaxe", "sapphire_spade", "sapphire_sword",
		"steel_chainsaw", "steel_chainsaw_animated", "steel_drill", "steel_jackhammer", "treetap", "wrench", "ejection_upgrade", "energy_storage_upgrade", "injection_upgrade", "overclocker_upgrade", "range_upgrade", "superconductor",
		"transformer_upgrade", "upgradebackingtemplate" };

	public static final Identifier SLOT_MACHINE_CONTAINER = new Identifier(TechRebornX.MOD_ID, "slot_machine");
	public static final Identifier GIVE_ITEM_PACKET = new Identifier(TechRebornX.MOD_ID, "give_item");
	public static List<ItemStack> items = new ArrayList<>();
	public static Block slotMachine;

	private static final Random random = new Random(System.currentTimeMillis());

	static {

	}

	private static Block register(String name, Block block, ItemGroup tab) {
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
		BlockItem item = new BlockItem(block, new Item.Settings().itemGroup(tab));
		item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
		register(name, item);
		return block;
	}

	private static Item register(String name, Item item) {
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
		return item;

	}

	@Override
	public void onInitialize() {
		ItemGroup group = FabricItemGroupBuilder.build(new Identifier(MOD_ID, MOD_ID), () -> Registry.ITEM.get(new Identifier(MOD_ID, "advanced_chainsaw")).getDefaultStack());
		slotMachine = register("slot_machine", new SlotMachineBlock(), group);
		for (String name : ITEMS) {
			Item item = new Item(new Item.Settings().itemGroup(group));
			register(name, item);
			items.add(new ItemStack(item));
		}
		ContainerProviderRegistry.INSTANCE.registerFactory(SLOT_MACHINE_CONTAINER, (syncId, identifier, player, buf) -> new SlotMachineContainer(syncId, player));
		ScreenProviderRegistry.INSTANCE.registerFactory(SLOT_MACHINE_CONTAINER, container -> new SlotMachineScreen((SlotMachineContainer) container));

		ServerSidePacketRegistry.INSTANCE.register(GIVE_ITEM_PACKET, (packetContext, packetByteBuf) -> {
			PlayerEntity entity = packetContext.getPlayer();
			entity.giveItemStack(items.get(random.nextInt(items.size() - 1)));
			ServerPlayerEntity entity1 = (ServerPlayerEntity) entity;
			entity1.container.sendContentUpdates();
		});

	}

	public static void giveItem(){
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		MinecraftClient.getInstance().getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(GIVE_ITEM_PACKET, buf));
	}
}
