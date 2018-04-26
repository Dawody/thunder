
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

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;


public class DBman {
    static Connection myconn;
    private PreparedStatement mypstm;
    private ResultSet myres;
    private String conn;
    private Statement stmt = null;
    private ResultSet resultset = null;
    
    
    /**
     * OMAR functions for crawling
     */
    public int GetCounter() throws Exception {
        Statement stmt;
        ResultSet resultset;
        stmt = myconn.createStatement();
        resultset = stmt.executeQuery("SELECT id FROM counter LIMIT 1;");
        resultset.next();
        return resultset.getInt(1);
    }
    
    public int GetLinkId(String x) throws Exception {
        Statement stmt;
        ResultSet resultset;
        stmt = myconn.createStatement();
        resultset = stmt.executeQuery("SELECT id FROM links WHERE link = '" + x +"'");
        //"SELECT id FROM links WHERE link = '" + x +"';"
        resultset.next();
        return resultset.getInt("id");
    }

    public int GetVisited(int id) throws Exception {
        Statement stmt;
        ResultSet resultset;
        stmt = myconn.createStatement();
        resultset = stmt.executeQuery("SELECT visited FROM links WHERE id = " + id);
        resultset.next();
        return resultset.getInt("visited");
    }


    public void IncCounter() throws Exception {
        Statement stmt;
        stmt = myconn.createStatement();
        stmt.executeUpdate("UPDATE counter SET id = id+1 LIMIT 1;");
    }
    
    public void SetCount(int x) throws Exception {
        Statement stmt;
        stmt = myconn.createStatement();
        stmt.executeUpdate("UPDATE counter SET id = " + x + " LIMIT 1;");
    }
    
    public int AddLinK(String x) throws Exception {
        Statement stmt = null;
        ResultSet resultset = null;
        stmt = myconn.createStatement();
        stmt.executeUpdate("INSERT IGNORE into links (link) VALUES ('" + x + "');", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
        //rs.next();
        //System.out.println(rs.getInt(1));
        
        
        
        if(rs.next())
        {
            return rs.getInt(1);
        }
        else
        {
            resultset = stmt.executeQuery("SELECT id FROM links WHERE link = '" + x +"';");
            resultset.next();
            return resultset.getInt(1);
        }
        
    }
    
    
    public void GetQueueAndSet(int x, Set<String> links, BlockingQueue<link> urls) throws Exception {
        Statement stmt;
        ResultSet resultset;
        stmt = myconn.createStatement();
        //int n = GetCounter();
        resultset = stmt.executeQuery("SELECT * FROM links WHERE visited <= 0 AND countdown = 0 LIMIT 10000");/*LIMIT 5000 - "+n*/
        while (resultset.next()) {
            urls.add(new link(resultset.getString("link"), resultset.getInt("id")));
        }
        Statement stmt2 = null;
        stmt2 = myconn.createStatement();
        resultset = stmt.executeQuery("SELECT * FROM links WHERE visited = 1 OR countdown >= 1 ");
        while (resultset.next()) {
            links.add(resultset.getString("link"));
        }
    }
    
    public void SetLastChanged(int id,int n) throws Exception {
        Statement stmt = null;
        ResultSet resultset = null;
        stmt = myconn.createStatement();
        if(n==1)
        {
            stmt.executeUpdate("UPDATE links SET lastchanged = lastchanged + 1 WHERE id = " + id);
        }
        else if(n==-1)
        {
            stmt.executeUpdate("UPDATE links SET lastchanged = lastchanged - 1 WHERE lastchanged > 0 AND id = " + id );
            stmt.executeUpdate("UPDATE links SET changed = 1 WHERE id = " + id );
        }
        
    }
    
    
    public void AdjustVariables(String x, String y, int id) throws Exception {
        Statement stmt = null;
        ResultSet resultset = null;
        stmt = myconn.createStatement();
        stmt.executeUpdate("UPDATE links SET visited = '1' WHERE id = " + id);
    }
    
    public void SetVisited(int id) throws Exception {
        Statement stmt = null;
        ResultSet resultset = null;
        stmt = myconn.createStatement();
        stmt.executeUpdate("UPDATE links SET visited = '1' WHERE id = " + id);
    }

    
    public void Reset() throws Exception {
        //Statement stmt = null;
        Statement stmt2 = null;
        //ResultSet resultset = null;
        //stmt = myconn.createStatement();
        stmt2 = myconn.createStatement();
        stmt2.executeUpdate("UPDATE links SET countdown = CASE WHEN countdown > 0 THEN countdown - 1 WHEN lastchanged > 0 AND countdown = 0 THEN POWER(2,lastchanged) ELSE 0 END");
        SetCount(0);
        stmt2.executeUpdate("UPDATE links SET visited = '0'");
        
    }
    
    
    //    public void PatcInsertion() throws Exception {
////        Statement stmt = null;
//        Statement stmt2 = null;
////        ResultSet resultset = null;
////        stmt = myconn.createStatement();
//        stmt2 = myconn.createStatement();
//        stmt2.executeUpdate("UPDATE links SET countdown = CASE WHEN countdown > 0 THEN countdown - 1 WHEN lastchanged > 0 AND countdown = 0 THEN POWER(2,lastchanged) ELSE 0 END");
//        SetCount(0);
//        stmt2.executeUpdate("UPDATE links SET visited = '0'");
//
//    }
    
    



    

        
        
        

        

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Get connection to  database
     */
    public DBman(){
        //conn="jdbc:mariadb://localhost:3306/thunder";
        conn="jdbc:mariadb://localhost:3306/thunder?useServerPrepStmts=false&rewriteBatchedStatements=true&rewriteBatchUpdates=true&integratedSecurity=true";
        try {
            myconn = DriverManager.getConnection(conn,"root","");
        } catch (SQLException ex) {
            Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
//        
//                try {
//            stmt = myconn.createStatement();
//            resultset = stmt.executeQuery("SHOW TABLES;");
//
//            if (stmt.execute("SHOW TABLES;")) {
//                resultset = stmt.getResultSet();
//            }
//
//            while (resultset.next()) {
//                System.out.println(resultset.getString("Tables_in_thunder"));
//            }
//        } catch (SQLException ex) {
//            // handle any errors
//            ex.printStackTrace();
//        } finally {
//            // release resources
////            if (resultset != null) {
////                try {
////                    resultset.close();
////                } catch (SQLException sqlEx) { }
////                resultset = null;
////            }
////
////            if (stmt != null) {
////                try {
////                    stmt.close();
////                } catch (SQLException sqlEx) { }
////                stmt = null;
////            }
//
////            if (conn != null) {
////                try {
////                    conn.close();
////                } catch (SQLException sqlEx) { }
////                conn = null;
////            }
//        }
        
        
        
        
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

