package octacode.allblue.code.moviezz;

/**
 * Created by shasha on 11/1/17.
 */

public class InfoTransfer {

    private String name,role,id_url,credit_id;

    public InfoTransfer(String name,String role,String id_url,String credit_id){
        this.name=name;
        this.role =role;
        this.id_url = id_url;
        this.credit_id = credit_id;
    }


    InfoTransfer(String name){
        this.name = name;
    }

    public InfoTransfer(String name, String role, String id_url){
        this.name=name;
        this.role=role;
        this.id_url=id_url;
    }

    public String getId_url() {
        return id_url;
    }

    public String getCredit_id() {return credit_id;}

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
