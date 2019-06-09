package com.pnu.cse.wedteam.springboot.controller;

import com.pnu.cse.wedteam.springboot.domain.ParamParsing;
import org.springframework.web.bind.annotation.*;
import com.pnu.cse.wedteam.springboot.domain.DBManager;

@RestController
@RequestMapping(value = "param")
public class ParamController {
    @GetMapping()
    public ParamParsing get(){
        return new ParamParsing("I'm alive", "w");
    }
    @GetMapping(value = "args")
    public String getArgs(
            @RequestParam(value = "dbname")String dbname,
            @RequestParam(value = "write")String write,
            @RequestParam(value = "uid", required = false, defaultValue = "novalue")String uid,
            @RequestParam(value = "restname", required = false, defaultValue = "novalue")String restname) {
        String result = new String();

        if (dbname.equals("p_id")) {
            DBManager connector = new DBManager("p_id");
            if(write.equals("r")){
                result = connector.SelectAll2("p_id");
            } else{
                result = connector.InsertId("pid", uid, write);
            }
        } else if (dbname.equals("review")) {
            DBManager connector = new DBManager("review");
            if(write.equals("r")){
                result = connector.SelectAll2("review");
            } else {
                result = connector.Insertvalues("review", write, uid, restname);
            }
        } else if (dbname.equals("star")) {
            DBManager connector = new DBManager("star");
            if(write.equals("r")){
                result = connector.SelectAll2("star");
            } else {
                result = connector.Insertvalues("star", write, uid, restname);
            }
        } else if (dbname.equals("favorite")) {
            DBManager connector = new DBManager("favorite");
            if(write.equals("r")){
                result = connector.SelectAll2("favorite");
            }else{
                result = connector.Insertvalues("favorite", write, uid, restname);
            }
        } else if (dbname.equals("sam") && write.equals("r")) {
            DBManager connector = new DBManager("sam");
            result = connector.SelectAll("sam");
        } else if (dbname.equals("neng") && write.equals("r")) {
            DBManager connector = new DBManager("neng");
            result = connector.SelectAll("neng");
        } else if (dbname.equals("bul") && write.equals("r")) {
            DBManager connector = new DBManager("bul");
            result = connector.SelectAll("bul");
        } else if (dbname.equals("mil") && write.equals("r")) {
            DBManager connector = new DBManager("mil");
            result = connector.SelectAll("mil");
        } else if (dbname.equals("guk") && write.equals("r")) {
            DBManager connector = new DBManager("guk");
            result = connector.SelectAll("guk");
        } else if (dbname.equals("gim") && write.equals("r")) {
            DBManager connector = new DBManager("gim");
            result = connector.SelectAll("gim");
        } else if (dbname.equals("cho") && write.equals("r")) {
            DBManager connector = new DBManager("cho");
            result = connector.SelectAll("cho");
        } else if (dbname.equals("zza") && write.equals("r")) {
            DBManager connector = new DBManager("zza");
            result = connector.SelectAll("zza");
        } else if (dbname.equals("zzam") && write.equals("r")) {
            DBManager connector = new DBManager("zzam");
            result = connector.SelectAll("zzam");
        } else if (dbname.equals("do") && write.equals("r")) {
            DBManager connector = new DBManager("do");
            result = connector.SelectAll("do");
        } else {
            return "fail";
        }
        return result;
    }
}