package octacode.allblue.code.moviezz;

/**
 * Created by shasha on 10/1/17.
 */

public class Utility {
    public static String getGenreName(int id){
        switch (id){
            case 28 :
                return "Action";
            case 12 :
                return "Adventure";
            case 16 :
                return "Animation";
            case 35 :
                return "Comedy";
            case 80 :
                return "Crime";
            case 99 :
                return "Documentary";
            case 18 :
                return "Drama";
            case 10751 :
                return "Family";
            case 14 :
                return "Fantasy";
            case 36 :
                return "History";
            case 27 :
                return "Horror";
            case 10472 :
                return "Music";
            case 9648 :
                return "Mystery";
            case 10749 :
                return "Romance";
            case 878 :
                return "Science Fiction";
            case 10770 :
                return "TV Movie";
            case 53 :
                return "Thriller";
            case 10752 :
                return "War";
            case 37 :
                return "Western";
        }
        return "";
    }
}
