package com.markovic.carfueling.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty(message = "Please enter Full Name of the car")
    private String fullName;

    @NonNull
    @NotEmpty(message = "Please enter fuel type of the car")
    private String fuel;

    @NonNull
    @NotEmpty(message = "Please enter year of production of the car")
    private String productionYear;

    @NonNull
    @NotEmpty(message = "Please enter owner")
    private String owner;

    @OneToMany(
            mappedBy = "car",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Fueling> fuelings = new ArrayList<>();


}
