package com.UserOps;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.FetchOptions;

public class UserOperations {
	static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	
	
	
	public static boolean addorUpdateExpense(String title,float amount,String category,String dateString,String notes) throws ParseException 
	{
		
	
		
		Entity entity = new Entity("expense",title);
		entity.setProperty("title", title);
		entity.setProperty("amount", amount);
		entity.setProperty("category", category);
		Date date;
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			System.out.println(date);
            entity.setProperty("date", date);
            if(notes!=null)
			entity.setProperty("notes", notes);
            else
            entity.setProperty("notes", "NA");		
            datastore.put(entity);
			return true;
			
	}
	
	public static boolean deleteExpense(String title)
	{
		try {
			Key key = KeyFactory.createKey("expense", title);
			datastore.delete(key);
			return true;
		}catch(Exception e)
		{
			System.out.print(e);
			return false;
		}
		
	}
	
	public static Iterable<Entity> viewAllExpenses()
	{
		Query query = new Query("expense");
		PreparedQuery pq = datastore.prepare(query);
		Iterable<Entity> list = pq.asIterable(FetchOptions.Builder.withLimit(10));
		return list;
		
	}
	
	public static Iterable<Entity> CalDailyExpense()
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateString = df.format(date);
		Iterable<Entity> list = null ;
		try {
			date = df.parse(currentDateString);
			Filter filter = new FilterPredicate("date", FilterOperator.EQUAL,date);
			Query query = new Query("expense").setFilter(filter);
			list = datastore.prepare(query).asIterable();
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return list;
	}
	
	public static Iterable<Entity> customRangeExpense(String startDateString, String endDateString)
	{
		Iterable<Entity> list = null;
		try {
			Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
			//System.out.println("startdate"+startDate);
			Filter startDateFilter = new FilterPredicate("date",FilterOperator.GREATER_THAN_OR_EQUAL,startDate);
			Filter endDateFilter = new FilterPredicate("date",FilterOperator.LESS_THAN_OR_EQUAL,endDate);
			CompositeFilter dateRangeFilter = CompositeFilterOperator.and(startDateFilter,endDateFilter);
			Query query = new Query("expense").setFilter(dateRangeFilter);
			list = datastore.prepare(query).asIterable();
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static Iterable<Entity> calWeeklyExpense() throws ParseException
	{
		Date date = new Date();
		int dayNum = date.getDay();
		String currentDateString;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<Date> arr = new ArrayList<>();
		Iterable<Entity> list = null ;
		long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
		
		do {
			currentDateString = df.format(date);
			date = df.parse(currentDateString);
			arr.add(date);
			date = new Date(date.getTime() - MILLIS_IN_A_DAY);
			dayNum--;
			
		}while(dayNum>0);
		
		Filter filter = new FilterPredicate("date", FilterOperator.IN,arr);
		Query query = new Query("expense").setFilter(filter);
		list = datastore.prepare(query).asIterable();
		return list;
	}
	
	public static Iterable<Entity> calMonthlyExpense() 
	{
		Date date = new Date();
		int dateNum = date.getDate();
		String currentDateString;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<Date> arr = new ArrayList<>();
		Iterable<Entity> list = null ;
		long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
		
		try {
		do {
			currentDateString = df.format(date);
			date = df.parse(currentDateString);
			arr.add(date);
			date = new Date(date.getTime() - MILLIS_IN_A_DAY);
			dateNum--;
			
		}while(dateNum>=1);
		
		Filter filter = new FilterPredicate("date", FilterOperator.IN,arr);
		Query query = new Query("expense").setFilter(filter);
		list = datastore.prepare(query).asIterable();
		} catch(ParseException e)
		{
			e.printStackTrace();
			
		}
		
		return list;
		
	}
	

}
