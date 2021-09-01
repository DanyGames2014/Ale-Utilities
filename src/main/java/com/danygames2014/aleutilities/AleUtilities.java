package com.danygames2014.aleutilities;


import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.danygames2014.aleutilities.proxy.CommonProxy;
import com.danygames2014.aleutilities.util.Reference;
import com.danygames2014.aleutilities.util.handlers.RegistryHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID, useMetadata=true, version = Reference.VERSION, name = Reference.MOD_NAME, acceptableRemoteVersions="*")
public class AleUtilities {
	
	Logger logger = LogManager.getLogger(Reference.MOD_ID);
	
	public static File config;
	
	@Instance
	public static AleUtilities instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		RegistryHandler.preInitRegistries(event);
		logger.info("Alehouse Utilities v1.0 Loaded");
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {RegistryHandler.initRegistries();}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {RegistryHandler.postInitRegistries();}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		RegistryHandler.serverRegistries(event);
	}
}
