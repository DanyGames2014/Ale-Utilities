package com.danygames2014.aleutilities.util.handlers;

import com.danygames2014.aleutilities.commands.CommandAleu;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class RegistryHandler {
	
	public static void preInitRegistries(FMLPreInitializationEvent event) {
		ConfigHandler.registerConfig(event);
	}
	
	public static void initRegistries() {
		MinecraftForge.EVENT_BUS.register(new UnloadHandler());
	}

	public static void postInitRegistries() {
	
	}

	public static void serverRegistries(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandAleu());
	}
}
