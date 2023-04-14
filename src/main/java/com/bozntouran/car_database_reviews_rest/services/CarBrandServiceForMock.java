package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.controller.NotFoundException;
import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
public class CarBrandServiceForMock implements CarBrandService{

    private final Map<UUID, CarBrandDTO> carBrandDTOMap;

    public CarBrandServiceForMock(){
        this.carBrandDTOMap = new HashMap<>();

        CarBrandDTO ferrari = CarBrandDTO.builder()
                .id(UUID.randomUUID())
                .brandName("Ferrari")
                .countryOfOrigin("Italy")
                .yearOfFoundation(1943)
                .build();
        CarBrandDTO ford = CarBrandDTO.builder()
                .id(UUID.randomUUID())
                .brandName("Ford")
                .countryOfOrigin("United States")
                .yearOfFoundation(1903)
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
        return Optional.of(carBrandDTOMap.get(id));
    }

    @Override
    public Page<CarBrand> getCarBrandByName(String carBrandName, Pageable pageable) {
        CarBrandDTO carBrandDTO = null;
        for (CarBrandDTO carBrandDto:carBrandDTOMap.values()) {
            if (carBrandDto.getId().equals(carBrandName)){
                carBrandDTO = carBrandDto;
            }
        }
        if (carBrandDTO == null){
            throw new NotFoundException();
        }

        List<CarBrand> carBrands = new ArrayList<>();
        carBrands.add(
                CarBrand.builder()
                        .brandName(carBrandDTO.getBrandName())
                        .yearOfFoundation(carBrandDTO.getYearOfFoundation())
                        .countryOfOrigin(carBrandDTO.getCountryOfOrigin())
                        .build());

        return new PageImpl<>(carBrands);

    }


    @Override
    public Page<CarBrandDTO> getAllBrands(String carBrand, String countryOfOrigin, Integer yearOfFoundation, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(this.carBrandDTOMap.values()));
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
        return true;
    }

    @Override
    public Optional<CarBrandDTO> updateCarBRandByID(UUID carBrandId, CarBrandDTO carBrandDTO) {

        CarBrandDTO carBrandDTOtoUpdate = carBrandDTOMap.get(carBrandId);

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

    @Override
    public Page<CarBrand> getCarBrandByYearOfFoundation(Integer yearoffoundation, Pageable pageable) {
        return null;
    }

    @Override
    public Page<CarBrand> getCarBrandByCountryOfOrigin(String countryOfOrigin, Pageable pageable) {
        return null;
    }


    @Override
    public Page<CarBrand> getCarBrandByBrandNameAndYearOfFoundation(String carBrandName, Integer yearOfFoundation, org.springframework.data.domain.Pageable pageable) {
        return null;
    }

    @Override
    public Page<CarBrand> getCarBrandByBrandNameAndCountryOfOrigin(String carBrandName, String countryOfOrigigin, org.springframework.data.domain.Pageable pageable) {
        return null;
    }

    @Override
    public Page<CarBrand> getCarBrandByYearOfFoundationAndCountryOfOrigin(Integer yearOfFoundation, String countryOfOrigin, org.springframework.data.domain.Pageable pageable) {
        return null;
    }

    @Override
    public Page<CarBrand> getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(String carBrand, String countryOfOrigin, Integer yearOfFoundation, org.springframework.data.domain.Pageable pageable) {
        return null;
    }
}
