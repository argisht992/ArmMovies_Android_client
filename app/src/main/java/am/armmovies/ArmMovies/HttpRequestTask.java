package am.armmovies.ArmMovies;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by argishti on 1/11/17.
 */

public class HttpRequestTask extends AsyncTask<Void, Void, String> {
    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;
    private String resultJson = "";
    private MainActivity mainActivity = null;
    private ProgressBar progressBar = null;
    private LinearLayout linearLayout= null;

    HttpRequestTask(Activity activity) {
        mainActivity = (MainActivity) activity;
        progressBar = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
        linearLayout = (LinearLayout) mainActivity.findViewById(R.id.main_layout);
    }

    @Override
    protected void onPreExecute() {
        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(ConstantsContainer.server); //+ ":" + ConstantsContainer.serverPort);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            resultJson = buffer.toString();

        } catch (Exception e) {
            mainActivity.onConectionFail();
        }
        return resultJson;
    }

    @Override
    protected void onPostExecute(String resultJSON) {
        Log.d("onPostExecute", resultJSON);
        mainActivity.onComplete(resultJSON);
        // hide progreebar
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        super.onPostExecute(resultJSON);
    }
}
