package com.comodif.challenge.service;
import com.comodif.challenge.entity.dto.BankPaymentRequest;
import com.comodif.challenge.entity.dto.BankPaymentResponse;
import com.comodif.challenge.entity.Seat;
import com.comodif.challenge.exception.SeatAlreadyBookedException;
import com.comodif.challenge.exception.SeatNotFoundException;
import com.comodif.challenge.repository.PaymentRepository;
import com.comodif.challenge.entity.Payment;
import com.comodif.challenge.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class PaymentService {

    private final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final BankService bankService;
    private final SeatRepository seatRepository;
    private final PaymentRepository paymentRepository;
    public void bookSeat(Long seatId) {
        logger.info("BASLA - Koltuk icin koltuk rezervasyon islemi: " + seatId);

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException("Kimlikle koltuk bulunamadi: " + seatId));

        if (seat.isBooked()) {
            throw new SeatAlreadyBookedException("Koltuk coktan rezerve edildi.");
        }

        seat.setBooked(true);
        seatRepository.save(seat);

        logger.info("BITIS - Koltuk basarıyla rezerve edildi: " + seatId);
    }

    @Transactional
    public void pay(BigDecimal price) {
        List<Seat> bookedSeats = seatRepository.findByBookedTrue();

        for (Seat seat : bookedSeats) {
            logger.info("BASLA - Rezerve edilen koltuk icin odeme sureci: " + seat.getSeatId());

            BankPaymentRequest request = new BankPaymentRequest();
            request.setPrice(price);
            BankPaymentResponse response = bankService.pay(request);
            persistPaymentWithRetry(price, response.getResultCode());
            logger.info("BITIS - Odeme koltuk icin basarıyla kaydedildi: " + seat.getSeatId());
            }
        }
    @Retryable(value = {SQLException.class}, maxAttempts = 3, backoff = @Backoff(delay = 100))
    private void persistPaymentWithRetry(BigDecimal price, String resultCode) {
        Payment payment = new Payment();
        payment.setBankResponse(resultCode);
        payment.setPrice(price);
        paymentRepository.save(payment);
    }

    }

