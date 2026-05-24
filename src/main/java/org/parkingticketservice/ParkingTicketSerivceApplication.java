package org.parkingticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
public class ParkingTicketSerivceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingTicketSerivceApplication.class, args);
    }

}
