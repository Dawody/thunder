/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thunder;

//import com.mysql.jdbc.ResultSet;
//import static com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory.set;
import org.mariadb.jdbc.internal.com.read.resultset.SelectResultSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.reflect.Array.set;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tartarus.snowball.ext.englishStemmer;

/**
 *
 * @author dawod
 */
public class indexer implements Runnable{
    ResultSet res;
    Integer files_counter;
    Query q = new Query();
    Set dictionary = new HashSet();
    Map<String, Integer> stopWordList = new HashMap<String, Integer>();
    Map<Integer,Boolean> linkStatusList = new HashMap<Integer,Boolean>();
    Map<Integer,String> linkList = new HashMap<Integer,String>();
    Map<String,Integer> total = new HashMap<String,Integer>();
    String stop_word;
    
    
    Query qr = new Query();
    int counter=0;
    List<Data> dataElement = new ArrayList<Data>();
    String filePath="documents";
    File directory = new File(filePath);
    File [] files = directory.listFiles();
    BufferedReader br ;
    String line,lines;
    String body;
    String head;
    String link;
    String [] words;
    String small_word,stem_word;
    
    
    /**
     * This constructor is used for tacking object of Indexer for non-indexing usage !
     * actually i use it to call function getLinkStatus()
     */
    public indexer(){
                setTotal();

    }
    
    /**
     * the main for indexing .
     * 
     * @param linkStatus : before indexing any link i need to check if that link content was changed or not . so indexing will be useful not repeated.
     * @param counter :
     * @param indexQuery : 
     * @param dictionary 
     */
    public indexer(Map<Integer,Boolean> linkStatus , Integer counter , Query indexQuery , HashSet dictionary , Map<String,Integer> total){
        this.linkStatusList = linkStatus;
        this.files_counter =counter;
        this.qr = indexQuery;
        this.dictionary = dictionary;
        this.total = total;
        
        setStopWordList();
        
        
        
        
        res = q.getLinkStatus();
        try {
            while(res.next()){
                linkList.put(res.getInt("id"), res.getString("link"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    /**
     * This function is responsible for preparing list of stop words
     * i read them from text file in lib folder. they are previously prepared in that file
     *
     */
    public void setStopWordList(){
        try {
            Scanner inFile = new Scanner(new FileReader("libs/stopWordList.txt"));
            while(inFile.hasNext()){
                stop_word=inFile.next();
                stopWordList.put(stop_word,1);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }



    /**
     * This function is responsible for preparing set of words that are needed for "did you mean" feature
     * 
     */
    public void setDictionary(){
        try {
            Scanner inFile = new Scanner(new FileReader("libs/dictionary.txt"));
            while(inFile.hasNext()){
                stop_word=inFile.next();
                dictionary.add(stop_word);
          
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }



    /**
     * This function is responsible for preparing list of total number of words into each link
     * this is to fix the problem of slow database!
     * 
     */
    public void setTotal(){
        try {
            String key="";
            int value=0;
            Scanner inFile = new Scanner(new FileReader("libs/total.txt"));
            while(inFile.hasNext()){
try{
                key=inFile.next();
//                System.out.println("key = " + key);
                value=inFile.nextInt();
//                System.out.println("value = "+value);
                total.put(key, value);
//                System.out.println("saved!");
}catch(Exception e){System.out.println("key = "+key + " .....value = "+value);}          
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }





    /**
     * needs:
     * Make SURE that parsing is perfect
     * SPEED
     *
     * ___________________________________________
     *
     * optimizations:
     * reading from one file is faster than multiple files in case of small files ONLY!..TRY to make it also for large files
     *
     * ____________________________________________________________________
     *
     * this function is responsible for the following
     * 1.check that file is realy file not folder ,stc.
     * 2. extract the file number and ignore the extention (.thml)
     * 3. if that file is not crawled completely ignore it now
     * 4. if that file is not changed or in other meaning (had been indexed before) ignore it also
     * 5. if that file is completely crawled and didn't indexed before or had been changed after indexing , so start index that file
     * 6. Delete the Data List and start counting from zero
     * 7. for each file i read line by line
     * 8. then i use Jsoup for HTML parsing .. i parse it into head and body only.
     * 9. then i use REGEX for removing all non-letters or non-numbers and replace them by spaces (this feature is canceled because i need some non letters and non numbers symbols like logical and mathematic symbols)
     * --the next operations i d twice . one time for the head text and another time for the body text
     * 10. for each line i separate it's words by spaces (" ")
     * 7. for each word i convert all letters to lowercase .. the output from this operation is the original word in the database
     * 8. for each word i do stemming using snowball library , other options/tools for stemming : porter(before snowball), lancaster(agressive)
     * 9. then i remove the empty words and stop words.
     * 10.Finally i add the word to the Data List. .. this is the stem word in the database
     *--------------------
     * 
     * finally this function perform some modifications on the database
     * till now i am only save all words i catch even if repeated in the file or in the database!
     */
    public void indexFiles(){
        
        for(int i =0 ; i<files.length ; i++){ //for eatch file in the directory            
            
            
            //1.check that file is realy file not folder ,stc.
            if(!files[i].isFile()) //files is ArrayList carries the files that had found in file path directory
            {
                
                //if not file : continue
                continue;
            }
            
            
            //2. extract the file number and ignore the extention (.thml)
            String fileName = files[i].getName();
            int fileId = Integer.parseInt(fileName.substring(0, fileName.length() - 5)); 
            
                        
            //3. if that file is not crawled completely ignore it now
            if(linkStatusList.get(fileId)==null)
            {
                //if not existed in the link lists : continue
                continue;
            }
            
            
            //4. if that file is not changed or in other meaning (had been indexed before) ignore it also
            if(linkStatusList.get(fileId)==false)
            {
                //if not changed : continue
                continue;
            }
            
            //5. if that file is completely crawled and didn't indexed before or had been changed after indexing , so start index that file
            if(linkStatusList.get(fileId)){
                synchronized(linkStatusList){   //to prevent two indexer threads from indexing the same file in the same time
                    if(!linkStatusList.get(fileId))
                        continue;
                    System.out.println("working file : "+fileId);
                    linkStatusList.put(fileId, false);
                    
                }
                
                
                
                
                //count words into the document and clean the data of the previous documents
                counter=1;
                dataElement.clear();
                
                try {
                    
                    br = new BufferedReader(new FileReader(files[i]));
                    
                    //get the link name .. it was stored in the constructor function
                    link = linkList.get(fileId);
                    
                    //collecting the text from the document line by line
                    lines="";
                    while((line = br.readLine())!=null )
                    {
                        lines=lines+" "+line;
                    }
                    
                    
                    //parsing the HTML into <head> and <body>
                    String html = lines;
                    Document doc = (Document) Jsoup.parse(html);
                    body = doc.body().text();
                    head = doc.head().text();
                    
                    //replace each non-letter or non-number or non-space into space
                    //body = body.replaceAll("[^a-zA-Z0-9 ]", " ");
                    //head = head.replaceAll("[^a-zA-Z0-9 ]", " ");
                    
                    
                    //split and store
                    words = head.split(" ");
                    for(String word : words ){
                        // in this section , do what you need for indexing the original word
                        small_word = word.toLowerCase();
                        stem_word = stemmer(small_word);
                        stem_word = stem_word.replaceAll(" ","");
                        if(stopWordList.get(stem_word)!=null || stem_word.equals(""))
                            continue;
                        
                        dictionary.add(small_word);
                        dataElement.add(new Data(stem_word, link ,counter ,small_word, "header", counter));
                        counter++;
                    }
                    
                    //once again
                    words = body.split(" ");
                    for(String word : words ){
                        // in this section , do what you need for indexing the original word
                        small_word = word.toLowerCase();
                        stem_word = stemmer(small_word);
                        stem_word = stem_word.replaceAll(" ","");
                        if(stopWordList.get(stem_word)!=null || stem_word.equals(""))
                            continue;
                        
                        dictionary.add(small_word);
                        dataElement.add(new Data(stem_word, link ,counter ,small_word, "body", counter));
                        counter++;
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //this line for getTotal function (increasing speed)
                dataElement.add(new Data("dawooood", link ,counter ,"dawooood", "body", counter));
                total.put(link, counter);
                
                qr.insert_all_indexes((ArrayList<Data>) dataElement);
                files_counter++;
                System.out.println(files_counter +" Total number of recoreds by this file ("+files[i] +") = "+counter);
                
            }//the end of the file
        }//the end of the Directory
        
        
    }//the end of the indexer
    
    
    
    
    
    
    //------------------------------------------------------------------------------------------------\\
    //----------------------------------FUNCTIONS FOR DATABASE----------------------------------------\\
    //------------------------------------------------------------------------------------------------\\  
    
    
    /**
     * This function is responsible for getting Links that contain specific word
     * @param word : single word either stemmed_word or original_word
     * @param type : if 0 then word argument is stemmed_word ,else it is original_word
     * @return List of Links
     */
    public ArrayList<String> getLink(String word,int type){
        
        res = q.getLinks(word, type);
        List<String> links =new ArrayList<String>();
        try {
            while(res.next()){
                links.add(res.getString("link"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return (ArrayList<String>) links;
        
    }
    
    
    
    public Map<Integer,Boolean> getLinkStatus(){
        res = q.getLinkStatus();
        Map<Integer, Boolean> linkStatus = new HashMap<Integer, Boolean>();
        boolean status;
        try {
            while(res.next()){
                status = ((res.getInt("changed")==1)? true:false);
                linkStatus.put(res.getInt("id"), status);
                linkList.put(res.getInt("id"), res.getString("link"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return linkStatus;
    }
    
    
    /**
     * This function serve multi threading
     * @param linkIds 
     */
    public void updateLinkStatus(ArrayList<Integer> linkIds){
        q.updateLinkStatus(linkIds);
    }
    
    /**
     * This function serve multi threading 
     * @return 
     */
    public Integer initFileCounter(){
        return 0;
    }
    
    /**
     *  This function serve multi threading
     * @return 
     */
    public Query initQuery(){
        return qr;
    }
    
    /**
     *  This function serve multi threading
     * @return 
     */
    public Set initDictionary(){
        setDictionary();
        return dictionary;
    }
    


    /**
     *  This function serve multi threading
     * @return 
     */
    public Map<String,Integer> initTotal(){
        setTotal();
        return total;
    }
    
    
    
    /**
     * This function is responsible for getting the total number of words in specific Document
     * @param link : the link of specific Document
     * @return : number of words in the Document
     */
    public int getTotal(String link){
        
        int tot =0;
        try{
        tot = total.get(link);
        }catch(NullPointerException e ){System.err.println("error with link : "+link);}
        return tot;
        
//        res = q.getTotal(link);
//        
//        try {
//            if(res.next())
//                return res.getInt("total");
//        } catch (SQLException ex) {
//            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return 0;
    }
    
    
    
    /**
     * This function is responsible for getting the Tags where i can find specific word in specific Document
     * @param link: the specific word
     * @param word: the specific word that can be either stemmed_word or original_word according to the type parameter
     * @param type: if 0 then the word is stemmed_word else its an original_word
     * @return List of Tags where i an find the specific word in the specific Document
     * @Note : there is no repeat in the tags , so if word is exist twice in the (body)tag i will output single (body)tag
     */
    public ArrayList<String> getTags(String link ,String word , int type){
        res=q.getTags(link, word, type);
        List<String> tags = new ArrayList<String>();
        try {
            while(res.next()){
                tags.add(res.getString("tag"));
            }} catch (SQLException ex) {
                Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        return (ArrayList<String>) tags;
    }
    
    
    
    /**
     * This function is responsible for getting the Positions where i can find specific word in specific Document
     * @param link: the specific word
     * @param word: the specific word that can be either stemmed_word or original_word according to the type parameter
     * @param type: if 0 then the word is stemmed_word else its an original_word
     * @return List of Positions where i an find the specific word in the specific Document
     * @Note : there is no repeat in the tags , so if word is exist twice in the (body)tag i will output single (body)tag
     */
    public ArrayList<Integer> getPositions(String link ,String word , int type){
        res=q.getPositions(link, word, type);
        List<Integer> positions = new ArrayList<Integer>();
        try {
            while(res.next()){
                positions.add(res.getInt("position"));
            }} catch (SQLException ex) {
                Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        return (ArrayList<Integer>) positions;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This function is responsible for counting how many times can i find specific word in list of documents
     * @param links : list of documents where i need to count how times i find specific word in them
     * @param word : the specific word that can be either stemmed_word or an original_word
     * @param type : if 0 then word is stemmed_word else it's an original_word
     * @return : Map of links and number of how many times can i find the word into each link
     */
    public Map<String,Integer> getCount(ArrayList<String> links , String word , int type){
        res = q.getCount(links,word,type);
        Map<String , Integer> mp =  new HashMap<String, Integer>();
        String link;
        try {
            while(res.next())
            {
                link = res.getString("link");
                if(mp.get(link)==null)
                    mp.put(link, 1);
                else
                    mp.put(link, mp.get(link)+1);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mp;
    }
    
    





    
    
    
    /**
     * this function must take single word , no multiple words.
     * @param word : original word
     * @return word after stemming
     */
    public String stemmer(String word){
        englishStemmer stem = new englishStemmer();
        stem.setCurrent(word);
        if(stem.stem()){
            // System.out.println(stem.getCurrent()); //comment this line
            return stem.getCurrent();
        }
        else
        {
            System.out.println("Failed to stem the word : "+word);
            return "aaaaa";
        }
        
    }
    
    @Override
    public void run() {
        indexFiles(); 
    }
    
    
    public  Map<String, Integer> getStopWords(){
        setStopWordList();
        return stopWordList;
    }

    
    
    
}
