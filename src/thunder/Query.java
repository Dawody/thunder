/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunder;

//import java.sql.ResultSet;
import org.mariadb.jdbc.internal.com.read.resultset.SelectResultSet;
import java.sql.SQLException;


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
    
    public SelectResultSet get_words(){
        q = "select * from words;";
        res = (SelectResultSet) dbman.execute(q);
        return res;
    }
    
    
    public boolean insert_word(String word){
        //q = "insert into words values(\"\",\""+word+"\");";
        //q = "insert into words values(\""+word+"\");";        
        q = "INSERT INTO words (`word_val`) values('"+word+"');";
        try{
            dbman.execute(q);
        }catch(Exception e){
            System.err.println("Problem in insert_word query function");
            return false;
        }
        
        
        return true;
    }
    
}
