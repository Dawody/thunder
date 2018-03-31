package thunder;


import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;


public class DBman {
    static Connection conn;

    public int GetCounter() throws Exception {
        Statement stmt;
        ResultSet resultset;
        stmt = conn.createStatement();
        resultset = stmt.executeQuery("SELECT id FROM counter LIMIT 1;");
        resultset.next();
        return resultset.getInt(1);
    }
    public int GetLinkId(String x) throws Exception {
        Statement stmt;
        ResultSet resultset;
        stmt = conn.createStatement();
        resultset = stmt.executeQuery("SELECT id FROM links WHERE link = '" + x +"'");
//        "SELECT id FROM links WHERE link = '" + x +"';"
        resultset.next();
        return resultset.getInt("id");
    }

    public int GetVisited(int id) throws Exception {
        Statement stmt;
        ResultSet resultset;
        stmt = conn.createStatement();
        resultset = stmt.executeQuery("SELECT visited FROM links WHERE id = " + id);
        resultset.next();
        return resultset.getInt("visited");
    }





    public void IncCounter() throws Exception {
        Statement stmt;
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE counter SET id = id+1 LIMIT 1;");
    }

    public void SetCount(int x) throws Exception {
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE counter SET id = " + x + " LIMIT 1;");
    }

    public int AddLinK(String x) throws Exception {
        Statement stmt = null;
        ResultSet resultset = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT IGNORE into links (link) VALUES ('" + x + "');", Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
//        rs.next();
//        System.out.println(rs.getInt(1));



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
        Statement stmt = null;
        ResultSet resultset = null;
        stmt = conn.createStatement();
//        int n = GetCounter();
        resultset = stmt.executeQuery("SELECT * FROM links WHERE visited <= 0 AND countdown = 0");/*LIMIT 5000 - "+n*/
        while (resultset.next()) {
            urls.add(new link(resultset.getString("link"), resultset.getInt("id")));
        }
        Statement stmt2 = null;
        stmt2 = conn.createStatement();
        resultset = stmt.executeQuery("SELECT * FROM links WHERE visited = 1 OR countdown >= 1 ");
        while (resultset.next()) {
            links.add(resultset.getString("link"));
        }
    }

    public void SetLastChanged(int id,int n) throws Exception {
        Statement stmt = null;
        ResultSet resultset = null;
        stmt = conn.createStatement();
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
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE links SET visited = '1' WHERE id = " + id);
    }

    public void SetVisited(int id) throws Exception {
        Statement stmt = null;
        ResultSet resultset = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE links SET visited = '1' WHERE id = " + id);
    }





    public void Reset() throws Exception {
//        Statement stmt = null;
        Statement stmt2 = null;
//        ResultSet resultset = null;
//        stmt = conn.createStatement();
        stmt2 = conn.createStatement();
        stmt2.executeUpdate("UPDATE links SET countdown = CASE WHEN countdown > 0 THEN countdown - 1 WHEN lastchanged > 0 AND countdown = 0 THEN POWER(2,lastchanged) ELSE 0 END");
        SetCount(0);
        stmt2.executeUpdate("UPDATE links SET visited = '0'");

    }

//    public void PatcInsertion() throws Exception {
////        Statement stmt = null;
//        Statement stmt2 = null;
////        ResultSet resultset = null;
////        stmt = conn.createStatement();
//        stmt2 = conn.createStatement();
//        stmt2.executeUpdate("UPDATE links SET countdown = CASE WHEN countdown > 0 THEN countdown - 1 WHEN lastchanged > 0 AND countdown = 0 THEN POWER(2,lastchanged) ELSE 0 END");
//        SetCount(0);
//        stmt2.executeUpdate("UPDATE links SET visited = '0'");
//
//    }



    public DBman() {
        //conn="jdbc:mariadb://localhost:3306/thunder";
        String myconn = "jdbc:mariadb://localhost:3306/thunder";
        try {
            conn = DriverManager.getConnection(myconn, "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(DBman.class.getName()).log(Level.SEVERE, null, ex);
        }

        Statement stmt = null;
        ResultSet resultset = null;

        try {
            stmt = conn.createStatement();
            resultset = stmt.executeQuery("SHOW TABLES;");

            if (stmt.execute("SHOW TABLES;")) {
                resultset = stmt.getResultSet();
            }

            while (resultset.next()) {
                System.out.println(resultset.getString("Tables_in_thunder"));
            }
        } catch (SQLException ex) {
            // handle any errors
            ex.printStackTrace();
        } finally {
            // release resources
//            if (resultset != null) {
//                try {
//                    resultset.close();
//                } catch (SQLException sqlEx) { }
//                resultset = null;
//            }
//
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException sqlEx) { }
//                stmt = null;
//            }

//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException sqlEx) { }
//                conn = null;
//            }
        }
    }


}