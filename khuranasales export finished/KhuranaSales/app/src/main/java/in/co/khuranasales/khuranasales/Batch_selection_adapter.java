package in.co.khuranasales.khuranasales;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush Khurana on 28/07/2017.
 */

public class Batch_selection_adapter extends RecyclerView.Adapter<Batch_selection_adapter.ViewHolder> {
    private Activity activity;
    private ArrayList<Batch> batches;
    private recyclerViewItemClickListener mlistener;
    public  ArrayList<Batch> selected_batches ;
    public Batch_selection_adapter(Activity activity, ArrayList<Batch> names, recyclerViewItemClickListener click_listener) {
        this.activity = activity;
        this.batches = names;
        this.mlistener = click_listener;
        selected_batches = new ArrayList<Batch>();


    }
    public void setSelected_batches(ArrayList<Batch> batches)
    {
        this.selected_batches = batches;
    }
    public void setBatches(ArrayList<Batch> batches)
    {
        this.batches = batches;
    }

    public ArrayList<Batch> getBatches()
    {
        return this.batches;
    }
    public void setListener(recyclerViewItemClickListener listener)
    {
        mlistener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_selection_card_categorization, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mlistener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
         Batch  batch_number = batches.get(position);
         holder.batch.setText(batch_number.getNumber()+"  ("+batch_number.getLocation()+")");
         holder.checked.setVisibility(View.INVISIBLE);
         holder.unchecked.setVisibility(View.VISIBLE);
        boolean found = false;
        for(int i = 0; i<selected_batches.size();i++)
        {
            if(selected_batches.get(i).getNumber().equals(batch_number.getNumber()))
            {
                holder.checked.setVisibility(View.VISIBLE);
                holder.unchecked.setVisibility(View.INVISIBLE);
                found = true;
                break;
            }

        }
        if(found == false)
        {
                holder.checked.setVisibility(View.INVISIBLE);
                holder.unchecked.setVisibility(View.VISIBLE);
        }
           }
    @Override
    public int getItemCount() {
        return batches.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView batch;
        public RelativeLayout relative_batch;
        public ImageView unchecked;
        public ImageView checked;
        public ViewHolder(final View itemView, recyclerViewItemClickListener item_click_listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mlistener = item_click_listener;
            batch = (TextView)itemView.findViewById(R.id.checkedTextBrandSelection);
            unchecked = (ImageView)itemView.findViewById(R.id.unchecked);
            checked = (ImageView)itemView.findViewById(R.id.checked);
            relative_batch = (RelativeLayout)itemView.findViewById(R.id.select_brand_card);
            checked.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            boolean present = false;
            for(int i = 0; i < selected_batches.size();i++)
            {
                if(selected_batches.get(i).getNumber().equals(batches.get(getAdapterPosition()).getNumber()))
                {
                 selected_batches.remove(i);
                 present = true;
                }
            }
            if(present == false)
            {
                selected_batches.add(batches.get(getAdapterPosition()));
            }
            mlistener.onItemClick(v,getAdapterPosition());
        }
    }
}