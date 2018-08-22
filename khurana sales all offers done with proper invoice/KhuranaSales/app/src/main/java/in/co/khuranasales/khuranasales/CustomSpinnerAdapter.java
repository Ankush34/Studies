package in.co.khuranasales.khuranasales;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomSpinnerAdapter extends BaseAdapter {
    public ArrayList<String> names = new ArrayList<>();
    public Activity mActivity;
    public LayoutInflater inflater;
    public CustomSpinnerAdapter(Activity activity , ArrayList<String> names)
    {
        this.mActivity  = activity;
        this.names = names;
        inflater = LayoutInflater.from(mActivity.getApplicationContext());
    }

    @Override
    public int getCount() {
       return names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = inflater.inflate(R.layout.spinner_item_layout_notification_activity, null);
        TextView name = (TextView) view.findViewById(R.id.spinner_item);
        name.setText(names.get(position));
        return view;
    }
}
