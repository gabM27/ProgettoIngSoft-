package it.unibo.ingsoft.gwt.client.settings;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.users.User;

public interface GreetingServiceAsync {
	
	void clearDB(AsyncCallback<String> callback);

	void adminInitialize(AsyncCallback<String> callback);
	
	void doLogin(String email, String password, AsyncCallback<Status> callback);

	void addDepartmentToDB(AsyncCallback<String> callback);

	void addUserToDB(String emailInput, String passwordInput, String accountType, AsyncCallback<String> callback);

	void addInfoToStudentAccount(String email, String iD, String username, String name,
				String surname, Date birthday, AsyncCallback<String> asyncCallback);

	void addInfoToProfessorAccount(String email, String username, String name, 
				String surname, Date birthday, AsyncCallback<String> asyncCallback);
}
