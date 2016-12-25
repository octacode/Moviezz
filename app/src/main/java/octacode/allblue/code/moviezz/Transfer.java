package octacode.allblue.code.moviezz;

/**
 * Created by shasha on 26/12/16.
 */

public class Transfer {
    private String id;
    private String orgLang;
    private String orgTitle;
    private String overview;
    private String relDate;
    private String postURL;
    private String popularity;
    private String votAvg;

    public Transfer(String postURL,String id,String orgLang,String overview,String relDate,String popularity,String votAvg,String orgTitle){
        this.postURL=postURL;
        this.id=id;
        this.orgLang=orgLang;
        this.overview=overview;
        this.relDate=relDate;
        this.popularity=popularity;
        this.votAvg=votAvg;
        this.orgTitle=orgTitle;
    }

    public Transfer() {

    }

    public String getId() {
        return id;
    }

    public String getOrgLang() {
        return orgLang;
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

    public String getRelDate() {
        return relDate;
    }

    public String getVotAvg() {
        return votAvg;
    }

    public String getPostURL() {
        return postURL;
    }
}
