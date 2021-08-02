package com.apress.prospring5.ch18.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.apress.prospring5.ch18.FluxGenerator;
import com.apress.prospring5.ch18.test.config.TestConfig;

/**
 * Created by iuliana.cosmina on 8/6/17.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class JUnit5IntegrationTest {

    @Autowired
    FluxGenerator fluxGenerator;

    @Test
    public void testGenerator() {
	List<String> list = fluxGenerator.generate("2", "3").collectList().block();
	assertEquals(2, list.size());
    }
}
