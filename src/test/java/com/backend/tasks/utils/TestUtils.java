package com.backend.tasks.utils;

import com.backend.tasks.dto.PetCreateOrUpdateDto;
import com.backend.tasks.model.Pet;
import com.backend.tasks.model.dict.PetType;
import com.backend.tasks.model.dict.TrackerType;

public class TestUtils {

    public static Pet createDog(Long id, TrackerType trackerType, Integer ownerId, Boolean inZone) {
        return createPet(id, PetType.DOG, trackerType, ownerId, inZone);
    }

    public static Pet createCat(Long id, TrackerType trackerType, Integer ownerId, Boolean inZone, Boolean lostTracker) {
        Pet cat = createPet(id, PetType.CAT, trackerType, ownerId, inZone);
        cat.setLostTracker(lostTracker);
        return cat;
    }

    public static Pet createPet(Long id, PetType petType, TrackerType trackerType, Integer ownerId, Boolean inZone) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setPetType(petType);
        pet.setTrackerType(trackerType);
        pet.setOwnerId(ownerId);
        pet.setInZone(inZone);
        return pet;
    }

    public static PetCreateOrUpdateDto createCatCreateOrUpdateDto(TrackerType trackerType, Integer ownerId, Boolean inZone, Boolean lostTracker) {
        PetCreateOrUpdateDto petCreateOrUpdateDto = createPetCreateOrUpdateDto(PetType.CAT, trackerType, ownerId, inZone);
        petCreateOrUpdateDto.setLostTracker(lostTracker);
        return petCreateOrUpdateDto;
    }

    public static PetCreateOrUpdateDto createDogCreateOrUpdateDto(TrackerType trackerType, Integer ownerId, Boolean inZone) {
        PetCreateOrUpdateDto petCreateOrUpdateDto = createPetCreateOrUpdateDto(PetType.DOG, trackerType, ownerId, inZone);
        return petCreateOrUpdateDto;
    }

    public static PetCreateOrUpdateDto createPetCreateOrUpdateDto(PetType petType, TrackerType trackerType, Integer ownerId, Boolean inZone) {
        PetCreateOrUpdateDto result = new PetCreateOrUpdateDto();
        result.setPetType(petType);
        result.setTrackerType(trackerType);
        result.setOwnerId(ownerId);
        result.setInZone(inZone);
        return result;
    }
}
