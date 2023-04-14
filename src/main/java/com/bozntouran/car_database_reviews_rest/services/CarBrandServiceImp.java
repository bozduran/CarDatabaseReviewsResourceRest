package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.mappers.CarBrandMapper;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.repositories.CarBrandRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class CarBrandServiceImp implements CarBrandService{
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final CarBrandRepository carBrandRepository;
    private final CarBrandMapper carBrandMapper;


    @Override
    public Optional<CarBrandDTO> getCarBrandByID(UUID id) {

        CarBrandDTO carBrandDTO = carBrandMapper.carBrandToCarBrandDto(
                carBrandRepository.getCarBrandById(id));
        return Optional.ofNullable(Optional.ofNullable(carBrandDTO).orElse(null));
    }

    @Override
    public Page<CarBrand> getCarBrandByName(String carBrandName, Pageable pageable) {
        return carBrandRepository.getCarBrandByBrandName(carBrandName, null);
    }

    @Override
    public Page<CarBrandDTO> getAllBrands(String carBrand,
                                          String countryOfOrigin,
                                          Integer yearOfFoundation,
                                          Integer pageNumber,
                                          Integer pageSize) {

        PageRequest pageRequest = pageRequestBuilder(pageNumber, pageSize);

        Page<CarBrand> carBrandPage ;

        if(carBrand == null && countryOfOrigin == null && yearOfFoundation == null) {
            carBrandPage = carBrandRepository.findAll(pageRequest);
        } else if(carBrand != null && countryOfOrigin == null && yearOfFoundation == null) {
            carBrandPage = getCarBrandByName(carBrand, pageRequest);
        } else if(carBrand == null && countryOfOrigin != null && yearOfFoundation == null) {
            carBrandPage = getCarBrandByCountryOfOrigin(countryOfOrigin, pageRequest);
        } else if(carBrand == null && countryOfOrigin == null && yearOfFoundation != null) {
            carBrandPage = getCarBrandByYearOfFoundation(yearOfFoundation, pageRequest);
        } else if(carBrand != null && countryOfOrigin != null && yearOfFoundation == null) {
            carBrandPage = getCarBrandByBrandNameAndCountryOfOrigin(carBrand, countryOfOrigin, pageRequest);
        } else if(carBrand != null && countryOfOrigin == null && yearOfFoundation != null) {
            carBrandPage = getCarBrandByBrandNameAndYearOfFoundation(carBrand, yearOfFoundation, pageRequest);
        } else if(carBrand == null && countryOfOrigin != null && yearOfFoundation != null) {
            carBrandPage = getCarBrandByYearOfFoundationAndCountryOfOrigin(yearOfFoundation, countryOfOrigin, pageRequest);
        } else{
            carBrandPage = getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(carBrand, countryOfOrigin, yearOfFoundation, pageRequest);
        }



        return carBrandPage.map(carBrandMapper::carBrandToCarBrandDto);

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
    public Page<CarBrand> getCarBrandByYearOfFoundation(Integer yearOfFoundation, org.springframework.data.domain.Pageable pageable) {
        return carBrandRepository.getCarBrandByYearOfFoundation(yearOfFoundation, pageable);
    }

    @Override
    public Page<CarBrand> getCarBrandByCountryOfOrigin(String countryOfOrigin, Pageable pageable) {
        return carBrandRepository.getCarBrandByCountryOfOrigin(countryOfOrigin, pageable);
    }

    @Override
    public Page<CarBrand> getCarBrandByBrandNameAndYearOfFoundation(String carBrandName, Integer yearOfFoundation, Pageable pageable) {
        return carBrandRepository.getCarBrandByBrandNameAndYearOfFoundation(carBrandName,yearOfFoundation, pageable);
    }

    @Override
    public Page<CarBrand> getCarBrandByBrandNameAndCountryOfOrigin(String carBrandName, String countryOfOrigigin, Pageable pageable) {
        return carBrandRepository.getCarBrandByBrandNameAndCountryOfOrigin(carBrandName,countryOfOrigigin, pageable);
    }

    @Override
    public Page<CarBrand> getCarBrandByYearOfFoundationAndCountryOfOrigin(Integer yearOfFoundation, String countryOfOrigin, Pageable pageable) {
        return carBrandRepository.getCarBrandByYearOfFoundationAndCountryOfOrigin(yearOfFoundation,countryOfOrigin, pageable);
    }

    @Override
    public Page<CarBrand> getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation
            (String carBrand, String countryOfOrigin, Integer yearOfFoundation, Pageable pageable) {
        return carBrandRepository.getCarBrandByBrandNameAndCountryOfOriginAndYearOfFoundation(
                carBrand, countryOfOrigin, yearOfFoundation, pageable);
    }

    public PageRequest pageRequestBuilder(Integer pageNumber, Integer pageSize){
        int queryPageSize ;
        int queryPageNumber;

        if (pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        }else{
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize != null && pageSize > 0){
            if (pageSize > 250){
                queryPageSize = 250;
            }else{
                queryPageSize = pageSize;
            }
        }else{
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        Sort sort = Sort.by("brandName");

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }
}
