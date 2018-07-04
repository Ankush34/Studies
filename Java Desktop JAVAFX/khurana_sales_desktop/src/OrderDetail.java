
public class OrderDetail {

	public String sales_order_product_name;
	public String sales_order_invoice_number;
	
	public OrderDetail(String name, String number)
	{
		this.sales_order_product_name = name;
		this.sales_order_invoice_number = number;
		
	}
	public String getSales_order_invoice_number() {
		return sales_order_invoice_number;
	}
	public void setSales_order_invoice_number(String sales_order_invoice_number) {
		this.sales_order_invoice_number = sales_order_invoice_number;
	}
	public String getSales_order_product_name() {
		return sales_order_product_name;
	}
	public void setSales_order_product_name(String sales_order_product_name) {
		this.sales_order_product_name = sales_order_product_name;
	}
	
}
