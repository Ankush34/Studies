package in.co.khuranasales.khuranasales;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

public class CategoryFragment extends Fragment implements EventClickListener{
    public ArrayList<SubCategory> subCategories = new ArrayList<SubCategory>();
    public  RecyclerView recycler_subcategory;
    public  LinearLayoutManager layoutManager;
    public  CategorizeAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.category_fragment_layout,container,false);
        recycler_subcategory = (RecyclerView)rootView.findViewById(R.id.categorize_recycler_view);
        layoutManager= new LinearLayoutManagerWithSmoothScroller(this.getActivity().getApplicationContext());
        recycler_subcategory.setHasFixedSize(true);
        recycler_subcategory.setLayoutManager(layoutManager);
        adapter = new CategorizeAdapter(this.getActivity(),this.subCategories);
        recycler_subcategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onItemClick(View view, int position) {
        if(subCategories.get(position).name.equals("Brand"))
        {

        }
    }
}
