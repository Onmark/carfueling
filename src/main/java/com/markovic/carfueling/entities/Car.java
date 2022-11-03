package com.markovic.carfueling.entities;

import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String fullName;
    @NonNull
    private String fuel;
    @NonNull
    private String productionYear;
    @NonNull
    private String owner;

    @OneToMany(mappedBy = "car")
    private List<Fueling> fuelings = new ArrayList<>();

    public void addFueling(Fueling fueling) {
        fuelings.add(fueling);
    }

}
