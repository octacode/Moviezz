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

    static String datePresenter(String date){
        if(date!=null && !date.matches("null")){
        String splits[] = date.split("-");
        if(splits.length==3){
            switch(splits[1]){
            case "01":
                splits[1]="January";
                break;
            case "02":
                splits[1]="Feburary";
                break;
            case "03":
                splits[1]="March";
                break;
            case "04":splits[1]="April";
                break;
            case "05":splits[1]="May";
                break;
            case "06":splits[1]="June";
                break;
            case "07":splits[1]="July";
                break;
            case "08":splits[1]="August";
                break;
            case "09":splits[1]="September";
                break;
            case "10":splits[1]="October";
                break;
            case "11":splits[1]="November";
                break;
            case "12":splits[1]="December";
                break;
                }
            return splits[0]+" "+splits[1]+" "+splits[2];
            }
        }
        else {
            return "";
        }
        return  "";
    }
}