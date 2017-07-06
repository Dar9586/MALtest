package com.company.utils;

import com.company.entries.Anime;
import com.company.entries.Entry;
import com.company.entries.Manga;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stopp on 28/06/2017.
 */
public  class MALUtils {
    public static String getDescription(int id,boolean anime){
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://myanimelist.net/"+(anime?"anime":"manga")+"/"+id).openConnection();
            BufferedReader in = new BufferedReader (new InputStreamReader(connection.getInputStream()));
            StringBuilder fina=new StringBuilder();
            String line;
            while (!(line = in.readLine()).contains("/manifest.json")) {
                fina.append(line);
            }
            int j=fina.indexOf("og:description")+25;
            return fina.substring(j,fina.indexOf("\"",j));
            //System.out.println("Sub3:"+(System.nanoTime()-t));
        } catch(Exception e) {}
        return "";
    }
    public static List<Entry>[] getEntries(String user, boolean anime){
        String[]tempxmls=getList(user,anime).split(anime?"</anime>":"</manga>");
        List<Entry>[] list= new List[]{new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()};
        List<Integer>nums=new ArrayList<>(Arrays.asList(1,2,3,4,6));
        for(int a=0;a<tempxmls.length-1;a++){
            list[nums.indexOf(Integer.parseInt(Entry.findTagValue("my_status",tempxmls[a])))].add(anime?new Anime(tempxmls[a]):new Manga(tempxmls[a]));
        }
        return  list;
    }
    public static String getList(String user,Boolean anime){
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL ("https://myanimelist.net/malappinfo.php?u="+user+"&status=all&type="+(anime?"anime":"manga")).openConnection();
            BufferedReader in = new BufferedReader (new InputStreamReader (connection.getInputStream()));
            //String line;
            StringBuilder fina=new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                fina.append(line);
            }
            //System.out.println("Sub3:"+(System.nanoTime()-t));
            return fina.toString();
        } catch(Exception e) {}
        return "";
    }

    public static String IndentXml(String x){
        return IndentXml(new StringBuilder(x));
    }
    public static String IndentXml(StringBuilder fina){
        int ol=0,l;
        Boolean closed=false;
        while((l=fina.indexOf("><",ol))>ol){
            if(fina.charAt(l+2)!='/') {
                closed=false;
                fina.insert(l + 1, '\n');
            }else{
                if(closed){
                    fina.insert(l + 1, '\n');
                }
                closed=true;
            }

            ol=l+2;}
        return fina.toString();
    }
}
