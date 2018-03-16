/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thunder;




/**
 *DatabaseManager class is responsible for getting the connection to database
 * and deliver queries to database and get the results.
 * @author dawod
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBman {
    private Connection myconn;
    private PreparedStatement mypstm;
    private ResultSet myres;
    private String conn;
    
    /**
     * Get connection to  database
     */
    public DBman(){
        //conn="jdbc:mariadb://localhost:3306/thunder";
        conn="jdbc:mariadb://localhost:3306/thunder?useServerPrepStmts=false&rewriteBatchedStatements=true";
        try {
            myconn = DriverManager.getConnection(conn,"root","dawod@SQL");
        } catch (SQLException ex) {
            Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*****************************************************************************************
     * schema0:
     * Send queries to database and receive result
     * @param query statements
     * @return query results
     */
//      public ResultSet old_execute(String query,ArrayList<String> words){
//     
//     
//      if(conn!=null)
//      {
//      try {
//      java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
//     
//      for(String word : words)
//      {
//      mypstm.setString(1,word);
//      mypstm.addBatch();
//      }
//     
//      mypstm.executeBatch();
//     
//     
//      return myres;
//     
//      } catch (SQLException ex) {
//      Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
//      }
//     
//      }
//     
//      return null;
//      }
     
    
    
    /***********************************************************************************************
     * schema1: open file database_scripts and run the schemma1 command on mariadb terminal. 
     * @param query : the query of inserting new row in indexer table
     * @param elements : all elements of row in indexer table (run command "describe indexer;" in database)
     * @return : actually i didn't use the return of this function in this case .
     * 
     * 
     * execute_arrdata : this function is responsible for inserting all data into indexer table in database
     * 
     */
    
    public ResultSet execute_arrdata(String query,ArrayList<Data> elements){
        
        if(conn!=null)
        {
            try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
                for(Data element : elements)
                {
                    mypstm.setString(1,element.getStem());
                    mypstm.setString(2,element.getLink());
                    mypstm.setInt(3, element.getTotal());
                    mypstm.setString(4,element.getOriginal());
                    mypstm.setString(5,element.getTag());
                    mypstm.setInt(6,element.getPosition());
                    mypstm.addBatch();
                }
                mypstm.executeBatch();
                return myres;
            } catch (SQLException ex) {
                Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    
    
    /***********************************************************************************
     * schema2:open file database_scripts.
     * @param query
     * @param words
     * @return 
     * 
     * each executeBatch function run insertion process into database.
     * each execute function run selection process from database.
     * 
     */
    
    public ResultSet executeBatch(String query , ArrayList<String> words){
        
        if(conn!=null)
        {
            try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
                for(String word : words)
                {
                    mypstm.setString(1,word);
                    mypstm.addBatch();
                }
                mypstm.executeBatch();
                
            } catch (SQLException ex) {
                Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return myres;
    }


    
    public ResultSet executeBatch(String query , ArrayList<String> words,ArrayList<Integer> numbers){
        if(conn!=null)
        {
            try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
                for(String word : words)
                {
                    mypstm.setString(1,word);
                    mypstm.addBatch();
                }
                mypstm.executeBatch();
                
            } catch (SQLException ex) {
                Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return myres;
    }
    
    
    
    
    
    
    public ResultSet execute(String query,String word){
        
        if(conn!=null)
        {
            try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
                
                mypstm.setString(1,word);
                myres=mypstm.executeQuery();
                return myres;
                
            } catch (SQLException ex) {
                Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    public ResultSet execute(String query,String word,String link){
        
        if(conn!=null)
        {
            try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
                
                mypstm.setString(1,word);
                mypstm.setString(2, link);
                myres=mypstm.executeQuery();
                return myres;
                
            } catch (SQLException ex) {
                Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    
    
    
    
    
    
}
