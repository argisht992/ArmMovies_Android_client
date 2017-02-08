package am.armmovies.ArmMovies;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by argishti on 1/22/17.
 */
///Singletone Class

public class SearchHelper {

    private static SearchHelper instance = null;
    private ArrayList<MovieModel> allData = null;
    private HashMap<String, String> translitMap = null;

    private SearchHelper() {
        initTranslitMap();
    }

    public static SearchHelper getInstance() {
        if(instance==null) {
            instance = new SearchHelper();
        }
        return instance;
    }

    public void setAllData(ArrayList<MovieModel> allData) {
        this.allData = allData;
    }

    public MovieModel getMovieByTitle(final String title) {
        for (MovieModel p: allData) {
            if(p.getName().equalsIgnoreCase(title)) {
                return p;
            }
        }
        return null;
    }

    public MovieModel getMovieById(final int id) {
        for (MovieModel p: allData) {
            if(id == p.getId()) {
                return p;
            }
        }
        return null;
    }

    public HashSet<String> getMovieByMatchName(String subStringName) {
        HashSet<String> tmp = new HashSet<>();
        for (int i = 0; i < 2; ++i) {
            Pattern pattern = Pattern.compile(subStringName, Pattern.CASE_INSENSITIVE);
            for (int j = 0; j < allData.size(); ++j) {
                Matcher matcher = pattern.matcher(allData.get(j).getName());
                if(matcher.find()) {
                    tmp.add(allData.get(j).getName());
                }
            }
            subStringName = getArmenianMatchedWord(subStringName);
        }
        return tmp;
    }

    private String getArmenianMatchedWord(String word) {
        String armenianWord = "";
        word = word.trim();
        if(word.matches("[a-zA-Z0-9@]*") && null != translitMap) {
                word = word.replaceAll("sh|sH", "շ");
                word = word.replaceAll("dz|dZ", "ձ");
                word = word.replaceAll("ch|cH", "չ");
                word = word.replaceAll("vo|vO", "ո");
                word = word.replaceAll("gh|gH", "ղ");
                word = word.replaceAll("ev|eV", "և");
                word = word.replaceAll("SH|Sh", "Շ");
                word = word.replaceAll("DZ|Dz", "Ձ");
                word = word.replaceAll("ch|Ch", "Չ");
                word = word.replaceAll("vo|Vo", "Ո");
                word = word.replaceAll("GH|Gh", "Ղ");
                word = word.replaceAll("@", "ը");

                for (int i = 0; i < word.length(); ++i) {
                    if ((String.valueOf(word.charAt(i))).matches("[a-zA-z]")) {
                        String arm = translitMap.get(String.valueOf(word.charAt(i)));
                        armenianWord += (null != arm) ? arm : "";
                    } else {
                        armenianWord += String.valueOf(word.charAt(i));
                    }
                }
                Log.d("regexp", "match " + armenianWord);
            } else {
                armenianWord = word;
            }
        return armenianWord;

    }

    public void initTranslitMap() {
        /// issues  պ-փ չ-ճ ծ-ց տ-թ ղ-խ
        translitMap = new HashMap<>();
        translitMap.put("a","ա"); translitMap.put("A","Ա");
        translitMap.put("b","բ"); translitMap.put("B","Բ");
        translitMap.put("g","գ"); translitMap.put("G","Գ");
        translitMap.put("d","դ"); translitMap.put("D","Դ");
        translitMap.put("e","ե"); translitMap.put("E","Ե");
        translitMap.put("z","զ"); translitMap.put("Z","Զ");
        translitMap.put("e","է"); translitMap.put("E","Է");
       // translitMap.put("@","ը"); translitMap.put("@","Ը");
       //// translitMap.put("t","թ"); translitMap.put("T","Թ");
        translitMap.put("j","ժ"); translitMap.put("J","Ժ");
        translitMap.put("i","ի"); translitMap.put("I","Ի");
        translitMap.put("l","լ"); translitMap.put("L","Լ");
        translitMap.put("x","խ"); translitMap.put("X","Խ");
        translitMap.put("c","ծ"); translitMap.put("C","Ծ");
        translitMap.put("k","կ"); translitMap.put("K","Կ");
        translitMap.put("h","հ"); translitMap.put("H","Հ");
        translitMap.put("dz","ձ"); translitMap.put("DZ","Ձ");
        ///translitMap.put("x","ղ"); translitMap.put("X","Ղ");
        ///translitMap.put("ch","ճ"); translitMap.put("CH","Ճ");
        translitMap.put("m","մ"); translitMap.put("M","Մ");
        translitMap.put("y","յ"); translitMap.put("Y","Յ");
        translitMap.put("n","ն"); translitMap.put("N","Ն");
        //translitMap.put("sh","շ"); translitMap.put("SH","Շ");
       // translitMap.put("vo","ո"); translitMap.put("VO","Ո");
       // translitMap.put("ch","չ"); translitMap.put("CH","Չ");
        translitMap.put("p","պ"); translitMap.put("P","Պ");
        ///translitMap.put("j","ջ"); translitMap.put("J","Ջ");
        translitMap.put("r","ռ"); translitMap.put("R","Ռ");
        translitMap.put("s","ս"); translitMap.put("S","Ս");
        translitMap.put("v","վ"); translitMap.put("V","Վ");
        translitMap.put("t","տ"); translitMap.put("T","Տ");
        translitMap.put("r","ր"); translitMap.put("R","Ր");
        translitMap.put("c","ց"); translitMap.put("C","Ց");
        translitMap.put("u","ու"); translitMap.put("U","ՈՒ");
       //// translitMap.put("p","փ"); translitMap.put("P","Փ");
        translitMap.put("q","ք"); translitMap.put("Q","Ք");
        //translitMap.put("ev","և"); translitMap.put("EV","և");
        translitMap.put("o","ո"); translitMap.put("O","Ո");
        translitMap.put("f","ֆ"); translitMap.put("F","Ֆ");
    }

}
