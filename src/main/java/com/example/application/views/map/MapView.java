package com.example.application.views.map;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONObject;

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

		String[] sunTime = new String[2];
		try {
			sunTime = getSunTime(geolocation.getLatitude(), geolocation.getLongitude());
			System.out.println("Sun rise time is: " + sunTime[0] + "(UTC), Sunset time is: " + sunTime[1] + "(UTC)");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// tide API call
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://tides.p.rapidapi.com/tides?longitude=" + geolocation.getLongitude()
						+ "&latitude=" + geolocation.getLatitude() + "&interval=60&duration=1440"))
				.header("x-rapidapi-host", "tides.p.rapidapi.com")
				// NEED TO UNCOMMENT FOR TIDE API TO WORK
				//.header("x-rapidapi-key", "f479f3ee79mshc51d938348a653fp109fb3jsneee9a580813f")
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bottomTabs(sunTime);

	}

	// returns array with information containing time of sunrise and sunset
	// NOTE: time is in UTC, therefore add 11 hours to convert to local Melbourne
	// time
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

	private void bottomTabs(String[] sunTime) {

		Tab Fish = new Tab("Fish");
		Tab Sun = new Tab("Sunrise and Set");
		Tab Tide = new Tab("Tides");

		Tabs tabs = new Tabs(false, Fish, Sun, Tide);

		Text newText = new Text("");
		//Text oldText = new Text("");
		tabs.addSelectedChangeListener(event -> {
			if (sunTime != null) {
				if (event.getSelectedTab().getLabel().equals("Sunrise and Set")) {
					newText.setText(
							"Sun rise time is: " + sunTime[0] + "(UTC), Sunset time is: " + sunTime[1] + "(UTC)");
				} else if (event.getSelectedTab().getLabel().equals("Tides")) {
					newText.setText("tide info");
				} else if (event.getSelectedTab().getLabel().equals("Fish")) {
					newText.setText("");
				}
			}
		});

		add(tabs, newText);
	}
}
