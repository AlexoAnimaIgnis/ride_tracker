package com.pluralsight.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout=30000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/ride_tracker_war/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}

	@Test(timeout=30000)
	public void testCreateRides() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = new Ride();
		ride.setName("Taylor Swift Valley Ride");
		ride.setDuration(45);

		ride = restTemplate.postForObject("http://localhost:8080/ride_tracker_war/ride",
				ride,
				Ride.class);
		System.out.println("Ride: " + ride);
	}

	@Test(timeout = 30000)
	public void testGetRide() {
		RestTemplate template = new RestTemplate();

		Ride ride = template.getForObject("http://localhost:8080/ride_tracker_war/ride/5",
				Ride.class);
		System.out.print(ride.getName());
	}

	@Test(timeout = 30000)
	public void testUpdateRide() {
		RestTemplate template = new RestTemplate();

		Ride ride = template.getForObject("http://localhost:8080/ride_tracker_war/ride/5",
				Ride.class);

		ride.setDuration(ride.getDuration() + 2);
		template.put("http://localhost:8080/ride_tracker_war/ride", ride);
		System.out.print(ride.getName() + "-" + ride.getDuration());
	}

}
