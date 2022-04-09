package it.unibo.ingsoft.gwt.client.settings.professorSettings;

import java.util.Date;

import org.apache.james.mime4j.field.DateTimeField;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import it.unibo.ingsoft.gwt.client.Mainpage;
import it.unibo.ingsoft.gwt.client.settings.ActualSession;
import it.unibo.ingsoft.gwt.shared.Status;

public class CreateChangeTestFormProfessorDashboard extends Composite{
	private Mainpage mainpage; // Riferimento alla mainpage
	private VerticalPanel mainPanel = new VerticalPanel(); // mainPanel che comprende tutti gli elementi della dashboard
	private Date data;
	private String ora;
	private String durezza;
	private String aulaSvolgimento;
	//private String esame;

	public CreateChangeTestFormProfessorDashboard(Mainpage main) {
		this.mainpage = main;
		initWidget(this.mainPanel);
		/*
		 * formPanel : contiene tutti gli elementi necessari per aggiungere un nuovo account.
		 * buttonPanel: contiene tutti i bottoni.
		 */
		VerticalPanel formPanel = new VerticalPanel();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		
		
		/*
		 * DATA 
		 */
		Label dataLbl = new Label("Data :");
		DatePicker dataPick = new DatePicker();
		dataPick.setYearAndMonthDropdownVisible(true);
		dataPick.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				// DEBUG
				Window.alert("Hai selezionato " + dataPick.getHighlightedDate());
				data = dataPick.getHighlightedDate();
			}
		});
		
		/*
		 * ORA 
		 */
		Label oraLbl = new Label("ora:");
		TextBox oraTB = new TextBox();
		oraTB.setText("descrizione");
		oraTB.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				ora = oraTB.getText();
			}
		});
		
		/*
		 * DUREZZA
		 */
		 Label durezzaLbl = new Label("Inserimento difficoltà esame:");
		 TextBox durezzaTB = new TextBox();
		 durezzaTB.setText("");
		 durezzaTB.addChangeHandler(new ChangeHandler() {
			 @Override
			 public void onChange(ChangeEvent event) {
				 durezza = durezzaTB.getText();
			 }
		 });
		 
		 /*
		  * AULA SVOLGIMENTO
		  */
		 Label aulaSvolgimentoLbl = new Label("Inserimento difficoltà esame:");
		 TextBox aulaSvolgimentoTB = new TextBox();
		 aulaSvolgimentoTB.setText("");
		 aulaSvolgimentoTB.addChangeHandler(new ChangeHandler() {
				 @Override
				 public void onChange(ChangeEvent event) {
					 aulaSvolgimento = aulaSvolgimentoTB.getText();
				 }
		 });
		 
		 /*
		  * BUTTONS
		  */
			Button btnSend = new Button("SEND"); // Send info to DB
			btnSend.addClickHandler(new SendInfoHandler());
			Button btnBack = new Button("BACK");
			btnBack.addClickHandler(new ClickHandler() { // BACK TO ADMIN DASHBOARD
				@Override
				public void onClick(ClickEvent event) {
					mainPanel.clear();
					mainpage.openAccountDashboard(ActualSession.getActualSession().getActualStatus());
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
			 * ADDING ELEMENTS TO FORM PANEL
			 */
			formPanel.add(dataLbl);
			formPanel.add(dataPick);
			formPanel.add(oraLbl);
			formPanel.add(oraTB);
			formPanel.add(durezzaLbl);
			formPanel.add(durezzaTB);
			formPanel.add(aulaSvolgimentoLbl);
			formPanel.add(aulaSvolgimentoTB);
			formPanel.setSpacing(5);
			
			/*
			 * ADDING ELEMENTS TO BUTTON PANEL
			 */
			buttonPanel.add(btnSend);
			buttonPanel.add(btnBack);
			buttonPanel.add(btnLogout);
			buttonPanel.setSpacing(5);
			
			/*
			 * ADDING PANELS TO MAIN PANEL
			 */
			this.mainPanel.add(formPanel);
			this.mainPanel.add(buttonPanel);
	
	}
	
	private class SendInfoHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
		}

	}

}
