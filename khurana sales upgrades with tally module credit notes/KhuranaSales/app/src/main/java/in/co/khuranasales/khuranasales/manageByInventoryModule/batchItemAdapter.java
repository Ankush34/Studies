package in.co.khuranasales.khuranasales.manageByInventoryModule;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import org.w3c.dom.Text;

import java.util.ArrayList;

import in.co.khuranasales.khuranasales.Product_desc_activity;
import in.co.khuranasales.khuranasales.R;
import in.co.khuranasales.khuranasales.recyclerViewItemClickListener;

public class batchItemAdapter extends RecyclerView.Adapter<batchItemAdapter.ViewHolder> {

    public Activity mActivity;
    public ArrayList<batchItem> items;
    public recyclerViewItemClickListener inventory_click_listener;
    private int lastPosition = -1;
    public batchItemAdapter(Activity mActivity, ArrayList<batchItem> batch_items)
    {
        this.mActivity = mActivity;
        this.items = batch_items;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        batchItem item = items.get(position);
        holder.inventory_text.setText(item.getValue());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case 0:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_batch_section, parent,false);
                batchItemAdapter.ViewHolder viewHolder = new batchItemAdapter.ViewHolder(view);
                return viewHolder;
            case 1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_batch_row, parent,false);
                batchItemAdapter.ViewHolder viewHolder1 = new batchItemAdapter.ViewHolder(view1);
                return viewHolder1;
        }
        return null;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        batchItem item = items.get(position);
        switch (item.getType())
        {
            case "Section":
                return 0;
            case "Batch":
                return 1;
        }
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView inventory_text;
        public ViewHolder(View itemView){
            super(itemView);
            inventory_text = (TextView)itemView.findViewById(R.id.inventory_text);
        }

        @Override
        public void onClick(View v) {
        }
    }

}
