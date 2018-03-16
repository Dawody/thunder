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
    
    /**
     * Send queries to database and receive result
     * @param query statements
     * @return query results
     */
    public ResultSet old_execute(String query,ArrayList<String> words){
        
        
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

                
                return myres;
                
            } catch (SQLException ex) {
                Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return null;
    }
    
    
    
    
        public ResultSet execute(String query,ArrayList<Data> elements){
        
        
        if(conn!=null)
        {
            try {
                java.sql.PreparedStatement mypstm=  myconn.prepareStatement(query);

                for(Data element : elements)
                {
                    mypstm.setString(1,element.getStem());
                    mypstm.setString(2,element.getLink());
                    mypstm.setString(3,element.getOriginal());
                    mypstm.setString(4,element.getTag());
                    mypstm.setInt(5,element.getPosition());
                    
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

    
    
    
    
}
