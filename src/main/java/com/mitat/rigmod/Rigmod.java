package com.mitat.rigmod;

import com.mitat.rigmod.block.RandomItemGeneratorBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Rigmod.MODID)
public class Rigmod {

	public static final String MODID = "rigmod";

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<RandomItemGeneratorBlock> RIG_BLOCK = BLOCKS.register("random_item_generator_block", RandomItemGeneratorBlock::new);

	public static final ItemGroup RIG_ITEM_GROUP = new ItemGroup(MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(RIG_BLOCK.get());
		}
	};

	public static final RegistryObject<Item> RIG_ITEM = ITEMS.register("random_item_generator_item", () -> new BlockItem(RIG_BLOCK.get(), new Item.Properties().group(RIG_ITEM_GROUP)));

	public Rigmod() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
