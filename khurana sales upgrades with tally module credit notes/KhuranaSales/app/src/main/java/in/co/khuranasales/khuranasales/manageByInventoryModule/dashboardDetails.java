package in.co.khuranasales.khuranasales.manageByInventoryModule;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.manageByInventoryModule.billsPayableDashboardDataModels.billPayable;
import in.co.khuranasales.khuranasales.manageByInventoryModule.bills_receivable_dashboard_data_models.billReceivable;
import in.co.khuranasales.khuranasales.manageByInventoryModule.creditNoteDashboardDataModels.creditNote;
import in.co.khuranasales.khuranasales.manageByInventoryModule.debitNodeDashboardDataModels.debitNote;
import in.co.khuranasales.khuranasales.manageByInventoryModule.journalVoucherDashboardDataModels.journalVoucher;
import in.co.khuranasales.khuranasales.manageByInventoryModule.paymentVoucherDashboardDataModels.paymentVoucher;
import in.co.khuranasales.khuranasales.manageByInventoryModule.purchaseVoucherDashboardDataModels.purchaseVoucher;
import in.co.khuranasales.khuranasales.manageByInventoryModule.receiptVoucherDasboardDataModels.receiptVoucher;
import in.co.khuranasales.khuranasales.manageByInventoryModule.retailVoucherDashboardDataModels.retailVoucher;

public class dashboardDetails {
    public String credit_note_todays_outstanding;
    public String credit_note_total_outstanding;
    public ArrayList<creditNote> credit_notes_past_15_days = new ArrayList<>();

    public String debit_note_todays_outstanding;
    public String debit_note_total_outstanding;
    public ArrayList<debitNote> debit_notes_past_15_days = new ArrayList<>();

    public String receipt_vouchers_todays_outstanding;
    public String receipt_vouchers_total_outstanding;
    public String total_receipt_vouchers_count;
    public String receipt_vouchers_15_days_outstanding;
    public ArrayList<receiptVoucher> receipt_vouchers = new ArrayList<>();

    public String total_purchase_voucher_count;
    public String purchase_vouchers_15_days_count;
    public String total_purchase_voucher_today_spent_amount;
    public String total_stock_bought_purchase_voucher;
    public String total_purchase_voucher_stock_bought_today;
    public String total_purchase_voucher_outstanding;
    public ArrayList<purchaseVoucher> purchase_vouchers = new ArrayList<purchaseVoucher>();

    public String bills_receivable_total_outstanding;
    public String bills_receivable_todays_outstanding;
    public ArrayList<billReceivable> bills_receivables = new ArrayList<>();

    public String payment_todays_outstanding;
    public String payment_outstanding_total;
    public ArrayList<paymentVoucher> payment_vouchers = new ArrayList<>();

    public String total_bills_payable_outstanding;
    public String todays_bills_payabele_outstanding;
    public ArrayList<billPayable> bills_payable = new ArrayList<billPayable>();

    public String journal_voucher_total_today;
    public String journal_voucher_past_15_days;
    public ArrayList<journalVoucher> journal_vouchers = new ArrayList<>();

    public String total_retail_outstanding;
    public String total_retail_outstanding_today;
    public String total_retail_outstanding_15_days;
    public ArrayList<retailVoucher> retail_vouchers = new ArrayList<retailVoucher>();


    public String getTotal_retail_outstanding() {
        return total_retail_outstanding;
    }

    public void setTotal_retail_outstanding(String total_retail_outstanding) {
        this.total_retail_outstanding = total_retail_outstanding;
    }

    public String getTotal_retail_outstanding_today() {
        return total_retail_outstanding_today;
    }

    public void setTotal_retail_outstanding_today(String total_retail_outstanding_today) {
        this.total_retail_outstanding_today = total_retail_outstanding_today;
    }

    public String getTotal_retail_outstanding_15_days() {
        return total_retail_outstanding_15_days;
    }

    public void setTotal_retail_outstanding_15_days(String total_retail_outstanding_15_days) {
        this.total_retail_outstanding_15_days = total_retail_outstanding_15_days;
    }

    public String getJournal_voucher_total_today() {
        return journal_voucher_total_today;
    }

    public void setJournal_voucher_total_today(String journal_voucher_total_today) {
        this.journal_voucher_total_today = journal_voucher_total_today;
    }

    public String getJournal_voucher_past_15_days() {
        return journal_voucher_past_15_days;
    }

    public void setJournal_voucher_past_15_days(String journal_voucher_past_15_days) {
        this.journal_voucher_past_15_days = journal_voucher_past_15_days;
    }

    public String getTotal_bills_payable_outstanding() {
        return total_bills_payable_outstanding;
    }

    public void setTotal_bills_payable_outstanding(String total_bills_payable_outstanding) {
        this.total_bills_payable_outstanding = total_bills_payable_outstanding;
    }

    public String getTodays_bills_payabele_outstanding() {
        return todays_bills_payabele_outstanding;
    }

    public void setTodays_bills_payabele_outstanding(String todays_bills_payabele_outstanding) {
        this.todays_bills_payabele_outstanding = todays_bills_payabele_outstanding;
    }

    public String getPayment_todays_outstanding() {
        return payment_todays_outstanding;
    }

    public void setPayment_todays_outstanding(String payment_todays_outstanding) {
        this.payment_todays_outstanding = payment_todays_outstanding;
    }

    public String getPayment_outstanding_total() {
        return payment_outstanding_total;
    }

    public void setPayment_outstanding_total(String payment_outstanding_total) {
        this.payment_outstanding_total = payment_outstanding_total;
    }

    public String getBills_receivable_total_outstanding() {
        return bills_receivable_total_outstanding;
    }

    public void setBills_receivable_total_outstanding(String bills_receivable_total_outstanding) {
        this.bills_receivable_total_outstanding = bills_receivable_total_outstanding;
    }

    public String getBills_receivable_todays_outstanding() {
        return bills_receivable_todays_outstanding;
    }

    public void setBills_receivable_todays_outstanding(String bills_receivable_todays_outstanding) {
        this.bills_receivable_todays_outstanding = bills_receivable_todays_outstanding;
    }


    public String getTotal_purchase_voucher_count() {
        return total_purchase_voucher_count;
    }

    public void setTotal_purchase_voucher_count(String total_purchase_voucher_count) {
        this.total_purchase_voucher_count = total_purchase_voucher_count;
    }

    public String getTotal_purchase_voucher_today_spent_amount() {
        return total_purchase_voucher_today_spent_amount;
    }

    public void setTotal_purchase_voucher_today_spent_amount(String total_purchase_voucher_today_spent_amount) {
        this.total_purchase_voucher_today_spent_amount = total_purchase_voucher_today_spent_amount;
    }

    public String getTotal_stock_bought_purchase_voucher() {
        return total_stock_bought_purchase_voucher;
    }

    public void setTotal_stock_bought_purchase_voucher(String total_stock_bought_purchase_voucher) {
        this.total_stock_bought_purchase_voucher = total_stock_bought_purchase_voucher;
    }

    public String getTotal_purchase_voucher_stock_bought_today() {
        return total_purchase_voucher_stock_bought_today;
    }

    public void setTotal_purchase_voucher_stock_bought_today(String total_purchase_voucher_stock_bought_today) {
        this.total_purchase_voucher_stock_bought_today = total_purchase_voucher_stock_bought_today;
    }

    public String getTotal_purchase_voucher_outstanding() {
        return total_purchase_voucher_outstanding;
    }

    public void setTotal_purchase_voucher_outstanding(String total_purchase_voucher_outstanding) {
        this.total_purchase_voucher_outstanding = total_purchase_voucher_outstanding;
    }


    public String getReceipt_vouchers_todays_outstanding() {
        return receipt_vouchers_todays_outstanding;
    }

    public void setReceipt_vouchers_todays_outstanding(String receipt_vouchers_todays_outstanding) {
        this.receipt_vouchers_todays_outstanding = receipt_vouchers_todays_outstanding;
    }

    public String getReceipt_vouchers_total_outstanding() {
        return receipt_vouchers_total_outstanding;
    }

    public void setReceipt_vouchers_total_outstanding(String receipt_vouchers_total_outstanding) {
        this.receipt_vouchers_total_outstanding = receipt_vouchers_total_outstanding;
    }

    public String getTotal_receipt_vouchers_count() {
        return total_receipt_vouchers_count;
    }

    public void setTotal_receipt_vouchers_count(String total_receipt_vouchers_count) {
        this.total_receipt_vouchers_count = total_receipt_vouchers_count;
    }

    public String getReceipt_vouchers_15_days_outstanding() {
        return receipt_vouchers_15_days_outstanding;
    }

    public void setReceipt_vouchers_15_days_outstanding(String receipt_vouchers_15_days_outstanding) {
        this.receipt_vouchers_15_days_outstanding = receipt_vouchers_15_days_outstanding;
    }


    public String getCredit_note_todays_outstanding() {
        return credit_note_todays_outstanding;
    }

    public void setCredit_note_todays_outstanding(String credit_note_todays_outstanding) {
        this.credit_note_todays_outstanding = credit_note_todays_outstanding;
    }

    public String getCredit_note_total_outstanding() {
        return credit_note_total_outstanding;
    }

    public void setCredit_note_total_outstanding(String credit_note_total_outstanding) {
        this.credit_note_total_outstanding = credit_note_total_outstanding;
    }

    public String getDebit_note_todays_outstanding() {
        return debit_note_todays_outstanding;
    }

    public void setDebit_note_todays_outstanding(String debit_note_todays_outstanding) {
        this.debit_note_todays_outstanding = debit_note_todays_outstanding;
    }

    public String getDebit_note_total_outstanding() {
        return debit_note_total_outstanding;
    }

    public void setDebit_note_total_outstanding(String debit_note_total_outstanding) {
        this.debit_note_total_outstanding = debit_note_total_outstanding;
    }


}
