package com.bozntouran.car_database_reviews_rest.model;
import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarBrandCSV {
    @CsvBindByName

    private String carbrand;
    @CsvBindByName

    private String countyoforigin;
    @CsvBindByName

    private Integer yearoffoundation;
}
