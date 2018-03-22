/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thunder;

//import com.mysql.jdbc.ResultSet;
import org.mariadb.jdbc.internal.com.read.resultset.SelectResultSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.tartarus.snowball.ext.englishStemmer;

/**
 *
 * @author dawod
 */
public class indexer {
    ResultSet res;
    int counter=0;
    Query q = new Query();
    List<Data> dataElement = new ArrayList<Data>();
    String filePath="src/dataset/";
    File directory = new File(filePath);
    File [] files = directory.listFiles();
    BufferedReader br ;
    String line;
    String link;
    String [] words;
    List<String> all_words = new ArrayList<String>();
    String small_word,stem_word,stop_word;
    Map<String, Integer> stopWordList = new HashMap<String, Integer>();
    
    public indexer(){
        setStopWordList();
        
    }
    
    
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
     * needs:
     * Tags Detection
     * SPEED
     * 
     * ___________________________________________
     * 
     * optimizations:
     * HTML parsing module. some links and declaring variable lines in script tag still not existed
     * ____________________________________________________________________
     *
     * this function is responsible for the following
     * 0. Delete the Data List and start counting from zero
     * 1. i read all files in the directory specified  by the file_path variable
     * 2. for each file i read line by line (First Line is the Link of the Document)
     * 3. then i use Jsoup for HTML parsing
     * 4. then i use REGEX for removing all non-letters or non-numbers and replace them by spaces
     * 5. for each line i separate it's words by spaces (" ")
     * 7. for each word i convert all letters to lowercase
     * 8. for each word i do stemming using snowball library , other options : porter(before snowball), lancaster(agressive)
     * 9. then i remove the empty words and stop words.
     * 10.Finally i add the word to the Data List.
     *
     * finally this function perform some modifications on the database
     * till now i am only save all words i catch even if repeated in the file or in the database!
     */
    public void indexFiles(){
        
        
        for(int i =0 ; i<files.length ; i++){ //for eatch file in the directory
            if(files[i].isFile()){
                counter=1;
                dataElement.clear();
                
                try {
                    br = new BufferedReader(new FileReader(files[i]));
                    link = br.readLine();
                    //System.out.println("link is "+link);
                    while((line = br.readLine())!=null ){ //for each line in the current file
                        String html = line;
                        Document doc = (Document) Jsoup.parse(html);
                        line = doc.text();
                        //Test the Jsoup library
//                        System.out.println("line after barsing : "+line);
                        line = line.replaceAll("[^a-zA-Z0-9 ]", " ");
                        
                        words = line.split(" ");
                        for(String word : words ){
                            // in this section , do what you need for indexing the original word
                            
                            
                            small_word = word.toLowerCase();
                            stem_word = stemmer(small_word);
                            stem_word = stem_word.replaceAll(" ","");
                            
                            if(stopWordList.get(stem_word)!=null || stem_word.equals(""))
                                continue;

                            
                            dataElement.add(new Data(stem_word, link ,counter ,small_word, "header", counter));
                            
                            counter++;
                            
                            
                            
                        }
                    }
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                q.insert_all_indexes((ArrayList<Data>) dataElement);
                System.out.println("Total number of recoreds by this file = "+counter);
                                
            }//the end of the file
        }//the end of the Directory
    }//the end of the indexer
    
    
    
    
    
    
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
    
    
    
    /**
     * This function is responsible for getting the total number of words in specific Document
     * @param link : the link of specific Document
     * @return : number of words in the Document
     */
    public int getTotal(String link){
        res = q.getTotal(link);
        
        try {
            if(res.next())
                return res.getInt("total");
        } catch (SQLException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
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
     * This function is responsible for counting how many times can i find specific word in specific document
     * @param link : the specific Document
     * @param word : the specific word that can be either stemmed_word or an original_word
     * @param type : if 0 then word is stemmed_word else it's an original_word
     * @return : how many times can i find specific word in specific document
     */
    public int getCount(String link , String word , int type){
        res = q.getCount(link,word,type);
        
        try {
            if(res.next())
                return res.getInt("count(*)");
        } catch (SQLException ex) {
            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
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
    
    
    
    
    
    
}
