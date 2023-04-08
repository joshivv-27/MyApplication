package com.example.abcd.myapplication.helper;

import java.sql.*;

/**
 * Created by abcd on 2/23/17.
 */

public class MySQLDBHelper {

    public MySQLDBHelper() {

        String databaseURL = "jdbc:mysql://198.71.57.109:3306/htspl_ondemandcrm_co";//"jdbc:mysql://mysql3.gear.host/testdbhorus";
        String user = "hoffice_manager";
        String password = "DsfR4EVfVtrLBPJ5";

        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(databaseURL, user, password);

            if (conn != null) {
                System.out.println("Connected to the database");

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from contacts");

                ResultSetMetaData rsmd = rs.getMetaData();

                System.out.println("Contacts : \n");
                while(rs.next()) {

                    System.out.println(rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n");
                    System.out.println(rsmd.getColumnName(2) + ": " + rs.getInt(2) + "\n");
                    System.out.println(rsmd.getColumnName(3) + ": " + rs.getInt(3) + "\n");
                }

                //System.out.println("Contacts : \n");

            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();

        } catch (ClassNotFoundException e) {
            System.out.println("Could not find database driver class");
            e.printStackTrace();
        } finally {

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }

    }

}
