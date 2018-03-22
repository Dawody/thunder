/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunder;


/**
 * DatabaseManager class is responsible for getting the connection to database
 * and deliver queries to database and get the results.
 *
 * @author dawod
 */

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;


public class DBman {
    private Connection conn;
    private PreparedStatement mypstm;
    private ResultSet myres;
    private String myconn;

    /**
     * Get connection to  database
     */
    public DBman() {
        //conn="jdbc:mariadb://localhost:3306/thunder";
        myconn = "jdbc:mariadb://localhost:3306/thunder?useServerPrepStmts=false&rewriteBatchedStatements=true";
        try {
            conn = DriverManager.getConnection(myconn, "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
        }

        Statement stmt = null;
        ResultSet resultset = null;

        try {
            stmt = conn.createStatement();
            resultset = stmt.executeQuery("SHOW DATABASES;");

            if (stmt.execute("SHOW DATABASES;")) {
                resultset = stmt.getResultSet();
            }

            while (resultset.next()) {
                System.out.println(resultset.getString("Database"));
            }
        }
        catch (SQLException ex){
            // handle any errors
            ex.printStackTrace();
        }
        finally {
            // release resources
            if (resultset != null) {
                try {
                    resultset.close();
                } catch (SQLException sqlEx) { }
                resultset = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }
                stmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) { }
                conn = null;
            }
        }
    }




}