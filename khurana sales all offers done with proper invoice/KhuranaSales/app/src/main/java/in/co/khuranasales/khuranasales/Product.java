package in.co.khuranasales.khuranasales;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by Ankush khurana on 7/5/2017.
 */

public class Product implements Parcelable
{
    private int sales_order_invoice_number;
    private String promoter_name;
    private String promoter_contact;
    private String promoter_id;
    private int product_id;
    private String Color;
    private int ram_size;
    private String to_address;
    private int price_mrp;
    private int sales_order_number ;
    private String sales_order_date;
    private int price_mop;
    private boolean status;
    private int price_ks;
    private int mem_size;
    private String Brand;
    private String Name;
    private int stock;
    private boolean update_status;
    private String link;
    private int _id;
    private float tax;
    private static Boolean word_present = false;
    private String order_type;
    private String order_status;
    private String invoice_status;
    private String customer_name;
    private Boolean favorite_status;
    private String customer_contact;
    public ArrayList<Batch> selected_batch_numbers = new ArrayList<>();
    public ArrayList<Batch> batch_numbers =new ArrayList<>();
    public String Product_HSN;
    public int sold_count;
    public String product_type;
    private int dicount_amount;
    private String offer_type;
    private int min_item_count;
    private int discount_offered_online_fetch;
    private String launch_date;


    //this field tells if the product has offers associated with it or no
    private boolean contains_offers = false;


    public boolean has_offers() {
        return contains_offers;
    }

    public void set_has_offers(boolean contains_offers) {
        this.contains_offers = contains_offers;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(String launch_date) {
        this.launch_date = launch_date;
    }


    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public int getMin_item_count() {
        return min_item_count;
    }

    public void setMin_item_count(int min_item_count) {
        this.min_item_count = min_item_count;
    }

    public int getDiscount_offered_online_fetch() {
        return discount_offered_online_fetch;
    }

    public void setDiscount_offered_online_fetch(int discount_offered_online_fetch) {
        this.discount_offered_online_fetch = discount_offered_online_fetch;
    }

    public int getDicount_amount() {
        return dicount_amount;
    }

    public void setDicount_amount(int dicount_amount) {
        this.dicount_amount = dicount_amount;
    }

    public String getOffer_type() {
        return offer_type;
    }

    public void setOffer_type(String offer_type) {
        this.offer_type = offer_type;
    }



    public int getSold_count() {
        return sold_count;
    }

    public void setSold_count(int sold_count) {
        this.sold_count = sold_count;
    }

    public Boolean isFavorite() {

        return this.favorite_status;
    }

    public void setFavorite_status(Boolean favorite_status) {
        this.favorite_status = favorite_status;
    }

    public ArrayList<Batch> getSelected_batch_numbers() {
        return selected_batch_numbers;
    }


    public int getSales_order_invoice_number() {
        return sales_order_invoice_number;
    }

    public void setSales_order_invoice_number(int sales_order_invoice_number) {
        this.sales_order_invoice_number = sales_order_invoice_number;
    }

    public void addSelected_batch_numbers(Batch selected_batch_number) {
        this.selected_batch_numbers.add(selected_batch_number);
    }
    public void remove_batch_number(Batch batch_number) {
        for(int i = 0 ; i <selected_batch_numbers.size(); i++)
        {
            if(selected_batch_numbers.get(i).getNumber().equals(batch_number.getNumber()))
            {
                selected_batch_numbers.remove(i);
            }
        }
    }
    public ArrayList<Batch> getBatchNumbers() {
        return batch_numbers;
    }

    public void addBatchNumbers(Batch batch_number) {
        this.batch_numbers.add(batch_number);
    }

    public String getPromoter_name() {
        return promoter_name;
    }

    public void setPromoter_name(String promoter_name) {
        this.promoter_name = promoter_name;
    }

    public String getPromoter_contact() {
        return promoter_contact;
    }

    public void setPromoter_contact(String promoter_contact) {
        this.promoter_contact = promoter_contact;
    }

    public String getPromoter_id() {
        return promoter_id;
    }

    public void setPromoter_id(String promoter_id) {
        this.promoter_id = promoter_id;
    }

    public String getOrder_type() { return order_type; }

    public void setOrder_type(String order_type) { this.order_type = order_type; }

    public String getOrder_status() { return order_status; }

    public void setOrder_status(String order_status) { this.order_status = order_status; }

    public String getInvoice_status() { return invoice_status; }

    public void setInvoice_status(String invoice_status) { this.invoice_status = invoice_status; }

    public String getCustomer_name() { return customer_name; }

    public void setCustomer_name(String customer_name) { this.customer_name = customer_name; }

    public String getCustomer_contact() { return customer_contact; }

    public void setCustomer_contact(String customer_contact) { this.customer_contact = customer_contact; }

    public float getTax() { return tax; }

    public void setTax(float tax) { this.tax = tax; }

    public String get_sales_order_date() {
        return sales_order_date;
    }

    public void set_sales_order_date(String sales_order_date) {
        this.sales_order_date = sales_order_date; }

    public int get_sales_order_number() {
        return sales_order_number;
    }

    public void set_sales_order_number(int sales_order_number) {
        this.sales_order_number = sales_order_number; }
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean get_update_status() {
        return update_status;
    }

    public void set_update_status(boolean update_status) {
        this.update_status = update_status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getRam_size() {
        return ram_size;
    }

    public void setRam_size(int ram_size) {
        this.ram_size = ram_size;
    }

    public int getPrice_mrp() {
        return price_mrp;
    }

    public void setPrice_mrp(int price_mrp) {
        this.price_mrp = price_mrp;
    }

    public int getPrice_mop() {
        return price_mop;
    }

    public void setPrice_mop(int price_mop) {
        this.price_mop = price_mop;
    }

    public int getPrice_ks() {
        return price_ks;
    }

    public void setPrice_ks(int price_ks) {
        this.price_ks = price_ks;
    }
    public int getMem_size() {
        return mem_size;
    }

    public void setMem_size(int mem_size) {
        this.mem_size = mem_size;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String get_Name() {
        return Name;
    }

    public void set_Name(String name) {
        Name = name;
    }

    public int get_Stock() {
        return stock;
    }

    public void set_Stock(int stock) {
        this.stock = stock;
    }

    public String get_link() {
        return link;
    }

    public void set_link(String link) {
        this.link = link;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_to_address() {
        return to_address;
    }

    public void set_to_address(String to_address) {
        this.to_address = to_address;
    }

    public String getProduct_HSN() {
        return Product_HSN;
    }

    public void setProduct_HSN(String product_HSN) {
        Product_HSN = product_HSN;
    }

    public Product()
    {  }
    public Product(String name, int stock)
    {
        this.Name=name;
        this.stock=stock; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(Name);
        dest.writeInt(price_mop);
        dest.writeInt(price_mrp);
        dest.writeInt(price_ks);
        dest.writeInt(stock);
        dest.writeFloat(tax);
        dest.writeString(Product_HSN);
        dest.writeList(getSelected_batch_numbers());
    }

    private Product(Parcel in)
    {
        Name = in.readString();
        price_mop = in.readInt();
        price_mrp = in.readInt();
        price_ks = in.readInt();
        stock = in.readInt();
        tax = in.readFloat();
        Product_HSN = in.readString();
        selected_batch_numbers = in.readArrayList(Batch.class.getClassLoader());
    }
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }
        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public static ArrayList<ProductListElement> load_names(ArrayList<Product> products)
    {
        ArrayList<ProductListElement> names = new ArrayList<>();
        for(int i = 0; i< products.size(); i ++)
        {
            ProductListElement elem = new ProductListElement();
            elem.setProduct_name(products.get(i).get_Name());
            names.add(elem);
        }
        return names;
    }
    public static ArrayList<ProductListElement> filtered_products(ArrayList<ProductListElement> list, String filter)
    {
        String[] words_searchable = filter.split(" ");
        Log.d("Product class: ","filtering......."+list.size());
        ArrayList<ProductListElement> filtered_products = new ArrayList<>();

        for(ProductListElement p : list)
        {
           word_present = false;
            for(String word : words_searchable)
            {
                if(p.getProduct_name().toLowerCase().contains(word.toLowerCase()))
                {
                    word_present = true;
                    continue;
                }
                else
                {
                    word_present = false;
                    break;
                }
            }
            if(word_present  == true)
            {
                filtered_products.add(p);
            }

        }
        Log.d("Product class Size: ","filtering......."+filtered_products.size());
        return  filtered_products;
    }

    public String get_batch_numbers_filtered(String type)
    {
        StringBuilder builder = new StringBuilder();
        if(type.equals("selected"))
        {
            for(int i = 0 ; i <this.selected_batch_numbers.size();i++)
            {
                builder.append(this.selected_batch_numbers.get(i).getNumber()+",");
            }
        }else if(type.equals("filtered"))
        {
            for(int i = 0 ; i <this.batch_numbers.size();i++)
            {
                builder.append(this.batch_numbers.get(i).getNumber()+",");
            }
            for(int i = 0 ; i < this.selected_batch_numbers.size(); i ++)
            {
                if(builder.toString().contains(this.selected_batch_numbers.get(i).getNumber()))
                {
                builder = new StringBuilder(builder.toString().replace(this.selected_batch_numbers.get(i).getNumber()+",",""));
                }
            }
        }
        if(!builder.toString().equals(""))
        {
            builder.replace(builder.toString().length()-1,builder.toString().length(),"");
        }
        return builder.toString();
    }
}
