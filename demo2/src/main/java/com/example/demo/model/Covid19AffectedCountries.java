package com.example.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "covid19_affected_countries")
@ApiModel(description = "Covid19 desc")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Covid19AffectedCountries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "id")
    public Integer id;

    @Column(name = "name")
    @ApiModelProperty(notes = "name")
    public String name;

    @Column(name = "date")
    @ApiModelProperty(notes = "date")
    public Date date;

    public Covid19AffectedCountries(String name, Date date){
        this.name = name;
        this.date = date;
    }
}
