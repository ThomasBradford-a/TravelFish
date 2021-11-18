package com.example.application.views.map;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.elmot.flow.sensors.GeoLocation;
import org.vaadin.elmot.flow.sensors.Position;

import com.example.application.components.leafletmap.LeafletMap;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.MainLayout;

@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout {

	
    private LeafletMap map = new LeafletMap();

    public MapView() {
    	
        setSizeFull();
        setPadding(false);
        map.setSizeFull();
        map.setView(55.0, 10.0, 4);
        add(map);
        
        geoLocater();
        
        //Position test = thisLocation.getValue();
        //map.setView(geoLocation.getValue().getLongitude(), geoLocation.getValue().getLatitude(), 1);
        
    }
    
    // Won't store valid coordinates, not sure if it is because it's not being deployed on real web server
    public Position geoLocater() {
    	
    	 GeoLocation thisLocation = new GeoLocation();
         thisLocation.setWatch(true);
         thisLocation.setHighAccuracy(true);
         thisLocation.setTimeout(100000);
         thisLocation.setMaxAge(200000);
         thisLocation.addValueChangeListener( e->{});
         thisLocation.addErrorListener( e->{});
         add(thisLocation);
         
        System.out.println("Location is: " + thisLocation);
         
        Position location = thisLocation.getValue();
         
		return location;
    }
 
}
