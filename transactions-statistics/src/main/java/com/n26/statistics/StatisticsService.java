package com.n26.statistics;

import com.n26.transaction.Transaction;
import com.n26.utils.AverageUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.time.ZoneOffset.UTC;

@Service
public class StatisticsService {

    private static Statistics statistics;

    private static Map<Long, Transaction> transactionsMap = Collections.synchronizedMap(new HashMap<Long, Transaction>());

    private final Lock lock = new ReentrantLock();

    public static final long TRANSACTIONS_LIVE_TIME = 60000;

    public void add(Transaction transaction) {
        ZonedDateTime now = OffsetDateTime.now(UTC).toZonedDateTime();

        if (statistics == null) {
            statistics = new Statistics(transaction);
        } else {
            lock.lock();
            try {
                statistics.setCount(statistics.getCount() + 1);
                statistics.setSum(statistics.getSum().add(transaction.getAmount()));
                statistics.setAvg(AverageUtil.calculateAddAverage(statistics, transaction));
                if (statistics.getMax().compareTo(transaction.getAmount()) < 0)
                    statistics.setMax(transaction.getAmount());
                if (statistics.getMin().compareTo(transaction.getAmount()) > 0)
                    statistics.setMin(transaction.getAmount());
                transactionsMap.put(System.currentTimeMillis(), transaction);
            } finally {
                lock.unlock();
            }
        }

        Executors.newScheduledThreadPool(6).schedule(
                () -> remove(transaction),
                TRANSACTIONS_LIVE_TIME - ChronoUnit.MILLIS.between(transaction.getTimestamp(), now),
                TimeUnit.MILLISECONDS);

    }

    public void deleteAll() {
        lock.lock();
        try {
            transactionsMap = Collections.synchronizedMap(new HashMap<Long, Transaction>());
            statistics = null;
        } finally {
            lock.unlock();
        }
    }

    private void remove(Transaction transaction) {
        lock.lock();
        try {
            this.statistics.setSum(this.statistics.getSum().subtract(transaction.getAmount()));
            this.statistics.setAvg(AverageUtil.calculateSubstractAverage(this.statistics, transaction));
            this.statistics.setCount(this.statistics.getCount() - 1);
            Optional<BigDecimal> max = transactionsMap.entrySet()
                    .stream()
                    .map(a -> a.getValue().getAmount())
                    .max(BigDecimal::compareTo);
            statistics.setMax(max.isPresent() ? max.get() : new BigDecimal(0));
            Optional<BigDecimal> min = transactionsMap.entrySet()
                    .stream()
                    .map(a -> a.getValue().getAmount())
                    .min(BigDecimal::compareTo);
            statistics.setMin(min.isPresent() ? min.get() : new BigDecimal(0));
        } finally {
            lock.unlock();
        }
    }

    public Statistics get() {
        if (statistics == null) return new Statistics(new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), 0);
        return statistics;
    }
}
