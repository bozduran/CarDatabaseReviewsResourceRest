package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.mappers.CarBrandMapper;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.repositories.CarBrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
    public CarBrandDTO getCarBrandByName(String carBrandName) {
        CarBrandDTO carBrandDTO = carBrandMapper.carBrandToCarBrandDto(
                carBrandRepository.getCarBrandByBrandName(carBrandName));
        return carBrandDTO;
    }

    @Override
    public List<CarBrandDTO> getAllBrands() {
        return carBrandRepository.findAll()
                .stream()
                .map(carBrandMapper::carBrandToCarBrandDto)
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
            carBrand.setBrandName(carBrand.getBrandName());
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
}
