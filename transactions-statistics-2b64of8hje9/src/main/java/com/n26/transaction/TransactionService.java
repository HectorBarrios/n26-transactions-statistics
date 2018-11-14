package com.n26.transaction;

import com.n26.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private StatisticsService statisticsService;


    public void add(Transaction transaction) {
        statisticsService.add(transaction);
    }

    public void deleteAll() {
        statisticsService.deleteAll();
    }

}
