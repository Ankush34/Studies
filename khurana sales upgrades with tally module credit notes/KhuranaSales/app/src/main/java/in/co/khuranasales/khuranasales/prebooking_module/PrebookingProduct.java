package in.co.khuranasales.khuranasales.prebooking_module;

public class PrebookingProduct {
    public String name;
    public String expected_date;
    public String expected_price;
    public String prebooking_product_id;
    public String link;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpected_date() {
        return expected_date;
    }

    public void setExpected_date(String expected_date) {
        this.expected_date = expected_date;
    }

    public String getExpected_price() {
        return expected_price;
    }

    public void setExpected_price(String expected_price) {
        this.expected_price = expected_price;
    }

    public String getPrebooking_product_id() {
        return prebooking_product_id;
    }

    public void setPrebooking_product_id(String prebooking_product_id) {
        this.prebooking_product_id = prebooking_product_id;
    }

}
