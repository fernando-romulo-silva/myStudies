package com.apress.prospring5.ch15;

import org.springframework.beans.factory.annotation.Autowired;

import com.apress.prospring5.ch12.services.SingerService;

public class AppStatisticsImpl implements AppStatistics {

    @Autowired
    private SingerService singerService;

    @Override
    public int getTotalSingerCount() {
	return singerService.findAll().size();
    }
}
