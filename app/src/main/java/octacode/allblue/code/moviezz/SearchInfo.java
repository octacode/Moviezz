package octacode.allblue.code.moviezz;

/**
 * Created by shasha on 28/1/17.
 */

public class SearchInfo {
    private String id;
    private String orgLang;
    private String orgTitle;
    private String overview;
    private String relDate;
    private String postURL;
    private String adult;
    private String popularity;
    private String votAvg;
    private String backdropURl;
    private String genre_ids;

    public SearchInfo(String id, String orgLang, String orgTitle, String overview, String relDate, String postURL, String adult, String popularity, String votAvg, String backdropURl, String genre_ids){
        this.id=id;
        this.orgLang=orgLang;
        this.orgTitle=orgTitle;
        this.overview=overview;
        this.relDate=relDate;
        this.postURL=postURL;
        this.adult=adult;
        this.popularity=popularity;
        this.votAvg=votAvg;
        this.backdropURl=backdropURl;
        this.genre_ids=genre_ids;
    }

    public String getId() {
        return id;
    }

    public String getOrgLang() {
        return orgLang;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public String getVotAvg() {
        return votAvg;
    }

    public String getBackdropURl() {
        return backdropURl;
    }

    public String getAdult() {
        return adult;
    }

    public String getOrgTitle() {
        return orgTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPostURL() {
        return postURL;
    }

    public String getRelDate() {
        return relDate;
    }
}
