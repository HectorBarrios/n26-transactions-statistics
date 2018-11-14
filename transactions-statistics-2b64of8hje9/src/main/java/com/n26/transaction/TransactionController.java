package com.n26.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;

@RestController
public class TransactionController {

    private static final long PAST_TIME_LIMIT_IN_SECONDS = 60;

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "transactions", method = RequestMethod.POST)
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        ZonedDateTime now = OffsetDateTime.now(UTC).toZonedDateTime();
        long diff = now.toEpochSecond() - transaction.getTimestamp().toEpochSecond();

        // Validate transactions older than 60 seconds
        if (diff >= PAST_TIME_LIMIT_IN_SECONDS) return new ResponseEntity(null, HttpStatus.NO_CONTENT);

        // Validate transactions with future date
        if (diff < 0) return new ResponseEntity(null, HttpStatus.UNPROCESSABLE_ENTITY);

        transactionService.add(transaction);

        return new ResponseEntity(null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "transactions", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllTransactions() {
        transactionService.deleteAll();

        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}
