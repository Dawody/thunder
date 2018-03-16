/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thunder;

import java.sql.ResultSet;
import org.mariadb.jdbc.internal.com.read.resultset.SelectResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author dawod
 */
public class Query {
    String q;
    DBman dbman;
    SelectResultSet res;
    
    public Query(){
        dbman = new DBman();
    }
    
//    public SelectResultSet get_words(){
//        q = "select * from words;";
//        res = (SelectResultSet) dbman.execute(q);
//        return res;
//    }
    



/**
 * schema1
 * @param words
 * @return 
 *    
    
    public boolean insert_all_word(ArrayList<String> words){
        q = "insert ignore into words (stem) values (?)";
        try{
            dbman.execute(q,words);
        }catch(Exception e){
            System.err.println("Dawod : Problem in dbman.execute(q) function");
            return false;
        }
        
        
        return true;
    }
    
    
    
    
    public boolean insert_all_links(ArrayList<String> links){
        
        q = "insert into links (link,total) values (?,?)";
        try{
            dbman.execute(q,links);
        }catch(Exception e){
            System.err.println("Dawod : Problem in dbman.execute(q) function");
            return false;
        }
        
        return true;
    }
    
    
    
    
    
    public boolean insert_all_existences(ArrayList<String> existences){
        
        q = "insert into existence (stem_id,link_id,original,tag,position) values (?,?,?,?,?)";
        try{
            dbman.execute(q,existences);
        }catch(Exception e){
            System.err.println("Dawod : Problem in dbman.execute(q) function");
            return false;
        }
        
        return true;
    }
    
  */  
    
    
    
    /**
     * schema2
     * @param indexes
     * @return 
     */
    public boolean insert_all_indexes(ArrayList<Data> indexes){
        
        q = "insert into indexer (stem,link,original,tag,position) values (?,?,?,?,?)";
        try{
            dbman.execute(q,indexes);
        }catch(Exception e){
            System.err.println("Dawod : Problem in dbman.execute(q) function");
            return false;
        }
        
        return true;
    }
    
    
    
    
    
    
    
/**
 * schema0:
 */    
//
//    public boolean insert_word(String word){
//        //q = "insert into words values(\"\",\""+word+"\");";
//        //q = "insert into words values(\""+word+"\");";
//        q = "INSERT INTO words (`word_val`) values('"+word+"');";
//        try{
//            dbman.execute(q);
//        }catch(Exception e){
//            System.err.println("Dawod : Problem in dbman.execute(q) function");
//            return false;
//        }
//
//
//        return true;
//    }
    
}
