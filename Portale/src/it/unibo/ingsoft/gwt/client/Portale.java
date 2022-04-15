package it.unibo.ingsoft.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import it.unibo.ingsoft.gwt.shared.domain.University;

public class Portale implements EntryPoint {

public static final University uni = new University("Alma Mater Studiorum", "Bologna","051 209 9111","Via Zamboni, 33 - 40126");

  /**
   * Entry point method.
   */
  public void onModuleLoad() {
	  Mainpage mainpage = new Mainpage();
	  RootPanel.get().add(mainpage);
  }
  
}