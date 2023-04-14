package com.bozntouran.car_database_reviews_rest.services;

import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.mappers.CarModelMapper;
import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import com.bozntouran.car_database_reviews_rest.repositories.CarModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@AllArgsConstructor
public class CarModelServiceImp implements CarModelService{

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final CarModelRepository carModelRepository;
    private CarModelMapper carModelMapper;

    @Override
    public Page<CarModelDTO> getAllCarModels(String modelName,
                                             String drive,
                                             String fuelType,
                                             String transmision,
                                             String carType,
                                             Integer yearOfProduction,
                                             Integer pageNumber,
                                             Integer pageSize) {

        PageRequest pageRequest = pageRequestBuilder(pageNumber, pageSize);

        Page<CarModel> carModelPage;

        //add ifs
        if (modelName == null &&
                drive == null &&
                fuelType == null &&
                transmision == null &&
                carType == null &&
                carType == null &&
        yearOfProduction == null ){
            carModelPage = carModelRepository.findAll(pageRequest);
        }else{
            carModelPage = carModelRepository.findAll(pageRequest);

        }







        return carModelPage.map(carModelMapper::carModelToCarModelDto);

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
        return Optional.of(carModelMapper.carModelToCarModelDto(
                carModelRepository.getCarModelById(carModelID)
        ));
    }

    @Override
    public boolean deleteCarModelByID(UUID carModelID) {
        if ( carModelRepository.existsById(carModelID)){
            carModelRepository.deleteById(carModelID);
            return true;
        }
        return false;
    }

    @Override
    public void pattchCarModelByID(UUID carModelID, CarModelDTO carModelDTO) {
        AtomicReference<Optional<CarModelDTO>> atomicReference = new AtomicReference<>();
        carModelRepository.findById(carModelID).ifPresentOrElse(retrivedCarModel ->{
            if (carModelDTO.getModelName() != null){
                retrivedCarModel.setModelName(carModelDTO.getModelName());
            }
            if(carModelDTO.getCarType() != null){
                retrivedCarModel.setCarType(carModelDTO.getCarType());
            }
            if(carModelDTO.getFuelType() !=null){
                retrivedCarModel.setFuelType(carModelDTO.getFuelType());
            }
            if(carModelDTO.getDrive()!=null){
                retrivedCarModel.setDrive(carModelDTO.getDrive());
            }
            if(carModelDTO.getTransmision()!=null){
                retrivedCarModel.setTransmision(carModelDTO.getTransmision());
            }
            if(carModelDTO.getYearOfProduction()!=null){
                retrivedCarModel.setYearOfProduction(carModelDTO.getYearOfProduction());
            }
        },()-> {
            atomicReference.set(Optional.empty());
            }
        );
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
