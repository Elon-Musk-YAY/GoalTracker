package com.akshar828.goaltracker.gui;

import java.io.IOException;

import com.akshar828.goaltracker.GoalTrackerMod;
import com.akshar828.goaltracker.commands.GoalCommand;
import com.akshar828.goaltracker.text.GoalText;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSlider;

public class MoveGui extends GuiScreen {

	GuiSlider xcord;
	GuiSlider ycord;
	
	@Override
	public void initGui() {
		buttonList.add(new GuiButtonExt(1, width/2-100, height/2+30, 200, 20, "Done"));
//		if (width < GoalText.refXY.get(0)) {
//			buttonList.add(xcord = new GuiSlider(2, width/2-100, height/2-25, 200, 20, "X coords: ", "", 0, width, width, false, true));
//		}
//		else {
		buttonList.add(xcord = new GuiSlider(2, width/2-100, height/2-25, 200, 20, "X coords: ", "", 0, width, Double.parseDouble(String.valueOf(GoalText.refXY.get(0))), false, true));
//		}
//		if (height < GoalText.refXY.get(1)) {
//			buttonList.add(ycord = new GuiSlider(3, width/2-100, height/2, 200, 20, "Y coords: ", "", 0, height, height, false, true));
//		}
		buttonList.add(ycord = new GuiSlider(3, width/2-100, height/2, 200, 20, "Y coords: ", "", 0, height, Double.parseDouble(String.valueOf(GoalText.refXY.get(1))), false, true));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case 1:
			Minecraft.getMinecraft().thePlayer.closeScreen();
			break;
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GoalText.refXY.set(0, xcord.getValueInt());
		GoalText.refXY.set(1, ycord.getValueInt());
		GoalCommand.data.addProperty("x", GoalText.refXY.get(0));
		GoalCommand.data.addProperty("y", GoalText.refXY.get(1));
		GoalTrackerMod.writeToConfig(GoalCommand.data);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}
 }
