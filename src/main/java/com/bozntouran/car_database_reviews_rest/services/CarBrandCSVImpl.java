package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarBrandCSV;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
@Service
public class CarBrandCSVImpl implements CarBrandCSVService {
    @Override
    public List<CarBrandCSV> conevertCSV(File file) {

        try {
            List<CarBrandCSV> carBrandCSVList = new CsvToBeanBuilder<CarBrandCSV>(new FileReader(file))
                    .withType(CarBrandCSV.class)
                    .build()
                    .parse();
            return carBrandCSVList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
