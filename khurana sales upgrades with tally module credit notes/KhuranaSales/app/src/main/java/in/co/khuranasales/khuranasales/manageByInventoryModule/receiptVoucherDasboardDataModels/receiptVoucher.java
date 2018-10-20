package in.co.khuranasales.khuranasales.manageByInventoryModule.receiptVoucherDasboardDataModels;

import java.util.ArrayList;

public class receiptVoucher {
	public String voucher_date;
	public String voucher_guid;
	public String vochcer_type;
	public String voucher_number;
	public String voucher_party_ledger_name;
	public String voucher_created_by;
	public String voucher_alter_id;
	public String voucher_master_id;
	public String voucher_key;
	public String voucher_amount_ledgers_account;

	public ArrayList<receiptVoucherLedger> ledgers = new ArrayList<>();
	public ArrayList<receiptVoucherBankInfo> bank_info = new ArrayList<>();

	public String company_name;
	public String company_tag;
	public String company_state;


	public String getVoucher_amount_ledgers_account() {
		return voucher_amount_ledgers_account;
	}

	public void setVoucher_amount_ledgers_account(String voucher_amount_ledgers_account) {
		this.voucher_amount_ledgers_account = voucher_amount_ledgers_account;
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
	public String getVochcer_type() {
		return vochcer_type;
	}
	public void setVochcer_type(String vochcer_type) {
		this.vochcer_type = vochcer_type;
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
	public String getVoucher_created_by() {
		return voucher_created_by;
	}
	public void setVoucher_created_by(String voucher_created_by) {
		this.voucher_created_by = voucher_created_by;
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
