package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface CarBrandService {
    Optional<CarBrandDTO> getCarBrandByID(UUID id);
    Page<CarBrand> getCarBrandByName(String carBrandName, Pageable pageable);
    Page<CarBrandDTO> getAllBrands(String carBrand, String countryOfOrigin, Integer yearOfFoundation, Integer pageNumber, Integer pageSize);
    CarBrandDTO getByBrandName();
    CarBrandDTO saveNewCarBrand(CarBrandDTO carBrandDTO);

    boolean deleteCarBrandByID(UUID carBrandID);

    Optional<CarBrandDTO> updateCarBRandByID(UUID carBrandId, CarBrandDTO carBrandDTO);

    Optional<CarBrandDTO> patchCarBrandByID(UUID carBrandId, CarBrandDTO carBrandDTO);

    Page<CarBrand> getCarBrandByYearOfFoundation(Integer yearoffoundation, Pageable pageable);
    Page<CarBrand> getCarBrandByCountryOfOrigin(String countryOfOrigin, Pageable pageable);

    Page<CarBrand> getCarBrandByBrandNameAndYearOfFoundation(String carBrandName, Integer yearOfFoundation, Pageable pageable);

    Page<CarBrand> getCarBrandByBrandNameAndCountryOfOrigin(String carBrandName, String countryOfOrigin, Pageable pageable);

    Page<CarBrand> getCarBrandByYearOfFoundationAndCountryOfOrigin(Integer yearOfFoundation, String countryOfOrigin, Pageable pageable);

    Page<CarBrand> getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(String carBrand, String countryOfOrigin, Integer yearOfFoundation, Pageable pageable);

}
