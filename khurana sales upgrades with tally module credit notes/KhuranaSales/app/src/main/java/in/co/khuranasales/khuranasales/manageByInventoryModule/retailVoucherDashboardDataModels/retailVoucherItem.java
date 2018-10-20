package in.co.khuranasales.khuranasales.manageByInventoryModule.retailVoucherDashboardDataModels;

import java.util.ArrayList;

public class retailVoucherItem {
 
	public String name;
	public String rate;
	public String amount_of_purchase;
	public String actual_quantity;
	public String billed_quantity;
	public String inclusive_vat_rate;
	public ArrayList<retailItemBatch> batches = new ArrayList<>();	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAmount_of_purchase() {
		return amount_of_purchase;
	}
	public void setAmount_of_purchase(String amount_of_purchase) {
		this.amount_of_purchase = amount_of_purchase;
	}
	public String getActual_quantity() {
		return actual_quantity;
	}
	public void setActual_quantity(String actual_quantity) {
		this.actual_quantity = actual_quantity;
	}
	public String getBilled_quantity() {
		return billed_quantity;
	}
	public void setBilled_quantity(String billed_quantity) {
		this.billed_quantity = billed_quantity;
	}
	public String getInclusive_vat_rate() {
		return inclusive_vat_rate;
	}
	public void setInclusive_vat_rate(String inclusive_vat_rate) {
		this.inclusive_vat_rate = inclusive_vat_rate;
	}
	
}
