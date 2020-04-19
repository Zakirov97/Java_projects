package com.example.demo.service.impl;

import com.example.demo.DemoApplication;
import com.example.demo.model.Covid19AffectedCountries;
import com.example.demo.model.CovidDateAndCountry;
import com.example.demo.model.CovidWorldStat;
import com.example.demo.repository.Covid19AffCounRepo;
import com.example.demo.service.Covid19Service;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class Covid19ServiceImpl implements Covid19Service {
    @Autowired
    private Covid19AffCounRepo covid19AffCounRepo;
    @Autowired
    private Environment env;

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public List<Covid19AffectedCountries> getAffectedCountries() {
        String uri = env.getProperty("covid19.affected.country");
        List resp = (ArrayList<Covid19AffectedCountries>) covid19AffCounRepo.findAll();
        try {
            HttpGet request = new HttpGet(uri);

            request.addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com");
            request.addHeader("x-rapidapi-key", "a95005b30dmsh04d64578d35d5fap1ff6aajsn3fbc576af34e");
            int cnt = 0;
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject myJson = new JSONObject(result);
                    JSONArray affArr = myJson.getJSONArray("affected_countries");

                    String dts = myJson.getString("statistic_taken_at");

                    Date dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dts);
                    for (int i = 0; i < affArr.length(); i++) {
                        if (!affArr.get(i).toString().isEmpty()) {
                            try {
                                int finalI = i;
                                if (!resp.stream().anyMatch(m -> ((Covid19AffectedCountries) m).name.equals(affArr.get(finalI).toString()))) {
                                    covid19AffCounRepo.save(new Covid19AffectedCountries(affArr.get(i).toString(), dt));
                                    resp.add(new Covid19AffectedCountries(affArr.get(i).toString(), dt));
                                }
                            } catch (Exception ex) {
                                DemoApplication.logger.error("getAffectedCountries METHOD" + Arrays.toString(ex.getStackTrace()));
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                DemoApplication.logger.warn(ex.getStackTrace());
            }
        } catch (Exception ex) {
            DemoApplication.logger.warn(ex.getStackTrace());
        }
        return resp;
    }

    @Override
    public CovidWorldStat getWorldStat() {
        CovidWorldStat covidWorldStat = new CovidWorldStat();
        //TODO URI CHANGE
        String uri = env.getProperty("covid19.world.stat");
        try {
            HttpGet request = new HttpGet(uri);
            request.addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com");
            request.addHeader("x-rapidapi-key", "a95005b30dmsh04d64578d35d5fap1ff6aajsn3fbc576af34e");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                DemoApplication.logger.info("Response status from /covid-19/worldstat" + response.getStatusLine().toString());
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject myJson = new JSONObject(result);
                    covidWorldStat.setTotalCases(myJson.get("total_cases").toString());
                    covidWorldStat.setTotalDeaths(myJson.get("total_deaths").toString());
                    covidWorldStat.setTotalRecovered(myJson.get("total_recovered").toString());
                    covidWorldStat.setNewCases(myJson.get("new_cases").toString());
                    covidWorldStat.setNewDeaths(myJson.get("new_deaths").toString());
                    covidWorldStat.setStatisticTakenAt(myJson.get("statistic_taken_at").toString());
                }
            } catch (Exception ex) {
                DemoApplication.logger.warn(ex.getStackTrace());
            }

        } catch (Exception ex) {
            DemoApplication.logger.warn(ex.getStackTrace());
        }
        return covidWorldStat;
    }

    @Override
    public List<CovidDateAndCountry> getCovidByDateAndCountryHistory(String country, String date) {
        List resp = new ArrayList<CovidDateAndCountry>();
        //TODO URI CHANGE
        String uri = env.getProperty("covid19.date.and.country");
        uri = uri.replace("myCountry", country).replace("myDate", date);
        HttpGet request = new HttpGet(uri);
        try {

            request.addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com");
            request.addHeader("x-rapidapi-key", "a95005b30dmsh04d64578d35d5fap1ff6aajsn3fbc576af34e");


            try (CloseableHttpResponse response = httpClient.execute(request)) {
                DemoApplication.logger.info("Response status from /covid-19/contry_and_date" + response.getStatusLine().toString());

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONObject myJson = new JSONObject(result);
                    JSONArray stat = myJson.getJSONArray("stat_by_country");

                    for (int i = 0; i < stat.length(); i++) {
                        try {
                            CovidDateAndCountry temp = new CovidDateAndCountry();
                            JSONObject tenMinState = (JSONObject) stat.get(i);
                            temp.setId(tenMinState.get("id").toString());
                            temp.setCountryName(tenMinState.get("country_name").toString());
                            temp.setTotalCases(tenMinState.get("total_cases").toString());
                            temp.setNewCases(tenMinState.get("new_cases").toString());
                            temp.setActiveCases(tenMinState.get("active_cases").toString());
                            temp.setTotalDeaths(tenMinState.get("total_deaths").toString());
                            temp.setNewDeaths(tenMinState.get("new_deaths").toString());
                            temp.setTotalRecovered(tenMinState.get("total_recovered").toString());
                            temp.setSeriousCritical(tenMinState.get("serious_critical").toString());
                            temp.setRegion(tenMinState.get("region").toString());
                            temp.setTotalCasesPer1m(tenMinState.get("total_cases_per1m").toString());
                            temp.setRecordDate(tenMinState.get("record_date").toString());
                            temp.setDeathsPer1m(tenMinState.get("deaths_per1m").toString());
                            temp.setTotalTests(tenMinState.get("total_tests").toString());
                            temp.setTotalTestsPer1m(tenMinState.get("total_tests_per1m").toString());
                            resp.add(temp);
                        } catch (Exception ex) {
                            DemoApplication.logger.error("getAffectedCountries METHOD" + Arrays.toString(ex.getStackTrace()));
                        }
                    }
                }
            } catch (Exception ex) {
                DemoApplication.logger.warn(ex.getStackTrace());
            }

        } catch (Exception ex) {
            DemoApplication.logger.warn(ex.getStackTrace());
        }
        return resp;
    }
}
