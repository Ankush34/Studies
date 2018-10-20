package in.co.khuranasales.khuranasales.manageByInventoryModule.journalVoucherDashboardDataModels;

import java.util.ArrayList;

public class journalVoucherItem {
	public String item_name;
	public String actual_quantity;
	public String billed_quantity;
	public ArrayList<journalVoucherItemBatch> batches = new ArrayList<>();
	
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
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
	public ArrayList<journalVoucherItemBatch> getBatches() {
		return batches;
	}
	public void setBatches(ArrayList<journalVoucherItemBatch> batches) {
		this.batches = batches;
	}
}
