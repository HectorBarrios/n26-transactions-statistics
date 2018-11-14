package com.n26.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = "statistics", method = RequestMethod.GET)
    public ResponseEntity<Statistics> getStatistics() {
        return ResponseEntity.ok(statisticsService.get());
    }
}
