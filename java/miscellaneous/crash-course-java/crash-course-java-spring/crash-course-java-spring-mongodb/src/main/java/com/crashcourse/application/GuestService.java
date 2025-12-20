package com.crashcourse.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crashcourse.domain.repository.GuestRepository;

@Service
@Transactional(readOnly = true)
public class GuestService {

    private final GuestRepository guestRepository;

    GuestService(final GuestRepository guestRepository) {
        super();
        this.guestRepository = guestRepository;
    }
}
