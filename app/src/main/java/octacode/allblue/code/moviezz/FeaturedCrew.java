package octacode.allblue.code.moviezz;

/**
 * Created by shasha on 11/1/17.
 */

public class FeaturedCrew {

    private String name,role,id_url;

    public FeaturedCrew(String name, String role){
        this.name=name;
        this.role=role;
    }

    public FeaturedCrew(String name, String role, String id_url){
        this.name=name;
        this.role=role;
        this.id_url=id_url;
    }

    public String getId_url() {
        return id_url;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
