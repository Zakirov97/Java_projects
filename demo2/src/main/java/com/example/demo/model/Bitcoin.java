package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bitcoin_data")
@ApiModel(description = "Bitcoin desc")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Bitcoin {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @ApiModelProperty("id")
    public Integer id;

    @NotNull
    @Column(name = "date")
    @ApiModelProperty("date")
    public Date date;

    @NotNull
    @Column(name = "bprice")
    @ApiModelProperty("bprice")
    public BigDecimal bprice;

    private BigDecimal minusDateVal;

    public Bitcoin(Date date, BigDecimal bprice){
        this.date = date;
        this.bprice = bprice;
    }

    public Bitcoin(Date date, BigDecimal bprice, BigDecimal prevVal){
        this.date = date;
        this.bprice = bprice;
        this.minusDateVal = prevVal;
    }
}
