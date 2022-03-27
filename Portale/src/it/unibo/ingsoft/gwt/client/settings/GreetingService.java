package it.unibo.ingsoft.gwt.client.settings;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import it.unibo.ingsoft.gwt.shared.Status;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	
	String clearDB();
	
	String adminInitialize();
	
	Status doLogin(String email, String password);
	// TODO:
	String addDepartmentToDB();
	
	String addUserToDB();
	
	
}
