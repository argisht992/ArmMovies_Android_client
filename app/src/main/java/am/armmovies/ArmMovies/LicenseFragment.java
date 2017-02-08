package am.armmovies.ArmMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by argishti on 2/5/17.
 */

public class LicenseFragment extends Fragment {

    private MainActivity parentActivity = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_license, null, false);
        parentActivity = (MainActivity)getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity.getSupportActionBar().setTitle(R.string.license);
        parentActivity.setCurentFragmet(this);
    }

}
