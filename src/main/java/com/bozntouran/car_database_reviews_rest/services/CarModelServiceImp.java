package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.mappers.CarModelMapper;
import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import com.bozntouran.car_database_reviews_rest.repositories.CarModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class CarModelServiceImp implements CarModelService{

    private final CarModelRepository carModelRepository;
    private CarModelMapper carModelMapper;

    @Override
    public List<CarModelDTO> getAllModels() {
        return carModelRepository.findAll()
                .stream()
                .map(carModelMapper::carModelToCarModelDto)
                .collect(Collectors.toList());
    }

    @Override
    public CarModelDTO saveCarModel(CarModelDTO carModelDTO) {
        return carModelMapper.carModelToCarModelDto(
        carModelRepository.save(
                carModelMapper
                        .carModelDtoToCarModel(carModelDTO)));

    }

    @Override
    public Optional<CarModelDTO> getCarModelByID(UUID carModelID) {
        System.out.println(carModelRepository.getCarModelById(carModelID).getId());
        return Optional.of(carModelMapper.carModelToCarModelDto(
                carModelRepository.getCarModelById(carModelID)
        ));
    }
}
