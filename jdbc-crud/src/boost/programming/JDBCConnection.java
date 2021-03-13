package boost.programming;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marshad
 */
public class JDBCConnection {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            //step1 load the driver class  
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //step2 create  the connection object  
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
            if (conn == null) {
                System.out.println("Unable to Connect with database");
                return null;
            }
            System.out.println("Connected with database");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        try {
            Connection conn = JDBCConnection.getConnection();
            insRegion(conn);
            getRegion(conn);
            updRegion(conn);
            delRegion(conn);
            //close the connection object
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void getRegion(Connection conn) {
        try {
            // create the statement object
            Statement stmt = conn.createStatement();
            //execute query
            ResultSet rs = stmt.executeQuery("select * from hr.regions");

            System.out.println("Id \t Name");

            while (rs.next()) {
                System.out.println(rs.getInt("REGION_ID") + "\t" + rs.getString("REGION_NAME"));
            }
            //close statement
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insRegion(Connection conn) {
        PreparedStatement stmt = null;
        try {
            String query = "insert into  hr.regions(region_id, region_name) values(?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, 10);
            stmt.setString(2, "Pakistan");
            stmt.executeUpdate();

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delRegion(Connection conn) {
        PreparedStatement stmt = null;
        try {
            String query = "delete from  hr.regions  where region_id=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, 10);
            stmt.executeUpdate();

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updRegion(Connection conn) {
        PreparedStatement stmt = null;
        try {
            String query = "update  hr.regions  set region_name=? where region_id=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "Pakistan Region");
            stmt.setInt(2, 3);
            stmt.executeUpdate();

            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
