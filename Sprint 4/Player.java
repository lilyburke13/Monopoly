import java.util.ArrayList;

/**
 * @author Lilah Koudelka
 * @author Elizabeth Burke
 * @version April 10, 2017 Sprint 4
 */
public class Player 
{
	private String name;
	private int position;
	private int balance;
	private int amount;
	private String tokenName;
	private int tokenID;
	private boolean passedGo;
	private ArrayList<Property> properties = new ArrayList<Property>();
	
	public Player(String name, String tokenName, int tokenID) 
	{
		this.name = name;
		this.tokenName = tokenName;
		this.tokenID = tokenID;
		position = 0;
		balance = 0;
		passedGo = false;
		return;
	}
	
	public Player(Player player)  // copy constructor
	{  
		this(player.getName(), player.getTokenName(), player.getTokenID());
	}

	/*--------------------PLAYER DATA--------------------*/
	
	public String getName() 
	{
		return name;
	}
	
	public String getTokenName()
	{
		return tokenName;
	}
	
	public int getTokenID() 
	{
		return tokenID;
	}
	
	/*--------------------TOKEN POSITION--------------------*/
	
	public int getPosition()
	{
		return position;
	}
	
	public void move(int squares) 
	{
		position = position + squares;
		
		if(position >= Board.NUM_SQUARES) 
		{
			position = position - Board.NUM_SQUARES;
			passedGo = true;
		} 
		else 
		{
			passedGo = false;
		}
		if(position < 0) 
		{
			position = position + Board.NUM_SQUARES;
		} 
		return;
	}
	
	public void moveToSquare(int square)
	{
		position = square;
	}
	
	public boolean passedGo() 
	{
		return passedGo;
	}
	
	/*--------------------MONEY HANDLING--------------------*/

	public void doTransaction(int amount) 
	{
		balance = balance + amount;
		this.amount = amount;
		return;
	}
	
	public int getTransaction() 
	{
		return amount;
	}
	
	public int getBalance() 
	{
		return balance;
	}
	
	public int getAssets() 
	{
		int assets = balance;
		for(Property property: properties) 
		{
			assets = assets + property.getPrice();
		}
		return assets;
	}
	
	/*--------------------PROPERTY HANDLING--------------------*/
	
	public void addProperty(Property property)
	{
		property.setOwner(this);
		properties.add(property);
		return;
	}
	
	public Property getLatestProperty() 
	{
		return properties.get(properties.size()-1);
	}
	
	public ArrayList<Property> getProperties() 
	{
		return properties;
	}
	
	public boolean isGroupOwner(Site site)
	{
		boolean ownsAll = true;
		ColorGroup colourGroup = site.getColorGroup();
		for(Site s : colourGroup.getMembers()) 
		{
			if(!s.isOwned() || (s.isOwned() && s.getOwner() != this))
			{
				ownsAll = false;
			}
		}
		return ownsAll;
	}
	
	public int getNumStationsOwned() 
	{
		int numOwned = 0;
		for(Property p : properties) 
		{
			if(p instanceof Station) 
			{
				numOwned++;
			}
		}
		return numOwned;
	}
	
	public int getNumUtilitiesOwned() 
	{
		int numOwned = 0;
		for(Property p : properties) 
		{
			if(p instanceof Utility) 
			{
				numOwned++;
			}
		}
		return numOwned;
	}
	
	/*--------------------GENERAL--------------------*/
	
	public boolean equals(String name) 
	{
		return this.name.toLowerCase() == name;
	}
	
	public String toString() 
	{
		return name + " (" + tokenName + ")";
	}
}
