package com.vegetableshoppingbe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int month;
    private Double revenue;

}

