package com.bozntouran.car_database_reviews_rest.repositories;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {

    CarBrand getCarBrandById(UUID id);
    boolean existsById(UUID id);
    List<CarBrand> getCarBrandByYearOfFoundation(Integer yearoffoundation);
    List<CarBrand> getCarBrandByCountryOfOrigin(String countryOfOrigin);
    List<CarBrand> getCarBrandByBrandName(String carBrandName);

    List<CarBrand> getCarBrandByBrandNameAndYearOfFoundation(String carBrandName,Integer yearOfFoundation);

    List<CarBrand> getCarBrandByBrandNameAndCountryOfOrigin(String carBrandName, String countryOfOrigigin);

    List<CarBrand> getCarBrandByYearOfFoundationAndCountryOfOrigin(Integer yearOfFoundation, String countryOfOrigin);

    List<CarBrand> getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(String carBrand, String countryOfOrigin, Integer yearOfFoundation);

}