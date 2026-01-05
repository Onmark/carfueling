package com.markovic.carfueling.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @JsonBackReference
    @NonNull
    private Car car;


}
