package com.fileprocess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.connection.Dbconn;

public class risk {

	public static String StringTokenizerShow(String id,Connection con)
	{
		String word="";
		
		StringBuilder sb=new StringBuilder();
		try {
			con = Dbconn.conn();
		
		Statement stat1 = con.createStatement();
		String qrry1 = "select * from tbl_job_train_data where ID='"+id+"'";
		ResultSet rs1 = stat1.executeQuery(qrry1);
		if(rs1.next()) 
		{
			String word1=rs1.getString("Occupation").replace(",","");
			
			StringTokenizer st = new StringTokenizer(word1," ");  
		     while (st.hasMoreTokens()) {  
		         
		         String data=st.nextToken().trim();
		         sb.append(data).append("-");
		     }  
		     word = sb.toString().substring(0, sb.toString().length() - 1);
		     System.out.println(word);
		   
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return word;
	}
	
	public static void main(String[] args) {
		
		String id="2";
		Connection con;
		StringBuilder sb=new StringBuilder();
		try {
			con = Dbconn.conn();
		
		Statement stat1 = con.createStatement();
		String qrry1 = "select * from tbl_job_train_data where ID='"+id+"'";
		ResultSet rs1 = stat1.executeQuery(qrry1);
		if(rs1.next()) 
		{
			String word1=rs1.getString("Occupation").replace(",","");
			System.out.println(word1);
			StringTokenizer st = new StringTokenizer(word1," ");  
		     while (st.hasMoreTokens()) {  
		         
		         String data=st.nextToken().trim();
		         sb.append(data).append("-");
		     }  
		     String word = sb.toString().substring(0, sb.toString().length() - 1);
		     System.out.println(word);
		     String j=calculatejobscore(word);
		   
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static double Risk_Score(double probability, int numberEmployed,
			double medianAnnualWage, int employedMay2016,
			double averageAnnualWage) {
		// Calculate risk score
		double riskScore = (probability * numberEmployed * averageAnnualWage)
				+ medianAnnualWage + employedMay2016;
		return riskScore;
	}

	public static double calculateHourlyWages(double averageAnnualWage) {
		// Assuming a standard 40-hour work week and 52 weeks per year
		int hoursPerWeek = 40;
		int weeksPerYear = 52;
		double hourlyWage = averageAnnualWage / (hoursPerWeek * weeksPerYear);
		double wage = calculateWages(hourlyWage);
		System.out.println(wage + "Hourly wage at average annual wage:"
				+ hourlyWage);
		return hourlyWage;
	}

	public static double calculateVolume(int numbEmployed, int len,
			double probability, double averageAnnualWage, int median_ann_wage,
			int employed_may2016, double average_ann_wage) {
		// Example formula: Volume = numbEmployed * len * probability *
		// averageAnnualWage * median_ann_wage * employed_may2016 *
		// average_ann_wage
		return numbEmployed * len * probability * averageAnnualWage
				* median_ann_wage * employed_may2016 * average_ann_wage;
	}
	public static String calculatepolling(String word) {
		String polling = null;
		try {
			String url = "https://willrobotstakemyjob.com/" + word;
			Document doc = Jsoup.connect(url).get();
			Element pollingElement = doc
					.selectFirst("div#PanelPolling div:nth-child(2)");
			polling = pollingElement != null ? pollingElement.text() : "N/A";

		} catch (IOException e) {
			System.err.println("Error fetching data: " + e.getMessage());
		}
		return polling;
	}
	public static String calculatejobscore(String word) {
		String jobscore = null;
		try {
			String url = "https://willrobotstakemyjob.com/" + word;
			Document doc = Jsoup.connect(url).get();
			Element jobScoreElement = doc.selectFirst("div:contains(SUMMARY JOB SCORE)");
			jobscore = jobScoreElement.text();
			String[] d=jobscore.split("SUMMARY JOB SCORE");
			StringTokenizer st = new StringTokenizer(d[1]," ");  
		     while (st.hasMoreTokens()) {  
		         
		         String data=st.nextToken().trim();
		         if(data.contains("/"))
		         {
		        	 jobscore=data;
		        	 break;
		         }
		         else
		         {}
		     }
	        System.out.println("Job Score: " + jobscore);

		} catch (IOException e) {
			System.err.println("Error fetching data: " + e.getMessage());
		}
		return jobscore;
	}

	public static String calculateGrowth(String word) {
		String growth = null;
		try {
			String url = "https://willrobotstakemyjob.com/" + word;
			Document doc = Jsoup.connect(url).get();
			Element growthElement = doc.selectFirst("div.occupation-badge[data-badge-number='1'] div:nth-child(2)");
             growth = growthElement != null ? growthElement.text() : "N/A";


		} catch (IOException e) {
			System.err.println("Error fetching data: " + e.getMessage());
		}
		return growth;
	}
	
	public static String calculateWages(String word) {
		String Wages = null;
		try {
			String url = "https://willrobotstakemyjob.com/" + word;
			Document doc = Jsoup.connect(url).get();
			 Element wagesElement = doc.selectFirst("div.occupation-badge[data-badge-number='2'] div:nth-child(2)");
	          Wages = wagesElement != null ? wagesElement.text() : "N/A";


		} catch (IOException e) {
			System.err.println("Error fetching data: " + e.getMessage());
		}
		return Wages;
	}
	
	public static String calculaterisk(String word) {
		String risk = null;
		try {
			String url = "https://willrobotstakemyjob.com/" + word;
			Document doc = Jsoup.connect(url).get();
			Element riskScoreElement = doc.selectFirst("div.occupation-badge[data-badge-number='4'] div:nth-child(2)");
            risk = riskScoreElement != null ? riskScoreElement.text() : "N/A";


		} catch (IOException e) {
			System.err.println("Error fetching data: " + e.getMessage());
		}
		return risk;
	}
	public static String calculateVolumes(String word) {
		String volume = null;
		try {
			String url = "https://willrobotstakemyjob.com/" + word;
			Document doc = Jsoup.connect(url).get();
			Element volumeElement = doc
					.selectFirst("div.occupation-badge[data-badge-number='3'] div:nth-child(2)");
			volume = volumeElement != null ? volumeElement.text() : "N/A";

		} catch (IOException e) {
			System.err.println("Error fetching data: " + e.getMessage());
		}
		return volume;
	}

	public static double calculategrowth(double principal, double rate,
			int years) {
		return principal * Math.pow(1 + rate, years);
	}

	private static double calculateWages(double wagePerHour) {
		return wagePerHour * 40 * 52; // Assuming 40 hours per week and 52 weeks
										// per year
	}

}
