package com.mitat.rigmod.setup;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {

	public static final String CATEGORY_GENERAL = "general";

	public static ForgeConfigSpec SERVER_CONFIG;

	public static ForgeConfigSpec.BooleanValue ALLOW_FORTUNE;
	public static ForgeConfigSpec.BooleanValue ALLOW_SILK_TOUCH;

	static {

		ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

		SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

		ALLOW_FORTUNE = SERVER_BUILDER.comment("Allow fortune enchantment to give multiple drops").define("allowFortune", true);
		ALLOW_SILK_TOUCH = SERVER_BUILDER.comment("Allow silk touch enchantment to drop item itself").define("allowSilkTouch", true);

		SERVER_BUILDER.pop();

		SERVER_CONFIG = SERVER_BUILDER.build();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {

	}

	@SubscribeEvent
	public static void onReload(final ModConfig.Reloading configEvent) {

	}

}
