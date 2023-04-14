package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<CarModelDTO> getAllCarModels(String modelName, String drive, String fuelType, String transmision, String carType, Integer yearOfProduction, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(this.carModelDTOMap.values()));
    }

    @Override
    public CarModelDTO saveCarModel(CarModelDTO carModelDTO) {
        return null;
    }

    @Override
    public Optional<CarModelDTO> getCarModelByID(UUID carModelID) {
        return null;
    }

    @Override
    public boolean deleteCarModelByID(UUID carModelID) {
        return false;
    }

    @Override
    public void pattchCarModelByID(UUID carModelID, CarModelDTO carModelDTO) {

    }
}
