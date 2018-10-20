package in.co.khuranasales.khuranasales.manageByInventoryModule.journalVoucherDashboardDataModels;

import java.util.ArrayList;

public class journalVoucher {
	public String jounal_voucher_date;
	public String journal_voucher_guid;
	public String journal_voucher_created_by;
	public String journal_voucher_altered_by;
	public String voucher_type;
	public String voucher_number;
	public String destination_go_down_location;
	public String journal_voucher_effective_date;
	public String journal_voucher_alter_id;
	public String journal_voucher_master_id;
	public String journal_voucher_key;
	
	public String company_name;
	public String company_tag;
	public String company_state;
	public String items_ids;


	public String getItems_ids() {
		return items_ids;
	}

	public void setItems_ids(String items_ids) {
		this.items_ids = items_ids;
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

	public ArrayList<journalVoucherItem> voucher_items = new ArrayList<>();
	
	public String getJounal_voucher_date() {
		return jounal_voucher_date;
	}

	public void setJounal_voucher_date(String jounal_voucher_date) {
		this.jounal_voucher_date = jounal_voucher_date;
	}

	public String getJournal_voucher_guid() {
		return journal_voucher_guid;
	}

	public void setJournal_voucher_guid(String journal_voucher_guid) {
		this.journal_voucher_guid = journal_voucher_guid;
	}

	public String getJournal_voucher_created_by() {
		return journal_voucher_created_by;
	}

	public void setJournal_voucher_created_by(String journal_voucher_created_by) {
		this.journal_voucher_created_by = journal_voucher_created_by;
	}

	public String getJournal_voucher_altered_by() {
		return journal_voucher_altered_by;
	}

	public void setJournal_voucher_altered_by(String journal_voucher_altered_by) {
		this.journal_voucher_altered_by = journal_voucher_altered_by;
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

	public String getDestination_go_down_location() {
		return destination_go_down_location;
	}

	public void setDestination_go_down_location(String destination_go_down_location) {
		this.destination_go_down_location = destination_go_down_location;
	}

	public String getJournal_voucher_effective_date() {
		return journal_voucher_effective_date;
	}

	public void setJournal_voucher_effective_date(String journal_voucher_effective_date) {
		this.journal_voucher_effective_date = journal_voucher_effective_date;
	}

	public String getJournal_voucher_alter_id() {
		return journal_voucher_alter_id;
	}

	public void setJournal_voucher_alter_id(String journal_voucher_alter_id) {
		this.journal_voucher_alter_id = journal_voucher_alter_id;
	}

	public String getJournal_voucher_master_id() {
		return journal_voucher_master_id;
	}

	public void setJournal_voucher_master_id(String journal_voucher_master_id) {
		this.journal_voucher_master_id = journal_voucher_master_id;
	}

	public String getJournal_voucher_key() {
		return journal_voucher_key;
	}

	public void setJournal_voucher_key(String journal_voucher_key) {
		this.journal_voucher_key = journal_voucher_key;
	}

	public ArrayList<journalVoucherItem> getVoucher_items() {
		return voucher_items;
	}

	public void setVoucher_items(ArrayList<journalVoucherItem> voucher_items) {
		this.voucher_items = voucher_items;
	}

}
