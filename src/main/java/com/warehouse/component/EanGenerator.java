package com.warehouse.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EanGenerator {

    @Value("${ean.aansluitnummer}")
    String aansluitNummer;

    public String newEan( String articleNumber) {

        StringBuilder sb = new StringBuilder();

        sb.append("87");
        sb.append(aansluitNummer);
        // padleft
        sb.append( String.format("%1$" + 5 + "s", articleNumber).replace(' ', '0'));
        sb.append("0");

        return sb.toString();
    }
}
