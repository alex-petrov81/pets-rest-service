package com.backend.tasks.service.pet;

import com.backend.tasks.model.Pet;

public interface PetService {
    Pet create(Pet pet);

    Pet find(Long id);

    Pet update(Pet pet);

    void delete(Long petId);


    Long calculateOutsidePets();
}
