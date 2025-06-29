/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.apress.cems.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apress.cems.dao.Storage;
import com.apress.cems.repos.StorageRepo;
import com.apress.cems.services.impl.SimpleStorageService;

/**
 * @author Iuliana Cosmina
 * @since 1.0 Description: using old-style Mockito mocks with JUnit 5
 */
class SimpleStorageServiceTest {
    static final Long STORAGE_ID = 1L;

    private StorageRepo mockRepo = mock(StorageRepo.class);

    private SimpleStorageService storageService;

    @BeforeEach
    void setUp() {
	storageService = new SimpleStorageService();
	storageService.setRepo(mockRepo);
    }

    @Test
    void findByIdPositive() {
	var storage = new Storage();
	storage.setId(STORAGE_ID);

	when(mockRepo.findById(any(Long.class))).thenReturn(Optional.of(storage));

	Storage result = storageService.findById(STORAGE_ID);
	assertAll(() -> assertNotNull(result), () -> assertEquals(storage.getId(), result.getId()));
    }

}
