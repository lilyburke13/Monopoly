
public class Tax extends Square
{
	int taxPrice;
	
	public Tax(String name, int taxPrice) 
	{
		super(name);
		this.taxPrice = taxPrice;
		return;
	}
	
	public int getTaxPrice()
	{
		return taxPrice;
	}
}
