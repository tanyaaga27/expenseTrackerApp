package com.UserOps;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddExpenseServlet")
public class AddExpenseServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		
		
		try {
		String title = req.getParameter("title");
		String Stringamount = req.getParameter("amount");
		String category = req.getParameter("category");
		String stringdate = req.getParameter("date");
		
		
		if(title==null)
		{
			out.println("Please provide title!");
			return;
		}
		else if(Stringamount==null)
		{
			out.println("Please provide amount!");
			return;
		}
		else if(category==null)
		{
			out.println("Provide provide category!");
			return;
		}
		else if(stringdate==null)
		{
			out.println("Please provide date!");
			return;
		}
		
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringdate);
		Date todaydate = new Date();
		String todaydateString = new SimpleDateFormat("yyyy-MM-dd").format(todaydate);
		todaydate = new SimpleDateFormat("yyyy-MM-dd").parse(todaydateString);
		if(date.compareTo(todaydate)>0)
		{
			out.println("Please provide a valid date input! Do not provide future dates!");
			return;
		}
		float amount = Float.parseFloat(Stringamount);
		String notes = req.getParameter("notes");
		boolean flag = UserOperations.addorUpdateExpense(title, amount, category, stringdate, notes);
		
		if(flag==true)
		{
			out.print("Entry added Successfully!");
		}
		
		}catch(NumberFormatException e)
		{
			out.println("Invalid Input! Please Provide a number for amount");
			
		}catch(ParseException e)
		{
			out.println("Error occured while parsing the date!");
		}
		
	
			
	}

}
