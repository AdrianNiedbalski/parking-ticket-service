package org.parkingticketservice.parkingticketserivce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ParkingTicketSerivceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingTicketSerivceApplication.class, args);
    }

}
