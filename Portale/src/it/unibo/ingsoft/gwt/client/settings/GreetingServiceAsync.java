package it.unibo.ingsoft.gwt.client.settings;

import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.shared.Status;

public interface GreetingServiceAsync {
	
	void clearDB(AsyncCallback<String> callback);

	void adminInitialize(AsyncCallback<String> callback);
	
	void doLogin(String email, String password, AsyncCallback<Status> callback);

	void addDepartmentToDB(AsyncCallback<String> callback);

	void addUserToDB(AsyncCallback<String> callback);
}
