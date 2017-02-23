package am.armmovies.ArmMovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by argishti on 2/5/17.
 */

public class SettingsListAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater = null;
    private String[] titles = null;
    private Context context = null;

    public SettingsListAdapter(Context context, String[] titles) {
        super(context, R.layout.setting_list_item, titles);
        this.titles = titles;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.setting_list_item, null, false);
        TextView title = (TextView) view.findViewById(R.id.settings_main_text);
        TextView subTitle = (TextView) view.findViewById(R.id.settings_secondary_text);
        Switch sswitch = (Switch) view.findViewById(R.id.settings_switch);
        SharedPreferences preferences = null;
        switch (titles[position]) {
            case "Night Mode":
                preferences
                        = context.getSharedPreferences(
                        ConstantsContainer.preferencesName, Context.MODE_PRIVATE);
                title.setText(titles[position]);
                sswitch.setVisibility(View.VISIBLE);
                sswitch.setChecked(preferences.getBoolean("NIGHT_MODE", false));
                break;
            case "Animation":
                preferences
                        = context.getSharedPreferences(
                        ConstantsContainer.preferencesName, Context.MODE_PRIVATE);
                title.setText(titles[position]);
                sswitch.setVisibility(View.VISIBLE);
                sswitch.setChecked(preferences.getBoolean("ANIMATE", true));
                break;
            case "Version":
                title.setText(titles[position]);
                try {
                    PackageInfo packageInfo = context.getPackageManager()
                            .getPackageInfo(context.getPackageName(), 0);
                    subTitle.setText(packageInfo.versionName);
                    subTitle.setVisibility(View.VISIBLE);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                title.setText(titles[position]);
                break;
        }
        return view;
    }
}
