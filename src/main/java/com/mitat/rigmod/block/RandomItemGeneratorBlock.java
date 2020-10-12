package com.mitat.rigmod.block;

import com.mitat.rigmod.Rigmod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class RandomItemGeneratorBlock extends Block {

	public RandomItemGeneratorBlock() {
		super(Material.ROCK);
		setHardness(0.5f);
		setHarvestLevel("pickaxe", 0);
		setUnlocalizedName(Rigmod.MODID + ".random_item_generator_block");
		setRegistryName("random_item_generator_block");
		setCreativeTab(Rigmod.RIG_ITEM_GROUP);
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (!world.isRemote) {

			if (player.isCreative()) {
				return super.removedByPlayer(state, world, pos, player, willHarvest);
			}

			ItemStack mainHandTool = player.getHeldItemMainhand();

			boolean hasSilkTouch = false;
			int fortuneLevel = 0;
			if (mainHandTool.getItem().getToolClasses(mainHandTool).contains("pickaxe")) {
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
						if (randomItem != null)
							InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(randomItem));
					}
				}

				return false;
			} else {
				return super.removedByPlayer(state, world, pos, player, willHarvest);
			}
		}

		return super.removedByPlayer(state, world, pos, player, willHarvest);
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
