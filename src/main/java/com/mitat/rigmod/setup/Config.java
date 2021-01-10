package com.mitat.rigmod.setup;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class Config {

	public static final String CATEGORY_GENERAL = "general";
	public static final String CATEGORY_BLACKLIST_WHITELIST = "blacklist/whitelist";

	public static ForgeConfigSpec SERVER_CONFIG;

	public static ForgeConfigSpec.BooleanValue ALLOW_FORTUNE;
	public static ForgeConfigSpec.BooleanValue ALLOW_SILK_TOUCH;

	public static ForgeConfigSpec.ConfigValue<ArrayList<String>> ITEM_WHITELIST;
	public static ForgeConfigSpec.ConfigValue<ArrayList<String>> ITEM_BLACKLIST;

	public static ForgeConfigSpec.ConfigValue<ArrayList<String>> MOD_WHITELIST;
	public static ForgeConfigSpec.ConfigValue<ArrayList<String>> MOD_BLACKLIST;

	public static ForgeConfigSpec.ConfigValue<ArrayList<String>> TAG_WHITELIST;
	public static ForgeConfigSpec.ConfigValue<ArrayList<String>> TAG_BLACKLIST;

	static {

		ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

		SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

		ALLOW_FORTUNE = SERVER_BUILDER.comment("Allow fortune enchantment to give multiple drops").define("allowFortune", true);
		ALLOW_SILK_TOUCH = SERVER_BUILDER.comment("Allow silk touch enchantment to drop item itself").define("allowSilkTouch", true);

		SERVER_BUILDER.pop();

		SERVER_BUILDER.comment("Blacklist/Whitelist settings").push(CATEGORY_BLACKLIST_WHITELIST);

		ITEM_WHITELIST = SERVER_BUILDER.comment("Whitelisted Items (Leave empty to whitelist all items unless blacklist specified)").define("item_whitelist", new ArrayList<>());
		ITEM_BLACKLIST = SERVER_BUILDER.comment("Blacklisted Items").define("item_blacklist", new ArrayList<>());

		MOD_WHITELIST = SERVER_BUILDER.comment("Whitelisted Mods (Leave empty to whitelist all mods unless blacklist specified)").define("mod_whitelist", new ArrayList<>());
		MOD_BLACKLIST = SERVER_BUILDER.comment("Blacklisted Mods").define("mod_blacklist", new ArrayList<>());

		TAG_WHITELIST = SERVER_BUILDER.comment("Whitelisted Tags (Leave empty to whitelist all tags unless blacklist specified)").define("tag_whitelist", new ArrayList<>());
		TAG_BLACKLIST = SERVER_BUILDER.comment("Blacklisted Tags").define("tag_blacklist", new ArrayList<>());

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
