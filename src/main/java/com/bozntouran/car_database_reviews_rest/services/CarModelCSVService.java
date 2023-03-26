package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarBrandCSV;
import com.bozntouran.car_database_reviews_rest.model.CarModelCSV;

import java.io.File;
import java.util.List;

public interface CarModelCSVService {

    List<CarModelCSV> conevertCSV(File file);

}
