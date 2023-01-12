package fr.energy.manager.application.resources;

import fr.energy.manager.application.dtos.OfferCreationDto;
import fr.energy.manager.application.dtos.ParkCreationDto;
import fr.energy.manager.application.dtos.ProductionCapacityCreationDto;
import fr.energy.manager.application.mappers.ParkCreationDtoMapper;
import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.model.TimeBloc;
import fr.energy.manager.domain.ports.api.EnergyParkCreationApi;
import fr.energy.manager.domain.validations.ValidationError;
import fr.energy.manager.infrastructure.adapters.EnergyParkSearchAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static fr.energy.manager.application.TestUtils.OBJECT_MAPPER;
import static fr.energy.manager.domain.model.PowerMarket.PRIMARY_RESERVE;
import static fr.energy.manager.domain.model.ProductionType.SOLAR;
import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ParkResource.class})
class ParkResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EnergyParkCreationApi parkCreationApi;
    @MockBean
    private EnergyParkSearchAdapter searchAdapter;

    @Test
    void should_create_park() throws Exception {
        final var day = LocalDate.of(2022, 6, 5);
        final var given =
                ParkCreationDto.builder()
                        .name("name")
                        .productionType(SOLAR)
                        .productionCapacities(
                                java.util.List.of(
                                        ProductionCapacityCreationDto.builder().capacity(40).day(day).build()))
                        .offers(
                                java.util.List.of(
                                        OfferCreationDto.builder()
                                                .powerMarket(PRIMARY_RESERVE)
                                                .day(day)
                                                .timeBloc(TimeBloc.NINE_AM_TO_NOON)
                                                .floorPrice(50.12)
                                                .energyQuantity(25)
                                                .build()))
                        .build();
        final var park = ParkCreationDtoMapper.toDomain(given);

        when(parkCreationApi.create(any(EnergyPark.class))).thenReturn(Right(park));

        mockMvc
                .perform(
                        post("/parks")
                                .content(OBJECT_MAPPER.writeValueAsString(given))
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string(OBJECT_MAPPER.writeValueAsString(park)));

        verify(parkCreationApi).create(any(EnergyPark.class));
        verifyNoMoreInteractions(parkCreationApi);
    }

    @Test
    void should_not_create_park() throws Exception {
        final var day = LocalDate.of(2022, 6, 5);
        final var given =
                ParkCreationDto.builder()
                        .name("name")
                        .productionType(SOLAR)
                        .productionCapacities(
                                java.util.List.of(
                                        ProductionCapacityCreationDto.builder().capacity(40).day(day).build()))
                        .offers(
                                java.util.List.of(
                                        OfferCreationDto.builder()
                                                .powerMarket(PRIMARY_RESERVE)
                                                .day(day)
                                                .timeBloc(TimeBloc.NINE_AM_TO_NOON)
                                                .floorPrice(50.12)
                                                .energyQuantity(25)
                                                .build()))
                        .build();
        final var park = ParkCreationDtoMapper.toDomain(given);

        when(parkCreationApi.create(any(EnergyPark.class))).thenReturn(Left(ValidationError.of("message", null)));

        mockMvc
                .perform(
                        post("/parks")
                                .content(OBJECT_MAPPER.writeValueAsString(given))
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON));

        verify(parkCreationApi).create(any(EnergyPark.class));
        verifyNoMoreInteractions(parkCreationApi);
    }
}
