package kr.ac.pnu.cse.menumapproject.model;

public class Review {
    public String id;
    public String restname;
    public String value;

    public Review(){}

    public Review(String id, String restname, String value) {
        this.id = id;
        this.restname = restname;
        this.value = value;
    }
}
