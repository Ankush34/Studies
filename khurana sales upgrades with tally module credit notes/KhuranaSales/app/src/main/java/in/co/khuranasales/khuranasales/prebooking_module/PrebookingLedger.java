package in.co.khuranasales.khuranasales.prebooking_module;

public class PrebookingLedger {
    private String name;
    private String phone;
    private String address;
    private String prebooking_amount;
    private String prebooked_product_id;
    private String prebooked_product_name;
    private String booking_status;
    private String prebooking_id;

    public String getPrebooking_id() {
        return prebooking_id;
    }

    public void setPrebooking_id(String prebooking_id) {
        this.prebooking_id = prebooking_id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrebooking_amount() {
        return prebooking_amount;
    }

    public void setPrebooking_amount(String prebooking_amount) {
        this.prebooking_amount = prebooking_amount;
    }

    public String getPrebooked_product_id() {
        return prebooked_product_id;
    }

    public void setPrebooked_product_id(String prebooked_product_id) {
        this.prebooked_product_id = prebooked_product_id;
    }

    public String getPrebooked_product_name() {
        return prebooked_product_name;
    }

    public void setPrebooked_product_name(String prebooked_product_name) {
        this.prebooked_product_name = prebooked_product_name;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }



}
