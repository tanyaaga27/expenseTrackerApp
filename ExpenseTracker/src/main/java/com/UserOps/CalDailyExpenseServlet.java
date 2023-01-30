package com.UserOps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;




@WebServlet("/CalDailyExpenseServlet")
public class CalDailyExpenseServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		
		PrintWriter out = res.getWriter();
		
		int sum = 0;
		Iterable<Entity> list = UserOperations.CalDailyExpense();
		for(Entity e : list)
		{
			String amountString = e.getProperty("amount").toString();
			sum = sum + Integer.parseInt(amountString);
		}
	    
	    if(sum!=0)
	    {
	    	out.println("Total expense for the date :"+sum);
	    }
	    else
	    {
	    	out.print("No expenses were made on this date!");
	    }
	    
	    
		
	}

}
