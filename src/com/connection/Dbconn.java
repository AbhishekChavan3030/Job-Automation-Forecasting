/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.connection;

import java.sql.*;
import java.util.TreeSet;

public class Dbconn {

    public static tring Risk_Level= " ";
    public static tring Growth= " ";
    public static String Wages = "";
    public static String Volume = "";
    public static String Polling = "";
    public static double Job_Score = 0.0;

    public static String Job_score = "";

    public Dbconn() throws SQLException {
        // initComponents();
        // Connection con;

    }

    public static Connection conn() throws Exception {
        Connection con;

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/240496_cs_g118_job_automation_prediction_model",
                "root", "abhishek");

        return (con);

    }

    public static int g1 = 0, g2 = 0, g3 = 0, g4 = 0, g5 = 0, g6, g7 = 0, g8, g9;
    public static int count = 0;

    public static void main(String[] args) {
        TreeSet<String> list = new TreeSet<String>();
        try {
            Connection con = Dbconn.conn();
            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery("select * from traindata");
            while (rs1.next()) {
                count = count + 1;

                list.add(rs1.getString("International_plan"));

            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println(list);

    }

}
