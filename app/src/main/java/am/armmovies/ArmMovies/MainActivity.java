package am.armmovies.ArmMovies;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements JsonListener,
        HttpRequestListener {

    private HttpRequestTask httpRequestTask = null;
    private ArrayList<MovieModel> movieModels = null;
    private Fragment currentFragment = null;
    private MenuItem searchItem = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpRequestTask = new HttpRequestTask(this);
        httpRequestTask.execute();
        //setTheme(R.style.AppTheme_Dark);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onComplete(String jsonString) {
        JsonParser parser =  new JsonParser(jsonString);
        movieModels = parser.parse();
        SearchHelper.getInstance().setAllData(movieModels);
        DataMaker dataMaker = new DataMaker();
        ArrayList<ArrayList<MovieModel>> allData = dataMaker.sort(movieModels);
        renderView(allData);
    }

    private void renderView(ArrayList<ArrayList<MovieModel>> allData) {
        ArrayList<SectionViewModel> sections = new ArrayList<>();
        SectionViewModel firstSection = new SectionViewModel();
        firstSection.setSectionTitle("New Releases");
        firstSection.setAllMoviesInSection(allData.get(0));
        sections.add(firstSection);
        SectionViewModel secondSection = new SectionViewModel();
        secondSection.setSectionTitle("Top Movies");
        secondSection.setAllMoviesInSection(allData.get(1));
        sections.add(secondSection);
        RecyclerView mainRecView = (RecyclerView) findViewById(R.id.recycler_view_sections);
        mainRecView.setHasFixedSize(true);
        SectionRecyclerViewAdapter adapter = new SectionRecyclerViewAdapter(this, sections);
        mainRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainRecView.setAdapter(adapter);
        searchItem.setEnabled(true);
    }

    @Override
    public void onConectionFail() {
        httpRequestTask.cancel(false);
        httpRequestTask = null;
        final AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle(R.string.error);
        adb.setMessage(R.string.unable_connect_server);
        adb.setPositiveButton(R.string.reload, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                httpRequestTask = new HttpRequestTask(MainActivity.this);
                httpRequestTask.execute();
            }
        });
        adb.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adb.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(currentFragment instanceof SearchFragment) {
                    ((SearchFragment)currentFragment).onQueryChanged(newText);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                final Fragment searchFragment = new SearchFragment();
                MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment_container,searchFragment)
                                .commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        getSupportFragmentManager().beginTransaction()
                                .remove(searchFragment)
                                .commit();
                        getSupportFragmentManager().executePendingTransactions();
                        return true;
                    }
                });
                break;

            case R.id.settings:
                searchItem.collapseActionView();
                Fragment settingsFragment = new SettingsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container,settingsFragment)
                        .commit();
                getSupportFragmentManager().executePendingTransactions();
                break;
        }
        return true;
    }

    public void setCurentFragmet(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    @Override
    public void onBackPressed() {
        if(currentFragment instanceof SettingsFragment) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
