package com.akshar828.goaltracker.text;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.akshar828.goaltracker.GoalTrackerMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GoalText extends Gui {
	public String text="";
	public String second_text="";
	public int scale = 1;
	//TODO make a way to abbreviate numbers so they aren't so big
	public static List<Integer> refXY;
	public GoalText(Minecraft mc)
	{
		try {
		
		NumberFormat commaFormat = NumberFormat.getInstance();
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
	    commaFormat.setGroupingUsed(true);
		mc.renderEngine.bindTexture(new ResourceLocation("GoalTracker",GoalTrackerMod.getIcon()));
	    long goal_amt = Long.parseLong(GoalTrackerMod.command.GOAL_AMT);
	    long goal_total = Long.parseLong(GoalTrackerMod.command.GOAL_TOTAL);
	    if (GoalTrackerMod.command.GOAL_COMP && GoalTrackerMod.command.GOAL_VIS) {
	    	second_text = "§aCOMPLETE§r";
	    	text= "GOAL: §5"+commaFormat.format(goal_amt)+" §3out of §1"+commaFormat.format(goal_total)+"§r";
			drawModalRectWithCustomSizedTexture(refXY.get(0) - 32, refXY.get(1) -6, 0, 0, 35*scale, 35*scale, 35*scale, 35*scale);
	    }
	    else {
	    if (GoalTrackerMod.command.GOAL_VIS) {
		text= "GOAL: §5"+commaFormat.format(goal_amt)+" §3out of §1"+commaFormat.format(goal_total)+"§r";
    	second_text = "§4INCOMPLETE§r";
		drawModalRectWithCustomSizedTexture(refXY.get(0) - 32, refXY.get(1) - 6, 0, 0, 35*scale, 35*scale, 35*scale, 35*scale);
	    }

	    }
		
		

		drawString(mc.fontRendererObj, this.text, refXY.get(0), refXY.get(1), Integer.parseInt("00AAAA", 16*scale));
		drawString(mc.fontRendererObj, this.second_text, refXY.get(0), refXY.get(1)+15, Integer.parseInt("FFAA00", 16*scale));
		
		
	} catch (Exception e) {
		e.printStackTrace();
	} 
		}
	
	public void changeIconVar (String new1) {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("GoalTracker", GoalTrackerMod.getIcon()));
		
	}
}
