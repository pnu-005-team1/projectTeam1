package com.pnu.cse.wedteam.springboot.domain;

public class ParamParsing {
    private String msg;
    private String msg2;

    public ParamParsing(String msg, String msg2) {
        this.msg = msg;
        this.msg2 = msg2;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg) {
        this.msg2 = msg;
    }
}
