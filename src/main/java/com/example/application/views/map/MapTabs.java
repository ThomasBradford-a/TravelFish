package com.example.application.views.map;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;

public class MapTabs extends VerticalLayout {

	public MapTabs(){
		
		
		Tab Fish = new Tab(
			VaadinIcon.ANCHOR.create(),
			new Span("Fish")
		);
		Tab Weather = new Tab(
			VaadinIcon.SUN_O.create(),
			new Span("Weather")
		);
		Tab Tides = new Tab(
			VaadinIcon.MOON_O.create(),
			new Span("Tides")
		);
		
		// Set the icon on top
		for (Tab tab : new Tab[] { Fish, Weather, Tides }) {
			tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		}

		Tabs tabs = new Tabs(Fish, Weather, Tides);
		
		
		/*Fish.getElement().addEventListener(click, //event
				->{
					
				});
		add (tabs);
		*/
		
	}
}
