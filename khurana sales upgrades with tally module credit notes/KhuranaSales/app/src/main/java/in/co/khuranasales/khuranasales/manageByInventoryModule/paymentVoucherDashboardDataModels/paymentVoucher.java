package in.co.khuranasales.khuranasales.manageByInventoryModule.paymentVoucherDashboardDataModels;

import java.util.ArrayList;

public class paymentVoucher {
	public String payment_date;
	public String payment_guid;
	public String payment_voucher_number;
	public String party_ledger_name;
	public String payment_voucher_created_by;
	public String payment_effective_date;
	public String payment_has_cash_flow;
	public String payment_alter_id;
	public String payment__master_id;
	public String payment_voucher_key;
	public String payment_voucher_ledger_name;
	public String payment_voucher_amount;
	public String payment_voucher_type;
	public String company_name;
	public String company_tag;
	public String company_state;
	
	public ArrayList<paymentVoucherLedger> payment_ledgers = new ArrayList<>();
	public ArrayList<paymentVoucherBillInfo> payment_bills = new ArrayList<>();
	public ArrayList<paymentLedgerTransactionInfo> payment_transactions = new ArrayList<>();
	
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
	
	public String getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}
	public String getPayment_guid() {
		return payment_guid;
	}
	public void setPayment_guid(String payment_guid) {
		this.payment_guid = payment_guid;
	}
	public String getPayment_voucher_number() {
		return payment_voucher_number;
	}
	public void setPayment_voucher_number(String payment_voucher_number) {
		this.payment_voucher_number = payment_voucher_number;
	}
	public String getParty_ledger_name() {
		return party_ledger_name;
	}
	public void setParty_ledger_name(String party_ledger_name) {
		this.party_ledger_name = party_ledger_name;
	}
	public String getPayment_voucher_created_by() {
		return payment_voucher_created_by;
	}
	public void setPayment_voucher_created_by(String payment_voucher_created_by) {
		this.payment_voucher_created_by = payment_voucher_created_by;
	}
	public String getPayment_effective_date() {
		return payment_effective_date;
	}
	public void setPayment_effective_date(String payment_effective_date) {
		this.payment_effective_date = payment_effective_date;
	}
	public String getPayment_has_cash_flow() {
		return payment_has_cash_flow;
	}
	public void setPayment_has_cash_flow(String payment_has_cash_flow) {
		this.payment_has_cash_flow = payment_has_cash_flow;
	}
	public String getPayment_alter_id() {
		return payment_alter_id;
	}
	public void setPayment_alter_id(String payment_alter_id) {
		this.payment_alter_id = payment_alter_id;
	}
	public String getPayment__master_id() {
		return payment__master_id;
	}
	public void setPayment__master_id(String payment__master_id) {
		this.payment__master_id = payment__master_id;
	}
	public String getPayment_voucher_key() {
		return payment_voucher_key;
	}
	public void setPayment_voucher_key(String payment_voucher_key) {
		this.payment_voucher_key = payment_voucher_key;
	}
	public String getPayment_voucher_ledger_name() {
		return payment_voucher_ledger_name;
	}
	public void setPayment_voucher_ledger_name(String payment_voucher_ledger_name) {
		this.payment_voucher_ledger_name = payment_voucher_ledger_name;
	}
	public String getPayment_voucher_amount() {
		return payment_voucher_amount;
	}
	public void setPayment_voucher_amount(String payment_voucher_amount) {
		this.payment_voucher_amount = payment_voucher_amount;
	}
	public ArrayList<paymentVoucherLedger> getPayment_ledgers() {
		return payment_ledgers;
	}
	public void setPayment_ledgers(ArrayList<paymentVoucherLedger> payment_ledgers) {
		this.payment_ledgers = payment_ledgers;
	}
	public ArrayList<paymentVoucherBillInfo> getPayment_bills() {
		return payment_bills;
	}
	public void setPayment_bills(ArrayList<paymentVoucherBillInfo> payment_bills) {
		this.payment_bills = payment_bills;
	}
	public ArrayList<paymentLedgerTransactionInfo> getPayment_transactions() {
		return payment_transactions;
	}
	public void setPayment_transactions(ArrayList<paymentLedgerTransactionInfo> payment_transactions) {
		this.payment_transactions = payment_transactions;
	}
	
	public String getPayment_voucher_type() {
		return payment_voucher_type;
	}
	public void setPayment_voucher_type(String payment_voucher_type) {
		this.payment_voucher_type = payment_voucher_type;
	}
	
}
