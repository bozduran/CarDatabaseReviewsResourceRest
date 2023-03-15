package com.bozntouran.car_database_reviews_rest.mappers;

import com.bozntouran.car_database_reviews_rest.entities.CarModel;
import com.bozntouran.car_database_reviews_rest.model.CarModelDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarModelMapper {
    CarModel carModelDtoToCarModel(CarModelDTO carModelDTO);
    CarModelDTO carModelToCarModelDto(CarModel carModel);
}

