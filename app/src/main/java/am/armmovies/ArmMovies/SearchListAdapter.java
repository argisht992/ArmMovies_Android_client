package am.armmovies.ArmMovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by argishti on 2/4/17.
 */

public class SearchListAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater = null;
    private ArrayList<String> movieNames = null;

    public SearchListAdapter(Context context) {
        super(context, R.layout.search_list_item);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  movieNames = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.search_list_item, null, false);
        if(null != movieNames) {
                ((TextView) view.findViewById(R.id.search_result_title)).setText(movieNames.get(position));
        }
        Log.d("listView", "Position = " + position);
        return view;
    }

    @Override
    public void addAll(Collection<? extends String> collection) {
        clear();
        if(movieNames != null) {
            movieNames.clear();
        }
        movieNames = new ArrayList<>(collection);
//        for(String name: collection) {
//            movieNames.add(name);
//        }
        super.addAll(movieNames);
        notifyDataSetChanged();
    }
}
