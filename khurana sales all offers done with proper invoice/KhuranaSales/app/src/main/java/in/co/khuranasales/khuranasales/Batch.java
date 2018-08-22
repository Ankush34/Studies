package in.co.khuranasales.khuranasales;

import android.os.Parcel;
import android.os.Parcelable;

public class Batch implements Parcelable {
    public String location;
    public String number;

    public Batch(String location, String number)
    {
        this.location = location;
        this.number = number;
    }

    protected Batch(Parcel in) {
        location = in.readString();
        number = in.readString();
    }

    public static final Creator<Batch> CREATOR = new Creator<Batch>() {
        @Override
        public Batch createFromParcel(Parcel in) {
            return new Batch(in);
        }

        @Override
        public Batch[] newArray(int size) {
            return new Batch[size];
        }
    };

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(location);
        dest.writeString(number);
    }
}
