
/**  
 * This program simulates a game of Monopoly.
 * 
 * @author Lilah Koudelka, 
 * @author Elizabeth Burke
 * @version March 27, 2017 Sprint 3
 */
public class MonopolyGame
{
	public static void main(String args[]) 
	{	
		Monopoly monopoly = new Monopoly();	

		monopoly.inputNames();
		monopoly.giveStartMoney();
		monopoly.decideStarter();

		do 
		{
			monopoly.processTurn();
			if(!monopoly.isGameOver()) 
			{
				monopoly.nextPlayer();
			}
		} while(!monopoly.isGameOver());

		monopoly.decideWinner();
		monopoly.displayGameOver();

		return;
	}
}
