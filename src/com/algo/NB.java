package com.algo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

import com.connection.Dbconn;

public class NB {
	public static HashMap<Integer, Double> capitalCities = new HashMap<Integer, Double>();
	public static ArrayList<String> trainingrule = new ArrayList<>();
	public static ArrayList<String> results = new ArrayList<>();
	public static void trainingdata(Connection con)
	{
		try {
			Statement st1 = (Statement) con.createStatement();
		ResultSet rs=st1.executeQuery("select * from tbl_job_train_data");
		while(rs.next())
		{
			trainingrule.add(rs.getString("Occupation"));
			
		}
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void  NBprobability(String testing)
	{
		DecimalFormat df = new DecimalFormat("#.##");
		trainingrule.clear();
		results.clear();
		   Connection con;
		try {
			con = Dbconn.conn();
			trainingdata(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
      HashMap<Integer, Double> similarities = new HashMap<>();
     int id=1;
      for(int i=0;i<trainingrule.size();i++)
        {
        	String rule=trainingrule.get(i);
        	double similarity = calculateSimilarity(testing, rule);
        	String score=df.format(similarity);
        	double scores=Double.parseDouble(score)*100;
        //    System.out.println(i+rule+"=>" + scores);
            similarities.put(id, scores);
            id++;
        }

      List<Entry<Integer, Double>> entryList = new ArrayList<>(similarities.entrySet());
      entryList.sort(Entry.comparingByValue(Comparator.reverseOrder()));

      System.out.println("Top 5 values:");
      for (int i = 0; i < Math.min(5, entryList.size()); i++) {
          Entry<Integer, Double> entry = entryList.get(i);
          System.out.println(entry.getKey() + " => " + entry.getValue());
          String str=entry.getKey()+"@"+entry.getValue();
          results.add(str);
      }
	}
	
	
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("#.##");
		  String testing = "Wholesale,Still Manufacturing Grinding Lapping Filtering Clarifying Precipitating Sales";
		  Connection con;
		try {
			con = Dbconn.conn();
			trainingdata(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        HashMap<Integer, Double> similarities = new HashMap<>();
       
        for(int i=0;i<trainingrule.size();i++)
          {
          	String rule=trainingrule.get(i);
          	double similarity = calculateSimilarity(testing, rule);
          	String score=df.format(similarity);
          	double scores=Double.parseDouble(score)*100;
          //    System.out.println(i+rule+"=>" + scores);
              similarities.put(i, scores);
          }

        System.out.println("Similarity scores:");
        for (Map.Entry<Integer, Double> entry : similarities.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
        List<Entry<Integer, Double>> entryList = new ArrayList<>(similarities.entrySet());
        entryList.sort(Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println("Top 5 values:");
        for (int i = 0; i < Math.min(5, entryList.size()); i++) {
            Entry<Integer, Double> entry = entryList.get(i);
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }
    }

    public static double calculateSimilarity(String s1, String s2) {
        int editDistance = calculateEditDistance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        return 1.0 - (double) editDistance / maxLength;
    }

    private static int calculateEditDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }
}