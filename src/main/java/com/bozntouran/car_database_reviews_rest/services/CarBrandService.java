package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CarBrandService {
    Optional<CarBrandDTO> getCarBrandByID(UUID id);
    CarBrandDTO getCarBrandByName(String carBrandName);
    List<CarBrandDTO> getAllBrands();
    CarBrandDTO getByBrandName();
    CarBrandDTO saveNewCarBrand(CarBrandDTO carBrandDTO);

    boolean deleteCarBrandByID(UUID carBrandID);

    Optional<CarBrandDTO> updateCarBRandByID(UUID carBrandId, CarBrandDTO carBrandDTO);

    Optional<CarBrandDTO> patchCarBrandByID(UUID carBrandId, CarBrandDTO carBrandDTO);

}
