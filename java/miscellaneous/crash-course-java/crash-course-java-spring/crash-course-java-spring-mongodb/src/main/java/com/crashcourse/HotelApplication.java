package com.crashcourse;

import static java.lang.System.out;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.crashcourse.application.HotelService;
import com.crashcourse.domain.model.Guest;
import com.crashcourse.domain.model.Hotel;
import com.crashcourse.domain.model.Room;
import com.crashcourse.domain.model.RoomType;
import com.crashcourse.domain.repository.GuestRepository;
import com.crashcourse.domain.repository.HotelRepository;
import com.crashcourse.domain.repository.RoomRepository;

@EnableWebMvc
@EnableAutoConfiguration
@SpringBootApplication
public class HotelApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private GuestRepository guestRepository;

	@Override
	public void run(String... args) throws Exception {

		guestRepository.deleteAll();
		hotelRepository.deleteAll();
		roomRepository.deleteAll();

		final var guest01 = guestRepository.save(new Guest("Alice", "5555-5555"));
		final var guest02 = guestRepository.save(new Guest("Bob", "6666-6666"));
		final var guest03 = guestRepository.save(new Guest("John", "7777-7777"));

		// save a couple of Hotels
		final var room01 = roomRepository.save(new Room("R22", (short) 2, RoomType.BASIC));
		final var room02 = roomRepository.save(new Room("L51", (short) 5, RoomType.STANDARD));
		final var room03 = roomRepository.save(new Room("R12", (short) 10, RoomType.LUXURY));
		final var room04 = roomRepository.save(new Room("L44", (short) 4, RoomType.BASIC));
		final var room05 = roomRepository.save(new Room("R26", (short) 2, RoomType.STANDARD));
		final var room06 = roomRepository.save(new Room("C6", (short) 6, RoomType.BASIC));

		final var hotel01 = new Hotel.Builder().with($ -> {
			$.name = "Copacabana Palace";
			$.city = "Rio de Janeiro";
			$.rooms = Arrays.asList(room01, room02, room03);
			$.score = 8;
		}).build();

		final var hotel02 = new Hotel.Builder().with($ -> {
			$.name = "Fasano";
			$.city = "Sao Paulo";
			$.rooms = Arrays.asList(room04, room05, room06);
			$.score = 9;
		}).build();

		hotelService.save(hotel01);
		hotelService.save(hotel02);

		out.println(hotel01.getId());

		final var t01Begin = LocalDateTime.of(2024, 10, 01, 14, 00);
		final var t01End = LocalDateTime.of(2024, 10, 10, 12, 00);

		hotelService.book(guest01.getId(), room01.getId(), t01Begin, t01End);

		// fetch all guests
		out.println("Guests found with findAll():");
		out.println("-------------------------------");

		for (final var guest : guestRepository.findAll()) {
			out.println(guest);
		}

		// fetch all guests
		out.println("Hotels found with findAll():");
		out.println("-------------------------------");

		for (final var hotel : hotelRepository.findAll()) {
			out.println(hotel);
		}

	}

}
