package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "Covid get total statistic by Date and Country")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CovidDateAndCountry {
    @ApiModelProperty("id")
    String id;
    @ApiModelProperty("countryName")
    String countryName;
    @ApiModelProperty("totalCases")
    String totalCases;
    @ApiModelProperty("newCases")
    String newCases;
    @ApiModelProperty("activeCases")
    String activeCases;
    @ApiModelProperty("totalDeaths")
    String totalDeaths;
    @ApiModelProperty("newDeaths")
    String newDeaths;
    @ApiModelProperty("totalRecovered")
    String totalRecovered;
    @ApiModelProperty("seriousCritical")
    String seriousCritical;
    @ApiModelProperty("region")
    String region;
    @ApiModelProperty("totalCasesPer1m")
    String totalCasesPer1m;
    @ApiModelProperty("recordDate")
    String recordDate;
    @ApiModelProperty("deathsPer1m")
    String deathsPer1m;
    @ApiModelProperty("totalTests")
    String totalTests;
    @ApiModelProperty("totalTestsPer1m")
    String totalTestsPer1m;
}
