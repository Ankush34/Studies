package in.co.khuranasales.khuranasales;

/**
 * Created by Ankush khurana on 7/30/2017.
 */

public class Address {
    public String address;
    public boolean status;
    public Address(String address1)
    {
        address=address1;
    }
    public void setStatus(boolean status1)
    {
    status=status1;
      }
    public void setAddress(String address1)
    {
        address=address1;
    }
    public boolean getStatus()
    {
        return status;
    }
    public String getAddress()
    {
        return address;
    }
}
