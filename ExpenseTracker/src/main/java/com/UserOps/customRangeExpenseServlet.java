package com.UserOps;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;


@WebServlet("/customRangeExpenseServlet")
public class customRangeExpenseServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		String startDateString = req.getParameter("startdate");
		String endDateString = req.getParameter("enddate");
		
		
		Iterable<Entity> list = UserOperations.customRangeExpense(startDateString, endDateString);
		
		if(list==null)
		{
			out.println("No expenses made in this time period");
			return;
		}
		
		int sum = 0;
		for(Entity e : list)
		{
			String amountString = e.getProperty("amount").toString();
			sum = sum + Integer.parseInt(amountString);
		}
		if(sum!=0)
		{
			out.println("Total expense for the range:"+sum);
		}
		else
	    {
	    	out.print("No expenses were made in this time period!");
	    }
	}
	
	
}
