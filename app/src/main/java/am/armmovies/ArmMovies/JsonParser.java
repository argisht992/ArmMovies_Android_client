package am.armmovies.ArmMovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by argishti on 1/15/17.
 */

public class JsonParser {
    private String jsonString = null;
    private ArrayList<MovieModel> movieModels = null;
    JsonParser(String jsonString) {
        this.jsonString = jsonString;
    }

    public ArrayList<MovieModel> parse() {
        movieModels = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); ++i) {
                JSONObject tmpObj = array.getJSONObject(i);
                MovieModel model = new MovieModel();
                model.setId(tmpObj.getInt("id"));
                model.setName(tmpObj.getString("name"));
                model.setDescription(tmpObj.getString("description"));
                model.setMovieLink(tmpObj.getString("link"));
                model.setImageLink(ConstantsContainer.imageLink + tmpObj.getString("link") + ConstantsContainer.image);
                model.setRate(tmpObj.getString("rate"));
                model.setViewsCount(tmpObj.getInt("views"));
                model.setYear(tmpObj.getString("year"));
                movieModels.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.movieModels;
    }
}
