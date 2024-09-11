package com.algo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.connection.Dbconn;
import com.fileprocess.risk;
public class OccupationDataJ {
	
	public static Map<String, Object> row1 = new HashMap<>();
	public static Map<String, Object> row2 = new HashMap<>();
	public static Map<String, Object> results = new HashMap<>();
	public  static List<Map<String, Object>> data = new ArrayList<>();
	
	public static void Automation_Risk_process(double prob,int average_ann_wage,int numbEmployed, double probability,int len)
	{
		try {
			
		row1.put("prob", prob);
        row1.put("average_ann_wage", average_ann_wage);
        row1.put("numbEmployed", numbEmployed);
        row1.put("probability", probability);
        row1.put("len", len);
        // Calculate risk level, growth, wages, volume, polling, and job score
        row2.put("Risk Level", calculateRiskLevel((double) row1.get("prob")));
        row2.put("Growth", calculateGrowth((int) row1.get("len")));
        row2.put("Wages", calculateWages((int) row1.get("average_ann_wage")));
        row2.put("Volume", calculateVolume((int) row1.get("numbEmployed")));
        row2.put("Polling", calculatePolling((double) row1.get("probability")));
        row2.put("Job Score", calculateJobScore(row2));

        // Add the row to the data list
        data.add(row2);

        // Display the data
        for (Map<String, Object> row : data) {
           
//            System.out.println("Risk Level: " + row.get("Risk Level"));
//            System.out.println("Growth: " + row.get("Growth"));
//            System.out.println("Wages: " + row.get("Wages"));
//            System.out.println("Volume: " + row.get("Volume"));
//            System.out.println("Polling: " + row.get("Polling"));
//            System.out.println("Job Score: " + row.get("Job Score"));
//            System.out.println();
            results.put("Risk Level", row.get("Risk Level"));
            results.put("Growth", row.get("Growth"));
            results.put("Wages", row.get("Wages"));
            results.put("Volume", row.get("Volume"));
            results.put("Polling", row.get("Polling"));
            results.put("Job Score", row.get("Job Score"));
            
            
//            Dbconn.Risk_Level=(String) row.get("Risk Level");
//            Dbconn.Growth=(String)row.get("Growth");
//            Dbconn.Wages=(String)row.get("Wages");
//            Dbconn.Volume=(String)row.get("Volume");
//            Dbconn.Polling=(String)row.get("Polling");
            
        }
		
		} catch (Exception e) {
		System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
        

        // Sample data
		String id="700";
      
		Connection con;
		try {
			con = Dbconn.conn();
		
		Statement stat1 = con.createStatement();
		String qrry1 = "select * from tbl_job_train_data where ID='"+id+"'";
		ResultSet rs1 = stat1.executeQuery(qrry1);
		if(rs1.next()) 
		{
			
			double prob=rs1.getDouble("Prob_Values");
			int average_ann_wage=rs1.getInt("Average_annual_wage");
			int numbEmployed=rs1.getInt("NumbEmployed");
			double probability=rs1.getDouble("Probability");
			int len=rs1.getInt("Len_values");
			OccupationDataJ.Automation_Risk_process( prob, average_ann_wage, numbEmployed,  probability, len);
			
		}
		} catch (Exception e) {
			System.out.println(e);
		}
        

       
    }

    private static String calculateRiskLevel(double prob) {
        if (prob < 0.3) {
            return "Low";
        } else if (prob < 0.7) {
            return "Medium";
        } else {
            return "High";
        }
    }

    private static String calculateGrowth(int len) {
        if (len < 3) {
            return "Low";
        } else if (len < 5) {
            return "Medium";
        } else {
            return "High";
        }
    }

    private static String calculateWages(double average_ann_wage) {
        if (average_ann_wage < 50000) {
            return "Low";
        } else if (average_ann_wage < 100000) {
            return "Medium";
        } else {
            return "High";
        }
    }

    private static String calculateVolume(int numbEmployed) {
        if (numbEmployed < 50000) {
            return "Low";
        } else if (numbEmployed < 100000) {
            return "Medium";
        } else {
            return "High";
        }
    }

    private static String calculatePolling(double probability) {
        if (probability < 0.5) {
            return "Negative";
        } else {
            return "Positive";
        }
    }

    private static double calculateJobScore(Map<String, Object> row) {
        // Example calculation: Sum of risk level, growth, wages, and volume
        double riskScore = getScore(row.get("Risk Level"));
        double growthScore = getScore(row.get("Growth"));
        double wagesScore = getScore(row.get("Wages"));
        double volumeScore = getScore(row.get("Volume"));

        return riskScore + growthScore + wagesScore + volumeScore;
    }

    private static double getScore(Object value) {
        switch ((String) value) {
            case "Low":
                return 1;
            case "Medium":
                return 2;
            case "High":
                return 3;
            default:
                return 0;
        }
    }
}