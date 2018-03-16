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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tartarus.snowball.ext.englishStemmer;

/**
 *
 * @author dawod
 */
public class indexer {
    int counter=0;
    Query q = new Query();
    List<Data> dataElement = new ArrayList<>();
    String filePath="src/dataset/";
    File directory = new File(filePath);
    File [] files = directory.listFiles();
    BufferedReader br ;
    String line;
    String [] words;
    List<String> all_words = new ArrayList<String>();
    String small_word,stem_word;
    
    
    /**
     * needs:
     * I need stooping removing function
     * check that the new word is unique in the database.
     *
     * ____________________________________________________________________
     *
     * this function is responsible for the following
     * 1. i read all files in the directory specified  by the file_path variable
     * 2. for each file i read line by line
     * 3. for each line i separate it's words by spaces (" ")
     * 4. for each word i convert all letters to lowercase
     * 5. for each word i do stemming using snowball library , other options : porter(before snowball), lancaster(agressive)
     *
     *
     * finally this function perform some modifications on the database
     * till now i am only save all words i catch even if repeated in the file or in the database!
     */
    public void indexFiles(){

        for(int i =0 ; i<files.length ; i++){ //for eatch file in the directory
            if(files[i].isFile()){
                
                try {
                    br = new BufferedReader(new FileReader(files[i]));
                    while((line = br.readLine())!=null ){ //for each line in the current file
                        //System.out.println("file is : ("+files[i]+") line is : ("+line+")");
                        words = line.split(" ");
                        for(String word : words ){
                            // in this section , do what you need for indexing the original word
                            
                            
                            small_word = word.toLowerCase();
                            stem_word = stemmer(small_word);
                            all_words.add(stem_word);
                            dataElement.add(new Data(stem_word, "http://link.com", small_word, "header", counter));
                            
                            counter++;
                                    
                            //System.out.println("file : "+files[i]+" ----- original : "+word+" --- stemmed : "+stemmer(word));
                            
                        //    q.insert_all_word(stem_word,all_words);

                            
                        }
                    }
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                System.err.println("Dawod : "+files[i]+" is not file!");
            }
            
        }
        //q.insert_all_word((ArrayList<String>) all_words);
        q.insert_all_indexes((ArrayList<Data>) dataElement);
    }
    
    
    
//    public void getWords(){
//        SelectResultSet res;
//        res =(SelectResultSet) q.get_words();
//        try {
//            while(res.next()){
//                System.out.println(res.getString("word_val"));
//                
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(indexer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * this function must take single word . no multiple words
     * @param word
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
