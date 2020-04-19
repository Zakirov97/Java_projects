package com.example.demo.service;

import com.example.demo.model.Bitcoin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BitcoinService {
    List<Bitcoin> getBitcoinHistory(String startDate, String endDate);
    List<Bitcoin> getTopTenJumps();
}
