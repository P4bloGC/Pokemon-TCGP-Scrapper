package com.tcgp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data

@AllArgsConstructor
@NoArgsConstructor
public class Attack {
    private String cost;
    private String name;
    private String effect;
    private String power;
    private Boolean variableDamage;
}