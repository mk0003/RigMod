package com.mitat.rigmod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
	public static void register(IForgeRegistry<Item> registry) {
		registry.register(new ItemBlock(ModBlocks.randomItemGeneratorBlock).setRegistryName(ModBlocks.randomItemGeneratorBlock.getRegistryName()));
	}
}
