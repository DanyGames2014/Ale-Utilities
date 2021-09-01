package com.danygames2014.aleutilities.commands;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.danygames2014.aleutilities.util.Reference;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

public class CommandAleu extends CommandBase{
	
	private final List<String> aliases = Lists.newArrayList(Reference.MOD_ID, "aleu","sloth");
	
	Logger logger = LogManager.getLogger(Reference.MOD_ID);
	
	public void messagelogged(ICommandSender sender, String message, TextFormatting text) {
		logger.info(message + " | Triggered by : " + sender.getName());
		sender.sendMessage(new TextComponentString(text + message));
	}
	
	public void message(ICommandSender sender, String message, TextFormatting text) {
		sender.sendMessage(new TextComponentString(text + message));
	}
	
	public void messagec(ICommandSender sender, String message) {
		sender.sendMessage(new TextComponentString(TextFormatting.WHITE + message));
	}
	
	@Override
	public String getName() {
		return "aleutilities";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/aleutilities <module> <command> <arguments>";
	}
	
	@Override
	public List<String> getAliases(){
		return aliases;
	}
	
	@Override
	public int getRequiredPermissionLevel(){
        return 4;
    }
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 1) {
			sender.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Use /aleutilities help to display avalible commands"));
		}else {
			String category = args[0];
			switch (category) {
				case "help":
					sender.sendMessage(new TextComponentString(TextFormatting.BLUE + " TODO :) Annoy the hell out of DanyGames2014#9409 until this actually does something"));
				break;
				
				case "dimension":
					switch(args[1]){
						case "unload":
							try {
								int dimtounload = Integer.parseInt(args[2]);
								WorldServer worldserver = DimensionManager.getWorld(dimtounload);
								ChunkProviderServer provider = worldserver.getChunkProvider();
								if(provider.getLoadedChunkCount() == 0) {
								messagelogged(sender,"Attempting to unload dimension " + dimtounload,TextFormatting.YELLOW);
								DimensionManager.unloadWorld(dimtounload);
								}
							} catch (Exception e) {
								logger.error("Exception when handling dimension ID \n" + e);
								message(sender,"Invalid ID",TextFormatting.RED);
							}
							break;
							
						case "forceunload":
							try {
								int dimtounload = Integer.parseInt(args[2]);
								messagelogged(sender,"Attempting to force unload dimension " + dimtounload,TextFormatting.YELLOW);
								DimensionManager.keepDimensionLoaded(dimtounload, false);
								DimensionManager.unloadWorld(dimtounload);
							} catch (Exception e) {
								logger.error("Exception when handling dimension ID \n" + e);
								message(sender,"Invalid ID",TextFormatting.RED);
							}
							break;
							
						case "info":
							int dimid;	
							try {
								dimid = Integer.parseInt(args[2]);
								DimensionType providerType = DimensionManager.getProviderType(dimid);
								WorldServer worldserver = DimensionManager.getWorld(dimid);
								ChunkProviderServer provider = worldserver.getChunkProvider();
								
								message(sender,"Info About Dimension " + dimid + " (" + providerType.getName() + ")",TextFormatting.YELLOW);
								messagec(sender,
										"Should load spawn? : " + providerType.shouldLoadSpawn() 
										+ "\nTime : " + worldserver.getWorldTime() 
										+ "\nChunks Loaded : " + provider.getLoadedChunkCount()	
										+ "\nLoaded Entities : " + worldserver.loadedEntityList.size() 
										+ "\nLoaded Tile Entities : " + worldserver.loadedTileEntityList.size()
										+ "\nPlayers : " + worldserver.playerEntities.size());
								
								for (int i = 0; i < worldserver.playerEntities.size(); i++) {
									messagec(sender,worldserver.playerEntities.get(i).getName());
								}
										
							} catch (Exception e) {
								
							}
							break;
					
						case "keeploaded":
							try {
								int dimtokeep = Integer.parseInt(args[2]);
								DimensionType providerType = DimensionManager.getProviderType(dimtokeep);
								
								if(args.length == 3) {
								DimensionManager.keepDimensionLoaded(dimtokeep, true);
								messagelogged(sender,"Setting dimension's " + dimtokeep + " (" + providerType.getName() + ") keep loaded to true",TextFormatting.YELLOW);
								}else{
									if (args[3].contentEquals("true")) {
										DimensionManager.keepDimensionLoaded(dimtokeep, true);
										messagelogged(sender,"Setting dimension's " + dimtokeep + " (" + providerType.getName() + ") keep loaded to true",TextFormatting.YELLOW);
									} else if(args[3].contentEquals("false")) {
										DimensionManager.keepDimensionLoaded(dimtokeep, false);
										messagelogged(sender,"Setting dimension's " + dimtokeep + " (" + providerType.getName() + ") keep loaded to false",TextFormatting.YELLOW);
									} else {
										message(sender,"Invalid true/false option",TextFormatting.RED);
									}
								}

							} catch (Exception e) {
								logger.error("Exception when handling dimension ID \n" + e);
								sender.sendMessage(new TextComponentString(TextFormatting.RED + " Invalid ID"));
							}
							break;
							
						/*case "isloaded":
							try {
								String dimname = "";
								int dimtosee = Integer.parseInt(args[2]);
								System.out.println("before provide");
								DimensionType providerType = DimensionManager.getProviderType(dimtosee);
								System.out.println("after prodive");
								dimname = providerType.getName();
								System.out.println(dimname);
								System.out.println(DimensionManager.getProvider(dimtosee).getDimension());
								
							}catch(Exception e){
								System.out.println("OwO"+e);
							}
							break;*/
							
						case "getid":
							Integer[] loadeddims = DimensionManager.getIDs();
							for (int i = 0; i < loadeddims.length; i++) {
								messagec(sender,loadeddims[i]+"");
							}
							break;
					}
				break;

				default:
					sender.sendMessage(new TextComponentString(TextFormatting.RED + "Invalid Command, please use /aleutilities help to display avalible commands"));
				break;
			}
			
		}
		
	}

}
