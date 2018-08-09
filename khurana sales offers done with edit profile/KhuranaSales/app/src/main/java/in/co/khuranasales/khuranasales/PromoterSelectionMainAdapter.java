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

public class PromoterSelectionMainAdapter extends RecyclerView.Adapter<PromoterSelectionMainAdapter.ViewHolder> {
    private Activity activity;
    private List<Promoter> promoters;
    public static ArrayList<String> selected_promoters = new ArrayList<String>();
    public PromoterSelectionMainAdapter(Activity activity, List<Promoter> promoters) {
        this.activity = activity;
        this.promoters = promoters;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        // i have used the same view as that of brand no need to create the layout again for this view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_selection_card_categorization, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Promoter  promoter = promoters.get(position);
        holder.promoter.setText(promoter.getName());
        holder.relative_promoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected_promoters.contains(promoter.getEmail()))
                {
                    selected_promoters.remove(promoter.getEmail());
                    notifyItemChanged(position);
                }
                else if(!selected_promoters.contains(promoter.getEmail()))
                {
                    selected_promoters.add(promoter.getEmail());
                    notifyItemChanged(position);
                }
            }
        });
        holder.checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_promoters.remove(promoter.getEmail());
                notifyItemChanged(position);
            }
        });
        holder.unchecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_promoters.add(promoter.getEmail());
                notifyItemChanged(position);
            }
        });
        if(selected_promoters.contains(promoter.getEmail()))
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
    @Override
    public int getItemCount() { return promoters.size(); }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView promoter;
        public RelativeLayout relative_promoter;
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
            promoter = (TextView)itemView.findViewById(R.id.checkedTextBrandSelection);
            unchecked = (ImageView)itemView.findViewById(R.id.unchecked);
            checked = (ImageView)itemView.findViewById(R.id.checked);
            relative_promoter = (RelativeLayout)itemView.findViewById(R.id.select_brand_card);
        }

    }
}