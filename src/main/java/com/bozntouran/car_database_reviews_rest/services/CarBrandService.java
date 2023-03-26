package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CarBrandService {
    Optional<CarBrandDTO> getCarBrandByID(UUID id);
    List<CarBrand> getCarBrandByName(String carBrandName);
    List<CarBrandDTO> getAllBrands(String carBrand, String countryOfOrigin, Integer yearOfFoundation);
    CarBrandDTO getByBrandName();
    CarBrandDTO saveNewCarBrand(CarBrandDTO carBrandDTO);

    boolean deleteCarBrandByID(UUID carBrandID);

    Optional<CarBrandDTO> updateCarBRandByID(UUID carBrandId, CarBrandDTO carBrandDTO);

    Optional<CarBrandDTO> patchCarBrandByID(UUID carBrandId, CarBrandDTO carBrandDTO);

    List<CarBrand> getCarBrandByYearOfFoundation(Integer yearoffoundation);
    List<CarBrand> getCarBrandByCountryOfOrigin(String countryOfOrigin);

    List<CarBrand> getCarBrandByBrandNameAndYearOfFoundation(String carBrandName, Integer yearOfFoundation);

    List<CarBrand> getCarBrandByBrandNameAndCountryOfOrigin(String carBrandName, String countryOfOrigin);

    List<CarBrand> getCarBrandByYearOfFoundationAndCountryOfOrigin( Integer yearOfFoundation, String countryOfOrigin);

    List<CarBrand> getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(String carBrand, String countryOfOrigin, Integer yearOfFoundation);

}
