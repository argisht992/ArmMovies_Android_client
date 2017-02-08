package am.armmovies.ArmMovies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by argishti on 1/17/17.
 */

public class DataMaker {
    //sort
    private ArrayList<ArrayList<MovieModel>> allData = null;

    public ArrayList<ArrayList<MovieModel>> sort(ArrayList<MovieModel> arrayList) {
        ArrayList<MovieModel> objCopy = new ArrayList<>(arrayList);
        allData = new ArrayList<>();
        allData.add(objCopy);
        allData.add(sortByView(arrayList));
        /// can add other sorted lists
        return allData;
    }

    private ArrayList<MovieModel> sortByView(ArrayList<MovieModel> arrayList) {
        Collections.sort(arrayList, new Comparator<MovieModel>() {
            @Override
            public int compare(MovieModel o1, MovieModel o2) {
                int v = 0;
                if(o1.getViewsCount() > o2.getViewsCount()) {
                    v = -1;
                }
                if(o1.getViewsCount() < o2.getViewsCount()) {
                    v = 1;
                }
                return v;
            }
        });
        return arrayList;
    }
}
