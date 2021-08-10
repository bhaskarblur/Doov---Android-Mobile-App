package com.blurspace.doov.Models;

public class PlatformModel {
    private String Platformimgurl;
    private String Platformname;
    private String Platformfield;
    private String fordreams;

    public String getFordreams() {
        return fordreams;
    }

    public void setFordreams(String fordreams) {
        this.fordreams = fordreams;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    private String link;

    public PlatformModel() {

    }
    public PlatformModel(String Platformimgurl, String Platformname, String Platformfield,String fordreams,String link) {
        this.Platformimgurl=Platformimgurl;
        this.Platformname=Platformname;
        this.Platformfield=Platformfield;
        this.fordreams=fordreams;
        this.link=link;


    }
    public String getPlatformimgurl() {
        return Platformimgurl;
    }

    public void setPlatformimgurl(String dreamimgurl) {
        Platformimgurl = dreamimgurl;
    }

    public String getPlatformname() {
        return Platformname;
    }

    public void setPlatformname(String dreamname) {
        Platformname = dreamname;
    }

    public String getPlatformfield() {
        return Platformfield;
    }

    public void setPlatformfield(String dreamfield) {
        Platformfield = dreamfield;
    }
}
