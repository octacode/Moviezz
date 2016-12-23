package octacode.allblue.code.moviezz;

/**
 * Created by shasha on 23/12/16.
 */

public class MovieInfo {
    String name;
    int thumbnail;

    public MovieInfo(String name,int thumbnail){
        this.name=name;
        this.thumbnail=thumbnail;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
