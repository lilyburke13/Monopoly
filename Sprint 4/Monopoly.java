import java.util.ArrayList;

/**
 * This is the main class that creates the GUI version of Monopoly.
 * 
 * @author Lilah Koudelka
 * @author Elizabeth Burke
 * @version April 10, 2017 Sprint 4
 */
public class Monopoly 
{
	private static final int START_MONEY = 15000;
	private static final int GO_MONEY = 2000;

	private Players players = new Players();
	private Player currPlayer;
	private Dice dice = new Dice();
	private Board board = new Board(dice);
	private MonopolyGUI gui = new MonopolyGUI(players, board);
	private boolean gameOver = false;
	private boolean onlyOneNotBankrupt = false;
	private boolean turnFinished;
	private boolean rollDone;
	private boolean inJail = false;
	private boolean sentToJail = false; //chance or cc go to jail card
	private boolean goToJail = false; //go to jail square
	private boolean threeDoubles = false; //rolled 3 doubles in a row

	Monopoly() 
	{
		gui.display();
		return;
	}

	/*--------------------SET UP GAME FOR PLAYERS--------------------*/

	/**
	 * Gets each of the player's names.
	 */
	public void inputNames() 
	{
		int playerId = 0;
		do 
		{
			gui.inputName(playerId);
			if(!gui.isDone()) 
			{
				boolean duplicate = false;
				for(Player p : players.get()) 
				{
					if(gui.getString().toLowerCase().equals(p.getName().toLowerCase())) 
					{
						duplicate = true;
					}
				}
				if(!duplicate) 
				{
					players.add(new Player(gui.getString(), gui.getTokenName(playerId),playerId));
					playerId++;
				} 
				else
				{
					gui.displayError(MonopolyGUI.ERR_DUPLICATE);
				}
			}
		} while(!gui.isDone() && players.canAddPlayer());

		return;
	}

	/**
	 * Allocates the initial amount of money to each player.
	 */
	public void giveStartMoney()
	{
		for(Player p : players.get()) 
		{
			p.doTransaction(START_MONEY);
			gui.displayBankTransaction(p);
		}
		return;
	}

	/**
	 * Decides who starts the game and then continues in clockwise rotation.
	 */
	public void decideStarter()
	{
		Players inPlayers = new Players(players), selectedPlayers = new Players();
		boolean tie = false;

		do
		{
			int highestTotal = 0;
			for(Player p : inPlayers.get())
			{
				dice.roll();
				gui.displayDice(p, dice);
				if(dice.getTotal() > highestTotal) 
				{
					tie = false;
					highestTotal = dice.getTotal();
					selectedPlayers.clear();
					selectedPlayers.add(p);
				} 
				else if(dice.getTotal() == highestTotal)
				{
					tie = true;
					selectedPlayers.add(p);
				}
			}
			if(tie) 
			{
				gui.displayRollDraw();
				inPlayers = new Players(selectedPlayers);
				selectedPlayers.clear();
			}
		} while(tie);

		currPlayer = selectedPlayers.get(0);
		gui.displayRollWinner(currPlayer);
		gui.display();

		return;
	}

	/*--------------------COMMANDS AND INPUT CASES--------------------*/

	private void processRoll()
	{
		int doublesCount = 0;
		int rollCount = 0;
		
		if(!rollDone) 
		{
			if(currPlayer.getBalance() >= 0) 
			{
				dice.roll();
				gui.displayDice(currPlayer, dice);
				rollCount += 1;

				//if not "just visiting" jail (player is in jail)
				if(board.getSquare(currPlayer.getPosition()) instanceof Jail &&
						(sentToJail || goToJail || threeDoubles))
				{
					inJail = true;

					if(dice.isDouble())
					{
						inJail = false;
					}
					else if(rollCount > 2)
					{
						inJail = false;
					}
					//else if player uses get out of jail free card
					//else if player pays bail
					else
					{
						rollDone = true;
					}

				}
				if(!inJail)
				{
					currPlayer.move(dice.getTotal());
					gui.display();
					if(currPlayer.passedGo()) 
					{
						currPlayer.doTransaction(+GO_MONEY);
						gui.displayPassedGo(currPlayer);
						gui.displayBankTransaction(currPlayer);
					}
					gui.displaySquare(currPlayer, board, dice);
					if(board.getSquare(currPlayer.getPosition()) instanceof Property && 
							((Property) board.getSquare(currPlayer.getPosition())).isOwned() &&
							!((Property) board.getSquare(currPlayer.getPosition())).getOwner().equals(currPlayer) &&
							!((Property) board.getSquare(currPlayer.getPosition())).isMortgaged()) 
					{
						//automatically deducts rent if property is already owned and not mortgaged
						Property property = (Property) board.getSquare(currPlayer.getPosition());
						int rent = property.getRent();
						Player owner = property.getOwner();
						currPlayer.doTransaction(-rent);
						owner.doTransaction(+rent);
						gui.displayTransaction(currPlayer, owner);
					} 

					//player rolls doubles or not
					if(!dice.isDouble()) 
					{
						rollDone = true;
					}
					else if(dice.isDouble())
					{
						rollDone = false;
						gui.displayRollDoubles();
						doublesCount++;

						if(doublesCount > 2)
						{
							currPlayer.moveToSquare(10);
							threeDoubles = true;
						}
					}

					//go to jail square
					if(board.getSquare(currPlayer.getPosition()) instanceof GoToJail)
					{
						currPlayer.moveToSquare(10);
						goToJail = true;
					}

					//chance and community chest squares
					if(board.getSquare(currPlayer.getPosition()) instanceof Chance)
					{
						gui.drawChance();
					}
					if(board.getSquare(currPlayer.getPosition()) instanceof CommunityChest)
					{
						gui.drawCommunityChest();
					}

					//automatically deducts money upon landing tax squares
					if(board.getSquare(currPlayer.getPosition()) instanceof Tax)
					{
						Tax tax = (Tax) board.getSquare(currPlayer.getPosition());
						int taxPrice = tax.getTaxPrice();
						currPlayer.doTransaction(-taxPrice);
						gui.displayBankTransaction(currPlayer);
					}
				}
				else
				{
					gui.displayError(MonopolyGUI.ERR_STUCK_IN_JAIL);
				}
			}	
			else
			{
				gui.displayError(MonopolyGUI.ERR_NEGATIVE_BALANCE);
			}
		} 
		else
		{
			gui.displayError(MonopolyGUI.ERR_DOUBLE_ROLL);
		}
		return;
	}

	private void processBuy() 
	{
		if(board.getSquare(currPlayer.getPosition()) instanceof Property) 
		{
			Property property = (Property) board.getSquare(currPlayer.getPosition());
			if(!property.isOwned()) 
			{
				if(currPlayer.getBalance() >= property.getPrice()) 
				{				
					currPlayer.doTransaction(-property.getPrice());
					gui.displayBankTransaction(currPlayer);
					currPlayer.addProperty(property);
					gui.displayLatestProperty(currPlayer);
				} 
				else 
				{
					gui.displayError(MonopolyGUI.ERR_INSUFFICIENT_FUNDS);
				}
			} 
			else 
			{
				gui.displayError(MonopolyGUI.ERR_IS_OWNED);
			}
		} 
		else
		{
			gui.displayError(MonopolyGUI.ERR_NOT_A_PROPERTY);
		}
		return;
	}

	private void processBuild()
	{
		Property property = gui.getInputProperty();
		if(property.isOwned() && property.getOwner().equals(currPlayer)) 
		{
			if(property instanceof Site) 
			{
				Site site = (Site) property;
				if(currPlayer.isGroupOwner(site)) 
				{
					if(!site.isMortgaged()) 
					{
						int numBuildings = gui.getInputNumber();
						if(numBuildings > 0) 
						{
							if(site.canBuild(numBuildings)) 
							{
								int debit = numBuildings*site.getBuildingPrice();
								if(currPlayer.getBalance()>debit) 
								{
									site.build(numBuildings);
									currPlayer.doTransaction(-debit);
									gui.displayBuild(currPlayer,site,numBuildings);
								} 
								else 
								{
									gui.displayError(MonopolyGUI.ERR_INSUFFICIENT_FUNDS);
								}
							} 
							else 
							{
								gui.displayError(MonopolyGUI.ERR_TOO_MANY_HOUSES);
							}
						} 
						else 
						{
							gui.displayError(MonopolyGUI.ERR_TOO_FEW_HOUSES);
						}
					} 
					/*else 
					{
						gui.displayError(MonopolyGUI.ERR_IS_MORTGAGED);
					}*/
				} 
				else 
				{
					gui.displayError(MonopolyGUI.ERR_NO_MONOPOLY);
				}
			}
			else 
			{
				gui.displayError(MonopolyGUI.ERR_NOT_A_SITE);
			}
		} 
		else 
		{
			gui.displayError(MonopolyGUI.ERR_NOT_YOURS);
		}
		return;
	}

	private void processDemolish() 
	{
		Property property = gui.getInputProperty();
		if(property.isOwned() && property.getOwner().equals(currPlayer))
		{
			if(property instanceof Site) 
			{
				Site site = (Site) property;
				int numBuildings = gui.getInputNumber();
				if(numBuildings > 0) 
				{
					if(site.canDemolish(numBuildings))
					{
						site.demolish(numBuildings);
						int credit = numBuildings * site.getBuildingPrice()/2;
						currPlayer.doTransaction(+credit);
						gui.displayDemolish(currPlayer,site,numBuildings);
					}
					else 
					{
						gui.displayError(MonopolyGUI.ERR_NOT_ENOUGH_HOUSES);
					}
				} 
				else
				{
					gui.displayError(MonopolyGUI.ERR_NO_HOUSES);
				}
			} 
			else
			{
				gui.displayError(MonopolyGUI.ERR_NOT_A_SITE);
			}
		} 
		else
		{
			gui.displayError(MonopolyGUI.ERR_NOT_YOURS);
		}
		return;		
	}

	public void processCheat()
	{
		switch(gui.getInputNumber()) 
		{
		case 1 :       // acquire color group
			Property property = board.getProperty("burg");
			currPlayer.addProperty(property);		
			property = board.getProperty("parish");
			currPlayer.addProperty(property);
			break;
		case 2 :	   // make zero balance
			currPlayer.doTransaction(-currPlayer.getBalance());
			break;
		}
		return;
	}

	public void processBankrupt()
	{
		gui.displayBankrupt(currPlayer);
		Player tempPlayer = players.getNextPlayer(currPlayer);
		players.remove(currPlayer);
		currPlayer = tempPlayer;
		if(players.numPlayers() == 1)
		{
			gameOver = true;
			onlyOneNotBankrupt = true;
		}
		gui.display();
		return;
	}

	public void processMortgage() 
	{
		Property property = gui.getInputProperty();
		if(property.isOwned() && property.getOwner().equals(currPlayer)) 
		{
			if((property instanceof Site) && !((Site) property).hasBuildings() || (property instanceof Station) || (property instanceof Utility)) 
			{
				if(!property.isMortgaged()) 
				{
					property.setMortgaged();
					currPlayer.doTransaction(+property.getMortgageValue());
					gui.displayMortgage(currPlayer,property);
				}
			} 
			else 
			{
				gui.displayError(MonopolyGUI.ERR_HAS_BUILDINGS);
			}
		}
		else 
		{
			gui.displayError(MonopolyGUI.ERR_NOT_YOURS);
		}
		return;		
	}

	public void processRedeem()
	{
		Property property = gui.getInputProperty();
		if(property.isOwned() && property.getOwner().equals(currPlayer)) 
		{
			if(property.isMortgaged()) 
			{
				int price = property.getMortgageRemptionPrice();
				if(currPlayer.getBalance() >= price) 
				{
					property.setNotMortgaged();
					currPlayer.doTransaction(-price);
					gui.displayMortgageRedemption(currPlayer,property);
				} 
				else 
				{
					gui.displayError(MonopolyGUI.ERR_INSUFFICIENT_FUNDS);
				}
			} 
			else 
			{
				gui.displayError(MonopolyGUI.ERR_IS_NOT_MORTGAGED);
			}
		}
		else 
		{
			gui.displayError(MonopolyGUI.ERR_NOT_YOURS);
		}
		return;			
	}

	private void processDone() 
	{
		if(rollDone) 
		{
			if(currPlayer.getBalance() >= 0) 
			{
				turnFinished = true;
			} 
			else
			{
				gui.displayError(MonopolyGUI.ERR_NEGATIVE_BALANCE);
			}
		} 
		else 
		{
			gui.displayError(MonopolyGUI.ERR_NO_ROLL);
		}
		return;
	}

	/*--------------------ESTABLISH PLAYERS' TURNS--------------------*/

	public void processTurn() 
	{
		turnFinished = false;
		rollDone = false;
		do 
		{
			gui.inputCommand(currPlayer);
			switch(gui.getCommandId()) 
			{
			case MonopolyGUI.CMD_ROLL :
				processRoll();
				break;
				/*				case MonopolyGUI.CMD_PAY_RENT :
					processPayRent();
					break;*/
			case MonopolyGUI.CMD_BUY :
				processBuy();
				break;
			case MonopolyGUI.CMD_BALANCE :
				gui.displayBalance(currPlayer);
				break;
			case MonopolyGUI.CMD_PROPERTY :
				gui.displayProperty(currPlayer);
				break;
			case MonopolyGUI.CMD_BANKRUPT :
				processBankrupt();
				turnFinished = true;
				break;
			case MonopolyGUI.CMD_BUILD :
				processBuild();
				break;
			case MonopolyGUI.CMD_DEMOLISH :
				processDemolish();
				break;
			case MonopolyGUI.CMD_REDEEM :
				processRedeem();
				break;
			case MonopolyGUI.CMD_MORTGAGE :
				processMortgage();
				break;
			case MonopolyGUI.CMD_CHEAT :
				processCheat();
				break;
			case MonopolyGUI.CMD_HELP :
				gui.displayCommandHelp();
				break;
			case MonopolyGUI.CMD_DONE :
				processDone();
				break;
			case MonopolyGUI.CMD_QUIT : 
				turnFinished = true;
				gameOver = true;
				break;
			}
		} while(!turnFinished);

		return;
	}

	public void nextPlayer() 
	{
		currPlayer = players.getNextPlayer(currPlayer);
		return;
	}

	/*--------------------DETERMINE OUTCOME OF GAME--------------------*/

	public void decideWinner() 
	{
		if(onlyOneNotBankrupt) 
		{
			gui.displayWinner(currPlayer);			
		}
		else 
		{
			ArrayList<Player> playersWithMostAssets = new ArrayList<Player>();
			int mostAssets = players.get(0).getAssets();
			for(Player player : players.get()) 
			{
				gui.displayAssets(player);
				if(player.getAssets() > mostAssets) 
				{
					playersWithMostAssets.clear(); 
					playersWithMostAssets.add(player);
				} 
				else if(player.getAssets() == mostAssets)
				{
					playersWithMostAssets.add(player);
				}
			}
			if(playersWithMostAssets.size() == 1) 
			{
				gui.displayWinner(playersWithMostAssets.get(0));
			} 
			else 
			{
				gui.displayDraw(playersWithMostAssets);
			}
		}
		return;
	}

	public void displayGameOver() 
	{
		gui.displayGameOver ();
		return;
	}

	public boolean isGameOver() 
	{
		return gameOver;
	}
}