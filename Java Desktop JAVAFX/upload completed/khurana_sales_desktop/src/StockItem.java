
public class StockItem {
	public String product_id;
	public String date_of_insert;
	public String Brand;
	public String Name;
	public String Stock;
	public String Price_MRP;
	public String Price_MOP;
	public String Price_KS;
	public String links;
	public String Batch_numbers;
	public String Product_HSN;
	public String product_tax;

	public StockItem(String name, String stock, String mrp, String mop, String ksprice, String tax, String hsn)
	{
		this.Name = name;
		this.Stock = stock;
		this.Price_MRP = mrp;
		this.Price_MOP = mop;
		this.Price_KS = ksprice;
		this.product_tax = tax;
		this.Product_HSN = hsn;
	}

	public String getProduct_tax() {
		return product_tax;
	}

	public void setProduct_tax(String product_tax) {
		this.product_tax = product_tax;
	}

	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getDate_of_insert() {
		return date_of_insert;
	}
	public void setDate_of_insert(String date_of_insert) {
		this.date_of_insert = date_of_insert;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getStock() {
		return Stock;
	}
	public void setStock(String stock) {
		Stock = stock;
	}
	public String getPrice_MRP() {
		return Price_MRP;
	}
	public void setPrice_MRP(String price_MRP) {
		Price_MRP = price_MRP;
	}
	public String getPrice_MOP() {
		return Price_MOP;
	}
	public void setPrice_MOP(String price_MOP) {
		Price_MOP = price_MOP;
	}
	public String getPrice_KS() {
		return Price_KS;
	}
	public void setPrice_KS(String price_KS) {
		Price_KS = price_KS;
	}
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	public String getBatch_numbers() {
		return Batch_numbers;
	}
	public void setBatch_numbers(String batch_numbers) {
		Batch_numbers = batch_numbers;
	}
	public String getProduct_HSN() {
		return Product_HSN;
	}
	public void setProduct_HSN(String product_HSN) {
		Product_HSN = product_HSN;
	}
}
