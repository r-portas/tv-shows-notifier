package ConfigFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import FileDownloader.FileDownloader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Roy Portas
 */

public class ConfigFile {

    String foldername = "data";
    String foldercon = foldername + "/";
    
    String downloadfolder = "download";
    String downloadcon = foldercon + downloadfolder + "/";
    
    String filename = "config.json";
    public JSONObject json;
    
    long timecheck = -4l;
    
    FileDownloader fdownloader;
    
    public ConfigFile(){
        fdownloader = new FileDownloader();
    }
    
    public void loadConfig(){
        try {
            
            String data = FileUtils.readFileToString(new File(foldercon + filename));
            // Parse it into json form
            JSONParser parser = new JSONParser();
            json  = (JSONObject) parser.parse(data);
            System.out.println(json);
            
        } catch (FileNotFoundException e){
            // If the file does not exist create it
            createConfig();
        } catch (Exception e){
            System.out.println("Error in loadConfig: " + e.toString());
        }
    }
    
    public void saveConfig(){
        try {
            if (json != null){
                PrintWriter writer = new PrintWriter(foldercon + filename, "UTF-8");
                writer.print(json);
                writer.close();
            } else {
                System.out.println("Error: json file is empty");
            }
        } catch (Exception e) {
            System.out.println("Error in saveConfig: " + e.toString());
        }
    }
    
    public void setTvShows(JSONArray shows){
        try {
            json.put("shows", shows);
        } catch (Exception e) {
            System.out.println("ERROR cannot setTvShows: " + e.toString());
        }
    }
    
    public JSONArray getTvShows(){
        try {
            JSONArray shows = (JSONArray) json.get("shows");
            return shows;
        } catch (Exception e){
            System.out.println("Error getting tv shows: " + e.toString());
            JSONArray empty = new JSONArray();
            return empty;
        }
    }
    
    public int setApiKey(String key){
        // Returns 0 if good, 1 otherwise
        try {
            if (key != null){
                json.put("apikey", key);
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e){
            return 1;
        }
    }
    
    public String getApiKey(){
        if (json != null && json.get("apikey") != null){
            return json.get("apikey").toString();
        } else {
            return "null";
        }
    }
    
    public ArrayList getShowEpisodes(){
        ArrayList showeps = new ArrayList();
        JSONArray shows = getTvShows();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        
        for(Object show : shows){
            JSONObject jshow = (JSONObject) show;
            String showid = (String) jshow.get("showid");
            String showname = (String) jshow.get("name");
            
            // Read the xml file
            try {
                File file = new File(downloadcon + showid + ".xml");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
                
                NodeList nodeList = doc.getElementsByTagName("Episode");
                for (int s = 0; s < nodeList.getLength(); s++){
                    Node ep = nodeList.item(s);
                    Element epElement = (Element) ep;
                    
                    HashMap<String, String> hash = new HashMap<>();
                    
                    String epName = epElement.getElementsByTagName("EpisodeName").item(0).getTextContent();
                    String epDate = epElement.getElementsByTagName("FirstAired").item(0).getTextContent();
                    String epOverview = epElement.getElementsByTagName("Overview").item(0).getTextContent();
                    
                    //System.out.println(epName + " " + epDate + " " + epOverview);
                    hash.put("name", epName);
                    hash.put("date", epDate);
                    hash.put("overview", epOverview);
                    hash.put("showname", showname);
                    
                    Date air = df.parse(epDate);
                    long diff = air.getTime() - now.getTime();
                    Long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    hash.put("daystill", days.toString());
                 
                    if (days > timecheck){
                        showeps.add(hash);
                    }
                }
                
            } catch (Exception e){
                System.out.println("ERROR in getShowEpisodes: " + e.toString());
            }
            
        }
        return showeps;
    }
    
    public void removeShow(String sid){
        try {
            JSONArray shows = getTvShows();
            JSONArray finShows = new JSONArray();
            for(Object show : shows){
                JSONObject jshow = (JSONObject) show;
                //System.out.println(jshow.get("showid"));
                if (!jshow.get("showid").equals(sid)){
                    finShows.add(jshow);
                }
            }
            setTvShows(finShows);
            saveConfig();
            loadConfig();
            File xml = new File(downloadcon + sid + ".xml");
            xml.delete();
        } catch (Exception e){
            System.out.println("Error in removeShow: " + e.toString());
        }
    }
    
    public void refreshFiles(ArrayList seriesids){
        try {
            if (Files.exists(Paths.get(foldercon + downloadfolder)) != true){
                    System.out.println("Folder does not exist");
                    Files.createDirectory(Paths.get(foldercon + downloadfolder));
                }
            
            // Download each file
            for (Object seriesid : seriesids){
                String sid = (String) seriesid;
                fdownloader.downloadFile(sid, getApiKey(), downloadcon);
            }
            
        } catch (Exception e) {
            System.out.print("Error in downloadFile");
        }
    }
    
    public void downloadFile(String seriesid){
        // Check if the download folder exists
        try {
            if (Files.exists(Paths.get(foldercon + downloadfolder)) != true){
                    System.out.println("Folder does not exist");
                    Files.createDirectory(Paths.get(foldercon + downloadfolder));
                }
            fdownloader.downloadFile(seriesid, getApiKey(), downloadcon);
        } catch (Exception e) {
            System.out.print("Error in downloadFile");
        }
    }
    
    public void createConfig(){
        json = new JSONObject();
        json.put("apikey", null);
        JSONArray shows = new JSONArray();
        json.put("shows", shows);
        
        // Save the file
        try {
            if (Files.exists(Paths.get(foldername)) != true){
                System.out.println("Folder does not exist");
                Files.createDirectory(Paths.get(foldername));
            }
            PrintWriter writer = new PrintWriter(foldercon + filename, "UTF-8");
            writer.print(json);
            writer.close();
            
        } catch (Exception e) {
            System.out.println("Error in createConfig: " + e.toString());
        }
        
    }
}
