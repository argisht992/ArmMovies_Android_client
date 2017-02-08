package am.armmovies.ArmMovies;

import android.view.View;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

/**
 * Created by argishti on 1/18/17.
 */

public class ViewAnimator implements SpringListener {

    private Spring spring = null;
    private static ViewAnimator instance = null;
    private View view = null;

    private ViewAnimator() {
        SpringSystem springSystem = SpringSystem.create();
        spring = springSystem.createSpring();
        spring.addListener(this);
    }

    public static ViewAnimator getInstance() {

        if(instance == null) {
            instance = new ViewAnimator();
        }
        return instance;
    }

    @Override
    public void onSpringUpdate(Spring spring) {
        float value = (float) spring.getCurrentValue();
        float scale = 1f - (value * 0.5f);
        view.setScaleX(scale);
        view.setScaleY(scale);
    }

    public void setView(View view) {
        this.view = view;
    }

    public Spring getSpring() {
        return spring;
    }

    @Override
    public void onSpringAtRest(Spring spring) {

    }

    @Override
    public void onSpringActivate(Spring spring) {

    }

    @Override
    public void onSpringEndStateChange(Spring spring) {

    }
}
