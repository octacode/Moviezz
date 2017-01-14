package octacode.allblue.code.moviezz;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import java.text.NumberFormat;

/**
 * Created by shasha on 10/1/17.
 */

public class Utility {

    public static String getPage(Context context){
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        return preference.getString("pref_page","1");
    }

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

    public static String getLang(String lang) {
        switch (lang) {
            case "af":
                return "Afrikaans";
            case "sq":
                return "Albanian";
            case "ar":
                return "Arabic";
            case "hy":
                return "Armenian";
            case "az":
                return "Azerbaijani";
            case "eu":
                return "Basque";
            case "be":
                return "Belarusian";
            case "bn":
                return "Bengali";
            case "bs":
                return "Bosnian";
            case "bg":
                return "Bulgarian";
            case "ca":
                return "Catalan";
            case "ceb":
                return "Cebuano";
            case "zh-CN":
                return "Chinese";
            case "hr":
                return "Croatian";
            case "cs":
                return "Czech";
            case "da":
                return "Danish";
            case "nl":
                return "Dutch";
            case "en":
                return "English";
            case "eo":
                return "Esperanto";
            case "et":
                return "Estonian";
            case "tl":
                return "Filipino";
            case "fi":
                return "Finnish";
            case "fr":
                return "French";
            case "gl":
                return "Galician";
            case "ka":
                return "Georgian";
            case "de":
                return "German";
            case "el":
                return "Greek";
            case "gu":
                return "Gujarati";
            case "ht":
                return "Haitian Creole";
            case "ha":
                return "Hausa";
            case "iw":
                return "Hebrew";
            case "hi":
                return "Hindi";
            case "hmn":
                return "Hmong";
            case "hu":
                return "Hungarian";
            case "is":
                return "Icelandic";
            case "ig":
                return "Igbo";
            case "id":
                return "Indonesian";
            case "ga":
                return "Irish";
            case "it":
                return "Italian";
            case "ja":
                return "Japanese";
            case "jw":
                return "Javanese";
            case "kn":
                return "Kannada";
            case "km":
                return "Khmer";
            case "ko":
                return "Korean";
            case "lo":
                return "Lao";
            case "la":
                return "Latin";
            case "lv":
                return "Latvian";
            case "lt":
                return "Lithuanian";
            case "mk":
                return "Macedonian";
            case "ms":
                return "Malay";
            case "mt":
                return "Maltese";
            case "mi":
                return "Maori";
            case "mr":
                return "Marathi";
            case "mn":
                return "Mongolian";
            case "ne":
                return "Nepali";
            case "no":
                return "Norwegian";
            case "fa":
                return "Persian";
            case "pl":
                return "Polish";
            case "pt":
                return "Portuguese";
            case "pa":
                return "Punjabi";
            case "ro":
                return "Romanian";
            case "ru":
                return "Russian";
            case "sr":
                return "Serbian";
            case "sk":
                return "Slovak";
            case "sl":
                return "Slovenian";
            case "so":
                return "Somali";
            case "es":
                return "Spanish";
            case "sw":
                return "Swahili";
            case "sv":
                return "Swedish";
            case "ta":
                return "Tamil";
            case "te":
                return "Telugu";
            case "th":
                return "Thai";
            case "tr":
                return "Turkish";
            case "uk":
                return "Ukrainian";
            case "ur":
                return "Urdu";
            case "vi":
                return "Vietnamese";
            case "cy":
                return "Welsh";
            case "yi":
                return "Yiddish";
            case "yo":
                return "Yoruba";
            case "zu":
                return "Zulu";
            default:
                return lang;
        }
    }

    public static String getRuntime(String runtime){
        int run = Integer.parseInt(runtime);
        int hours = run/60;
        int min = run - (hours*60);
        return hours+"h "+min+" min";
    }

    public static String getBudget(String budget){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String budget_er = formatter.format(Double.parseDouble(budget));
        return budget_er.replace(".00","");
    }
}