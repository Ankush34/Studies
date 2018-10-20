package in.co.khuranasales.khuranasales.manageByInventoryModule.purchaseVoucherDashboardDataModels;

import java.util.ArrayList;

public class purchaseVoucherProduct {
	public String product_name;
	public String product_ledger_name;
	public String discount_amount;
	public String item_price;
	public String total_amount_spent;
	public String total_purchase_quantity;
	public String billed_purchase_quantity;
	public ArrayList<purchaseVoucherItemBatch> purchase_voucher_item_batches = new ArrayList<>();
	
	public String getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(String discount_amount) {
		this.discount_amount = discount_amount;
	}
	public String getTotal_purchase_quantity() {
		return total_purchase_quantity;
	}
	public void setTotal_purchase_quantity(String total_purchase_quantity) {
		this.total_purchase_quantity = total_purchase_quantity;
	}
	public String getBilled_purchase_quantity() {
		return billed_purchase_quantity;
	}
	public void setBilled_purchase_quantity(String billed_purchase_quantity) {
		this.billed_purchase_quantity = billed_purchase_quantity;
	}
	
	public String getTotal_amount_spent() {
		return total_amount_spent;
	}
	public void setTotal_amount_spent(String total_amount_spent) {
		this.total_amount_spent = total_amount_spent;
	}
	public String getItem_price() {
		return item_price;
	}
	public void setItem_price(String item_price) {
		this.item_price = item_price;
	}
	public ArrayList<purchaseVoucherItemBatch> getPurchase_voucher_item_batches() {
		return purchase_voucher_item_batches;
	}
	public void setPurchase_voucher_item_batches(ArrayList<purchaseVoucherItemBatch> purchase_voucher_item_batches) {
		this.purchase_voucher_item_batches = purchase_voucher_item_batches;
	}
	
	
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_ledger_name() {
		return product_ledger_name;
	}
	public void setProduct_ledger_name(String product_ledger_name) {
		this.product_ledger_name = product_ledger_name;
	}
	
}
