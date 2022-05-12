package it.unibo.ingsoft.gwt.client.settings.studentSettings;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.Status;

public class ViewExamsMarksDashboard extends Composite {
	private Mainpage mainpage;
	private VerticalPanel mainPanel = new VerticalPanel();

	public ViewExamsMarksDashboard(Mainpage main, String info) {
		initWidget(mainPanel);
		this.mainpage = main;


		/*
		 * panel che contiene tutti i componenti 
		 */
		VerticalPanel panel = new VerticalPanel();
		/*
		 * TextArea che contiene le informazioni
		 */
		TextArea infoArea = new TextArea();
		infoArea.setText(info);
		infoArea.setPixelSize(600,500);
		infoArea.setReadOnly(true);



		/*
		 * HorizontalPanel che comprende tutti i bottoni
		 */
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		/*
		 * Buttons
		 */
		Button btnBack = new Button("BACK");
		btnBack.addClickHandler(new ClickHandler() { // BACK TO SearchPersonalInfo page or dashboard
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.clear();
				if (ActualSession.getActualSession().getActualStatus() == Status.ADMIN) {
					mainpage.openSearchPersonalInfo();
				} else {
					mainpage.openAccountDashboard(ActualSession.getActualSession().getActualStatus());
				}
			}
		});

		Button btnLogout = new Button("LOGOUT"); // BACK TO HOMEPAGE
		btnLogout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ActualSession.getActualSession().setActualSession("", Status.NOT_REGISTERED);
				mainpage.openHomepage();
			}
		});

		/*
		 * ADDING BUTTONS TO BUTTON PANEL 
		 */
		buttonsPanel.add(btnBack);
		buttonsPanel.add(btnLogout);
		buttonsPanel.setSpacing(5);

		/*
		 * ADDING ELEMENTS TO PANEL
		 */
		panel.add(new Label("VOTI DEGLI ESAMI A CUI SEI ISCRITTO."));
		panel.add(new Label("Voto -1 --> esame non ancora sostenuto."));
		panel.add(new Label("Voto inferiore a 18 --> esame non superato."));
		panel.add(new Label("Voto tra 18 e 30 --> esame superato."));
		panel.add(new Label("Voto uguale a 31 --> 30 e lode."));
		panel.add(infoArea);
		panel.add(buttonsPanel);
		panel.setSpacing(5);

		// ADDING PANEL TO MAIN PANEL
		mainPanel.add(panel);

	}


}