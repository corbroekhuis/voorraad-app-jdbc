# Spring Boot september 2024

## Voorraad Applicatie
* [https://github.com/corbroekhuis/voorraad-app.git](https://github.com/corbroekhuis/voorraad-app.git)

<br/><br/>
<br/><br/>

#### <a id="top"></a>

* [EAN generator](#ean)
* [DTO pattern](#dto)
* [H2 properties](#properties)
* [@Scheduled](#scheduled)

<br/><br/>
<br/><br/>
<br/><br/>
<br/><br/>

## <a id="ean"></a>EAN Generator
**_NOTE:_**  Maakt gebruik van de `@Value` annotatie om de waarde uit de properties te halen.
```java
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
```

* [Top](#top)

## <a id="dto"></a>Data Transfer Object

* [https://www.baeldung.com/java-dto-pattern](https://www.baeldung.com/java-dto-pattern)

[![Alt text](https://www.baeldung.com/wp-content/uploads/2021/08/layers-4.svg)](## "Where did the image go..")


<br/><br/>
<br/><br/>
<br/><br/>
<br/><br/>

* [Top](#top)

## <a id="properties"></a>Properties

```properties
server.port=8081

ean.aansluitnummer=12345

spring.application.name=voorraad-app

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.h2.console.enabled=true
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:file:C:/Temp/voorraad
spring.datasource.username=sa
spring.datasource.password=sa
spring.jpa.hibernate.ddl-auto=create
```

* [Top](#top)

## <a id="scheduled"></a>Scheduled tasks
* [https://www.baeldung.com/spring-scheduled-tasks](https://www.baeldung.com/spring-scheduled-tasks)

```java
@Configuration
@EnableScheduling
public class RestockScheduler {

    @Scheduled(fixedDelay = 1000)
    //@Scheduled(fixedRate = 1000)
    //@Scheduled(cron = "0 15 10 15 * ?")
    public void RestockTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
```

<br/><br/>

* [Top](#top)
* @JsonProperty(access = JsonProperty.Access.READ_ONLY)


{
"name": "Fat Bike",
"description": "Gevaarlijk vervoermiddel",
"articleNumber": "55555",
"stock": 10,
"minimumStock": 3
}


