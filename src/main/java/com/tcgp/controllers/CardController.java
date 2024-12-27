package com.tcgp.controllers;

import com.tcgp.models.Card;
import com.tcgp.services.Cardservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    Cardservice cardservice;

    @GetMapping("/save")
    public ResponseEntity<List<Card>> loadCards() {
        return ResponseEntity.ok(cardservice.loadCards());
    }
}
