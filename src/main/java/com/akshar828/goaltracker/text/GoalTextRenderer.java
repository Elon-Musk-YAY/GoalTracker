package com.akshar828.goaltracker.text;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class GoalTextRenderer extends GuiScreen {
	public static GoalText goaltext;
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if (event.type != ElementType.EXPERIENCE) return;
		goaltext = new GoalText(Minecraft.getMinecraft());
	}
	
	
}

