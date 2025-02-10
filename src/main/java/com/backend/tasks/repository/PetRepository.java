package com.backend.tasks.repository;

import com.backend.tasks.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query(value ="SELECT count(*) FROM Pet WHERE (pet_type = 'DOG'  OR (pet_type = 'CAT' AND lost_tracker = false)) AND in_zone = false", nativeQuery = true)
    Long calculateOutsidePets();
}
