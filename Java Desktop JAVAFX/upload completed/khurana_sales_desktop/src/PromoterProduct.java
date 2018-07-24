
public class PromoterProduct {
 public String product_name;
 public String product_count;
 public String date;
 public String product_price;
 public String sales_order_status;
 public String invoice_status;
 
 public PromoterProduct(String name, String count, String date,String product_price, String sales_order_status, String invoice_status)
 {
	 this.product_name = name;
	 this.product_count = count;
	 this.date  = date;
	 this.product_price = product_price;
	 this.sales_order_status = sales_order_status;
	 this.invoice_status = invoice_status;
 }
 	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_count() {
		return product_count;
	}
	public void setProduct_count(String product_count) {
		this.product_count = product_count;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}
	public String getSales_order_status() {
		return sales_order_status;
	}
	public void setSales_order_status(String sales_order_status) {
		this.sales_order_status = sales_order_status;
	}
	public String getInvoice_status() {
		return invoice_status;
	}
	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

}
