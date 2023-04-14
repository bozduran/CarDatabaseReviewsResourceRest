package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface CarModelService {

    Page<CarModelDTO> getAllCarModels(String modelName, String drive, String fuelType, String transmision, String carType, Integer yearOfProduction, Integer pageNumber, Integer pageSize);
    CarModelDTO saveCarModel(CarModelDTO carModelDTO);
    Optional<CarModelDTO> getCarModelByID(UUID carModelID);

    boolean deleteCarModelByID(UUID carModelID);

    void pattchCarModelByID(UUID carModelID, CarModelDTO carModelDTO);
}
