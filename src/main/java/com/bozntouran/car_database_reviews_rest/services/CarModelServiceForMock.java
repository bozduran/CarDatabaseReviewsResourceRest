package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarModelServiceForMock implements CarModelService{
    @Override
    public List<CarModelDTO> getAllModels() {
        return null;
    }

    @Override
    public CarModelDTO saveCarModel(CarModelDTO carModelDTO) {
        return null;
    }

    @Override
    public Optional<CarModelDTO> getCarModelByID(UUID carModelID) {
        return null;
    }
}
