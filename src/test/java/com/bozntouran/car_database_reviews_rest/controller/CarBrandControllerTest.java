package com.bozntouran.car_database_reviews_rest.controller;


import com.bozntouran.car_database_reviews_rest.model.CarBrandDTO;
import com.bozntouran.car_database_reviews_rest.services.CarBrandService;
import com.bozntouran.car_database_reviews_rest.services.CarBrandServiceForMock;
import com.bozntouran.car_database_reviews_rest.services.CarBrandServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Slf4j
@WebMvcTest(CarBrandController.class)
class CarBrandControllerTest {
    private static final String CAR_BRAND = "/api/brand";
    private static final String CAR_BRAND_ID = CAR_BRAND + "/{carBrandId}";

    @Autowired
    CarBrandController carBrandController;

    @MockBean
    CarBrandService carBrandService;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    CarBrandService carBrandServiceForMock;

    @Captor
    ArgumentCaptor<CarBrandDTO> carBrandDTOArgumentCaptor;
    @Captor
    ArgumentCaptor<UUID> UUIDArgumentCaptor;

    @BeforeEach
    void setUp() {
        carBrandServiceForMock = new CarBrandServiceForMock();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testPatchCarByID() throws Exception{

        CarBrandDTO carBrandDTO = CarBrandDTO.builder()
                .id(carBrandServiceForMock.getAllBrands(null, null, null, 1, 10).getContent().get(0).getId())
                .yearOfFoundation(null)
                .brandName(null)
                .countryOfOrigin(null)
                .build();

        Map<String, Object> carBrandMap = new HashMap<>();
        carBrandMap.put("brandName","Fiat");

        mockMvc.perform(patch(CAR_BRAND+"/"+carBrandDTO.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carBrandDTO)))
                        .andExpect(status().isNoContent());
        verify(carBrandService).patchCarBrandByID(UUIDArgumentCaptor.capture(), carBrandDTOArgumentCaptor.capture());

        assertThat(carBrandDTO.getId()).isEqualTo(UUIDArgumentCaptor.getValue());
        log.info(carBrandDTOArgumentCaptor.getValue().getBrandName());
        assertThat(carBrandMap.get("brandName")).isEqualTo("Fiat") ;

    }

    @Test
    void testUpdateCarByID() throws Exception{
        CarBrandDTO carBrandDTO = carBrandServiceForMock.getAllBrands(null, null, null, 1, 10).getContent().get(0);
        System.out.println(carBrandDTO.getId());
        carBrandDTO.setBrandName("Ferrari version");

        mockMvc.perform(put(CAR_BRAND+"/"+carBrandDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carBrandDTO)));

        verify(carBrandService).updateCarBRandByID(any(UUID.class), any(CarBrandDTO.class));

    }

    @Test
    void testCreate() throws Exception {
        CarBrandDTO carBrandDTO = carBrandServiceForMock.getAllBrands(null, null, null, 1, 10).getContent().get(0);
        carBrandDTO.setVersion(null);
        carBrandDTO.setId(null);
        given(carBrandService.saveNewCarBrand(any(CarBrandDTO.class)))
                .willReturn(carBrandServiceForMock.getAllBrands(null, null, null, 1, 10).getContent().get(1));

        mockMvc.perform(post(CAR_BRAND)
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carBrandDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testGetAllCarBrands() throws Exception {
        given(carBrandService.getAllBrands(any(),any(),any(),any(),any()))
                .willReturn(carBrandServiceForMock.getAllBrands(null, null, null, 1, 10));

        mockMvc.perform(get(CAR_BRAND)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(2)));
    }

    @Test
    void getCarBrandByIDNotFound() throws Exception{

        given(carBrandServiceForMock.getCarBrandByID(any(UUID.class))).willReturn(Optional.empty());
        mockMvc.perform(get(CAR_BRAND_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
    @Test
    void getCarBrandByID() throws Exception{
        CarBrandDTO carBrandDTO = carBrandServiceForMock.getAllBrands(null, null, null, 1, 10).getContent().get(0);

        given(carBrandService.getCarBrandByID(any(UUID.class))  ).willReturn(Optional.of(carBrandDTO));

        mockMvc.perform(get(CAR_BRAND+"/"+UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id",is(carBrandDTO.getId().toString()) ))
                .andExpect(jsonPath("$.brandName",is(carBrandDTO.getBrandName())))
        ;
    }

}