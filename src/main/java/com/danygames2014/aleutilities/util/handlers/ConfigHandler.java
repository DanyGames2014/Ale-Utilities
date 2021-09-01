package com.danygames2014.aleutilities.util.handlers;

import java.io.File;

import com.danygames2014.aleutilities.AleUtilities;
import com.danygames2014.aleutilities.util.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	public static Configuration config;
	
	public static int unloadDelay = 600;
	public static String[] blacklist = {};
	public static String[] defaultblacklist = {"0"};
	
	public static void init(File file) {
		config = new Configuration(file);
		String category;
		
		category = "Automatic Unloading";
		config.addCustomCategoryComment(category, "Settings for automatic dimension unloading");
		unloadDelay = config.getInt("Unload Delay",category,600,100,36000,"The delay between dimension unloading");
		blacklist = config.getStringList("blacklist", category, defaultblacklist, "The Blacklist of Dimensions that will not be unloaded");
		
		config.save();
	}
	
	public static void registerConfig(FMLPreInitializationEvent event) {
		AleUtilities.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
		AleUtilities.config.mkdirs();
		init(new File(AleUtilities.config.getPath(), Reference.MOD_ID + ".cfg"));
	}
}
