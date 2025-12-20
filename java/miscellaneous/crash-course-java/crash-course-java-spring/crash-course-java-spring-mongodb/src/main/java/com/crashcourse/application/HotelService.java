package com.crashcourse.application;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.Document;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crashcourse.domain.model.Book;
import com.crashcourse.domain.model.Hotel;
import com.crashcourse.domain.repository.BookRepository;
import com.crashcourse.domain.repository.GuestRepository;
import com.crashcourse.domain.repository.HotelRepository;
import com.crashcourse.domain.repository.RoomRepository;
import com.crashcourse.infra.logging.Loggable;

@Service
@Loggable(value = LogLevel.INFO)
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;

    private final BookRepository bookRepository;

    private final GuestRepository guestRepository;

    private final RoomRepository roomRepository;

    HotelService(
            final HotelRepository hotelRepository, final BookRepository bookRepository,
            final GuestRepository guestRepository, final RoomRepository roomRepository) {
        super();
        this.hotelRepository = hotelRepository;
        this.bookRepository = bookRepository;
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Page<Hotel> findBy(final Document document, final Pageable page) {
        return hotelRepository.findAll(document, page);
    }

    @Transactional
    public void save(final Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Transactional
    public void book(final String guestId, final String roomId, final LocalDateTime begin, final LocalDateTime end) {

        final var room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException(""));

        final var guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException(""));

        final var book = new Book.Builder().with($ -> {
            $.guest = guest;
            $.room = room;
            $.begin = begin;
            $.end = end;
            // $.payment = ;
        }).build();

        bookRepository.save(book);
    }

    @Transactional
    public void book(final Book book) {
        bookRepository.save(book);
    }

    public Hotel findById(final String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(""));
    }

}
