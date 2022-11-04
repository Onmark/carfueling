package com.markovic.carfueling.entities;

import lombok.*;
import org.springframework.boot.actuate.endpoint.web.Link;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Fueling {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NonNull
    @NotEmpty(message="Please enter Station Name")
    private String stationName;
    
    @NonNull
    @NotEmpty(message="Please enter volume that was refueled (in chosen units)")
    private String liters;
    
    @NonNull
    @NotEmpty(message="Please enter price of fuel per unit")
    private String pricePerLiter;
    
    @NonNull
    @NotEmpty(message="Please enter the date of refueling")
    private String datum;

    @ManyToOne
    @NonNull
    private Car car;


}
