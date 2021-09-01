package com.danygames2014.aleutilities.util.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.danygames2014.aleutilities.util.Reference;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class UnloadHandler {
	private int tickClock = 0;
	
	Logger logger = LogManager.getLogger(Reference.MOD_ID);
	
	public void unloadDim(int dimid) {
		try {
			logger.info("Attempting to auto-unload dimension " + dimid);
			DimensionManager.keepDimensionLoaded(dimid, false);
			DimensionManager.unloadWorld(dimid);
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			logger.error("Exception when handling dimension ID \n" + e);
		}
		
	}
	
	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent tick) {
		if(tick.phase == TickEvent.Phase.END &&(++tickClock == ConfigHandler.unloadDelay)){
			tickClock = 0;
			
			Integer[] loadeddims = DimensionManager.getIDs();
			
			String[] sblacklistdims = ConfigHandler.blacklist;
			
			Integer[] blacklistdims = new Integer[sblacklistdims.length];
			
			for (int i = 0; i < sblacklistdims.length; i++) {
				blacklistdims[i] = Integer.parseInt(sblacklistdims[i]);
			}

			
			for (int i = 0; i < loadeddims.length; i++) {
				boolean tounload = true;
				
				for (int j = 0; j < blacklistdims.length; j++) {
					
					//System.out.println(loadeddims[i] + " " + blacklistdims[j] + (loadeddims[i].equals(blacklistdims[j])));
					if (loadeddims[i].equals(blacklistdims[j])) {
						tounload = false;
					}
					
				}
				
				//logger.info(loadeddims[i] + " will be unloaded : " + tounload);
				if(tounload) {unloadDim(loadeddims[i]);}
			}
					
		}
		
	}
}
