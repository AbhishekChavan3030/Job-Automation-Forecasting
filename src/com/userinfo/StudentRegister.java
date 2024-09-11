package com.userinfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.connection.Dbconn;

/**
 * Servlet implementation class StudentRegister
 */
@WebServlet("/StudentRegister")
public class StudentRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String name = request.getParameter("txtName");
		String addres = request.getParameter("txtAddress");
		String email = request.getParameter("txtEmail");
		String gender = request.getParameter("rdoGender");
		String pwwd = request.getParameter("passPassword");
		String cntct = request.getParameter("txtContact");
		try {
			Connection con = Dbconn.conn();
			
			Statement st3 = con.createStatement();
		   st3.executeUpdate("insert into studentdetails(name,address,gender,email,contactno,password) values('" + name
					+ "' , '" + addres + "'  , '" + gender + "' , '" + email
					+ "' ," + cntct + " , '" + pwwd
					+ "')");
		    pw.println("<html><script>alert('Register Successfully');</script><body>");
			pw.println("");
			pw.println("</body></html>");
			
		} catch (Exception e) {

			e.printStackTrace();
		} 
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.include(request, response); 
	}

}
