package am.armmovies.ArmMovies;

import java.util.ArrayList;

/**
 * Created by argishti on 1/16/17.
 */
public class SectionViewModel {
    private String sectionTitle = null;
    private ArrayList<MovieModel> allMoviesInSection = null;

    SectionViewModel() {

    }

    public SectionViewModel(String sectionTitle, ArrayList<MovieModel> allMoviesInSection) {
        this.sectionTitle = sectionTitle;
        this.allMoviesInSection = allMoviesInSection;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public ArrayList<MovieModel> getAllMoviesInSection() {
        return this.allMoviesInSection;
    }

    public void setAllMoviesInSection(ArrayList<MovieModel> allMoviesInSection) {
        this.allMoviesInSection = allMoviesInSection;
    }
}