import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * This class creates the graphics for the Monopoly game and displays the
 * board, information panel, and the control/input panel upon calling up the 
 * program.
 * 
 * @author Lilah Koudelka
 * @author Elizabeth Burke
 * @version February 27, 2017 Sprint 2
 */
/**
 * @author Lilah Koudelka
 * @author Elizabeth Burke
 * @version April 10, 2017 Sprint 4
 */
public class MonopolyGUI
{
	private static final int FRAME_WIDTH = 1150;
	private static final int FRAME_HEIGHT = 747;
	private static final String CURRENCY = " drops of blood";

	//List of command ID's
	public static final int CMD_QUIT = 0;
	public static final int CMD_DONE = 1;
	public static final int CMD_ROLL = 2;
	public static final int CMD_BUY = 3;
	public static final int CMD_PAY_RENT = 4;
	public static final int CMD_DEMOLISH = 5;
	public static final int CMD_PROPERTY = 6;
	public static final int CMD_BALANCE = 7;
	public static final int CMD_BANKRUPT = 8;
	public static final int CMD_BUILD = 9;
	public static final int CMD_HELP = 10;
	public static final int CMD_MORTGAGE = 11;
	public static final int CMD_REDEEM = 12;
	public static final int CMD_CHEAT = 13;

	//List of error ID's
	public static final int ERR_SYNTAX = 0;
	public static final int ERR_DOUBLE_ROLL = 1;
	public static final int ERR_NO_ROLL = 2;
	public static final int ERR_INSUFFICIENT_FUNDS = 3;
	public static final int ERR_NOT_A_PROPERTY = 4;
	public static final int ERR_NEGATIVE_BALANCE = 5;
	public static final int ERR_NOT_OWNED = 6;
	public static final int ERR_IS_OWNED = 7;
	public static final int ERR_SELF_OWNED = 8;
	public static final int ERR_NOT_YOURS = 9;
	public static final int ERR_NO_MONOPOLY = 10; //can't build
	public static final int ERR_HAS_BUILDINGS = 11; 
	public static final int ERR_NOT_A_SITE = 12; //can't build on unowned or invalid property
	public static final int ERR_TOO_MANY_HOUSES = 13; //too many houses
	public static final int ERR_TOO_FEW_HOUSES = 14;
	public static final int ERR_NO_HOUSES = 15;
	public static final int ERR_NOT_ENOUGH_HOUSES = 16;
	public static final int ERR_NOT_A_NAME = 17;
	public static final int ERR_DUPLICATE = 18;
	public static final int ERR_IS_NOT_MORTGAGED = 19;
	public static final int ERR_ALREADY_MORTGAGED = 20;
	public static final int ERR_STUCK_IN_JAIL = 21;
	
	//List of Chance Card ID's
	public static final int CARD_CHANCE_1 = 0;
	public static final int CARD_CHANCE_2 = 1;
	public static final int CARD_CHANCE_3 = 2;
	public static final int CARD_CHANCE_4 = 3;
	public static final int CARD_CHANCE_5 = 4;
	public static final int CARD_CHANCE_6 = 5;
	public static final int CARD_CHANCE_7 = 6;
	public static final int CARD_CHANCE_8 = 7;
	public static final int CARD_CHANCE_9 = 8;
	public static final int CARD_CHANCE_10 = 9;
	public static final int CARD_CHANCE_11 = 10;
	public static final int CARD_CHANCE_12 = 11;
	public static final int CARD_CHANCE_13 = 12;
	public static final int CARD_CHANCE_14 = 13;
	public static final int CARD_CHANCE_15 = 14;
	public static final int CARD_CHANCE_16 = 15;
	
	//List of Community Chest Card ID's
	public static final int CARD_COMMUNITY_1 = 0;
	public static final int CARD_COMMUNITY_2 = 1;
	public static final int CARD_COMMUNITY_3 = 2;
	public static final int CARD_COMMUNITY_4 = 3;
	public static final int CARD_COMMUNITY_5 = 4;
	public static final int CARD_COMMUNITY_6 = 5;
	public static final int CARD_COMMUNITY_7 = 6;
	public static final int CARD_COMMUNITY_8 = 7;
	public static final int CARD_COMMUNITY_9 = 8;
	public static final int CARD_COMMUNITY_10 = 9;
	public static final int CARD_COMMUNITY_11 = 10;
	public static final int CARD_COMMUNITY_12 = 11;
	public static final int CARD_COMMUNITY_13 = 12;
	public static final int CARD_COMMUNITY_14 = 13;
	public static final int CARD_COMMUNITY_15 = 14;
	public static final int CARD_COMMUNITY_16 = 15;

	//Error messages
	private final String[] errorMessages = 
		{
				"Umm, no. Not a valid command. Try again, buddy.",
				"Don't be greedy! You took too many rolls this turn.",
				"You can't get away that easily! You have to roll now!.",
				"Bummer dude, you don't have enough money.",
				"Does it look like this place is for sale? NO! This square is not a property.",
				"Thief! Pay up! You must pay your debt!",
				"Sorry, you can't pay a ghost! The property is not owned.",
				"Ooo, you missed your chance. The property is already owned.",
				"Don't be silly! You already own the property.",
				"Say what? That's not a property in your possession. You don't own the world. Tsk, tsk, tsk...",
				"Slow down there! You don't have a Monopoly, so you can't build any houses/hotels.",
				"Don't be Mr. Krabs! You have to sell the houses before you mortgage, penny pincher.",
				"Bet you wish that this square was a site... Yeah, well it's not!",
				"Do you think buildings grow on trees? No. You get 5 max.",
				"Fickle, fickle, fickle. Fine then, don't buy a your mother a house... Selfish.",
				"Uh oh! There's nothing to demolish. You don't own any buildings.",
				"Whoa there! You think you can demolish more buildings than you own? Well, think again. ",
				"What is that? Gibberish? That's not a valid name, weirdo.",
				"Are you seeing double? No duplicate names allowed!",
				"Hold up now! Get back here! This property is not yet mortgaged.",
				"You can't mortgage a mortgaged property. This isn't inception! Move along.",
				"Where do you think you're going? There's no escape for criminals!"
		};
	
	private final String[] communityChest = 
		{
			"Advance to Go.",
			"Go back to Undead Burg.",
			"Go directly to jail. Do not pass Go. Do not collect 2000 drops of blood.",
			"You donate to the blood drive. Pay hospital 1000 drops of blood.",
			"You break your leg and rush to the ER. Pay 500 drops of blood.",
			"Pay your insurance premium 500 drops of blood.",
			"Bank error in your favour. Collect 2000 drops of blood.",
			"Your grandmother dies, but you inherit 1000 drops of blood.",
			"Annuity matures. Collect 1000 drops of blood.",
			"From sale of stock you get 500 drops of blood.",
			"Receive interest on 7% preference shares: 250 drops of blood.",
			"Income tax refund. Collect 200 drops of blood.",
			"You have won second prize in a beauty contest. Collect 100 drops of blood.",
			"It is your birthday. Collect 100 drops of blood from each player.",
			"Get out of jail free card. This card may be kept until needed or sold.",
			"Pay a fine of 100 drops of blood or take a Chance."
		};
	
	private final String[] chance = 
		{
			"Advance to Go.",
			"Go directly to jail. Do not pass Go. Do not collect 2000 drops of blood.",
			"Advance to Firelink Shrine. If you pass Go collect 2000 drops of blood.",
			"Take a trip to Royal Wood, and if you pass Go, collect 2000 drops of blood.",
			"Advance to Lost Izalith. If you pass Go collect 2000 drops of blood.",
			"Advance to King of the First Flame.",
			"Go back three spaces.",
			"Make general repairs on all of your houses. For each house pay 250 drops of blood. For each hotel pay 1000 drops of blood.",
			"You are assessed for street repairs: 400 drops of blood per house, 1150 drops of blood per hotel.",
			"Pay school fees of 1500 drops of blood.",
			"Drunk in charge fine 200 drops of blood.",
			"Speeding fine of 150 drops of blood.",
			"Your building loan matures. Receive 1500 drops of blood.",
			"You have won a crossword competition. Collect 1000 drops of blood.",
			"Bank pays you dividend of 500 drops of blood.",
			"Get out of jail free. This card may be kept until needed or sold."
		};

	private JFrame frame = new JFrame();
	private BoardPanel boardPanel;	
	private InformationPanel infoPanel = new InformationPanel();
	private CommandPanel commandPanel = new CommandPanel();
	private String string;
	private boolean done;
	private int commandId;
	private int chanceId;
	private int communityId;
	private Board board;
	private Players players;
	private Property inputProperty;
	private int inputNumber;
	private Player inputPlayer;

	MonopolyGUI(Players players, Board board) 
	{
		this.players = players;
		this.board = board;
		boardPanel = new BoardPanel(this.players);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Monopoly");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(boardPanel, BorderLayout.LINE_START);
		frame.add(infoPanel, BorderLayout.LINE_END);
		frame.add(commandPanel,BorderLayout.PAGE_END);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	/*--------------------INPUT METHODS--------------------*/

	/**
	 * Method that allows user to input the players names.
	 * @param numPlayers
	 */
	public void inputName(int numPlayers) 
	{
		boolean inputValid = false;
		if(numPlayers == 0)
		{
			infoPanel.addText("Enter new player name (" + boardPanel.getTokenName(numPlayers) + "):");			
		} 
		else 
		{
			infoPanel.addText("Enter new player name (" + boardPanel.getTokenName(numPlayers)  +  ") or done:");
		}
		do 
		{
			commandPanel.inputString();
			string = commandPanel.getString();
			string = string.trim();
			if(string.length() == 0) 
			{
				inputValid = false;
				done = false;
			} 
			else if((numPlayers > 0) && (string.toLowerCase().equals("done"))) 
			{
				inputValid = true;
				done = true;
			} 
			else if(string.contains(" ")) 
			{
				inputValid = false;
				done = false;
			} 
			else
			{
				inputValid = true;
			}
			infoPanel.addText("# " + string);
			if(!inputValid) 
			{
				displayError(ERR_NOT_A_NAME);
			}
		} while(!inputValid);
		return;
	}
	
	private boolean hasNoArgument(String[] words)
	{
		return (words.length == 1);
	}
	
	private boolean hasOneArgument(String[] words)
	{
		return (words.length == 2);
	}	

	private boolean hasTwoArguments(String[] words) 
	{
		return (words.length == 3);
	}

	public void inputCommand(Player player) 
	{
		boolean inputValid = false;
		do 
		{
			infoPanel.addText(player + " type your command: ");
			commandPanel.inputString();
			string = commandPanel.getString();
			infoPanel.addText("# " + string);
			string = commandPanel.getString();
			string = string.toLowerCase();
			string = string.trim();
			string = string.replaceAll("( )+", " ");
			String[] words = string.split(" ");
			
			switch(words[0]) 
			{
				case "quit" :
					commandId = CMD_QUIT;
					inputValid = hasNoArgument(words);
					break;
				case "done" :
					commandId = CMD_DONE;
					inputValid = hasNoArgument(words);
					break;
				case "roll" :
					commandId = CMD_ROLL;
					inputValid = hasNoArgument(words);
					break;
				case "buy" :
					commandId = CMD_BUY;
					inputValid = hasNoArgument(words);
					break;
				case "property" :
					commandId = CMD_PROPERTY;
					inputValid = hasNoArgument(words);
					break;
				case "balance" :
					commandId = CMD_BALANCE;
					inputValid = hasNoArgument(words);
					break;
				case "bankrupt" :
					commandId = CMD_BANKRUPT;
					inputValid = hasNoArgument(words);
					break;
				case "mortgage" :
					commandId = CMD_MORTGAGE;
					if(hasOneArgument(words) && board.isProperty(words[1])) 
					{ 
						inputProperty = board.getProperty(words[1]);
						inputValid = true;
					} 
					else
					{
						inputValid = false;
					}
					break;
				case "redeem" :
					commandId = CMD_REDEEM;
					if(hasOneArgument(words) && board.isProperty(words[1])) 
					{ 
						inputProperty = board.getProperty(words[1]);
						inputValid = true;
					} 
					else 
					{
						inputValid = false;
					}
					break;
				case "build" :
					commandId = CMD_BUILD;
					if(hasTwoArguments(words) && board.isSite(words[1]) && words[2].matches("[0-9]+")) 
					{ 
						inputProperty = board.getProperty(words[1]);
						inputNumber = Integer.parseInt(words[2]);
						inputValid = true;
					}
					else
					{
						inputValid = false;
					}
					break;
				case "demolish" :
					commandId = CMD_DEMOLISH;
					if(hasTwoArguments(words) && board.isSite(words[1]) && words[2].matches("[0-9]+"))
					{ 
						inputProperty = board.getProperty(words[1]);
						inputNumber = Integer.parseInt(words[2]);
						inputValid = true;
					} 
					else
					{
						inputValid = false;
					}
					break;					
				case "help" :
					commandId = CMD_HELP;
					inputValid = hasOneArgument(words);
					break;
				case "cheat" :
					commandId = CMD_CHEAT;
					if(hasOneArgument(words) && words[1].matches("[0-9]+")) 
					{
						inputNumber = Integer.parseInt(words[1]);
						inputValid = true;
					} 
					else 
					{
						inputValid = false;
					}
					break;
				default:
					inputValid = false;
				}
			if(!inputValid) 
			{
				displayError(ERR_SYNTAX);
			}
		} while(!inputValid);
		if(commandId == CMD_DONE) 
		{
			done = true;
		}
		else 
		{
			done = false;
		}		
		return;
	}
	
	/*--------------------CHANCE CARDS--------------------*/
	
	public void drawChance()
	{
		chanceId = (int)(Math.random()*17);
		
		switch(chanceId)
		{
		case 1:
			chanceId = CARD_CHANCE_1;
			infoPanel.addText(chance[CARD_CHANCE_1]);
			break;
		case 2:
			chanceId = CARD_CHANCE_2;
			infoPanel.addText(chance[CARD_CHANCE_2]);
			break;
		case 3:
			chanceId = CARD_CHANCE_3;
			infoPanel.addText(chance[CARD_CHANCE_3]);
			break;
		case 4:
			chanceId = CARD_CHANCE_4;
			infoPanel.addText(chance[CARD_CHANCE_4]);
			break;
		case 5:
			chanceId = CARD_CHANCE_5;
			infoPanel.addText(chance[CARD_CHANCE_5]);
			break;
		case 6:
			chanceId = CARD_CHANCE_6;
			infoPanel.addText(chance[CARD_CHANCE_6]);
			break;
		case 7:
			chanceId = CARD_CHANCE_7;
			infoPanel.addText(chance[CARD_CHANCE_7]);
			break;
		case 8:
			chanceId = CARD_CHANCE_8;
			infoPanel.addText(chance[CARD_CHANCE_8]);
			break;
		case 9:
			chanceId = CARD_CHANCE_9;
			infoPanel.addText(chance[CARD_CHANCE_9]);
			break;
		case 10:
			chanceId = CARD_CHANCE_10;
			infoPanel.addText(chance[CARD_CHANCE_10]);
			break;
		case 11:
			chanceId = CARD_CHANCE_11;
			infoPanel.addText(chance[CARD_CHANCE_11]);
			break;
		case 12:
			chanceId = CARD_CHANCE_12;
			infoPanel.addText(chance[CARD_CHANCE_12]);
			break;
		case 13:
			chanceId = CARD_CHANCE_13;
			infoPanel.addText(chance[CARD_CHANCE_13]);
			break;
		case 14:
			chanceId = CARD_CHANCE_14;
			infoPanel.addText(chance[CARD_CHANCE_14]);
			break;
		case 15:
			chanceId = CARD_CHANCE_15;
			infoPanel.addText(chance[CARD_CHANCE_15]);
			break;
		case 16:
			chanceId = CARD_CHANCE_16;
			infoPanel.addText(chance[CARD_CHANCE_16]);
			break;
		}
	}
	
	/*--------------------COMMUNITY CHEST CARDS--------------------*/
	
	public void drawCommunityChest()
	{
		communityId = (int)(Math.random()*17);
		
		switch(communityId)
		{
		case 1:
			chanceId = CARD_COMMUNITY_1;
			infoPanel.addText(communityChest[CARD_COMMUNITY_1]);
			break;
		case 2:
			chanceId = CARD_COMMUNITY_2;
			infoPanel.addText(communityChest[CARD_COMMUNITY_2]);
			break;
		case 3:
			chanceId = CARD_COMMUNITY_3;
			infoPanel.addText(communityChest[CARD_COMMUNITY_3]);
			break;
		case 4:
			chanceId = CARD_COMMUNITY_4;
			infoPanel.addText(communityChest[CARD_COMMUNITY_4]);
			break;
		case 5:
			chanceId = CARD_COMMUNITY_5;
			infoPanel.addText(communityChest[CARD_COMMUNITY_5]);
			break;
		case 6:
			chanceId = CARD_COMMUNITY_6;
			infoPanel.addText(communityChest[CARD_COMMUNITY_6]);
			break;
		case 7:
			chanceId = CARD_COMMUNITY_7;
			infoPanel.addText(communityChest[CARD_COMMUNITY_7]);
			break;
		case 8:
			chanceId = CARD_COMMUNITY_8;
			infoPanel.addText(communityChest[CARD_COMMUNITY_8]);
			break;
		case 9:
			chanceId = CARD_COMMUNITY_9;
			infoPanel.addText(communityChest[CARD_COMMUNITY_9]);
			break;
		case 10:
			chanceId = CARD_COMMUNITY_10;
			infoPanel.addText(communityChest[CARD_COMMUNITY_10]);
			break;
		case 11:
			chanceId = CARD_COMMUNITY_11;
			infoPanel.addText(communityChest[CARD_COMMUNITY_11]);
			break;
		case 12:
			chanceId = CARD_COMMUNITY_12;
			infoPanel.addText(communityChest[CARD_COMMUNITY_12]);
			break;
		case 13:
			chanceId = CARD_COMMUNITY_13;
			infoPanel.addText(communityChest[CARD_COMMUNITY_13]);
			break;
		case 14:
			chanceId = CARD_COMMUNITY_14;
			infoPanel.addText(communityChest[CARD_COMMUNITY_14]);
			break;
		case 15:
			chanceId = CARD_COMMUNITY_15;
			infoPanel.addText(communityChest[CARD_COMMUNITY_15]);
			break;
		case 16:
			chanceId = CARD_COMMUNITY_16;
			infoPanel.addText(communityChest[CARD_COMMUNITY_16]);
			break;
		}
	}
	
	/*--------------------GET METHODS--------------------*/

	public String getString() 
	{
		return string; 
	}

	public String getTokenName(int tokenId) 
	{
		return boardPanel.getTokenName(tokenId);
	}

	public int getCommandId() 
	{
		return commandId;
	}

	public boolean isDone()
	{
		return done;
	}
	
	public Property getInputProperty() 
	{
		return inputProperty;
	}
	
	public Player getInputPlayer() 
	{
		return inputPlayer;
	}
	
	public int getInputNumber() 
	{
		return inputNumber;
	}

	/*--------------------DISPLAY METHODS--------------------*/

	public void display() 
	{
		boardPanel.refresh();
		return;
	}

	public void displayString(String string) 
	{
		infoPanel.addText(string + "\n");
		return;
	}

	public void displayBankTransaction(Player player) 
	{
		if(player.getTransaction() >= 0) 
		{
			infoPanel.addText(player + " receives " + player.getTransaction() + CURRENCY + " from the bank.");
		} 
		else 
		{
			infoPanel.addText(player + " pays " + (-player.getTransaction()) + CURRENCY + " to the bank.");			
		}
		return;
	}

	public void displayTransaction(Player fromPlayer, Player toPlayer) 
	{
		infoPanel.addText(fromPlayer + " pays " + toPlayer.getTransaction() + CURRENCY + " to " + toPlayer);
		return;
	}

	public void displayDice(Player player, Dice dice) 
	{
		infoPanel.addText(player + " rolls " + dice + ".");
		return;
	}
	
	public void displayRollDoubles()
	{
		infoPanel.addText("You rolled doubles! Roll again!.");
		return;
	}

	public void displayRollDraw() 
	{
		infoPanel.addText("It was a tie.");
		return;
	}

	public void displayRollWinner(Player player) 
	{
		infoPanel.addText(player + " wins the roll.");
		return;
	}

	public void displayGameOver() 
	{
		infoPanel.addText("GAME OVER");
		return;
	}

	public void displayCommandHelp() 
	{
		infoPanel.addText("------------------------------------\n" 
				+ "This is the help menu! Here is a list of the valid commands that you may use during the game: \n"
				+ "• Roll: roll the dice \n"
				+ "• Done: end your turn \n"
				+ "• Property: view all the properties you own \n"
				+ "• Balance: view your current bank account balance \n"
				+ "• Build 'property name' 'number of units': build houses on your Monopolies \n"
				+ "• Demolish 'property name' 'number of units': sell houses when in debt \n"
				+ "• Mortgage 'property name': mortgage a property when in debt \n"
				+ "• Redeem 'property name': redeem your mortgaged properties when out of debt \n"
				+ "• Buy: purchase a property if it is unowned \n"
				+ "• Help: view the list of valid commands \n" 
				+ "• Quit: end the game early \n"
				+ "------------------------------------");
		return;
	}

	public void displayBalance(Player player) 
	{
		infoPanel.addText(player + "'s balance is " + player.getBalance() + CURRENCY);
		return;
	}

	public void displayError(int errorId) 
	{
		infoPanel.addText(errorMessages[errorId]);
		return;
	}

	public void displayPassedGo(Player player) 
	{
		infoPanel.addText(player + " passed Go.");
		return;
	}

	public void displayLatestProperty(Player player) 
	{
		infoPanel.addText(player + " bought " + player.getLatestProperty());
		return;
	}

	public void displayProperty(Player player) 
	{
		ArrayList<Property> propertyList = player.getProperties();
		if(propertyList.size() == 0) 
		{
			infoPanel.addText(player + " owns no property.");
		} 
		else
		{
			infoPanel.addText(player + " owns the following property...");
			for(Property p : propertyList) 
			{
				String mortgageStatus = "";
				if(p.isMortgaged()) 
				{
					mortgageStatus = ", is mortgaged";
				}
				if(p instanceof Site) 
				{
					Site site = (Site) p;
					String buildStatus = "";
					if(site.getNumBuildings() == 0)
					{
						buildStatus = "with no buildings";
					}
					else if(site.getNumBuildings() == 1) 
					{
						buildStatus = "with 1 house";
					} 
					else if(site.getNumBuildings() < 5) 
					{
						buildStatus = "with " + site.getNumBuildings() + " houses";
					} 
					else if(site.getNumBuildings() == 5)
					{
						buildStatus = "with a hotel";
					}
					infoPanel.addText(site + " (" + site.getColorGroup().getName() + "), rent " + site.getRent() + CURRENCY + ", " + buildStatus + mortgageStatus + ".");		
				} 
				else if(p instanceof Station) 
				{
					infoPanel.addText(p + ", rent " + p.getRent() + CURRENCY + mortgageStatus + ".");	
				} 
				else if(p instanceof Utility) 
				{
					infoPanel.addText(p + ", rent " + ((Utility) p).getRentMultiplier() + " times dice" + mortgageStatus + ".");
				}
			}
		}
	}

	public void displaySquare(Player player, Board board, Dice dice) 
	{
		Square square = board.getSquare(player.getPosition());
		infoPanel.addText(player + " lands on " + square.getName() + ".");
		if(square instanceof Property) 
		{
			Property property = (Property) square;
			if(property.isOwned()) 
			{
				infoPanel.addText("The property is owned by " + property.getOwner() + ". Rent is " + property.getRent() + CURRENCY + ".");				
			} 
			else 
			{
				infoPanel.addText("The property is not yet owned.");								
			}
		}
		return;
	}
	
	public void displayBuild(Player player, Site site, int numUnits) 
	{
		if(numUnits == 1)
		{
			infoPanel.addText(player + " builds 1 unit on " + site);			
		} 
		else
		{
			infoPanel.addText(player + " builds " + numUnits + " units on " + site);
		}
		return;
	}
	
	public void displayDemolish(Player player, Site site, int numUnits) 
	{
		if(numUnits == 1) 
		{
			infoPanel.addText(player + " demolishes 1 unit on " + site);			
		} 
		else
		{
			infoPanel.addText(player + " demolishes " + numUnits + " units on " + site);
		}
		return;
	}	
	
	public void displayBankrupt(Player player)
	{
		infoPanel.addText(player + " is bankrupt.");
		return;
	}
	
	public void displayMortgage(Player player, Property property)
	{
		infoPanel.addText(player + " mortgages " + property + " for " + property.getMortgageValue() + CURRENCY);
		return;				
	}
	
	public void displayMortgageRedemption(Player player, Property property)
	{
		infoPanel.addText(player + " redeems " + property + " for " + property.getMortgageRemptionPrice() + CURRENCY);
		return;
	}

	public void displayAssets(Player player) 
	{
		infoPanel.addText(player + " has assets of " + player.getAssets() + CURRENCY);
		return;
	}

	public void displayWinner(Player player) 
	{
		infoPanel.addText("The winner is " + player);
		return;
	}

	public void displayDraw(ArrayList<Player> players) 
	{
		infoPanel.addText("The following players tied the game: " + players);
		return;
	}
}