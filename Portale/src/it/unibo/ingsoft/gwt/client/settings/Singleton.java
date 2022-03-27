package it.unibo.ingsoft.gwt.client.settings;

import com.google.gwt.core.client.GWT;

public class Singleton {
	private static GreetingServiceAsync greetingService;
	
	public Singleton() {}
	
	public static synchronized GreetingServiceAsync getGreetingService() {
		if (greetingService == null) {
			greetingService = GWT.create(GreetingService.class);
		}
		return greetingService;
	}
}
