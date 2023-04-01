package com.bozntouran.car_database_reviews_rest.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CarModel {

    public CarModel(UUID id, Integer version, String modelName, Integer yearOfProduction, String drive, String fuelType, String transmision, String carType, LocalDateTime createdDate, LocalDateTime updateDate, CarBrand carBrand) {
        this.id = id;
        this.version = version;
        this.modelName = modelName;
        this.yearOfProduction = yearOfProduction;
        this.drive = drive;
        this.fuelType = fuelType;
        this.transmision = transmision;
        this.carType = carType;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.carBrand = carBrand;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    private String modelName;
    @Min(value = 1800)
    private Integer yearOfProduction;

    private String drive;
    private String fuelType;
    private String transmision;
    private String carType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;


    @ManyToOne()
    private CarBrand carBrand;

    public void setCarBrand(CarBrand carBrand){
        this.carBrand  = carBrand;
        carBrand.getModels().add(this);
    }


}
