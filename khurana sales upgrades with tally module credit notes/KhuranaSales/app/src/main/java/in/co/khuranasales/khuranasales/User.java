package in.co.khuranasales.khuranasales;

public class User {

    public String name;
    public String user_type;
    public String contact_number;
    public int customer_id;
    public String email;
    public String shop_name;
    public String Firebaseld;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseld() {
        return Firebaseld;
    }

    public void setFirebaseld(String firebaseld) {
        Firebaseld = firebaseld;
    }

    public User(String name,String contact_number,String  user_type)
    {
        this.name = name;
        this.contact_number = contact_number;
        this.user_type = user_type;
    }
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

}
