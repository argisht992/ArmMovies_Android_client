package am.armmovies.ArmMovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Locale;

/**
 * Created by argishti on 1/18/17.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.threadPoolSize(5);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
        ///Setting default languade Eng+lish
        Locale locale = new Locale("en");
        Configuration localeConfig = getBaseContext().getResources().getConfiguration();
        localeConfig.locale = locale;
        getBaseContext().getResources().updateConfiguration(localeConfig,
                getBaseContext().getResources().getDisplayMetrics());

    }
}
