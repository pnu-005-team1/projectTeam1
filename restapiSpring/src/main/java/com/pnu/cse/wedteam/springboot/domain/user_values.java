package com.pnu.cse.wedteam.springboot.domain;

public class user_values {
    private String id;
    private String restname;
    private String value;

    public user_values(String id, String restname, String value){
        this.id = id;
        this.restname = restname;
        this.value = value;
    }
    public String getid(){
        return this.id;
    }
    public String getRestname(){
        return this.restname;
    }
    public String getValue(){ return this.value; }
}