package com.userLogin;

import javax.servlet.RequestDispatcher;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;

public class userLoginOps {
	
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	static boolean registerUser(String fname, String lname, String email, String password)
	{
		Entity newUser = new Entity("userDetails");
		newUser.setProperty("fname", fname);
		newUser.setProperty("lname", lname);
		newUser.setProperty("email", email);
		newUser.setProperty("password", password);
		ds.put(newUser);
		return true;
		
	}
	
	static Iterable<Entity> loginUser(String email, String password)
	{
		Filter emailFilter = new FilterPredicate("email",FilterOperator.EQUAL,email);
		Filter passwordFilter = new FilterPredicate("password",FilterOperator.EQUAL, password);
		CompositeFilter loginFilter = CompositeFilterOperator.and(emailFilter, passwordFilter);
		Query query = new Query("userDetails").setFilter(loginFilter);
		PreparedQuery pq = ds.prepare(query);
		Iterable<Entity> list = pq.asIterable();
		return list;
       
	}

}
