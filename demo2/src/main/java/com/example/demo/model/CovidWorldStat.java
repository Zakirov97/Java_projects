package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "Covid world statistic")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CovidWorldStat {
    @ApiModelProperty("total_cases")
    String totalCases;

    @ApiModelProperty("total_deaths")
    String totalDeaths;

    @ApiModelProperty("total_recovered")
    String totalRecovered;

    @ApiModelProperty("new_cases")
    String newCases;

    @ApiModelProperty("new_deaths")
    String newDeaths;

    @ApiModelProperty("statistic_taken_at")
    String statisticTakenAt;
}
