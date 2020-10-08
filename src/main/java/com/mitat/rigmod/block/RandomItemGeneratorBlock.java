package com.mitat.rigmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class RandomItemGeneratorBlock extends Block {

	public RandomItemGeneratorBlock() {
		super(Properties.create(Material.ROCK).hardnessAndResistance(0.5f).harvestLevel(0).harvestTool(ToolType.PICKAXE));
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
		if (!world.isRemote) {
			ItemStack mainHandTool = player.getHeldItemMainhand();

			boolean hasSilkTouch = false;
			int fortuneLevel = 0;
			if (mainHandTool.getToolTypes().contains(ToolType.PICKAXE)) {
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(mainHandTool);
				for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
					if (entry.getKey() == Enchantments.SILK_TOUCH) {
						hasSilkTouch = true;
					} else if (entry.getKey() == Enchantments.FORTUNE) {
						fortuneLevel = entry.getValue();
					}
				}
			}

			if (!hasSilkTouch) {
				Collection<ResourceLocation> itemKeyList = ForgeRegistries.ITEMS.getKeys();

				int fortuneCount = calculateFortune(fortuneLevel);

				for (int i = 0; i <= fortuneCount; i++) {

					int randomIndex = (int) (Math.random() * itemKeyList.size());
					Optional<ResourceLocation> key = itemKeyList.stream().skip(randomIndex).findFirst();

					if (key.isPresent()) {
						Item randomItem = ForgeRegistries.ITEMS.getValue(key.get());

						ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(randomItem, 1));
						world.addEntity(item);
					}
				}

			} else {
				Item thisItem = BlockItem.BLOCK_TO_ITEM.get(this);
				ItemEntity item = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(thisItem, 1));
				world.addEntity(item);

				super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
			}
		}

		return true;
	}

	private int calculateFortune(int fortuneLevel) {
		if (fortuneLevel < 1)
			return 0;

		double partNumber = Math.random() * (fortuneLevel + 2);

		int result = 0;
		if (partNumber >= 2) {
			result = (int) (partNumber - 1);
		}

		return result;
	}
}
