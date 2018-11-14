package com.n26.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.transaction.Transaction;
import com.n26.utils.StatisticsSerializer;

import java.math.BigDecimal;

public class Statistics {

    public Statistics(Transaction transaction) {
        this(transaction.getAmount(), transaction.getAmount(), transaction.getAmount(), transaction.getAmount(), 1);
    }

    public Statistics(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, int count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = StatisticsSerializer.class)
    BigDecimal sum;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = StatisticsSerializer.class)
    BigDecimal avg;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = StatisticsSerializer.class)
    BigDecimal max;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = StatisticsSerializer.class)
    BigDecimal min;

    int count;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
