
public class Property extends Square 
{
	private static final float MORTGAGE_PREMIUM = 1.1f;
	
	private boolean isOwned;
	private int price;
	private Player owner;
	private String shortName;
	private boolean mortgaged;
	private int mortgageValue;
	
	public Property(String name, int price, String shortName, int mortgageValue) 
	{
		super(name);
		this.price = price;
		this.shortName = shortName;
		isOwned = false;
		owner = null;
		mortgaged = false;
		this.mortgageValue = mortgageValue;
		return;
	}
	
	/*--------------------PRICE--------------------*/
	
	public int getPrice() 
	{
		return price;
	}
	
	/*--------------------RENT--------------------*/
	
	public int getRent() //overloaded by subclasses
	{
		return 0;
	}
	
	/*--------------------OWNERSHIP--------------------*/
	
	public Player getOwner() 
	{
		return owner;
	}
	
	public boolean isOwned() 
	{
		return isOwned;
	}
	
	public void setOwner(Player inPlayer) 
	{
		owner = inPlayer;
		isOwned = true;
		return;
	}
	
	public void releaseOwnership() 
	{
		isOwned = false;
		owner = null;
		mortgaged = false;
		return;
	}
	
	/*--------------------OWNERSHIP--------------------*/
	
	public void setMortgaged() 
	{
		mortgaged = true;
		return;
	}
	
	public boolean isMortgaged() 
	{
		return mortgaged;
	}
	
	public void setNotMortgaged() 
	{
		mortgaged = false;
		return;
	}
	
	public int getMortgageValue() 
	{
		return mortgageValue;
	}
	
	public int getMortgageRemptionPrice() 
	{
		return (int) (((float) mortgageValue) * MORTGAGE_PREMIUM);
	}
	
	/*--------------------General--------------------*/
	
	public boolean equals(String string) 
	{
		return shortName.equals(string);
	}
	
	public String toString() 
	{
		return super.toString();
	}
}
