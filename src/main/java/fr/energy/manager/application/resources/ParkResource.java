package fr.energy.manager.application.resources;


import fr.energy.manager.application.dtos.ParkCreationDto;
import fr.energy.manager.application.mappers.ParkCreationDtoMapper;
import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.ports.api.EnergyParkCreationApi;
import fr.energy.manager.infrastructure.adapters.EnergyParkSearchAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parks")
public class ParkResource {

  private final EnergyParkCreationApi parkCreationApi;
  private final EnergyParkSearchAdapter searchAdapter; // /!\ Don't do that in a real case

  @PostMapping
  @Operation(summary = "Create a new Park")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Park successfully created",
        content = @Content(schema = @Schema(implementation = EnergyPark.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Malformed request",
        content = @Content(schema = @Schema(implementation = Error.class)))
  })
  public ResponseEntity<?> create(@RequestBody ParkCreationDto dto) {
    return parkCreationApi
        .create(ParkCreationDtoMapper.toDomain(dto))
        .fold(error -> ResponseEntity.badRequest().body(error), ResponseEntity::ok);
  }

  @GetMapping
  @Operation(summary = "Search for parks using RSQL syntax")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Represents a page of parks (maybe empty)",
        content = @Content(schema = @Schema(implementation = Page.class))),
  })
  public ResponseEntity<?> search(
      @RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestParam(value = "query", required = false) String query,
      @RequestParam(value = "sort", required = false) String sort) {
    return searchAdapter
        .search(page, size, query, sort)
        .fold(error -> ResponseEntity.badRequest().body(error), ResponseEntity::ok);
  }
}
