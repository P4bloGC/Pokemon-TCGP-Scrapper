package com.tcgp.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.tcgp.models.Set;
import com.tcgp.models.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcgp.mappers.SetMapper;

@Service
public class Cardservice {
    @Autowired
    SetMapper setMapper;

    public List<Card> saveCards(){
        List<Card> cards = loadCards();

        for(Card card : cards){
            this.insertSet(card.getSet());
        }
        return cards;
    }

    public List<Card> loadCards() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("cards_data.json");
            List<Card> cards = objectMapper.readValue(file, new TypeReference<List<Card>>() {
            });
            return cards;
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo JSON", e);
        }
    }

    public Void insertSet(String text) {
        Set set = setMapper.getSetByName(text);

        if(set == null){
            Set setDB = new Set();
            setDB.setSetName(text);
            setMapper.insertSet(setDB);
        }
        return null;
    }

}
