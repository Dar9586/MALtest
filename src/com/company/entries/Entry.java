package com.company.entries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by atopp on 28/06/2017.
 */
public abstract class Entry {
    protected String title,synonyms,imageURL,XML;
    protected int id,type,status,score,mystatus;
    protected boolean rewatch;
    protected Date start,finish,mystart,myfinish;
    protected String findTagValue(String tag){
        return XML.substring(XML.indexOf('<'+tag+'>')+tag.length()+2,XML.indexOf("</"+tag+'>'));
    }
    public static String findTagValue(String tag,String XML){
        return XML.substring(XML.indexOf('<'+tag+'>')+tag.length()+2,XML.indexOf("</"+tag+'>'));
    }
    public Entry(String xml){
        XML=xml;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = format.parse(findTagValue("series_start"));
            finish = format.parse(findTagValue("series_end"));
            mystart = format.parse(findTagValue("my_start_date"));
            myfinish = format.parse(findTagValue("my_finish_date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        title=findTagValue("series_title");
        synonyms=findTagValue("series_synonyms");
        imageURL=findTagValue("series_image");

        type=Integer.parseInt(findTagValue("series_type"));

        status=Integer.parseInt(findTagValue("series_status"));

        score=Integer.parseInt(findTagValue("my_score"));
        mystatus=Integer.parseInt(findTagValue("my_status"));

        loadMore();
    }
    protected void loadMore(){}
    public int     getID         (){return id;}
    public String  getTitle      (){return title;}
    public String  getSynonyms   (){return synonyms;}
    public int     getType       (){return type;}
    public int     getStatus     (){return status;}
    public Date    getStart      (){return start;}
    public Date    getFinish     (){return finish;}
    public String  getImageURL   (){return imageURL;}
    public Date    getMyStart    (){return mystart;}
    public Date    getMyFinish   (){return myfinish;}
    public int     getScore      (){return score;}
    public int     getMyStatus   (){return mystatus;}
    public boolean getRewatch    (){return rewatch;}
}
