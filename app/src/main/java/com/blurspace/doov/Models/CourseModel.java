package com.blurspace.doov.Models;

public class CourseModel {
    private String Courseimgurl;
    private String Coursename;
    private String Coursefield;

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

    private String fordreams;
    private String link;

    public CourseModel() {

    }
    public CourseModel(String Courseimgurl, String Coursename, String Coursefield,String fordreams,String link) {
        this.Courseimgurl=Courseimgurl;
        this.Coursename=Coursename;
        this.Coursefield=Coursefield;
        this.fordreams=fordreams;
        this.link=link;


    }
    public String getCourseimgurl() {
        return Courseimgurl;
    }

    public void setCourseimgurl(String dreamimgurl) {
        Courseimgurl = dreamimgurl;
    }

    public String getCoursename() {
        return Coursename;
    }

    public void setCoursename(String dreamname) {
        Coursename = dreamname;
    }

    public String getCoursefield() {
        return Coursefield;
    }

    public void setCoursefield(String dreamfield) {
        Coursefield = dreamfield;
    }
}
