package in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels;

import java.util.ArrayList;

public class creditNote {
	public String credit_date;
	public String credit_guid;
	public String credit_state;
	public String credit_gstin;
	public String credit_voucher_type;
	public String credit_ledger_name;
	public String credit_supply_place_name;
	public String credit_date_of_invoice;
	public String voucher_key;
	public String alter_id;
	public String master_id;
	public String narration;
	public String credit_amount;
	
	public String company_tag;
	public String company_name;
	public String company_state;

	public String ledger_type;
	public String credit_address;
	public String credit_note_created_by;

	public ArrayList<credit_note_ledger> credit_ledgers = new ArrayList<>();
	public ArrayList<credit_note_item> credit_note_items = new ArrayList<>();
	public ArrayList<credit_note_account_ledger> credit_note_accounting_ledgers = new ArrayList<>();

	public String getCredit_note_created_by() {
		return credit_note_created_by;
	}
	public void setCredit_note_created_by(String credit_note_created_by) {
		this.credit_note_created_by = credit_note_created_by;
	}
	public String getLedger_type() {
		return ledger_type;
	}
	public void setLedger_type(String ledger_type) {
		this.ledger_type = ledger_type;
	}
	
	public String getCredit_address() {
		return credit_address;
	}
	public void setCredit_address(String credit_address) {
		this.credit_address = credit_address;
	}
	public String getCredit_date() {
		return credit_date;
	}
	public void setCredit_date(String credit_date) {
		this.credit_date = credit_date;
	}
	public String getCredit_guid() {
		return credit_guid;
	}
	public void setCredit_guid(String credit_guid) {
		this.credit_guid = credit_guid;
	}
	public String getCredit_state() {
		return credit_state;
	}
	public void setCredit_state(String credit_state) {
		this.credit_state = credit_state;
	}
	public String getCredit_gstin() {
		return credit_gstin;
	}
	public void setCredit_gstin(String credit_gstin) {
		this.credit_gstin = credit_gstin;
	}
	public String getCredit_voucher_type() {
		return credit_voucher_type;
	}
	public void setCredit_voucher_type(String credit_voucher_type) {
		this.credit_voucher_type = credit_voucher_type;
	}
	public String getCredit_ledger_name() {
		return credit_ledger_name;
	}
	public void setCredit_ledger_name(String credit_ledger_name) {
		this.credit_ledger_name = credit_ledger_name;
	}
	public String getCredit_supply_place_name() {
		return credit_supply_place_name;
	}
	public void setCredit_supply_place_name(String credit_supply_place_name) {
		this.credit_supply_place_name = credit_supply_place_name;
	}
	public String getCredit_date_of_invoice() {
		return credit_date_of_invoice;
	}
	public void setCredit_date_of_invoice(String credit_date_of_invoice) {
		this.credit_date_of_invoice = credit_date_of_invoice;
	}
	public String getCredit_amount() {
		return credit_amount;
	}
	public void setCredit_amount(String credit_amount) {
		this.credit_amount = credit_amount;
	}
	
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	
	public String getCompany_tag() {
		return company_tag;
	}
	public void setCompany_tag(String company_tag) {
		this.company_tag = company_tag;
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
	
	public String getVoucher_key() {
		return voucher_key;
	}
	public void setVoucher_key(String voucher_key) {
		this.voucher_key = voucher_key;
	}
	public String getAlter_id() {
		return alter_id;
	}
	public void setAlter_id(String alter_id) {
		this.alter_id = alter_id;
	}
	public String getMaster_id() {
		return master_id;
	}
	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}
	
}
