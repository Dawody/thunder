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
    ResultSet res;
    
    public Query(){
        dbman = new DBman();
    }
    
//    public SelectResultSet get_words(){
//        q = "select * from words;";
//        res = (SelectResultSet) dbman.execute(q);
//        return res;
//    }
    
    
    
    
    /************************************************************************************************
     * schema2 : in this schema i try to minimize the redundancy by splitting the data into 3 tables
     *              but i found that it will be more difficult in handling
     *              and it's speed will still as the speed of schema1
     *              it's more complex and has many corner cases.
     * @param words
     * @return
     */
    
//    public boolean insert_all_words(ArrayList<String> words){
//        q = "insert ignore into words (stem) values (?)";
//        try{
//            dbman.executeBatch(q,words);
//        }catch(Exception e){
//            System.err.println("Dawod : Problem in dbman.execute(q) function");
//            return false;
//        }
//
//
//        return true;
//    }
//
//
//
//
//    public boolean excute_batch(ArrayList<String> links, ArrayList<int> totals){
//
//        q = "insert into links (link,total) values (?,?)";
//        try{
//            dbman.executeBatch(q,links,totals);
//        }catch(Exception e){
//            System.err.println("Dawod : Problem in dbman.execute(q) function");
//            return false;
//        }
//
//        return true;
//    }
//
//
//
//
//
//    public boolean insert_all_existences(ArrayList<String> existences){
//
//        q = "insert into existence (stem_id,link_id,original,tag,position) values (?,?,?,?,?)";
//        try{
//            dbman.execute(q,existences);
//        }catch(Exception e){
//            System.err.println("Dawod : Problem in dbman.execute(q) function");
//            return false;
//        }
//
//        return true;
//    }
//
//
//
//    public boolean insert(ArrayList<Data> data){
//
//
//        return true;
//    }
    
    
    
    /**********************************************************************************************
     * schema1 : In this schema I put all the data into single table
     *          the negative side of this implementation is the large redundancy
     *          the positive side if this implementation is the simple handling
     *          and few corner cases
     *          also the speed is very good.
     * @param indexes
     * @return
     */
    
    
    
    
    /**
     * this function insert all data into indexer table in database
     * @param indexes : ArrayList of all data that needed to be inserted into database , see Class Data.java for more information
     * @return : i don't use the return in this case
     */
    public boolean insert_all_indexes(ArrayList<Data> indexes){
        
        q = "insert into indexer (stem,link,total,original,tag,position) values (?,?,?,?,?,?)";
        try{
            dbman.execute_arrdata(q,indexes);
        }catch(Exception e){
            System.err.println("Dawod : Problem in dbman.execute(q) function");
            
        }
        
        return true;
    }
    
    
    /**
     * This function is responsible for getting Links that contain specific word
     * @param word : single word either stemmed_word or original_word
     * @param type : if 0 then word argument is stemmed_word ,else it is original_word
     * @return List of Links
     */
    public ResultSet getLinks(String word,int type){
        if(type==0)
            q = "select DISTINCT(link) from indexer where stem=?";
        else
            q = "select DISTINCT(link) from indexer where original=?";
        
        res = dbman.execute(q, word);
        
        return res;
    }
    
    /**
     * This function is responsible for getting the total number of words in specific Document
     * @param link : the link of specific Document
     * @return : number of words in the Document
     */
    public ResultSet getTotal(String link){
        q = "select total from indexer where link=?";
        
        res = dbman.execute(q, link);
        
        return res;
    }
    
    
    /**
     * This function is responsible for getting the Tags where i can find specific word in specific Document
     * @param link: the specific word
     * @param word: the specific word that can be either stemmed_word or original_word according to the type parameter
     * @param type: if 0 then the word is stemmed_word else its an original_word
     * @return List of Tags where i an find the specific word in the specific Document
     * @Note : there is no repeat in the tags , so if word is exist twice in the (body)tag i will output single (body)tag
     */
    public ResultSet getTags(String link,String word,int type){
        if(type==0)
            q = "select distinct(tag) from indexer where stem=? and link=?";
        else
            q = "select distinct(tag) from indexer where original=? and link=?";
        
        res = dbman.execute(q,word,link);
        
        return res;
    }
    
    /**
     * This function is responsible for counting how many times can i find specific word in specific document
     * @param link : the specific Document
     * @param word : the specific word that can be either stemmed_word or an original_word
     * @param type : if 0 then word is stemmed_word else it's an original_word
     * @return : how many times can i find specific word in specific document
     */
    public ResultSet getCount(String link , String word,int type){
        if(type==0)
            q = "select count(*) from indexer where stem=? and link=?";
        else
            q = "select count(*) from indexer where original=? and link=?";
        
        res = dbman.execute(q , word, link);
        
        return res;
        
    }
    
    
    
    /**
     *
     * @return
     */
    public ResultSet getLinkStatus(){
        
        q = "select id,link,changed from links where changed=1";
        
        res = dbman.execute(q);
        
        return res;
    }
    
    
    
    /**
     *
     *
     */
    public boolean updateLinkStatus(ArrayList<Integer> linkIds){
        
        q = "update links set changed=0 where id=?";
        try{
            dbman.execute_update_linkStatus(q,linkIds);
        }catch(Exception e){
            System.err.println("Dawod : Problem in dbman.update(q) function");
            
        }
        
        return true;
    }
    
    
    
    
    
    /*************************************************************************************************
     * schema0: very slow 20record/second
     *          while schema1&2 has about 50,000 record/second speed.
     */
    
    
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
