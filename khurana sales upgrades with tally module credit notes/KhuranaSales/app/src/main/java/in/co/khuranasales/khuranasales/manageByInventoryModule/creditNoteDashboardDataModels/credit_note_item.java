package in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels;

import java.util.ArrayList;

public class credit_note_item {
	public String item_name;
	public String rate;
	public String item_amount;
	public String billed_quantity;
	public String actual_quantity;
	public ArrayList<credit_note_ledger> ledgers_included = new ArrayList<>();
	public ArrayList<credit_note_account_ledger> account_ledgers_included = new ArrayList<>();


	public ArrayList<credit_note_item_batch> batches =new ArrayList<>();

	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(String item_amount) {
		this.item_amount = item_amount;
	}
	public String getBilled_quantity() {
		return billed_quantity;
	}
	public void setBilled_quantity(String billed_quantity) {
		this.billed_quantity = billed_quantity;
	}
	public String getActual_quantity() {
		return actual_quantity;
	}
	public void setActual_quantity(String actual_quantity) {
		this.actual_quantity = actual_quantity;
	}
}
