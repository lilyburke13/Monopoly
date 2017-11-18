
public class Site extends Property 
{	
	private static int MAX_NUM_UNITS = 5;

	private int[] rentTable;
	private ColorGroup colorGroup;
	private int numBuildings;
	private int buildPrice;
	
	public Site(String name, String shortName, int price, int mortgageValue, int[] rentTable, ColorGroup colorGroup, int buildPrice) 
	{
		super(name, price, shortName, mortgageValue);
		this.rentTable = rentTable;
		this.colorGroup = colorGroup;
		this.buildPrice = buildPrice;
		numBuildings = 0;
		colorGroup.addMember(this);
		return;
	}
	
// METHODS DEALING WITH BUILDING UNITS (HOUSES AND HOTELS)
	
	public boolean canBuild(int numToBuild) 
	{
		 return (numBuildings+numToBuild) <= MAX_NUM_UNITS;
	}
	
	public void build(int numToBuild) 
	{
		if(canBuild(numToBuild)) 
		{
			numBuildings = numBuildings + numToBuild;
		}
		return;
	}
	
	public boolean canDemolish(int numToDemolish) 
	{
		return (numBuildings-numToDemolish) >= 0;
	}
	
	public void demolish(int numToDemolish) 
	{
		if(canDemolish(numToDemolish)) 
		{
			numBuildings = numBuildings - numToDemolish;
		}
	}
	
	public void demolishAll()
	{
		numBuildings = 0;
		return;
	}
	
	public int getNumBuildings()
	{
		return numBuildings;
	}
	
	public int getBuildingPrice()
	{
		return buildPrice;
	}
	
	public boolean hasBuildings()
	{
		return numBuildings > 0;
	}

// METHODS DEALING WITH COLOUR GROUPS
	
	public ColorGroup getColorGroup() 
	{
		return colorGroup;
	}
	
// METHODS DEALING WITH RENT
		
	public int getRent()
	{
		int rent;
		
		if(numBuildings == 0 && super.getOwner().isGroupOwner(this)) 
		{
			rent = rentTable[0];
		} 
		else if(numBuildings == 0 && super.getOwner().isGroupOwner(this)) 
		{
			rent = 2*rentTable[0]; 
		} 
		else 
		{
			rent = rentTable[numBuildings];
		}
		return rent;
	}
	
// COMMON JAVA METHODS
	
	public String toString() 
	{
		return super.toString();
	}
}
