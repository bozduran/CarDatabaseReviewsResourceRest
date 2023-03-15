package com.bozntouran.car_database_reviews_rest.repositories;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, UUID> {

    CarBrand getCarBrandById(UUID id);
    boolean existsById(UUID id);
    CarBrand getCarBrandByBrandName(String carBrandName);
}