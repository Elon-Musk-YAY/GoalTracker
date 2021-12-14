package com.akshar828.goaltracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.akshar828.goaltracker.commands.GoalCommand;
import com.akshar828.goaltracker.gui.MoveGui;
import com.akshar828.goaltracker.text.GoalText;
import com.akshar828.goaltracker.text.GoalTextRenderer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;





@Mod(modid = GoalTrackerMod.MODID, version = GoalTrackerMod.VERSION, name=GoalTrackerMod.NAME, clientSideOnly=true)
public class GoalTrackerMod
{
    public static final String MODID = "GoalTracker";
    public static final String VERSION = "0.2.2";
    public static final String NAME = "Goal Tracker Mod";
    public static GoalCommand command = null;
    public static final GoalTextRenderer renderer = new GoalTextRenderer();
    public static JsonObject data;
    private static String ICON = "";
    private static String path = "";
    public static GoalTrackerMod instance = null;
    public static boolean openMenu;
    
	
    


    
    @EventHandler
    public void init(FMLInitializationEvent event) throws Exception
    {
        System.out.println("Successfully loaded mod!");
        instance = this;
		GoalText.refXY = new ArrayList<Integer>();
    	GoalText.refXY.add(data.get("x").getAsInt());
    	GoalText.refXY.add(data.get("y").getAsInt());
        MinecraftForge.EVENT_BUS.register(instance);
    }
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    	System.out.println("Server Loaded");
    }
    @EventHandler
    public void preinit(FMLPreInitializationEvent e) {
    	File config_file = new File(e.getModConfigurationDirectory().getAbsolutePath()+"/goaltracker/data.json");
    	try {
    		if (!config_file.exists()) {
    			new File(e.getModConfigurationDirectory().getAbsolutePath()+"/goaltracker").mkdir();
    			FileWriter writer = new FileWriter(e.getModConfigurationDirectory().getAbsolutePath()+"/goaltracker/data.json");
    			// ad scale amt
			    writer.write("{\"total\":0,\"added\":0, \"icon\":\"emerald 2.png\", \"visible\":true, \"completed\": false, \"x\":313 , \"y\": 40}");
				writer.close();
				data = new JsonParser().parse("{\"total\":0,\"added\":0, \"icon\":\"emerald 2.png\", \"visible\":true, \"completed\": false, \"x\":313 , \"y\": 40}").getAsJsonObject();
		    	ICON = data.get("icon").getAsString();
		    	path = e.getModConfigurationDirectory().getAbsolutePath()+"/goaltracker/data.json";
    		}
    		else {
    	path = e.getModConfigurationDirectory().getAbsolutePath()+"/goaltracker/data.json";
    	Reader reader = Files.newBufferedReader(Paths.get(e.getModConfigurationDirectory().getAbsolutePath()+"/goaltracker/data.json"));
    	data = new JsonParser().parse(reader).getAsJsonObject();
    	ICON = data.get("icon").getAsString();


		} 
    }
     catch (IOException e1) {
			e1.printStackTrace();
		}
    	this.command = new GoalCommand();
    	ClientCommandHandler.instance.registerCommand(this.command);
    	MinecraftForge.EVENT_BUS.register(this.renderer);
    }
    
    public static String getIcon () {
    	return ICON;
    }
    public static void changeIcon (JsonObject data2, String newq) {
    	ICON = newq;
    	data2.addProperty("icon", newq);
    	writeToConfig(data2);
    }
    public static JsonObject getData () {
    	return data;
    }
    public static void writeToConfig(JsonObject data2) {
    	FileWriter writer;
		try {
			writer = new FileWriter(path);
		    writer.write(data2.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public static GoalTrackerMod getMod () {
    	return instance;
    }
    
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent e){
        if(openMenu){
            Minecraft.getMinecraft().displayGuiScreen(new MoveGui());
            openMenu = false;
        }

        ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		if (GoalText.refXY.get(0) > width) {
			GoalText.refXY.set(0, width);
		}
		if (GoalText.refXY.get(1) > height) {
			GoalText.refXY.set(1, height);
		}
    }

    public static void openMenu(){
        openMenu = true;
    }
    
    
    
    

}
