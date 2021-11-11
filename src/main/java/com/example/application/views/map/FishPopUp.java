package com.example.application.views.map;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;


public class FishPopUp extends FormLayout {

	public FishPopUp(){
		
		Dialog dialog = new Dialog();
		dialog.add(new Text("Close me with the esc-key or an outside click"));

		dialog.setWidth("400px");
		dialog.setHeight("150px");

	}
}
