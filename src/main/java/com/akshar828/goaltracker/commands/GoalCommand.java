package com.akshar828.goaltracker.commands;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.akshar828.goaltracker.GoalTrackerMod;
import com.google.gson.JsonObject;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;


public class GoalCommand implements ICommand {
	public static JsonObject data = GoalTrackerMod.data;
	public String GOAL_TOTAL = data.get("total").toString();
	public String GOAL_AMT = data.get("added").toString();
	public boolean GOAL_COMP = data.get("completed").getAsBoolean();
	public boolean GOAL_VIS = data.get("visible").getAsBoolean();

	

	private String help_text = "§7§m------------§7[§b§l Goal Tracker §7]§7§m------------" + "\n" +
	        "§b● /goal §eor§b /goal help §7- Open this menu" + "\n" +
	        "§b● /goal add <number> §7- Add a certain amount towards your total goal" + "\n" +
	        "§b● /goal set <number>  §7- Set your total goal" + "\n" +
	        "§b● /goal remove <number> §eor§b /goal take <number>  §7- Take a certain amount away towards your current goal" + "\n" +
	        "§b● /goal view <on|off>  §7- Toggles visibility of the tracker on the screen" + "\n" +
	        "§b● /goal reset  §7- Resets the goal by setting the total goal and current goal amount to 0." + "\n" +
	        "§b● /goal icon <§diron§b|§dgold§b|§dlapis§b|§dredstone§b|§ddiamond§b|§ddiamond2§b|§demerald§b|§demerald2§b> §eor§b /goal ci <§diron§b|§dgold§b|§dlapis§b|§dredstone§b|§ddiamond§b|§ddiamond2§b|§demerald§b|§demerald2§b>   §7- Changes the icon that is next to the HUD!" + "\n" +
	        "§b● /goal move  §7- Allows you to move the HUD!" + "\n" +
	        "§7§m------------------------------------------";
	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "track";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Add to goal: goal add <amount>. Take from goal: goal remove <amount> or goal take <amount>. Set total goal: goal set <amount>";
	}

	@Override
	public List<String> getCommandAliases() {
		List<String> commandAliases = new ArrayList();
		commandAliases.add("goal");
		commandAliases.add("gt");
		commandAliases.add("tracker");
		return commandAliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (sender instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender;
			NumberFormat commaFormat = NumberFormat.getInstance();
		    commaFormat.setGroupingUsed(true);
			if (args != null && args.length > 0) {
				//checks for args
				
				
				//set command
			if (args[0].equalsIgnoreCase("set")) {
				try {
				if (args[1] != null) {
					args[1] = args[1].replace(",", "");
				long num = Long.parseLong(args[1]);
				if (num > 0) {
				player.addChatMessage(new ChatComponentText("§2Successfully set goal to "+commaFormat.format(num)+"!"));
				if (num < Long.parseLong(this.GOAL_AMT)|| num == Long.parseLong(this.GOAL_AMT)) {
					this.GOAL_AMT = String.valueOf(num);
					this.GOAL_COMP = true;
					this.GOAL_TOTAL = args[1];
					data.addProperty("added", Long.parseLong(this.GOAL_AMT));
					data.addProperty("completed", true);
					data.addProperty("total", Long.parseLong(this.GOAL_TOTAL));
					GoalTrackerMod.writeToConfig(data);
					

				}
				else {
					this.GOAL_TOTAL = args[1];
					data.addProperty("total", Long.parseLong(this.GOAL_TOTAL));
					GoalTrackerMod.writeToConfig(data);
				}
				}
				else if (num > Long.parseLong(this.GOAL_AMT)) {
					this.GOAL_COMP = false;
					data.addProperty("completed", false);
					GoalTrackerMod.writeToConfig(data);
				}
				
				else if (num == 0) {
					player.addChatMessage(new ChatComponentText("§4The goal cannot be 0!"));
				}
				else if (args[1].charAt(0) == '-') {
					player.addChatMessage(new ChatComponentText("§4The goal cannot be less than 0!"));
				}
				}
				}
				
				catch (ArrayIndexOutOfBoundsException e) {
					player.addChatMessage(new ChatComponentText("§4Please enter an amount to set what the goal will be!"));	
				}
				catch (NumberFormatException e) {
					try {
					if (args[1].charAt(args[1].length()-1) == 'm' || args[1].charAt(args[1].length()-1) == 'M') {
						String num =  args[1].replace(args[1].substring(args[1].length()-1), "");
						BigDecimal n = new BigDecimal(num);
						n = n.multiply(BigDecimal.valueOf(1000000));
						long num2 = n.longValue();
						if (args[1].charAt(0) == '-' || String.valueOf(num2).charAt(0) == '-') {
							player.addChatMessage(new ChatComponentText("§4The goal cannot be less than 0!"));
							return;
						}
						if (num2 < Long.parseLong(this.GOAL_AMT) || num2 == Long.parseLong(this.GOAL_AMT)) {
							this.GOAL_AMT = String.valueOf(num2);
							this.GOAL_COMP = true;
							data.addProperty("added", Long.parseLong(this.GOAL_AMT));
							data.addProperty("completed", true);
							GoalTrackerMod.writeToConfig(data);
						}
						else if (num2 == 0) {
							player.addChatMessage(new ChatComponentText("§4The goal cannot be 0!"));
						}
						if (num2 > Long.parseLong(this.GOAL_TOTAL)) {
							this.GOAL_COMP = false;
							data.addProperty("completed", false);
							GoalTrackerMod.writeToConfig(data);
						}
						player.addChatMessage(new ChatComponentText("§2Successfully set goal to "+commaFormat.format(num2)+"!"));
						this.GOAL_TOTAL = String.valueOf(num2);
						data.addProperty("total", Long.parseLong(this.GOAL_TOTAL));
						GoalTrackerMod.writeToConfig(data);
						
					}
						else if (args[1].charAt(args[1].length()-1) == 'k' || args[1].charAt(args[1].length()-1) == 'K'){
							String num =  args[1].replace(args[1].substring(args[1].length()-1), "");
							BigDecimal n = new BigDecimal(num);
							n = n.multiply(BigDecimal.valueOf(1000));
							long num2 = n.longValue();
							if (args[1].charAt(0) == '-' || String.valueOf(num2).charAt(0) == '-') {
								player.addChatMessage(new ChatComponentText("§4The goal cannot be less than 0!"));
								return;
							}
							if (num2 < Long.parseLong(this.GOAL_AMT)|| num2 == Long.parseLong(this.GOAL_AMT)) {
								this.GOAL_AMT = String.valueOf(num2);
								this.GOAL_COMP=true;
								data.addProperty("added", Long.parseLong(this.GOAL_AMT));
								data.addProperty("completed", true);
								GoalTrackerMod.writeToConfig(data);
							}
							else if (num2 == 0) {
								player.addChatMessage(new ChatComponentText("§4The goal cannot be 0!"));
							}
							if (num2 > Long.parseLong(this.GOAL_TOTAL)) {
								this.GOAL_COMP = false;
								data.addProperty("completed", false);
								GoalTrackerMod.writeToConfig(data);
							}
							
							player.addChatMessage(new ChatComponentText("§2Successfully set goal to "+commaFormat.format(num2)+"!"));
							this.GOAL_TOTAL = String.valueOf(num2);
							data.addProperty("total", Long.parseLong(this.GOAL_TOTAL));
							GoalTrackerMod.writeToConfig(data);
							
						}
							else {
						
					player.addChatMessage(new ChatComponentText("§4That is an invalid amount! Please make sure your amount is a number and is less than 9,223,372,036,854,775,807.Using m for million or k for thousand is allowed."));	
				}
					}
					catch(NumberFormatException f) {
						player.addChatMessage(new ChatComponentText("§4That is an invalid amount! Please make sure your amount is a number and is less than 9,223,372,036,854,775,807. Using m for million or k for thousand is allowed."));
					}
				}
				}
			
			
			
			
			
			
			// "add" command
				else if (args[0].equalsIgnoreCase("add")) {
					try {
						if (args[1] != null) {
							args[1] = args[1].replace(",", "");
						long num = Long.parseLong(args[1]);
						if (args[1].charAt(0) == '-') {
							player.addChatMessage(new ChatComponentText("§4You can't add a negative number to the goal!"));
							return;
						}
						if (Long.parseLong(this.GOAL_AMT) + num >= Long.parseLong(this.GOAL_TOTAL) && this.GOAL_TOTAL != "0" && this.GOAL_COMP == false) {
							this.GOAL_AMT = String.valueOf(Long.parseLong(this.GOAL_AMT) + num);
							Long goal_total_converted = Long.parseLong(this.GOAL_TOTAL);
							Long over = Math.abs(Long.parseLong(this.GOAL_AMT)-Long.parseLong(this.GOAL_TOTAL));
							player.addChatMessage(new ChatComponentText("§bYou have completed your goal of "+commaFormat.format(goal_total_converted)+"! You went over by "+commaFormat.format(over)+"."));
							this.GOAL_COMP = true;
							data.addProperty("added", Long.parseLong(this.GOAL_AMT));
							data.addProperty("completed", true);
							GoalTrackerMod.writeToConfig(data);
							return;
						}
						else if (Long.parseLong(this.GOAL_AMT) + num >= Long.parseLong(this.GOAL_TOTAL) && this.GOAL_TOTAL != "0" && this.GOAL_COMP) {
							player.addChatMessage(new ChatComponentText("§2Successfully added "+commaFormat.format(num)+" towards goal!!"));
							this.GOAL_AMT = String.valueOf(Long.parseLong(this.GOAL_AMT) + num);
							data.addProperty("added", Long.parseLong(this.GOAL_AMT));
							GoalTrackerMod.writeToConfig(data);
							return;
						}
						else if (num > 0 && num <= Long.parseLong(this.GOAL_TOTAL) && num + Long.parseLong(this.GOAL_AMT) <= Long.parseLong(this.GOAL_TOTAL) && num <= Long.parseLong("100000000000")) {
						player.addChatMessage(new ChatComponentText("§2Successfully added "+commaFormat.format(num)+" towards goal!!"));
						this.GOAL_AMT = String.valueOf(Long.parseLong(this.GOAL_AMT) + num);
						data.addProperty("added", Long.parseLong(this.GOAL_AMT));
						GoalTrackerMod.writeToConfig(data);
						
						}
						else if (num == 0) {
							player.addChatMessage(new ChatComponentText("§4You cannot add 0 to the goal!"));
						}
						else if (this.GOAL_TOTAL == "0") {
							player.addChatMessage(new ChatComponentText("§4Your goal is 0!"));
						}
						else if (num > Long.parseLong("100000000000")) {
							player.addChatMessage(new ChatComponentText("§4You can only add up to 100,000,000,000 per command!"));
						}
							
						}
					}
					
						catch (ArrayIndexOutOfBoundsException e) {
							player.addChatMessage(new ChatComponentText("§4Please enter an amount to add to the goal."));	
							
						}
						catch (NumberFormatException e) {
			
							try {
							if (args[1].toLowerCase().charAt(args[1].length()-1) == 'm') {
								String num =  args[1].replace(args[1].substring(args[1].length()-1), "");
								BigDecimal n = new BigDecimal(num);
								n = n.multiply(BigDecimal.valueOf(1000000));
								long num2 = n.longValue();
								if (this.GOAL_TOTAL == "0") {
									player.addChatMessage(new ChatComponentText("§4Your goal is 0!"));
									return;
								}
								if (args[1].charAt(0) != '-' && String.valueOf(num2).charAt(0) == '-') {
									player.addChatMessage(new ChatComponentText("§4That is an invalid amount! Please make sure your amount is a number and is less than 100,000,000,000.Using m for million or k for thousand is allowed."));	
									return;
								}
								if (args[1].charAt(0) == '-') {
									player.addChatMessage(new ChatComponentText("§4You can't add a negative number to the goal!"));
									return;
								}
								if (args[1].charAt(0) != '-' && num2 < 0) {
									player.addChatMessage(new ChatComponentText("§4That is an invalid amount! Please make sure your amount is a number and is less than 100,000,000,000.Using m for million or k for thousand is allowed."));
									return;
								}
								if (num2 > Long.parseLong("100000000000") && args[1].charAt(0) != '-') {
									player.addChatMessage(new ChatComponentText("§4You can only add up to 100,000,000,000 per command!"));
									return;
								}
								if (Long.parseLong(this.GOAL_AMT) + num2 >= Long.parseLong(this.GOAL_TOTAL) && this.GOAL_COMP == false) {
									this.GOAL_AMT = String.valueOf(Long.parseLong(this.GOAL_AMT) + num2);
									Long goal_total_converted = Long.parseLong(this.GOAL_TOTAL);
									Long over = Math.abs(Long.parseLong(this.GOAL_AMT)-Long.parseLong(this.GOAL_TOTAL));
									player.addChatMessage(new ChatComponentText("§bYou have completed your goal of "+commaFormat.format(goal_total_converted)+"! You went over by "+commaFormat.format(over)+"."));
									this.GOAL_COMP = true;
									data.addProperty("added", Long.parseLong(this.GOAL_AMT));
									data.addProperty("completed", true);
									GoalTrackerMod.writeToConfig(data);
									return;
								}
								player.addChatMessage(new ChatComponentText("§2Successfully added "+commaFormat.format(num2)+" to goal!"));
								this.GOAL_AMT = String.valueOf(Long.parseLong(this.GOAL_AMT) + num2);
								data.addProperty("added", Long.parseLong(this.GOAL_AMT));
								GoalTrackerMod.writeToConfig(data);
								
							 }
								
								
							
								else if (args[1].toLowerCase().charAt(args[1].length()-1) == 'k'){
									
									String num1 =  args[1].replace(args[1].substring(args[1].length()-1), "");
									BigDecimal n1 = new BigDecimal(num1);
									n1 = n1.multiply(BigDecimal.valueOf(1000));
									long num3 = n1.longValue();
									if (this.GOAL_TOTAL == "0") {
										player.addChatMessage(new ChatComponentText("§4Your goal is 0!"));
										return;
									}
									if (args[1].charAt(0) != '-' && String.valueOf(num3).charAt(0) == '-') {
										player.addChatMessage(new ChatComponentText("§4That is an invalid amount! Please make sure your amount is a number and is less than 100,000,000,000.Using m for million or k for thousand is allowed."));	
										return;
									}
									if (args[1].charAt(0) == '-') {
										player.addChatMessage(new ChatComponentText("§4You can't add a negative number to the goal!"));
										return;
									}
									if (args[1].charAt(0) != '-' && num3 < 0) {
										player.addChatMessage(new ChatComponentText("§4That is an invalid amount! Please make sure your amount is a number and is less than 100,000,000,000.Using m for million or k for thousand is allowed."));
										return;
									}
									if (num3 > Long.parseLong("100000000000") && args[1].charAt(0) != '-') {
										player.addChatMessage(new ChatComponentText("§4You can only add up to 100,000,000,000 per command!"));
										return;
									}
									if (Long.parseLong(this.GOAL_AMT) + num3 >= Long.parseLong(this.GOAL_TOTAL) && this.GOAL_COMP == false) {
										this.GOAL_AMT = String.valueOf(Long.parseLong(this.GOAL_AMT) + num3);
										Long goal_total_converted = Long.parseLong(this.GOAL_TOTAL);
										Long over = Math.abs(Long.parseLong(this.GOAL_AMT)-Long.parseLong(this.GOAL_TOTAL));
										player.addChatMessage(new ChatComponentText("§bYou have completed your goal of "+commaFormat.format(goal_total_converted)+"! You went over by "+commaFormat.format(over)+"."));
										this.GOAL_COMP = true;
										data.addProperty("added", Long.parseLong(this.GOAL_AMT));
										data.addProperty("completed", true);
										GoalTrackerMod.writeToConfig(data);
										return;
									}
									player.addChatMessage(new ChatComponentText("§2Successfully added "+commaFormat.format(num3)+" to goal!"));
									this.GOAL_AMT = String.valueOf(Long.parseLong(this.GOAL_AMT) + num3);
									data.addProperty("added", Long.parseLong(this.GOAL_AMT));
									GoalTrackerMod.writeToConfig(data);
									
								 }
									else {
							player.addChatMessage(new ChatComponentText("§4That is an invalid amount! Please make sure your amount is a number and is less than 100,000,000,000.Using m for million or k for thousand is allowed."));	
							
							} 
							}
								
							
								
							catch(NumberFormatException f) {
								player.addChatMessage(new ChatComponentText("§4That is an invalid amount! Please make sure your amount is a number and is less than 100,000,000,000. Using m for million or k for thousand is allowed."));
							}
								}
					}
			
			
			
			
			//view command
			else if (args[0].equalsIgnoreCase("view")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("on")) {
						if (this.GOAL_VIS == false) {
							this.GOAL_VIS = true;
							data.addProperty("visible", true);
							player.addChatMessage(new ChatComponentText("§2Successfuly set visibility for goal to ON!"));
							GoalTrackerMod.writeToConfig(data);
						}
							else {
								player.addChatMessage(new ChatComponentText("§4The goal is already visible!"));
							}
					}
					else if (args[1].equalsIgnoreCase("off")) {
						if (this.GOAL_VIS == true) {
						this.GOAL_VIS = false;
						data.addProperty("visible", false);
						player.addChatMessage(new ChatComponentText("§2Successfuly set visibility for goal to OFF!"));
						GoalTrackerMod.writeToConfig(data);
					}
						else {
							player.addChatMessage(new ChatComponentText("§4The goal is already not visible!"));
						}
					}
					else {
						player.addChatMessage(new ChatComponentText("§4That is an invlid type! Please choose from on or off."));
					}
				}
				else {
					player.addChatMessage(new ChatComponentText("§4Please choose on or off!"));
				}
			}
			
			
			
			
			
			
			
			//reset command
			else if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("rs")) {
				if (this.GOAL_AMT.equals("0") && this.GOAL_TOTAL.equals("0")) {
					player.addChatMessage(new ChatComponentText("§2The goal is already reset!"));
					return;
				}
				this.GOAL_AMT = "0";
				data.addProperty("added", 0);
				this.GOAL_TOTAL = "0";
				data.addProperty("total", 0);
				this.GOAL_COMP = false;
				data.addProperty("completed", false);
				player.addChatMessage(new ChatComponentText("§2Successfully reset goal!"));
				GoalTrackerMod.writeToConfig(data);
			}
			
			
			
			
			
			
			
			
			
			
			//icon command
			else if (args[0].equalsIgnoreCase("icon") || args[0].equalsIgnoreCase("ci")) {
				if (args.length > 1) {
					if (GoalTrackerMod.getIcon().equals("diamond 2.png") && args[1].toLowerCase().equals("diamond2")) {
						player.addChatMessage(new ChatComponentText("§4The icon was already Diamond 2!"));
						return;
					}
					if (GoalTrackerMod.getIcon().equals("emerald 2.png") && args[1].toLowerCase().equals("emerald2")) {
						player.addChatMessage(new ChatComponentText("§4The icon was already Emerald 2!"));
						return;
					}
					if (GoalTrackerMod.getIcon().contains(args[1].toLowerCase())) {
						player.addChatMessage(new ChatComponentText("§4The icon is already "+(args[1].toLowerCase().substring(0, 1).toUpperCase()+args[1].toLowerCase().substring(1))+"!"));
						return;
					}
					switch (args[1].toLowerCase()) {
					case "iron":
						GoalTrackerMod.changeIcon(data, "iron.png");
						player.addChatMessage(new ChatComponentText("§2Successfully changed the icon to "+(args[1].toLowerCase().substring(0, 1).toUpperCase()+args[1].toLowerCase().substring(1))+"!"));
						break;
					case "gold":
						GoalTrackerMod.changeIcon(data, "gold.png");
						player.addChatMessage(new ChatComponentText("§2Successfully changed the icon to "+(args[1].toLowerCase().substring(0, 1).toUpperCase()+args[1].toLowerCase().substring(1))+"!"));
						break;
					case "lapis":
						GoalTrackerMod.changeIcon(data, "lapis.png");
						player.addChatMessage(new ChatComponentText("§2Successfully changed the icon to "+(args[1].toLowerCase().substring(0, 1).toUpperCase()+args[1].toLowerCase().substring(1))+"!"));
						break;
					case "redstone":
						GoalTrackerMod.changeIcon(data, "redstone.png");
						player.addChatMessage(new ChatComponentText("§2Successfully changed the icon to "+(args[1].toLowerCase().substring(0, 1).toUpperCase()+args[1].toLowerCase().substring(1))+"!"));
						break;
					case "diamond":
						GoalTrackerMod.changeIcon(data, "diamond.png");
						player.addChatMessage(new ChatComponentText("§2Successfully changed the icon to "+(args[1].toLowerCase().substring(0, 1).toUpperCase()+args[1].toLowerCase().substring(1))+"!"));
						break;
					case "diamond2":
						GoalTrackerMod.changeIcon(data, "diamond 2.png");
						player.addChatMessage(new ChatComponentText("§2Successfully changed the icon to Diamond 2!"));
						break;
					case "emerald":
						GoalTrackerMod.changeIcon(data, "emerald.png");
						player.addChatMessage(new ChatComponentText("§2Successfully changed the icon to "+(args[1].toLowerCase().substring(0, 1).toUpperCase()+args[1].toLowerCase().substring(1))+"!"));
						break;
					case "emerald2":
						GoalTrackerMod.changeIcon(data, "emerald 2.png");
						player.addChatMessage(new ChatComponentText("§2Successfully changed the icon to Emerald 2!"));
						break;
					default:
						player.addChatMessage(new ChatComponentText("§4Please choose from §eiron§4, §egold§4, §elapis§4, §eredstone§4, §ediamond§4, §ediamond2§4, §eemerald§4, §eemerald2§4!"));
						return;
					
					}
				}
				else {
					player.addChatMessage(new ChatComponentText("§2Please enter something to change the icon to!"));
				}
			}
			
			
			
			/*
			 help command
			 */
			else if (args[0].equalsIgnoreCase("help")) {
				player.addChatMessage(new ChatComponentText(help_text));
			}
			
			
			
			/*
			 move command
			 */
			else if (args[0].equalsIgnoreCase("move")) {
				GoalTrackerMod.openMenu();
			}
			else {
				player.addChatMessage(new ChatComponentText("§4Unknown command. Try §d/goal help §eor§d /goal§4 to see a list of commands."));


			}
			} 
			
			else  {
				player.addChatMessage(new ChatComponentText(help_text));
			}
			
			
		
		
		
	
	}
	}

	

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> ar = new ArrayList();
		if (args.length > 1) {
			if (args[0].startsWith("i")) {
				ar.add("iron");
				ar.add("gold");
				ar.add("redstone");
				ar.add("lapis");
				ar.add("diamond");
				ar.add("diamond2");
				ar.add("emerald");
				ar.add("emerald2");
				return ar;
			}
		}
		if (args[0].startsWith("i")) {
			ar.add("icon");
			ar.add("ci");
			return ar;
		}
		if (args[0].startsWith("a")) {
			ar.add("add");
			return ar;
		}
		if (args[0].startsWith("s")) {
			ar.add("set");
			return ar;
		}
		if (args[0].startsWith("r")) {
			ar.add("remove");
			return ar;
		}
		if (args[0].startsWith("v")) {
			if (args.length > 1) {
				if (this.GOAL_VIS == false) {
				ar.add("on");
				ar.add("off");
				return ar;
				}
				else if (this.GOAL_VIS) {
					ar.add("off");
					ar.add("on");
					return ar;
				}
			}
			ar.add("view");
			return ar;
		}
		if (args[0].startsWith("h")) {
			ar.add("help");
			return ar;
		}
		if (args[0].startsWith("m")) {
			ar.add("move");
			return ar;
		}
		ar.add("add");
		ar.add("remove");
		ar.add("set");
		ar.add("view");
		ar.add("icon");
		ar.add("ci");
		ar.add("move");
		// TODO Auto-generated method stub
		return ar;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

}
