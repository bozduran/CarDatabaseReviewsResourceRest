package com.bozntouran.car_database_reviews_rest.repositories;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;
@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {

    CarBrand getCarBrandById(UUID id);

    boolean existsById(UUID id);
    Page<CarBrand> getCarBrandByYearOfFoundation(Integer yearoffoundation, Pageable pageable);
    Page<CarBrand> getCarBrandByCountryOfOrigin(String countryOfOrigin, Pageable pageable);
    Page<CarBrand> getCarBrandByBrandName(String carBrandName, Pageable pageable);

    Page<CarBrand> getCarBrandByBrandNameAndYearOfFoundation(String carBrandName, Integer yearOfFoundation, Pageable pageable);

    Page<CarBrand> getCarBrandByBrandNameAndCountryOfOrigin(String carBrandName, String countryOfOrigigin, Pageable pageable);

    Page<CarBrand> getCarBrandByYearOfFoundationAndCountryOfOrigin(Integer yearOfFoundation, String countryOfOrigin, Pageable pageable);

    Page<CarBrand> getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(String carBrand, String countryOfOrigin, Integer yearOfFoundation, Pageable pageable);

}