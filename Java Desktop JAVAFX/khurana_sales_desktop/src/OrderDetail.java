
public class OrderDetail {

	public String sales_order_product_name;
	public String sales_order_invoice_number;
	public String sales_order_product_count;
	public String sales_order_price;
	public String sales_order_end_user_name;
	public String sales_order_end_user_phone;
	public String batches_selected;
	public String promoter_name;
	public String cash_payment_amount;
	public String paytm_payment;
	public String finance_payment;
	public String card_payment;
	public String cheque_payment;
	
	public OrderDetail(String name, String number, String product_count, 
			String price, String end_user_name, String end_user_phone, 
			String batches_selected, String promoter_name, String cash_payment_amount, 
			String paytm_payment, String finance_payment, String card_payment, String cheque_payment)
	{
		this.sales_order_product_name = name;
		this.sales_order_invoice_number = number;
		this.sales_order_product_count = product_count;
		this.sales_order_price = price;
		this.sales_order_end_user_name = end_user_name;
		this.sales_order_end_user_phone = end_user_phone;
		this.batches_selected = batches_selected;
		this.promoter_name = promoter_name;
		this.cash_payment_amount = cash_payment_amount;
		this.paytm_payment = paytm_payment;
		this.finance_payment = finance_payment;
		this.card_payment  = card_payment;
		this.cheque_payment = cheque_payment;
	}
	
	public String getSales_order_product_count() {
		return sales_order_product_count;
	}
	public void setSales_order_product_count(String sales_order_product_count) {
		this.sales_order_product_count = sales_order_product_count;
	}
	public String getSales_order_price() {
		return sales_order_price;
	}
	public void setSales_order_price(String sales_order_price) {
		this.sales_order_price = sales_order_price;
	}
	public String getSales_order_end_user_name() {
		return sales_order_end_user_name;
	}
	public void setSales_order_end_user_name(String sales_order_end_user_name) {
		this.sales_order_end_user_name = sales_order_end_user_name;
	}
	public String getSales_order_end_user_phone() {
		return sales_order_end_user_phone;
	}
	public void setSales_order_end_user_phone(String sales_order_end_user_phone) {
		this.sales_order_end_user_phone = sales_order_end_user_phone;
	}
	public String getBatches_selected() {
		return batches_selected;
	}
	public void setBatches_selected(String batches_selected) {
		this.batches_selected = batches_selected;
	}
	public String getPromoter_name() {
		return promoter_name;
	}
	public void setPromoter_name(String promoter_name) {
		this.promoter_name = promoter_name;
	}
	public String getCash_payment_amount() {
		return cash_payment_amount;
	}
	public void setCash_payment_amount(String cash_payment_amount) {
		this.cash_payment_amount = cash_payment_amount;
	}
	public String getPaytm_payment() {
		return paytm_payment;
	}
	public void setPaytm_payment(String paytm_payment) {
		this.paytm_payment = paytm_payment;
	}
	public String getFinance_payment() {
		return finance_payment;
	}
	public void setFinance_payment(String finance_payment) {
		this.finance_payment = finance_payment;
	}
	public String getCard_payment() {
		return card_payment;
	}
	public void setCard_payment(String card_payment) {
		this.card_payment = card_payment;
	}
	public String getCheque_payment() {
		return cheque_payment;
	}
	public void setCheque_payment(String cheque_payment) {
		this.cheque_payment = cheque_payment;
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
