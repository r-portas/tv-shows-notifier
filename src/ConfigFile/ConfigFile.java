package ConfigFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Roy Portas
 */

public class ConfigFile {

    String filename = "config.json";
    public JSONObject json;
    
    public void loadConfig(){
        try {
            String data = FileUtils.readFileToString(new File(filename));

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
                PrintWriter writer = new PrintWriter(filename, "UTF-8");
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
    
    public void createConfig(){
        json = new JSONObject();
        json.put("apikey", null);
        
        // Save the file
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.print(json);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error in createConfig: " + e.toString());
        }
        
    }
}
