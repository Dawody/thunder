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


//import Admin.connection;
//import com.mysql.jdbc.Statement;
import org.mariadb.jdbc.MariaDbStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBman {
    private Connection myconn;
    private MariaDbStatement mystm;
    private ResultSet myres;
    private String conn;
    
    /**
     * Get connection to  database
     */
    public DBman(){

        myconn=null;
        myres=null;
        mystm=null;
        conn="jdbc:mariadb://localhost:3306/thunder";
        
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
    public ResultSet execute(String query){
        
        
        if(conn!=null)
        {
            try {
                mystm = (MariaDbStatement) myconn.createStatement();
                myres = mystm.executeQuery(query);
                return myres;
                
            } catch (SQLException ex) {
                Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return null;
    }
    
    
    
    
}
