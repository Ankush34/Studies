package in.co.khuranasales.khuranasales.manageByInventoryModule.purchaseVoucherDashboardDataModels;

import java.util.ArrayList;

public class purchaseVoucher {
	
	public String purchase_address_location;
	public String purchase_date;
	public String purchase_guid;
	public String purchase_state;
	public String purchase_country;
	public String purchase_party_gstin;
	public String purchase_party_name;
	public String purchase_voucher_type;
	public String purchase_voucher_number;
	public String purchase_party_ledger_name;
	
	public String company_name;
	public String company_tag;
	public String company_state;
	
	public String co_assignee_gst_number;
	public String purchase_place_of_supply;
	public String purchase_time_of_invoice;
	public String purchase_co_assignee_state_name;
	public String purchase_voucher_created_by;
	public String purchase_voucher_alter_id;
	public String purchase_voucher_master_id;
	public String purchase_voucher_key;
	public String purchase_voucher_narration;
	public String purchase_voucher_spent_amount;

	public ArrayList<purchaseVoucherLedger> purchase_voucher_ledgers = new ArrayList<purchaseVoucherLedger>();
	public ArrayList<purchaseVoucherProduct> purchase_voucher_items = new ArrayList<>();


	public String getPurchase_voucher_spent_amount() {
		return purchase_voucher_spent_amount;
	}

	public void setPurchase_voucher_spent_amount(String purchase_voucher_spent_amount) {
		this.purchase_voucher_spent_amount = purchase_voucher_spent_amount;
	}

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_tag() {
		return company_tag;
	}
	public void setCompany_tag(String company_tag) {
		this.company_tag = company_tag;
	}
	public String getCompany_state() {
		return company_state;
	}
	public void setCompany_state(String company_state) {
		this.company_state = company_state;
	}
	
	public String getPurchase_address_location() {
		return purchase_address_location;
	}
	public void setPurchase_address_location(String purchase_address_location) {
		this.purchase_address_location = purchase_address_location;
	}
	public String getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
	}
	public String getPurchase_guid() {
		return purchase_guid;
	}
	public void setPurchase_guid(String purchase_guid) {
		this.purchase_guid = purchase_guid;
	}
	public String getPurchase_state() {
		return purchase_state;
	}
	public void setPurchase_state(String purchase_state) {
		this.purchase_state = purchase_state;
	}
	public String getPurchase_country() {
		return purchase_country;
	}
	public void setPurchase_country(String purchase_country) {
		this.purchase_country = purchase_country;
	}
	public String getPurchase_party_gstin() {
		return purchase_party_gstin;
	}
	public void setPurchase_party_gstin(String purchase_party_gstin) {
		this.purchase_party_gstin = purchase_party_gstin;
	}
	public String getPurchase_party_name() {
		return purchase_party_name;
	}
	public void setPurchase_party_name(String purchase_party_name) {
		this.purchase_party_name = purchase_party_name;
	}
	public String getPurchase_voucher_type() {
		return purchase_voucher_type;
	}
	public void setPurchase_voucher_type(String purchase_voucher_type) {
		this.purchase_voucher_type = purchase_voucher_type;
	}
	public String getPurchase_voucher_number() {
		return purchase_voucher_number;
	}
	public void setPurchase_voucher_number(String purchase_voucher_number) {
		this.purchase_voucher_number = purchase_voucher_number;
	}
	public String getPurchase_party_ledger_name() {
		return purchase_party_ledger_name;
	}
	public void setPurchase_party_ledger_name(String purchase_party_ledger_name) {
		this.purchase_party_ledger_name = purchase_party_ledger_name;
	}
	public String getCo_assignee_gst_number() {
		return co_assignee_gst_number;
	}
	public void setCo_assignee_gst_number(String co_assignee_gst_number) {
		this.co_assignee_gst_number = co_assignee_gst_number;
	}
	public String getPurchase_place_of_supply() {
		return purchase_place_of_supply;
	}
	public void setPurchase_place_of_supply(String purchase_place_of_supply) {
		this.purchase_place_of_supply = purchase_place_of_supply;
	}
	public String getPurchase_time_of_invoice() {
		return purchase_time_of_invoice;
	}
	public void setPurchase_time_of_invoice(String purchase_time_of_invoice) {
		this.purchase_time_of_invoice = purchase_time_of_invoice;
	}
	public String getPurchase_co_assignee_state_name() {
		return purchase_co_assignee_state_name;
	}
	public void setPurchase_co_assignee_state_name(String purchase_co_assignee_state_name) {
		this.purchase_co_assignee_state_name = purchase_co_assignee_state_name;
	}
	public String getPurchase_voucher_created_by() {
		return purchase_voucher_created_by;
	}
	public void setPurchase_voucher_created_by(String purchase_voucher_created_by) {
		this.purchase_voucher_created_by = purchase_voucher_created_by;
	}
	public String getPurchase_voucher_alter_id() {
		return purchase_voucher_alter_id;
	}
	public void setPurchase_voucher_alter_id(String purchase_voucher_alter_id) {
		this.purchase_voucher_alter_id = purchase_voucher_alter_id;
	}
	public String getPurchase_voucher_master_id() {
		return purchase_voucher_master_id;
	}
	public void setPurchase_voucher_master_id(String purchase_voucher_master_id) {
		this.purchase_voucher_master_id = purchase_voucher_master_id;
	}
	public String getPurchase_voucher_key() {
		return purchase_voucher_key;
	}
	public void setPurchase_voucher_key(String purchase_voucher_key) {
		this.purchase_voucher_key = purchase_voucher_key;
	}
	public String getPurchase_voucher_narration() {
		return purchase_voucher_narration;
	}
	public void setPurchase_voucher_narration(String purchase_voucher_narration) {
		this.purchase_voucher_narration = purchase_voucher_narration;
	}
	

}
