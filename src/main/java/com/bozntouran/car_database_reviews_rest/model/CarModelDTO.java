package com.bozntouran.car_database_reviews_rest.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link com.bozntouran.car_database_reviews_rest.entities.CarModel} entity
 */
@Data
@Builder
public class CarModelDTO implements Serializable {
    private  UUID id;
    private  Integer version;
//    private  CarBrandDTO carBrand;
    @NotNull
    @NotBlank
    private  String modelName;
    @Min(value = 1800)
    private  Integer yearOfProduction;

}