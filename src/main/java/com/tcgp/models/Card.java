package com.tcgp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String cardNumber;
    private String cardType;
    private String cardSubType;
    private String set;
    private String pack;
    private String name;
    private String stage;
    private String evolvesFrom;
    private String type;
    private String hp;
    private String retreatCost;
    private String weakness;
    private String artist;
    private String imageUrl;
    private List<Ability> abilites;
    private List<Attack> attacks;
    private String effect;
    private String rarity;
}

