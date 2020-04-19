package com.example.demo.service;

import com.example.demo.model.Covid19AffectedCountries;
import com.example.demo.model.CovidDateAndCountry;
import com.example.demo.model.CovidWorldStat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Covid19Service {
    List<Covid19AffectedCountries> getAffectedCountries();
    CovidWorldStat getWorldStat();
    List<CovidDateAndCountry> getCovidByDateAndCountryHistory(String country,String date);
}
