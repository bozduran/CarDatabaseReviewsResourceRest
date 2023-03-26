package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.mappers.CarBrandMapper;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.repositories.CarBrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class CarBrandServiceImp implements CarBrandService{

    private final CarBrandRepository carBrandRepository;
    private CarBrandMapper carBrandMapper;



    @Override
    public Optional<CarBrandDTO> getCarBrandByID(UUID id) {

        CarBrandDTO carBrandDTO = carBrandMapper.carBrandToCarBrandDto(
                carBrandRepository.getCarBrandById(id));
        return Optional.ofNullable(Optional.ofNullable(carBrandDTO).orElse(null));
    }

    @Override
    public List<CarBrand> getCarBrandByName(String carBrandName) {
        return carBrandRepository.getCarBrandByBrandName(carBrandName);
    }




    @Override
    public List<CarBrandDTO> getAllBrands(String carBrand, String countryOfOrigin, Integer yearOfFoundation) {
        List<CarBrand> carBrands = new ArrayList<>();
        if(carBrand == null && countryOfOrigin == null && yearOfFoundation == null) {
            carBrands = carBrandRepository.findAll();
        } else if(carBrand != null && countryOfOrigin == null && yearOfFoundation == null) {
            carBrands = getCarBrandByName(carBrand);
        } else if(carBrand == null && countryOfOrigin != null && yearOfFoundation == null) {
            carBrands = getCarBrandByCountryOfOrigin(countryOfOrigin);
        } else if(carBrand == null && countryOfOrigin == null && yearOfFoundation != null) {
            carBrands = getCarBrandByYearOfFoundation(yearOfFoundation);
        } else if(carBrand != null && countryOfOrigin != null && yearOfFoundation == null) {
            carBrands = getCarBrandByBrandNameAndCountryOfOrigin(carBrand, countryOfOrigin);
        } else if(carBrand != null && countryOfOrigin == null && yearOfFoundation != null) {
            carBrands = getCarBrandByBrandNameAndYearOfFoundation(carBrand, yearOfFoundation);
        } else if(carBrand == null && countryOfOrigin != null && yearOfFoundation != null) {
            carBrands = getCarBrandByYearOfFoundationAndCountryOfOrigin(yearOfFoundation, countryOfOrigin);
        } else if(carBrand != null && countryOfOrigin != null && yearOfFoundation != null) {
            carBrands = getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(carBrand, countryOfOrigin, yearOfFoundation);
        }




        return carBrands.stream()
                .map(carBrandObject -> carBrandMapper.carBrandToCarBrandDto(carBrandObject))
                .collect(Collectors.toList());
    }



    @Override
    public CarBrandDTO getByBrandName() {
        return null;
    }

    @Override
    public CarBrandDTO saveNewCarBrand(CarBrandDTO carBrandDTO) {
        return carBrandMapper
                .carBrandToCarBrandDto(
                        carBrandRepository.save(
                                carBrandMapper.carBrandDtoToCarBrand(
                                        carBrandDTO)));
    }

    @Override
    public boolean deleteCarBrandByID(UUID carBrandID) {
        if ( carBrandRepository.existsById(carBrandID)){
            carBrandRepository.deleteById(carBrandID);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CarBrandDTO> updateCarBRandByID(UUID carBrandId, CarBrandDTO carBrandDTO) {

        AtomicReference<Optional<CarBrandDTO>> atomicReference = new AtomicReference<>();

        carBrandRepository.findById(carBrandId).ifPresentOrElse(carBrand -> {
            carBrand.setBrandName(carBrandDTO.getBrandName());
            carBrand.setCountryOfOrigin(carBrandDTO.getCountryOfOrigin());
            atomicReference.set(Optional.of(carBrandMapper
                    .carBrandToCarBrandDto(carBrandRepository.save(carBrand))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Optional<CarBrandDTO> patchCarBrandByID(UUID carBrandId, CarBrandDTO carBrandDTO) {
        AtomicReference<Optional<CarBrandDTO>> atomicReference = new AtomicReference<>();
        carBrandRepository.findById(carBrandId).ifPresentOrElse(retreivedCarBrand ->{
            if (carBrandDTO.getBrandName() != null){
                retreivedCarBrand.setBrandName(carBrandDTO.getBrandName());
            }
            if (carBrandDTO.getCountryOfOrigin() != null){
                retreivedCarBrand.setCountryOfOrigin(carBrandDTO.getCountryOfOrigin());
            }

        },()-> {
                    atomicReference.set(Optional.empty());
                }
        );


        return atomicReference.get();
    }

    @Override
    public List<CarBrand> getCarBrandByYearOfFoundation(Integer yearOfFoundation) {
        return carBrandRepository.getCarBrandByYearOfFoundation(yearOfFoundation);
    }

    @Override
    public List<CarBrand> getCarBrandByCountryOfOrigin(String countryOfOrigin) {
        return carBrandRepository.getCarBrandByCountryOfOrigin(countryOfOrigin);
    }

    @Override
    public List<CarBrand> getCarBrandByBrandNameAndYearOfFoundation(String carBrandName, Integer yearOfFoundation) {
        return carBrandRepository.getCarBrandByBrandNameAndYearOfFoundation(carBrandName,yearOfFoundation);
    }

    @Override
    public List<CarBrand> getCarBrandByBrandNameAndCountryOfOrigin(String carBrandName, String countryOfOrigigin) {
        return carBrandRepository.getCarBrandByBrandNameAndCountryOfOrigin(carBrandName,countryOfOrigigin);
    }

    @Override
    public List<CarBrand> getCarBrandByYearOfFoundationAndCountryOfOrigin(Integer yearOfFoundation, String countryOfOrigin) {
        return carBrandRepository.getCarBrandByYearOfFoundationAndCountryOfOrigin(yearOfFoundation,countryOfOrigin);
    }

    @Override
    public List<CarBrand> getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation
            (String carBrand, String countryOfOrigin, Integer yearOfFoundation) {
        return carBrandRepository.getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(carBrand, countryOfOrigin, yearOfFoundation);
    }
}
