package com.fileprocess;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algo.OccupationDataJ;
import com.connection.Dbconn;

/**
 * Servlet implementation class Automation_Risk
 */
@WebServlet("/Automation_Risk")
public class Automation_Risk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Automation_Risk() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		Connection con;
		try {
			con = Dbconn.conn();
		
		Statement stat1 = con.createStatement();
		String qrry1 = "select * from tbl_job_train_data where ID='"+id+"'";
		ResultSet rs1 = stat1.executeQuery(qrry1);
		if(rs1.next()) 
		{
			String word=risk.StringTokenizerShow(id,con);
			int numberEmployed=rs1.getInt("NumbEmployed");
			double probability=rs1.getDouble("Probability");
			
			int len=rs1.getInt("Len_values");
			
//	        double medianAnnualWage =rs1.getDouble("Median_ann_wage");
//	        int employedMay2016 = rs1.getInt("Employed_may2016");
	        double averageAnnualWage = rs1.getDouble("Average_ann_wage");
//	        double growthRate = 0.3; // Assuming 3% growth rate per year
	        DecimalFormat decfor = new DecimalFormat("0.00");
			OccupationDataJ.Automation_Risk_process( probability, (int) averageAnnualWage, numberEmployed,  probability, len);
	       
	       Dbconn.Risk_Level=risk.calculaterisk(word);
	      
	       Dbconn.Wages=risk.calculateWages(word);
	      
	        Dbconn.Growth=risk.calculateGrowth(word);
	       
	        Dbconn.Volume=risk.calculateVolumes(word);
	        Dbconn.Job_score=risk.calculatejobscore(word);
	        String[] p=risk.calculatepolling(word).split("%");
	        Dbconn.Polling=p[0]+"%";
	        
	        
		}
		} catch (Exception e) {
			System.out.println(e);
		}
		/*PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('Upload Data Set Successfully');");
		out.println("</script>");*/
		RequestDispatcher rd = request
				.getRequestDispatcher("/Admin_ShowPage.jsp");
		rd.include(request, response);
	}

}
