package com.crashcourse.domain.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Payment {

     @Id
     private String id;

     private BigDecimal value;
}
