package in.co.khuranasales.khuranasales;

import android.view.View;

public interface EventClickListener
{
    //Act as a communicator between main activity and adapter to provide item click
    void onItemClick(View view, int position);
}