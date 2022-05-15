package it.unibo.ingsoft.gwt.shared.usersfacade;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import it.unibo.ingsoft.gwt.client.settings.Singleton;

public class SecretaryFacade {
	// Variabili istanza
	private static SecretaryFacade secretaryFacade;

	// Ritorna l'istanza di professorFacade, se e' null la istanzia.
	public static synchronized SecretaryFacade getSecretaryFacade() {
		if (secretaryFacade == null) {
			secretaryFacade = new SecretaryFacade();
		}
		return secretaryFacade;
	}

	public void setMarksVisibility(String examName) {
		Singleton.getGreetingService().setExamsMarksVisibilityVisible(examName, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR SETTING EXAMS' MARKS VISIBILITY: " + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				Window.alert("Tryed setting exams' marks visibility.\nResult: " + result);
			}
			
		});
	}

	
	
	
}
