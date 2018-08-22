package in.co.khuranasales.khuranasales.CircularPagerIndicartor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.khuranasales.khuranasales.R;

public class FragmentPermission1 extends Fragment {

    public static FragmentPermission1 newInstance() {
        return new FragmentPermission1();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1_permission_description,container,false);
    }
}
