package FileDownloader;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author roy
 */
public class FileDownloader {
    /**
     * Downloads a file from URL and puts it in the data folder
     * @param seriesid
     */
    public void downloadFile(String seriesid, String apikey, String path){
       try {
       URL downloadurl = new URL("http://www.thetvdb.com/api/" + apikey + "/series/" + seriesid + "/all/en.zip");
       Files.copy(downloadurl.openStream(), new File(path + seriesid + ".zip").toPath(), StandardCopyOption.REPLACE_EXISTING);
       System.out.println("DOWNLOADING " + seriesid);
       
       // Extract the zip file and rename the 'en.xml' to the series id
       ZipFile zfile = new ZipFile(path + seriesid + ".zip");
       Enumeration entries = zfile.entries();
       
       while (entries.hasMoreElements()){
           ZipEntry entry = (ZipEntry) entries.nextElement();
           if (entry.getName().equals("en.xml")){
               Files.copy(zfile.getInputStream(entry), Paths.get(path + seriesid + ".xml"), StandardCopyOption.REPLACE_EXISTING);
               
               System.out.println("Extracting " + seriesid + ".xml");
           }
       }
       
       // Delete the zip file
       File file = new File(path + seriesid + ".zip");
       file.delete();
       System.out.println("Deleting zip file");
       
       } catch (Exception e) {
           System.out.println("Error in downloadFile: " + e.toString());
       }
       
    }
}
