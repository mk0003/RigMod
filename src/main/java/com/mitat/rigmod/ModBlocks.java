package com.mitat.rigmod;

import com.mitat.rigmod.block.RandomItemGeneratorBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

	@GameRegistry.ObjectHolder("rigmod:random_item_generator_block")
	public static RandomItemGeneratorBlock randomItemGeneratorBlock;

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		randomItemGeneratorBlock.initModel();
	}

	public static void register(IForgeRegistry<Block> registry) {
		registry.register(new RandomItemGeneratorBlock());
	}
}
