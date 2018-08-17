package in.co.khuranasales.khuranasales;

import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush Khurana on 28/07/2017.
 */

public class BrandSelectionCategorizationAdapter extends RecyclerView.Adapter<BrandSelectionCategorizationAdapter.ViewHolder> {
    private Activity activity;
    public List<String> names;
    public static ArrayList<String> selected_payment_modes = new ArrayList<String>();
    public static ArrayList<String> selected_names = new ArrayList<String>();
    public BrandSelectionCategorizationAdapter(Activity activity, List<String> names) {
        this.activity = activity;
        this.names = names;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_selection_card_categorization, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String  name = names.get(position);
        holder.brand.setText(name);
        if(holder.itemView.getContext().getClass().getSimpleName().equals("Buy_final_activity"))
       {
           holder.relative_brand.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(selected_payment_modes.contains(name))
                   {
                       selected_payment_modes.remove(name);
                       notifyItemChanged(position);
                   }
                   else if(!selected_payment_modes.contains(name))
                   {
                       selected_payment_modes.add(name);
                       notifyItemChanged(position);
                   }
               }
           });
           holder.checked.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   selected_payment_modes.remove(name);
                   notifyItemChanged(position);
               }
           });

           holder.unchecked.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   selected_payment_modes.add(name);
                   notifyItemChanged(position);
               }
           });
           if(selected_payment_modes.contains(name))
           {
               holder.unchecked.setVisibility(View.INVISIBLE);
               holder.checked.setVisibility(View.VISIBLE);
           }
           else
           {
               holder.checked.setVisibility(View.INVISIBLE);
               holder.unchecked.setVisibility(View.VISIBLE);
           }

       }
       else
       {
           holder.relative_brand.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(selected_names.contains(name))
                   {
                       selected_names.remove(name);
                       notifyItemChanged(position);
                   }
                   else if(!selected_names.contains(name))
                   {
                       selected_names.add(name);
                       notifyItemChanged(position);
                   }
               }
           });
           holder.checked.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   selected_names.remove(name);
                   notifyItemChanged(position);
               }
           });
           holder.unchecked.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   selected_names.add(name);
                   notifyItemChanged(position);
               }
           });
           if(selected_names.contains(name))
           {
               holder.unchecked.setVisibility(View.INVISIBLE);
               holder.checked.setVisibility(View.VISIBLE);
           }
           else
           {
               holder.checked.setVisibility(View.INVISIBLE);
               holder.unchecked.setVisibility(View.VISIBLE);
           }
       }
    }
    @Override
    public int getItemCount() {
        return names.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView brand;
        public RelativeLayout relative_brand;
        public ImageView unchecked;
        public ImageView checked;
        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                }
            });
        brand = (TextView)itemView.findViewById(R.id.checkedTextBrandSelection);
        unchecked = (ImageView)itemView.findViewById(R.id.unchecked);
        checked = (ImageView)itemView.findViewById(R.id.checked);
        relative_brand = (RelativeLayout)itemView.findViewById(R.id.select_brand_card);
        }

    }
}