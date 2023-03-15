package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarModelService {

    List<CarModelDTO> getAllModels();
    CarModelDTO saveCarModel(CarModelDTO carModelDTO);
    Optional<CarModelDTO> getCarModelByID(UUID carModelID);

}
