package in.co.khuranasales.khuranasales.exportWorkers;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import in.co.khuranasales.khuranasales.R;

public class select_options_export_recycler_adapter extends RecyclerView.Adapter<select_options_export_recycler_adapter.ViewHolder> {
    public ArrayList<selectionPojo> selection_list = new ArrayList<>();

    public select_options_export_recycler_adapter(Activity activity,ArrayList<selectionPojo> selection_list)
    {
        this.selection_list = selection_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_selection_card_categorization,parent,false);
       select_options_export_recycler_adapter.ViewHolder holder = new select_options_export_recycler_adapter.ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.selection_name.setText(selection_list.get(position).getSelection_text());
        if(selection_list.get(position).is_selected())
        {
            holder.checked_image.setVisibility(View.VISIBLE);
            holder.unchecked_image.setVisibility(View.INVISIBLE);
        }

        if(!selection_list.get(position).is_selected())
        {
            holder.unchecked_image.setVisibility(View.VISIBLE);
            holder.checked_image.setVisibility(View.INVISIBLE);
        }

        holder.checked_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection_list.get(position).set_selected(false);
                notifyItemChanged(position);
            }
        });

        holder.unchecked_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection_list.get(position).set_selected(true);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
       return selection_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView checked_image;
        public ImageView unchecked_image;
        public TextView selection_name;
        public ViewHolder(View itemView) {

            super(itemView);
            checked_image = (ImageView)itemView.findViewById(R.id.checked);
            unchecked_image = (ImageView)itemView.findViewById(R.id.unchecked);
            selection_name = (TextView)itemView.findViewById(R.id.checkedTextBrandSelection);
        }
    }
}
