package com.example.taazakhabar;

public class Headline {
    private String imageUrl, title, description, time,url;
    public Headline(String iu, String t, String d,String ti, String u){
        imageUrl=iu;
        title=t;
        description=d;
        time=ti;
        url=u;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getTime(){
        return time;
    }
    public String getUrl(){ return url;}
}
