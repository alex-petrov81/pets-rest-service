package com.backend.tasks.model;

import com.backend.tasks.model.dict.PetType;
import com.backend.tasks.model.dict.TrackerType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "pet")
public class Pet {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pet_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Column(name = "tracker_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrackerType trackerType;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "in_zone")
    private Boolean inZone = false;

    @Column(name = "lost_tracker")
    private Boolean lostTracker;

}
