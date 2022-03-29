package it.unibo.ingsoft.gwt.client.settings;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.users.User;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	
	String clearDB();
	
	String adminInitialize();
	
	Status doLogin(String email, String password);
	
	String addUserToDB(User u);
	
	
	String addInfoToStudentAccount(String email, String iD, String username, 
				String name, String surname, Date birthday);
	
	String addInfoToProfessorAccount(String email, String username, 
				String name, String surname, Date birthday);
	
	// TODO:
		String addDepartmentToDB();

	
}
