package com.markovic.carfueling.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String fullName;
    @NonNull
    private String fuel;
    @NonNull
    private String productionYear;
    @NonNull
    private String owner;

}
