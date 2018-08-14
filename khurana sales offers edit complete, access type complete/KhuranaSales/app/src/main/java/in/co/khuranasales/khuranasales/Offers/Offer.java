package in.co.khuranasales.khuranasales.Offers;

import java.util.ArrayList;

public class Offer {

    public String offer_id;
    public String offer_title;
    public String offer_descripiton;
    public String offer_product_name;
    public String offer_product_price;
    public String offer_product_discounted_price;
    public String offer_product_mop_price;
    public String offr_producT_id;
    public String offer_product_image_links;
    public String offer_type;
    public String discounted_product_ids;
    public String free_product_ids;
    public String discounted_product_prices;
    public String discount_amount_offeres_single;
    public String item_count;
    public String total_discounted_amount;
    public Boolean lies_in_discount = false;
    public Boolean lies_in_offers = false;
    public Boolean expired = false;
    public int count_applicable = 0;
    public String expiry_date;
    public String count_of_times;
    public String access_allowed;


    public String getAccess_allowed() {
        return access_allowed;
    }

    public void setAccess_allowed(String access_allowed) {
        this.access_allowed = access_allowed;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getCount_of_times() {
        return count_of_times;
    }

    public void setCount_of_times(String count_of_times) {
        this.count_of_times = count_of_times;
    }


    public int getCount_applicable() {
        return count_applicable;
    }

    public void setCount_applicable(int count_applicable) {
        this.count_applicable = count_applicable;
    }

    public Boolean isExpired()
    {
        return  expired;
    }

    public void setExpired(Boolean expired)
    {
        this.expired = expired;
    }

    public ArrayList<Offer> discounted_products_offers_list = new ArrayList<>();

    public String offer_product_instock_quantity;


    public String getOffer_product_instock_quantity() {
        return offer_product_instock_quantity;
    }

    public void setOffer_product_instock_quantity(String offer_product_instock_quantity) {
        this.offer_product_instock_quantity = offer_product_instock_quantity;
    }

    public Boolean getLies_in_discount() {
        return lies_in_discount;
    }

    public void setLies_in_discount(Boolean lies_in_discount) {
        this.lies_in_discount = lies_in_discount;
    }

    public Boolean getLies_in_offers() {
        return lies_in_offers;
    }

    public void setLies_in_offers(Boolean lies_in_offers) {
        this.lies_in_offers = lies_in_offers;
    }

    public String getOffer_product_mop_price() {
        return offer_product_mop_price;
    }

    public void setOffer_product_mop_price(String offer_product_mop_price) {
        this.offer_product_mop_price = offer_product_mop_price;
    }

    public String getOffr_producT_id() {
        return offr_producT_id;
    }

    public void setOffr_producT_id(String offr_producT_id) {
        this.offr_producT_id = offr_producT_id;
    }

    public String getOffer_product_image_links() {
        return offer_product_image_links;
    }

    public void setOffer_product_image_links(String offer_product_image_links) {
        this.offer_product_image_links = offer_product_image_links;
    }

    public String getOffer_title() {
        return offer_title;
    }

    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    public String getOffer_descripiton() {
        return offer_descripiton;
    }

    public void setOffer_descripiton(String offer_descripiton) {
        this.offer_descripiton = offer_descripiton;
    }

    public String getOffer_product_name() {
        return offer_product_name;
    }

    public void setOffer_product_name(String offer_product_name) {
        this.offer_product_name = offer_product_name;
    }

    public String getOffer_product_price() {
        return offer_product_price;
    }

    public void setOffer_product_price(String offer_product_price) {
        this.offer_product_price = offer_product_price;
    }

    public String getOffer_product_discounted_price() {
        return offer_product_discounted_price;
    }

    public void setOffer_product_discounted_price(String offer_product_discounted_price) {
        this.offer_product_discounted_price = offer_product_discounted_price;
    }

    public String getOffer_type() {
        return offer_type;
    }

    public void setOffer_type(String offer_type) {
        this.offer_type = offer_type;
    }

    public String getDiscounted_product_ids() {
        return discounted_product_ids;
    }

    public void setDiscounted_product_ids(String discounted_product_ids) {
        this.discounted_product_ids = discounted_product_ids;
    }

    public String getFree_product_ids() {
        return free_product_ids;
    }

    public void setFree_product_ids(String free_product_ids) {
        this.free_product_ids = free_product_ids;
    }

    public String getDiscounted_product_prices() {
        return discounted_product_prices;
    }

    public void setDiscounted_product_prices(String discounted_product_prices) {
        this.discounted_product_prices = discounted_product_prices;
    }

    public String getDiscount_amount_offeres_single() {
        return discount_amount_offeres_single;
    }

    public void setDiscount_amount_offeres_single(String discount_amount_offeres_single) {
        this.discount_amount_offeres_single = discount_amount_offeres_single;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public String getTotal_discounted_amount() {
        return total_discounted_amount;
    }

    public void setTotal_discounted_amount(String total_discounted_amount) {
        this.total_discounted_amount = total_discounted_amount;
    }

}
