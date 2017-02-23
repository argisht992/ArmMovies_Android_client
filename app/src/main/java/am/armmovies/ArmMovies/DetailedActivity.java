package am.armmovies.ArmMovies;

import android.animation.Animator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DetailedActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener, View.OnClickListener, YouTubePlayer.OnFullscreenListener {

    private Bundle attributes = null;
    private TextView title = null;
    private TextView year  = null;
    private TextView rate  = null;
    private TextView title_description = null;
    private YouTubePlayer youTubePlayer = null;
    private TextView description = null;
    private TextView readMore = null;
    private ImageView destinationImage = null;
    private ImageView animationImage = null;
    private boolean runSharedAnimation = false;
    private boolean runRevealAnimation = false;
    private String currentOrientation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        //setTheme(R.style.AppTheme_Dark);
        title = (TextView) findViewById(R.id.title_detailed);
        year = (TextView) findViewById(R.id.year_detailed);
        rate = (TextView) findViewById(R.id.rate_detailed);
        title_description = (TextView) findViewById(R.id.title_detailed_description);
        description = (TextView) findViewById(R.id.description_detailed);
        readMore = (TextView) findViewById(R.id.read_more_detailed);
        destinationImage = (ImageView) findViewById(R.id.image_detailed);
        animationImage = (ImageView) findViewById(R.id.animation_image_detailed);
        getAttributesFromBudle(getIntent());
        ImageView share = (ImageView) findViewById(R.id.share_detailed);
        TextView share_text = (TextView) findViewById(R.id.share_text_detailed);
        readMore.setOnClickListener(this);
        share.setOnClickListener(this);
        share_text.setOnClickListener(this);
        setResources(attributes);

        currentOrientation = "portrait";
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            attributes.putBoolean("RUN_SHARED_ANIMATION", false);
            attributes.putBoolean("RUN_REVEAL_ANIMATION", false);
            runSharedAnimation = false;
            runRevealAnimation = false;
            currentOrientation = "landscape";
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(ConstantsContainer.YouTube_API_KEY, DetailedActivity.this);
        displayImage(destinationImage, animationImage);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        youTubePlayer.cueVideo(attributes.getString("MOVIELINK"));
        this.youTubePlayer = youTubePlayer;
        youTubePlayer.setOnFullscreenListener(DetailedActivity.this);
        if(currentOrientation.equals("landscape")) {
            youTubePlayer.setFullscreen(true);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("Youtube", "Fail");
    }

    private void setResources(Bundle attr) {
        title.setText(attr.getString("NAME"));
        title_description.setText(attr.getString("NAME"));
        year.setText(attr.getString("YEAR"));
        rate.setText(
                attr.getString("RATE").equalsIgnoreCase("null")? "": attr.getString("RATE"));
        description.setText(
                attr.getString("DESCRIPTION").equalsIgnoreCase("null")? "": attr.getString("DESCRIPTION"));
        runSharedAnimation = attr.getBoolean("RUN_SHARED_ANIMATION");
        runRevealAnimation = attr.getBoolean("RUN_REVEAL_ANIMATION");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_detailed:
            case R.id.share_text_detailed:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TITLE, title.getText().toString());
                shareIntent.putExtra(Intent.EXTRA_TEXT, "www.youtube.com/watch?v="
                        + attributes.getString("MOVIELINK") + "\n"
                        + "Shared via ArmMovies Android app");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent,"Share via"));
                break;
            case R.id.read_more_detailed:
                if (description.getLineCount() > description.getMaxLines()) {
                    description.setMaxLines(description.getLineCount());
                    readMore.setText(R.string.read_less);
                } else {
                    description.setMaxLines(5);
                    readMore.setText(R.string.read_more);
                }
                break;
        }
    }

    private void getAttributesFromBudle(Intent i) {
        attributes = i.getBundleExtra("ATTRIBUTES");
    }

    private void displayImage(ImageView image, ImageView animationImage) {

        ImageLoadingListener listener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, final View view, Bitmap bitmap) {
                ((ImageView)view).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            ((ImageView)view).getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else {
                            ((ImageView)view).getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        }
                        ///run Animation
                        onUiReady();

                    }
                });
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        };
        ImageLoader.getInstance().setDefaultLoadingListener(listener);
        ImageLoader.getInstance().displayImage(attributes.getString("IMAGELINK"), image);
        ImageLoader.getInstance().displayImage(attributes.getString("IMAGELINK"), animationImage);
    }

    /// Callback function
    private void onUiReady() {
        final Bundle startValues = attributes.getBundle("STARTVALUES");
        final Bundle endValues = getAttributes(destinationImage);
        destinationImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ///remove previous (default) listeners
                if(null != startValues && runSharedAnimation) {
                    destinationImage.getViewTreeObserver().removeOnPreDrawListener(this);
                    int startX = getStartX(startValues);
                    int startY = getStartY(startValues);
                    int destinationX = getDestinationX(endValues);
                    int destinationY = getDestinationY(endValues)-60;
                    animationImage.setTranslationX(startX);
                    animationImage.setTranslationY(startY);
                    runSharedAnimation(destinationX,destinationY);
                }

                if(runRevealAnimation) {
                    runRevealAnimation();
                    runRevealAnimation = false;
//                    attributes.putBoolean("RUN_REVEAL_ANIMATION", false);
                }
                return true;
            }
        });

    }

    private Bundle getAttributes(View v) {
        final Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        v.getLocationOnScreen(screenLocation);
        b.putInt("LOCATION_LEFT", screenLocation[0]);
        b.putInt("LOCATION_TOP", screenLocation[1]);
        b.putInt("WIDTH", v.getWidth());
        b.putInt("HEIGHT", v.getHeight());
        return b;
    }

    private int getStartX(Bundle startValues) {
        return startValues.getInt("LOCATION_LEFT");
    }

    private int getStartY(Bundle startValues) {
        return startValues.getInt("LOCATION_TOP");
    }

    private int getDestinationX(Bundle endValues) {
        return endValues.getInt("LOCATION_LEFT");
    }

    private int getDestinationY(Bundle endValues) {
        return endValues.getInt("LOCATION_TOP");
    }

    private void runSharedAnimation(final int endX, final int endY) {
        animationImage.animate()
                .setDuration(1000)
                .setListener(new AnimationListener())
                .setInterpolator(new FastOutSlowInInterpolator())
                .translationX(endX)
                .translationY(endY)
                .start();

    }
    public void runRevealAnimation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_content);
                RevealAnimation.performReveal(mainLayout);
            }
        }).start();
    }

    @Override
    public void onFullscreen(boolean b) {
        int currentTime = youTubePlayer.getCurrentTimeMillis();
        if(b) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            currentOrientation = "landscape";
            if(youTubePlayer.isPlaying()) {
                youTubePlayer.seekToMillis(currentTime);
                youTubePlayer.play();
            } else {
                youTubePlayer.seekToMillis(currentTime);
            }
        } else if(!b){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            currentOrientation = "portrait";
            if(youTubePlayer.isPlaying()) {
                youTubePlayer.seekToMillis(currentTime);
                youTubePlayer.play();
            } else {
                youTubePlayer.seekToMillis(currentTime);
            }
        }
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    @Override
    public void onBackPressed() {
        try {
            if (currentOrientation.equals("landscape")) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                youTubePlayer.setFullscreen(false);
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            //for TV
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            youTubePlayer.setFullscreen(true);
        } else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            youTubePlayer.setFullscreen(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != youTubePlayer && youTubePlayer.isPlaying()) {
            youTubePlayer.pause();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(null != youTubePlayer) {
            youTubePlayer.release();
        }
    }

    private class AnimationListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            ((FrameLayout)findViewById(R.id.animation_layout_detailed)).setVisibility(View.VISIBLE);
            animationImage.setVisibility(View.VISIBLE);
            destinationImage.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ((FrameLayout)findViewById(R.id.animation_layout_detailed)).setVisibility(View.INVISIBLE);
            animationImage.setVisibility(View.INVISIBLE);
            destinationImage.setVisibility(View.VISIBLE);
            runSharedAnimation = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
