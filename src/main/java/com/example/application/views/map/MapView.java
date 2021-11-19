package com.example.application.views.map;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.dom.Element;

import org.vaadin.elmot.flow.sensors.GeoLocation;
import org.vaadin.elmot.flow.sensors.Position;

import com.example.application.components.leafletmap.LeafletMap;
import com.vaadin.flow.router.Route;

import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;

import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;

@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout {

	private static final int EARTH_RADIUS = 6371;
	
	
	
    private LeafletMap map = new LeafletMap();

    public MapView() {
    	
        setSizeFull();
        setPadding(false);
        map.setSizeFull();
        map.setView(55.0, 10.0, 4);
        add(map);
        
        IPGeolocationAPI api = new IPGeolocationAPI("4960d2a06473426eb14c8863cc45e9b4");
    	
    	// Get geolocation for IP address (1.1.1.1) and fields (geo, time_zone and currency)
    	   GeolocationParams geoParams = new GeolocationParams();
    	   // need to create dynamic way of obtaining user's IP address 
    	   geoParams.setIPAddress("2001:8004:1500:1144:b151:aebf:9cf:3a56");
    	   geoParams.setFields("geo,time_zone,currency");

    	  Geolocation geolocation = api.getGeolocation(geoParams);

    	   // Check if geolocation lookup was successful
    	    if(geolocation.getStatus() == 200) {
    	       System.out.println(geolocation.getLongitude());
    	       System.out.println(geolocation.getLatitude());
    	   } else {
    	       System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
    	   }
        
        map.setView(Double.parseDouble(geolocation.getLatitude()), Double.parseDouble(geolocation.getLongitude())
        		, 100);
    }
    
    
    
   
 
}
