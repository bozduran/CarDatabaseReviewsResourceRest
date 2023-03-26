package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarBrandCSV;
import com.bozntouran.car_database_reviews_rest.model.CarModelCSV;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
@Service
public class CarModelCSVServiceImpl implements CarModelCSVService{
    @Override
    public List<CarModelCSV> conevertCSV(File file) {

        try {
            List<CarModelCSV> CarModelCSVList = new CsvToBeanBuilder<CarModelCSV>(new FileReader(file))
                    .withType(CarModelCSV.class)
                    .build()
                    .parse();
            return CarModelCSVList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
