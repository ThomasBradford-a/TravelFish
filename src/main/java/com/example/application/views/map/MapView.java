package com.example.application.views.map;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.dom.Element;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import org.vaadin.elmot.flow.sensors.GeoLocation;
import org.vaadin.elmot.flow.sensors.Position;

import com.example.application.components.leafletmap.LeafletMap;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

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

		GeolocationParams geoParams = new GeolocationParams();

		// String userIP = VaadinSession.getCurrent().getBrowser().getAddress();
		// geoParams.setIPAddress(userIP);
		// would be used to dynamically acquire user's IP address

		// manual IP address added for testing purposes
		geoParams.setIPAddress("2001:8004:1500:1144:b151:aebf:9cf:3a56");
		geoParams.setFields("geo,time_zone,currency");

		Geolocation geolocation = api.getGeolocation(geoParams);

		// Check if geolocation lookup was successful
		if (geolocation.getStatus() == 200) {
			System.out.println(geolocation.getLongitude());
			System.out.println(geolocation.getLatitude());
		} else {
			System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
		}

		map.setView(Double.parseDouble(geolocation.getLatitude()), Double.parseDouble(geolocation.getLongitude()), 100);

		
		try {
			String[] sunTime = getSunTime(geolocation.getLatitude(), geolocation.getLongitude());
			System.out.println("Sun rise time is: " + sunTime[0] + ", Sunset time is: " + sunTime[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String[] getSunTime(String lati, String longi) throws IOException {

		String[] sunTime = new String[2];

		String s = "https://api.sunrise-sunset.org/json?lat=" + lati + "&lng=" + longi;
		URL url = new URL(s);

		// read from the URL
		Scanner scan = new Scanner(url.openStream());
		String str = new String();
		while (scan.hasNext()) {
			str += scan.nextLine();
		}
		scan.close();

		JSONObject obj = new JSONObject(str);

		String sunRise = obj.getJSONObject("results").getString("sunrise");
		String sunSet = obj.getJSONObject("results").getString("sunset");
		sunTime[0] = sunRise;
		sunTime[1] = sunSet;

		return sunTime;
	}

}
