package com.warehouse.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EanGenerator {

    private String aansluitNummer;

    @Autowired
    public EanGenerator(@Value("${ean.aansluitnummer}") String aansluitNummer) {
        this.aansluitNummer = aansluitNummer;
    }

    public String newEan( String articleNumber) throws IOException {

        StringBuilder sb = new StringBuilder();

        if( articleNumber.length() > 5){
            throw new IOException("Artikelnummer te lang");
        }

        sb.append("87");
        sb.append(aansluitNummer);
        // padleft
        sb.append( String.format("%1$" + 5 + "s", articleNumber).replace(' ', '0'));
        sb.append("0");

        return sb.toString();
    }
}
