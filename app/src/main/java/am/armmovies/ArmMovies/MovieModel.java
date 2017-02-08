package am.armmovies.ArmMovies;

/**
 * Created by argishti on 1/15/17.
 */

public class MovieModel {
    private String name = null;
    private String description = null;
    private String linkMovie = null;
    private int viewsCount;
    private String year = null;
    private String imageLink = null;
    private String rate  = null;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieLink(String linkMovie) {
        this.linkMovie = linkMovie;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setImageLink(String image) {
        this.imageLink = image;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMovieLink() {
        return linkMovie;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public String getYear() {
        return year;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getRate() {
        return rate;
    }
}
