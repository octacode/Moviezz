package octacode.allblue.code.moviezz;

/**
 * Created by shasha on 11/1/17.
 */

public class InfoTransfer {

    private String name,role,id_url;

    InfoTransfer(String name, String role){
        this.name=name;
        this.role=role;
    }

    InfoTransfer(String name, String role, String id_url){
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
