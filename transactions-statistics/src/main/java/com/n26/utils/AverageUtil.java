package com.n26.utils;

import com.n26.statistics.Statistics;
import com.n26.transaction.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Mathematical formulas extracted from Math Stack Exchange
 */
public class AverageUtil {
    public static BigDecimal calculateAddAverage(Statistics s, Transaction t) {
        return s.getAvg().add(t.getAmount().subtract(s.getAvg()).divide(new BigDecimal(s.getCount()), 6, RoundingMode.HALF_UP));
    }

    public static BigDecimal calculateSubstractAverage(Statistics s, Transaction t) {
        return s.getAvg().multiply(new BigDecimal(s.getCount())).subtract(t.getAmount()).divide(new BigDecimal(s.getCount() - 1), 6, RoundingMode.HALF_UP);
    }
}
