package com.mitat.rigmod.block;

import com.mitat.rigmod.setup.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class RandomItemGeneratorBlock extends Block {

	public RandomItemGeneratorBlock() {
		super(Properties.create(Material.ROCK).hardnessAndResistance(0.5f).harvestLevel(0).harvestTool(ToolType.PICKAXE));
	}

	@Override
	public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
		if (!world.isRemote) {

			if (player.isCreative()) {
				return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
			}

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

			if (!Config.ALLOW_SILK_TOUCH.get())
				hasSilkTouch = false;
			if (!Config.ALLOW_FORTUNE.get())
				fortuneLevel = 0;

			if (!hasSilkTouch) {
				ArrayList<Item> itemList = new ArrayList<>();
				for (int i = 0; i <= ForgeRegistries.ITEMS.getKeys().size(); i++) {
					Optional<ResourceLocation> itemKey = ForgeRegistries.ITEMS.getKeys().stream().skip(i).findFirst();
					if (itemKey.isPresent()) {
						Item item = ForgeRegistries.ITEMS.getValue(itemKey.get());
						if (item != null) {
							ResourceLocation itemRegistry = item.getRegistryName();
							if (itemRegistry != null) {
								boolean isItemWhitelisted = Config.ITEM_WHITELIST.get().contains(itemRegistry.toString());
								boolean isItemBlacklisted = Config.ITEM_BLACKLIST.get().contains(itemRegistry.toString());
								boolean isModWhitelisted = Config.MOD_WHITELIST.get().contains(itemRegistry.getNamespace());
								boolean isModBlacklisted = Config.MOD_BLACKLIST.get().contains(itemRegistry.getNamespace());
								boolean isTagWhitelisted = false;
								for (ResourceLocation tag : item.getTags()) {
									isTagWhitelisted = Config.TAG_WHITELIST.get().contains(tag.toString());
									if (isTagWhitelisted)
										break;
								}
								boolean isTagBlacklisted = false;
								for (ResourceLocation tag : item.getTags()) {
									isTagBlacklisted = Config.TAG_BLACKLIST.get().contains(tag.toString());
									if (isTagBlacklisted)
										break;
								}

								if (!isItemBlacklisted && !isModBlacklisted && !isTagBlacklisted)
									if (Config.ITEM_WHITELIST.get().isEmpty() || isItemWhitelisted)
										if (Config.MOD_WHITELIST.get().isEmpty() || isModWhitelisted)
											if (Config.TAG_WHITELIST.get().isEmpty() || isTagWhitelisted)
												itemList.add(item);
							}
						}
					}
				}

				int fortuneCount = calculateFortune(fortuneLevel);

				for (int i = 0; i <= fortuneCount; i++) {

					int randomIndex = (int) (Math.random() * itemList.size());

					Item randomItem = itemList.get(randomIndex);
					if (randomItem != null)
						InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(randomItem));
				}

				return false;
			} else {
				Item thisItem = BlockItem.BLOCK_TO_ITEM.get(this);
				if (thisItem != null)
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(thisItem));

				return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
			}
		}

		return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
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
