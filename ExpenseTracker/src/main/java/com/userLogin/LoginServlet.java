package com.userLogin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{
		PrintWriter out = res.getWriter();
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		if(email==null)
		{
			out.println("Email cannot be empty!");
			return;
		}
		if(password==null)
		{
			out.println("Password cannot be empty!");
		}
		
	   Iterable<Entity> list = userLoginOps.loginUser(email, password);
	   boolean flag=false;
	   for(Entity e : list)
	   {
		   if(flag==true)
			   break;
		   flag = true;
		   
	   }
	   if(flag==true)
	   {
		   RequestDispatcher rd = req.getRequestDispatcher("WelcomeDisplay.html");
		   rd.forward(req, res);
	   }
	   else 
	   {
		   out.println("User does not exist! Try again!");
		   return;
	   }
	 
	   
	}

}
