package com.bozntouran.car_database_reviews_rest.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link com.bozntouran.car_database_reviews_rest.entities.CarBrand} entity
 */
@Data
@Builder
public class CarBrandDTO implements Serializable {
    private  UUID id;
    private  Integer version;
    @NotNull
    @NotBlank
    private  String brandName;
    @NotNull
    @NotBlank
    private  String countryOfOrigin;
    @Min(value = 1800)
    private  Integer creationYear;
//    private  Set<CarModelDTO> models ;
}