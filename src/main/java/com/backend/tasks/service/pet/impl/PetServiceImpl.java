package com.backend.tasks.service.pet.impl;

import com.backend.tasks.exceptions.ObjectNotFoundException;
import com.backend.tasks.model.Pet;
import com.backend.tasks.repository.PetRepository;
import com.backend.tasks.service.pet.PetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;


    @Transactional
    @Override
    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }

    @Transactional
    @Override
    public Pet find(Long id) {
        Optional<Pet> petOpt = petRepository.findById(id);
        if (petOpt.isEmpty()) {
            ObjectNotFoundException notFoundException = new ObjectNotFoundException("Pet", id);
            log.error(notFoundException.getMessage());
            throw notFoundException;
        }
        return petOpt.orElse(null);
    }

    @Transactional
    @Override
    public Pet update(Pet pet) {
        Long petId = pet.getId();
        Optional<Pet> petOpt = petRepository.findById(petId);
        if (petOpt.isEmpty()) {
            ObjectNotFoundException notFoundException = new ObjectNotFoundException("Pet", petId);
            log.error(notFoundException.getMessage());
            throw notFoundException;
        }
        return petRepository.save(pet);
    }

    @Transactional
    @Override
    public void delete(Long petId) {
        Pet pet = find(petId);
        petRepository.deleteById(pet.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Long calculateOutsidePets() {
        return petRepository.calculateOutsidePets();
    }
}

