package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.controller.NotFoundException;
import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
public class CarBrandServiceForMock implements CarBrandService{

    private final Map<UUID, CarBrandDTO> carBrandDTOMap;

    public CarBrandServiceForMock(){
        this.carBrandDTOMap = new HashMap<>();

        System.out.println("PostConstruct Data bootstrapping.");
        CarBrandDTO ferrari = CarBrandDTO.builder()
                .id(UUID.randomUUID())
                .brandName("Ferrari")
                .countryOfOrigin("Italy")
                .creationYear(1943)
                .build();
        CarBrandDTO ford = CarBrandDTO.builder()
                .id(UUID.randomUUID())
                .brandName("Ford")
                .countryOfOrigin("United States")
                .creationYear(1903)
                .build();


        CarModel enzo = CarModel.builder()
                .id(UUID.randomUUID())
                .modelName("Enzo")
                .yearOfProduction(2002)
                .build();
        CarModel gt = CarModel.builder()
                .id(UUID.randomUUID())
                .modelName("GT")
                .yearOfProduction(2002)
                .build();

        this.carBrandDTOMap.put( ferrari.getId() , ferrari);
        this.carBrandDTOMap.put( ford.getId() ,ford);

    }



    @Override
    public Optional<CarBrandDTO> getCarBrandByID(UUID id) {
        CarBrandDTO carBrandDTO = this.carBrandDTOMap.get(id);
        if (carBrandDTO == null){
            throw new NotFoundException();
        }
        return Optional.of(carBrandDTO);
    }

    @Override
    public CarBrandDTO getCarBrandByName(String carBrandName) {
        CarBrandDTO carBrandDTO = null;
        for (CarBrandDTO carBrandDto:carBrandDTOMap.values()) {
            if (carBrandDto.getId().equals(carBrandName)){
                carBrandDTO = carBrandDto;
            }
        }
        if (carBrandDTO == null){
            throw new NotFoundException();
        }

        return carBrandDTO;
    }

    @Override
    public List<CarBrandDTO> getAllBrands() {
        return new ArrayList<>(this.carBrandDTOMap.values());
    }

    @Override
    public CarBrandDTO getByBrandName() {
        return null;
    }

    @Override
    public CarBrandDTO saveNewCarBrand(CarBrandDTO carBrandDTO) {
        return null;
    }

    @Override
    public boolean deleteCarBrandByID(UUID carBrandID) {
        return false;
    }

    @Override
    public Optional<CarBrandDTO> updateCarBRandByID(UUID carBrandId, CarBrandDTO carBrandDTO) {

        CarBrandDTO carBrandDTOtoUpdate = carBrandDTOMap.get(carBrandId);
        System.out.println(carBrandDTOtoUpdate.getId());

        carBrandDTOtoUpdate.setBrandName( carBrandDTO.getBrandName());
        carBrandDTOtoUpdate.setCountryOfOrigin( carBrandDTO.getCountryOfOrigin());

        return Optional.of(carBrandDTO);
    }

    @Override
    public Optional<CarBrandDTO> patchCarBrandByID(UUID carBrandId, CarBrandDTO carBrandDTO) {
        CarBrandDTO carBrandDTORetreived = carBrandDTOMap.get(carBrandId);

        if( carBrandDTO.getBrandName() != null){
            carBrandDTORetreived.setBrandName(carBrandDTO.getBrandName());
        }
        if (carBrandDTO.getCountryOfOrigin() != null){
            carBrandDTORetreived.setCountryOfOrigin(carBrandDTO.getCountryOfOrigin());
        }


        return Optional.of(carBrandDTORetreived);
    }
}
