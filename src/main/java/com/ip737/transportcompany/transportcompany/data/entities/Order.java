package com.ip737.transportcompany.transportcompany.data.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String orderId;

    private double sourceLatitude;
    private double sourceLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private double volume;
    private double weight;
    private String car_id;
    private String admins_id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    public String status;

}