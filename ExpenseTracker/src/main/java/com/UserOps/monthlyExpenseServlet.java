package com.UserOps;



import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;


@WebServlet("/monthlyExpenseServlet")
public class monthlyExpenseServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		Iterable<Entity> list = UserOperations.calMonthlyExpense();
		
		if(list==null)
		{
			out.println("No expenses made in this month!");
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
			out.println("Total expense for this month:"+sum);
		}
		else
	    {
	    	out.print("No expenses were made in this month");
	    }
		
	}

}
