package com.userLogin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		String fname = req.getParameter("fname");
		String lname = req.getParameter("lname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		if(fname==null)
		{
			out.println("Please provide first name!");
			return;
		}
		else if(lname==null)
		{
			out.println("Please provide last name!");
			return;
		}
		else if(email==null)
		{
			out.println("Please provide email ID");
			return;
		}
		else if(password==null)
		{
			out.println("Please provide password");
			return;
		}
		else if(password.length()<4)
		{
			out.println("Length of the password should be 4 or more!");
			return;
		}
		int ch=0;
		int num=0;
		for(char c : password.toCharArray())
		{
			if(c >= '0' && c <= '9')
			{
				num++;
			}
			if(c >= 'A' && c <= 'Z')
			{
				ch++;
			}
		}
		if(ch==0||num==0)
		{
			out.println("Password should have atleast one number and one upper case character!");
			return;
		}
		
		if(userLoginOps.registerUser(fname, lname, email, password))
		{
			out.println("User Registered Successfully!");
		}
		
	}

}
