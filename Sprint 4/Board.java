
public class Board 
{
	public static final int NUM_SQUARES = 40;
		
	private Square[] squares = new Square[NUM_SQUARES];
	private ColorGroup brown = new ColorGroup("brown");
	private ColorGroup lightBlue = new ColorGroup("light blue");
	private ColorGroup pink = new ColorGroup("pink");
	private ColorGroup orange = new ColorGroup("orange");
	private ColorGroup red = new ColorGroup("red");
	private ColorGroup yellow = new ColorGroup("yellow");
	private ColorGroup green = new ColorGroup("green");
	private ColorGroup darkBlue = new ColorGroup("dark blue");
	
	public Board(Dice dice) 
	{
		squares[0] = new Square("NG+");
		squares[1] = new Site("Undead Burg", "burg", 600, 300, new int[] {20,100,300,900,1600,2500}, brown, 500);
		squares[2] = new CommunityChest("Loot Chest");
		squares[3] = new Site("Undead Parish", "parish", 600, 300, new int[] {40,200,600,1800,3200,4500}, brown, 500);
		squares[4] = new Tax("You Died", 2000);
		squares[5] = new Station("Oolacile Sanctuary", "sanctuary", 2000, 1000, new int[] {250,500,1000,2000,2000,2000});
		squares[6] = new Site("Darkroot Garden", "garden", 1000, 500, new int[] {60,300,900,2700,4000,5500}, lightBlue, 500);
		squares[7] = new Chance("Orange Guidance Message");
		squares[8] = new Site("Darkroot Basin", "basin", 1000, 500, new int[] {60,300,900,2700,4000,5500}, lightBlue, 500);
		squares[9] = new Site("Depths", "depths", 1200, 600, new int[] {80,400,1000,3000,4500,6000}, lightBlue, 500);
		squares[10] = new Jail("Undead Asylum");
		squares[11] = new Site("Blighttown", "blighttown", 1400, 700, new int[] {100,500,1500,4500,6250,7500}, pink, 1000);
		squares[12] = new Utility("Bell Of Awakening", "bell", 1500, 750, new int[] {4,10}, dice);
		squares[13] = new Site("Valley of Drakes", "drakes", 1400, 700, new int[] {100,500,1500,4500,6250,7500}, pink, 1000);
		squares[14] = new Site("Quelaag's Domain", "domain", 1600, 800, new int[] {120,600,1800,5000,7000,9000}, pink, 1000);
		squares[15] = new Station("Royal Wood", "wood", 2000, 1000, new int[] {250,500,1000,2000,2000,2000});
		squares[16] = new Site("Demon Ruins", "demon", 1800, 900, new int[] {140,700,2000,5500,7500,9500}, orange, 1000);
		squares[17] = new CommunityChest("Loot Chest");
		squares[18] = new Site("Catacombs", "catacombs", 1800, 900, new int[] {140,700,2000,5500,7500,9500}, orange, 1000);
		squares[19] = new Site("Sens's Fortress", "fortress", 2000, 1000, new int[] {160,800,2200,6000,8000,10000}, orange, 1000);
		squares[20] = new Square("Snuggly's Nest");
		squares[21] = new Site("Anor Londo","londo", 2200, 1100, new int[] {180,900,2500,7000,8750,10500}, red, 1500);
		squares[22] = new Chance("Orange Guidance Message");
		squares[23] = new Site("Tomb of Giants", "giants", 2200, 1100, new int[] {180,900,2500,7000,8750,10500}, red, 1500);
		squares[24] = new Site("Lost Izalith", "izalith", 2400, 1200, new int[] {200,1000,3000,7500,9250,11000}, red, 1500);
		squares[25] = new Station("Oolacile Township", "township", 2000, 1000, new int[] {250,500,1000,2000,2000,2000});
		squares[26] = new Site("New Londo Ruins","ruins", 2600, 1300, new int[] {220,1100,3300,8000,9750,11500}, yellow, 1500);
		squares[27] = new Site("Duke's Archives", "archives", 2600, 1300, new int[] {220,1100,3300,8000,9750,11500}, yellow, 1500);
		squares[28] = new Utility("Bell of Awakening", "awakening", 1500, 750, new int[] {4,10}, dice);
		squares[29] = new Site("Crystal Cave", "cave", 2800, 1400, new int[] {220,1200,3600,8500,10250,12000}, yellow, 1500);
		squares[30] = new GoToJail("Go To Undead Asylum");
		squares[31] = new Site("Painted World of Ariamis", "ariamis", 3000, 1500, new int[] {260,1300,3900,9000,11000,12750}, green, 2000);
		squares[32] = new Site("Great Hollow", "hollow", 3000, 1500, new int[] {260,1300,3900,9000,11000,12750}, green, 2000);
		squares[33] = new CommunityChest("Loot Chest");
		squares[34] = new Site("Ash Lake", "lake", 3200, 1600, new int[] {280,1500,4500,10000,12000,14000}, green, 2000);
		squares[35] = new Station("Chasm Of The Abyss", "abyss", 2000, 1000, new int[] {250,500,1000,2000,2000,2000});
		squares[36] = new Chance("Orange Guidance Message");
		squares[37] = new Site("Firelink Shrine", "shrine", 3500, 1750, new int[] {350,1750,5000,11000,13000,15000}, darkBlue, 2000);
		squares[38] = new Tax("Request Absolution", 1000);
		squares[39] = new Site("King of the First Flame", "flame", 4000, 2000, new int[] {500,2000,6000,14000,17000,20000}, darkBlue, 2000);
		return;
	}
	
	public Square getSquare(int index) 
	{
		return squares[index];
	}
	
	public Property getProperty(int index) 
	{
		return (Property) squares[index];
	}
	
	public Property getProperty(String shortName) 
	{
		Property property = null;
		for(Square s : squares) 
		{
			if(s instanceof Property) 
			{
				Property p = (Property) s;
				if(p.equals(shortName))
				{
					property = p;
				}
			}
		}
		return property;
	}
	
	public boolean isProperty(int index) 
	{
		return squares[index] instanceof Property;
	}
	
	public boolean isProperty(String shortName)
	{
		boolean found = false;
		for(Square s :squares) 
		{
			if(s instanceof Property) 
			{
				Property p = (Property) s;
				if(p.equals(shortName))
				{	
					found = true;
				}
			}
		}
		return found;
	}
	
	public boolean isSite(String shortName)
	{
		return isProperty(shortName) && getProperty(shortName) instanceof Site;
	}

	public boolean isStation(String shortName) 
	{
		return isProperty(shortName) && getProperty(shortName) instanceof Station;
	}

	public boolean isUtility(String shortName) 
	{
		return isProperty(shortName) && getProperty(shortName) instanceof Utility;
	}
}
