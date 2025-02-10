package com.backend.tasks.service.pet.impl;

import com.backend.tasks.ApplicationTest;
import com.backend.tasks.exceptions.ObjectNotFoundException;
import com.backend.tasks.model.Pet;
import com.backend.tasks.model.dict.TrackerType;
import com.backend.tasks.repository.PetRepository;
import com.backend.tasks.service.pet.PetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.backend.tasks.utils.TestUtils.createCat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
public class PetServiceImplTest {
    @Autowired
    private PetService petService;

    @MockBean
    private PetRepository petRepository;

    @Test
    public void create() {
        Pet petA = createCat(null, TrackerType.SMALL, 22, true, false);
        Pet createdPetA = createCat(1L, TrackerType.SMALL, 22, true, false);

        when(petRepository.save(petA)).thenReturn(createdPetA);

        Pet createdPet = petService.create(petA);

        assertThat(createdPet).isEqualTo(createdPetA);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void updateWithNotExistedPet() {
        Pet petA = createCat(1L, TrackerType.SMALL, 22, true, false);
        when(petRepository.findById(petA.getId())).thenReturn(Optional.empty());
        petService.update(petA);
    }

    @Test
    public void update() {
        Pet petA = createCat(1L, TrackerType.SMALL, 22, true, false);
        Pet updatedPetA = createCat(1L, TrackerType.SMALL, 22, false, true);

        when(petRepository.findById(petA.getId())).thenReturn(Optional.of(petA));
        when(petRepository.save(updatedPetA)).thenReturn(updatedPetA);

        Pet updatedPet = petService.update(updatedPetA);

        assertThat(updatedPet.getId()).isEqualTo(updatedPetA.getId());
        assertThat(updatedPet.getInZone()).isEqualTo(updatedPetA.getInZone());
        assertThat(updatedPet.getLostTracker()).isEqualTo(updatedPetA.getLostTracker());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void delete() {
        Pet petA = createCat(1L, TrackerType.SMALL, 22, true, false);

        doNothing().when(petRepository).deleteById(petA.getId());
        when(petRepository.findById(petA.getId())).thenReturn(Optional.empty());

        petService.delete(petA.getId());

        petService.find(petA.getId());
    }

    @Test
    public void find() {
        Pet petA = createCat(1L, TrackerType.SMALL, 22, true, false);

        when(petRepository.findById(petA.getId())).thenReturn(Optional.of(petA));

        Pet petById = petService.find(petA.getId());

        assertThat(petById).isEqualTo(petA);
    }

    @Test
    public void calculateOutsidePets() {
        when(petRepository.calculateOutsidePets()).thenReturn(2L);
        Long result = petService.calculateOutsidePets();

        assertThat(result).isEqualTo(2);
    }

}
