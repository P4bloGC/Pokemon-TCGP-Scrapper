package com.tcgp.controllers;

import com.tcgp.models.PokemonCard;
import com.tcgp.services.PocketLimitlessScraperService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/scraper")
public class PocketLimitlessScraperController {

    private final PocketLimitlessScraperService scraperService;

    public PocketLimitlessScraperController(PocketLimitlessScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @GetMapping("/cards")
    @Operation(summary = "Endpoint para obtener cartas de Pokémon de una lista de colecciones")
    public List<PokemonCard> scrapeCards() throws IOException {
            return scraperService.scrapeAllCards();
    }
}
