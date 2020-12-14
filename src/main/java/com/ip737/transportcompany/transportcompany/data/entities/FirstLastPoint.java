package com.ip737.transportcompany.transportcompany.data.entities;

import lombok.*;

import javax.persistence.Entity;

//@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirstLastPoint {
    public Coordinates source;
    public Coordinates destination;
}
