package com.example.demo.controllers;

import com.example.demo.DemoApplication;
import com.example.demo.model.Bitcoin;
import com.example.demo.model.Covid19AffectedCountries;
import com.example.demo.model.CovidDateAndCountry;
import com.example.demo.model.CovidWorldStat;
import com.example.demo.repository.BitcoinRepo;
import com.example.demo.repository.Covid19AffCounRepo;
import com.example.demo.service.impl.BitcoinServiceImpl;
import com.example.demo.service.impl.Covid19ServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Api(value = "Bitcoin And Covid-19 API's Management")

public class Controller {
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Autowired
    private BitcoinRepo bitcoinRepository;
    @Autowired
    private Covid19AffCounRepo covid19AffCounRepo;
    @Autowired
    private BitcoinServiceImpl bitcoinService;
    @Autowired
    private Covid19ServiceImpl covid19Service;

    @ApiOperation(value = "View a Covid-19 World Statistic For Now", response = String.class)
    @RequestMapping(value = "/covid-19/worldstat", method = RequestMethod.GET)
    public ResponseEntity<CovidWorldStat> getWorldStat() {
        CovidWorldStat worldStat = covid19Service.getWorldStat();
        if (worldStat != null) return new ResponseEntity<>(worldStat, HttpStatus.OK);
        else return new ResponseEntity<>(worldStat, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "View a Covid-19 Statistic By Date And Country Name", response = String.class)
    @RequestMapping(value = "/covid-19/contry_and_date", method = RequestMethod.POST)
    public ResponseEntity<List<CovidDateAndCountry>> getCovidByDateAndCountryHistory(@ApiParam(value = "Name Of Country", required = true) @RequestParam String country, @ApiParam(value = "Date", required = true) @RequestParam String date) {
        List resp = covid19Service.getCovidByDateAndCountryHistory(country,date);
        if (resp != null)
            return new ResponseEntity<>(resp, HttpStatus.OK);
        else return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "View a Bitcoin history From Start Date To End Date", response = String.class)
    @RequestMapping(value = "/bitcoin/history", method = RequestMethod.POST)
    public ResponseEntity<List<Bitcoin>> getBitcoinDateHistory(@ApiParam(value = "Start date Of historical data", required = true) @RequestParam String startDate, @ApiParam(value = "End Date Of Historical Data", required = true) @RequestParam String endDate) throws IOException {
        List response = bitcoinService.getBitcoinHistory(startDate, endDate);
        if (response != null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        else return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "View a List Affected Countries By Covid-19", response = String.class)
    @RequestMapping(value = "/covid-19/affected_countries", method = RequestMethod.GET)
    public ResponseEntity<List<Covid19AffectedCountries>> getAffectedCountries() {
        List resp = covid19Service.getAffectedCountries();
        if (resp != null)
            return new ResponseEntity<>(resp, HttpStatus.OK);
        else return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "View a Latest Info About Covid-19 World Statistic And Bitcoin", response = String.class)
    @RequestMapping(value = "/both/worldstat_and_bitcoin_latest", method = RequestMethod.GET)
    public ResponseEntity<Object> getWorldStatAndBitcoin() {
        CovidWorldStat worldStat = covid19Service.getWorldStat();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List response = bitcoinService.getBitcoinHistory(dtf.format(LocalDate.now().minusDays(1)), dtf.format(LocalDate.now()));

        return new ResponseEntity<>(worldStat.toString()+"\n" + response.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "View a Bitcoin Top 10 Jumps", response = String.class)
    @RequestMapping(value = "/bitcoin/topten", method = RequestMethod.GET)
    public ResponseEntity<List<Bitcoin>> getTopTen(){
        List response = bitcoinService.getTopTenJumps();
        if (response != null)
            return new ResponseEntity<>(response,HttpStatus.OK);
        else return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 