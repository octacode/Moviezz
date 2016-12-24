package octacode.allblue.code.moviezz;

/**
 * Created by shasha on 23/12/16.
 */

class MovieInfo {
    private String title;
    private String image_url;

    MovieInfo(String title, String image_url){
        this.title=title;
        this.image_url=image_url;
    }

    String getTitle() {
        return title;
    }

    String getImage_url() {
        return image_url;
    }
}
