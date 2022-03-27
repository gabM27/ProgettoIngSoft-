package it.unibo.ingsoft.gwt.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import it.unibo.ingsoft.gwt.client.settings.GreetingService;
import it.unibo.ingsoft.gwt.shared.Status;


public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	private static final long serialVersionUID = 1L;

	@Override
	public String clearDB() {
		if (UniDB.clearUniDB()) {
			return "DB cleaned";
		} else {
			return "An error occurred cleaning DB";
		}
	}

	@Override
	public String adminInitialize() {
		UniDB.adminUserInitialize();
		return "ADMIN inizializzato";
	}

	@Override
	public Status doLogin(String email, String password) {
		return UniDB.loginRequest(email, password);
	}	
	
// TODO:
	@Override
	public String addDepartmentToDB() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addUserToDB() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
