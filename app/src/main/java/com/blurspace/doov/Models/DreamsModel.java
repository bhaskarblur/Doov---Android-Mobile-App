package com.blurspace.doov.Models;

public class DreamsModel {
    private String Dreamimgurl;
    private String Dreamname;
    private String Dreamfield;

    public DreamsModel() {

    }
    public DreamsModel(String Dreamimgurl,String Dreamname,String Dreamfield) {
        this.Dreamimgurl=Dreamimgurl;
        this.Dreamname=Dreamname;
        this.Dreamfield=Dreamfield;


    }
    public String getDreamimgurl() {
        return Dreamimgurl;
    }

    public void setDreamimgurl(String dreamimgurl) {
        Dreamimgurl = dreamimgurl;
    }

    public String getDreamname() {
        return Dreamname;
    }

    public void setDreamname(String dreamname) {
        Dreamname = dreamname;
    }

    public String getDreamfield() {
        return Dreamfield;
    }

    public void setDreamfield(String dreamfield) {
        Dreamfield = dreamfield;
    }
}
