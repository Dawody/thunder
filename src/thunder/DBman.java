
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
        conn="jdbc:mariadb://localhost:3306/thunder?useServerPrepStmts=false&rewriteBatchedStatements=true&rewriteBatchUpdates=true&integratedSecurity=true";
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
        
//        //i'm sure that all elements have the same link
//        Data test_element = elements.get(0);
//       
//        
//        myres = execute("select count(*) from indexer where link=\""+test_element.getLink()+"\"");
//        try {
//            if(myres.next()){
//                if(myres.getInt("count(*)")>0){
//                    //remove the previous records related to this link;
//                    execute("delete from indexer where link =\""+test_element.getLink()+"\"");
//                }
//                
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        if(conn!=null)
        {
            try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
                for(Data element : elements)
                {
                    mypstm.setString(1,element.getStem());
                    mypstm.setString(2,element.getLink());
                    mypstm.setInt(3, elements.get(elements.size()-1).getPosition());
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
    
    
    
    
    
    public ResultSet execute_update_linkStatus(String query,ArrayList<Integer> linkId){
        if(conn!=null)
        {
            System.out.println("DBMAN : start iterating on link ids");
                try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
                for(int id : linkId)
                {
                    mypstm.setInt(1,id);
                    mypstm.addBatch();
                }
                    System.out.println("finish iterating and start to execute batch");
                mypstm.executeBatch();
                    System.out.println("batch executed successsfully");
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
    
    
    public ResultSet execute(String query){
        
        if(conn!=null)
        {
            try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);
                
                myres=mypstm.executeQuery();
                return myres;
                
            } catch (SQLException ex) {
                Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    
    
    
    
    
    
}

