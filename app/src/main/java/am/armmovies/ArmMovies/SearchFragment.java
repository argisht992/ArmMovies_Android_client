package am.armmovies.ArmMovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by argishti on 2/3/17.
 */

public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ArrayAdapter<String> adapter = null;
    private SearchHelper searchHelper = null;
    private ListView resultList = null;
    private MainActivity parentActivity = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null, false);
        parentActivity = (MainActivity) getActivity();
        searchHelper = SearchHelper.getInstance();
        resultList = (ListView) view.findViewById(R.id.search_result_list);
        resultList.setOnItemClickListener(this);
        adapter = new SearchListAdapter((MainActivity)getActivity());
        resultList.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        parentActivity.setCurentFragmet(this);
    }

    public void onQueryChanged(String newText) {
        if(newText.equals("")) {
            adapter.clear();
        } else {
            adapter.addAll(searchHelper.getMovieByMatchName(newText));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = ((TextView) view.findViewById(R.id.search_result_title)).getText().toString();
        MovieModel movieModel = SearchHelper.getInstance().getMovieByTitle(name);
        Intent intent = new Intent(parentActivity, DetailedActivity.class);
        intent.putExtra("ATTRIBUTES", getAttributes(movieModel));
        parentActivity.startActivity(intent);
        //parentActivity.removeFragment(this);

    }

    private Bundle getAttributes(MovieModel m) {
        SharedPreferences preferences
                = parentActivity.getSharedPreferences(ConstantsContainer.preferencesName, MODE_PRIVATE);
        Bundle b = new Bundle();
        b.putBoolean("RUN_SHARED_ANIMATION", false);
        b.putBoolean("RUN_REVEAL_ANIMATION", preferences.getBoolean("ANIMATE", true));
        b.putInt("ID", m.getId());
        b.putString("NAME", m.getName());
        b.putString("DESCRIPTION", m.getDescription());
        b.putString("MOVIELINK", m.getMovieLink());
        b.putString("IMAGELINK", m.getImageLink());
        b.putInt("VIEWSCOUNT", m.getViewsCount());
        b.putString("YEAR", m.getYear());
        b.putString("RATE", m.getRate());
        return b;
    }
}
