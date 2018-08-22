package in.co.khuranasales.khuranasales.exportWorkers;

public class cheque_export {
    public String cheque_payment_amount;
    public String cheque_bank_name;
    public String promoter_name;
    public String customer_name;
    public String sales_order_number;
    public String getCheque_payment_amount() {
        return cheque_payment_amount;
    }

    public void setCheque_payment_amount(String cheque_payment_amount) {
        this.cheque_payment_amount = cheque_payment_amount;
    }

    public String getCheque_bank_name() {
        return cheque_bank_name;
    }

    public void setCheque_bank_name(String cheque_bank_name) {
        this.cheque_bank_name = cheque_bank_name;
    }

    public String getPromoter_name() {
        return promoter_name;
    }

    public void setPromoter_name(String promoter_name) {
        this.promoter_name = promoter_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getSales_order_number() {
        return sales_order_number;
    }

    public void setSales_order_number(String sales_order_number) {
        this.sales_order_number = sales_order_number;
    }
}
