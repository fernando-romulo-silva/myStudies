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
package com.apress.cems.stub.util;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Person;
import com.apress.cems.util.*;

import java.time.LocalDateTime;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class TestObjectsBuilder {

    public static Detective buildDetective(String firstName, String lastName, Rank rank, String badgeNumber) {
	var detective = new Detective();
	var person = new Person();
	person.setFirstName(firstName);
	person.setLastName(lastName);
	person.setHiringDate(LocalDateTime.now());
	person.setUsername(firstName.concat(lastName));
	person.setPassword("whatever");
	detective.setPerson(person);
	detective.setBadgeNumber(badgeNumber);
	detective.setArmed(true);
	detective.setStatus(EmploymentStatus.ACTIVE);
	detective.setRank(rank);
	return detective;
    }

    public static CriminalCase buildCase(Detective leadInvestigator, CaseType caseType, CaseStatus status) {
	var criminalCase = new CriminalCase();
	criminalCase.setLeadInvestigator(leadInvestigator);
	criminalCase.setNumber(NumberGenerator.getEvidenceNumber());
	criminalCase.setType(caseType);
	criminalCase.setStatus(status);
	return criminalCase;
    }
}
