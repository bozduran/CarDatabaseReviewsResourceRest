package com.bozntouran.car_database_reviews_rest.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarModelCSV {

    @CsvBindByName
    private String make;
    @CsvBindByName
    private  String model;
    @CsvBindByName
    private  Integer year;

    @CsvBindByName
    private String drive;
    @CsvBindByName
    private String fuelType;
    @CsvBindByName
    private String trany;
    @CsvBindByName
    private String VClass;
}
