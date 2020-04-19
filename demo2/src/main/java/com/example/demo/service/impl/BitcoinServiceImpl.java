package com.example.demo.service.impl;

import com.example.demo.DemoApplication;
import com.example.demo.model.Bitcoin;
import com.example.demo.repository.BitcoinRepo;
import com.example.demo.service.BitcoinService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class BitcoinServiceImpl implements BitcoinService {
    @Autowired
    private BitcoinRepo bitcoinRepo;
    @Autowired
    private Environment env;

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public List<Bitcoin> getBitcoinHistory(String startDate, String endDate) {
        List bitcoins = new ArrayList<Bitcoin>();
        String uri = env.getProperty("bitcoin.history.URL");
        uri = uri.replace("myStartDate", startDate).replace("myEndDate", endDate);
        HttpGet request = new HttpGet(uri);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                StringBuilder stringBuilder = new StringBuilder();

                JSONObject myJson = new JSONObject(result);
                JSONObject bpi = myJson.getJSONObject("bpi");

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(startDate);
                for (int i = 0; i < bpi.length(); i++) {
                    String date = dtf.format(localDate.plusDays(i));
                    String value = bpi.get(date).toString();
                    stringBuilder.append("Date: " + date + ", Bitcoin Price: " + value + "\n");
                    bitcoins.add(new Bitcoin(new SimpleDateFormat("yyyy-MM-dd").parse(date), new BigDecimal(value)));
                }
                bitcoinRepo.saveAll(bitcoins);
            }
        } catch (Exception ex) {
            DemoApplication.logger.warn(ex.getStackTrace());
        }
        return bitcoins;
    }

    public List<Bitcoin> getTopTenJumps(){
        List bitcoins = new ArrayList<Bitcoin>();
        List tops = new ArrayList<Bitcoin>();
        String uri = env.getProperty("bitcoin.history.URL");
        String startDate = "2020-03-20";
        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        uri = uri.replace("myStartDate", startDate).replace("myEndDate", endDate);
        HttpGet request = new HttpGet(uri);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                StringBuilder stringBuilder = new StringBuilder();

                JSONObject myJson = new JSONObject(result);
                JSONObject bpi = myJson.getJSONObject("bpi");

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(startDate);
                String prevDt;
                String date = startDate;
                for (int i = 1; i < bpi.length(); i++) {
                    prevDt = date;
                    date = dtf.format(localDate.plusDays(i));
                    BigDecimal value = new BigDecimal(bpi.get(date).toString());
                    BigDecimal minusDateVal = new BigDecimal(bpi.get(date).toString()).subtract(new BigDecimal(bpi.get(prevDt).toString()));
                    stringBuilder.append("Date: " + date + ", Bitcoin Price: " + value + "\n");
                    bitcoins.add(new Bitcoin(new SimpleDateFormat("yyyy-MM-dd").parse(date), value, minusDateVal));
                }
                Comparator<Bitcoin> compareByVal = (Bitcoin b1,Bitcoin b2)->b1.getMinusDateVal().compareTo(b2.getMinusDateVal());
                bitcoins.sort(compareByVal);
                for (int i = 0; i < 10; i++) {
                    tops.add(bitcoins.get(i));
                }
                //bitcoinRepo.saveAll(bitcoins);
            }
        } catch (Exception ex) {
            DemoApplication.logger.warn(ex.getStackTrace());
        }
        return tops;
    }
}
