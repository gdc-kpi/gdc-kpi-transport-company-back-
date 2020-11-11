package com.ip737.transportcompany.transportcompany.data.entities;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    @Id
    private UUID id;
    private String fullname;
    private String carPlate;
}
