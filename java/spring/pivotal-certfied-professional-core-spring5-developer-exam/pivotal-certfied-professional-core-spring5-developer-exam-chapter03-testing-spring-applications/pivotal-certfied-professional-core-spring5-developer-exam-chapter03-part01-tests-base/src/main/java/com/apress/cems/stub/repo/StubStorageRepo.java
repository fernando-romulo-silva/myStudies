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
package com.apress.cems.stub.repo;

import com.apress.cems.dao.Storage;
import com.apress.cems.repos.StorageRepo;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class StubStorageRepo extends StubAbstractRepo<Storage> implements StorageRepo {
    @Override
    public Optional<Storage> findByName(String name) {
	switch (name) {
	case "Here":
	    return Optional.of(records.get(2L));
	case "There":
	    return Optional.of(records.get(3L));
	}
	return Optional.empty();
    }

    @Override
    public Storage update(Storage entity) {
	throw new NotImplementedException("Not needed for this stub.");
    }

    @Override
    public Optional<Storage> findByLocation(String location) {
	throw new NotImplementedException("Not needed for this stub.");
    }
}
