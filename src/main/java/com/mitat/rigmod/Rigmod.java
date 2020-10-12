package com.mitat.rigmod;

import com.mitat.rigmod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Rigmod.MODID, name = Rigmod.NAME, version = Rigmod.VERSION)
public class Rigmod {

	public static final String MODID = "rigmod";
	public static final String NAME = "Random Item Generator Mod";
	public static final String VERSION = "1.12.2-1.0";

	@SidedProxy(clientSide = "com.mitat.rigmod.proxy.ClientProxy", serverSide = "com.mitat.rigmod.proxy.ServerProxy")
	public static CommonProxy proxy;

	public static CreativeTabs RIG_ITEM_GROUP = new CreativeTabs(MODID) {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModBlocks.randomItemGeneratorBlock);
		}
	};

	public Rigmod() {
	}

	@Mod.Instance
	public static Rigmod instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
}