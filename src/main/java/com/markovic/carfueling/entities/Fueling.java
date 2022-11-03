package com.markovic.carfueling.entities;

import lombok.*;
import org.springframework.boot.actuate.endpoint.web.Link;

import javax.persistence.*;


@Entity
@RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Fueling {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String stationName;
    @NonNull
    private String liters;
    @NonNull
    private String pricePerLiter;

    private String datum;

    @ManyToOne
    @NonNull
    private Car car;


}
