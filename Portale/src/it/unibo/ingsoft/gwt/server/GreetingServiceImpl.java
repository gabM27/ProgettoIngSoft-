package it.unibo.ingsoft.gwt.server;


import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import it.unibo.ingsoft.gwt.client.settings.GreetingService;
import it.unibo.ingsoft.gwt.shared.Status;
import it.unibo.ingsoft.gwt.shared.users.User;


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
		return null;
	}


	@Override
	public String addUserToDB(User u) {
		return UniDB.addUsers(u);
	}
	
	@Override
	public String addInfoToStudentAccount(String email, String iD, String username, String name,
					String surname, Date birthday) {
		return UniDB.addPersonalInfoToStudent(email, iD, username, name, surname, birthday);
	}
	
	@Override
	public String addInfoToProfessorAccount(String email, String username, String name,
			String surname, Date birthday){
		return UniDB.addPersonalInfoToProfessor(email, username, name, surname, birthday);
	}
}
