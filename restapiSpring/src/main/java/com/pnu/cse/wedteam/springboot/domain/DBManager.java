package com.pnu.cse.wedteam.springboot.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pnu.cse.wedteam.springboot.domain.restarant;
import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    Connection con;
    public DBManager(String dbname) {
        String driver   = "org.mariadb.jdbc.Driver";
        String url      = "jdbc:mariadb://localhost:3306/"+dbname+"db";
        String uId      = "root";
        String uPwd     = "12qwaszx";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("드라이버 로드 실패");
        }
        try {
            con = DriverManager.getConnection(url, uId, uPwd);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("fail to connect to DB");
        }
    }
    public String SelectAll(String dbname){
        String sql = "select * from " + dbname +"db." + dbname + "table";
        ArrayList<restarant> jArray = new ArrayList<restarant>();

        try {
            PreparedStatement pstmt =  con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                jArray.add(new restarant(rs.getString("열 1"), rs.getString("열 2"), rs.getString("열 3"), rs.getString("열 5"), rs.getString("열 4")));
            }
        } catch (SQLException e){
            System.out.println("quary excute fail");
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(jArray);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    public String SelectAll2(String dbname){
        String sql = "select * from " + dbname +"db." + dbname + "table";
        ArrayList<user_values> jArray = new ArrayList<user_values>();

        try {
            PreparedStatement pstmt =  con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                jArray.add(new user_values(rs.getString("id"), rs.getString("food"), rs.getString("value")));
            }
        } catch (SQLException e){
            System.out.println("quary excute fail");
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = mapper.writeValueAsString(jArray);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
    public String InsertId(String dbname, String id, String password){
        String sql = "insert " + " into " + dbname +"table value(\"" + id + "\", \"" + password + "\")";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
        }
        catch (SQLException e){
            return "fail";
        }
        return "Success";
    }
    public String Insertvalues(String dbname, String value, String uid, String restname){
        String sql = "insert " + " into " + dbname +"table value(\"" + uid + "\", \"" + restname + "\", \"" + value + "\")";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
        }
        catch (SQLException e){
            return "fail";
        }
        return "Success";
    }

}