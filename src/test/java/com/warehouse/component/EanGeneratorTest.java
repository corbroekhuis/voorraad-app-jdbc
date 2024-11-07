package com.warehouse.component;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EanGeneratorTest {

    private static EanGenerator eanGenerator;

    @BeforeAll
    public void initBeforeAll(){
        eanGenerator = new EanGenerator("11999");
    }

    @BeforeEach
    public  void initBeforeEach(){

    }

    @Test
    public void eanGeneratorValidTest() throws Exception {
        String expected = "8711999430110";
        String actual = eanGenerator.newEan("43011");
        assertEquals( expected, actual);

    }

    @Test
    public void eanGeneratorValidTestTooLong() {
        try {
            String actual = eanGenerator.newEan("4301133");
        } catch (Exception e) {
            assertEquals("Artikelnummer te lang", e.getMessage());
        }
    }
}