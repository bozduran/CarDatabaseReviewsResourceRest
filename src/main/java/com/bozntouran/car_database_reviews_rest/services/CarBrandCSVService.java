package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarBrandCSV;

import java.io.File;
import java.util.List;

public interface CarBrandCSVService {
    List<CarBrandCSV> conevertCSV(File file);
}
