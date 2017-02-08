package am.armmovies.ArmMovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

/**
 * Created by argishti on 2/5/17.
 */

public class SettingsFragment extends Fragment implements AdapterView.OnItemClickListener{

    private MainActivity parentActivity = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null, false);
        parentActivity = (MainActivity)getActivity();
        ListView settingsList = (ListView) view.findViewById(R.id.settings_list_view);
        settingsList.setOnItemClickListener(this);
        String[] titles = parentActivity.getResources().getStringArray(R.array.settings);
        ArrayAdapter<String> adapter = new SettingsListAdapter(parentActivity, titles);
        settingsList.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        parentActivity.getSupportActionBar().setTitle(R.string.settings);
        parentActivity.setCurentFragmet(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        parentActivity.getSupportActionBar().setTitle(R.string.app_name);
        parentActivity.setCurentFragmet(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Switch onOff = null;
        switch (position) {
            /*case 0: // Night Mode On/Off
                onOff = (Switch) view.findViewById(R.id.settings_switch);
                onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SharedPreferences preferences
                                = parentActivity.getSharedPreferences(
                                ConstantsContainer.preferencesName, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("NIGHT_MODE", isChecked);
                        editor.commit();
                    }
                });
                onOff.toggle();
                break;*/
            case 0: // Animation On/Of
                onOff = (Switch) view.findViewById(R.id.settings_switch);
                onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        SharedPreferences preferences
                                = parentActivity.getSharedPreferences(
                                ConstantsContainer.preferencesName, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("ANIMATE", isChecked);
                        editor.commit();
                    }
                });
                onOff.toggle();
                break;
            case 1: // License
                Fragment licenseFragment = new LicenseFragment();
                parentActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_fragment_container, licenseFragment)
                        .commit();
                break;
            case 3: //Share this app
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TITLE,
                        parentActivity.getResources().getString(R.string.app_name));
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        parentActivity.getResources().getString(R.string.play_store_link));
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent,"Share via"));
                break;
            default: // no clickable (Version)
                break;
        }
    }
}
