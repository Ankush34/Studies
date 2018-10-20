package in.co.khuranasales.khuranasales.inventoryViewModule;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.R;

public class dateSelectorAdapter extends RecyclerView.Adapter<dateSelectorAdapter.ViewHolder> {

    public ArrayList<datePeriodPojo> date_period_pojos = new ArrayList<>();
    public Activity mActivity;

    public dateSelectorAdapter(ArrayList<datePeriodPojo> pojos, Activity activity)
    {
        this.date_period_pojos = pojos;
        this.mActivity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_period_selector_card, parent, false);
        ViewHolder view_holder = new ViewHolder(view);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.index.setText(""+(position+1)+" )");
        holder.date_period_name.setText(date_period_pojos.get(position).getDate_period_name());
    }

    @Override
    public int getItemCount() {
        return date_period_pojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date_period_name;
        TextView index;
        public ViewHolder(View item_view)
        {
            super(item_view);
            date_period_name = (TextView)item_view.findViewById(R.id.date_period_name);
            index = (TextView)item_view.findViewById(R.id.index);

        }
    }
}
