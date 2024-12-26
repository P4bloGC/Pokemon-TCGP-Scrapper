package com.tcgp.services;
import com.tcgp.models.*;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j
public class PocketLimitlessScraperService {

    private static final List<String> BASE_URLS = List.of(
            "https://pocket.limitlesstcg.com/cards/A1/",
            "https://pocket.limitlesstcg.com/cards/P-A/",
            "https://pocket.limitlesstcg.com/cards/A1a/"
    );

    private static final Map<String, String> energyMap = new HashMap<String, String>() {{
        put("G", "Grass");
        put("C", "Colorless");
        put("W", "Water");
        put("R", "Fire");
        put("L", "Lightning");
        put("P", "Psychic");
        put("D", "Darkness");
        put("F", "Fighting");
        put("M", "Metal");
    }};

    public List<PokemonCard> scrapeAllCards() {
        List<PokemonCard> allCards = new ArrayList<>();

        for (String baseUrl : BASE_URLS) {
            try {
                Document doc = Jsoup.connect(baseUrl).get();
                int totalCards = extractTotalCards(doc);

                log.info("Total de cartas en " + baseUrl + ": " + totalCards);

                for (int i = 1; i <= totalCards; i++) {
                    String url = baseUrl + i;
                    try {
                        allCards.add(scrapeCard(url));
                    } catch (Exception e) {
                        log.error("Error al procesar la carta en " + url + ": " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                log.error("Error al conectar con la base " + baseUrl + ": " + e.getMessage());
            }
        }

        return allCards;
    }

    private PokemonCard scrapeCard(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String cardType = doc.selectFirst(".card-text-type").text().split("-")[0].trim();
        String cardSubType = doc.selectFirst(".card-text-type").text().split("-")[1].trim();
        String cardName = doc.selectFirst(".card-text-name").text();

        log.info("Obteniendo la carta: " + cardName);

        // Crear el modelo de la carta
        PokemonCard card = new PokemonCard();
        card.setCardNumber(extractCardNumber(doc));
        card.setName(cardName);
        card.setSet(extractSet(doc));
        card.setPack(extractPack(doc));
        card.setRarity(extractRarity(doc));
        card.setCardType(cardType);
        card.setType(extractType(doc));
        card.setStage(extractStage(doc));
        card.setEvolvesFrom(extractEvolvesFrom(doc));
        card.setRetreatCost(extractRetreatCost(doc));
        card.setWeakness(extractWeakness(doc));
        card.setArtist(extractArtist(doc));
        card.setAbilites(extractAbilities(doc));
        card.setAttacks(extractAttacks(doc));
        card.setImageUrl(extractImage(doc));

        if(cardType.equals("Trainer")){
            card.setHp(null);
            card.setEffect(extractCardEffect(doc));
            card.setCardSubType(cardSubType);
        }else{
            card.setHp(extractHp(doc));
            card.setEffect(null);
            card.setCardSubType(null);
        }

        return card;
    }

    private List<Ability> extractAbilities(Document doc) {
        List<Ability> abilities = new ArrayList<>();
        Elements abilityElements = doc.select(".card-text-ability");

        for (Element abilityElement : abilityElements) {
            String abilityName = abilityElement.selectFirst(".card-text-ability-info") != null
                    ? abilityElement.selectFirst(".card-text-ability-info").text().replace("Ability:", "").trim()
                    : null;

            String abilityDescription = abilityElement.selectFirst(".card-text-ability-effect") != null
                    ? abilityElement.selectFirst(".card-text-ability-effect").text().trim()
                    : null;

            abilities.add(new Ability(abilityName, abilityDescription));
        }
        return abilities;
    }

    private List<Energy> extractEnergys(String attackCost){
        List<Energy> energyElements = new ArrayList<>();
        String[] parts = attackCost.split("");

        for(String part : parts){
            Energy energy = new Energy();
            energy.setEnergyType(this.getEnergy(part));
            energyElements.add(energy);
        }

        return energyElements;

    }

    private String getEnergy(String text) {
        return energyMap.getOrDefault(text, null);
    }

    private List<Attack> extractAttacks(Document doc) {
        List<Attack> attacks = new ArrayList<>();
        Elements attackElements = doc.select(".card-text-attack");

        for (Element attackElement : attackElements) {
            Attack attack = new Attack();
            String attackCost = attackElement.selectFirst(".ptcg-symbol").text();
            String attackInfo = attackElement.selectFirst(".card-text-attack-info").text();
            String powerStr = attackInfo.replaceAll("[^0-9\\+\\-x]", "").trim();
            String power = powerStr.isEmpty() ? null : powerStr;
            String attackName = attackInfo.replaceFirst("^" + attackCost, "").trim();
            attackName = attackName.replaceAll("(\\d+[\\+\\-x]?)$", "").trim();
            String attackEffect = attackElement.selectFirst(".card-text-attack-effect").text().trim();
            attack.setEnergyCost(this.extractEnergys(attackCost));
            attack.setName(attackName);
            attack.setPower(power);
            attack.setEffect(attackEffect);
            attacks.add(attack);
        }

        return attacks;
    }

    private String extractHp(Document doc) {
        Element hpElement = doc.selectFirst(".card-text-title");
        if (hpElement != null) {
            return hpElement.text().replaceAll(".*-.*-", "").replace("HP", "").trim();
        }
        return null;
    }

    private String extractRetreatCost(Document doc) {
        Element retreatElement = doc.selectFirst(".card-text-wrr");
        if (retreatElement != null && retreatElement.text().contains("Retreat:")) {
            return retreatElement.text().replaceAll(".*Retreat: ", "").trim();
        }
        return null;
    }

    private String extractWeakness(Document doc) {
        Element weaknessElement = doc.selectFirst(".card-text-wrr");
        if (weaknessElement != null && weaknessElement.text().contains("Weakness:")) {
            return weaknessElement.text().replaceAll(".*Weakness: ", "").split("Retreat")[0].trim();
        }
        return null;
    }

    private String extractArtist(Document doc) {
        Element artistElement = doc.selectFirst(".card-text-artist a");
        return artistElement != null ? artistElement.text() : null;
    }

    private String extractImage(Document doc) {
        Element imageElement = doc.selectFirst(".card-image img");
        if (imageElement != null) {
            return imageElement.attr("src");
        }
        return null;
    }

    private String extractType(Document doc) {
        Element titleElement = doc.selectFirst(".card-text-title");
        if (titleElement != null) {
            String fullText = titleElement.text();
            String[] parts = fullText.split(" - ");
            if (parts.length > 1) {
                return parts[1].trim();
            }
        }
        return null;
    }

    private String extractStage(Document doc) {
        Element titleElement = doc.selectFirst(".card-text-type");
        if (titleElement != null) {
            String fullText = titleElement.text();
            String[] parts = fullText.split(" - ");
            if (parts.length > 1) {
                String stageStr = parts[1].trim();
                return stageStr;

            }
        }
        return null;
    }

    private String extractEvolvesFrom(Document doc) {
        Element evolvesFromElement = doc.selectFirst(".card-text-type a");
        if (evolvesFromElement != null) {
            return evolvesFromElement.text();
        }
        return null;
    }

    public String extractSet(Document doc){
        return doc.selectFirst(".prints-current-details .text-lg").text().trim();
    }

    private String extractPack(Document doc) {
        Element packElement = doc.selectFirst(".prints-current-details span:nth-of-type(2)");
        if (packElement != null) {
            String[] parts = packElement.text().split("·");
            if (parts.length >= 3) {
                return parts[2].trim();
            }
        }
        return null;
    }

    private String extractRarity(Document doc) {
        Element rarityElement = doc.selectFirst(".prints-current-details span:nth-of-type(2)");
        if (rarityElement != null) {
            String[] parts = rarityElement.text().split("·");
            if (parts.length >= 2) {
                return parts[1].trim();
            }
        }
        return null;
    }

    private String extractCardNumber(Document doc) {
        Element rarityElement = doc.selectFirst(".prints-current-details span:nth-of-type(2)");
        if (rarityElement != null) {
            String[] parts = rarityElement.text().split("·");
            if (parts.length >= 2) {
                return parts[0].replaceAll("#", "").trim();
            }
        }
        return null;
    }

    private String extractCardEffect(Document doc) {
        Element effectElement = doc.selectFirst(".card-text-section:nth-of-type(2)");
        if (effectElement != null) {
            return effectElement.text().trim();
        }
        return null;
    }

    private Integer extractTotalCards(Document doc) {
        Element infoBoxLine = doc.selectFirst(".infobox-line");
        if (infoBoxLine != null) {
            String[] parts = infoBoxLine.text().split("•");
            if (parts.length >= 2) {
                String cardsPart = parts[1].trim();
                String totalCards = cardsPart.replaceAll("[^0-9]", "");
                return Integer.parseInt(totalCards);
            }else{
                String totalCards = infoBoxLine.text().replaceAll("[^0-9]", "");
                return Integer.parseInt(totalCards);
            }
        }
        return 0;
    }

}
