package kr.ac.pusan.cse.category5.UserModel;

public class Category {
    private String Name;
    private String Image;

    public Category(){

    }
    public Category(String name, String image){
        Name = name;
        Image = image;
    }
    public String getName(){
        return Name;
    }
    public String getImage(){
        return Image;
    }
}
