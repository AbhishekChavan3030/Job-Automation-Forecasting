package com.algo;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.connection.Dbconn;

public class SimilarityAlgorithm {
	public static ArrayList<String> results = new ArrayList<>();

    public static void main(String[] args) {
        String testing = "Actors";
        try {
        	 Connection con;
			con = Dbconn.conn();
			NB.trainingdata(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        ArrayList<String> trainingrule = new ArrayList<>();
//        trainingrule.add("Grinding@0@Lapping");
//        trainingrule.add("Separating@0@Filtering@0@Clarifying@0@ Precipitating and Still Machine Setters@0@ Operators and Tenders");
//        trainingrule.add("Sales Representatives@0@ Wholesale and Manufacturing@0@ Except Technical and Scientific Products");
//        trainingrule.add("Actors");
        // Calculate similarities
        Map<Integer, Double> similarities = new HashMap<>();
//        for (String rule : NB.trainingrule) {
//            double similarity = calculateSimilarity(testing, rule);
//            System.out.println(rule+"="+similarity);
//            similarities.put(rule, similarity);
//        }
        DecimalFormat df = new DecimalFormat("#.##");
        int id=1;
        for(int i=0;i<NB.trainingrule.size();i++)
        {
        	String rule=NB.trainingrule.get(i);
        	double similarity = calculateSimilarity(testing, rule);
        	String score=df.format(similarity);
        	double scores=Double.parseDouble(score)*100;
            System.out.println(i+rule+"=>" + scores);
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

    private static double calculateSimilarity(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return 1.0 - (double) dp[s1.length()][s2.length()] / Math.max(s1.length(), s2.length());
    }
}
