
import java.util.ArrayList;
import org.json.simple.JSONObject;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

// Import other files
import ConfigFile.ConfigFile;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Roy Portas
 */

class TvSeries{
    public String name;
    public String showid;
    
    TvSeries(String n, String id){
        name = n;
        showid = id;
    }
 
    JSONObject generateJson(){
        JSONObject temp = new JSONObject();
        temp.put("name", name);
        temp.put("showid", showid);
        return temp;
    }
}

class TvShow implements Comparable<TvShow>{
    public String showname;
    public String showdesc;
    public String showep;
    public int days;
    
    public int compareTo(TvShow other){
        return this.days - other.days;
    }
    
    TvShow(String name, String epname, String description, String sdays){
        showname = name;
        showep = epname;
        showdesc = description;
        days = Integer.parseInt(sdays);
        //System.out.println(showname + " - " + epname);
        }
    }



public class tvShowNotifierUI extends javax.swing.JFrame {
    
    ArrayList<TvShow> shows;
    ArrayList<TvSeries> series;
    
    DefaultTableModel tModel;
    ListSelectionModel lsModel;
    
    String today = "0";
    String yesterday = "-1";
    
    ConfigFile cfile;
    
    /**
     * Creates new form tvShowNotifierUI
     */
    public tvShowNotifierUI() {
        initComponents();
        
        setup();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TV Show Notifier");

        jLabel1.setText("TV Show Name");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Show Name", "Episode Name", "Airing"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jMenu1.setText("File");

        jMenuItem1.setText("Refresh");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("API key");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("TV Show");

        jMenuItem3.setText("Add show");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem5.setText("Remove show");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem4.setText("Refresh show info");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(317, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(408, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.out.println("Refreshing tv show list");
        populateList();
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // Set the API key
        JFrame parent = new JFrame();
        String newKey = (String) JOptionPane.showInputDialog(parent, 
                "Enter API key for the TVDB",
                null);
        System.out.println(newKey);
        int success = cfile.setApiKey(newKey);
        if (success != 0){
            JOptionPane.showMessageDialog(parent, "Error applying key, try again");
        } else {
            // It worked, save the file
            cfile.saveConfig();
            System.out.println("Saving file");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    
    /**
     * Add TV show
     * @param evt 
     */
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFrame parent = new JFrame();
        String showname = (String) JOptionPane.showInputDialog(parent,
                "Enter show to search",
                null);
        searchShows(showname);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // Refresh the show info
        ArrayList seriesid = new ArrayList();
        for(TvSeries tv : series){
            seriesid.add(tv.showid);
        }
        
        Thread thread = new Thread(){
            public void run(){
                cfile.refreshFiles(seriesid);
            }
        };
        thread.start();
        
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // Remove the show
        
        // Generate a list of shows
        Map shows = new HashMap();
        for(TvSeries tv : series){
            shows.put(tv.name, tv.showid);
        }
        Set<String> keyset = shows.keySet();
        String[] names = keyset.toArray(new String[shows.size()]);
        
        // Show the popup
        String selectedshow = (String) JOptionPane.showInputDialog(null, "Choose show", "Shows",
                    JOptionPane.QUESTION_MESSAGE, null,
                    names, names[0]);
        
        String sid = (String) shows.get(selectedshow);;
        cfile.removeShow(sid);
        populateList();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    void loadSeries(){
        try {
        JSONArray temp = cfile.getTvShows();
        for (Object j : temp){
            JSONObject jo = (JSONObject) j;
            series.add(new TvSeries(jo.get("name").toString(), 
                    jo.get("showid").toString()));
            
        }
        System.out.println(series);
        } catch (Exception e) {
            System.out.println("ERROR in file loadSeries: " + e.toString());
        }
    }
    
    /**
     * Search for a show
     * @param showname 
     */
    void searchShows(String showname){
        try {
            String newShowname = showname.replaceAll(" ", "%20");
            URL url = new URL("http://thetvdb.com/api/GetSeries.php?seriesname=" + newShowname);
            InputStream is = url.openStream();
            String output = IOUtils.toString(is, "utf-8");
            
            //System.out.println(output);
            is.close();
            
            // Parse the XML data
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(output.getBytes("utf-8"))));
            
            NodeList shows = doc.getElementsByTagName("Series");
            
            Map showDict = new HashMap();

            
            for (int i = 0; i < shows.getLength(); i++){
                Node node = shows.item(i);
                Element element = (Element) node;
                String tshow = element.getElementsByTagName("SeriesName").item(0).getTextContent();
                String tid = element.getElementsByTagName("seriesid").item(0).getTextContent();
                //System.out.println(tshow + " " + tid);
                showDict.put(tshow, tid);
            }
            Set<String> keyset = showDict.keySet();
            String[] showDictNames = keyset.toArray(new String[keyset.size()]);
            
            String input = (String) JOptionPane.showInputDialog(null, "Choose show", "Shows",
                    JOptionPane.QUESTION_MESSAGE, null,
                    showDictNames, showDictNames[0]);
            String showid = (String) showDict.get(input);
            System.out.println(showid);
            // Check that the show is not already in the list
            int contains = 0;
            for (TvSeries tv : series){  
                if (tv.showid.equals(showid)){
                    contains = 1;
                    System.out.println("Found duplicate");
                }
            }
            if (contains == 0){
                if (showid != null){
                    series.add(new TvSeries(input, showid));
                    saveSeries();
                    cfile.downloadFile(showid);
                }
            }
            
            populateList();
            
        } catch (Exception e){
            System.out.println("ERROR in searchShows: " + e.toString());
        }
         
    }
    
    /**
     * Save the TV Series information
     */
    void saveSeries(){
        JSONArray temp = new JSONArray();
        for (TvSeries tv : series){
            temp.add(tv.generateJson());
        }
        
        // Save the file
        cfile.setTvShows(temp);
        cfile.saveConfig();
        //System.out.println(temp);
    }
    
    /**
     * Sets up the objects to store tv shows
     */
    void populateList(){
        tModel.setRowCount(0);
        shows = new ArrayList<TvShow>();

        
        //addShow("Person of Interest", "Stuff1", "10-02-2014", "Show desc 1");
        //addShow("Person of Interest", "Stuff2", "10-02-2014", "Show desc 2");
        ArrayList data = cfile.getShowEpisodes();
        for(int i = 0; i < data.size(); i++){
            HashMap<String, String> hm = (HashMap) data.get(i);
            shows.add(new TvShow(hm.get("showname"), hm.get("name"), hm.get("overview"), hm.get("daystill")));
        }
        
        Collections.sort(shows);

        
        // Sort the list
        for (int a = 0; a < shows.size(); a++){
            TvShow show = shows.get(a); 
            addShow(show.showname, show.showep, show.showdesc, String.valueOf(show.days + 2)); //This is the showing offset
        }
        
    }
    
    /**
     *  Search for a tv show via params and sets the labels and text area
     * @param name
     * @param epname 
     */
    void searchTvShows(String name, String epname){
        for (TvShow tv : shows){
            if (tv.showname == name && tv.showep == epname){
                jLabel1.setText(tv.showname + " - " + tv.showep);
                jTextArea1.setText(tv.showdesc);
            }
        }
        
    }
    
    void addShow(String showname, String episodename, String epdesc, String air){
        //shows.add(new TvShow(showname, episodename, epdesc));
        if (air.compareTo(today) == 0){
            air = "Today";
        } else if (air.compareTo(yesterday) == 0){
            air = "Yesterday";
        }
        
        // Add to the jTable
        String[] data = {showname, episodename, air}; 
        tModel.addRow(data);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            /*
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            */
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(tvShowNotifierUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tvShowNotifierUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tvShowNotifierUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tvShowNotifierUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tvShowNotifierUI().setVisible(true);
            }
        });
    }
    
    /**
     * Sets up the UI and the backend
     */        
    void setup(){
        
        // Set up the config file
        cfile = new ConfigFile();
        
        // Load the config file
        cfile.loadConfig();
        //System.out.println(cfile.getApiKey());
        
        // Set the labels
        if (cfile.getApiKey() != "null"){
            jLabel3.setText("API key loaded");
        } else {
            jLabel3.setText("No API key");
        }
        
        shows = new ArrayList<TvShow>();
        series = new ArrayList<TvSeries>();
        
        //saveSeries();
        loadSeries();
        
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        
        tModel = (DefaultTableModel) jTable1.getModel();
        lsModel = jTable1.getSelectionModel();
        
        lsModel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                
                // Get the show name and episode,  used for searching the list
                int row = jTable1.getSelectedRow();
                if (row >= 0){
                    String tShowname = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
                    String tEpname = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();
                
                    searchTvShows(tShowname, tEpname);
                }
            }
        });
        
        populateList();
        int noShows = series.size();
        jLabel2.setText(String.valueOf(noShows) + " shows loaded");
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
