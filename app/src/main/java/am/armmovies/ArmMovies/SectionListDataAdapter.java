package am.armmovies.ArmMovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by argishti on 1/16/17.
 */
public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.singleItemRowHolder> {

    private ArrayList<MovieModel> itemsList;
    private Context context = null;
    private DisplayImageOptions displayOptions = null;
    private ViewAnimator animator = null;
    private MovieModel singleItem = null;

    public SectionListDataAdapter(Context context, ArrayList<MovieModel> itemsList) {
        this.itemsList = itemsList;
        this.context = context;

        displayOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.image_loading)
            .showImageForEmptyUri(R.drawable.default_image)
            .showImageOnFail(R.drawable.image_loading)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();
    }

    @Override
    public singleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_view_card, null);
        singleItemRowHolder holder = new singleItemRowHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(singleItemRowHolder holder, int position) {
        singleItem = itemsList.get(position);
        holder.setMovieID(singleItem.getId());
        holder.setTitle(singleItem.getName());
        holder.setRateText(singleItem.getRate());
        ImageLoader.getInstance().displayImage(singleItem.getImageLink(),holder.getImageView(), displayOptions);

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    class singleItemRowHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView itemImage;
        private TextView rateText = null;
        private int movieID;

        singleItemRowHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.movie_title);
            itemImage = (ImageView) view.findViewById(R.id.itemImage);
            rateText = (TextView) view.findViewById(R.id.rate_text);
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    SharedPreferences preferences
                            = context.getSharedPreferences(ConstantsContainer.preferencesName, MODE_PRIVATE);
                    if(preferences.getBoolean("ANIMATE", true)) {
                        animator = ViewAnimator.getInstance();
                        animator.setView(v);
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if(preferences.getBoolean("ANIMATE", true)) {
                                animator.getSpring().setEndValue(0.6f);
                            }
                            return true;
                        case MotionEvent.ACTION_UP:
                            if(preferences.getBoolean("ANIMATE", true)) {
                                animator.getSpring().setEndValue(0f);
                            }
                            MovieModel m = SearchHelper.getInstance().getMovieById(getMovieID());
                            Intent intent = new Intent(context, DetailedActivity.class);
                            intent.putExtra("ATTRIBUTES", getAttributes(m, v));
                            context.startActivity(intent);
                            return true;
                        case MotionEvent.ACTION_CANCEL:
                            if(preferences.getBoolean("ANIMATE", true)) {
                                animator.getSpring().setEndValue(0f);
                            }
                            return true;
                        case MotionEvent.ACTION_SCROLL:
                            if(preferences.getBoolean("ANIMATE", true)) {
                                animator.getSpring().setEndValue(0f);
                            }
                            return true;

                    }
                    return false;
                }
            });
        }

        private Bundle getAttributes(MovieModel m, View imageView) {
            SharedPreferences preferences
                    = context.getSharedPreferences(ConstantsContainer.preferencesName, MODE_PRIVATE);
            Bundle b = new Bundle();
            Bundle values = new Bundle();
            int[] screenLocation = new int[2];
            imageView.getLocationOnScreen(screenLocation);
            b.putBoolean("RUN_SHARED_ANIMATION", preferences.getBoolean("ANIMATE", true));
            b.putBoolean("RUN_REVEAL_ANIMATION", preferences.getBoolean("ANIMATE", true));
            b.putInt("ID", m.getId());
            b.putString("NAME", m.getName());
            b.putString("DESCRIPTION", m.getDescription());
            b.putString("MOVIELINK", m.getMovieLink());
            b.putString("IMAGELINK", m.getImageLink());
            b.putInt("VIEWSCOUNT", m.getViewsCount());
            b.putString("YEAR", m.getYear());
            b.putString("RATE", m.getRate());
            values.putInt("LOCATION_LEFT", screenLocation[0]);
            values.putInt("LOCATION_TOP", screenLocation[1]);
            values.putInt("WIDTH", imageView.getWidth());
            values.putInt("HEIGHT", imageView.getHeight());
            b.putBundle("STARTVALUES", values);
            return b;
        }

        public void setTitle(String title) {
            tvTitle.setText(title);
        }
        public String getTitle() {
            return tvTitle.getText().toString();
        }
        public void setMovieID(int id) {
            movieID = id;
        }
        public int getMovieID() {
            return movieID;
        }
        public ImageView getImageView() {
            return this.itemImage;
        }

        public void setRateText(String rate) {
            rateText.setText(rate);
        }
    }

}
