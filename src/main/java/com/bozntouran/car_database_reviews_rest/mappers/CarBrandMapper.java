package com.bozntouran.car_database_reviews_rest.mappers;

import com.bozntouran.car_database_reviews_rest.entities.CarBrand;
import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarBrandMapper {
    CarBrand carBrandDtoToCarBrand(CarBrandDTO carBrandDTO);
    CarBrandDTO carBrandToCarBrandDto(CarBrand carBrand);
}
