package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;
@Getter
@Service
public class CarModelServiceForMock implements CarModelService{
    private final Map<UUID, CarModelDTO> carModelDTOMap;


    public CarModelServiceForMock(){
        carModelDTOMap = new HashMap<>();

        carModelDTOMap.put(UUID.randomUUID(),CarModelDTO.builder()
                .carType("4x4")
                .modelName("Navar")
                .fuelType("Gasoline")
                .build());


    }
    @Override
    public List<CarModelDTO> getAllModels() {
        return new ArrayList<>(this.carModelDTOMap.values());
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
