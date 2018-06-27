package in.co.khuranasales.khuranasales;


import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


/**
 * Created by Ankush Khurana on 28/07/2017.
 */

public class BatchViewAdapter extends RecyclerView.Adapter<BatchViewAdapter.ViewHolder> {
    private Activity activity;
    public List<Batch> batches;
    public BatchViewAdapter(Activity activity, List<Batch> batches) {
        this.activity = activity;
        this.batches = batches;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        // i have used the same view as that of brand no need to create the layout again for this view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_name_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Batch batch_number = batches.get(position);
        holder.batch_number.setText((position+1)+":    "+batch_number.getNumber()+"  ("+batch_number.getLocation()+")");
    }
    @Override
    public int getItemCount() { return batches.size(); }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView batch_number;
        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                }
            });
            batch_number = (TextView)itemView.findViewById(R.id.name);
        }

    }
}