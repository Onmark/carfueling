package com.markovic.carfueling.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


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
    @NotEmpty(message = "Please enter Station Name")
    private String stationName;

    private int liters;

    private int pricePerLiter;

    @NotNull(message = "Please enter Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // This is for bind Date with @ModelAttribute
    private LocalDate date;

    @ManyToOne
    @NonNull
    private Car car;


}
