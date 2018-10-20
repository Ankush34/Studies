package in.co.khuranasales.khuranasales.manageByInventoryModule.retailVoucherDashboardDataModels;

import java.util.ArrayList;

public class retailVoucher {
	public String voucher_address;
	public String voucher_date;
	public String voucher_guid;
	public String voucher_state;
	public String voucher_party_name;
	public String voucher_type;
	public String voucher_number;
	public String voucher_party_ledger_name;
	public String voucher_place_of_supply;
	public String voucher_date_of_invoice;
	public String voucher_alter_id;
	public String voucher_master_id;
	public String voucher_key;
	public String voucher_created_by;
	public String total_outstanding;

	public ArrayList<retailVoucherLedger> retail_voucher_ledgers = new ArrayList<>();
	public ArrayList<retailPaymentLedger> retail_payment_ledgers = new ArrayList<>();
	public ArrayList<retailVoucherItem> retail_voucher_items = new ArrayList<>();
	
	public String company_name;
	public String company_state;
	public String company_tag;


	public String get_total_outstanding() {
		return total_outstanding;
	}

	public void set_total_outstanding(String set_total_outstanding) {
		this.total_outstanding = set_total_outstanding;
	}

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_state() {
		return company_state;
	}
	
	public void setCompany_state(String company_state) {
		this.company_state = company_state;
	}
	public String getCompany_tag() {
		return company_tag;
	}
	public void setCompany_tag(String company_tag) {
		this.company_tag = company_tag;
	}
	public String getVoucher_created_by() {
		return voucher_created_by;
	}
	public void setVoucher_created_by(String voucher_created_by) {
		this.voucher_created_by = voucher_created_by;
	}
	
	public String getVoucher_address() {
		return voucher_address;
	}
	public void setVoucher_address(String voucher_address) {
		this.voucher_address = voucher_address;
	}
	public String getVoucher_date() {
		return voucher_date;
	}
	public void setVoucher_date(String voucher_date) {
		this.voucher_date = voucher_date;
	}
	public String getVoucher_guid() {
		return voucher_guid;
	}
	public void setVoucher_guid(String voucher_guid) {
		this.voucher_guid = voucher_guid;
	}
	public String getVoucher_state() {
		return voucher_state;
	}
	public void setVoucher_state(String voucher_state) {
		this.voucher_state = voucher_state;
	}
	public String getVoucher_party_name() {
		return voucher_party_name;
	}
	public void setVoucher_party_name(String voucher_party_name) {
		this.voucher_party_name = voucher_party_name;
	}
	public String getVoucher_type() {
		return voucher_type;
	}
	public void setVoucher_type(String voucher_type) {
		this.voucher_type = voucher_type;
	}
	public String getVoucher_number() {
		return voucher_number;
	}
	public void setVoucher_number(String voucher_number) {
		this.voucher_number = voucher_number;
	}
	public String getVoucher_party_ledger_name() {
		return voucher_party_ledger_name;
	}
	public void setVoucher_party_ledger_name(String voucher_party_ledger_name) {
		this.voucher_party_ledger_name = voucher_party_ledger_name;
	}
	public String getVoucher_place_of_supply() {
		return voucher_place_of_supply;
	}
	public void setVoucher_place_of_supply(String voucher_place_of_supply) {
		this.voucher_place_of_supply = voucher_place_of_supply;
	}
	public String getVoucher_date_of_invoice() {
		return voucher_date_of_invoice;
	}
	public void setVoucher_date_of_invoice(String voucher_date_of_invoice) {
		this.voucher_date_of_invoice = voucher_date_of_invoice;
	}
	public String getVoucher_alter_id() {
		return voucher_alter_id;
	}
	public void setVoucher_alter_id(String voucher_alter_id) {
		this.voucher_alter_id = voucher_alter_id;
	}
	public String getVoucher_master_id() {
		return voucher_master_id;
	}
	public void setVoucher_master_id(String voucher_master_id) {
		this.voucher_master_id = voucher_master_id;
	}
	public String getVoucher_key() {
		return voucher_key;
	}
	public void setVoucher_key(String voucher_key) {
		this.voucher_key = voucher_key;
	}
	
}
