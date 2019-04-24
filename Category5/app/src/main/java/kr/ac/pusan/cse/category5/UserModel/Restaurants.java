package kr.ac.pusan.cse.category5.UserModel;

public class Restaurants {
    private String Name, Image, Location, Information_1, Information_2, Description;

    public Restaurants(){

    }
    public Restaurants(String name, String image, String description, String loc, String information_1, String information_2){
        Name = name;
        Image = image;
        Location = loc;
        Information_1 = information_1;
        Information_2 = information_2;
        Description = description;
    }
    public String getName(){
        return Name;
    }

    public void setName(String name){
        Name = name;
    }
    public String getImage(){
        return Image;
    }
    public void setImage (String image){
        Image = image;
    }
    public String getLocation( ){
        return Location;
    }
    public void setLocation(String loc){
        Location = loc;
    }
    public String getInformation_1(){
        return Information_1;
    }
    public void setInformation_1(String information_1){
        Information_1=information_1;
    }

    public String getInformation_2() {
        return Information_2;
    }
    public void setInformation_2(String information_2){
        Information_2=information_2;
    }
    public String getDescription(){
        return Description;
    }
    public void setDescription(String description){
        Description=description;
    }
}
