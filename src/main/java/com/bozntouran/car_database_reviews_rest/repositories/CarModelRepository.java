package com.bozntouran.car_database_reviews_rest.repositories;

import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
@Repository
public interface CarModelRepository extends JpaRepository<CarModel, UUID> {
    CarModel getCarModelById(UUID id);
}