package com.UserOps;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddExpenseServlet")
public class AddExpenseServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		PrintWriter out = res.getWriter();
		String title = req.getParameter("title");
		String amount = req.getParameter("amount");
		String category = req.getParameter("category");
		String stringdate = req.getParameter("date");
		String notes = req.getParameter("notes");
		//out.println("User input Date="+stringdate);
		boolean flag = UserOperations.addorUpdateExpense(title, amount, category, stringdate, notes);
		if(flag==true)
		{
			out.print("Entry added Successfully!");
		}
		
		
		/*	Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
			KeyFactory keyFactory = datastore.newKeyFactory().setKind("expense");
			Key key = keyFactory.newKey(title);
			Entity entity = Entity.newBuilder(key).set("title",title).set("amount",amount).set("category",category).set("expenseDate", Timestamp.parseTimestamp(stringdate)).set("notes",notes).build();
			datastore.put(entity);
			out.println("Entry added Successfully!");
		*/
			 
			
	}

}
