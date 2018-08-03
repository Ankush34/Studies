package in.co.khuranasales.khuranasales;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class FinanceBankNameAdapter extends RecyclerView.Adapter<FinanceBankNameAdapter.ViewHolder>  {

    public Activity mActivity;
    public ArrayList<String> data = new ArrayList<>();
    public recyclerViewItemClickListener click_listener;
    public FinanceBankNameAdapter(Activity activity, ArrayList<String> data, recyclerViewItemClickListener listener)
    {
        this.mActivity = activity;
        this.data = data;
        this.click_listener = listener;
    }

    public void setListener(recyclerViewItemClickListener listener)
    {
        click_listener = listener;
    }
    @Override
    public FinanceBankNameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_name_list_layout, parent, false);
        FinanceBankNameAdapter.ViewHolder viewHolder = new FinanceBankNameAdapter.ViewHolder(view,click_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FinanceBankNameAdapter.ViewHolder holder, int position) {
        holder.name.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;

        public ViewHolder(final View itemView, recyclerViewItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            click_listener = listener;
            name = (TextView)itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View v) {
            click_listener.onItemClick(v,getAdapterPosition());
        }
    }
}


