import java.util.ArrayList;

public class ProductInfoGoDown {

	public String name;
	public ArrayList<String> stock_batches = new ArrayList<String>();

	public ProductInfoGoDown(String name, ArrayList<String> batches)
	{
		this.name = name;
		this.stock_batches.addAll(batches);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getStock_batches() {
		return stock_batches;
	}
	public void setStock_batches(ArrayList<String> stock_batches) {
		this.stock_batches = stock_batches;
	}
	public String get(int finalIdx) {
		// TODO Auto-generated method stub
		return stock_batches.get(finalIdx);
	}}
