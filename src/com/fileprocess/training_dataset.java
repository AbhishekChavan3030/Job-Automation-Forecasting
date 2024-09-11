package com.fileprocess;

import java.io.*;
import java.sql.Connection;
import java.sql.Statement;

import com.connection.Dbconn;

public class training_dataset {

	public static void main(String[] args) {
		String line = "";  
		String splitBy = ",";  
		try   
		{  
			Connection con = Dbconn.conn();
			Statement st1 = (Statement) con.createStatement();
			int i=0;
		//parsing a CSV file into BufferedReader class constructor  
		BufferedReader br = new BufferedReader(new FileReader("G:\\BE2023-2024\\240496 cs g118 job automation Final year project\\job_automation_probability.csv"));  
		while ((line = br.readLine()) != null)   //returns a Boolean value  
		{  
			String[] parts = line.split(",");    // use comma as separator  
			if (i == 0) {
				i++;
			} else {
				System.out.println(line);
				
					String Query = "insert into tbl_job_train_data(Education_data,Occupation,Short_occupation,Rank_values,Code_Values,Prob_Values,Average_annual_wage,Len_values,Probability,NumbEmployed,Median_ann_wage,Employed_may2016,Average_ann_wage) values('"
							+ parts[0].replace("@0@",",")
							+ "','"
							+ parts[1].replace("@0@",",")
							+ "','"
							+ parts[2].replace("@0@",",")
							+ "','"
							+ parts[3]
							+ "','"
							+ parts[4]
							+ "','"
							+ parts[5]
							+ "','"
							+ parts[6]
							+ "','"
							+ parts[7]
							+ "','"
							+ parts[8]
							+ "','"
							+ parts[9]
							+ "','"
							+ parts[10]
							+ "','"
							+ parts[11]
							+ "','"
							+ parts[12]
							+ "')";
					st1.executeUpdate(Query);
			
			}
		
		}  
		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		}  
		
		
	}

}
