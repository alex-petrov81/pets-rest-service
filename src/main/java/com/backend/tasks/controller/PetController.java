package com.backend.tasks.controller;

import com.backend.tasks.dto.PetCreateOrUpdateDto;
import com.backend.tasks.dto.PetReadDto;
import com.backend.tasks.model.Pet;
import com.backend.tasks.service.pet.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.backend.tasks.mapper.PetMapper.INSTANCE;
import static org.springframework.http.HttpStatus.CREATED;

/**
 * Implement create, read, update, delete  rest controller endpoints for pets.
 * Map endpoints to /pets path.
 * 1. Post to /pets endpoint should create and return pet. Response status should be 201.
 * 2. Put to /pets/{petId} endpoint should update, save and return pet with petId=petId.
 * 3. Get to /pets/{petId} endpoint should fetch and return pet with petId=petId.
 * 4. Delete to /pets/{petId} endpoint should delete pet with petId=petId. Response status should be 204.
 * 5. Get to /pets endpoint should return list of all pets
 * 6. Get /pets/calculateOutsidePets calculates the number of pets (dogs and cats) outside of the power saving zone based on data from different types of trackers
 */
@Api
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/pets")
public class PetController {
    private final PetService petService;


    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful operation", response = PetReadDto.class),
            @ApiResponse(code = 400, message = "Validation exception")})
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity create(@Valid @RequestBody PetCreateOrUpdateDto petCreateDto) {

        Pet pet = INSTANCE.petCreateOrUpdateDtoToPet(petCreateDto);
        pet = petService.create(pet);
        PetReadDto result = INSTANCE.petToPetReadDto(pet);
        return ResponseEntity.status(CREATED).body(result);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = PetReadDto.class),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "Pet not found")})
    @PutMapping(value = "/{petId}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<PetReadDto> update(@PathVariable("petId") Long id, @Valid @RequestBody PetCreateOrUpdateDto petDto) {

        Pet pet = INSTANCE.petCreateOrUpdateDtoToPet(petDto);
        pet.setId(id);

        pet = petService.update(pet);
        PetReadDto result = INSTANCE.petToPetReadDto(pet);
        return ResponseEntity.ok(result);
    }


    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "Pet not found")})
    @DeleteMapping(value = "/{petId}", produces = {"application/json"})
    public ResponseEntity delete(@PathVariable("petId") Long petId) {
        petService.delete(petId);
        return ResponseEntity.noContent().build();
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Long.class)})
    @GetMapping(value = "/calculateOutsidePets", produces = {"application/json"})
    public ResponseEntity<Long> calculateOutsidePets() {
        Long result = petService.calculateOutsidePets();
        return ResponseEntity.ok(result);
    }

}
