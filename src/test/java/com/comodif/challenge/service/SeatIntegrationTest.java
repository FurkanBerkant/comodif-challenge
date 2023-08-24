package com.comodif.challenge.service;

import com.comodif.challenge.exception.SeatAlreadyBookedException;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.sql.SQLException;


@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@Transactional
public class SeatIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConcurrentSeatBooking() throws InterruptedException {
        Long seatId = 1L;

        Thread passenger1Thread = new Thread(() -> {
            try {
                paymentService.bookSeat(seatId);
            } catch (SeatAlreadyBookedException e) {
                e.printStackTrace();
            }
        });

        Thread passenger2Thread = new Thread(() -> {
            try {
                paymentService.bookSeat(seatId);
            } catch (SeatAlreadyBookedException e) {
                e.printStackTrace();
            }
        });

        passenger1Thread.start();
        passenger2Thread.start();

        passenger1Thread.join();
        passenger2Thread.join();
    }
    @AfterEach
    public void tearDown() throws SQLException {
        if (dataSource instanceof EmbeddedDatabase) {
            ((EmbeddedDatabase) dataSource).shutdown();
            dataSource.getConnection().close();

        }
    }
}
