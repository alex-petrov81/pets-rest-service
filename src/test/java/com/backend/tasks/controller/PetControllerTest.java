package com.backend.tasks.controller;

import com.backend.tasks.ApplicationTest;
import com.backend.tasks.dto.PetCreateOrUpdateDto;
import com.backend.tasks.exceptions.ExceptionTranslator;
import com.backend.tasks.exceptions.ObjectNotFoundException;
import com.backend.tasks.model.Pet;
import com.backend.tasks.model.dict.PetType;
import com.backend.tasks.model.dict.TrackerType;
import com.backend.tasks.service.pet.PetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Random;

import static com.backend.tasks.utils.TestUtils.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Implement tests for PetController
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ApplicationTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetControllerTest extends BaseControllerTest {

    @MockBean
    private PetService petService;

    @Test
    public void create() throws Exception {
        PetCreateOrUpdateDto catADto = createCatCreateOrUpdateDto(TrackerType.SMALL, 22, true, false);

        Random random = new Random();
        Pet catA = createCat(random.nextLong(), TrackerType.SMALL, 22, true, false);

        when(petService.create((Pet) notNull())).thenReturn(catA);

        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(json(catADto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(catA.getId()))
                .andExpect(jsonPath("$.petType").value(catA.getPetType().name()));


    }

    @Test
    public void createWithValidationErrors() throws Exception {
        PetCreateOrUpdateDto catADto = createPetCreateOrUpdateDto(PetType.CAT, TrackerType.SMALL, 22, null);
        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(json(catADto)))
                .andDo(print()).andExpect(status().isBadRequest());

        PetCreateOrUpdateDto dogADto = createDogCreateOrUpdateDto(TrackerType.MEDIUM, 22, null);
        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(json(dogADto)))
                .andDo(print()).andExpect(status().isBadRequest());

        dogADto = createDogCreateOrUpdateDto(TrackerType.MEDIUM, null, true);
        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(json(dogADto)))
                .andDo(print()).andExpect(status().isBadRequest());

        dogADto = createDogCreateOrUpdateDto(null, 22, true);
        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(json(dogADto)))
                .andDo(print()).andExpect(status().isBadRequest());

        catADto = createPetCreateOrUpdateDto(null, TrackerType.SMALL, 22, true);
        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(json(catADto)))
                .andDo(print()).andExpect(status().isBadRequest());

    }

    @Test
    public void update() throws Exception {
        PetCreateOrUpdateDto catADto = createCatCreateOrUpdateDto(TrackerType.BIG, 22, false, false);


        Random random = new Random();
        Long id = random.nextLong();
        Pet petA = createCat(id, TrackerType.SMALL, 22, true, false);
        Pet petAUpdated = createCat(id, TrackerType.BIG, 22, false, false);

        when(petService.find(id)).thenReturn(petA);
        when(petService.update(petAUpdated)).thenReturn(petAUpdated);


        mockMvc.perform(put("/pets/" + id).contentType(MediaType.APPLICATION_JSON).content(json(catADto)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(petA.getId())))
                .andExpect(jsonPath("$.petType", is(petAUpdated.getPetType().name())))
                .andExpect(jsonPath("$.trackerType", is(petAUpdated.getTrackerType().name())))
                .andExpect(jsonPath("$.inZone", is(petAUpdated.getInZone())));
    }

    @Test
    public void deletePet() throws Exception {
        Random random = new Random();
        Long id = random.nextLong();

        Pet petA = createCat(id, TrackerType.SMALL, 22, true, false);

        PetService serviceMock = mock(PetService.class);
        when(petService.find(id)).thenReturn(petA);
        doNothing().when(serviceMock).delete(id);

        mockMvc.perform(delete("/pets/" + id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNoContent());

    }

    @Test
    public void deleteOrganizationNotFound() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(petService).setControllerAdvice(new ExceptionTranslator(null)).build();
        Random random = new Random();
        Long id = random.nextLong();

        PetService serviceMock = mock(PetService.class);
        doThrow(new ObjectNotFoundException("Pet", id)).when(serviceMock).delete(id);

        mockMvc.perform(delete("/pets/" + id).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void calculateOutsidePets() throws Exception {
        Random random = new Random();
        Long pets = random.nextLong();
        when(petService.calculateOutsidePets()).thenReturn(pets);

        mockMvc.perform(get("/pets/calculateOutsidePets").contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(pets.toString()));

    }
}
