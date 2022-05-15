package it.unibo.ingsoft.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import it.unibo.ingsoft.gwt.client.settings.Singleton;
import it.unibo.ingsoft.gwt.shared.domain.University;

public class Portale implements EntryPoint {
	
	public static final University uni = new University("Alma Mater Studiorum", "Bologna","051 209 9111","Via Zamboni, 33 - 40126");

	/*
	 * Entry point method.
	 */
	public void onModuleLoad() {
		doAdminInitialize();
		Mainpage mainpage = new Mainpage();
		RootPanel.get().add(mainpage);
		
	}
  
	private static void doAdminInitialize() {
		Singleton.getGreetingService().adminInitialize(new AsyncCallback<String> () {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR in initializing admin account: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				System.out.println("Admin initializing success: " + result);
			}
			
		});
	}
}