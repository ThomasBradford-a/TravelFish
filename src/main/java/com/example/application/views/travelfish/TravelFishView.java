package com.example.application.views.travelfish;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("TravelFish")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class TravelFishView extends VerticalLayout {

	// message of the day string
	static String MOTD = "NOTICE: Minor flood warning for the Murray River";
	
    public TravelFishView() {
        setSpacing(false);

        Div div = new Div();
        div.add(new H3("Message of the Day: "));
        div.add(new H3(MOTD));
        add(div);
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        add(new Paragraph(""));
        
        
        Image img = new Image("images/TravelFishLogo.png", "Travel Fish Logo");
        img.setWidth("200px");
        add(img);

        add(new H2("Welcome to TravelFish"));
        add(new Paragraph("Select an item on the side bar to start"));
        

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
